#!/usr/bin/env python3
"""Debug aid: add RT_Trace("F:<name>") to every function in run_ue/ so a crash can be located
(the last [t] line on stderr is the function being run). Undo with `python tools/prepare_ue.py --src ../scpcb-ue-22`."""
import glob
import os
import re

root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SKIP = {
    "IniGetBufferString", "IniGetString", "IniGetInt", "IniGetFloat", "GetLocalString", "GetFileLocalString",
    "Min", "Max", "Clamp", "Distance", "DistanceSquared", "PowTwo", "CurveValue", "CurveAngle", "Format",
    "StripPath", "Piece", "Chr", "Lerp", "IsEqual", "Float2", "TextEx", "AAText", "GetTextureFromCache",
    "ChrCanDisplay", "GetDummyPivot", "MilliSec2", "FileExtension", "Format",
}
total = 0
files = glob.glob(os.path.join(root, "run_ue", "*.bb")) + glob.glob(os.path.join(root, "run_ue", "Source Code", "*.bb"))
for path in files:
    name = os.path.basename(path)
    if name in ("Compat.bb", "Bass_Core.bb", "Shaders_Core.bb") or name.startswith("RMesh_Model") or name == "IniController.bb":
        continue
    text = open(path, encoding="latin-1", newline="").read()
    nl = "\r\n" if "\r\n" in text else "\n"

    def inst(m):
        global total
        if m.group(1) in SKIP:
            return m.group(0)
        total += 1
        return m.group(0) + nl + '\tRT_Trace("F:' + m.group(1) + '")'

    new = re.sub(r"^Function\s+(\w+)[^\r\n]*", inst, text, flags=re.M | re.I)
    open(path, "w", encoding="latin-1", newline="").write(new)
print("instrumented", total, "functions")
