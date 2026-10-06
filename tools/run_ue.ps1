# Run UE Reborn from run_ue/ through the blitz3d-ng JIT and collect screenshots.
# Usage: powershell -File tools\run_ue.ps1 [-Seconds 90] [-Inject "45000:key:44"] [-EveryMs 5000]
param([int]$Seconds = 90, [string]$Inject = "", [int]$EveryMs = 5000, [string]$Options = "ue_options.ini")
$root = Split-Path -Parent $PSScriptRoot
$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:BB_TRACE_ERRORS = "1"
$env:BB_NOWARP = "1"
$env:BB_LOS_STATS = "1"
$env:BB_SCREENSHOT_PATH = "$root\tests\ueshot"
$env:BB_SCREENSHOT_EVERY_MS = "$EveryMs"
if ($Inject) { $env:BB_INJECT = $Inject }
Get-ChildItem "$root\tests\ueshot_*" -ErrorAction SilentlyContinue | ForEach-Object { [System.IO.File]::Delete($_.FullName) }
# the game rewrites options.ini and a forced kill can truncate it: always start from a known-good copy
New-Item -ItemType Directory -Force "$root\run_ue\UserData\scpcb-ue\Data" | Out-Null
Copy-Item "$root\tools\$Options" "$root\run_ue\UserData\scpcb-ue\Data\options.ini" -Force
Set-Location "$root\run_ue"
$err = "$env:TEMP\ue_err.txt"
$p = Start-Process -FilePath "$env:blitzpath\bin\blitzcc64.exe" -ArgumentList "-q","-ns","-r","opengl","Game.bb" -PassThru -RedirectStandardError $err
$deadline = (Get-Date).AddSeconds($Seconds)
$peak = 0
while ((Get-Date) -lt $deadline -and -not $p.HasExited) { Start-Sleep 2; try { $p.Refresh(); if ($p.WorkingSet64 -gt $peak) { $peak = $p.WorkingSet64 } } catch {} }
"peak memory: $([int]($peak/1MB)) MB"
if ($p.HasExited) { $p.WaitForExit(); "exited code $($p.ExitCode)" } else { "still running after ${Seconds}s"; $p.Kill() }
Get-Content $err | Where-Object { $_ -match "bbEx|fb error|watchdog|exception|^  [A-Za-z0-9_.]+!" } | Select-Object -First 60
"--- stderr tail ---"
Get-Content $err -Tail 8
Add-Type -AssemblyName System.Drawing
Get-ChildItem "$root\tests\ueshot_*.bmp" | ForEach-Object {
  $b = [System.Drawing.Image]::FromFile($_.FullName); $b.Save($_.FullName.Replace('.bmp','.png'), [System.Drawing.Imaging.ImageFormat]::Png); $b.Dispose(); [System.IO.File]::Delete($_.FullName); $_.Name.Replace('.bmp','.png')
}
