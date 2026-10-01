#!/usr/bin/env python3
"""Debug aid: add RT_Trace before every simple statement of one function in
run/<file>.bb, to find the exact line a crash happens on.
Usage: python tools/trace_function.py Main.bb MouseLook"""
import os
import re
import sys

root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
fname, func = sys.argv[1], sys.argv[2]
path = os.path.join(root, "run", fname)
with open(path, encoding="latin-1", newline="") as f:
    text = f.read()
nl = "\r\n" if "\r\n" in text else "\n"
lines = text.split(nl)
start = next(i for i, l in enumerate(lines) if re.match(r"Function\s+" + func + r"\b", l, re.I))
end = next(i for i in range(start, len(lines)) if lines[i].strip().lower() == "end function")
# statements that are safe to put a trace line before (never Else/EndIf/Next/etc.)
simple = re.compile(r"^\t+(Local|[A-Za-z_]\w*\s*[=(]|PositionEntity|RotateEntity|MoveEntity|TurnEntity|"
                    r"HideEntity|ShowEntity|If\s)", re.I)
block_words = re.compile(r"^\t+(Else|ElseIf|EndIf|End If|Next|Wend|Until|Forever|Case|Default|End Select)\b", re.I)
out = []
n = 0
for i, l in enumerate(lines):
    if start < i < end and simple.match(l) and not block_words.match(l) and not l.rstrip().endswith("_"):
        n += 1
        label = l.strip().replace('"', "").replace("\\", "")[:50]
        out.append('\tRT_Trace("L%d: %s")' % (n, label))
    out.append(l)
with open(path, "w", encoding="latin-1", newline="") as f:
    f.write(nl.join(out))
print("inserted", n, "traces in", func)
