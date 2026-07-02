@echo off
chcp 65001 > nul
set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8

echo 终端编码已设置为UTF-8
cd server
call mvn clean package -DskipTests

rem 可以根据需要修改为实际的启动命令
echo 编译完成，请使用IDE或其他方式启动应用
