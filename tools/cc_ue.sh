#!/bin/sh
# compile-check the UE build: prepare run_ue and run blitzcc -c
cd "$(dirname "$0")/.." || exit 1
python tools/prepare_ue.py --src ../scpcb-ue-22 --no-assets >/dev/null || { echo prepare failed; exit 1; }
cd run_ue
export blitzpath="$(pwd)/../blitz3d-ng/_release"
timeout 280 "$blitzpath/bin/blitzcc64.exe" -c Game.bb 2>&1 | tail -${1:-3}
