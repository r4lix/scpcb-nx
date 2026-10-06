; Post-processing shaders of UE Reborn (HLSL effects) are not available: every pass is a no-op here.

Const DEFERRED_PATH$ = "GFX\Shaders\Deferred\"
Const POSTEFFECTS_PATH$ = "GFX\Shaders\PostEffects\"

Global PostEffectQuad%
Global LinearDepth%
Global ReflectionProbesEffect%

Function LoadEffectEx%(File$, Defines$ = "", Necessary% = True)
	Return(0)
End Function

Function InitShaders%()
End Function

Function ReloadPostEffects%()
End Function

Function ProcessBloom%(Threshold# = 1.0)
End Function

Function ProcessFog%(R%, G%, B%)
End Function

Function ProcessSSAO%(Cam%, Strength#, Radius#, BloomThreshold#)
End Function

Function ProcessLinearDepth%(Cam%)
End Function

Function ProcessGamma%(Src%, Dest%, Gamma#)
End Function

Function PresentGBuffer%(Texture%, Dest% = 0, Depth% = 0, Pow% = 0, Blend% = 0)
End Function

Function ClearBuffer%(Buffer%, R%, G%, B%, Alpha%)
End Function

Function RenderEffectQuad%(Effect%, Texture%, Technique$, Blend% = 0)
End Function

Function BlendReflectionProbes%(Texture%)
End Function

Function PrepareReflectionProbes%(Texture%)
End Function
