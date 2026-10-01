f=WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\cmp.txt")
a%=1924037920
b%=0
WriteLine f,"a<>0 = " + (a<>0)
WriteLine f,"a=0 = " + (a=0)
WriteLine f,"a>b = " + (a>b)
WriteLine f,"1+(a<>0) = " + (1+(a<>0))
WriteLine f,"Not 0 = " + (Not 0)
WriteLine f,"Not 5 = " + (Not 5)
x#=0.5
WriteLine f,"x#>0.2 = " + (x>0.2)
WriteLine f,"True = " + True
WriteLine f,"(a<>0) And 1 = " + ((a<>0) And 1)
CloseFile f
End
