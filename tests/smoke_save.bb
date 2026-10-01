Graphics3D 800,600,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
PositionEntity cam,0,0,-5
l=CreateLight()
c=CreateCube()
EntityColor c,255,80,40
For i=1 To 30
  TurnEntity c,1,2,0
  RenderWorld
  Text 10,10,"smoke ok"
  Flip
Next
RenderWorld
Text 10,10,"smoke ok"
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\smoke_out.bmp")
Flip
End
