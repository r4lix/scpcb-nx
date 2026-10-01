Function NoReturn%(a%)
	If a > 5 Then Return 7
End Function
Function NoReturnF#(a%)
	If a > 5 Then Return 1.5
End Function
Function NoReturnS$(a%)
	If a > 5 Then Return "x"
End Function
Type T
	Field v%
End Type
Function NoReturnO.T(a%)
	If a > 5 Then Return New T
End Function
f=WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\fall.txt")
WriteLine f,"a=" + NoReturn(1)
WriteLine f,"b=" + NoReturnF(1)
WriteLine f,"c=[" + NoReturnS(1) + "]"
WriteLine f,"d=" + (NoReturnO(1) = Null)
WriteLine f,"done"
CloseFile f
End
