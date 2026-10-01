Graphics 1280,720,32,2
SetBuffer BackBuffer()
Const MenuScale# = 720 / 1024.0
a=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\back.jpg")
ResizeImage(a, ImageWidth(a) * MenuScale, ImageHeight(a) * MenuScale)
Color 255,255,255
Rect 0,0,1280,720,True
DrawImage a,0,0
Color 255,255,255
Text 10,10,"resized "+ImageWidth(a)+"x"+ImageHeight(a)
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\imgtest2.bmp")
Flip
End
