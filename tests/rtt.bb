Graphics3D 800,600,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
PositionEntity cam,0,0,-5
CameraClsColor cam,30,30,60
l=CreateLight()
amb=CreateTexture(2,2,257)
TextureBlend amb,5
SetBuffer TextureBuffer(amb)
ClsColor 120,200,80
Cls
SetBuffer BackBuffer()
c=CreateCube()
EntityTexture c,amb
big=CreateTexture(256,256,1+256)
SetBuffer TextureBuffer(big)
ClsColor 200,40,40
Cls
Color 255,255,0
Rect 20,20,100,100,True
SetBuffer BackBuffer()
c2=CreateCube()
PositionEntity c2,2.5,0,0
EntityTexture c2,big
For i=1 To 10
 TurnEntity c,1,2,0
 TurnEntity c2,1,2,0
 RenderWorld
 Flip
Next
RenderWorld
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\rtt.bmp")
Flip
End
