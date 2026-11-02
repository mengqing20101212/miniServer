# MiniServer AI Agent Guide

This repository is a Java 25 + Maven multi-module game server project.

## 1. Repository shape
- Root repo: `miniServer`
- Main code lives under: `server/`
- Maven aggregator: `server/pom.xml`
- Aggregated modules: `config`, `proto`, `tool`, `core`, `LoginServer`, `GameServer`, `GateServer`, `BotServer`, `GMServer`
- Config tables: `excel/`
- Proto definitions: `proto/`
- Generated SQL output: `generated-sql/`
- Runtime logs: `logs/`, `runlogs/`

## 2. Important existing AI/generated context files
Read these before making broad architectural assumptions:
- `STARTUP.SKILL.md` — canonical local startup parameters and startup order
- `docs/AI_PROJECT_INDEX.md` — AI-oriented repository overview and document map
- `docs/DEV_WORKFLOW.md` — build / generate / startup / debug workflow
- `docs/JAVA_SOURCE_INDEX.md` — live Java file index
- `server/doc/module_index.md` — module-level reading guide
- `server/doc/net_packet_unification_plan.md` — packet refactor design note
- `docs/ROOT_LOOSE_FILES_AUDIT.md` — audit of root-level loose artifacts and cleanup suggestions
- `nacos-config.txt` — older local Nacos note; may not match current startup skill

Notes:
- `docs/JAVA_SOURCE_INDEX.md` is now a live rescan-based index for the current tree.
- `STARTUP.SKILL.md` points to remote Nacos `118.25.76.117:8848` with namespace `ly`; do not overwrite those values blindly with localhost defaults from older notes.

## 3. Build and run expectations
- Preferred build root: `server/`
- Full build: `mvn -DskipTests install`
- Existing helper scripts and IDE tasks may use `mvn clean compile` or `mvn clean package -DskipTests`
- Project expects Java 25 in local editor settings
- LoginServer is Spring Boot; other servers are plain Java entrypoints

## 4. Suggested code reading order
1. `README.md`
2. `server/doc/module_index.md`
3. `server/core/src/main/java`
4. `server/GameServer/src/main/java`
5. `server/GateServer/src/main/java`
6. `server/LoginServer/src/main/java`
7. `server/BotServer/src/main/java`
8. `server/tool/src/main/java`

## 5. Module intent summary
- `core`: runtime foundation, net/rpc/nacos/mysql/redis/config abstractions
- `GameServer`: main game logic, player lifecycle, message handling
- `GateServer`: client gateway and login forwarding
- `LoginServer`: HTTP login and server-list service
- `BotServer`: robot client / pressure testing
- `GMServer`: Spring Boot game-management backend, admin/role/menu pages, JWT auth, and operation logs
- `tool`: Excel / proto / db-entry generation tooling
- `config`: config models and managers
- `proto`: generated protobuf Java classes and factories

## 6. High-signal entrypoints
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

## 7. Caution zones
- `excel/` contains source config spreadsheets; do not casually rename or mass-edit.
- `generated-sql/create-tables.sql` is tracked output, not disposable scratch.
- `logs/` and `runlogs/` mix historical and current logs; verify timestamps before drawing conclusions.
- Historical loose test artifacts have been partly normalized: the old root-level SQL generator probe and text-based `.xlsx` sample were moved into test directories; still verify intent before deleting any remaining root-level utilities or notes.

## 8. When updating indexes
If you refresh project indexes, update or replace:
- `docs/AI_PROJECT_INDEX.md`
- `docs/JAVA_SOURCE_INDEX.md`
- `server/doc/module_index.md`
Only claim counts after rescanning the live tree.

## 9. 用户明确要求的实现约束
- 不猜测几乎不会发生的竞态或边界问题，不为此额外增加复杂度。确需增加防护时，先说明具体出错方式、发生概率及新增代码量，得到用户同意后再实施；不得私自扩大改动范围。
- 外部传入的数据只在入口校验一次；内部流转中已经确定类型的值，不再反复做防御检查。
- 不提前抽取只使用一次的函数。只有逻辑会复用、封装复杂逻辑或代表独立概念时，才抽成函数。
- 主函数只写正常业务流程；校验和细节逻辑放到子函数中。
- 注释只说明不明显的约束或反直觉的逻辑，不注释显而易见的代码。
- AI 自动生成的文件不得手动修改；修改生成源头，再通过生成器重新生成。
- 测试直接验证真实实现，不把业务逻辑复制到测试中，尽量少用 Mock。
- 遵循仓库现有工具和工作流程，不擅自引入新的工具或流程。
