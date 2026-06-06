#!/bin/bash
# 检查服务状态 (纯 Linux)

echo "=== miniServer 服务状态 ==="
echo ""

check_service() {
    local name=$1
    local port=$2

    if ss -tlnp 2>/dev/null | grep -q ":$port "; then
        echo "[OK]   $name (端口 $port)"
    else
        echo "[DOWN] $name (端口 $port)"
    fi
}

check_service "LoginServer NetServer" 8888
check_service "LoginServer HTTP" 8889
check_service "GameServer" 9002
check_service "GateServer" 9001
check_service "GMServer" 9090

echo ""

if pgrep -f "BotServer-1.0-SNAPSHOT" > /dev/null 2>&1; then
    echo "[OK]   BotServer (进程运行中)"
else
    echo "[DOWN] BotServer"
fi

echo ""
echo "=== 检查完成 ==="
