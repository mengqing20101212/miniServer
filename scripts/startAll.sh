#!/bin/bash
# 按顺序启动所有服务 (纯 Linux)
# 启动顺序: LoginServer → GateServer → GameServer → BotServer

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

export JAVA_HOME="/usr/lib/jvm/java-21-openjdk-amd64"

echo "========================================="
echo "  miniServer 全服务启动"
echo "========================================="
echo ""
echo "启动顺序: LoginServer → GateServer → GameServer → BotServer"
echo ""

check_port() {
    local port=$1
    if ss -tlnp 2>/dev/null | grep -q ":$port "; then
        echo "[WARN] 端口 $port 已被占用"
        return 1
    fi
    return 0
}

echo "检查端口..."
check_port 8888 || echo "  LoginServer NetServer (8888) 可能已在运行"
check_port 8889 || echo "  LoginServer HTTP (8889) 可能已在运行"
check_port 9001 || echo "  GateServer (9001) 可能已在运行"
check_port 9002 || echo "  GameServer (9002) 可能已在运行"
echo ""

# 启动 LoginServer
echo "[1/4] 启动 LoginServer..."
"$SCRIPT_DIR/startLogin.sh" &
LOGIN_PID=$!
echo "  LoginServer PID: $LOGIN_PID"

echo "  等待 LoginServer 就绪..."
for i in $(seq 1 30); do
    if ss -tlnp 2>/dev/null | grep -q ":8889 "; then
        echo "  LoginServer 已就绪"
        break
    fi
    sleep 2
done

# 启动 GateServer
echo "[2/4] 启动 GateServer..."
"$SCRIPT_DIR/startGate.sh" &
GATE_PID=$!
echo "  GateServer PID: $GATE_PID"

echo "  等待 GateServer 就绪..."
for i in $(seq 1 30); do
    if ss -tlnp 2>/dev/null | grep -q ":9001 "; then
        echo "  GateServer 端口已就绪，等待初始化..."
        sleep 3
        break
    fi
    sleep 2
done

# 启动 GameServer
echo "[3/4] 启动 GameServer..."
"$SCRIPT_DIR/startGame.sh" &
GAME_PID=$!
echo "  GameServer PID: $GAME_PID"

echo "  等待 GameServer 就绪..."
for i in $(seq 1 30); do
    if ss -tlnp 2>/dev/null | grep -q ":9002 "; then
        echo "  GameServer 端口已就绪，等待初始化..."
        sleep 5
        break
    fi
    sleep 2
done

# 启动 BotServer
echo "[4/4] 启动 BotServer..."
"$SCRIPT_DIR/startBot.sh" &
BOT_PID=$!
echo "  BotServer PID: $BOT_PID"

echo ""
echo "========================================="
echo "  所有服务已启动"
echo "========================================="
echo ""
echo "停止所有: $SCRIPT_DIR/stopAll.sh"

wait
