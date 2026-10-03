# Assets the upstream checkout is missing

Copied over the game's asset folders when the Switch package is built (`tools/build_switch.ps1 -Sd ...`).

- `GFX/map/label1123.png` - the SCP-1123 containment room refers to this label, but it was removed by
  accident in upstream's merge 0cd3fdd and is absent from the checked out commit; without it the sign in that
  room renders plain white. Taken from upstream's `dev` branch (commit ec3f0b2, "Update 1123 label to green
  version"). Same licence as the rest of the game (CC BY-SA 3.0).
