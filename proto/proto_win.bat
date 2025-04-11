@echo off
chcp 65001 >nul

:: 设置输出目录
set "outputDir=..\server\proto\src\main\java"

:: 创建输出目录（如果不存在）
if not exist "%outputDir%" (
    mkdir "%outputDir%"
)

:: 编译所有 proto 文件到指定 Java 目录
.\bin\protoc.exe --proto_path=. --java_out="%outputDir%" *.proto

:: 可选：列出生成的文件
echo Generated Java files:
dir /s /b "%outputDir%"
echo create ProtoMessageFactory
java -jar .\tool-1.0-SNAPSHOT.jar ParserProto
pause