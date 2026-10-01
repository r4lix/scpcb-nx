@echo off
rem Cross-build the blitz3d-ng runtime libraries for Nintendo Switch (devkitA64).
rem usage (from blitz3d-ng\): ..\tools\build-nx.bat <ninja target>
set DEVKITPRO=C:/devkitPro
set DEVKITA64=C:/devkitPro/devkitA64
set PATH=C:\devkitPro\devkitA64\bin;C:\devkitPro\tools\bin;C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\IDE\CommonExtensions\Microsoft\CMake\CMake\bin;C:\Program Files\Microsoft Visual Studio\2022\Community\Common7\IDE\CommonExtensions\Microsoft\CMake\Ninja;%PATH%
cmake -G Ninja -S . -B build\aarch64-nx-release -DDEVKITPRO=C:/devkitPro -DCMAKE_TOOLCHAIN_FILE=%~dp0switch-toolchain.cmake -DBB_PLATFORM=nx -DBB_ENV=release -DARCH=aarch64 -DCMAKE_POLICY_VERSION_MINIMUM=3.5 || exit /b 1
cmake --build build\aarch64-nx-release --target %1 -- -k 0 2>&1
