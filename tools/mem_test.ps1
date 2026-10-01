param([string]$File)
$root = Split-Path -Parent $PSScriptRoot
$env:blitzpath = "$root\blitz3d-ng\_release"; $env:PATH = "$env:blitzpath\bin;$env:PATH"
Set-Location "$root\tests"
$p = Start-Process -FilePath "$env:blitzpath\bin\blitzcc64.exe" -ArgumentList "-q","-r","opengl",$File -PassThru -WindowStyle Hidden
$first = $null; $last = 0
while (-not $p.HasExited) { Start-Sleep -Milliseconds 700; try { $p.Refresh(); $m = [int]($p.WorkingSet64/1MB); if ($first -eq $null) { $first = $m }; $last = [Math]::Max($last,$m) } catch {} }
"{0}: start {1} MB -> peak {2} MB" -f $File, $first, $last
