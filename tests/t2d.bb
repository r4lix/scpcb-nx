Graphics 320,240,32,2
SetBuffer BackBuffer()
ClsColor 200,30,30
Cls
Color 30,200,30
Rect 20,20,100,80,True
Color 255,255,255
Text 10,150,"2D ok"
SaveBuffer(BackBuffer(),"C:\Users\Admin\Documents\scpcb-nx\tests\t2d_out.bmp")
Flip
Delay 500
End
