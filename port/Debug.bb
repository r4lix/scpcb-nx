;----------------------------------------------------------------------------------------------------------------------------------------------------
; Debug.bb - test aids for the scpcb-nx port (inactive unless switch_settings.ini says so)
;
;   dbg_npcs=1    stand a few characters (guard, class D, clerk, SCP-173) in front of the player, to check how they are lit
;   dbg_door=1    walk the player up to the nearest door and face it (checks how doors are lit)
;   dbg_inv=1     put three items in the inventory and open it (gamepad inventory navigation test)
;   dbg_paper=1   open a document as if it was being read (checks the image resize path)
;   dbg_items=1   a couple of seconds into a game, drop a sample of every kind of item on the floor in front of the player,
;                 to check how item models and textures render
;----------------------------------------------------------------------------------------------------------------------------------------------------

Global DBG_Frames% = 0
Global DBG_Done% = False
Global DBG_DoorDone% = False
Global DBG_NpcDone% = False
Global DBG_InvDone% = False
Global DBG_PaperDone% = False
Global DBG_LookDown% = 0

Function DBG_SpawnItem(name$, tempname$, forward#, side#)
	Local x#, z#, it.Items
	TFormPoint side, 0, forward, Camera, 0
	x = TFormedX() : z = TFormedZ()
	it = CreateItem(name, tempname, x, EntityY(Collider) + 0.2, z)
	If it <> Null Then RT_Trace("spawned " + name)
End Function


Function DBG_SpawnNpc.NPCs(kind%, forward#, side#)
	Local n.NPCs
	TFormPoint side, 0, forward, Camera, 0
	n = CreateNPC(kind, TFormedX(), EntityY(Collider) - 0.1, TFormedZ())
	If n <> Null Then
		PointEntity n\Collider, Collider
		n\State = 0
	EndIf
	Return n
End Function

Function DBG_UpdateNpcs()
	If DBG_NpcDone Then Return
	If RT_SettingI("dbg_npcs", 0) = 0 Then
		DBG_NpcDone = True
		Return
	EndIf
	DBG_Frames = DBG_Frames + 1
	If DBG_Frames < 150 Then Return
	DBG_NpcDone = True
	DBG_SpawnNpc(NPCtypeGuard, 1.4, -0.7)
	DBG_SpawnNpc(NPCtypeD, 1.4, 0.0)
	DBG_SpawnNpc(NPCtypeClerk, 1.4, 0.7)
	If Curr173 <> Null Then
		TFormPoint 0.0, 0, 2.2, Camera, 0
		PositionEntity Curr173\Collider, TFormedX(), EntityY(Collider) - 0.1, TFormedZ()
		ResetEntity Curr173\Collider
	EndIf
End Function

Function DBG_UpdateDoor()
	If DBG_DoorDone Then Return
	If RT_SettingI("dbg_door", 0) = 0 Then
		DBG_DoorDone = True
		Return
	EndIf
	DBG_Frames = DBG_Frames + 1
	If DBG_Frames < 150 Then Return
	DBG_DoorDone = True

	Local d.Doors, best.Doors = Null, bd# = 9999.0, dist#
	For d = Each Doors
		If d\obj <> 0 Then
			dist = EntityDistance(Collider, d\obj)
			If dist > 1.0 And dist < bd Then bd = dist : best = d
		EndIf
	Next
	If best = Null Then Return

	Local ax#, az#, bx#, bz#
	TFormPoint 0, 0, 1.7, best\obj, 0
	ax = TFormedX() : az = TFormedZ()
	TFormPoint 0, 0, -1.7, best\obj, 0
	bx = TFormedX() : bz = TFormedZ()
	If Sqr((ax - EntityX(Collider)) ^ 2 + (az - EntityZ(Collider)) ^ 2) < Sqr((bx - EntityX(Collider)) ^ 2 + (bz - EntityZ(Collider)) ^ 2) Then
		PositionEntity Collider, ax, EntityY(best\obj) + 0.3, az
	Else
		PositionEntity Collider, bx, EntityY(best\obj) + 0.3, bz
	EndIf
	ResetEntity Collider
	PointEntity Collider, best\obj
	RotateEntity Collider, 0, EntityYaw(Collider), 0
	user_camera_pitch = 0.0
End Function

Function DBG_Update()
	DBG_UpdateDoor()
	DBG_UpdateNpcs()
	If RT_SettingI("dbg_inv", 0) <> 0 And DBG_InvDone = False Then
		DBG_Frames = DBG_Frames + 1
		If DBG_Frames > 150 Then
			DBG_InvDone = True
			Local dit.Items
			dit = CreateItem("Document SCP-173", "paper", EntityX(Collider), EntityY(Collider), EntityZ(Collider))
			PickItem(dit)
			dit = CreateItem("Level 1 Key Card", "key1", EntityX(Collider), EntityY(Collider), EntityZ(Collider))
			PickItem(dit)
			dit = CreateItem("First Aid Kit", "firstaid", EntityX(Collider), EntityY(Collider), EntityZ(Collider))
			PickItem(dit)
			InvOpen = True
		EndIf
	EndIf
	If RT_SettingI("dbg_paper", 0) <> 0 And DBG_PaperDone = False Then
		DBG_Frames = DBG_Frames + 1
		If DBG_Frames > 150 Then
			DBG_PaperDone = True
			Local pit.Items = CreateItem("Document SCP-173", "paper", EntityX(Collider), EntityY(Collider), EntityZ(Collider))
			SelectedItem = pit
		EndIf
	EndIf
	If DBG_LookDown > 0 Then
		DBG_LookDown = DBG_LookDown - 1
		user_camera_pitch = 48.0
	EndIf
	If DBG_Done Then Return
	If RT_SettingI("dbg_items", 0) = 0 Then
		DBG_Done = True
		Return
	EndIf
	DBG_Frames = DBG_Frames + 1
	If DBG_Frames < 150 Then Return
	DBG_Done = True

	DBG_SpawnItem("Document SCP-173", "paper", 1.1, -0.9)
	DBG_SpawnItem("Document SCP-500", "paper", 1.1, -0.55)
	DBG_SpawnItem("Level 1 Key Card", "key1", 1.1, -0.2)
	DBG_SpawnItem("Ballistic Vest", "vest", 1.1, 0.15)
	DBG_SpawnItem("First Aid Kit", "firstaid", 1.1, 0.5)
	DBG_SpawnItem("Gas Mask", "gasmask", 1.1, 0.85)
	DBG_SpawnItem("Origami", "misc", 1.8, -0.9)
	DBG_SpawnItem("Radio Transceiver", "radio", 1.8, -0.55)
	DBG_SpawnItem("9V Battery", "bat", 1.8, -0.2)
	DBG_SpawnItem("Clipboard", "clipboard", 1.8, 0.15)
	DBG_SpawnItem("Night Vision Goggles", "nvgoggles", 1.8, 0.5)
	DBG_SpawnItem("Metal Panel", "scp148", 1.8, 0.85)
	DBG_LookDown = 150
End Function
