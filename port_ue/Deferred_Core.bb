; Forward-rendering replacement for UE Reborn's deferred PBR pipeline (Deferred_Core.bb upstream).
; The upstream renderer needs DX11 effect files, MRTs and shadow maps; here the world is rendered with
; the stock Blitz3D pipeline and the nearest few lights are mapped onto fixed-function lights.

Const DEFERRED_LIGHT_DIRECTIONAL% = 1
Const DEFERRED_LIGHT_POINT% = 2
Const DEFERRED_LIGHT_SPOT% = 3

Const DEFERRED_DIFF% = 0
Const DEFERRED_DIFFSKYBOX% = 1
Const DEFERRED_DIFFNORMAL% = 1 Shl 1
Const DEFERRED_DIFFROUGH% = 1 Shl 2
Const DEFERRED_DIFFEMISSIVE% = 1 Shl 3
Const DEFERRED_DIFFEMISSIVEMUL% = 1 Shl 4
Const DEFERRED_FULLBRIGHT% = 1 Shl 5
Const DEFERRED_TRANSPARENT% = 1 Shl 6
Const DEFERRED_DIFFENVMAP% = 1 Shl 7
Const DEFERRED_DIFFHEIGHTMAP% = 1 Shl 8
Const DEFERRED_MASKED% = 1 Shl 9
Const DEFERRED_DISABLEFOG% = 1 Shl 10
Const DEFERRED_DIFFORM% = 1 Shl 11
Const DEFERRED_FORWARD% = 1 Shl 12
Const DEFERRED_LOCALTRANSFORM% = 1 Shl 13
Const DEFERRED_ADDITIVE% = 1 Shl 15
Const DEFERRED_NOMATERIAL% = 1 Shl 16

Global SHADOW_BIAS# = 0.00044
Global NORMAL_OFFSET# = 1.0
Global SLOPE_BIAS# = 2.0

Global MRTColor%, MRTAlbedo%, MRTDepth%, MRTNormal%, MRTLighting%, MRTVolume%, RSDepth%
Global TempColorTexture%
Global GlobalEnvironmentMap%, BlendEnvironmentMap%
Global CurrentTween#
Global ProhibitedInputVariations%
Global EmissiveMultiply#, EnvBlendFactor#

Const MAX_FORWARD_LIGHTS% = 8
Global ForwardLight%[MAX_FORWARD_LIGHTS]
Global ForwardLightDist#[MAX_FORWARD_LIGHTS]
Global ForwardLightSrc%[MAX_FORWARD_LIGHTS]
Global ForwardLightR#[MAX_FORWARD_LIGHTS]
Global ForwardLightG#[MAX_FORWARD_LIGHTS]
Global ForwardLightB#[MAX_FORWARD_LIGHTS]
Global ForwardLightRange#[MAX_FORWARD_LIGHTS]
Global ForwardLightType%[MAX_FORWARD_LIGHTS]
Global ForwardLightFOV#[MAX_FORWARD_LIGHTS]
Global ForwardLightCount%

Type DynamicLight
	Field OBJ%
	Field LType%
	Field R%, G%, B%
	Field Range#
	Field Fade#
	Field FOV#
	Field Scattering#
	Field CastShadows%
End Type

Function InitDeferred%()
	Local i%

	For i = 0 To MAX_FORWARD_LIGHTS - 1
		ForwardLight[i] = CreateLight(2)
		HideEntity(ForwardLight[i])
	Next
End Function

Function GetResolutionDepth%()
	Return(0)
End Function

Function SetRenderParameters%(ScaleX#, ScaleY#, HDR%)
End Function

Function PreloadShaders%()
End Function

Function SetDeferredParticle%(Entity%, Enable% = True)
End Function

Function SetShadowsCasting%(Entity%, Enable%)
End Function

Function SetShadowsBias%(Bias#, Normal#)
End Function

Function ApplyForwardState%(Entity%, State%)
	If State = -1 Then Return
	If (State And DEFERRED_ADDITIVE) <> 0 Then EntityBlend(Entity, 3)

	Local FX% = 0

	If (State And DEFERRED_FULLBRIGHT) <> 0 Then FX = FX Or 1
	If (State And DEFERRED_DISABLEFOG) <> 0 Then FX = FX Or 8
	If FX <> 0 Then EntityFX(Entity, FX)
End Function

Function SetDeferredEntity%(Entity%, CastShadows% = False, State% = -1)
	If Entity = 0 Then Return
	ApplyForwardState(Entity, State)
End Function

Function SetDeferredBrush%(Brush%, State = -1, Frame% = 0)
End Function

Function UpdateEntityMaterial%(Entity%, State% = -1, Frame% = 0)
	If Entity = 0 Then Return
	If EntityClass(Entity) = "Pivot" Then Return
	ApplyForwardState(Entity, State)
End Function

Function GetEmissiveMultiply#()
	Return(EmissiveMultiply)
End Function

Function SetEmissiveMultiply%(Value#, Force% = False)
	EmissiveMultiply = Value
End Function

Function SetEnvBlendFactor%(Value#, Force% = False)
	EnvBlendFactor = Value
End Function

Function GetProhibitedInputEffect%()
	Return(ProhibitedInputVariations)
End Function

Function ProhibitInputEffect%(Bits%)
	ProhibitedInputVariations = Bits
End Function

Function SetGlobalEnvironment%(Texture$)
End Function

Function GenerateEnvironment%(FaceWidth%, x#, y#, z#)
	Return(0)
End Function

Function Batches%()
	Return(0)
End Function

Function Count3D%()
	CurrTrisAmount = CurrTrisAmount + TrisRendered()
	Return(0)
End Function

; ~ Dynamic lights (CreateLight & co. are builtins of the engine; the game's own versions are renamed DL*)

Function FindDynamicLight.DynamicLight(OBJ%)
	Local dl.DynamicLight

	For dl.DynamicLight = Each DynamicLight
		If dl\OBJ = OBJ Then Return(dl)
	Next
	Return(Null)
End Function

Function DLCreateLight%(LType%, Parent% = 0)
	Local dl.DynamicLight = New DynamicLight

	dl\OBJ = CreatePivot(Parent)
	dl\LType = LType
	dl\Fade = 1.0
	dl\R = 255
	dl\G = 255
	dl\B = 255
	dl\Range = 10.0
	dl\FOV = 90.0
	EntityDestructor(dl\OBJ, @OnLightDestruct)
	Return(dl\OBJ)
End Function

Function DLLightRange%(Entity%, Range#)
	Local dl.DynamicLight = FindDynamicLight(Entity)

	If dl <> Null Then dl\Range = Range
End Function

Function DLLightColor%(Entity%, R%, G%, B%)
	Local dl.DynamicLight = FindDynamicLight(Entity)

	If dl <> Null
		dl\R = R
		dl\G = G
		dl\B = B
	EndIf
End Function

Function LightFOV%(Entity%, FOV#)
	Local dl.DynamicLight = FindDynamicLight(Entity)

	If dl <> Null Then dl\FOV = FOV
End Function

Function LightCastShadows%(Entity%, CastShadows%)
End Function

Function LightScattering%(Entity%, Scattering#)
End Function

Function OnLightDestruct%(Entity%)
	Local dl.DynamicLight = FindDynamicLight(Entity)

	If dl <> Null Then Delete(dl)
End Function

; ~ Forward lighting

Function RenderLight%(Cam%, OBJ%, Range#, R%, G%, B%, Intensity#, LType%, FOV# = 90.0, CastShadows% = True, Scattering# = 1.0)
	If Intensity <= 0.0 Then Return

	Local Dist# = EntityDistance(Cam, OBJ)

	If Dist - Range > GetCameraRangeFar(Cam) Then Return

	; ~ keep the MAX_FORWARD_LIGHTS nearest lights
	Local Slot% = -1
	Local i%

	If ForwardLightCount < MAX_FORWARD_LIGHTS
		Slot = ForwardLightCount
		ForwardLightCount = ForwardLightCount + 1
	Else
		Local Worst# = Dist

		For i = 0 To MAX_FORWARD_LIGHTS - 1
			If ForwardLightDist[i] > Worst Then Worst = ForwardLightDist[i] : Slot = i
		Next
	EndIf
	If Slot = -1 Then Return

	ForwardLightDist[Slot] = Dist
	ForwardLightSrc[Slot] = OBJ
	ForwardLightR[Slot] = R * Intensity
	ForwardLightG[Slot] = G * Intensity
	ForwardLightB[Slot] = B * Intensity
	ForwardLightRange[Slot] = Range
	ForwardLightType[Slot] = LType
	ForwardLightFOV[Slot] = FOV
End Function

Function ApplyForwardLights%()
	Local i%, L%

	For i = 0 To MAX_FORWARD_LIGHTS - 1
		L = ForwardLight[i]
		If i < ForwardLightCount
			Local Src% = ForwardLightSrc[i]

			PositionEntity(L, EntityX(Src, True), EntityY(Src, True), EntityZ(Src, True))
			If ForwardLightType[i] = DEFERRED_LIGHT_SPOT
				RotateEntity(L, EntityPitch(Src, True), EntityYaw(Src, True), 0.0)
				LightConeAngles(L, 0.0, Min(ForwardLightFOV[i], 170.0))
			EndIf
			LightRange(L, ForwardLightRange[i])
			LightColor(L, Min(ForwardLightR[i], 255.0), Min(ForwardLightG[i], 255.0), Min(ForwardLightB[i], 255.0))
			ShowEntity(L)
		Else
			HideEntity(L)
		EndIf
	Next
End Function

Function ProcessGraphics%(Cam%, Environment% = False)
	Local l.Lights, dl.DynamicLight

	ForwardLightCount = 0
	For l.Lights = Each Lights
		If (Not EntityHidden(l\OBJ)) Then RenderLight(Cam, l\OBJ, l\Range, l\R, l\G, l\B, Max(l\Fade * Min(SecondaryLightOn, 1.0), Environment), l\LType, l\FOV, False, 0.0)
	Next

	For dl.DynamicLight = Each DynamicLight
		If (Not EntityHidden(dl\OBJ)) And (GetParent(dl\OBJ) = 0 Lor (Not EntityHidden(GetParent(dl\OBJ)))) Then RenderLight(Cam, dl\OBJ, dl\Range, dl\R, dl\G, dl\B, dl\Fade, dl\LType, dl\FOV, False, 0.0)
	Next

	ApplyForwardLights()
End Function

Function ProcessDeferred%(Cam%, Tween# = 1.0, ScaleX# = 1.0, ScaleY# = 1.0, Environment% = False, Destination% = 0)
	CurrentTween = Tween
	ProcessGraphics(Cam, Environment)
	RenderWorld(Tween)
	Count3D()
End Function
