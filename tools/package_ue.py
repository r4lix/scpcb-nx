#!/usr/bin/env python3
"""Pack the UE Reborn 2.2 Beta Switch build as an SD-card zip (extract at the card root).

  python tools/package_ue.py [--out build/scpcb-ue-switch.zip]

Layout: switch/scpcb-ue/{scpcb-ue.nro, Data, GFX, SFX, Localization, UserData/...}.
Entries use forward slashes (Compress-Archive writes backslashes, which break FAT)."""
import os
import sys
import zipfile

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
NRO = os.path.join(ROOT, "build", "switch_ue", "scpcb-ue.nro")
GAME = os.path.join(ROOT, "SCP 2.2 Beta Public Build")
OUT = os.path.join(ROOT, "build", "scpcb-ue-switch.zip")
if "--out" in sys.argv:
    OUT = os.path.abspath(sys.argv[sys.argv.index("--out") + 1])

base = "switch/scpcb-ue/"
with zipfile.ZipFile(OUT, "w", zipfile.ZIP_DEFLATED, compresslevel=1) as z:
    z.write(NRO, base + "scpcb-ue.nro")
    z.write(os.path.join(ROOT, "tools", "ue_options.ini"), base + "UserData/scpcb-ue/Data/options.ini")
    for d in ("Data", "GFX", "SFX", "Localization"):
        for dp, dn, fn in os.walk(os.path.join(GAME, d)):
            for f in fn:
                if f.lower().endswith(".exe"):
                    continue
                full = os.path.join(dp, f)
                z.write(full, base + os.path.relpath(full, GAME).replace(os.sep, "/"))
print(OUT, round(os.path.getsize(OUT) / 1048576), "MB")
