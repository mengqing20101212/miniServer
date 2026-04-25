---
name: mini-server-startup
description: 统一约束 LoginServer、GameServer、GateServer、BotServer 的本地启动参数、校验规则和启动顺序。
startup:
  nacos:
    url: "118.25.76.117:8848"
    namespace: "ly"
  login:
    serverType: "LOGIN"
    serverId: "login"
    env: "ly"
    netPort: 8888
    springPort: 8889
  game:
    serverType: "GAME"
    serverId: "game1001"
    env: "ly"
    netPort: 9002
  gate:
    serverType: "GATE"
    serverId: "gate1001"
    env: "ly"
    netPort: 9001
  bot:
    command: "--run-bots"
    loginHost: "127.0.0.1"
    loginHttpPort: 8889
    numBots: 1
  validation:
    loginSpringPortOffset: 1
    requireBotHttpPortEqualsLoginSpringPort: true
    startupOrder:
      - login
      - game
      - gate
      - bot
---

# MiniServer Startup Skill

## 启动参数摘要

- Nacos 地址: `118.25.76.117:8848`
- 命名空间: `ly`
- LoginServer: `serverId=login`，NetServer `8888`，Spring HTTP `8889`
- GameServer: `serverId=game1001`，NetServer `9002`
- GateServer: `serverId=gate1001`，NetServer `9001`
- BotServer: `--run-bots 127.0.0.1 8889 1`

## 启动顺序

1. `LoginServer`
2. `GameServer`
3. `GateServer`
4. `BotServer`

## 校验规则

- 所有服务启动前必须先读取本文件。
- `LoginServer` 的 Spring 端口必须等于 NetServer 端口 `+1`。
- `BotServer` 登录用的 HTTP 端口必须等于 `LoginServer.springPort`。
- `GameServer`、`GateServer` 的 `nacosUrl/env/serverId` 必须和本文件一致。

## 本地命令

```text
LoginServer: 由 Spring Boot 启动，参数从 skill 自动加载
GameServer:  java ... ly.GameServer 118.25.76.117:8848 ly game1001
GateServer:  java ... ly.GateServer 118.25.76.117:8848 ly gate1001
BotServer:   java ... ly.BotServer --run-bots 127.0.0.1 8889 1
```
