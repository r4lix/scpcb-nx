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

Button names are the ones printed on the console.

**Playing**

| Button | Does |
|---|---|
| Left stick | Walk |
| Right stick | Look around |
| ZR or A | Interact / click |
| ZL | Right click |
| L or left-stick click | Sprint |
| R or Y | Blink |
| B | Crouch |
| X | Open / close the inventory |
| + | Pause menu |
| - (short press) | Quick save |
| - (hold about a second) | Settings overlay (see below) |

**Menus** (main menu, pause menu, options, load game, keypads...): the **left stick moves the cursor**
(the right stick does too), and the **d-pad jumps to the next button**, which is the easiest way to
aim; **A** selects, **B** goes back. To change a slider, move onto it, hold **A** and press left/right on
the d-pad (or drag with the stick). The touchscreen works too. A green frame shows where the gamepad is,
and the bottom of the screen lists the buttons for the current screen (turn the hints off in the settings
overlay, Controls tab).

**Inventory and containers** (clipboard, wallet...): the left stick moves the cursor and the d-pad jumps between slots.

| Button | Does |
|---|---|
| A | Use / open the item under the cursor (a single press: equips it, reads a document, ...) |
| Y | Pick the item up; move to another slot; press Y again to drop it there |
| X or B | Close the inventory |

Reading a document: press **A** on it once. Press **A** again to put it away.

A software cursor is drawn in menus because the Switch has none. "Press any key" screens accept A.

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
| Multiplayer | mode (off / host / join), player name, shared map seed, host address, port |
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

## Multiplayer (early prototype)

Up to four players on the same network. Everybody runs the full game locally; players who start a
**new game with the same map seed and difficulty** get the same level, so the only thing exchanged is
where each player is: the others appear as class-D figures with a name tag walking through your copy of
the level. Doors, items and monsters are **not** synchronised yet.

1. Open the overlay menu, Multiplayer tab. Everyone enters the same *Map seed*.
2. One player sets *Mode: Host* (the tab shows the console's address and the port).
3. The others set *Mode: Join*, *Host address* to that address, then start a new game.

Source: `port/Multiplayer.bb` (UDP, host relays states 20 times a second), wired into the game by
`tools/prepare_run.py`. `tools/mp_test.ps1` runs a host and a guest on one PC for testing.
