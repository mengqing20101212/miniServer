. "$PSScriptRoot\common.ps1"
Assert-LocalSettings
$java = Get-JavaExecutable
Assert-Java25 $java

$builder = Join-Path $script:RuntimeDir 'config-builder.jar'
if (-not (Test-Path -LiteralPath $builder)) { throw "Missing $builder" }
$excelDir = Join-Path $script:ProjectRoot 'excel'
$target = Join-Path $excelDir 'serverConfig'
$staging = Join-Path $script:WorkDir 'serverConfig-staging'
$backup = Join-Path $script:WorkDir 'serverConfig-backup'
Remove-Item -LiteralPath $staging -Recurse -Force -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $staging | Out-Null

& $java -jar $builder buildConfigData $excelDir $staging
if ($LASTEXITCODE -ne 0) { throw "Config generation failed with exit code $LASTEXITCODE" }
if ((Get-ChildItem -LiteralPath $staging -Filter '*.txt').Count -eq 0) { throw 'No TXT files were generated.' }

Remove-Item -LiteralPath $backup -Recurse -Force -ErrorAction SilentlyContinue
try {
    if (Test-Path -LiteralPath $target) { Move-Item -LiteralPath $target -Destination $backup }
    Move-Item -LiteralPath $staging -Destination $target
    Remove-Item -LiteralPath $backup -Recurse -Force -ErrorAction SilentlyContinue
} catch {
    if (-not (Test-Path -LiteralPath $target) -and (Test-Path -LiteralPath $backup)) {
        Move-Item -LiteralPath $backup -Destination $target
    }
    throw
}
Write-Host "Generated config: $target"
