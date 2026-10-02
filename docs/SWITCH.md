# SCP: Containment Breach on Nintendo Switch (test build)

This is an early port: the game is the unmodified SCP:CB 1.3.11 source, compiled with a
modified [blitz3d-ng](https://github.com/blitz3d-ng/blitz3d-ng) for the Switch.
It runs on hardware (Switch Lite tested) and in the Eden emulator; expect rough edges.

## Install

**From the release (easiest).** Download `scpcb-switch.zip` from the
[pre-release](https://github.com/r4lix/scpcb-nx/releases) and extract it to the **root of the SD
card**. You end up with `sdmc:/switch/scpcb/scpcb.nro`, `options.ini` and the game's `GFX`,
`SFX`, `Data` and `Loadingscreens` folders (about 300 MB). Extract on a PC if you can; zips with
backslash paths (e.g. made by PowerShell's `Compress-Archive`) fail on the Switch with
`FsError_InvalidCharacter`.

**To update only the program** replace `sdmc:/switch/scpcb/scpcb.nro` with the standalone
`scpcb.nro` from the release (about 9 MB); the assets do not change.

**Building it yourself (Windows):**

```
powershell -File tools\build_switch.ps1 -Sd E:      # E: = your SD card
```

`-Sd` takes any folder, not just a drive: `-Sd C:\some\folder` stages
`C:\some\folder\switch\scpcb\`, which you can copy to the SD card's root afterwards.
Without `-Sd` it only builds `build\switch\scpcb.nro`; copy it, `tools\switch_options.ini`
(as `options.ini`) and the four asset folders from `scpcb\` yourself.

Start it from hbmenu.

**Use title override (hold R while launching a game, then pick hbmenu).** In applet mode
(launching from the Album) homebrew only gets a few hundred MB, and the game needs about
1.2 GB once a level is loaded. If it quits straight away that is the first thing to check.

## Controls

| Switch | Does |
|---|---|
| Left stick | Walk (W A S D) |
| Right stick | Look / move the menu pointer |
| ZR or A | Click / interact |
| ZL | Right click |
| Y or R | Blink (Space) |
| L or left-stick click | Sprint (Shift) |
| B | Crouch (Ctrl) |
| X | Inventory (Tab) |
| + | Pause / back (Esc) |
| − | Quick save (F5) |
| D-pad | Arrow keys |
| Touchscreen | Mouse (menus) |

A software cursor is drawn in menus because the Switch has none.

## Settings menu (overlay)

**Hold `-` for about a second** (a short press is still the quick save) to open the settings
overlay, over a frozen copy of the game: the game, its clock and its audio are paused while it
is open. Navigate with the d-pad (up/down to pick a row, left/right to change it, A to
toggle), L/R switch tabs, B or + close it; the touchscreen works too. Settings are saved to
`switch_settings.ini` when it closes.

| Tab | Settings |
|---|---|
| Controls | pointer/look speed, stick dead zone and response curve, the shortcut that opens the menu (hold `-`, or hold L3+R3) |
| Display | render resolution, texture size limit, text size (these three apply after a restart), FPS counter |
| Audio | master volume |
| System | resume, reset all settings, quit game, GPU/memory info |

On a PC keyboard the menu opens with F10 (arrows, Enter, Esc, Q/E to navigate).
"Press any key" screens accept Y, R or A.

## When something goes wrong

The game writes **`sdmc:/switch/scpcb/scpcb.log`** and flushes it on every line. Send it back.
What the lines mean:

- `GL Version:` should mention OpenGL ES 3.x; `GL window: 1280x720` is the render size.
- `[flip N]` appears for the first frames and every 300th, so the log shows how far it got.
- `[perf]` every 10 s: FPS, worst frame, heap in MB. `[slow]` = a frame over 120 ms.
  `[slowop]` = a load or sound call over 25 ms, with the file name.
- `[open failed]` a file could not be opened (the reason is printed).
- `[bbEx]` a Blitz runtime error (the message follows). `[fatal]` an uncaught error.
- `[crash]` a hard crash with registers and a backtrace (addresses are relative to `bbStart`).
- `[texture failed] 'GFX/map/'` with an empty name is normal; the map data lists unused slots.

An optional `env.txt` next to the NRO (`KEY=VALUE` per line) sets debug variables, e.g.
`BB_SCREENSHOT_EVERY_MS=20000` with `BB_SCREENSHOT_PATH=sdmc:/switch/scpcb/shot` saves BMP
screenshots, and `BB_INJECT="70000:key:40;80000:move:250,224;80500:click:1"` presses keys and
clicks at given milliseconds after start (used to drive the emulator without a controller).

## Testing without a console

The Eden emulator runs the NRO. Put the same `switch/scpcb` folder in Eden's `sdmc` directory
(`%APPDATA%\eden\sdmc` on Windows) and start `eden-cli -g path\to\scpcb.nro`. Eden does not
enforce some Horizon file-system rules, so a pass there is not a guarantee on hardware.

## Known limits

- First load takes a while (it decodes ~400 textures from the SD card).
- Textures are capped at 512 px to fit in memory (`BB_TEXTURE_MAX` overrides on PC).
- Startup videos are skipped; the bump-map layer on floors is ignored.
- Text boxes (e.g. the new-game name) open the Switch on-screen keyboard when clicked. The right stick moves the pointer (BB_PAD_SPEED in env.txt changes its speed). Antialiased text is disabled.
- Some characters/props are loaded when they spawn, which can cause a short hitch.
- Online / multiplayer is not part of this build.
