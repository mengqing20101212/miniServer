@echo off
chcp 65001>nul
rem Change to the server module and run Maven package skipping tests
setlocal
set "SCRIPT_DIR=%~dp0"

if not exist "%SCRIPT_DIR%server" (
	echo Directory not found: "%SCRIPT_DIR%server"
	endlocal
	exit /b 1
)

pushd "%SCRIPT_DIR%server" || (
	echo Failed to change directory to "%SCRIPT_DIR%server"
	endlocal
	exit /b 1
)

mvn clean package -DskipTests %*
set "RC=%ERRORLEVEL%"
popd
endlocal
exit /b %RC%
