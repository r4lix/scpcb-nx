; Blitz3D-TSS commands that UE Reborn expects and that are plain Blitz here.

Function Max#(a#, b#)
	If a > b Then Return(a)
	Return(b)
End Function

Function Min#(a#, b#)
	If a < b Then Return(a)
	Return(b)
End Function

Function Clamp#(v#, lo#, hi#)
	If v < lo Then Return(lo)
	If v > hi Then Return(hi)
	Return(v)
End Function

Function PowTwo#(v#)
	Return(v * v)
End Function

Function DistanceSquared#(x1#, x2#, z1#, z2#)
	Return((x1 - x2) * (x1 - x2) + (z1 - z2) * (z1 - z2))
End Function

Function DistanceSquared3D#(x1#, x2#, y1#, y2#, z1#, z2#)
	Return((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2))
End Function

Function Distance#(x1#, x2#, z1#, z2#)
	Return(Sqr(DistanceSquared(x1, x2, z1, z2)))
End Function

Function IsNaN%(v#)
	Return(v <> v)
End Function

Function FileExtension$(f$)
	Local i%
	For i = Len(f) To 1 Step -1
		Local c$ = Mid(f, i, 1)
		If c = "." Then Return(Mid(f, i + 1))
		If c = "/" Or c = "\" Then Exit
	Next
	Return("")
End Function

Function ConvertToUTF8$(s$)
	Return(s)
End Function

Function ConvertToANSI$(s$)
	Return(s)
End Function

Function TotalPhys%()
	Return(3500000)
End Function

Function AvailPhys%()
	Return(1500000)
End Function

Function DesktopWidth%()
	Return(1280)
End Function

Function DesktopHeight%()
	Return(720)
End Function

Global TSS_Clipboard$ = ""

Function SetClipboardContents(s$)
	TSS_Clipboard = s
End Function

Function GetClipboardContents$()
	Return(TSS_Clipboard)
End Function

Function DownloadFile%(url$, dest$)
	Return(0)
End Function

Function Unzip%(f$, dest$)
	Return(0)
End Function

Function DeleteFolder%(d$)
	Local h% = ReadDir(d)
	If h = 0 Then Return(0)
	Local n$ = NextFile(h)
	While n <> ""
		If n <> "." And n <> ".."
			If FileType(d + "/" + n) = 2 Then DeleteFolder(d + "/" + n) Else DeleteFile(d + "/" + n)
		EndIf
		n = NextFile(h)
	Wend
	CloseDir(h)
	DeleteDir(d)
	Return(1)
End Function

Function EngineSetting%(k$, v$)
	Return(0)
End Function

Function GetUserLanguage$()
	Return("English")
End Function

Function MemoryAccessViolation%()
	RuntimeError("MemoryAccessViolation")
End Function

Function InitErrorMsgs%(n%, b%)
	Return(0)
End Function

Function SetErrorMsg%(n%, s$)
	Return(0)
End Function

Function BankPointer%(b%)
	Return(0)
End Function

; Small text is what is unreadable on a handheld; the big title fonts only need a little extra to stay inside their buttons.
Function FontScaleFor#(h%)
	If h < 30 Then Return(RT_SettingF("font_scale", 1.35))
	Return(1.15)
End Function

Function TextInputEx$(s$, v%)
	If v = 8
		If Len(s) > 0 Then Return(Left(s, Len(s) - 1))
		Return(s)
	EndIf
	If v >= 32 Then Return(s + Chr(v))
	Return(s)
End Function
