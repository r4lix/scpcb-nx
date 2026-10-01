Graphics 1280,720,32,2
SetBuffer BackBuffer()
ClsColor 40,0,40
Cls
a=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\back.jpg")
b=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\back.jpg")
w=ImageWidth(a):h=ImageHeight(a)
Text 10,10,"orig "+w+"x"+h
ResizeImage b,w*0.7,h*0.7
Text 10,30,"resized "+ImageWidth(b)+"x"+ImageHeight(b)
DrawImage a,0,50
DrawImage b,700,50
c=LoadImage("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\menu\menublack.jpg")
MaskImage c,255,255,0
DrawImage c,10,500
RotateImage c,90
DrawImage c,300,500
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\imgtest.bmp")
Flip
End
