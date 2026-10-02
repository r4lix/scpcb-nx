# Two-instance co-op test on the PC: a host in run\ and a guest in run2\ (both prepared with
# prepare_run.py, switch_settings.ini with mp_mode=1 / mp_mode=2). Saves screenshots from both.
#   powershell -File tools\mp_test.ps1 [-Seconds 420]
param([int]$Seconds = 420)
$root = Split-Path -Parent $PSScriptRoot
$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:BB_TRACE_ERRORS = "1"
$flow = "70000:key:40;80000:move:250,224;80500:click:1;90000:move:315,348;90500:click:1;100000:move:463,534;100500:click:1;300000:key:40;310000:key:40;320000:key:40"
$procs = @()
foreach ($d in "run", "run2") {
    Get-ChildItem "$root\tests\mp_${d}_*" -ErrorAction SilentlyContinue | ForEach-Object { [System.IO.File]::Delete($_.FullName) }
    Copy-Item "$root\tools\run_options.ini" "$root\$d\options.ini" -Force
    $env:BB_INJECT = $flow
    $env:BB_SCREENSHOT_PATH = "$root\tests\mp_${d}"
    $env:BB_SCREENSHOT_EVERY_MS = "20000"
    $procs += Start-Process -FilePath "$env:blitzpath\bin\blitzcc64.exe" -ArgumentList "-q", "-r", "opengl", "Main.bb" -WorkingDirectory "$root\$d" -PassThru -RedirectStandardError "$env:TEMP\mp_$d.txt"
}
$deadline = (Get-Date).AddSeconds($Seconds)
while ((Get-Date) -lt $deadline -and ($procs | Where-Object { -not $_.HasExited })) { Start-Sleep 5 }
foreach ($p in $procs) { if (-not $p.HasExited) { $p.Kill() } }
Add-Type -AssemblyName System.Drawing
Get-ChildItem "$root\tests\mp_*.bmp" | ForEach-Object {
    $b = [System.Drawing.Image]::FromFile($_.FullName); $b.Save($_.FullName.Replace('.bmp', '.png'), [System.Drawing.Imaging.ImageFormat]::Png); $b.Dispose(); [System.IO.File]::Delete($_.FullName); $_.Name.Replace('.bmp', '.png')
}
