$ErrorActionPreference = 'Stop'

$script:LocalDevDir = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
$script:ProjectRoot = (Resolve-Path (Join-Path $script:LocalDevDir '..')).Path
$script:RuntimeDir = Join-Path $script:LocalDevDir 'runtime'
$script:RunDir = Join-Path $script:LocalDevDir 'run'
$script:LogDir = Join-Path $script:LocalDevDir 'logs'
$script:WorkDir = Join-Path $script:LocalDevDir 'work'

function Import-LocalEnv {
    $envFile = Join-Path $script:LocalDevDir '.env'
    if (-not (Test-Path -LiteralPath $envFile)) {
        throw "Missing $envFile. Run setup.ps1 first."
    }
    foreach ($line in Get-Content -LiteralPath $envFile -Encoding UTF8) {
        $trimmed = $line.Trim()
        if (-not $trimmed -or $trimmed.StartsWith('#')) { continue }
        $parts = $trimmed.Split('=', 2)
        if ($parts.Count -eq 2) {
            [Environment]::SetEnvironmentVariable($parts[0].Trim(), $parts[1].Trim(), 'Process')
        }
    }
}

function Get-JavaExecutable {
    if ($env:JAVA_HOME) {
        $candidate = Join-Path $env:JAVA_HOME 'bin\java.exe'
        if (Test-Path -LiteralPath $candidate) { return $candidate }
    }
    $command = Get-Command java.exe -ErrorAction SilentlyContinue
    if ($command) { return $command.Source }
    throw 'JDK 25 was not found. Set JAVA_HOME in local-dev/.env.'
}

function Assert-Java25([string]$Java) {
    $version = (& $Java -version 2>&1 | Select-Object -First 1) -join ''
    if ($version -notmatch 'version "25(?:\.|"|-)') {
        throw "JDK 25 is required, current: $version"
    }
}

function Assert-LocalSettings {
    Import-LocalEnv
    if ($env:LOCAL_GAME_ID -notmatch '^game-local-[a-zA-Z0-9_-]+$') {
        throw 'LOCAL_GAME_ID must be the game-local-* id assigned by a server developer.'
    }
    if (-not $env:NACOS_URL -or -not $env:NACOS_NAMESPACE) {
        throw 'NACOS_URL and NACOS_NAMESPACE are required.'
    }
}
