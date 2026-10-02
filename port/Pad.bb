;----------------------------------------------------------------------------------------------------------------------------------------------------
; Pad.bb - gamepad support for the inventory screens (scpcb-nx port)
;
; The game's inventories are mouse driven: press on an item, drag it to another slot, release. With a gamepad that means holding the
; button while pushing a stick. Instead the game tells the runtime where each slot is (this file mirrors the layout code in DrawGUI),
; and the runtime lets the d-pad / left stick jump from slot to slot, A pick an item up and drop it (press, move, press) and Y
; double-click (use or equip). Everything else keeps working with the right stick and the triggers.
;----------------------------------------------------------------------------------------------------------------------------------------------------

Function PadInvReport(count%, y0%)
	Local w% = 70, h% = 70, sp% = 35, n%, k%, x0%, x%, y%
	x0 = GraphicWidth / 2 - (w * MaxItemAmount / 2 + sp * (MaxItemAmount / 2 - 1)) / 2
	x = x0
	y = y0
	RT_PadInvBegin()
	For n = 0 To count - 1
		RT_PadInvSlot(x + w / 2, y + h / 2)
		x = x + w + sp
		k = k + 1
		If k = 5 Then
			k = 0
			y = y + h * 2
			x = x0
		EndIf
	Next
End Function
