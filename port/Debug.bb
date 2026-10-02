;----------------------------------------------------------------------------------------------------------------------------------------------------
; Debug.bb - test aids for the scpcb-nx port (inactive unless switch_settings.ini says so)
;
;   dbg_inv=1     put three items in the inventory and open it (gamepad inventory navigation test)
;   dbg_paper=1   open a document as if it was being read (checks the image resize path)
;   dbg_items=1   a couple of seconds into a game, drop a sample of every kind of item on the floor in front of the player,
;                 to check how item models and textures render
;----------------------------------------------------------------------------------------------------------------------------------------------------

Global DBG_Frames% = 0
Global DBG_Done% = False
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

Function DBG_Update()
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
