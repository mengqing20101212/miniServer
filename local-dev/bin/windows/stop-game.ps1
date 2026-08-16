. "$PSScriptRoot\common.ps1"
$pidFile = Join-Path $script:RunDir 'game.pid'
if (-not (Test-Path -LiteralPath $pidFile)) { Write-Host 'GameServer is not running.'; exit 0 }
$gamePid = [int](Get-Content -LiteralPath $pidFile -Raw)
$process = Get-CimInstance Win32_Process -Filter "ProcessId=$gamePid" -ErrorAction SilentlyContinue
if (-not $process) { Remove-Item -LiteralPath $pidFile -Force; Write-Host 'Removed stale PID file.'; exit 0 }
if ($process.CommandLine -notlike '*game-server.jar*') { throw "PID $gamePid is not this GameServer; refusing to stop it." }
Stop-Process -Id $gamePid
Wait-Process -Id $gamePid -Timeout 15 -ErrorAction SilentlyContinue
if (Get-Process -Id $gamePid -ErrorAction SilentlyContinue) { Stop-Process -Id $gamePid -Force }
Remove-Item -LiteralPath $pidFile -Force
Write-Host "GameServer stopped. pid=$gamePid"
