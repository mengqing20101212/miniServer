# MiniServer AI 项目索引

更新时间：2026-08-19

## 当前可信入口

在做较大范围判断前，优先阅读这些文件：

- `STARTUP.SKILL.md`：当前本地启动参数、启动顺序和验收标准。
- `docs/DEV_WORKFLOW.md`：构建、生成、启动、调试流程。
- `docs/JAVA_SOURCE_INDEX.md`：按当前源码树重新扫描得到的 Java 源码索引。
- `server/doc/module_index.md`：模块级阅读指南。
- `server/doc/net_packet_unification_plan.md`：网络包统一改造设计记录。
- `docs/PLAYER_MODULE_PERSISTENCE_PLAN.md`：玩家模块从单一 BLOB 演进到模块级持久化的设计方案，尚未实施。
- `docs/SLG_MINISERVER_FOUNDATION.md`：SLG 场景运行时第一阶段基础骨架和线程边界说明。
- `server/SceneServer/`：独立场景服工程，包含公共、本服、跨服场景代码、玩家独立战争迷雾、Region/Portal 两级异步 A*、热点 Region 单线程迁移、行军/集结状态机、玩家场景投影恢复和周期线程负载日志。
- `docs/ROOT_LOOSE_FILES_AUDIT.md`：根目录散落文件审计与清理记录。
- `nacos-config.txt`：旧的 localhost Nacos 说明，只能作为历史参考。

当前运行参数以 `STARTUP.SKILL.md` 为准。该文件指向 Nacos
`118.25.76.117:8848`，namespace/env 为 `ly`；不要用 `nacos-config.txt`
里的旧 localhost 示例覆盖它。

## 仓库概况

- 仓库根目录：`miniServer`
- Maven 聚合工程：`server/pom.xml`
- 当前 Maven 模块数：10
- 当前 Java 源码文件数：1803 个，不包含 `target/` 和 `.gradle/`

当前聚合模块：

1. `config`
2. `proto`
3. `tool`
4. `core`
5. `LoginServer`
6. `GameServer`
7. `SceneServer`
8. `GateServer`
9. `BotServer`
10. `GMServer`

## 模块职责

- `config`：生成的配置模型和配置管理器。
- `proto`：生成的 protobuf Java 类和消息工厂。
- `tool`：Excel、proto、DB Entry/Helper 等离线生成工具。
- `core`：网络、RPC、Nacos、MySQL、Redis、配置、工具类等公共运行时基础。
- `LoginServer`：Spring Boot 登录和服务器列表服务。
- `GameServer`：游戏逻辑、玩家生命周期和玩家养成业务。
- `SceneServer`：地图状态权威，承载公共场景运行时、本服/跨服场景、AOI 分层同步、玩家独立战争迷雾、Region/Portal 两级异步 A*、热点 Region 单线程迁移、行军/集结、玩家投影异步入库/启动恢复和线程负载观测。
- `GateServer`：客户端网关、连接管理、登录和游戏消息转发。
- `BotServer`：机器人客户端、协议验证和压力测试。
- `GMServer`：Spring Boot 后台管理服务，包含 Thymeleaf 页面、JWT 鉴权、管理员/角色/菜单管理和操作日志。

## 高价值入口

- `server/core/src/main/java/ly/Main.java`
- `server/core/src/main/java/ly/ServerContext.java`
- `server/core/src/main/java/ly/net/NetService.java`
- `server/core/src/main/java/ly/rpc/RpcService.java`
- `server/GameServer/src/main/java/ly/GameServer.java`
- `server/GateServer/src/main/java/ly/GateServer.java`
- `server/LoginServer/src/main/java/ly/loginserver/LoginServerApplication.java`
- `server/BotServer/src/main/java/ly/BotServer.java`
- `server/GMServer/src/main/java/ly/gmserver/GMServerApplication.java`
- `server/tool/src/main/java/ly/ToolMain.java`
- `server/tool/src/main/java/ly/ParserDbEntry.java`

## 最近工作上下文

当前 `dev_hero` 分支最近的提交重点在 `GMServer`：

- 已将 `GMServer` 加入 `server/pom.xml` 聚合模块。
- 实现了后台管理相关 `gm_*` 表，并生成 Entry/Helper。
- 增加了管理员、角色、菜单、日志、登录、dashboard/profile 等页面和接口。
- 增加 JWT 鉴权、角色菜单数据、临时超级管理员登录路径、操作日志 AOP。
- 修复生成的 `EntryHelper.select(null)` 返回空列表的问题，改为查询全部行。
- 同步更新 `ParserDbEntry`，保证后续生成的 Helper 继承相同行为。

## 当前清理状态

早前列出的根目录清理项大多已经完成：

- 根目录 `.class` 产物已清理。
- `test_entity_generator.java` 已规范到 `server/core/src/test/java/ly/EntityToSqlGeneratorSmokeTest.java`。
- `test_new_types.xlsx` 已移动到 `server/tool/src/test/resources/test_new_types.xlsx`。
- `nacos-config.txt` 已标记为旧 localhost 说明。
- 本轮发现的剩余跟踪清理项是 `server/nul`，它只是一次 shell 路径错误输出的误产物。

## 注意区

- `excel/` 是配置源数据，不要随意改名或批量编辑。
- `generated-sql/create-tables.sql` 是跟踪输出，不是临时文件。
- `logs/` 和 `runlogs/` 需要按时间戳判断，历史失败日志不能直接代表当前状态。
- 如果修改生成代码行为，要同步修改生成器模板和已生成代码。
- 刷新项目索引时必须重新扫描当前树，只能基于扫描结果声明数量。
