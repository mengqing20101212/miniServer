# MiniServer 发布与本地策划测试环境方案

## 1. 目标与边界

本方案同时覆盖两类环境：

1. 线上和稳定测试环境：业务服务使用 Docker 发布，策划配置由 GM 从 Git 自动构建并通过 PREPARE/COMMIT 发布。
2. 内网日常开发环境：LoginServer、GateServer、GMServer 和基础设施由开发统一维护；策划和测试在自己的 Windows 或 macOS 电脑上运行独立 GameServer，直接加载本地 Excel 导出的 TXT 配置。

两套环境共享相同的 Config、ConfigManager 和 Checker 代码，但配置数据来源不同：

| 环境 | 配置数据来源 | 发布方式 |
|---|---|---|
| 线上/稳定测试 | GM 中的 MySQL 不可变版本 | PREPARE/COMMIT |
| 策划/测试本机 | 当前 Git 工作区 `excel/serverConfig` | 重新转表并重启本地 GameServer |

## 2. 总体拓扑

```text
                         内网公共环境
  +----------------------------------------------------------+
  | Nacos | MySQL | Redis | LoginServer | GateServer | GM    |
  +-----------------------------+----------------------------+
                                |
                    Nacos 服务发现和 Gate RPC
             +------------------+------------------+
             |                                     |
  策划/测试 A 电脑                              策划/测试 B 电脑
  game-local-alice                             game-local-bob
  GameServer.jar                               GameServer.jar
  excel/serverConfig                           excel/serverConfig
```

公共服务保持稳定，个人 GameServer 可以频繁停止、改时间、转表和重启，互不影响。个人 GameServer 的身份、端口和启动配置由服务器开发预先分配，不允许使用者自行注册。

## 3. 线上发布方案

### 3.1 程序发布链

GameServer、GateServer、LoginServer 等业务服务使用 Docker。镜像采用稳定分层：

```text
JDK 25 + /opt/arthas
应用依赖 /app/lib
业务代码 /app/app.jar
build-info.json 和启动入口
```

Arthas 放在 `/opt/arthas`，不进入应用 Classpath。依赖没有变化时只更新较小的业务代码层。

GM 是内部单节点工具，允许随时停止，使用 JAR + systemd 手动部署，不纳入 Docker 管理。GM 发布包携带匹配版本的 Config、ConfigManager、Checker 和 `config-builder.jar`。

### 3.2 配置发布链

```text
策划表 Git
  -> GM 使用只读 Git 账号拉取指定分支和 commit
  -> DATA_ONLY 模式生成 TXT
  -> Excel/结构/单表/跨表/全量装载校验
  -> MySQL 不可变版本仓库
  -> PREPARE
  -> 所有目标服务器 READY
  -> COMMIT
```

GM 页面负责 Git 数据源、构建任务、校验报告、版本差异、PREPARE/COMMIT 和历史版本回滚。配置内容在 MySQL 中按 SHA-256 去重存储，不使用第三方 OSS。

### 3.3 结构兼容规则

- 仅修改数值或数据行：直接生成配置版本并热更。
- 新增/删除字段、修改类型或 Config 代码：先手动升级 GM，再发布兼容的业务镜像，最后发布新配置。
- GM 和业务服务都上报 `configSchemaMin`、`configSchemaMax` 和 `configBuilderVersion`。
- 配置版本不可覆盖；回滚通过重新发布一个历史完整版本完成。

## 4. 内网公共环境

开发维护一套公共且相对稳定的服务：

- LoginServer
- GateServer
- GMServer
- Nacos
- MySQL
- Redis/KeyDB

公共环境职责：

- LoginServer 根据登录账号返回该账号已分配的个人 GameServer，不展示其他人的服务器。
- GateServer 根据客户端提交的 `gameServerId` 转发到目标个人 GameServer。
- Nacos 保存公共服务配置、公共 Game 模板并提供服务发现。
- MySQL 和 Redis 可共用物理实例，但数据必须按环境、GameServer 或测试账号形成明确隔离规则。
- GM 供开发维护和内部管理；个人本地配表验证不经过 GM 配置发布链。

公共 Login/Gate/GM 只有协议、路由、公共基础代码变化时才升级，不跟随策划表日常变化。

## 5. 个人 GameServer 环境

### 5.1 使用者工作流

策划或测试首次使用：

```text
向服务器开发申请个人 GameServer 和测试账号
-> 安装 JDK 25
-> 拉取项目 Git
-> 复制 local-dev/example.env 为 local-dev/.env
-> 填写已分配的 serverId
-> 运行转表脚本
-> 运行 GameServer 启动脚本
-> 使用绑定的测试账号登录自己的 GameServer
```

日常修改配置：

```text
修改 excel/*.xlsx
-> generate-config
-> restart-game
-> 客户端重新登录验证
```

不修改 Java 代码时，不要求安装 Maven，也不需要编译项目。

### 5.2 实例身份

每个人必须有唯一且稳定的实例 ID，由服务器开发分配：

```text
game-local-<用户名>
```

例如：

```text
game-local-alice
game-local-test03
```

服务器开发在 Nacos 中为该 serverId 创建完整启动配置，包括服务 IP、端口、共享 MySQL、Redis、运行模式以及其他启动参数。个人本地只保存最少信息：

```text
LOCAL_GAME_ID=game-local-alice
NACOS_URL=<内网 Nacos 地址>
NACOS_NAMESPACE=dev-local
```

Nacos 中的 `serverIp` 必须是公共 Gate 可以访问的使用者局域网固定地址或 DHCP 保留地址，不能是 `127.0.0.1`。IP 发生变化时由服务器开发修改分配记录和 Nacos 配置。macOS 防火墙和 Windows Defender 防火墙需要允许 JDK 25 监听已分配端口。

### 5.3 公共模板和本地覆盖

当前启动逻辑按 `serverId` 从 Nacos读取完整 ServerConfig，而且启动技能文件固定校验 Game 端口。为了支持个人实例，需要增加受控的本地开发模式：

1. 服务器开发根据 `GAME_LOCAL_TEMPLATE` 为每个已分配 serverId 创建 Nacos 配置。
2. 启动脚本只提交 Nacos 地址、namespace 和已分配 serverId；IP、端口、数据库及 Redis 配置均来自 Nacos。
3. Nacos 的 `configPath` 使用约定占位值 `${PROJECT_ROOT}/excel/serverConfig`，启动时由本机解析，避免保存 Windows/macOS 绝对路径。
4. `LOCAL_CONFIG` 模式关闭 GM 配置热更监听，始终加载本地 `excel/serverConfig`。
5. 正式模式继续保持当前严格参数和端口校验，不能被本地模式放宽。

建议 GameServer 增加显式参数入口：

```text
--profile local-config
--nacos <url>
--namespace dev-local
--server-id game-local-alice
```

脚本只能传白名单参数，禁止通过拼接 Shell 字符串启动。

### 5.4 账号路由与数据隔离

客户端登录请求已经携带 `gameServerId`，公共 Gate 可以连接个人 GameServer。账号和服务器分配由开发维护，不能信任客户端任意选择。需要完成以下约束：

- 增加“开发账号 -> gameServerId”分配记录，由服务器开发在 GM 或数据库管理页面维护。
- LoginServer 登录后只返回该账号绑定且在 Nacos 中健康的 GameServer。
- 客户端不提供自由选择服务器入口，默认进入账号绑定的 GameServer。
- GateServer 校验登录请求中的 `gameServerId` 与服务端账号分配一致，不能仅信任客户端字段。
- 一个测试账号同一时刻只绑定一个 GameServer；切换时沿用现有登出和 Redis 绑定逻辑。
- 默认每人使用独立测试账号前缀，例如 `alice_*`，避免玩家数据互相覆盖。
- 时间偏移必须属于个人 GameServer 配置或个人测试账号，不能写入公共 Redis 全局键。
- 排行榜、全服任务等共享数据必须增加 `serverId`/环境前缀，或在本地模式下禁用。

账号或 GameServer 重新分配只能由服务器开发操作，并记录操作人和时间。第一阶段可以共用 MySQL 和 Redis；如果后续出现清库、批量造数或全局状态冲突，再提供每人独立 schema 和 Redis database/prefix。不能允许个人脚本执行公共库 DROP/TRUNCATE。

## 6. 根目录最小启动环境

项目根目录新增：

```text
local-dev/
├─ README.md
├─ example.env
├─ VERSION
├─ bin/
│  ├─ windows/
│  │  ├─ setup.ps1
│  │  ├─ generate-config.ps1
│  │  ├─ start-game.ps1
│  │  ├─ stop-game.ps1
│  │  ├─ restart-game.ps1
│  │  └─ doctor.ps1
│  └─ macos/
│     ├─ setup.sh
│     ├─ generate-config.sh
│     ├─ start-game.sh
│     ├─ stop-game.sh
│     ├─ restart-game.sh
│     └─ doctor.sh
├─ runtime/
│  ├─ game-server.jar
│  ├─ config-builder.jar
│  ├─ lib/
│  └─ checksums.sha256
├─ logs/
├─ run/
└─ work/
```

Git 管理规则：

- 跟踪脚本、`example.env`、VERSION、JAR、依赖和校验文件。
- 忽略 `.env`、日志、PID、临时 Git 工作区和生成中间文件。
- `excel/serverConfig` 是否提交保持当前项目约定；本地启动前总是允许重新生成。
- JAR 更新必须伴随 VERSION、SHA-256 和构建 commit 更新。

直接把二进制提交到主 Git 会增加仓库历史体积，但可以实现策划/测试只执行 `git pull`。第一阶段按此需求执行；当依赖产物明显增大时，再迁移到内网 Git Release/制品库，脚本接口保持不变。

## 7. Windows 和 macOS 脚本职责

两套脚本行为必须一致。

### setup

- 检查 JDK 25。
- 创建 `.env`、logs、run 和 work。
- 校验填写的 `gameServerId` 已由开发在 Nacos 中分配。
- 展示 Nacos 分配的局域网 IP 和端口，并检查是否属于当前电脑且端口可用。
- 验证 Nacos、公共 Login/Gate、MySQL/Redis的可达性。

### generate-config

- 使用随仓库提供的 `config-builder.jar --mode=data-only`。
- 输入固定为根目录 `excel`。
- 输出固定为 `excel/serverConfig` 或临时目录校验成功后原子替换。
- 生成报告并在失败时保留旧的可用 TXT。
- 不生成或覆盖 Java Config 源码。

### start-game

- 执行 doctor 的必要检查。
- 防止同一目录重复启动。
- 清理已经失效的 PID 文件。
- 使用参数数组启动 `runtime/game-server.jar`。
- 日志写入 `local-dev/logs/game-YYYYMMDD.log`。
- 等待端口监听和 Nacos 注册成功后返回成功。

### stop/restart

- 只停止 PID 文件记录且命令行匹配当前 GameServer 的进程。
- 禁止批量结束机器上的全部 Java 进程。
- restart 先优雅停止，超时后才结束当前实例。

### doctor

至少检查：

- Java 主版本等于 25。
- Game JAR、builder JAR 和依赖校验和正确。
- `.env` 字段完整。
- Game ID 命名合法且存在对应的 Nacos 配置。
- Nacos 配置中的 IP 属于当前电脑，端口未被其他进程使用。
- 配置目录存在且包含 TXT。
- 端口未占用。
- 本机 IP 不是 loopback，且属于活动网卡。
- Nacos 和公共 Gate 可达。
- 当前 GameServer 代码支持本地配置 schema。

## 8. GameServer JAR 更新规则

策划和测试不负责编译 Java。开发在以下情况更新根目录运行包：

- GameServer 业务代码变化。
- core、config、proto 变化。
- ConfigManager/Checker 或配置 schema 变化。
- Maven 运行依赖变化。

开发发布本地运行包的流程：

```text
mvn 构建和测试
-> 生成 thin game-server.jar + lib
-> 生成 config-builder.jar
-> 写入 VERSION/build-info.json
-> 生成 checksums.sha256
-> Windows 和 macOS doctor/start 冒烟测试
-> 提交 Git
```

`build-info.json` 至少记录：

```text
appVersion
sourceCommit
configSchemaMin
configSchemaMax
configBuilderVersion
buildTime
javaVersion
```

策划执行 `git pull` 后，脚本发现 VERSION 变化时应先停止旧 GameServer，再启动新包。依赖层未变化时 Git 只更新业务 JAR 和版本文件。

## 9. 时间调试方案

本地测试和集成测试直接修改 GameServer 所在操作系统的系统时间，不实现业务时间偏移量。

原因是部分第三方 JAR 会直接读取系统时间，例如调用 `System.currentTimeMillis()`、`Instant.now()` 或依赖系统时钟的调度器。业务代码中的 TimeProvider 或时间偏移只能覆盖自有代码，无法保证第三方依赖、缓存过期、定时任务和超时逻辑使用同一时间基准。

环境要求：

- 策划和测试的个人 GameServer 运行在各自电脑上，可以直接修改该电脑系统时间。
- 集成测试 GameServer 必须运行在专用物理机或虚拟机上，由测试独占修改系统时间。
- 公共 LoginServer、GateServer、GMServer、Nacos、MySQL 和 Redis 不能与需要改时间的 GameServer 部署在同一台机器上。
- 禁止修改公共基础设施和开发人员公共服务器的系统时间。
- 自动化脚本不得静默修改系统时间；改时间必须由使用者明确执行并能看到当前时间。

修改时间前后的操作规范：

1. 停止当前 GameServer，避免时间跳变发生在业务处理过程中。
2. 暂停该测试机器的自动时间同步，记录原始时区和当前时间同步状态。
3. 修改系统时间后重新启动 GameServer并执行测试。
4. 测试完成后停止 GameServer，恢复正确时间和自动时间同步。
5. 确认时间同步完成后再启动常规测试实例。

已知影响必须纳入测试说明：

- HTTPS/TLS 证书可能因为系统时间超出有效期而验证失败。
- Login Token、JWT、Redis TTL、缓存过期和数据库时间可能与公共服务产生偏差。
- 时间回拨可能导致定时任务重复执行、排序异常或持续时间为负数。
- 时间前跳可能触发大量到期任务、活动结算和缓存失效。
- 日志时间与公共服务器不一致，排查问题时必须记录测试机器的时间偏移。

如果跨机器协议依赖严格的 Token 有效期或时间窗口，应为内网测试账号提供独立、可审计的宽松策略，而不是修改公共 Login/Gate 的系统时间。

## 10. 网络与安全

- 个人 GameServer 只监听内网地址，不暴露公网。
- 内网防火墙仅允许公共 Gate/Nacos/运维网段访问 Game 端口。
- 策划/测试不持有生产数据库、Redis、Nacos或 Git 写入凭据。
- 本地 `.env` 不提交 Git。
- 公共开发环境使用独立 Nacos namespace、数据库和 Redis 前缀，禁止连接生产环境。
- GameServer 不直接接收外部客户端连接，客户端仍统一经过公共 Login/Gate。

## 11. 实施阶段

### 阶段一：可启动

1. 产出 thin GameServer JAR、lib 和 config-builder DATA_ONLY 模式。
2. 增加 `LOCAL_CONFIG` 启动 profile 和本地参数覆盖。
3. 建立独立的内网开发 namespace、公共 Game 模板和开发分配流程。
4. 完成 Windows PowerShell 和 macOS shell 脚本。
5. 验证公共 Gate 能访问 Windows/macOS 本地 GameServer。

### 阶段二：可维护

1. 增加 doctor、PID 管理、健康检查和结构化日志。
2. 增加账号与 GameServer 分配管理，并让 Login/Gate 执行服务端绑定校验。
3. 增加账号、Redis key 和共享业务数据隔离。
4. 建立 Game 本地产物构建、校验和提交规范。

### 阶段三：完整体验

1. 收口业务时间到 TimeProvider，并提供个人时间控制。
2. 增加本地配置校验报告页面或轻量工具。
3. 自动清理 Nacos 中离线的个人节点和过期账号绑定。
4. 根据仓库体积决定是否把二进制迁移到内网制品服务。

## 12. 验收标准

- 新使用者安装 JDK 25、拉取 Git、填写 `.env` 后可以在 10 分钟内启动个人 GameServer。
- 使用者不安装 Maven也能转表和启动。
- Windows x64、macOS Intel 和 macOS Apple Silicon 的脚本行为一致。
- 两名使用者可以同时运行不同配置的 GameServer，登录各自绑定账号后由公共 Gate 正确路由，不能进入对方服务器。
- 转表失败不会破坏上一份可启动配置。
- 个人 GameServer 停止不影响公共 Login/Gate/GM 和其他人的实例。
- 业务代码没有变化时，策划只需修改 Excel、转表、重启即可验证。
- 线上 Docker 发布链和 GM PREPARE/COMMIT 链不被本地模式绕过或放宽。
