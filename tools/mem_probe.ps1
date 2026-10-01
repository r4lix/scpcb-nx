# Run the game and print its memory every few seconds (find leaks).
# usage: powershell -File tools\mem_probe.ps1 [-Seconds 120] [-Every 10]   (env vars set by the caller apply)
param([int]$Seconds = 120, [int]$Every = 10, [string]$Inject = "")
$root = Split-Path -Parent $PSScriptRoot
$env:blitzpath = "$root\blitz3d-ng\_release"
$env:PATH = "$env:blitzpath\bin;$env:PATH"
$env:BB_TRACE_ERRORS = "1"
if ($Inject) { $env:BB_INJECT = $Inject }
Copy-Item "$root\tools\run_options.ini" "$root\run\options.ini" -Force
Set-Location "$root\run"
$p = Start-Process -FilePath "$env:blitzpath\bin\blitzcc64.exe" -ArgumentList "-q","-r","opengl","Main.bb" -PassThru -RedirectStandardError "$env:TEMP\probe_err.txt"
$t = 0
while ($t -lt $Seconds -and -not $p.HasExited) {
  Start-Sleep $Every; $t += $Every
  try { $p.Refresh(); "t={0,4}s  working set {1,6} MB  private {2,6} MB" -f $t, [int]($p.WorkingSet64/1MB), [int]($p.PrivateMemorySize64/1MB) } catch {}
}
if (-not $p.HasExited) { $p.Kill() }
