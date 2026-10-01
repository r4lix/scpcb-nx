# Run SCP:CB from run/ through the blitz3d-ng JIT and collect screenshots.
# Usage: powershell -File tools\run_game.ps1 [-Seconds 90] [-Inject "45000:key:44"] [-EveryMs 5000]
param([int]$Seconds = 90, [string]$Inject = "", [int]$EveryMs = 5000, [string]$Shots = "")
$root = Split-Path -Parent $PSScriptRoot
$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:BB_TRACE_ERRORS = "1"
$env:BB_SCREENSHOT_PATH = "$root\tests\shot"
$env:BB_SCREENSHOT_EVERY_MS = "$EveryMs"
if ($Inject) { $env:BB_INJECT = $Inject }
Get-ChildItem "$root\tests\shot_*" -ErrorAction SilentlyContinue | ForEach-Object { [System.IO.File]::Delete($_.FullName) }
# the game rewrites options.ini and a forced kill can truncate it: always start from a known-good copy
Copy-Item "$root\tools\run_options.ini" "$root\run\options.ini" -Force
Set-Location "$root\run"
$err = "$env:TEMP\scp_err.txt"
$p = Start-Process -FilePath "$env:blitzpath\bin\blitzcc64.exe" -ArgumentList "-q","-r","opengl","Main.bb" -PassThru -RedirectStandardError $err
$deadline = (Get-Date).AddSeconds($Seconds)
$peak = 0
while ((Get-Date) -lt $deadline -and -not $p.HasExited) { Start-Sleep 2; try { $p.Refresh(); if ($p.WorkingSet64 -gt $peak) { $peak = $p.WorkingSet64 } } catch {} }
"peak memory: $([int]($peak/1MB)) MB"
if ($p.HasExited) { $p.WaitForExit(); "exited code $($p.ExitCode)" } else { "still running after ${Seconds}s"; $p.Kill() }
Get-Content $err | Where-Object { $_ -match "bbEx|fb error|watchdog|exception|^  [A-Za-z0-9_.]+!" } | Select-Object -First 60
"--- stderr tail ---"
Get-Content $err -Tail 6
Add-Type -AssemblyName System.Drawing
Get-ChildItem "$root\tests\shot_*.bmp" | ForEach-Object {
  $b = [System.Drawing.Image]::FromFile($_.FullName); $b.Save($_.FullName.Replace('.bmp','.png'), [System.Drawing.Imaging.ImageFormat]::Png); $b.Dispose(); [System.IO.File]::Delete($_.FullName); $_.Name.Replace('.bmp','.png')
}
