#!/usr/bin/env python3
"""Generate tests/meshload.bb: loads every .x/.b3d model under scpcb/GFX and
writes ok/FAIL per file to tests/meshes.txt (a quick check of the model loaders)."""
import glob
import os
import re


def win(p):
    p = p.replace("/", "\\")
    # MSYS Python reports C:\ as \c\
    return re.sub(r"^\\([a-zA-Z])\\", lambda m: m.group(1).upper() + ":\\", p)


root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
files = sorted(f for f in glob.glob(os.path.join(root, "scpcb", "GFX", "**", "*"), recursive=True)
               if f.lower().endswith((".x", ".b3d")))
out = win(os.path.join(root, "tests", "meshes.txt"))
lines = ["Graphics3D 640,480,32,2", "SetBuffer BackBuffer()",
         'f=WriteFile("%s")' % out, "Local m%"]
for p in files:
    w = win(p)
    rel = os.path.relpath(p, os.path.join(root, "scpcb")).replace("/", "\\")
    lines.append('m=LoadMesh("%s")' % w)
    lines.append('If m=0 Then WriteLine f,"FAIL %s" Else WriteLine f,"ok   %s"' % (rel, rel))
    lines.append("If m<>0 Then FreeEntity m")
lines += ["CloseFile f", "End"]
with open(os.path.join(root, "tests", "meshload.bb"), "w") as fh:
    fh.write("\n".join(lines) + "\n")
print(len(files), "models")
