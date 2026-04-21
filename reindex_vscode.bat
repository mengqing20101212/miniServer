@echo off
cls
echo =================================================
echo VS Code Java Project Reindex Helper
echo =================================================
setlocal

:: Define project root
set "PROJECT_ROOT=d:\WORK\me\miniServer"
set "SERVER_DIR=%PROJECT_ROOT%\server"

echo Current dir: %cd%
echo =================================================
echo Step 1: Close VS Code processes
echo =================================================
taskkill /f /im Code.exe >nul 2>&1
taskkill /f /im CodeHelper.exe >nul 2>&1
taskkill /f /im Code-Insiders.exe >nul 2>&1

:: Wait 2 seconds
ping 127.0.0.1 -n 3 >nul

echo =================================================
echo Step 2: Clean Java language server cache
echo =================================================

:: Remove Java language server cache
if exist "%APPDATA%\Code\User\workspaceStorage" (
    rd /s /q "%APPDATA%\Code\User\workspaceStorage"
    mkdir "%APPDATA%\Code\User\workspaceStorage"
)

:: Remove temporary VS Code Java cache
if exist "%LOCALAPPDATA%\Temp\vscode-java" (
    rd /s /q "%LOCALAPPDATA%\Temp\vscode-java"
)

echo =================================================
echo Step 3: Clean all server projects build artifacts
echo =================================================

:: Clean all server subprojects
for %%P in (GateServer,GameServer,LoginServer,core,config,proto,tool) do (
    echo Cleaning %%P project...
    if exist "%SERVER_DIR%\%%P\target" (
        rd /s /q "%SERVER_DIR%\%%P\target"
        mkdir "%SERVER_DIR%\%%P\target"
    )
)

echo =================================================
echo Step 4: Clean Maven local repository
echo =================================================
set "MAVEN_LOCAL_REPO=%USERPROFILE%\.m2\repository"
if exist "%MAVEN_LOCAL_REPO%\ly" (
    rd /s /q "%MAVEN_LOCAL_REPO%\ly"
)

echo =================================================
echo CLEANUP COMPLETE!
echo Please manually reopen VS Code and:
echo 1. Right-click server folder -> "Rescan Java Projects"
echo 2. Run the "build entire server directory" task
echo 3. Wait for Java language server to reindex (5-10 minutes)
echo =================================================
pause