# MiniServer 模块索引

更新时间：2026-05-09

本文是当前 `server/pom.xml` 聚合工程的模块级阅读指南。

## 聚合模块

`server/pom.xml` 当前聚合 9 个 Maven 模块：

1. `config`
2. `proto`
3. `tool`
4. `core`
5. `LoginServer`
6. `GameServer`
7. `GateServer`
8. `BotServer`
9. `GMServer`

## 依赖关系概览

- `core` 依赖 `config`、`proto`，并承载 Netty、Nacos、Redis、MySQL 等公共基础能力。
- `tool` 依赖 `core`，负责各类离线生成流程。
- `GameServer`、`GateServer`、`LoginServer`、`BotServer` 基于 `core` 构建。
- `GMServer` 是 Spring Boot 后台管理服务，依赖 `core`、`config`、`proto`。

## 模块详情

### config

职责：生成的配置数据类和配置管理器。

关键文件：

- `server/config/src/main/java/ly/ConfigService.java`
- `server/config/src/main/java/ly/AbstractConfigManger.java`
- `server/config/src/main/java/ly/InterfaceConfigManagerProxy.java`
- `server/config/src/main/java/ly/config/*Config.java`
- `server/config/src/main/java/ly/config/*ConfigManager.java`

注意：

- 大部分文件由 `excel/` 生成。
- 自定义代码应放在生成器保留区。
- 重新生成会覆盖大量文件。

### proto

职责：生成的 protobuf Java 类和协议工厂。

关键文件：

- `server/proto/src/main/java/ly/ProtoMessageFactory.java`
- `server/proto/src/main/java/ly/proto/Cmd.java`
- `server/proto/src/main/java/ly/proto/Login.java`
- `server/proto/src/main/java/ly/proto/Server.java`
- `server/proto/src/main/java/ly/proto/ErrorMsg.java`

### tool

职责：离线生成工具。

关键文件：

- `server/tool/src/main/java/ly/ToolMain.java`
- `server/tool/src/main/java/ly/ParserExcelConfig.java`
- `server/tool/src/main/java/ly/ParserProto.java`
- `server/tool/src/main/java/ly/ParserDbEntry.java`

注意：

- `ParserDbEntry` 负责生成 DB Entry/Helper。
- 如果手工修了生成后的 Helper 行为，也要同步修生成器模板。

### core

职责：全项目公共运行时基础。

关键文件：

- `server/core/src/main/java/ly/Main.java`
- `server/core/src/main/java/ly/StandaloneServer.java`
- `server/core/src/main/java/ly/ServerContext.java`
- `server/core/src/main/java/ly/net/NetService.java`
- `server/core/src/main/java/ly/rpc/RpcService.java`
- `server/core/src/main/java/ly/nacos/NacosService.java`
- `server/core/src/main/java/ly/db/MysqlService.java`
- `server/core/src/main/java/ly/redis/RedisUtils.java`
- `server/core/src/main/java/ly/EntityToSqlGenerator.java`

### LoginServer

职责：Spring Boot 登录和服务器列表服务。

关键文件：

- `server/LoginServer/src/main/java/ly/loginserver/LoginServerApplication.java`
- `server/LoginServer/src/main/java/ly/loginserver/controller/LoginController.java`
- `server/LoginServer/src/main/java/ly/loginserver/service/LoginService.java`
- `server/LoginServer/src/main/java/ly/loginserver/LoginClient.java`

运行说明：

- 当前规范端口见 `STARTUP.SKILL.md`：NetServer `8888`，Spring HTTP `8889`。

### GameServer

职责：主游戏逻辑服务。

关键文件：

- `server/GameServer/src/main/java/ly/GameServer.java`
- `server/GameServer/src/main/java/ly/GameClientManager.java`
- `server/GameServer/src/main/java/ly/logic/login/LoginManager.java`
- `server/GameServer/src/main/java/ly/logic/player/Player.java`
- `server/GameServer/src/main/java/ly/logic/player/PlayerManager.java`
- `server/GameServer/src/main/java/ly/net/GameHandlerRouteManager.java`

### GateServer

职责：客户端网关和消息转发。

关键文件：

- `server/GateServer/src/main/java/ly/GateServer.java`
- `server/GateServer/src/main/java/ly/GateClientManager.java`
- `server/GateServer/src/main/java/ly/logic/login/GateLoginController.java`
- `server/GateServer/src/main/java/ly/net/GateConnectSession.java`
- `server/GateServer/src/main/java/ly/net/PacketCompat.java`

### BotServer

职责：机器人客户端、协议验证和压力测试。

关键文件：

- `server/BotServer/src/main/java/ly/BotServer.java`
- `server/BotServer/src/main/java/ly/bot/RobotManager.java`
- `server/BotServer/src/main/java/ly/bot/session/RobotSession.java`
- `server/BotServer/src/main/java/ly/bot/state/RobotState.java`
- `server/BotServer/src/main/java/ly/bot/module/ModuleManager.java`

运行说明：

- 当前规范命令是 `--run-bots 127.0.0.1 8889 1`。
- Bot 登录 HTTP 端口必须等于 LoginServer Spring HTTP 端口。

### GMServer

职责：游戏后台管理服务。

关键文件：

- `server/GMServer/src/main/java/ly/gmserver/GMServerApplication.java`
- `server/GMServer/src/main/java/ly/gmserver/config/WebSecurityConfig.java`
- `server/GMServer/src/main/java/ly/gmserver/controller/AdminController.java`
- `server/GMServer/src/main/java/ly/gmserver/controller/RoleController.java`
- `server/GMServer/src/main/java/ly/gmserver/controller/MenuController.java`
- `server/GMServer/src/main/java/ly/gmserver/controller/LogController.java`
- `server/GMServer/src/main/java/ly/gmserver/filter/JwtAuthFilter.java`
- `server/GMServer/src/main/java/ly/gmserver/filter/OperationLogAspect.java`
- `server/GMServer/src/main/java/ly/gmserver/service/GmAdminService.java`
- `server/GMServer/src/main/java/ly/gmserver/service/GmRoleService.java`
- `server/GMServer/src/main/java/ly/gmserver/service/GmMenuService.java`
- `server/GMServer/src/main/resources/templates/index.html`
- `server/GMServer/src/main/resources/templates/login.html`
- `server/GMServer/src/main/resources/schema.sql`

注意：

- 当前 HTTP 端口是 `server/GMServer/src/main/resources/application.yml` 中的 `9090`。
- 最近一轮工作修复了生成的 `gm_*` Helper 中 `select(null)` 的行为。
- 生成后的 Helper 和 `ParserDbEntry` 模板需要保持同步。

## 建议阅读顺序

1. `README.md`
2. `STARTUP.SKILL.md`
3. `server/doc/module_index.md`
4. `docs/JAVA_SOURCE_INDEX.md`
5. `server/core/src/main/java`
6. `server/GameServer/src/main/java`
7. `server/GateServer/src/main/java`
8. `server/LoginServer/src/main/java`
9. `server/GMServer/src/main/java`
10. `server/BotServer/src/main/java`
11. `server/tool/src/main/java`
