param(
    [string]$RepositoryRoot = (Split-Path -Parent $PSScriptRoot),
    [string]$OutputFile = "docs/JAVA_SOURCE_INDEX.md"
)

$ErrorActionPreference = "Stop"
$repoPath = [IO.Path]::GetFullPath($RepositoryRoot)
$serverPath = Join-Path $repoPath "server"
$outputPath = Join-Path $repoPath $OutputFile
$moduleOrder = @(
    "config", "proto", "tool", "core", "LoginServer", "GameServer",
    "SceneServer", "GateServer", "BotServer", "GMServer"
)

function Get-JavaSourceKind([string]$path) {
    if ($path -match '[\\/]src[\\/]main[\\/]java[\\/]') { return "main" }
    if ($path -match '[\\/]src[\\/]test[\\/]java[\\/]') { return "test" }
    return "other"
}

function Get-JavaMetadata([IO.FileInfo]$file) {
    $source = Get-Content -LiteralPath $file.FullName -Raw -Encoding UTF8
    $packageMatch = [regex]::Match($source, '(?m)^\s*package\s+([^;]+);')
    $typeMatch = [regex]::Match(
        $source,
        '(?m)^\s*public\s+(?:(?:final|abstract|sealed|non-sealed|static)\s+)*(?:class|interface|enum|record|@interface)\s+([A-Za-z_$][A-Za-z0-9_$]*)')
    [pscustomobject]@{
        File = $file
        Kind = Get-JavaSourceKind $file.FullName
        Package = if ($packageMatch.Success) { $packageMatch.Groups[1].Value.Trim() } else { "(default)" }
        MainType = if ($typeMatch.Success) { $typeMatch.Groups[1].Value } else { $file.BaseName }
    }
}

$javaFiles = @(Get-ChildItem -LiteralPath $repoPath -Recurse -Filter *.java -File |
    Where-Object { $_.FullName -notmatch '[\\/](target|\.gradle)[\\/]' } |
    Sort-Object FullName)
$serverFiles = @($javaFiles | Where-Object {
    $_.FullName.StartsWith($serverPath + [IO.Path]::DirectorySeparatorChar)
})
$mainCount = @($javaFiles | Where-Object { (Get-JavaSourceKind $_.FullName) -eq "main" }).Count
$testCount = @($javaFiles | Where-Object { (Get-JavaSourceKind $_.FullName) -eq "test" }).Count
$otherCount = $javaFiles.Count - $mainCount - $testCount

$moduleData = foreach ($module in $moduleOrder) {
    $modulePath = Join-Path $serverPath $module
    $files = @($serverFiles | Where-Object {
        $_.FullName.StartsWith($modulePath + [IO.Path]::DirectorySeparatorChar)
    })
    $metadata = @($files | ForEach-Object { Get-JavaMetadata $_ })
    [pscustomobject]@{
        Name = $module
        Path = $modulePath
        Files = $metadata
        Packages = @($metadata.Package | Sort-Object -Unique).Count
        Main = @($metadata | Where-Object Kind -eq "main").Count
        Test = @($metadata | Where-Object Kind -eq "test").Count
    }
}

$lines = [Collections.Generic.List[string]]::new()
$lines.Add("# Java Source Index")
$lines.Add("")
$lines.Add("生成时间: $((Get-Date).ToString('yyyy-MM-dd HH:mm:ss')) CST")
$lines.Add("")
$lines.Add("Java 文件总数: $($javaFiles.Count) (server 目录: $($serverFiles.Count), 仓库其他位置: $($javaFiles.Count - $serverFiles.Count))")
$lines.Add("")
$lines.Add("说明: 本索引由 ``scripts/generate-java-source-index.ps1`` 基于当前仓库实时扫描生成，排除 ``target/`` 和 ``.gradle/``。")
$lines.Add("")
$lines.Add("## 汇总")
$lines.Add("")
$lines.Add("- main 源码: $mainCount files")
$lines.Add("- test 源码: $testCount files")
$lines.Add("- other 位置源码: $otherCount files")
$lines.Add("")
$lines.Add("## 模块概览")
$lines.Add("")
foreach ($module in $moduleData) {
    $lines.Add("- $($module.Name): $($module.Files.Count) files, $($module.Packages) packages")
}
$lines.Add("")
$lines.Add("## 各模块详细索引")

foreach ($module in $moduleData) {
    $lines.Add("")
    $lines.Add("### $($module.Name)")
    $lines.Add("")
    $lines.Add("- 文件数: $($module.Files.Count)")
    $lines.Add("- 包数: $($module.Packages)")
    $lines.Add("- main/test: $($module.Main)/$($module.Test)")
    foreach ($kind in @("main", "test", "other")) {
        $entries = @($module.Files | Where-Object Kind -eq $kind | Sort-Object { $_.File.FullName })
        if ($entries.Count -eq 0) { continue }
        $title = switch ($kind) {
            "main" { "main 源码" }
            "test" { "test 源码" }
            default { "other 源码" }
        }
        $lines.Add("")
        $lines.Add("#### $title")
        $lines.Add("")
        foreach ($entry in $entries) {
            $relative = [IO.Path]::GetRelativePath($module.Path, $entry.File.FullName).Replace('\', '/')
            $lines.Add("- [$kind] ``$relative`` — ``$($entry.Package)`` — $($entry.MainType)")
        }
    }
}

$outsideServer = @($javaFiles | Where-Object {
    !$_.FullName.StartsWith($serverPath + [IO.Path]::DirectorySeparatorChar)
})
if ($outsideServer.Count -gt 0) {
    $lines.Add("")
    $lines.Add("## 仓库其他 Java 源码")
    $lines.Add("")
    foreach ($file in $outsideServer) {
        $entry = Get-JavaMetadata $file
        $relative = [IO.Path]::GetRelativePath($repoPath, $file.FullName).Replace('\', '/')
        $lines.Add("- [$($entry.Kind)] ``$relative`` — ``$($entry.Package)`` — $($entry.MainType)")
    }
}

[IO.File]::WriteAllLines($outputPath, $lines, [Text.UTF8Encoding]::new($false))
Write-Output "Generated $outputPath with $($javaFiles.Count) Java files."
