# CMake toolchain for Nintendo Switch (devkitA64) usable from a Windows-hosted CMake.
# devkitPro's own Switch.cmake insists on running inside msys2, whose paths the native
# Windows compilers cannot read in Unity-build files, so this reproduces its settings.

set(CMAKE_SYSTEM_NAME Generic)
set(CMAKE_SYSTEM_PROCESSOR aarch64)

set(DEVKITPRO "C:/devkitPro" CACHE PATH "")
set(DKA64 "${DEVKITPRO}/devkitA64")

set(CMAKE_C_COMPILER   "${DKA64}/bin/aarch64-none-elf-gcc.exe")
set(CMAKE_CXX_COMPILER "${DKA64}/bin/aarch64-none-elf-g++.exe")
set(CMAKE_ASM_COMPILER "${DKA64}/bin/aarch64-none-elf-gcc.exe")
set(CMAKE_AR           "${DKA64}/bin/aarch64-none-elf-gcc-ar.exe")
set(CMAKE_RANLIB       "${DKA64}/bin/aarch64-none-elf-gcc-ranlib.exe")
set(CMAKE_TRY_COMPILE_TARGET_TYPE STATIC_LIBRARY)

set(NX_ARCH "-march=armv8-a+crc+crypto -mtune=cortex-a57 -mtp=soft -fPIE")
set(NX_DEFS "-D__SWITCH__ -DSWITCH")
set(NX_INCS "-isystem ${DEVKITPRO}/libnx/include -isystem ${DEVKITPRO}/portlibs/switch/include")
set(CMAKE_C_FLAGS_INIT   "${NX_ARCH} ${NX_DEFS} ${NX_INCS} -ffunction-sections -fdata-sections")
set(CMAKE_CXX_FLAGS_INIT "${NX_ARCH} ${NX_DEFS} ${NX_INCS} -ffunction-sections -fdata-sections")
set(CMAKE_ASM_FLAGS_INIT "${NX_ARCH}")

set(CMAKE_FIND_ROOT_PATH ${DEVKITPRO}/portlibs/switch ${DEVKITPRO}/libnx ${DKA64}/aarch64-none-elf)
set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY)
set(CMAKE_FIND_ROOT_PATH_MODE_PACKAGE ONLY)
