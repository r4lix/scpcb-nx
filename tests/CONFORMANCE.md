# Language/runtime conformance checks

Small Blitz programs that print `ok`/`FAIL` lines through `RT_Trace` (stderr). They found real
runtime bugs while porting (e.g. `Right$(s,0)` returning one character, `WriteFloat` writing the
wrong bytes on 64-bit targets), so run them after touching the runtime:

    blitzcc64 -q -r opengl tests/conformance_strings_math.bb
    blitzcc64 -q -r opengl tests/conformance_runtime.bb

`rowtext_repro.bb` is the game's `RowText` word wrapper with the drawing replaced by tracing; it
used to fill the loading screens with rows of dots.
Run from a scratch directory: the file tests create and delete `t4*` temporary files.
