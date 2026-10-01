# scpcb-nx

SCP: Containment Breach on Nintendo Switch, built on [blitz3d-ng](https://github.com/blitz3d-ng/blitz3d-ng).

- `scpcb/` upstream game source (submodule, untouched)
- `blitz3d-ng/` compiler + runtime (submodule; our patches live on branch `scpcb-nx`)
- `tools/` Windows build helpers; run from inside `blitz3d-ng/` with LLVM unpacked in `blitz3d-ng/llvm/`
