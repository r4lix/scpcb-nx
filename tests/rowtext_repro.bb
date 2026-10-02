Global GraphicWidth = 1280, GraphicHeight = 720
Function AAStringWidth(s$)
	Return Len(s) * 8
End Function
Function AAStringHeight(s$)
	Return 15
End Function
Function AAText(x, y, s$)
	RT_Trace("L " + y + " [" + s + "]")
End Function
Function RowText(A$, X, Y, W, H, align% = 0, Leading#=1)
	;Display A$ starting at X,Y - no wider than W And no taller than H (all in pixels).
	;Leading is optional extra vertical spacing in pixels
	
	If H<1 Then H=2048
	
	Local LinesShown = 0
	Local Height = AAStringHeight(A$) + Leading
	Local b$
	
	While Len(A) > 0
		Local space = Instr(A$, " ")
		If space = 0 Then space = Len(A$)
		Local temp$ = Left(A$, space)
		Local trimmed$ = Trim(temp) ;we might ignore a final space 
		Local extra = 0 ;we haven't ignored it yet
		;ignore final space If doing so would make a word fit at End of Line:
		If (AAStringWidth (b$ + temp$) > W) And (AAStringWidth (b$ + trimmed$) <= W) Then
			temp = trimmed
			extra = 1
		EndIf
		
		If AAStringWidth (b$ + temp$) > W Then ;too big, so Print what will fit
			If align Then
				AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b)
			Else
				AAText(X, LinesShown * Height + Y, b)
			EndIf			
			
			LinesShown = LinesShown + 1
			b$=""
		Else ;append it To b$ (which will eventually be printed) And remove it from A$
			b$ = b$ + temp$
			A$ = Right(A$, Len(A$) - (Len(temp$) + extra))
		EndIf
		
		If ((LinesShown + 1) * Height) > H Then Exit ;the Next Line would be too tall, so leave
	Wend
	
	If (b$ <> "") And((LinesShown + 1) <= H) Then
		If align Then
			AAText(X + W / 2 - (AAStringWidth(b) / 2), LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		Else
			AAText(X, LinesShown * Height + Y, b) ;Print any remaining Text If it'll fit vertically
		EndIf
	EndIf
	

End Function

RowText("SCP-1499 is a Soviet GP-5 gas mask. A seal test performed on the object suggests that the object retains its original functionality.", 440, 480, 400, 300, True)
RT_Trace("done")
End
