# Installable NSP

`tools\make_nsp.ps1` turns the game into a title you install from Cyberfoil / Tinfoil / DBI and start
from the home menu, with no files copied to the SD card.

```
powershell -File tools\make_nsp.ps1 -Keys "C:\path\to\prod.keys"
-> build\nsp\nsp\SCP Containment Breach [01005CB000010000][v0].nsp
```

## What is in it

| part | contents |
|---|---|
| exefs | the game ELF as an NSO + an NPDM (`npdm.template.json`, title id `0100 5CB0 0001 0000`) |
| romfs | `Data`, `GFX`, `SFX`, `Loadingscreens` and the stock `options.ini` (read-only) |
| control | name, icon (`icon.jpg`, 256x256) and a 32 MB **device save data** declaration |

`mknsp.py` is generic: any homebrew ELF + romfs directory works, only `--titleid`, `--name` and
`--icon` change. Each game needs its own title id.

## How the game finds its files (`overlay.nx.cpp`)

Started as an NRO nothing changes: files sit next to `scpcb.nro` on the SD card.

Started as an installed title the runtime sees a romfs and builds one device, `ov:`, as the working
directory:

* **read**: save data first, then the romfs, so an `options.ini` the game saved wins over the stock one
* **write**: save data only (`Saves/`, `options.ini`); missing directories are created on demand
* **list**: the union of both
* every write-closed file, rename, delete and mkdir is followed by `fsdevCommitDevice`, otherwise
  save data changes are lost; the overlay also commits at exit

If the save data cannot be mounted the writable layer falls back to `sdmc:/switch/scpcb/`.
`scpcb.log` is only written when `sdmc:/switch/scpcb/` exists (create the folder to get a log).

## Uninstall

Behaves like a retail game: removing the software deletes the installed content and leaves the save
data, which is only removed from *System Settings > Data Management*. Nothing is written to the SD
card, so there is nothing to clean up there.

## Requirements and limits

* The console needs Atmosphere with **signature patches**: the NCAs are not signed by Nintendo.
* hacBrewPack has no version option, so every build is `v0`. Installing a rebuilt NSP over an
  existing install may need the old one removed first (Cyberfoil's "replace" option, or delete it in
  Data Management, which keeps the save).
* Needs `prod.keys` with `header_key` and `key_area_key_application_*`. Keep it outside the repo.
* `bin\hacbrewpack.exe` is hacBrewPack 3.05 (GPLv2, licence next to it) built from the
  `dragonflylee/hacBrewPack` fork, because the upstream `The-4n/hacBrewPack` repository is gone. It
  was built with devkitPro's MSYS2 `gcc` and needs the two DLLs next to it; `mknsp.py` runs it from
  MSYS so they are found.

# Packing another project (checklist)

1. **Prerequisites**: devkitPro (`elf2nso`, `npdmtool`, `nacptool` in `tools\bin`), your `prod.keys`
   (outside the repo), and Atmosphere sigpatches on the target console. `tools\nsp\bin` already holds
   a working hacBrewPack; to rebuild it: `pacman -S gcc` in devkitPro's MSYS2, clone
   `dragonflylee/hacBrewPack`, `cp config.mk.template config.mk`, `make`, and copy
   `hacbrewpack.exe` + `msys-2.0.dll` + `msys-gcc_s-seh-1.dll` into `tools\nsp\bin`.
2. **Get the linked ELF** the NRO is made from. devkitPro Makefiles leave `<name>.elf` next to the
   NRO. Blitz3D-NG deletes it after packaging: set `BB_KEEP_ELF=<path>` (a path *different* from the
   compiler's own `<out>.elf`, or it crashes copying the file onto itself).
3. **Title id**: pick a new one, `0x0100....` to `0x01ff....` with the low 12 bits zero. Never reuse
   one, installing over another game's id replaces it.

   | project | title id |
   |---|---|
   | scpcb-nx | `0x01005CB000010000` |

   Next free by the same pattern: `0x01005CB000020000`, `...30000`, ...
4. **Icon**: 256x256 JPEG with no EXIF (re-encode a PNG through System.Drawing; exif makes the home
   menu show a placeholder). Use `Bitmap.Save` with an `EncoderParameters(1)` object, a wrong call
   leaves a 0-byte file.
5. **Decide what is read-only and what is written.** Read-only game data goes in the romfs
   directory. Anything the program writes (saves, config, logs) must NOT be in the romfs.
   * Program opens everything relative to its working directory (blitz3d-ng, many ports):
     copy `overlay.nx.cpp/.h` and call `nxOverlayInit( sdFallbackDir )` before the first file access.
   * Program uses hard-coded `sdmc:/...` paths: change the writable ones to the `save:` mount
     (`fsdevMountDeviceSaveData`, then `fsdevCommitDevice` after writes) and the read-only ones to
     `romfs:/`.
   * Need 256 MB+ of RAM, JIT or other heavy services: the NPDM template already allows all services
     and the code-memory SVCs (0x4B/0x4C); check `main_thread_stack_size` for deep recursion.
6. **Run the packer**:
   ```
   python3 tools/nsp/mknsp.py --elf X.elf --romfs <dir> --icon icon.jpg --keys <prod.keys> \
       --out build/nsp --titleid 0x... --name "Game Name" --author r4lix --version 1.0.0
   ```
   Large romfs directories do not need copying: stage them as directory junctions
   (`New-Item -ItemType Junction`), see `tools\make_nsp.ps1`.
7. **Check the result** with `hactool -k prod.keys -t nca <nca> 2>/dev/null` and look for the right
   `Title ID` and `Content Type` (Meta, Control, Program). **Always redirect hactool's stderr and do
   not grep for "Fail"**: its "Failed to match key" warnings print the values from `prod.keys`.
8. **Serve and install** through Cyberfoil, then test on the console. The first install proves the
   NPDM (services, SVCs), the save mount and the overlay; none of that can be checked off-device.

## Pitfalls found on hardware (scpcb, 2026-10-05)

* **`GetDeviceOpTab("romfs")` returns the default device, not "not mounted".** The name needs the
  colon (`"romfs:"`). Without it the overlay believed romfs was mounted, never called `romfsInit()`
  and silently fell back to the empty SD folder.
* **A devoptab that forwards to another one must set `r->deviceData`** to the target's `deviceData`
  for the duration of the call (the C library does it only for the device it dispatches to).
  libnx's romfs and fsdev read their mount from it; forgetting this is a null dereference at
  `far=0x190` inside `romfs_open`/`navigateToDir`. `overlay.nx.cpp` does it in the `CALL` macro.
* **A compiled Blitz program cannot throw out of itself on the Switch.** `End`/`Stop`/"close
  software" are `throw bbEx(0)`; with no unwind info through the generated frames it ends in
  `std::terminate`, which the OS reports as "closed because an error occurred". `bbEx`'s constructor
  now calls `bbNxEndHook` (set in `bbStart`) which flushes, commits the save and `_exit(0)`s.
* Debugging aids that paid off: log to the SD root (`sdmc:/scpcb.log`, works before any folder
  exists), print the overlay's mount status first, resolve crash addresses against the exact ELF
  that went into the NSP (`BB_KEEP_ELF`), and read the log over `sys-ftpd` (a log can read as 0 bytes
  for a few seconds after the game exits; retry).

Known limits: every NSP is `v0` (hacBrewPack cannot set a version), and the overlay only covers
POSIX/stdio file access, not code that talks to `fs` services directly.
