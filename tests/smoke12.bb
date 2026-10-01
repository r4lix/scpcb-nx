Graphics3D 800,600,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
PositionEntity cam,0,0,-5
l=CreateLight()
c=CreateCube()
t=MilliSecs()
While MilliSecs()-t < 12000
  TurnEntity c,1,2,0
  RenderWorld
  Text 10,10,"smoke ok"
  Flip
Wend
End
