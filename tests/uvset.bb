Graphics3D 800,600,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
PositionEntity cam,0,0,-5
CameraClsColor cam,30,30,60
t=LoadTexture("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\201_camera_diffuse1.png")
For n=0 To 1
 m=CreateMesh()
 s=CreateSurface(m)
 v0=AddVertex(s,-1,-1,0) : v1=AddVertex(s,1,-1,0) : v2=AddVertex(s,1,1,0) : v3=AddVertex(s,-1,1,0)
 VertexTexCoords s,v0,0,1,0,0 : VertexTexCoords s,v1,1,1,0,0 : VertexTexCoords s,v2,1,0,0,0 : VertexTexCoords s,v3,0,0,0,0
 VertexTexCoords s,v0,0,0.5,0,1 : VertexTexCoords s,v1,0.5,0.5,0,1 : VertexTexCoords s,v2,0.5,0,0,1 : VertexTexCoords s,v3,0,0,0,1
 AddTriangle s,v0,v2,v1 : AddTriangle s,v0,v3,v2
 UpdateNormals m
 EntityFX m,1
 TextureCoords t,n
 EntityTexture m,t
 PositionEntity m,-1.6+n*3.2,0,0
 If n=0 Then m0=m Else m1=m
Next
; same texture object, coords differ -> only last setting wins, so use two textures
t2=LoadTexture("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\201_camera_diffuse1.png")
TextureCoords t,0
TextureCoords t2,1
EntityTexture m0,t
EntityTexture m1,t2
RenderWorld
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\uvset.bmp")
Flip
End
