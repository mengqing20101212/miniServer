# MiniServer Dev Workflow

更新时间: 2026-04-26

## 1. 目标
本文整理 miniServer 当前真实可用的开发工作流，重点覆盖：
- 构建
- 生成链路
- 本地启动顺序
- VS Code 调试
- 常见验证点

运行参数优先级：
1. `STARTUP.SKILL.md`
2. `server/core/src/main/java/ly/startup/StartupSkillLoader.java`
3. `.vscode/launch.json`
4. 旧说明文件，如 `nacos-config.txt`

如果这些文件冲突，以 `STARTUP.SKILL.md` 为准。

## 2. 当前确认的运行参数
来自 `STARTUP.SKILL.md`：
- Nacos: `118.25.76.117:8848`
- namespace/env: `ly`
- LoginServer:
  - serverType: `LOGIN`
  - serverId: `login`
  - netPort: `8888`
  - springPort: `8889`
- GameServer:
  - serverType: `GAME`
  - serverId: `game1001`
  - netPort: `9002`
- GateServer:
  - serverType: `GATE`
  - serverId: `gate1001`
  - netPort: `9001`
- BotServer:
  - command: `--run-bots`
  - loginHost: `127.0.0.1`
  - loginHttpPort: `8889`
  - numBots: `1`

固定启动顺序：
1. LoginServer
2. GameServer
3. GateServer
4. BotServer

## 3. 环境要求
- Java 21
- Maven
- 可访问的 Nacos / MySQL / Redis
- 推荐使用仓库自带 VS Code Java 配置

已看到的本地编辑器配置：
- `.vscode/settings.json` 指向 `D:\Soft\env\Java\jdk-21`
- Maven 构建根设为 `server/pom.xml`

## 4. 构建入口
推荐构建根目录：`server/`

全量构建：
```bash
cd server
mvn -DskipTests install
```

如果只想做常规编译：
```bash
cd server
mvn clean compile -DskipTests
```

已有辅助入口：
- `.vscode/tasks.json`
  - `build entire server directory`
  - `mvn build`
  - `clean and build all server projects`
  - `clean and build GateServer`
- `start_app.bat`
  - 本质上也是 `cd server && mvn clean package -DskipTests`

## 5. 生成链路
项目有三类明显生成/辅助链路：

### 5.1 Excel 配置生成
入口：
- `server/tool/src/main/java/ly/ToolMain.java`
- `server/tool/src/main/java/ly/ParserExcelConfig.java`

README 中给出的入口：
```bash
cd server/tool
mvn compile exec:java -Dexec.mainClass="ly.ToolMain" -Dexec.args="parserExcelConfig ../../excel"
```

说明：
- Excel 原始数据在 `excel/`
- 生成结果会影响 `server/config` 及相关配置代码
- 不建议在不了解策划表约束时批量改动 `excel/`

### 5.2 Proto 相关生成
入口：
- `server/tool/src/main/java/ly/ParserProto.java`
- 根目录 `proto/` 下的 `.proto` 文件

说明：
- `server/proto` 中存在生成后的 Java 协议类
- 修改协议时，优先确认是只改 `.proto`，还是同步改工具链

### 5.3 DB Entry / SQL 生成
入口：
- `server/tool/src/main/java/ly/ParserDbEntry.java`
- `server/core/src/main/java/ly/EntityToSqlGenerator.java`
- `generated-sql/create-tables.sql`

说明：
- `generated-sql/create-tables.sql` 看起来是正式生成产物，不应按临时文件对待

## 6. 启动机制
`GameServer`、`GateServer`、`BotServer`、`LoginServer` 现在都接入了：
- `ly.startup.StartupSkillLoader`

这意味着：
- 启动时会自动查找仓库根目录下的 `STARTUP.SKILL.md`
- 如果你传入 CLI 参数，参数必须与 skill 中定义一致
- 如果参数与 skill 不一致，代码会直接抛异常，而不是“按 CLI 覆盖”

关键约束：
- LoginServer `springPort` 必须满足 `netPort + 1`
- BotServer `loginHttpPort` 必须等于 LoginServer `springPort`
- startup 顺序必须固定为 `login -> game -> gate -> bot`

## 7. 本地启动方式

### 7.1 LoginServer
入口类：
- `ly.loginserver.LoginServerApplication`

特点：
- Spring Boot 应用
- 启动时会读取 `STARTUP.SKILL.md`
- 会设置 `loginserver.nacosUrl`
- 会把 Spring 端口设置为 `8889`

IDE/命令行关注点：
- HTTP 入口走 `8889`
- Net 侧注册信息仍走 skill 中的 login 配置

### 7.2 GameServer
入口类：
- `ly.GameServer`

启动参数形态：
```bash
<nacosUrl> <env> <serverId>
```

当前应等于：
```bash
118.25.76.117:8848 ly game1001
```

### 7.3 GateServer
入口类：
- `ly.GateServer`

当前应等于：
```bash
118.25.76.117:8848 ly gate1001
```

### 7.4 BotServer
入口类：
- `ly.BotServer`

当前应等于：
```bash
--run-bots 127.0.0.1 8889 1
```

注意：
- `StartupSkillLoader` 明确要求 Bot 的 HTTP 登录端口等于 LoginServer `springPort`
- 所以这里应该是 `8889`，不是 `8888`

## 8. VS Code 调试
`.vscode/launch.json` 已经给出可直接使用的配置：
- `Debug LoginServer`
- `Debug GameServer`
- `Debug GateServer`
- `Debug BotServer`
- 组合调试：`Debug Backend Core`
- 组合调试：`Debug Full Stack With Bot`

当前 `.vscode/launch.json` 已经与 `STARTUP.SKILL.md` 对齐，`Debug BotServer` 参数已修正为：
- `--run-bots 127.0.0.1 8889 1`

如果 Java 索引异常，可优先使用：
- `reindex_vscode.bat`

该脚本会清理 VS Code Java 缓存、各模块 `target/`，并提示重新对 `server` 目录执行 “Rescan Java Projects”。

## 9. 推荐开发顺序

### 9.1 修改普通 Java 代码
1. 先看 `server/doc/module_index.md`
2. 进入对应模块修改代码
3. 在 `server/` 下执行：
   ```bash
   mvn clean compile -DskipTests
   ```
4. 需要联调时，按 `login -> game -> gate -> bot` 启动

### 9.2 修改 Excel 配置
1. 修改 `excel/` 下源表
2. 运行 Excel 生成链路
3. 再执行 Maven 编译
4. 启动服务验证配置是否生效

### 9.3 修改协议或网络包
1. 先确认是改 `.proto` 还是改 `ly.net.packet`
2. 如果涉及旧设计草案，参考 `server/doc/net_packet_unification_plan.md`
3. 修改后必须至少验证 Login / Gate / Game / Bot 链路

## 10. 最低验证清单
构建后至少验证：
- `server/` 全量编译通过
- LoginServer 成功监听 `8888` / `8889`
- GameServer 成功监听 `9002`
- GateServer 成功监听 `9001`
- BotServer 能连到 LoginServer，并出现登录成功相关日志
- `http://127.0.0.1:8889/actuator` 可访问

## 11. 当前已知仍需注意的点
1. `STARTUP.SKILL.md` 指向远程 Nacos：`118.25.76.117:8848`
2. `nacos-config.txt` 记录的是 localhost Nacos 历史说明
3. 运行参数仍应优先信 `STARTUP.SKILL.md`

结论：
- 不要再把旧 localhost Nacos 说明当作当前默认启动参数
- BotServer 端口相关 VS Code 配置已经修正到 `8889`

## 12. 推荐后续修正
如果继续收敛开发体验，建议下一步优先做：
- 将 `reindex_vscode.bat` 的用途补充进 README 或单独的 IDE 指南
- 视团队实际情况决定是否保留 `nacos-config.txt` 作为历史说明文件
