f=WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\audio1.txt")
s=LoadSound("C:\Users\Admin\Documents\scpcb-nx\scpcb\SFX\Alarm\Alarm.ogg")
WriteLine f,"loaded sound=" + (s<>0)
c=PlaySound(s)
WriteLine f,"playing(t=0)=" + ChannelPlaying(c)
Delay 700
WriteLine f,"playing(t=0.7)=" + ChannelPlaying(c)
ChannelPan c,-1.0
Delay 600
ChannelPan c,1.0
Delay 600
ChannelVolume c,0.0
Delay 400
StopChannel c
WriteLine f,"playing(after stop)=" + ChannelPlaying(c)
m=PlayMusic("C:\Users\Admin\Documents\scpcb-nx\scpcb\SFX\Music\Menu.ogg")
WriteLine f,"music started=" + (m<>0)
Delay 1500
WriteLine f,"music playing=" + ChannelPlaying(m)
StopChannel m
CloseFile f
End
