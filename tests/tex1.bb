Graphics3D 800,600,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
PositionEntity cam,0,0,-6
CameraClsColor cam,40,40,80
l=CreateLight()
t1=LoadTexture("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\201_camera_diffuse1.png")
t2=LoadTexture("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\008_lm1.png")
c1=CreateCube()
PositionEntity c1,-2,0,0
EntityTexture c1,t1
c2=CreateCube()
PositionEntity c2,2,0,0
EntityTexture c2,t1,0,0
TextureBlend t2,2
EntityTexture c2,t2,0,1
For i=1 To 20
 TurnEntity c1,1,2,0
 TurnEntity c2,1,2,0
 RenderWorld
 Flip
Next
RenderWorld
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\tex1.bmp")
Flip
End
