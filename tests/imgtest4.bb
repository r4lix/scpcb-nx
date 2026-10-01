Graphics 640,480,32,2
SetBuffer BackBuffer()
a=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\back.jpg")
LockBuffer ImageBuffer(a)
v0 = ReadPixelFast(300,100,ImageBuffer(a))
UnlockBuffer ImageBuffer(a)
ResizeImage a, 640, 480
LockBuffer ImageBuffer(a)
v1 = ReadPixelFast(300,200,ImageBuffer(a))
UnlockBuffer ImageBuffer(a)
f=WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\imgtest4.txt")
WriteLine f,"orig px=" + Hex(v0) + " resized px=" + Hex(v1)
CloseFile f
ClsColor 255,0,255
Cls
DrawImage a,0,0
LockBuffer BackBuffer()
v2 = ReadPixelFast(300,200,BackBuffer())
UnlockBuffer BackBuffer()
f=OpenFile("C:\Users\Admin\Documents\scpcb-nx\tests\imgtest4.txt")
SeekFile f,FileSize("C:\Users\Admin\Documents\scpcb-nx\tests\imgtest4.txt")
WriteLine f,"backbuffer after draw px=" + Hex(v2)
CloseFile f
Flip
End
