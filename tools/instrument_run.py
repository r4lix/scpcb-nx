#!/usr/bin/env python3
"""Debug aid: add RT_Trace("F:<name>") to every function in run/*.bb so a hang or
crash can be located (the last [t] line on stderr is the function being run).
Undo with `python tools/prepare_run.py`."""
import glob
import os
import re

root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
# hot helpers that would drown the trace
SKIP = {
    "ReadINILine", "GetINIString", "GetINIString2", "GetINIInt", "GetINIInt2", "GetINIFloat",
    "IniGetBufferString", "IniGetString", "StripPath", "Piece", "Min", "Max", "Distance",
    "CurveValue", "CurveAngle", "AAText", "AAStringWidth", "AAStringHeight", "AASetFont",
    "GetTextureFromCache", "GetBumpFromCache", "GetCache", "CheckRoomOverlap", "Chr", "Rand2",
    "MilliSecs2", "FloatToString", "Float2", "UpdateStreamSoundOrigin", "IsStreamPlaying_Strict",
    "ReadINIString", "ReadINIInt",
}
total = 0
for path in glob.glob(os.path.join(root, "run", "*.bb")):
    name = os.path.basename(path)
    if name.startswith(("Converter", "RMesh_Model", "LightMapPNG", "Blitz_File_ZipApi")) or "Kopio" in name:
        continue
    with open(path, encoding="latin-1", newline="") as f:
        text = f.read()
    nl = "\r\n" if "\r\n" in text else "\n"

    def inst(m):
        global total
        if m.group(1) in SKIP:
            return m.group(0)
        total += 1
        return m.group(0) + nl + '\tRT_Trace("F:' + m.group(1) + '")'

    new = re.sub(r"^Function\s+(\w+)[^\r\n]*", inst, text, flags=re.M | re.I)
    with open(path, "w", encoding="latin-1", newline="") as f:
        f.write(new)
print("instrumented", total, "functions")
