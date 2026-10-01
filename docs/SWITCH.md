# SCP: Containment Breach on Nintendo Switch (test build)

This is an early port: the game is the unmodified SCP:CB 1.3.11 source, compiled with a
modified [blitz3d-ng](https://github.com/blitz3d-ng/blitz3d-ng) for the Switch.
It has **never been run on hardware before**, so expect to send logs back.

## Install

1. Build the package (Windows):

   ```
   powershell -File tools\build_switch.ps1 -Sd E:      # E: = your SD card
   ```

   This creates `E:\switch\scpcb\` containing `scpcb.nro`, `options.ini` and the game's
   `GFX`, `SFX`, `Data` and `Loadingscreens` folders (about 300 MB).
   Without `-Sd` it only builds `build\switch\scpcb.nro`; copy it, `tools\switch_options.ini`
   (as `options.ini`) and the four asset folders from `scpcb\` yourself.

2. Start it from hbmenu.

**Use title override (hold R while launching a game, then pick hbmenu).** In applet mode
(launching from the Album) homebrew only gets a few hundred MB, and the game needs about
1.1 GB once a level is loaded. If it quits straight away that is the first thing to check.

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
"Press any key" screens accept Y, R or A.

## When something goes wrong

The game writes **`sdmc:/switch/scpcb/scpcb.log`** and flushes it on every line. Send it back.
It records the GL version, window sizes, controller name and any runtime error. The game's
own crash log (if any) is `error_log_*.txt` in the same folder.

Useful things to check in it:

- `GL Version:` should mention OpenGL ES 3.x. If the line is missing the GL context failed.
- `gamepad:` shows the controller that was detected.
- `[fatal] std::terminate` means an uncaught runtime error (the Blitz error text, if any,
  is printed just above it).

## Known limits

- First load takes a while (it decodes ~400 textures from the SD card).
- Textures are capped at 512 px to fit in memory (`BB_TEXTURE_MAX` overrides on PC).
- Startup videos are skipped; the bump-map layer on floors is ignored.
- On-screen keyboard is not implemented, so the new-game name stays empty.
- Online / multiplayer is not part of this build.
