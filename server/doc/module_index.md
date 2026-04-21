# MiniServer 模块代码索引（2026-04-21）

> 目标：帮助快速熟悉当前仓库中的各个服务模块、代码入口、核心包结构与依赖关系。

## 1. 总体模块清单

`server/pom.xml` 当前聚合了 6 个 Maven 模块：

1. `config`
2. `proto`
3. `tool`
4. `core`
5. `GameServer`
6. `BotServer`

另外仓库中还存在两个独立模块（未在聚合 `pom` 中）：

- `GateServer`
- `LoginServer`

## 2. 模块依赖关系（代码层）

- `core` 依赖：`config`、`proto`（以及 Netty/Nacos/Redis/MySQL 等基础中间件）。
- `tool` 依赖：`core`（复用 KV、Excel 解析等）。
- `GameServer` 依赖：`core`。
- `BotServer` 依赖：`core`。
- `GateServer` 依赖：`core`。
- `LoginServer` 依赖：`core`（并使用 Spring Boot Web）。

可将 `core` 理解为“基础运行时 + 网络/RPC/存储/配置能力层”，其他服务模块在此之上实现具体职责。

## 3. 模块详细索引

### 3.1 config（8 个 Java 文件）

**定位**：配置加载与配置管理抽象层。

**主要入口/核心类**
- `ly.Main`
- `ly.ConfigService`
- `ly.AbstractConfigManger`
- `ly.InterfaceConfigManagerProxy`
- `ly.config.HeroInfoConfigManager`

**包索引**
- `ly`
- `ly.config`

---

### 3.2 proto（6 个 Java 文件）

**定位**：协议定义与协议工厂（Protobuf 相关）。

**主要入口/核心类**
- `ly.Main`
- `ly.ProtoMessageFactory`
- `ly.proto.Cmd`
- `ly.proto.Login`
- `ly.proto.Server`
- `ly.proto.ErrorMsg`

**包索引**
- `ly`
- `ly.proto`

---

### 3.3 tool（4 个 Java 文件）

**定位**：离线工具模块（解析 Excel、解析 Proto、生成 DB 相关代码/结构）。

**主要入口/核心类**
- `ly.ToolMain`
- `ly.ParserExcelConfig`
- `ly.ParserProto`
- `ly.ParserDbEntry`

**包索引**
- `ly`

---

### 3.4 core（75 个 Java 文件）

**定位**：全项目基础能力核心模块（最关键）。

**主要入口/核心类**
- 顶层：`ly.Main`、`ly.StandaloneServer`、`ly.ServerContext`、`ly.IServer`
- 网络：`ly.net.NetService`、`ly.net.NetServer`、`ly.net.ConnectSession`、`ly.net.HandlerRouterManager`
- RPC：`ly.rpc.RpcService`、`ly.rpc.RpcNodeConnector`、`ly.rpc.RpcUtils`
- 注册发现：`ly.nacos.NacosService`、`ly.nacos.NacosServerNode`
- 数据库：`ly.db.MysqlService`、`ly.db.MysqlConnector`、`ly.db.AutoTableService`
- Redis：`ly.redis.RedisUtils`、`ly.redis.RedisKeys`
- 配置：`ly.config.ServerConfig`、`ly.config.DbConfig`、`ly.config.RedisConfig`

**包索引**
- `ly`
- `ly.cache`
- `ly.config`
- `ly.db`
- `ly.db.entry`
- `ly.game`
- `ly.nacos`
- `ly.net`
- `ly.net.packet`
- `ly.redis`
- `ly.rpc`
- `ly.utils`

---

### 3.5 GameServer（30 个 Java 文件）

**定位**：游戏逻辑服务（玩家生命周期、业务模块、登录登出流程等）。

**主要入口/核心类**
- `ly.GameServer`
- `ly.GameClientManager`
- 登录链路：`ly.logic.login.LoginManager`、`GamePlayerLoginController`、`GameLogoutController`
- 玩家域：`ly.logic.player.Player`、`PlayerManager`、`ModuleEnum`、`AbstractModule`
- 心跳：`ly.logic.ping.PingController`
- 路由/会话：`ly.net.GameHandlerRouter`、`GameHandlerRouteManager`、`GameConnectSession`

**包索引**
- `ly`
- `ly.logic.login`
- `ly.logic.ping`
- `ly.logic.player`
- `ly.logic.player.event`
- `ly.net`

---

### 3.6 BotServer（33 个 Java 文件）

**定位**：机器人压测/模拟客户端模块（状态机 + 命令 + 模块化行为策略）。

**主要入口/核心类**
- `ly.BotServer`
- `ly.bot.RobotManager`
- 状态机：`ly.bot.state.RobotState` + `ConnectingState/ConnectedState/LoggedInState`
- 行为策略：`ly.bot.strategy.RobotBehaviorStrategy` + `NormalBehaviorStrategy/AggressiveBehaviorStrategy`
- 机器人模块：`ly.bot.module.ModuleManager` 与各 `impl`（Login/Movement/Combat 等）
- 指令体系：`ly.bot.command.RobotCommand` 与 `impl`（Login/Move/Heartbeat）

**包索引**
- `ly`
- `ly.bot`
- `ly.bot.command`
- `ly.bot.command.impl`
- `ly.bot.data`
- `ly.bot.data.impl`
- `ly.bot.entity`
- `ly.bot.factory`
- `ly.bot.http`
- `ly.bot.module`
- `ly.bot.module.impl`
- `ly.bot.observer`
- `ly.bot.observer.impl`
- `ly.bot.session`
- `ly.bot.state`
- `ly.bot.state.impl`
- `ly.bot.stats`
- `ly.bot.strategy`
- `ly.bot.strategy.impl`
- `ly.bot.util`

---

### 3.7 GateServer（8 个 Java 文件，独立模块）

**定位**：网关服务，负责连接管理与登录流转。

**主要入口/核心类**
- `ly.GateServer`
- `ly.GateClientManager`
- 登录流程：`ly.logic.login.GateLoginController`、`GateLogoutController`
- 会话/连接：`ly.net.GateClient`、`GateConnectSession`、`GateConnectSessionProvider`

**包索引**
- `ly`
- `ly.logic.login`
- `ly.net`

---

### 3.8 LoginServer（9 个 Java 文件，独立模块）

**定位**：Spring Boot 登录服务（HTTP 登录、服列表、登录结果返回）。

**主要入口/核心类**
- `ly.loginserver.LoginServerApplication`
- `ly.loginserver.controller.LoginController`
- `ly.loginserver.service.LoginService`
- `ly.loginserver.LoginClient`
- 返回对象：`ly.loginserver.result.LoginResult`、`ServerListResult`、`ErrorCode`

**包索引**
- `ly.loginserver`
- `ly.loginserver.controller`
- `ly.loginserver.result`
- `ly.loginserver.service`

## 4. 建议阅读顺序（快速熟悉）

1. `README.md`（整体架构）
2. `server/core`（先看 `ly.Main`、`ServerContext`、`NetService`、`RpcService`）
3. `server/GameServer`（看登录与玩家模块装配）
4. `server/GateServer` + `server/LoginServer`（看登录入口与接入链路）
5. `server/BotServer`（看压测机器人状态机和行为策略）
6. `server/config` + `server/tool`（看配置和生成工具链）

## 5. 当前索引状态

- [x] 完成模块级目录梳理
- [x] 完成包级索引
- [x] 完成关键入口类索引
- [ ] 若需要，可继续扩展为“类 -> 方法 -> CMD 路由 -> 数据表”的深度索引
