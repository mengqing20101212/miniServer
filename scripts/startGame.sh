#!/bin/bash
# GameServer 启动脚本 (纯 Linux)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SERVER_DIR="$PROJECT_ROOT/server/GameServer"

JAVA="/usr/lib/jvm/java-21-openjdk-amd64/bin/java"
MVN="/mnt/d/Soft/env/apache-maven-3.9.15/bin/mvn"

export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"

# Windows 盘符符号链接
[ ! -e "$SERVER_DIR/D:" ] && ln -s /mnt/d "$SERVER_DIR/D:" 2>/dev/null || true

echo "=== 启动 GameServer ==="
echo "Java: $($JAVA -version 2>&1 | head -1)"
echo "目录: $SERVER_DIR"

cd "$SERVER_DIR"

echo "启动中... (serverId: game1001, NetServer: 9002)"
$MVN -q exec:java
