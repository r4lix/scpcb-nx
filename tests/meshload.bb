Graphics3D 640,480,32,2
SetBuffer BackBuffer()
f=WriteFile("C:\Users\Admin\Documents\scpcb-nx\tests\meshes.txt")
Local m%
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\173box.b3d")
If m=0 Then WriteLine f,"FAIL GFX\173box.b3d" Else WriteLine f,"ok   GFX\173box.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\apache.b3d")
If m=0 Then WriteLine f,"FAIL GFX\apache.b3d" Else WriteLine f,"ok   GFX\apache.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\apacherotor.b3d")
If m=0 Then WriteLine f,"FAIL GFX\apacherotor.b3d" Else WriteLine f,"ok   GFX\apacherotor.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\apacherotor2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\apacherotor2.b3d" Else WriteLine f,"ok   GFX\apacherotor2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\doorhit.b3d")
If m=0 Then WriteLine f,"FAIL GFX\doorhit.b3d" Else WriteLine f,"ok   GFX\doorhit.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\420.x")
If m=0 Then WriteLine f,"FAIL GFX\items\420.x" Else WriteLine f,"ok   GFX\items\420.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\427.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\427.b3d" Else WriteLine f,"ok   GFX\items\427.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\513.x")
If m=0 Then WriteLine f,"FAIL GFX\items\513.x" Else WriteLine f,"ok   GFX\items\513.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\Battery\Battery.x")
If m=0 Then WriteLine f,"FAIL GFX\items\Battery\Battery.x" Else WriteLine f,"ok   GFX\items\Battery\Battery.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\HGIB_Skull1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\HGIB_Skull1.b3d" Else WriteLine f,"ok   GFX\items\HGIB_Skull1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\NVG.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\NVG.b3d" Else WriteLine f,"ok   GFX\items\NVG.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\SCP-1499.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\SCP-1499.b3d" Else WriteLine f,"ok   GFX\items\SCP-1499.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\Syringe\syringe.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\Syringe\syringe.b3d" Else WriteLine f,"ok   GFX\items\Syringe\syringe.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\badge.x")
If m=0 Then WriteLine f,"FAIL GFX\items\badge.x" Else WriteLine f,"ok   GFX\items\badge.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\clipboard.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\clipboard.b3d" Else WriteLine f,"ok   GFX\items\clipboard.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\cup.x")
If m=0 Then WriteLine f,"FAIL GFX\items\cup.x" Else WriteLine f,"ok   GFX\items\cup.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\cupliquid.x")
If m=0 Then WriteLine f,"FAIL GFX\items\cupliquid.x" Else WriteLine f,"ok   GFX\items\cupliquid.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\electronics.x")
If m=0 Then WriteLine f,"FAIL GFX\items\electronics.x" Else WriteLine f,"ok   GFX\items\electronics.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\eyedrops.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\eyedrops.b3d" Else WriteLine f,"ok   GFX\items\eyedrops.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\firstaid.x")
If m=0 Then WriteLine f,"FAIL GFX\items\firstaid.x" Else WriteLine f,"ok   GFX\items\firstaid.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\gasmask.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\gasmask.b3d" Else WriteLine f,"ok   GFX\items\gasmask.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\hazmat.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\hazmat.b3d" Else WriteLine f,"ok   GFX\items\hazmat.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\key.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\key.b3d" Else WriteLine f,"ok   GFX\items\key.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\keycard.x")
If m=0 Then WriteLine f,"FAIL GFX\items\keycard.x" Else WriteLine f,"ok   GFX\items\keycard.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\metalpanel.x")
If m=0 Then WriteLine f,"FAIL GFX\items\metalpanel.x" Else WriteLine f,"ok   GFX\items\metalpanel.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\navigator.x")
If m=0 Then WriteLine f,"FAIL GFX\items\navigator.x" Else WriteLine f,"ok   GFX\items\navigator.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\note.x")
If m=0 Then WriteLine f,"FAIL GFX\items\note.x" Else WriteLine f,"ok   GFX\items\note.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\origami.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\origami.b3d" Else WriteLine f,"ok   GFX\items\origami.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\paper.x")
If m=0 Then WriteLine f,"FAIL GFX\items\paper.x" Else WriteLine f,"ok   GFX\items\paper.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\paperstrips.x")
If m=0 Then WriteLine f,"FAIL GFX\items\paperstrips.x" Else WriteLine f,"ok   GFX\items\paperstrips.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\pill.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\pill.b3d" Else WriteLine f,"ok   GFX\items\pill.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\radio.x")
If m=0 Then WriteLine f,"FAIL GFX\items\radio.x" Else WriteLine f,"ok   GFX\items\radio.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\scp1025.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\scp1025.b3d" Else WriteLine f,"ok   GFX\items\scp1025.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\scp148.x")
If m=0 Then WriteLine f,"FAIL GFX\items\scp148.x" Else WriteLine f,"ok   GFX\items\scp148.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\scp714.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\scp714.b3d" Else WriteLine f,"ok   GFX\items\scp714.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\severedhand.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\severedhand.b3d" Else WriteLine f,"ok   GFX\items\severedhand.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\vest.x")
If m=0 Then WriteLine f,"FAIL GFX\items\vest.x" Else WriteLine f,"ok   GFX\items\vest.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\items\wallet.b3d")
If m=0 Then WriteLine f,"FAIL GFX\items\wallet.b3d" Else WriteLine f,"ok   GFX\items\wallet.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\lightcone.b3d")
If m=0 Then WriteLine f,"FAIL GFX\lightcone.b3d" Else WriteLine f,"ok   GFX\lightcone.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\008_2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\008_2.b3d" Else WriteLine f,"ok   GFX\map\008_2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\079.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\079.b3d" Else WriteLine f,"ok   GFX\map\079.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\1123_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\1123_hb.b3d" Else WriteLine f,"ok   GFX\map\1123_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\173_2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\173_2.b3d" Else WriteLine f,"ok   GFX\map\173_2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\294.x")
If m=0 Then WriteLine f,"FAIL GFX\map\294.x" Else WriteLine f,"ok   GFX\map\294.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\372_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\372_hb.b3d" Else WriteLine f,"ok   GFX\map\372_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\914key.x")
If m=0 Then WriteLine f,"FAIL GFX\map\914key.x" Else WriteLine f,"ok   GFX\map\914key.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\914knob.x")
If m=0 Then WriteLine f,"FAIL GFX\map\914knob.x" Else WriteLine f,"ok   GFX\map\914knob.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Button.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Button.x" Else WriteLine f,"ok   GFX\map\Button.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\ButtonCode.x")
If m=0 Then WriteLine f,"FAIL GFX\map\ButtonCode.x" Else WriteLine f,"ok   GFX\map\ButtonCode.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\ButtonKeycard.x")
If m=0 Then WriteLine f,"FAIL GFX\map\ButtonKeycard.x" Else WriteLine f,"ok   GFX\map\ButtonKeycard.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\ButtonScanner.x")
If m=0 Then WriteLine f,"FAIL GFX\map\ButtonScanner.x" Else WriteLine f,"ok   GFX\map\ButtonScanner.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\CamHead.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\CamHead.b3d" Else WriteLine f,"ok   GFX\map\CamHead.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\CamHead.x")
If m=0 Then WriteLine f,"FAIL GFX\map\CamHead.x" Else WriteLine f,"ok   GFX\map\CamHead.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\ContDoorLeft.x")
If m=0 Then WriteLine f,"FAIL GFX\map\ContDoorLeft.x" Else WriteLine f,"ok   GFX\map\ContDoorLeft.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\ContDoorRight.x")
If m=0 Then WriteLine f,"FAIL GFX\map\ContDoorRight.x" Else WriteLine f,"ok   GFX\map\ContDoorRight.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Door01.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Door01.x" Else WriteLine f,"ok   GFX\map\Door01.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\DoorColl.x")
If m=0 Then WriteLine f,"FAIL GFX\map\DoorColl.x" Else WriteLine f,"ok   GFX\map\DoorColl.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\DoorFrame.x")
If m=0 Then WriteLine f,"FAIL GFX\map\DoorFrame.x" Else WriteLine f,"ok   GFX\map\DoorFrame.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\IntroDesk.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\IntroDesk.b3d" Else WriteLine f,"ok   GFX\map\IntroDesk.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\IntroDrawer.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\IntroDrawer.b3d" Else WriteLine f,"ok   GFX\map\IntroDrawer.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\205.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\205.x" Else WriteLine f,"ok   GFX\map\Props\205.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\ContDoorFrame.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\ContDoorFrame.x" Else WriteLine f,"ok   GFX\map\Props\ContDoorFrame.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\ElecBox.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\ElecBox.x" Else WriteLine f,"ok   GFX\map\Props\ElecBox.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\Tank1.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\Tank1.x" Else WriteLine f,"ok   GFX\map\Props\Tank1.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\Tank2.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\Tank2.x" Else WriteLine f,"ok   GFX\map\Props\Tank2.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\boxfile_a.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\boxfile_a.x" Else WriteLine f,"ok   GFX\map\Props\boxfile_a.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\boxfile_b.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\boxfile_b.x" Else WriteLine f,"ok   GFX\map\Props\boxfile_b.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\cabinet_a.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\cabinet_a.x" Else WriteLine f,"ok   GFX\map\Props\cabinet_a.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\cabinet_b.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\cabinet_b.x" Else WriteLine f,"ok   GFX\map\Props\cabinet_b.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\crate1.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\crate1.x" Else WriteLine f,"ok   GFX\map\Props\crate1.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\crate2.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\crate2.x" Else WriteLine f,"ok   GFX\map\Props\crate2.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\crate3.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\crate3.x" Else WriteLine f,"ok   GFX\map\Props\crate3.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\keyboard.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\keyboard.x" Else WriteLine f,"ok   GFX\map\Props\keyboard.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\lamp1.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\lamp1.x" Else WriteLine f,"ok   GFX\map\Props\lamp1.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\lamp2.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\lamp2.x" Else WriteLine f,"ok   GFX\map\Props\lamp2.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\lamp3.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\lamp3.x" Else WriteLine f,"ok   GFX\map\Props\lamp3.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\monitor.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\monitor.x" Else WriteLine f,"ok   GFX\map\Props\monitor.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\mug.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\mug.x" Else WriteLine f,"ok   GFX\map\Props\mug.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\Props\officeseat_a.x")
If m=0 Then WriteLine f,"FAIL GFX\map\Props\officeseat_a.x" Else WriteLine f,"ok   GFX\map\Props\officeseat_a.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\cam.x")
If m=0 Then WriteLine f,"FAIL GFX\map\cam.x" Else WriteLine f,"ok   GFX\map\cam.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\cambase.x")
If m=0 Then WriteLine f,"FAIL GFX\map\cambase.x" Else WriteLine f,"ok   GFX\map\cambase.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object0_cull.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object0_cull.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object0_cull.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object1.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object10.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object10.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object10.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object11.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object11.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object11.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object12.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object12.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object12.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object13.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object13.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object13.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object14.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object14.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object14.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object15.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object15.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object15.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object2.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object3.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object3.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object3.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object4.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object4.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object4.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object5.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object5.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object5.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object6.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object6.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object6.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object7.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object7.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object7.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object8.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object8.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object8.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499object9.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499object9.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499object9.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\dimension1499\1499plane.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\dimension1499\1499plane.b3d" Else WriteLine f,"ok   GFX\map\dimension1499\1499plane.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\elevatordoor.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\elevatordoor.b3d" Else WriteLine f,"ok   GFX\map\elevatordoor.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\exit1terrain.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\exit1terrain.b3d" Else WriteLine f,"ok   GFX\map\exit1terrain.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\fan.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\fan.b3d" Else WriteLine f,"ok   GFX\map\fan.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\detail\rock.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\detail\rock.b3d" Else WriteLine f,"ok   GFX\map\forest\detail\rock.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\detail\rock2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\detail\rock2.b3d" Else WriteLine f,"ok   GFX\map\forest\detail\rock2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\detail\treetest4.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\detail\treetest4.b3d" Else WriteLine f,"ok   GFX\map\forest\detail\treetest4.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\detail\treetest5.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\detail\treetest5.b3d" Else WriteLine f,"ok   GFX\map\forest\detail\treetest5.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\door.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\door.b3d" Else WriteLine f,"ok   GFX\map\forest\door.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\door_frame.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\door_frame.b3d" Else WriteLine f,"ok   GFX\map\forest\door_frame.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\forest\wall.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\forest\wall.b3d" Else WriteLine f,"ok   GFX\map\forest\wall.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\gatea_hitbox1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\gatea_hitbox1.b3d" Else WriteLine f,"ok   GFX\map\gatea_hitbox1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\gateatunnel.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\gateatunnel.b3d" Else WriteLine f,"ok   GFX\map\gateatunnel.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\gateawall1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\gateawall1.b3d" Else WriteLine f,"ok   GFX\map\gateawall1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\gateawall2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\gateawall2.b3d" Else WriteLine f,"ok   GFX\map\gateawall2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\heavydoor1.x")
If m=0 Then WriteLine f,"FAIL GFX\map\heavydoor1.x" Else WriteLine f,"ok   GFX\map\heavydoor1.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\heavydoor2.x")
If m=0 Then WriteLine f,"FAIL GFX\map\heavydoor2.x" Else WriteLine f,"ok   GFX\map\heavydoor2.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\intro_labels.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\intro_labels.b3d" Else WriteLine f,"ok   GFX\map\intro_labels.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\leverbase.x")
If m=0 Then WriteLine f,"FAIL GFX\map\leverbase.x" Else WriteLine f,"ok   GFX\map\leverbase.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\leverhandle.x")
If m=0 Then WriteLine f,"FAIL GFX\map\leverhandle.x" Else WriteLine f,"ok   GFX\map\leverhandle.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\lightgun.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\lightgun.b3d" Else WriteLine f,"ok   GFX\map\lightgun.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\lightgunbase.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\lightgunbase.b3d" Else WriteLine f,"ok   GFX\map\lightgunbase.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\medibay_props.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\medibay_props.b3d" Else WriteLine f,"ok   GFX\map\medibay_props.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\monitor.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\monitor.b3d" Else WriteLine f,"ok   GFX\map\monitor.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\monitor.x")
If m=0 Then WriteLine f,"FAIL GFX\map\monitor.x" Else WriteLine f,"ok   GFX\map\monitor.x"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\monitor_checkpoint.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\monitor_checkpoint.b3d" Else WriteLine f,"ok   GFX\map\monitor_checkpoint.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\pocketdimension2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\pocketdimension2.b3d" Else WriteLine f,"ok   GFX\map\pocketdimension2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\pocketdimension3.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\pocketdimension3.b3d" Else WriteLine f,"ok   GFX\map\pocketdimension3.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\pocketdimension4.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\pocketdimension4.b3d" Else WriteLine f,"ok   GFX\map\pocketdimension4.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\pocketdimension5.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\pocketdimension5.b3d" Else WriteLine f,"ok   GFX\map\pocketdimension5.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\pocketdimensionterrain.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\pocketdimensionterrain.b3d" Else WriteLine f,"ok   GFX\map\pocketdimensionterrain.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room012_2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room012_2.b3d" Else WriteLine f,"ok   GFX\map\room012_2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room012_3.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room012_3.b3d" Else WriteLine f,"ok   GFX\map\room012_3.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room049_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room049_hb.b3d" Else WriteLine f,"ok   GFX\map\room049_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room1062.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room1062.b3d" Else WriteLine f,"ok   GFX\map\room1062.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room2gw_pipes.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room2gw_pipes.b3d" Else WriteLine f,"ok   GFX\map\room2gw_pipes.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room2tesla_caution.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room2tesla_caution.b3d" Else WriteLine f,"ok   GFX\map\room2tesla_caution.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room3gw_pipes.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room3gw_pipes.b3d" Else WriteLine f,"ok   GFX\map\room3gw_pipes.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room3offices_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room3offices_hb.b3d" Else WriteLine f,"ok   GFX\map\room3offices_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room3storage_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room3storage_hb.b3d" Else WriteLine f,"ok   GFX\map\room3storage_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\map\room3z2_hb.b3d")
If m=0 Then WriteLine f,"FAIL GFX\map\room3z2_hb.b3d" Else WriteLine f,"ok   GFX\map\room3z2_hb.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\035.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\035.b3d" Else WriteLine f,"ok   GFX\npcs\035.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\035tentacle.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\035tentacle.b3d" Else WriteLine f,"ok   GFX\npcs\035tentacle.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\106_2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\106_2.b3d" Else WriteLine f,"ok   GFX\npcs\106_2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\1499-1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\1499-1.b3d" Else WriteLine f,"ok   GFX\npcs\1499-1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\173_2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\173_2.b3d" Else WriteLine f,"ok   GFX\npcs\173_2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\205_demon1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\205_demon1.b3d" Else WriteLine f,"ok   GFX\npcs\205_demon1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\205_demon2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\205_demon2.b3d" Else WriteLine f,"ok   GFX\npcs\205_demon2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\205_demon3.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\205_demon3.b3d" Else WriteLine f,"ok   GFX\npcs\205_demon3.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\205_woman.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\205_woman.b3d" Else WriteLine f,"ok   GFX\npcs\205_woman.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\372.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\372.b3d" Else WriteLine f,"ok   GFX\npcs\372.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\682arm.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\682arm.b3d" Else WriteLine f,"ok   GFX\npcs\682arm.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\MTF2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\MTF2.b3d" Else WriteLine f,"ok   GFX\npcs\MTF2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\bll.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\bll.b3d" Else WriteLine f,"ok   GFX\npcs\bll.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\classd.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\classd.b3d" Else WriteLine f,"ok   GFX\npcs\classd.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\clerk.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\clerk.b3d" Else WriteLine f,"ok   GFX\npcs\clerk.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\duck_low_res.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\duck_low_res.b3d" Else WriteLine f,"ok   GFX\npcs\duck_low_res.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\forestmonster.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\forestmonster.b3d" Else WriteLine f,"ok   GFX\npcs\forestmonster.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\guard.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\guard.b3d" Else WriteLine f,"ok   GFX\npcs\guard.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\naziofficer.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\naziofficer.b3d" Else WriteLine f,"ok   GFX\npcs\naziofficer.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\s2.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\s2.b3d" Else WriteLine f,"ok   GFX\npcs\s2.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-049.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-049.b3d" Else WriteLine f,"ok   GFX\npcs\scp-049.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-066.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-066.b3d" Else WriteLine f,"ok   GFX\npcs\scp-066.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-1048.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-1048.b3d" Else WriteLine f,"ok   GFX\npcs\scp-1048.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-1048a.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-1048a.b3d" Else WriteLine f,"ok   GFX\npcs\scp-1048a.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-1048pp.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-1048pp.b3d" Else WriteLine f,"ok   GFX\npcs\scp-1048pp.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-939.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-939.b3d" Else WriteLine f,"ok   GFX\npcs\scp-939.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp-966.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp-966.b3d" Else WriteLine f,"ok   GFX\npcs\scp-966.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\scp096.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\scp096.b3d" Else WriteLine f,"ok   GFX\npcs\scp096.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\zombie1.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\zombie1.b3d" Else WriteLine f,"ok   GFX\npcs\zombie1.b3d"
If m<>0 Then FreeEntity m
m=LoadMesh("C:\Users\Admin\Documents\scpcb-nx\scpcb\GFX\npcs\zombiesurgeon.b3d")
If m=0 Then WriteLine f,"FAIL GFX\npcs\zombiesurgeon.b3d" Else WriteLine f,"ok   GFX\npcs\zombiesurgeon.b3d"
If m<>0 Then FreeEntity m
CloseFile f
End
