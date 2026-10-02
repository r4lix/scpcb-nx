Function chk(name$, got$, want$)
	If got = want Then
		RT_Trace("ok   " + name)
	Else
		RT_Trace("FAIL " + name + " got=[" + got + "] want=[" + want + "]")
	EndIf
End Function

Type Node
	Field v%
	Field name$
	Field f#
End Type

; banks
b = CreateBank(16)
chk("bsize", Str(BankSize(b)), "16")
PokeInt b, 0, 123456789
PokeShort b, 4, 4660
PokeByte b, 6, 200
PokeFloat b, 8, 1.5
chk("peekint", Str(PeekInt(b, 0)), "123456789")
chk("peekshort", Str(PeekShort(b, 4)), "4660")
chk("peekbyte", Str(PeekByte(b, 6)), "200")
chk("peekfloat", Str(PeekFloat(b, 8)), "1.5")
PokeInt b, 12, -5 : chk("peekint-neg", Str(PeekInt(b, 12)), "-5")
ResizeBank b, 32
chk("resized", Str(BankSize(b)), "32")
chk("keptdata", Str(PeekInt(b, 0)), "123456789")
b2 = CreateBank(16)
CopyBank b, 0, b2, 0, 16
chk("copybank", Str(PeekInt(b2, 0)), "123456789")
FreeBank b2
FreeBank b

; types
For i = 1 To 5
	n.Node = New Node
	n\v = i
	n\name = "n" + i
	n\f = i * 0.5
Next
c = 0
For n.Node = Each Node
	c = c + 1
Next
chk("each-count", Str(c), "5")
f.Node = First Node
l.Node = Last Node
chk("first", Str(f\v), "1")
chk("last", Str(l\v), "5")
t1.Node = After f
chk("after", Str(t1\v), "2")
t1 = Before l
chk("before", Str(t1\v), "4")
Delete f
t1 = First Node
chk("firstafterdel", Str(t1\v), "2")
t1 = Last Node
chk("name", t1\name, "n5")
chk("float", Str(t1\f), "2.5")
Delete Each Node
chk("alldeleted", Str(First Node = Null), "1")

; arrays
Dim a(10)
For i = 0 To 10
	a(i) = i * i
Next
chk("dim1", Str(a(7)), "49")
Dim m#(3, 4)
m(2, 3) = 2.5
chk("dim2", Str(m(2, 3)), "2.5")
Dim s$(5)
s(2) = "hi"
chk("dims", s(2), "hi")
chk("dimdef", s(3), "")

; select / case
x = 3
Select x
	Case 1
		r$ = "one"
	Case 2, 3
		r$ = "two-three"
	Default
		r$ = "other"
End Select
chk("select", r, "two-three")

; data
Restore vals
Read q1, q2$, q3#
chk("data1", Str(q1), "42")
chk("data2", q2, "text")
chk("data3", Str(q3), "3.25")
.vals
Data 42, "text", 3.25

; gosub / goto
cnt = 0
Gosub incr
Gosub incr
chk("gosub", Str(cnt), "2")
Goto skip
cnt = 100
.skip
chk("goto", Str(cnt), "2")

; for step / exit / repeat
t = 0
For i = 10 To 1 Step -3
	t = t + i
Next
chk("forstep", Str(t), "22")
t = 0
Repeat
	t = t + 1
	If t = 7 Then Exit
Forever
chk("repeat", Str(t), "7")
t = 0
While t < 5
	t = t + 2
Wend
chk("while", Str(t), "6")
For fi# = 0 To 1 Step 0.25
	t = t + 1
Next
chk("floatfor", Str(t), "11")

; files
fn$ = "t4test.tmp"
f1 = WriteFile(fn)
WriteLine f1, "line one"
WriteLine f1, "line two"
WriteInt f1, 99
WriteFloat f1, 2.5
WriteString f1, "str"
WriteShort f1, 77
WriteByte f1, 5
CloseFile f1
chk("filesize", Str(FileSize(fn)), "38")
chk("filetype", Str(FileType(fn)), "1")
f1 = ReadFile(fn)
chk("readline1", ReadLine(f1), "line one")
chk("readline2", ReadLine(f1), "line two")
chk("readint", Str(ReadInt(f1)), "99")
chk("readfloat", Str(ReadFloat(f1)), "2.5")
chk("readstring", ReadString(f1), "str")
chk("readshort", Str(ReadShort(f1)), "77")
chk("readbyte", Str(ReadByte(f1)), "5")
chk("eof", Str(Eof(f1)), "1")
SeekFile f1, 0
chk("seek", ReadLine(f1), "line one")
chk("filepos", Str(FilePos(f1)), "10")
CloseFile f1
f1 = OpenFile(fn)
SeekFile f1, FileSize(fn)
WriteByte f1, 9
CloseFile f1
chk("append", Str(FileSize(fn)), "39")
CopyFile fn, "t4copy.tmp"
chk("copyfile", Str(FileSize("t4copy.tmp")), "39")
DeleteFile fn
DeleteFile "t4copy.tmp"
chk("deleted", Str(FileType(fn)), "0")
CreateDir "t4dir"
chk("createdir", Str(FileType("t4dir")), "2")
f1 = WriteFile("t4dir/a.txt") : CloseFile f1
f1 = WriteFile("t4dir/b.txt") : CloseFile f1
d = ReadDir("t4dir")
cnt = 0
Repeat
	e$ = NextFile(d)
	If e = "" Then Exit
	If e <> "." And e <> ".." Then cnt = cnt + 1
Forever
CloseDir d
chk("readdir", Str(cnt), "2")
DeleteFile "t4dir/a.txt"
DeleteFile "t4dir/b.txt"
DeleteDir "t4dir"
chk("deletedir", Str(FileType("t4dir")), "0")

; math/random
SeedRnd 5
ok = 1
For i = 1 To 200
	rr# = Rnd(2, 5)
	If rr < 2 Or rr > 5 Then ok = 0
	k = Rand(3, 6)
	If k < 3 Or k > 6 Then ok = 0
Next
chk("rnd-range", Str(ok), "1")
SeedRnd 5 : a1# = Rnd(0, 1) : SeedRnd 5 : a2# = Rnd(0, 1)
chk("rnd-seed", Str(a1 = a2), "1")
ms = MilliSecs()
Delay 50
chk("delay", Str((MilliSecs() - ms) >= 45), "1")
chk("currentdate", Str(Len(CurrentDate()) > 5), "1")
chk("currenttime", Str(Len(CurrentTime())), "8")

; string / handle
n.Node = New Node
n\v = 77
h = Handle(n)
t1 = Object.Node(h)
chk("handle", Str(t1\v), "77")

RT_Trace("done")
End

.incr
cnt = cnt + 1
Return
