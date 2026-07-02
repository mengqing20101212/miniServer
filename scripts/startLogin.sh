#!/bin/bash
# LoginServer 启动脚本 (纯 Linux)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SERVER_DIR="$PROJECT_ROOT/server/LoginServer"

JAVA="/usr/lib/jvm/java-21-openjdk-amd64/bin/java"

# Windows 盘符符号链接
[ ! -e "$PROJECT_ROOT/D:" ] && ln -s /mnt/d "$PROJECT_ROOT/D:" 2>/dev/null || true

echo "=== 启动 LoginServer ==="
echo "Java: $($JAVA -version 2>&1 | head -1)"
echo "目录: $SERVER_DIR"

cd "$PROJECT_ROOT"

JAR="$SERVER_DIR/target/LoginServer-0.0.1-SNAPSHOT.jar"
if [ ! -f "$JAR" ]; then
    echo "[ERROR] $JAR 不存在，请先执行: ./scripts/build.sh"
    exit 1
fi

echo "启动中... (NetServer: 8888, HTTP: 8889)"
$JAVA -jar "$JAR"
