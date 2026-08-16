# MiniServer

MiniServer 是一个 Java 25 + Maven 多模块游戏服务器工程，核心目标是把网关、登录、游戏逻辑、GM 后台、机器人测试、协议生成、配置表生成等能力放在同一个可本地联调的仓库中。

当前工程以 `server/pom.xml` 作为 Maven 聚合入口，运行时依赖 Nacos、MySQL、Redis/KeyDB，通信层基于 Netty，业务协议使用 protobuf 生成代码，GM 和 LoginServer 使用 Spring Boot。

## 目录结构

```text
miniServer/
├── server/                 # Maven 聚合工程，主要 Java 代码都在这里
│   ├── config/             # 策划配置表生成代码、ConfigManager、热更相关逻辑
│   ├── proto/              # protobuf 生成后的 Java 协议类和协议工厂
│   ├── tool/               # 协议、配置表、DB Entry 等代码生成工具
│   ├── core/               # 网络、RPC、Nacos、Redis、MySQL、日志、排行等公共能力
│   ├── LoginServer/        # 登录、注册、服务器列表、账号角色绑定
│   ├── GameServer/         # 游戏主逻辑、玩家对象、模块数据、GM 玩家编辑 RPC
│   ├── GateServer/         # 客户端网关、连接管理、转发 GameServer
│   ├── BotServer/          # 机器人客户端、Module/Action 测试框架
│   └── GMServer/           # GM 后台、权限、菜单、操作日志、配表热更、玩家详情
├── proto/                  # protobuf 源定义和 proto_win.bat 生成脚本
├── excel/                  # 策划配置表和运行时 txt 配表
├── generated-sql/          # 生成的建表 SQL，属于跟踪文件，不要当临时文件删除
├── docs/                   # 项目索引、开发流程、源码索引等文档
├── logs/                   # 当前或历史运行日志
├── runlogs/                # 本地联调运行日志
└── STARTUP.SKILL.md        # 本地启动参数、启动顺序、校验规则
```

## 模块说明

| 模块 | 职责 |
| --- | --- |
| `core` | 公共运行时基础能力，包括 Netty 网络、RPC、Nacos、MySQL、Redis、日志、通用排行榜、死锁检测、服务器上下文等。 |
| `config` | 配置表模型、Manager、Checker、A/B 版本热更加载与切换逻辑。 |
| `proto` | 由根目录 `proto/*.proto` 生成的 Java 协议类，以及 `ProtoMessageFactory`。 |
| `tool` | 离线代码生成工具，包括协议工厂生成、配置表代码生成、DB Entry/Helper/SQL 生成。 |
| `LoginServer` | Spring Boot 登录服务，提供账号登录、角色列表、服务器列表，并维护账号与玩家 ID 绑定。 |
| `GateServer` | 客户端接入网关，维护客户端 socket、SID、下行 seq，并把客户端包封装转发到 GameServer。 |
| `GameServer` | 游戏主逻辑服务，维护在线玩家、玩家模块、统一任务队列、协程调用、GM 玩家数据编辑等。 |
| `BotServer` | 机器人客户端，用 Module/Action 组织登录、移动、英雄等业务验证。 |
| `GMServer` | Spring Boot GM 后台，包含管理员、角色、菜单、操作日志、安全管理、配表热更、在线玩家详情编辑等页面。 |

## 环境要求

- JDK 25
- Maven 4.0.0-rc-5（通过 `server/mvnw` 或 `server/mvnw.cmd` 使用）
- MySQL
- Redis 或 KeyDB
- Nacos 2.x

本地调试时建议统一使用同一个 JDK 路径，避免 VSCode、命令行、脚本混用不同 Java 版本。

## 构建

推荐从 `server/` 目录构建：

```powershell
cd server
.\mvnw.cmd -DskipTests install
```

只编译部分模块时使用 `-pl` 和 `-am`：

```powershell
cd server
.\mvnw.cmd -DskipTests compile -pl GameServer -am
.\mvnw.cmd -DskipTests compile -pl GateServer -am
.\mvnw.cmd -DskipTests compile -pl BotServer -am
.\mvnw.cmd -DskipTests compile -pl GMServer -am
```

如果 VSCode 出现 Java 编译缓存和 Maven 不一致，可以执行 `Java: Clean Java Language Server Workspace` 后重新导入。

## 协议生成

协议源文件位于根目录 `proto/`：

```text
proto/
├── Cmd.proto
├── Common.proto
├── ErrorMsg.proto
├── GmPlayer.proto
├── Hero.proto
├── Login.proto
├── Move.proto
├── Resource.proto
└── Server.proto
```

Windows 下使用：

```powershell
cd proto
.\proto_win.bat
```

脚本会执行两步：

1. 调用 `proto/bin/protoc.exe` 生成 `server/proto/src/main/java` 下的协议 Java 类。
2. 调用 `tool-1.0-SNAPSHOT.jar ParserProto` 生成协议工厂。

协议命名需要和当前工具链保持一致。新增协议后要同步检查 `Cmd.proto`、协议消息名、生成后的 `ProtoMessageFactory`。

## 配置表和生成代码

策划配置源数据放在 `excel/`，运行时使用的 txt 配表也在该目录体系下。配置表相关代码由 `tool` 模块生成，主要产物在：

- `server/config/src/main/java/ly/config`
- `server/config/src/main/java/ly/config/*ConfigManager*`
- `server/config/src/main/java/ly/config/*Checker*`

当前配置表热更采用 A/B 版本切换思路：启动时加载一个版本，热更时加载备用版本，通过 GM 发布版本和切换时间，各服务器加载、检测成功后到点统一切换。

## 本地启动

本地启动参数以 `STARTUP.SKILL.md` 为准。当前规范参数：

| 服务 | serverId | 端口 | 启动参数 |
| --- | --- | --- | --- |
| LoginServer | `login` | Net `8888`，HTTP `8889` | `--loginserver.nacosUrl=118.25.76.117:8848` |
| GameServer | `game1001` | Net `9002` | `118.25.76.117:8848 ly game1001` |
| GateServer | `gate1001` | Net `9001` | `118.25.76.117:8848 ly gate1001` |
| GMServer | `gmServer` | Net `9088`，HTTP `9090` | 见 GMServer 配置和 Nacos |
| BotServer | - | - | `--run-bots 127.0.0.1 8889 1` |

启动顺序：

1. LoginServer
2. GameServer
3. GateServer
4. GMServer
5. BotServer

BotServer 必须等 Login、Game、Gate 都真正监听成功后再启动。

VSCode 已配置常用启动项：

- `Debug LoginServer`
- `Debug GameServer`
- `Debug GateServer`
- `Debug BotServer`
- `Debug Backend Core`
- `Debug Full Stack With Bot`

## 运行时约定

### Nacos

业务运行配置来自 Nacos。不要把旧文档里的 localhost 配置直接覆盖当前远程 Nacos 参数。当前本地联调默认 Nacos 地址是：

```text
118.25.76.117:8848
namespace/env: ly
```

### Redis/KeyDB

Redis/KeyDB 需要可写。如果实例处于 replica/slave 且只读，排行榜、可靠 RPC、登录缓存等写操作会失败。

### Gate、Game、SID、Seq

- SID 用来标识客户端当前连接到 Gate 的 socket。
- Gate 转发客户端上行包到 Game 时，会把客户端 SID 和客户端上行 seq 放进二次封装协议。
- Game 处理业务时使用客户端 SID 做日志和玩家连接定位。
- 下行 seq 由 Gate 面向客户端统一维护，用来帮助客户端发现下行包乱序或丢包。
- RPC 回包匹配应使用 `callId`，不要再用 seq 作为 RPC 回调唯一标识。

### GamePlayer 任务模型

GamePlayer 内部使用统一 FIFO 队列处理：

- 客户端上行 packet
- 玩家自身事件
- 其他玩家或模块投递的事件
- 系统全局事件
- 玩家协程任务

这样可以保证同一个玩家对象上的业务按入队顺序串行执行，降低并发读写玩家数据的风险。

### GM 玩家详情

GM 玩家详情页用于查看和编辑在线玩家数据：

- 玩家必须在线才允许编辑。
- 玩家所在 GameServer 从 Redis 中查询。
- 模块数据按玩家模块展开。
- 编辑请求通过 GMServer RPC 到 GameServer，再投递到玩家队列内执行。
- `PlayerEntry.modules` 是序列化大字段，页面不展示该字段。

### 排行榜

通用排行榜位于 `core` 模块，基于 Redis ZSet 实现：

- 具体排行榜继承抽象基类。
- `RankService` 管理排行榜实例和写入队列。
- `RankUtils` 提供 Redis 排行操作。
- 支持最大人数、过期删除、结算时间、结算后备份历史数据。
- 同分时依赖 ZSet member 设计保证先达到分数的玩家靠前。

## GM 后台

默认 HTTP 入口：

```text
http://127.0.0.1:9090/gm/index
```

主要能力：

- 管理员管理
- 角色管理
- 菜单管理
- 操作日志
- 安全管理
- 配表热更
- 玩家详情

GM 菜单由数据库配置驱动。如果页面提示菜单加载失败，优先检查 GMServer 日志、JWT 登录状态、菜单接口返回内容和数据库菜单记录。

## BotServer 测试

BotServer 使用 Module/Action 组织测试流程：

- Module 表示一组业务验证，例如登录模块、移动模块、英雄模块。
- Action 表示模块内的一次协议行为。
- 初始化模块按顺序执行，随机业务模块可按策略循环执行。

常用启动：

```powershell
cd server/BotServer
java ly.BotServer --run-bots 127.0.0.1 8889 1
```

成功标准：

- Bot 能通过 LoginServer 获取服务器列表。
- Bot 能连接 GateServer。
- 登录 GameServer 成功。
- 业务 Action 能收到对应下行协议。
- 日志无持续 RPC 超时、连接断开或协议解析错误。

## 日志和排查

常用日志目录：

- `logs/`
- `runlogs/`
- 各模块本地运行目录下的日志输出

注意：

- `logs/` 和 `runlogs/` 里可能混有历史日志，排查时先确认时间戳。
- 乱码优先检查 JVM 参数是否包含 `-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8`。
- Netty、Nacos、MySQL 启动阶段可能有短暂重试日志，要结合后续成功日志判断。
- 生成 SQL 文件是跟踪产物，不要因为本地脏改动直接删除。

## 参考文档

- `STARTUP.SKILL.md`：本地启动参数、启动顺序、成功标准。
- `docs/AI_PROJECT_INDEX.md`：项目索引和文档地图。
- `docs/DEV_WORKFLOW.md`：构建、生成、启动、调试流程。
- `docs/JAVA_SOURCE_INDEX.md`：Java 源码索引。
- `server/doc/module_index.md`：模块级阅读指南。
- `server/doc/net_packet_unification_plan.md`：网络包统一设计记录。
