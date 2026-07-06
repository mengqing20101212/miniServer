# BotServer 链路测试报告

本文用于记录 BotServer 验证 LoginServer、GateServer、GameServer 三段链路时的固定检查项。后续每次改协议、RPC、SID/Seq、玩家模块数据时，都按这份报告补充结果。

## 测试目标

BotServer 需要覆盖以下链路：

1. 通过 LoginServer HTTP 获取服务器列表。
2. 连接 GateServer，并完成 NetClient ACK 握手。
3. 通过 GateServer 转发 `CS_Login` 到 GameServer。
4. GameServer 登录成功后返回 `SC_Login`。
5. BotServer 登录成功后执行业务 Module/Action。
6. Hero 模块发送英雄相关协议并收到对应回包。
7. Movement 模块执行移动行为。
8. Combat 模块执行战斗模拟行为，并且不复用 Movement 的移动 Action。

## 启动前检查

按 `STARTUP.SKILL.md` 的顺序启动服务：

1. LoginServer
2. GameServer
3. GateServer
4. BotServer

BotServer 启动前确认：

- LoginServer HTTP 端口 `8889` 可访问。
- GateServer 端口 `9001` 已监听。
- GameServer 端口 `9002` 已监听。
- Redis/KeyDB 可写。
- Nacos 地址使用 `118.25.76.117:8848`，namespace/env 使用 `ly`。

## 启动命令

```powershell
cd server/BotServer
java ly.BotServer --run-bots 127.0.0.1 8889 1
```

VSCode 可直接使用 `Debug BotServer`。

## 成功判定

BotServer 日志需要满足：

- 能获取 LoginServer 返回的 GateServer 信息。
- Gate 连接成功，NetClient `sid` 不为 0。
- 收到 `SC_Login`，并解析出有效 `playerId`。
- 登录成功后开始执行 `RobotModule`。
- Hero 模块至少能完成 `CS_HeroList -> SC_HeroList`。
- Movement 模块执行成功，不再走旧 `RobotCommand`。
- Combat 模块执行 `SimulatedCombatAction`，只写入 `combat` 数据域，不发送移动包。
- 统计信息中登录成功数为 1。

示例关键日志：

```text
成功获取服务器列表
成功连接到GateServer
收到登录响应
登录成功
选择了新模块: HeroModule
收到英雄列表响应
[统计信息] 总机器人数: 1, 连接Gate: 1, 登录成功: 1
```

## 失败排查

### LoginServer HTTP 失败

检查：

- `http://127.0.0.1:8889` 是否可访问。
- LoginServer 是否使用了正确 Nacos 参数。
- LoginServer 是否能连接 MySQL、Redis。

### Gate 连接失败

检查：

- GateServer 是否启动并监听 `9001`。
- GateServer 是否从 Nacos 注册成功。
- BotServer 获取到的 Gate IP/端口是否正确。

### 登录成功但反复新建角色

检查：

- LoginServer 返回的 `players` 列表是否包含已有角色 ID。
- BotServer 发送 `CS_Login` 时是否带上 `playerId`。
- GameServer 是否按账号和角色绑定加载已有玩家。

### Hero 回包失败

检查：

- Gate 转发 `CS_HeroList` 时是否携带客户端 SID。
- GameServer 玩家是否已经登录完成。
- GameServer 是否正确封装 `SC_HeroList` 返回 Gate。
- Gate 是否生成面向客户端的下行 seq。

## 当前代码约定

- BotServer 的主链路使用 `RobotModule + RobotAction`。
- 旧 `RobotCommand` 已从主链路移除。
- `RobotSession#createPacket(cmd, msg)` 统一封装 guid、seq、sid。
- 登录前 guid 使用 accountId，登录后 guid 使用 playerId。
- 如果 LoginServer 返回已有 playerId，`LoginAction` 会把 playerId 写入 `CS_Login`。
- RPC 回包匹配使用 callId，不再使用 seq。

## 回归记录模板

```text
日期:
分支:
提交:
服务:
  LoginServer:
  GameServer:
  GateServer:
  BotServer:
测试账号:
测试结果:
  获取服务器列表:
  Gate连接:
  登录:
  HeroList:
  Movement:
  Combat:
问题:
结论:
```

