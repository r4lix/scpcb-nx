Graphics3D 1280,720,32,2
SetBuffer BackBuffer()
cam=CreateCamera()
CameraClsColor cam,20,20,50
PositionEntity cam,0,0,-4
l=CreateLight()
cube=CreateCube()

; 1) backbuffer -> big texture (the per-frame resize path)
big=CreateTexture(2048,2048,1+256)
ClsColor 0,0,0
Cls
Color 255,0,0 : Rect 0,0,200,100,True
Color 0,255,0 : Rect 200,0,200,100,True
Color 0,0,255 : Rect 0,100,200,100,True
CopyRect 0,0,640,480,704,544,BackBuffer(),TextureBuffer(big)

; 2) image -> texture
img=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\menuwhite.jpg")
tex2=CreateTexture(512,512,1+256)
CopyRect 0,0,ImageWidth(img),ImageHeight(img),0,0,ImageBuffer(img),TextureBuffer(tex2)

; 3) backbuffer -> image
img2=CreateImage(256,256)
CopyRect 100,100,256,256,0,0,BackBuffer(),ImageBuffer(img2)

; 4) camera-to-texture (security camera style)
scr=CreateTexture(512,512,1+256)
cam2=CreateCamera()
PositionEntity cam2,3,0,-4
CameraViewport cam2,0,0,512,512
CameraClsColor cam2,90,20,20
CameraProjMode cam,0
CameraProjMode cam2,1
RenderWorld
CopyRect 0,0,512,512,0,0,BackBuffer(),TextureBuffer(scr)
CameraProjMode cam2,0
CameraProjMode cam,1

; 5) reading texture pixels
LockBuffer TextureBuffer(big)
v=ReadPixelFast(710,550,TextureBuffer(big))
UnlockBuffer TextureBuffer(big)

; show results
ClsColor 40,40,40
Cls
EntityTexture cube,scr
TurnEntity cube,20,30,0
RenderWorld
DrawImage img2,10,10
Text 300,10,"pixel(710,550) of big = " + Hex(v)
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\rtt2.bmp")
Flip
End
