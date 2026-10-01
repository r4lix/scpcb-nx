Graphics 1280,720,32,2
SetBuffer BackBuffer()
a=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\back.jpg")
ResizeImage a, 640, 480
SaveBuffer(ImageBuffer(a),"C:\Users\Admin\Documents\scpcb-nx\tests\imgtest3.bmp")
LockBuffer ImageBuffer(a)
v = ReadPixelFast(300,100,ImageBuffer(a))
UnlockBuffer ImageBuffer(a)
Text 10,10,"px=" + Hex(v)
Flip
Delay 200
End
