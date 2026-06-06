#!/bin/bash
# BotServer 启动脚本 (纯 Linux)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SERVER_DIR="$PROJECT_ROOT/server/BotServer"

JAVA="/usr/lib/jvm/java-21-openjdk-amd64/bin/java"

# Windows 盘符符号链接
[ ! -e "$PROJECT_ROOT/D:" ] && ln -s /mnt/d "$PROJECT_ROOT/D:" 2>/dev/null || true

echo "=== 启动 BotServer ==="
echo "Java: $($JAVA -version 2>&1 | head -1)"
echo "目录: $SERVER_DIR"

cd "$PROJECT_ROOT"

JAR="$SERVER_DIR/target/BotServer-1.0-SNAPSHOT.jar"
if [ ! -f "$JAR" ]; then
    echo "[ERROR] $JAR 不存在，请先执行: ./scripts/build.sh"
    exit 1
fi

echo "启动中... (连接 127.0.0.1:8889, 机器人数量: 1)"
$JAVA -jar "$JAR" --run-bots 127.0.0.1 8889 1
