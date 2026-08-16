. "$PSScriptRoot\common.ps1"

New-Item -ItemType Directory -Force -Path $script:RunDir, $script:LogDir, $script:WorkDir | Out-Null
$envFile = Join-Path $script:LocalDevDir '.env'
if (-not (Test-Path -LiteralPath $envFile)) {
    Copy-Item -LiteralPath (Join-Path $script:LocalDevDir 'example.env') -Destination $envFile
    Write-Host "Created $envFile. Fill in the server id assigned by a server developer."
}
& "$PSScriptRoot\doctor.ps1" -SkipRuntime
