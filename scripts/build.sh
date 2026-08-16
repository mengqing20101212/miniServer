#!/bin/bash
# 构建整个项目 (纯 Linux)

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"
SERVER_DIR="$PROJECT_ROOT/server"

export JAVA_HOME="${JAVA_HOME:-/mnt/d/Soft/env/Java/jdk-25}"
JAVA="$JAVA_HOME/bin/java"
MVN="$SERVER_DIR/mvnw"
export LANG="zh_CN.UTF-8"
export JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8"

echo "=== 构建 miniServer ==="
echo "Java: $($JAVA -version 2>&1 | head -1)"
echo "目录: $SERVER_DIR"

cd "$SERVER_DIR"

sh "$MVN" -DskipTests install

echo ""
echo "=== 构建完成 ==="
