;----------------------------------------------------------------------------------------------------------------------------------------------------
; Multiplayer.bb - co-op "presence" prototype for the scpcb-nx port
;
; Everyone runs the full game locally. For the same Map seed and difficulty the level is generated identically, so the players only
; have to exchange where they are: each peer shows the others as class-D figures walking through its own copy of the level.
; Doors, items and monsters are NOT synchronised yet.
;
; Transport: UDP via the runtime's sockets commands. Star topology: the host listens on mp_port, joiners send their state to the host,
; and the host answers every joiner with the state of all other players (including its own) 20 times per second.
; Settings (set from the overlay menu, switch_settings.ini): mp_mode (0 off, 1 host, 2 join), mp_ip, mp_port, mp_name.
;
; Hooks (added by tools/prepare_run.py, upstream sources stay untouched):
;   MP_Update()  once per frame while a game is running
;   MP_Draw()    after the world has been rendered
;   MP_Reset()   when a game is torn down (NullGame): the figures are deleted with the world
;----------------------------------------------------------------------------------------------------------------------------------------------------

Const MP_MAX_PEERS% = 3
Const MP_SEND_MS% = 50
Const MP_TIMEOUT_MS% = 6000
Const MP_PACKET_STATE_UP% = 1
Const MP_PACKET_STATE_DOWN% = 2

Type MPPeer
	Field id%
	Field ip%, port%
	Field name$
	Field lastSeen%
	Field x#, y#, z#, yaw#, pitch#
	Field moving%, crouch%
	Field gx#, gy#, gz#, gyaw#
	Field obj%
End Type

Global MP_Mode% = 0
Global MP_Started% = False
Global MP_Stream% = 0
Global MP_HostIP% = 0, MP_HostPort% = 0
Global MP_MyID% = -1
Global MP_Name$ = "Player"
Global MP_NextSend% = 0
Global MP_NextID% = 1
Global MP_LastX#, MP_LastZ#, MP_Moving%
Global MP_Status$ = ""
Global MP_Connected% = False
Global MP_DebugOffset# = 0.0

Function MP_ParseIP%(s$)
	Local result% = 0, part$, p%, i%, n%
	For i = 0 To 3
		If i < 3 Then
			p = Instr(s, ".")
			If p = 0 Then Return 0
			part = Left(s, p - 1)
			s = Mid(s, p + 1)
		Else
			part = s
		EndIf
		n = Int(part)
		If n < 0 Or n > 255 Then Return 0
		result = (result Shl 8) Or n
	Next
	Return result
End Function

Function MP_Start()
	MP_Started = True
	MP_Mode = RT_SettingI("mp_mode", 0)
	MP_DebugOffset = RT_SettingF("mp_ghost_offset", 0.0)
	If MP_Mode <= 0 Then
		MP_Mode = 0
		Return
	EndIf
	MP_Name = Left(RT_SettingS$("mp_name", "Player"), 15)
	If MP_Name = "" Then MP_Name = "Player"
	Local port% = RT_SettingI("mp_port", 47815)
	If MP_Mode = 1 Then
		MP_Stream = CreateUDPStream(port)
		MP_MyID = 0
		MP_Status$ = "Hosting on port " + port
	Else
		MP_HostIP = MP_ParseIP(RT_SettingS$("mp_ip", ""))
		MP_HostPort = port
		If MP_HostIP = 0 Then
			MP_Mode = 0
			Return
		EndIf
		MP_Stream = CreateUDPStream(0)
		MP_Status$ = "Connecting to " + DottedIP(MP_HostIP) + "..."
	EndIf
	If MP_Stream = 0 Then
		MP_Status$ = "Could not open UDP port " + port
		MP_Mode = -1
	EndIf
End Function

Function MP_Reset()
	Local p.MPPeer
	For p = Each MPPeer
		p\obj = 0
	Next
End Function

Function MP_FindPeerByAddr.MPPeer(ip%, port%)
	Local p.MPPeer
	For p = Each MPPeer
		If p\ip = ip And p\port = port Then Return p
	Next
	Return Null
End Function

Function MP_FindPeerByID.MPPeer(id%)
	Local p.MPPeer
	For p = Each MPPeer
		If p\id = id Then Return p
	Next
	Return Null
End Function

Function MP_CountPeers%()
	Local n% = 0, p.MPPeer
	For p = Each MPPeer
		n = n + 1
	Next
	Return n
End Function

Function MP_RemovePeer(p.MPPeer)
	If p\obj <> 0 Then FreeEntity p\obj
	Delete p
End Function

Function MP_ReadState(p.MPPeer)
	p\x = ReadFloat(MP_Stream)
	p\y = ReadFloat(MP_Stream)
	p\z = ReadFloat(MP_Stream)
	p\yaw = ReadFloat(MP_Stream)
	p\pitch = ReadFloat(MP_Stream)
	Local flags% = ReadByte(MP_Stream)
	p\moving = (flags And 1) <> 0
	p\crouch = (flags And 2) <> 0
End Function

Function MP_WriteState(x#, y#, z#, yaw#, pitch#, flags%)
	WriteFloat MP_Stream, x
	WriteFloat MP_Stream, y
	WriteFloat MP_Stream, z
	WriteFloat MP_Stream, yaw
	WriteFloat MP_Stream, pitch
	WriteByte MP_Stream, flags
End Function

Function MP_MyFlags%()
	Local f% = 0
	If MP_Moving Then f = f Or 1
	If Crouch Then f = f Or 2
	Return f
End Function

Function MP_HandlePacket(ip%, port%, now%)
	Local kind% = ReadByte(MP_Stream)
	Local p.MPPeer, id%, count%, i%, name$

	If kind = MP_PACKET_STATE_UP And MP_Mode = 1 Then
		p = MP_FindPeerByAddr(ip, port)
		If p = Null Then
			If MP_CountPeers() >= MP_MAX_PEERS Then Return
			p = New MPPeer
			p\ip = ip : p\port = port
			p\id = MP_NextID
			MP_NextID = MP_NextID + 1
			p\gx = 0 : p\gy = -1000 : p\gz = 0
		EndIf
		p\name = Left(ReadString(MP_Stream), 15)
		MP_ReadState(p)
		p\lastSeen = now
	ElseIf kind = MP_PACKET_STATE_DOWN And MP_Mode = 2 Then
		MP_MyID = ReadByte(MP_Stream)
		count = ReadByte(MP_Stream)
		MP_Connected = True
		MP_Status$ = "Connected - " + count + " players"
		For i = 1 To count
			id = ReadByte(MP_Stream)
			name = Left(ReadString(MP_Stream), 15)
			If id = MP_MyID Then
				; our own entry: skip the state
				ReadFloat(MP_Stream) : ReadFloat(MP_Stream) : ReadFloat(MP_Stream) : ReadFloat(MP_Stream) : ReadFloat(MP_Stream) : ReadByte(MP_Stream)
			Else
				p = MP_FindPeerByID(id)
				If p = Null Then
					p = New MPPeer
					p\id = id
					p\gx = 0 : p\gy = -1000 : p\gz = 0
				EndIf
				p\name = name
				MP_ReadState(p)
				p\lastSeen = now
			EndIf
		Next
	EndIf
End Function

Function MP_SendState(now%)
	Local p.MPPeer, q.MPPeer, n%
	Local ex# = EntityX(Collider), ey# = EntityY(Collider), ez# = EntityZ(Collider)
	Local yaw# = EntityYaw(Collider), pitch# = EntityPitch(Camera)

	If MP_Mode = 2 Then
		WriteByte MP_Stream, MP_PACKET_STATE_UP
		WriteString MP_Stream, MP_Name
		MP_WriteState(ex, ey, ez, yaw, pitch, MP_MyFlags())
		SendUDPMsg MP_Stream, MP_HostIP, MP_HostPort
		If Not MP_Connected And now > 4000 Then MP_Status$ = "No answer from " + DottedIP(MP_HostIP)
	ElseIf MP_Mode = 1 Then
		n = MP_CountPeers()
		For p = Each MPPeer
			WriteByte MP_Stream, MP_PACKET_STATE_DOWN
			WriteByte MP_Stream, p\id
			WriteByte MP_Stream, n + 1
			; the host first
			WriteByte MP_Stream, 0
			WriteString MP_Stream, MP_Name
			MP_WriteState(ex, ey, ez, yaw, pitch, MP_MyFlags())
			For q = Each MPPeer
				WriteByte MP_Stream, q\id
				WriteString MP_Stream, q\name
				MP_WriteState(q\x, q\y, q\z, q\yaw, q\pitch, (q\moving) + (q\crouch * 2))
			Next
			SendUDPMsg MP_Stream, p\ip, p\port
		Next
		If n = 0 Then
			MP_Status$ = "Hosting on port " + RT_SettingI("mp_port", 47815) + " - waiting for players"
		Else
			MP_Status$ = "Hosting - " + (n + 1) + " players"
		EndIf
	EndIf
End Function

Function MP_UpdateGhosts()
	Local p.MPPeer, f#, d#, sc#
	f# = Min(1.0, 0.25 * FPSfactor2)
	For p = Each MPPeer
		If p\obj = 0 And ClassDObj <> 0 Then
			p\obj = CopyEntity(ClassDObj)
			sc = 0.5 / MeshWidth(ClassDObj)
			ScaleEntity p\obj, sc, sc, sc
			ShowEntity p\obj
			EntityPickMode p\obj, 0
			EntityType p\obj, 0
			p\gx = p\x : p\gy = p\y : p\gz = p\z : p\gyaw = p\yaw
		EndIf
		If p\obj <> 0 Then
			p\gx = p\gx + (p\x - p\gx) * f
			p\gy = p\gy + (p\y - p\gy) * f
			p\gz = p\gz + (p\z - p\gz) * f
			d = ((p\yaw - p\gyaw + 540.0) Mod 360.0) - 180.0
			p\gyaw = p\gyaw + d * f
			If MP_DebugOffset <> 0.0 Then
				; test aid: show the figure in front of our own camera instead of at its real position
				TFormPoint 0, 0, MP_DebugOffset, Camera, 0
				PositionEntity p\obj, TFormedX(), EntityY(Collider) - 0.32, TFormedZ()
			Else
				PositionEntity p\obj, p\gx, p\gy - 0.32, p\gz
			EndIf
			RotateEntity p\obj, 0, p\gyaw - 180.0, 0
			If p\moving Then
				Animate2(p\obj, AnimTime(p\obj), 236, 260, 0.4)
			Else
				Animate2(p\obj, AnimTime(p\obj), 210, 235, 0.1)
			EndIf
		EndIf
	Next
End Function

Function MP_Update()
	If Not MP_Started Then MP_Start()
	If MP_Mode <= 0 Then Return

	Local now% = MilliSecs()
	Local dx# = EntityX(Collider) - MP_LastX, dz# = EntityZ(Collider) - MP_LastZ
	MP_LastX = EntityX(Collider) : MP_LastZ = EntityZ(Collider)
	If FPSfactor > 0 Then MP_Moving = (Sqr(dx * dx + dz * dz) / FPSfactor) > 0.004

	Local ip%, p.MPPeer
	Repeat
		ip = RecvUDPMsg(MP_Stream)
		If ip = 0 Then Exit
		MP_HandlePacket(ip, UDPMsgPort(MP_Stream), now)
	Forever

	If now >= MP_NextSend Then
		MP_NextSend = now + MP_SEND_MS
		MP_SendState(now)
	EndIf

	For p = Each MPPeer
		If now - p\lastSeen > MP_TIMEOUT_MS Then MP_RemovePeer(p)
	Next

	MP_UpdateGhosts()
End Function

Function MP_Draw()
	If MP_Mode = 0 Then Return
	Local p.MPPeer, dist#

	AASetFont Font1
	Color 0, 0, 0
	AAText 11, 11, "MP: " + MP_Status$
	Color 200, 200, 200
	AAText 10, 10, "MP: " + MP_Status$

	For p = Each MPPeer
		If p\obj <> 0 Then
			dist = EntityDistance(Camera, p\obj)
			If dist < 30.0 Then
				CameraProject Camera, p\gx, p\gy + 1.55, p\gz
				If ProjectedZ() > 0.0 Then
					Color 0, 0, 0
					AAText ProjectedX() + 1, ProjectedY() + 1, p\name, True, True
					Color 255, 255, 255
					AAText ProjectedX(), ProjectedY(), p\name, True, True
				EndIf
			EndIf
		EndIf
	Next
End Function
