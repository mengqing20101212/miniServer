#!/bin/bash

# GameServer 启动脚本

echo "启动 GameServer..."

# 设置环境变量
export NACOS_URL="localhost:8848"
export ENV="DEV"
export SERVER_ID="game-server-1"

echo "Nacos URL: $NACOS_URL"
echo "Environment: $ENV"
echo "Server ID: $SERVER_ID"

# 进入项目目录
cd /data/work/miniServer/server/GameServer

# 使用 Maven 运行 GameServer
source /etc/profile
mvn exec:java -Dexec.mainClass="ly.GameServer" -Dexec.args="$NACOS_URL $ENV $SERVER_ID"

echo "GameServer 启动完成"