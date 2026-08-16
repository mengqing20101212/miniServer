param([switch]$SkipRuntime)
. "$PSScriptRoot\common.ps1"

Assert-LocalSettings
$java = Get-JavaExecutable
Assert-Java25 $java

if (-not $SkipRuntime) {
    foreach ($file in @('game-server.jar', 'config-builder.jar')) {
        $path = Join-Path $script:RuntimeDir $file
        if (-not (Test-Path -LiteralPath $path)) { throw "Missing runtime artifact: $path" }
    }
    $configDir = Join-Path $script:ProjectRoot 'excel\serverConfig'
    if (-not (Test-Path -LiteralPath $configDir)) { throw "Missing config directory: $configDir" }
}

$nacosHost = ($env:NACOS_URL -replace '^https?://', '').Split(':')[0]
$nacosPort = if (($env:NACOS_URL -replace '^https?://', '') -match ':(\d+)$') { [int]$Matches[1] } else { 8848 }
$reachable = Test-NetConnection -ComputerName $nacosHost -Port $nacosPort -InformationLevel Quiet -WarningAction SilentlyContinue
if (-not $reachable) { throw "Nacos is unreachable: $($env:NACOS_URL)" }
Write-Host "OK: Java 25, settings, and Nacos connectivity. serverId=$($env:LOCAL_GAME_ID)"
