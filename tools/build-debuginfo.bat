@echo off
call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat" || exit /b 1
set PATH=C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin;C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\IDE\CommonExtensions\Microsoft\CMake\Ninja;%PATH%
cmake -G Ninja -S . -B build\win64-release -DARCH=x86_64 -DBB_PLATFORM=win64 -DBB_ENV=release -UCMAKE_CXX_FLAGS -UCMAKE_C_FLAGS "-DCMAKE_CXX_FLAGS_RELEASE=/O2 /Ob2 /DNDEBUG /Zi /FS" "-DCMAKE_C_FLAGS_RELEASE=/O2 /Ob2 /DNDEBUG /Zi /FS" "-DCMAKE_SHARED_LINKER_FLAGS=/DEBUG" || exit /b 1
cmake --build build\win64-release --target %1 2>&1
