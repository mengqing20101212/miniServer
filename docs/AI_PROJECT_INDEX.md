# MiniServer AI Project Index

更新时间: 2026-04-25

## 1. 初始化结论
已读取并纳入索引的现有 AI / 中间文档：
- `STARTUP.SKILL.md`
- `docs/JAVA_SOURCE_INDEX.md`
- `server/doc/module_index.md`
- `server/doc/net_packet_unification_plan.md`
- `nacos-config.txt`

这些文件里，最可信的运行参数来源是 `STARTUP.SKILL.md`。
`nacos-config.txt` 更像较早阶段的本地化说明，不应直接覆盖 startup skill。

## 2. 仓库总览
- 仓库根目录: `miniServer`
- 主要源码根目录: `server/`
- Maven 聚合 POM: `server/pom.xml`
- 当前聚合模块数: 8
- 最新实扫 Java 文件数: 189

聚合模块：
1. `config`
2. `proto`
3. `tool`
4. `core`
5. `LoginServer`
6. `GameServer`
7. `GateServer`
8. `BotServer`

## 3. 目录索引
- `server/`：主工程与 Maven 多模块源码
- `excel/`：策划表 / 配置表来源
- `proto/`：`.proto` 协议定义与工具 jar
- `generated-sql/`：SQL 生成产物
- `logs/`：系统日志
- `runlogs/`：服务启动输出日志
- `.vscode/`：本地 Java / Maven / task 配置
- `.github/`：CI 与升级辅助脚本

## 4. 模块职责摘要
### core
基础运行时，承载网络、RPC、Nacos、MySQL、Redis、配置等公共能力。

### GameServer
主游戏逻辑模块，处理玩家生命周期、业务模块、登录后的核心逻辑。

### GateServer
网关模块，负责连接接入、消息转发与登录链路衔接。

### LoginServer
Spring Boot 登录服务，提供 HTTP 登录与服务器列表能力。

### BotServer
机器人客户端 / 压测模块，用于自动登录、行为模拟和协议验证。

### tool
离线生成工具，处理 Excel、Proto、DB Entry 等生成链路。

### config
配置模型与配置管理。

### proto
协议生成代码与消息工厂。

## 5. 高价值入口
- `README.md`
- `server/doc/module_index.md`
- `server/core/src/main/java/ly/ServerContext.java`
- `server/core/src/main/java/ly/net/NetService.java`
- `server/core/src/main/java/ly/rpc/RpcService.java`
- `server/GameServer/src/main/java/ly/GameServer.java`
- `server/GateServer/src/main/java/ly/GateServer.java`
- `server/LoginServer/src/main/java/ly/loginserver/LoginServerApplication.java`
- `server/BotServer/src/main/java/ly/BotServer.java`
- `server/tool/src/main/java/ly/ToolMain.java`

## 6. 现有 AI / 中间文件评估
### `STARTUP.SKILL.md`
价值高。给出了当前最明确的启动顺序、端口约束、Bot 参数与成功判定标准。

### `docs/JAVA_SOURCE_INDEX.md`
价值高，但会随仓库变化而变化。当前已按最新状态重生成，最新扫描结果是 189 个 Java 文件。
后续若继续新增/迁移 Java 文件，建议整体重生成而不是手改计数。

### `server/doc/module_index.md`
价值高。模块级理解入口清晰，适合新人或 AI 首轮熟悉代码时先读。

### `server/doc/net_packet_unification_plan.md`
设计草案类文档。仅在处理 `ly.net.packet` 协议收敛改造时作为设计输入，不应当成现状文档。

### `nacos-config.txt`
价值中等。它记录的是 localhost Nacos 场景，与 `STARTUP.SKILL.md` 的远程 Nacos 配置存在冲突。
运行时优先以 `STARTUP.SKILL.md` 为准。

## 7. 风险与注意点
- 根目录历史上的松散实验文件已经开始收敛：4 个 `*.class` 已清理，`test_entity_generator.java` 与 `test_new_types.xlsx` 已迁移到测试目录；后续仍可继续整理残余历史说明文件。
- `logs/`、`runlogs/` 中存在历史日志，定位问题时必须看时间戳。
- `generated-sql/create-tables.sql` 看起来是正式输出，不建议按临时文件处理。
- `README.md` 的部分描述较理想化，实际启动参数仍应以 `STARTUP.SKILL.md` 和当前代码为准。

## 8. 已初始化 / 已补齐的 AI 文件
当前已补齐：
- `AGENTS.md`
- `CLAUDE.md`
- `GEMINI.md`
- `.github/copilot-instructions.md`
- `docs/AI_PROJECT_INDEX.md`
- `docs/DEV_WORKFLOW.md`
- `docs/ROOT_LOOSE_FILES_AUDIT.md`

另外，`docs/JAVA_SOURCE_INDEX.md` 已按当前仓库状态重生成，最新统计为 189 个 Java 文件。

## 9. 下一步可继续做的事情
如果继续收口，我建议优先做：
1. 修正 `.vscode/launch.json` 与 `.vscode/tasks.json` 中 BotServer 仍使用 `8888` 的问题
2. 删除根目录 4 个未跟踪 `.class` 文件
3. 给 `nacos-config.txt` 加“历史说明”标记，避免误导
4. 把 `test_entity_generator.java` 与 `test_new_types.xlsx` 迁移到更规范的测试/样例目录
