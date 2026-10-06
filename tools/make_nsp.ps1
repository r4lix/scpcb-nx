# Build an installable NSP (build\nsp\nsp\*.nsp) of scpcb for Cyberfoil / Tinfoil / DBI.
#
#   powershell -File tools\make_nsp.ps1 -Keys "C:\path\prod.keys"
#
# The game files (Data, GFX, SFX, Loadingscreens) are packed into the title's romfs; the program is
# the ELF behind build\switch\scpcb.nro. Saves and options.ini live in device save data (see
# tools\nsp\README.md). Needs devkitPro (elf2nso, npdmtool, nacptool) and your own prod.keys.
param(
    [Parameter(Mandatory)][string]$Keys,
    [string]$Elf  = "",   # default: build\scpcb.elf
    [string]$Game = "",   # default: build\sdcard\switch\scpcb  (a finished SD card layout of the game)
    [string]$Out  = "",   # default: build\nsp
    [string]$Version = "0.1.0"
)
$root = Split-Path -Parent $PSScriptRoot
if (-not $Elf)  { $Elf  = "$root\build\scpcb.elf" }
if (-not $Game) { $Game = "$root\build\sdcard\switch\scpcb" }
if (-not $Out)  { $Out  = "$root\build\nsp" }
foreach ($p in $Elf, $Game, $Keys) { if (-not (Test-Path $p)) { throw "missing: $p" } }

# romfs staging: junctions to the read-only game folders (nothing is copied), plus the stock options.ini
# that the game starts from until it writes its own into the save.
$stage = "$Out\romfs"
New-Item -ItemType Directory -Force $stage | Out-Null
foreach ($d in "Data", "GFX", "SFX", "Loadingscreens") {
    if (Test-Path "$stage\$d") { (Get-Item "$stage\$d").Delete() }
    New-Item -ItemType Junction -Path "$stage\$d" -Target "$Game\$d" | Out-Null
}
Copy-Item "$root\tools\switch_options.ini" "$stage\options.ini" -Force

$bash = "C:\devkitPro\msys2\usr\bin\bash.exe"
function ToMsys($p) { $p = (Resolve-Path $p).Path -replace '\\', '/'; $p -replace '^([A-Za-z]):', { '/' + $_.Groups[1].Value.ToLower() } }
$cmd = "export DEVKITPRO=/opt/devkitpro; export PATH=/opt/devkitpro/tools/bin:`$PATH; " +
       "python3 '$(ToMsys "$root\tools\nsp\mknsp.py")' --elf '$(ToMsys $Elf)' --romfs '$(ToMsys $stage)' " +
       "--icon '$(ToMsys "$root\tools\nsp\icon.jpg")' --keys '$(ToMsys $Keys)' --out '$(ToMsys $Out)' --version '$Version'"
& $bash -lc $cmd
if ($LASTEXITCODE -ne 0) { throw "mknsp failed" }
