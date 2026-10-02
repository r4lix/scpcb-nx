#!/usr/bin/env python3
"""Build the runnable game directory.

Copies the unmodified upstream sources from scpcb/ into run/, applies the small
platform patches listed in PATCHES, and links the (large) asset folders.
Upstream is never edited: every divergence from it is visible in this file.

Usage: python tools/prepare_run.py [--out DIR] [--no-assets]
  --out DIR     prepare DIR instead of run/ (e.g. build/switch for the Switch build)
  --no-assets   do not link the asset folders (the Switch package copies them separately)
"""
import os
import re
import shutil
import subprocess
import sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "scpcb")
RUN = os.path.join(ROOT, "run")
LINK_ASSETS = True
ASSETS = ["Data", "GFX", "SFX", "Loadingscreens"]

# (file, regex, replacement, reason)
PATCHES = [
    (
        "Main.bb",
        r'If FileSize\("fmod\.dll"\)=0 Then InitErrorStr=InitErrorStr\+ "fmod\.dll"\+Chr\(13\)\+Chr\(10\)\r?\n'
        r'If FileSize\("zlibwapi\.dll"\)=0 Then InitErrorStr=InitErrorStr\+ "zlibwapi\.dll"\+Chr\(13\)\+Chr\(10\)\r?\n',
        "; fmod.dll / zlibwapi.dll checks removed: those libraries are built into the runtime\n",
        "the DLLs are replaced by the runtime's scpcb compat module",
    ),
    (
        "Menu.bb",
        r'Local f = OpenFile\(file\)',
        "Local f = ReadFile(file)",
        "the file is only read; Horizon refuses a second (read) open of a file held open for writing",
    ),
    (
        "Main.bb",
        r'((?:AALoadFont|LoadFont_Strict)\([^\r\n]*?)Int\((\d+) \* \(GraphicHeight / 1024\.0\)\)',
        r'\1Int(\2 * (GraphicHeight / 1024.0) * RT_SettingF("font_scale", 1.15))',
        "fonts are 15% larger by default (overlay menu setting font_scale): at 1280x720 on a handheld screen the stock sizes are too small to read",
    ),
    (
        "Menu.bb",
        r'((?:AALoadFont|LoadFont_Strict)\([^\r\n]*?)Int\((\d+) \* \(GraphicHeight / 1024\.0\)\)',
        r'\1Int(\2 * (GraphicHeight / 1024.0) * RT_SettingF("font_scale", 1.15))',
        "same, for the fonts reloaded from the options menu",
    ),
    (
        "AAText.bb",
        r'Global AATextEnable% = GetINIInt\(OptionFile, "options", "antialiased text"\)',
        'Global AATextEnable% = 0 ; antialiased text is off: building its font textures takes minutes on the Switch and toggling it crashed',
        "antialiased text is disabled on this port",
    ),
    (
        "Main.bb",
        r'AATextEnable% = DrawTick\(([^\r\n]*?), AATextEnable%\)',
        r'AATextEnable% = 0 : DrawTick(\1, 0)',
        "the options tick for antialiased text no longer changes anything",
    ),
    (
        "Menu.bb",
        r'AATextEnable% = DrawTick\(([^\r\n]*?), AATextEnable%\)',
        r'AATextEnable% = 0 : DrawTick(\1, 0)',
        "same, main menu options",
    ),
    (
        "Menu.bb",
        r'If MouseHit1 Then SelectedInputBox = ID : FlushKeys\r?\n',
        'If MouseHit1 Then SelectedInputBox = ID : FlushKeys : If RT_HasPrompt() Then Txt = RT_TextPrompt$("Enter text", Txt) : SelectedInputBox = 0 : FlushKeys : FlushMouse\n',
        "text boxes open the Switch on-screen keyboard (there is no physical keyboard)",
    ),
    (
        "Main.bb",
        r'(\r?\n)Repeat(\r?\n\t\r?\n\tCls\r?\n\t\r?\n\tCurTime = MilliSecs2\(\))',
        r'\1Include "Multiplayer.bb"\nInclude "Debug.bb"\nInclude "Pad.bb"\n\nRepeat\2',
        "co-op presence prototype (port/Multiplayer.bb)",
    ),
    (
        "Main.bb",
        r'(UpdateMainMenu\(\)\r?\n\tElse\r?\n)(\t\tUpdateStreamSounds\(\))',
        r'\1\t\tMP_Update()\n\t\tDBG_Update()\n\2',
        "multiplayer: once per frame while a game is running",
    ),
    (
        "Main.bb",
        r'\t\tDrawQuickLoading\(\)\r?\n',
        '\t\tMP_Draw()\n\t\tDrawQuickLoading()\n',
        "multiplayer: names above the other players",
    ),
    (
        "Main.bb",
        r'(Function NullGame\(playbuttonsfx%=True\)\r?\n)',
        r'\1\tMP_Reset()\n',
        "multiplayer: the figures are deleted with the world",
    ),
    (
        "Menu.bb",
        r'(\t\t\t\t\tIf RandomSeed = "" Then\r?\n\t\t\t\t\t\tRandomSeed = Abs\(MilliSecs\(\)\))',
        '\t\t\t\t\tIf RT_SettingI("mp_mode", 0) > 0 Then\n\t\t\t\t\t\tIf RT_SettingS$("mp_seed", "") <> "" Then RandomSeed = RT_SettingS$("mp_seed", "")\n\t\t\t\t\tEndIf\n' + r'\1',
        "multiplayer: every player uses the shared map seed from the overlay menu",
    ),
    (
        "Main.bb",
        r'(\n\t\ty = GraphicHeight / 2 - height\r?\n)',
        r'\1\t\tPadInvReport(MaxItemAmount, y)\n',
        "gamepad: slot centres of the player inventory (port/Pad.bb)",
    ),
    (
        "Main.bb",
        r'(\t\ty = GraphicHeight / 2 - \(height \* OtherSize /5 \+ spacing \* \(OtherSize / 5 - 1\)\) / 2;height\r?\n)',
        r'\1\t\tPadInvReport(OtherSize, y)\n',
        "gamepad: slot centres of the container inventory",
    ),
    (
        "Main.bb",
        r'Local file% = OpenFile\("Credits\.txt"\)',
        'Local file% = ReadFile("Credits.txt")',
        "read-only use, same reason",
    ),
]


def link_dir(src, dst):
    if os.path.lexists(dst):
        return
    if os.name == "nt":
        subprocess.check_call(
            ["powershell", "-NoProfile", "-Command",
             f'New-Item -ItemType Junction -Path "{dst}" -Target "{src}" | Out-Null'])
    else:
        os.symlink(src, dst)


def main():
    global RUN, LINK_ASSETS
    args = sys.argv[1:]
    if "--out" in args:
        RUN = os.path.abspath(args[args.index("--out") + 1])
    if "--no-assets" in args:
        LINK_ASSETS = False
    os.makedirs(RUN, exist_ok=True)
    for name in os.listdir(SRC):
        if name.endswith(".bb"):
            shutil.copyfile(os.path.join(SRC, name), os.path.join(RUN, name))
    # Settings are per-checkout: keep an existing run/options.ini.
    if not os.path.exists(os.path.join(RUN, "options.ini")):
        shutil.copyfile(os.path.join(SRC, "options.ini"), os.path.join(RUN, "options.ini"))
    if LINK_ASSETS:
        for d in ASSETS:
            link_dir(os.path.join(SRC, d), os.path.join(RUN, d))

    failed = False
    # files that belong to the port (not to upstream)
    for name in os.listdir(os.path.join(ROOT, "port")):
        if name.endswith(".bb"):
            shutil.copy2(os.path.join(ROOT, "port", name), os.path.join(RUN, name))

    for name, pattern, repl, why in PATCHES:
        path = os.path.join(RUN, name)
        with open(path, encoding="latin-1", newline="") as f:
            text = f.read()
        new, n = re.subn(pattern, repl, text)
        if n == 0:
            print(f"PATCH FAILED ({name}): {why}", file=sys.stderr)
            failed = True
            continue
        with open(path, "w", encoding="latin-1", newline="") as f:
            f.write(new)
        print(f"patched {name}: {why}")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
