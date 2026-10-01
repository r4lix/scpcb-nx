Function Pick$(a$, n%)
	If n > 5 Then Return a + "x"
	Return Left(a, 3)
End Function

Function Loopy%(s$)
	Local i%
	Local t$
	For i = 1 To 20
		t = Mid(s, i, 3)
		If t = "abc" Then Return 1
	Next
	Return 0
End Function

Function Inner$(s$)
	Local r$ = ""
	r = r + Lower(s) + Trim(" " + s + " ")
	Return r
End Function

Function ReadIt$(f%)
	Local l$ = ReadLine(f)
	Return Trim(l)
End Function

mode = 1
For i = 1 To 400000
	Select mode
	Case 1
		a$ = Pick("hello world", i Mod 10)
	Case 2
		n = Loopy("the quick brown fox jumps")
	Case 3
		a$ = Inner("Some Text")
	Case 4
		If Pick("hello", 1) = "hel" Then n = n + 1
	End Select
Next
f = WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\strleak.txt")
WriteLine f, "done"
CloseFile f
End
