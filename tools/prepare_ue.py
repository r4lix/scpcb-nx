#!/usr/bin/env python3
"""Build the runnable directory for the UE Reborn 2.2 Beta build.

Copies the unmodified sources of Jabka666/scpcb-ue-my (UET_DEV @ 6970faff, the
2.2 Beta commit) into the output directory, applies PATCHES and links the
asset folders from the public 2.2 Beta build. Upstream is never edited.

Usage: python tools/prepare_ue.py [--out DIR] [--src DIR] [--assets DIR] [--no-assets]
"""
import os, re, shutil, subprocess, sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, "scpcb-ue")
ASSETS_SRC = os.path.join(ROOT, "SCP 2.2 Beta Public Build")
OUT = os.path.join(ROOT, "run_ue")
LINK_ASSETS = True
ASSETS = ["Data", "GFX", "SFX", "Localization"]

# (file, regex, replacement, reason)
PATCHES = [
    ("Source Code/Main_Core.bb", r"GetEntityLinearVelocity\(me\\Collider, &vX, 0, &vZ\)",
     r"vX = GetEntityVelX(me\Collider) : vZ = GetEntityVelZ(me\Collider)",
     "no pointers to locals on a 64-bit target: velocity read through two getters"),
    ("Source Code/Map_Core.bb", r"LinearToSRGB\(&R, &G, &B\)",
     "R = LinearToSRGBc(R) : G = LinearToSRGBc(G) : B = LinearToSRGBc(B)",
     "no pointers to locals on a 64-bit target"),
    ("Source Code/Math_Core.bb", r"Function LinearToSRGB%\(InR%, InG%, InB%\)",
     "Function LinearToSRGBc%(V%)\n\tLocal R# = V / 255.0\n\tLocal r_sq1# = Sqr(R)\n\tLocal r_sq2# = Sqr(r_sq1)\n\tLocal r_sq3# = Sqr(r_sq2)\n"
     "\tR = 0.662002687 * r_sq1 + 0.68412206 * r_sq2 - 0.323583601 * r_sq3 - 0.022541147 * R\n\tReturn(Int(Clamp(R * 255.0, 0.0, 255.0)))\nEnd Function\n\nFunction LinearToSRGB%(InR%, InG%, InB%)",
     "scalar version of LinearToSRGB"),
    ("Game.bb", r'(Include "Source Code\\KeyBinds_Core\.bb")', lambda m: 'Include "Compat.bb"' + chr(10) + m.group(1), "TSS builtins implemented in Blitz"),
    ("Source Code/Map_Core.bb", r"(Field [^\x0a\[]*\[)PowTwo\(([^\x0a\]]*?)\)( \+ \d+)?\]", lambda m: m.group(1) + "(" + m.group(2) + ")*(" + m.group(2) + ")" + (m.group(3) or "") + "]", "PowTwo in a Field array size must be a constant expression"),
    ("Source Code/Main_Core.bb", r'Include "Source Code\\Shaders_Core\.bb"', 'Include "Shaders_Core.bb"', "forward renderer: no HLSL effects"),
    ("Source Code/Main_Core.bb", r'Include "Source Code\\Deferred_Core\.bb"', 'Include "Deferred_Core.bb"', "forward renderer: see port_ue/Deferred_Core.bb"),
    ("*", r"\bCreateLight\(", "DLCreateLight(", "the game's CreateLight/LightRange/LightColor are deferred dynamic lights; the builtins keep their names"),
    ("*", r"\bLightRange\(", "DLLightRange(", "see CreateLight"),
    ("*", r"\bLightColor\(", "DLLightColor(", "see CreateLight"),
    ("Source Code/Graphics_Core.bb", r"Function GetDummyPivot%\(x#", "Function GetDummyPivotXYZ%(x#", "no function overloading"),
    ("Source Code/NPCs_Core.bb", r"n\\CurrentRoom = (r_gate_a|r_dimension_1499)\b", "False", "upstream compares a Rooms object with a room ID (always false); TSS tolerates it"),
    ("Game.bb", r'If FileSize\("[A-Za-z0-9_]+\.dll"\) = 0 Then InitErrorStr[^\x0a]*\x0a', "", "the DLLs are built into the runtime"),
    ("Game.bb", r'Include "Source Code\\Bass_Core\.bb"', 'Include "Bass_Core.bb"', "BASS is replaced by the engine's audio"),
    ("Game.bb", r'Global AppDataPath\$ = GetEnv\("AppData"\)', 'Global AppDataPath$ = "UserData"', "settings and saves live beside the game (no %AppData%)"),
    ("Source Code/Graphics_Core.bb", r"Graphics3D\(Width, Height, Depth, Mode\)", "Graphics3D(Width, Height, Depth, 2)", "the runtime knows window modes 0-3 only; fullscreen is the platform's business"),
    ("Source Code/Strict_Functions_Core.bb", r"(Function RuntimeErrorEx%\(Message\$\)\x0d?\x0a)", lambda m: m.group(1) + "\tRT_Trace(Message)" + chr(10), "log the error text (the TSS error screen is not available)"),
    ("*", r"(<> Null\)?) And ", lambda m: m.group(1) + " Land ", "TSS evaluates a Null guard before the dereference; make it a short-circuit"),
    ("Source Code/Menu_Core.bb", r"TextInput\(((?:[^()]|\([^()]*\))*)\)", lambda m: "TextInputEx(" + m.group(1) + ", Value)", "TextInput needs the typed character: passed explicitly (see port_ue/Compat.bb)"),
]


# Blitz3D-TSS overloads functions by argument count: (name, {argument count: implementation name})
OVERLOADS = [
    ("DistanceSquared", {2: "EntityDistanceSquared", 6: "DistanceSquared3D"}),
]


def split_args(s):
    depth, cur, out, q = 0, "", [], False
    for ch in s:
        if ch == '"':
            q = not q
        if not q:
            if ch in "([":
                depth += 1
            elif ch in ")]":
                depth -= 1
            elif ch == "," and depth == 0:
                out.append(cur); cur = ""; continue
        cur += ch
    if cur.strip():
        out.append(cur)
    return out


def rename_overloads(text, name, table):
    out, pos, n = [], 0, 0
    for m in re.finditer(r"(?<![\w\\.])" + name + r"\(", text, re.I):
        if m.start() < pos:
            continue
        i, depth, q = m.end(), 1, False
        while i < len(text) and depth:
            c = text[i]
            if c == '"':
                q = not q
            elif not q:
                depth += (c == "(") - (c == ")")
            i += 1
        argc = len(split_args(text[m.end():i - 1]))
        if argc in table:
            out.append(text[pos:m.start()]); out.append(table[argc] + "("); pos = m.end(); n += 1
    out.append(text[pos:])
    return "".join(out), n


def link_dir(src, dst):
    if os.path.lexists(dst):
        return
    if os.name == "nt":
        subprocess.check_call(["powershell", "-NoProfile", "-Command",
                               f'New-Item -ItemType Junction -Path "{dst}" -Target "{src}" | Out-Null'])
    else:
        os.symlink(src, dst)


def main():
    global OUT, SRC, ASSETS_SRC, LINK_ASSETS
    a = sys.argv[1:]
    if "--out" in a: OUT = os.path.abspath(a[a.index("--out") + 1])
    if "--src" in a: SRC = os.path.abspath(a[a.index("--src") + 1])
    if "--assets" in a: ASSETS_SRC = os.path.abspath(a[a.index("--assets") + 1])
    if "--no-assets" in a: LINK_ASSETS = False
    os.makedirs(os.path.join(OUT, "Source Code"), exist_ok=True)
    for sub in ("", "Source Code"):
        d = os.path.join(SRC, sub)
        for n in os.listdir(d):
            if n.endswith(".bb"):
                shutil.copyfile(os.path.join(d, n), os.path.join(OUT, sub, n))
    if LINK_ASSETS:
        for d in ASSETS:
            link_dir(os.path.join(ASSETS_SRC, d), os.path.join(OUT, d))
    ud = os.path.join(OUT, "UserData", "scpcb-ue", "Data")
    os.makedirs(ud, exist_ok=True)
    if not os.path.exists(os.path.join(ud, "options.ini")):
        shutil.copyfile(os.path.join(ROOT, "tools", "ue_options.ini"), os.path.join(ud, "options.ini"))
    port = os.path.join(ROOT, "port_ue")
    if os.path.isdir(port):
        for n in os.listdir(port):
            shutil.copy2(os.path.join(port, n), os.path.join(OUT, n))
    failed = False
    for oname, table in OVERLOADS:
        total = 0
        for t in ["Game.bb"] + ["Source Code/" + n for n in os.listdir(os.path.join(OUT, "Source Code")) if n.endswith(".bb")]:
            p = os.path.join(OUT, *t.split("/"))
            text = open(p, encoding="latin-1", newline="").read()
            new, n = rename_overloads(text, oname, table)
            if n:
                open(p, "w", encoding="latin-1", newline="").write(new); total += n
        print(f"overload {oname}: {total} calls renamed")
    for name, pat, repl, why in PATCHES:
        if name == "*":
            targets = ["Game.bb"] + ["Source Code/" + n for n in os.listdir(os.path.join(OUT, "Source Code")) if n.endswith(".bb")]
        else:
            targets = [name]
        total = 0
        for t in targets:
            p = os.path.join(OUT, *t.split("/"))
            text = open(p, encoding="latin-1", newline="").read()
            new, n = re.subn(pat, repl if callable(repl) else (lambda m: repl), text)
            if n:
                open(p, "w", encoding="latin-1", newline="").write(new)
                total += n
        if total == 0:
            print(f"PATCH FAILED ({name}): {why}", file=sys.stderr); failed = True; continue
        print(f"patched {name} x{total}: {why}")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
