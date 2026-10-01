Graphics3D 1280,720,32,2
SetBuffer BackBuffer()
mode=1
For i=1 To 900
  ClsColor 20,20,40
  Cls
  Color 255,255,255
  Rect 100+(i Mod 50),100,300,200,True
  Text 20,20,"frame " + i
  Flip
Next
End
