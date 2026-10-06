# Build scpcb-ue.nro (SCP:CB Ultimate Edition Reborn 2.2 Beta) for the Nintendo Switch.
#
#   powershell -File tools\build_switch_ue.ps1 [-Src ..\scpcb-ue-22]
#
# Needs the blitz3d-ng compiler and the Switch runtime libraries (tools\build-nx.bat) built.
# The game files (Data, GFX, SFX, Localization) come from "SCP 2.2 Beta Public Build"; they are not copied here.
param([string]$Out = "", [string]$Src = "")
$root = Split-Path -Parent $PSScriptRoot
if (-not $Out) { $Out = "$root\build\switch_ue" }
if (-not $Src) { $Src = "$root\scpcb-ue" }
if (-not (Test-Path $Src)) { $Src = "$root\..\scpcb-ue-22" }

$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:DEVKITPRO = "C:/devkitPro"

$py = if (Get-Command py -ErrorAction SilentlyContinue) { "py" } else { "python" }
& $py -3 "$root\tools\prepare_ue.py" --out $Out --src $Src --no-assets
if ($LASTEXITCODE -ne 0) { throw "prepare_ue failed" }

Set-Location $Out
[System.IO.File]::Delete("$Out\scpcb-ue.nro")
& "$env:blitzpath\bin\blitzcc64.exe" -ns -llvm -target nx -o scpcb-ue.nro Game.bb 2>&1 |
    Where-Object { $_ -notmatch '^[%@;]|^\s|^$|^\!|^define|^declare|^\}' } | Select-Object -Last 30
if (-not (Test-Path "$Out\scpcb-ue.nro")) { throw "scpcb-ue.nro was not produced" }
"{0}  {1:N1} MB" -f "$Out\scpcb-ue.nro", ((Get-Item "$Out\scpcb-ue.nro").Length / 1MB)
