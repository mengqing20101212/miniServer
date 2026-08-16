#!/bin/bash
# GateServer 启动脚本 (纯 Linux)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SERVER_DIR="$PROJECT_ROOT/server/GateServer"

export JAVA_HOME="${JAVA_HOME:-/mnt/d/Soft/env/Java/jdk-25}"
JAVA="$JAVA_HOME/bin/java"
MVN="$PROJECT_ROOT/server/mvnw"

# Windows 盘符符号链接
[ ! -e "$SERVER_DIR/D:" ] && ln -s /mnt/d "$SERVER_DIR/D:" 2>/dev/null || true

echo "=== 启动 GateServer ==="
echo "Java: $($JAVA -version 2>&1 | head -1)"
echo "目录: $SERVER_DIR"

cd "$SERVER_DIR"

echo "启动中... (serverId: gate1001, NetServer: 9001)"
sh "$MVN" -q exec:java
