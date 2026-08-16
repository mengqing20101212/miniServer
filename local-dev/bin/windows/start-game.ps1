. "$PSScriptRoot\common.ps1"
& "$PSScriptRoot\doctor.ps1"
Assert-LocalSettings
$java = Get-JavaExecutable
New-Item -ItemType Directory -Force -Path $script:RunDir, $script:LogDir | Out-Null

$pidFile = Join-Path $script:RunDir 'game.pid'
if (Test-Path -LiteralPath $pidFile) {
    $oldPid = [int](Get-Content -LiteralPath $pidFile -Raw)
    if (Get-Process -Id $oldPid -ErrorAction SilentlyContinue) { throw "GameServer is already running, pid=$oldPid" }
    Remove-Item -LiteralPath $pidFile -Force
}

$jar = Join-Path $script:RuntimeDir 'game-server.jar'
$logBase = Join-Path $script:LogDir ("game-{0}" -f (Get-Date -Format 'yyyyMMdd-HHmmss'))
$outLog = "$logBase.out.log"
$errLog = "$logBase.err.log"
$jvmArgs = @('-Dminiserver.localConfig=true', "-Dminiserver.projectRoot=$($script:ProjectRoot)")
if ($env:JAVA_OPTS) { $jvmArgs += $env:JAVA_OPTS.Split(' ', [System.StringSplitOptions]::RemoveEmptyEntries) }
$arguments = $jvmArgs + @('-jar', $jar, $env:NACOS_URL, $env:NACOS_NAMESPACE, $env:LOCAL_GAME_ID)
$process = Start-Process -FilePath $java -ArgumentList $arguments -WorkingDirectory $script:ProjectRoot -RedirectStandardOutput $outLog -RedirectStandardError $errLog -PassThru -WindowStyle Hidden
Set-Content -LiteralPath $pidFile -Value $process.Id -Encoding ASCII
Start-Sleep -Seconds 3
if ($process.HasExited) { throw "GameServer exited during startup. See $outLog and $errLog" }
Write-Host "GameServer started. pid=$($process.Id), log=$outLog"
