# Build scpcb.nro for the Nintendo Switch.
#
#   powershell -File tools\build_switch.ps1            -> build\switch\scpcb.nro
#   powershell -File tools\build_switch.ps1 -Sd E:     -> also copy the game to E:\switch\scpcb\
#
# Needs the blitz3d-ng compiler and the Switch runtime libraries (tools\build-nx.bat) built.
param([string]$Out = "", [string]$Sd = "")
$root = Split-Path -Parent $PSScriptRoot
if (-not $Out) { $Out = "$root\build\switch" }

$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:DEVKITPRO = "C:/devkitPro"

$py = if (Get-Command py -ErrorAction SilentlyContinue) { "py" } else { "python" }
& $py -3 "$root\tools\prepare_run.py" --out $Out --no-assets
if ($LASTEXITCODE -ne 0) { throw "prepare_run failed" }

Set-Location $Out
[System.IO.File]::Delete("$Out\scpcb.nro")
& "$env:blitzpath\bin\blitzcc64.exe" -llvm -target nx -o scpcb.nro Main.bb 2>&1 |
    Where-Object { $_ -notmatch '^[%@;]|^\s|^$|^\!|^define|^declare|^\}' } | Select-Object -Last 30
if (-not (Test-Path "$Out\scpcb.nro")) { throw "scpcb.nro was not produced" }
"{0}  {1:N1} MB" -f "$Out\scpcb.nro", ((Get-Item "$Out\scpcb.nro").Length / 1MB)

if ($Sd) {
    $dest = "$Sd\switch\scpcb"
    New-Item -ItemType Directory -Force $dest | Out-Null
    Copy-Item "$Out\scpcb.nro" "$dest\scpcb.nro" -Force
    Copy-Item "$root\tools\switch_options.ini" "$dest\options.ini" -Force
    foreach ($d in "Data", "GFX", "SFX", "Loadingscreens") {
        robocopy "$root\scpcb\$d" "$dest\$d" /E /NFL /NDL /NJH /NJS /NP | Out-Null
    }
    "copied to $dest"
}
