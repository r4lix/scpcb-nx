#!/usr/bin/env python3
"""One-off: replace SDLRuntime::idle() in runtime.sdl.cpp with input_delivery.inc and hook
moveMouse / setPointerVisible. (Kept for reference; the result is committed.)"""
import os

root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
d = os.path.join(root, "blitz3d-ng", "src", "modules", "bb", "runtime.sdl")
p = os.path.join(d, "runtime.sdl.cpp")
s = open(p, newline="").read().replace("\r\n", "\n")
new = open(os.path.join(d, "input_delivery.inc"), newline="").read().replace("\r\n", "\n")

start = s.index("bool SDLRuntime::idle(){")
end = s.index("void *SDLRuntime::window(){")
s = s[:start] + '#include "input_delivery.inc"\n\n' + s[end:]

a = """void SDLRuntime::moveMouse( int x,int y ){
	if( !bbContextDriver ) return;
	auto graphics=(SDLGraphics*)((SDLContextDriver*)bbContextDriver)->getGraphics();
	graphics->moveMouse( x,y );
}"""
assert a in s
s = s.replace(a, """void SDLRuntime::moveMouse( int x,int y ){
	if( !bbContextDriver ) return;
	auto graphics=(SDLGraphics*)((SDLContextDriver*)bbContextDriver)->getGraphics();
	graphics->moveMouse( x,y );
	// SDL cannot warp a pointer that does not exist (Switch), so tell the engine directly
	padEmulation.pointerMoved( x,y );
	deliverMouseMove( x,y );
}""", 1)

a = """void SDLRuntime::setPointerVisible( bool vis ){
	SDL_ShowCursor( vis?SDL_ENABLE:SDL_DISABLE );
}"""
assert a in s
s = s.replace(a, """extern bool bbPointerVisible; // graphics: draws a software cursor when one is needed

void SDLRuntime::setPointerVisible( bool vis ){
	bbPointerVisible=vis;
	SDL_ShowCursor( vis?SDL_ENABLE:SDL_DISABLE );
}""", 1)

if "#include <cmath>" not in s:
    s = s.replace("#include <vector>", "#include <vector>\n#include <cmath>\n#include <algorithm>", 1)
open(p, "w", newline="").write(s)
print("spliced")
