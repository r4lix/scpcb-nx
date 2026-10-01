Graphics3D 1280,720,32,2
SetBuffer BackBuffer()
big=CreateTexture(2048,2048,1+256)
mode=1
For i=1 To 900
  ClsColor 20,20,40
  Cls
  Color 255,255,255
  Rect 100+(i Mod 50),100,300,200,True
  If mode=1 Then CopyRect 0,0,1280,720,384,664,BackBuffer(),TextureBuffer(big)
  Flip
Next
End
