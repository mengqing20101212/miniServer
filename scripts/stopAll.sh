#!/bin/bash
# 停止所有服务 (纯 Linux)

echo "=== 停止 miniServer 所有服务 ==="

kill_by_pattern() {
    local name=$1
    local pids=$(pgrep -f "$name" 2>/dev/null)
    if [ -n "$pids" ]; then
        echo "停止 $name (PID: $pids)"
        echo "$pids" | xargs kill -9 2>/dev/null || true
    else
        echo "$name 未运行"
    fi
}

# LoginServer (Spring Boot jar)
kill_by_pattern "LoginServer-0.0.1-SNAPSHOT.jar"

# GameServer (mvn exec:java)
kill_by_pattern "GameServer.*exec:java"

# GateServer (mvn exec:java)
kill_by_pattern "GateServer.*exec:java"

# BotServer (shade jar)
kill_by_pattern "BotServer-1.0-SNAPSHOT.jar"

echo ""
echo "=== 完成 ==="
