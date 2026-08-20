# SLG 项目二次开发路线评估

## 1. 文档目的

本文用于比较三种 SLG 服务器开发路线：

1. 基于自研 `miniServer` 二次开发。
2. 基于公司现有线上项目进行大范围改造。
3. 基于其他同事已有的 SLG 或其他项目二次开发。

本文只讨论服务器架构、核心能力、改造范围、风险和验收指标，不讨论具体玩法数值。

## 2. 已确定的 SLG 基础约束

### 2.1 目标场景

- 休闲/SLG 类型项目。
- 支持大地图、行军、资源点、怪物、农田、掉落物等动态场景对象。
- 支持联盟、联盟战争、跨服活动和跨服地图。
- 可能存在万人同图需求。
- 单张逻辑地图可能为 `1000 x 1000 = 1,000,000` 个格子。
- 第一阶段不做不同进程之间的地图分片。
- 使用一个 `SceneServer` 进程，内部运行多个 `SceneShard`。
- Java 21，使用 ZGC/Generational ZGC 方向。
- `BattleServer` 负责战斗计算和战斗结果落库；未完成战斗可以丢弃。
- 战斗结果由发起方启动时恢复、消费和确认，消费完成后再清理。
- 服务器之间优先使用一套自研 TCP 通信底座，支持 Request/Response、Command、Event。
- 重连由传输层负责，是否补发、重试、补偿由业务层决定。

### 2.2 目标架构原则

```text
# 玩家和玩家业务数据的权威拥有者
玩家状态       -> GameServer/GameShard 拥有
# 普通分区地图及其中的动态对象
普通地图状态   -> SceneServer/SceneShard 拥有
# 跨服战争或赛季地图的动态状态
跨服地图状态   -> Cross Scene/War Scene 拥有
# 战斗实例和战斗计算过程
战斗计算       -> BattleServer 拥有战斗实例
# 活动阶段、积分和奖励规则
活动规则       -> Activity Module/Activity Service 拥有
# 同一个业务对象不能被多个线程同时直接修改
核心状态修改   -> 单一拥有者串行执行
# 异步线程只能计算，结果必须回到权威线程校验后应用
异步计算结果   -> 回投状态拥有者校验后应用
```

## 3. 三种路线总览

| 方案 | 主要优点 | 主要问题 | 初步判断 |
|---|---|---|---|
| `miniServer` 二次开发 | 架构可控、Java 21 方向一致、可以从设计阶段建设 SLG 能力 | 需要补齐完整游戏服务器能力，前期开发量最大 | 适合长期建设新 SLG 基础平台 |
| 公司现有项目大改 | 有成熟登录、WebSocket、GM、线上运维和玩家基础功能 | 现有核心模型与 SLG 目标冲突，改造可能变成边运行边重写 | 不建议直接在原项目核心上硬改，建议复用外围、旁路建设 SLG 核心 |
| 其他同事已有项目二开 | 可能已有地图、战斗、联盟和运营能力 | 代码质量、所有权、设计边界和指标可能不清楚 | 必须先通过技术尽调和基准压测 |

## 4. 方案一：基于 miniServer 二次开发

### 4.1 适合的定位

将 `miniServer` 定位为新的 SLG 服务器基础平台，而不是继续复制旧项目的协议和数据库模型。

建议复用或沉淀：

- Java 21 运行环境。
- 模块化 Maven 工程结构。
- 配置、协议、RPC、日志和监控基础设施。
- 统一服务器启动和优雅关闭能力。
- 新的 `scene`、`battle`、`activity` 业务模块。

### 4.2 必须新增的代码模块

```text
# Gradle 多模块工程根目录
server/
# Gradle 工程入口，声明所有子项目
├── settings.gradle
# 根项目统一插件、仓库、Java 版本和公共配置
├── build.gradle
# Gradle Wrapper，保证构建使用固定 Gradle 版本
├── gradlew
├── gradlew.bat
# 公共工具、错误码、ID、时间和基础类型
├── common/
# 配置模型、配置加载和配置版本管理
├── config/
# Protobuf/自研协议定义及生成代码
├── proto/ 或 protocol/
# 服务器之间的 TCP 传输和投递能力
├── inter-server-transport/
# 玩家数据模型和玩家业务规则，领域模型与计算逻辑放在同一模块
├── game/
# 玩家业务服务进程
├── game-server/
# 地图、格子、行军、AOI、Tick 和场景规则，统一放在 scene 模块
├── scene/
# SceneShard、Region 和场景生命周期运行时
├── scene-runtime/
# 场景服务启动入口和网络接口
├── scene-server/
# 战斗模型、战斗状态和战斗计算规则，统一放在 battle 模块
├── battle/
# 战斗计算服务进程
├── battle-server/
# 活动模型、阶段、积分和奖励规则，统一放在 activity 模块
├── activity/
# 本服/跨服活动编排服务
├── activity-service/
# 跨服路由、协调和跨服命令
├── cross-server/
# 数据库、快照、Outbox/Inbox 等持久化能力
├── persistence/
# 指标、日志、Trace 和健康检查
└── observability/
```

公司项目使用 Gradle 时，建议在 `settings.gradle` 中明确声明子项目：

```groovy
// settings.gradle：声明 SLG 服务器的 Gradle 子项目
rootProject.name = 'slg-server'

include(
    // 公共基础库
    ':common',
    // 协议定义和生成代码
    ':proto',
    // 自研 TCP 通信底座
    ':inter-server-transport',
    // 玩家数据模型和玩家业务规则
    ':game',
    ':game-server',
    // 地图、场景规则、AOI 和 Tick 逻辑
    ':scene',
    ':scene-runtime',
    ':scene-server',
    // 战斗模型和战斗计算逻辑
    ':battle',
    ':battle-server',
    // 活动和跨服
    ':activity',
    ':activity-service',
    ':cross-server',
    // 持久化和可观测性
    ':persistence',
    ':observability'
)
```

根目录 `build.gradle` 负责公共构建约束，业务模块只声明自己的依赖：

```groovy
// 根 build.gradle：统一 Java 版本、编码和仓库
allprojects {
    group = 'com.company.slg'
    version = '1.0.0-SNAPSHOT'

    repositories {
        mavenCentral()
    }
}

subprojects {
    // 所有服务和公共模块统一使用 Java 21
    apply plugin: 'java-library'

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType(JavaCompile).configureEach {
        options.encoding = 'UTF-8'
    }
}
```

建议将以下内容统一放到 Gradle convention plugin 或公共脚本中：

```text
# Java 版本和编译参数
Java 21 toolchain
# 单元测试、集成测试和压测任务
test / integrationTest / loadTest
# Protobuf 或自研协议代码生成
generateProto 或 generateProtocol
# 统一依赖版本和安全扫描
dependencyManagement / dependencyCheck
# 服务打包和启动参数
application / distribution
# Docker 镜像和部署产物
bootJar 或自定义 serverDist
```

这里不再把同一业务拆成 `domain` 和 `engine` 两个 Gradle 子项目：

```text
# scene 模块同时包含地图模型、场景规则、路径、AOI 和 Tick 逻辑
scene/
# battle 模块同时包含战斗模型、战斗规则和战斗计算逻辑
battle/
# activity 模块同时包含活动模型、阶段、积分和奖励规则
activity/
```

需要独立的部分仍然单独保留：

```text
# 负责 SceneShard、Region、调度和运行时生命周期
scene-runtime/
# 负责 SceneServer 进程启动、网络接入和服务暴露
scene-server/
# 负责 BattleServer 进程启动、网络接入和结果投递
battle-server/
# 负责数据库、快照、Outbox/Inbox 等基础设施
persistence/
```

这样做的目标是让同一业务的模型和规则在一个模块内协同演进，同时避免把服务进程、运行时容器和基础设施代码混进业务模块。

### 4.3 miniServer 需要重点补齐的能力

#### A. SceneServer 和 SceneShard

```text
# 一个 SceneServer JVM 进程
一个 SceneServer JVM
  # 所有 SceneShard 共享的只读地图配置
  ├── StaticMapData
  # 根据 tileId/regionId 把命令路由到本地 Shard
  ├── SceneRouter
  # 每个 Shard 独占一部分 Region 的动态状态
  ├── SceneShard-0
  ├── SceneShard-1
  ├── SceneShard-N
  # 维护 Region 到 Shard 的归属关系
  └── RegionDirectory
```

需要实现：

- 全局坐标和 `tileId` 转换。
- 静态地图数组。
- `Region -> SceneShard` 映射。
- Region 状态单一拥有者。
- SceneShard 本地命令队列。
- SceneShard Tick 循环。
- 跨 Region 对象转移。
- AOI 和增量广播。
- 资源点、怪物、农田、掉落物等动态对象。
- 时间轮或到期事件队列。
- 地图快照和动态对象恢复。

#### B. 地图数据模型

```text
# 每个格子的固定数据：适合使用 byte[]/int[] 等紧凑数组
# 静态数据不会因为玩家操作频繁变化
静态数据：
  # 地形类型，例如平原、山地、河流
  terrain[]
  # 地图配置 ID，例如资源点、城市、出生点
  configId[]
  # 阻挡、可建造、可刷新等位标记
  flags[]
  # 资源/怪物/掉落物刷新规则 ID
  spawnRuleId[]

# 运行时才存在的数据：只有发生业务变化的格子或对象才创建
# 没有动态状态的格子不需要创建对应对象
运行时数据：
  # 格子占领、锁定、战斗等状态
  tileId -> TileRuntimeState
  # 资源点、怪物、农田、掉落物等动态对象
  objectId -> SceneObject
  # 反向索引：某个格子有哪些动态对象
  tileId -> objectIds
  # 到期事件，例如资源刷新、掉落物过期、行军到达
  dueTime -> ScheduledSceneEvent
```

建议：

- 静态地形、阻挡、配置使用数组。
- 占领、城市、战斗、驻军使用动态状态对象。
- 资源、怪物、农田、掉落物统一抽象为 `SceneObject`。
- 不为每个格子创建独立 Timer。
- 动态对象没有业务意义后必须回收。

#### C. 玩家/联盟/活动串行化

```text
# 同一个玩家的核心操作进入同一个玩家分片
playerId   -> PlayerShard
# 同一个联盟的资源和成员变更进入同一个联盟分片
allianceId -> AllianceShard
# 同一个地图区域的状态由同一个场景分片串行修改
regionId   -> SceneShard
# 同一个活动的阶段和积分由同一个活动分片处理
activityId -> ActivityShard
```

同一聚合对象的核心状态修改必须串行执行，异步任务只能返回结果，不能直接修改对象。

#### D. BattleServer

需要建设：

- 战斗输入快照。
- `battleId`、`requestId`、`ruleVersion`、`randomSeed`。
- 战斗计算实例。
- 战斗结果持久化。
- 结果查询。
- 发起方启动恢复。
- 消费 ACK。
- 多消费者消费记录。
- 结果清理和过期处理。

战斗结果生命周期：

```text
# BattleServer 已完成计算并已将结果持久化
COMPUTED
  # 已发送给发起方，但可能还未完成业务提交
  -> DELIVERED
  # 发起方已幂等应用结果并确认消费
  -> CONSUMED
  # 已经过保留期，且所有必需消费者都完成消费
  -> CLEANED
```

未完成的内存战斗可以丢弃，但发起方需要有 `TIMEOUT/ABANDONED` 状态和业务补偿流程。

#### E. 自研 TCP 通信底座

建议从“能发包”升级为统一的 `InterServerTransport`：

```text
# 传输层：只负责 TCP 连接、帧解析和连接状态
transport/
# TCP 长连接建立、发送、关闭和连接池
  ├── TCP Connection
# 处理拆包、粘包、长度字段和消息边界
  ├── Frame Codec
# 检测连接是否存活，避免半开连接长期占用资源
  ├── Heartbeat
# 连接断开后的自动重连，不代表业务消息一定补发
  ├── Reconnect
# 发送队列达到上限时的限流、拒绝或降级
  ├── Backpressure
# CONNECTED、DISCONNECTED、RECONNECTING 等连接状态
  └── Connection State

# 投递层：负责消息语义、确认、重试和恢复
delivery/
# 请求-响应：调用方等待明确的业务返回值
  ├── Request/Response
# 命令：要求目标服务执行一个业务动作
  ├── Command
# 事件：通知其他服务发生了某个业务事实
  ├── Event
# 确认：区分已接收、已处理和已持久化
  ├── ACK
# 发送方保存尚未确认的消息，支持重启恢复
  ├── Outbox
# 接收方记录已处理消息，避免重复执行
  ├── Inbox
# 超过重试次数或无法处理的消息，进入可审计的死信
  └── Dead Letter
```

每条重要消息至少包含：

```text
# 消息唯一 ID：用于去重和定位单条消息
messageId
# 业务请求 ID：一次请求可能对应多次传输尝试
requestId
# 调用链 ID：串联 GameServer、SceneServer、BattleServer 日志
traceId
# 发送方服务实例 ID
sourceServerId
# 接收方服务实例 ID
targetServerId
# 业务聚合 ID，例如 playerId、allianceId、regionId、battleId
aggregateId
# 同一聚合对象的顺序号或状态版本
sequence/version
# 消息允许等待到的时间点，避免无限阻塞
deadline
# 协议版本：用于灰度发布和前后版本兼容
protocolVersion
```

TCP 层负责连接重连和传输可靠性；业务层决定消息是否补发、重试、补偿或放弃。

### 4.4 miniServer 方案的主要风险

- 需要从基础设施开始建设，前期业务交付速度较慢。
- 地图、活动、联盟、战斗需要重新设计，不能只靠已有模块拼接。
- 需要团队长期维护自研 TCP、快照、恢复和幂等机制。
- 如果没有尽早建立压测和可观测性，架构风险会延迟暴露。

### 4.5 miniServer 方案的适用结论

适合：

- 公司愿意投入长期基础设施建设。
- SLG 不是一次性短项目，而是长期产品线。
- 希望彻底摆脱旧项目的同步 DB、JSON 内存模型和同步 RPC 约束。
- 团队能够承担 SceneServer、战斗、活动和数据恢复的建设。

## 5. 方案二：基于公司现有项目大改

### 5.1 现有项目特征

根据目前掌握的信息，现有项目大致具备：

- WebSocket + JSON。
- 阿里云负载均衡。
- 固定分区分服。
- 玩家数据大量使用 String/JSON。
- MyBatis 同步数据库访问。
- 登录和普通协议使用线程池。
- 没有玩家级串行执行模型。
- Thrift/Nifty 同步 RPC。
- RPC 缺少成熟的异步、重放和失败处理。
- Spring、Jetty、MySQL、Redis、Quartz 等基础依赖已存在。

### 5.2 可以直接复用的内容

```text
# 复用账号、角色和登录校验
账号和登录流程
# 复用客户端长连接接入层
WebSocket 接入
# 复用固定分区到服务器的路由
分区路由
# 复用客户端协议解码和响应格式
客户端协议基础
# 复用 GM、后台、封禁和运营操作能力
GM/后台能力
# 复用已有配置读取流程，但新 SLG 配置建议独立版本化
部分配置加载
# 复用启动、发布、日志目录和运维脚本
线上部署和运维脚本
# 复用已有账号和历史数据，但需要数据迁移边界
现有数据库和账号数据
# 复用基础日志和监控，但补齐 Scene/Battle 指标
基础监控和日志体系
```

### 5.3 必须进行的核心改造

#### A. 不能继续使用原有协议线程模型承载 SLG 核心逻辑

需要增加：

```text
# Netty/WebSocket 线程只负责收包、解码和投递
网络线程
  # 根据 playerId、regionId、allianceId 选择业务拥有者
  -> 协议路由
  # 由对应 Shard 串行修改核心业务状态
  -> Player/Scene/Alliance Shard
  # DB、RPC、Redis 不得阻塞核心业务线程
  -> 独立 DB/RPC/Redis IO
```

不能让同步 MyBatis 和同步 RPC 直接占用核心业务线程。

#### B. 增加 SceneServer/Scene 模块

现有 GameServer 不应该继续承担所有地图逻辑。建议旁路新增：

```text
# 继续承载已有玩家和接入能力
现有 GameServer
  # 只负责调用场景服务，不直接修改场景对象
  -> SceneClient
  # 新增的地图权威服务
  -> 新 SceneServer
       # 地图状态的串行执行单元
       ├── SceneShard
       # 地图逻辑区域
       ├── Region
       # 兴趣范围和增量广播
       ├── AOI
       # 资源、怪物、农田、掉落物等动态对象
       ├── SceneObject
       # 行军到达、资源刷新、掉落过期等定时事件
       └── Timer
```

这样可以先保留现有登录和玩家系统，逐步将地图、行军和动态场景从旧 GameServer 中迁出。

#### C. 增加玩家和联盟串行化

至少覆盖：

- 资源变更。
- 扣费和发奖。
- 部队修改。
- 建筑和科技。
- 联盟资源。
- 联盟领地。
- 活动积分。

#### D. 逐步降低 String/JSON 的内部依赖

不建议全项目一次性改造。应先：

- 新 SLG 模块使用 Java 领域对象。
- 旧玩家模块增加领域适配层。
- 优先改金币、部队、城市、联盟资源等高频核心数据。
- 避免同一请求内重复 JSON 解析和序列化。

#### E. 重做或旁路现有 RPC

不建议直接把旧 Thrift/Nifty 作为 SLG 核心跨服协议。可以：

```text
# 旧项目已经依赖的 RPC，不影响存量线上服务
旧 RPC：继续兼容已有线上业务
# 新 SLG 服务统一使用的新 TCP 通信底座
新 InterServerTransport：服务 SLG 新模块
```

最终逐步替换，而不是一次性切换全部服务。

#### F. 数据持久化改造

需要新增：

- 玩家数据版本。
- 关键操作幂等。
- 地图动态状态快照。
- 行军和定时任务恢复。
- Outbox/Inbox 或等价可靠记录。
- 战斗结果消费和清理。

### 5.4 现有项目大改的主要风险

- 线上稳定代码和新 SLG 代码互相污染。
- 旧模型会迫使新业务继续使用 JSON 和同步 DB。
- 改动容易形成“新旧两套状态同时存在”。
- 任何玩家数据、登录、WebSocket 改造都可能影响现有线上业务。
- 如果采用大爆炸式重构，发布和回滚困难。

### 5.5 推荐的改造方式

不建议：

```text
# 不建议把线上旧 GameServer 一次性改造成所有 SLG 核心
直接在旧 GameServer 内部把所有模块改成 SLG 架构
```

建议：

```text
# 继续使用账号、登录、GM、WebSocket 等成熟外围能力
保留旧项目的外围能力
# 新增地图、战斗和活动服务
新增 SceneServer、BattleServer、ActivityService
# 使用适配器和统一 TCP 通信层隔离新旧模型
通过适配器和自研 TCP 接入旧 GameServer
# 按模块逐步迁移，而不是一次性切换
逐步迁移地图和核心玩法
```

也就是：

```text
# 复用稳定的接入和运营能力，隔离不适合 SLG 的旧核心模型
复用外围，隔离核心
```

### 5.6 公司现有项目方案的适用结论

适合：

- 项目必须快速利用已有登录、玩家和运营能力。
- 公司不愿意维护两套账号和基础接入系统。
- 可以接受新增核心服务，而不是修改所有旧代码。
- 计划通过渐进迁移降低线上风险。

不适合：

- 直接把旧 GameServer 改成 SLG 的全部核心。
- 直接在旧 JSON/String 模型上继续堆叠地图和联盟逻辑。
- 直接复用没有可靠机制的同步 Thrift RPC 做跨服战争。

## 6. 方案三：基于其他同事已有项目二次开发

### 6.1 不能只看“有没有 SLG 功能”

必须确认该项目是否真正具备：

- 大地图运行模型。
- 玩家级/联盟级串行化。
- 地图分片和 AOI。
- 行军和定时任务恢复。
- 战斗结果幂等。
- 跨服活动和跨服地图。
- 可观测性。
- 压测数据。
- 故障恢复方案。

如果只有玩法代码，但没有运行模型，仍然需要大规模基础设施改造。

### 6.2 技术尽调必须拿到的资料

#### 架构资料

- 服务拓扑图。
- 线程模型。
- 网络协议模型。
- 玩家状态归属。
- 地图状态归属。
- 跨服调用链。
- 数据库和 Redis 拓扑。
- 消息队列和重试模型。

#### 运行资料

- 单服在线上限。
- 单服 QPS。
- 单地图最大人数。
- 单场战斗最大参与人数。
- 运行时堆内存。
- GC 日志。
- 数据库 QPS 和慢 SQL。
- RPC P95/P99。
- 历史事故和恢复记录。

#### 代码资料

- 是否有协议 IDL。
- 是否有版本兼容机制。
- 是否有单元测试和集成测试。
- 是否能本地启动。
- 是否能独立压测。
- 是否存在大量全局变量和静态状态。
- 是否存在业务直接访问数据库。
- 是否存在复制粘贴的地图和战斗代码。

### 6.3 外部项目必须达到的基础指标

以下指标是进入二次开发评审的最低基线，不代表最终上线目标。

| 类别 | 最低要求 |
|---|---|
| 网络 | 长连接稳定，协议有版本和消息 ID |
| 线程 | 网络、业务、DB、RPC 线程池隔离 |
| 玩家 | 同一玩家核心操作有顺序保证 |
| 地图 | 至少支持区域/分片或可扩展为 SceneShard |
| AOI | 不做全图广播，支持增量同步 |
| 定时任务 | 支持重启恢复和重复执行保护 |
| 战斗 | 输入快照、规则版本、结果幂等 |
| RPC | 有超时、请求 ID、失败分类、重试边界 |
| 数据 | 关键数据有版本和恢复机制 |
| 观测 | 指标、日志、线程池、DB、RPC 可观测 |
| 发布 | 支持优雅关闭、灰度、回滚 |
| 测试 | 能运行稳定性、协议和容量测试 |

### 6.4 外部项目的红线问题

出现以下情况时，不建议直接二开：

- 玩家、地图和联盟状态都放在全局静态对象里。
- 每个协议直接同步访问数据库。
- 同一玩家操作没有顺序保证。
- 地图每 Tick 遍历全部格子。
- 地图广播没有 AOI。
- 战斗结果没有唯一 ID 和消费确认。
- RPC 没有超时，或存在无限重试。
- 服务器重启后无法恢复行军和活动状态。
- 不能提供线上容量数据和事故数据。
- 代码无法拆分核心业务和基础设施。

## 7. 三种方案的关键改造对比

| 能力 | miniServer | 公司现有项目 | 其他同事项目 |
|---|---|---|---|
| 网络层 | 重新建设或规范化 | 可复用 WebSocket，但 SLG 内部通信建议新增统一 TCP 层 | 需要尽调后决定 |
| 玩家模型 | 可直接按领域对象设计 | 需要逐步隔离 String/JSON | 看现有模型质量 |
| 玩家串行化 | 新设计即可支持 | 需要补充并逐步接入 | 必须验证 |
| 地图 | 从零建设 SceneServer/SceneShard | 建议旁路新增 SceneServer | 看是否已有地图分片 |
| 跨服 | 新设计 | 需要替换或旁路旧同步 RPC | 看现有可靠性 |
| 战斗 | 新建 BattleServer | 可新增独立 BattleServer | 看是否已有战斗结果机制 |
| 数据恢复 | 新设计 | 需要补齐大量旧数据恢复流程 | 必须检查已有能力 |
| 线上风险 | 新项目风险 | 旧项目线上风险最高 | 取决于兼容性 |
| 初期速度 | 较慢 | 复用外围后中等 | 可能最快，也可能因理解成本变慢 |
| 长期可控性 | 高 | 中低，取决于隔离程度 | 不确定 |

## 8. 统一的服务器基础指标

以下指标适用于三种路线，最终应通过压测校准。

### 8.1 SceneServer

| 指标 | 第一版基线 |
|---|---:|
| 单地图静态格子 | 1,000,000 |
| 单逻辑地图在线目标 | 10,000 |
| SceneShard 数量 | 启动配置，建议 4～16 |
| 普通地图 Tick | 500ms～1s |
| Tick P99 占用 | 小于 Tick 周期的 50% |
| 单命令处理 P99 | 小于 50ms |
| 单玩家待处理命令 | 小于 20 |
| Shard 队列持续堆积 | 必须告警 |
| AOI | 增量、区域化、禁止全图广播 |
| 动态对象 | 只保存活跃对象，支持回收 |

### 8.2 网络和 RPC

| 指标 | 第一版基线 |
|---|---:|
| 普通内部调用 P99 | 小于 100ms |
| 跨服命令 P99 | 小于 300～800ms，按业务区分 |
| RPC 无 deadline | 禁止 |
| 无限重试 | 禁止 |
| 关键命令 | 必须有幂等 ID |
| 事件 | 必须有 messageId、version、ACK 规则 |
| 连接断线 | 传输层自动重连 |
| 消息补发 | 业务层决定 |

### 8.3 数据和恢复

| 指标 | 第一版基线 |
|---|---:|
| 关键玩家数据 | 可恢复、幂等、可确认 |
| 地图动态状态 | 快照 + 增量/任务恢复 |
| 战斗结果 | BattleServer 落库，发起方消费 ACK |
| 未完成战斗 | 可丢弃，但必须有超时补偿 |
| 结果重复消费 | 必须安全 |
| 定时任务 | 重启后可恢复 |
| 重要事件 | 不能只保存在内存 |
| 结果清理 | 消费确认后延迟清理 |

### 8.4 可观测性

每个服务必须提供：

```text
# 当前服务或分区的在线玩家数量
在线人数
# WebSocket/TCP 当前连接数量
连接数
# 每秒收到和处理的协议数量
协议 QPS
# 协议延迟分位数，定位长尾请求
协议 P50/P95/P99
# 单个 SceneShard 完成一轮 Tick 的耗时
SceneShard Tick 耗时
# SceneShard 尚未处理的命令数量
Shard 队列长度
# 地图中的动态对象总数
动态对象数量
# AOI 产生的增量广播消息数量
AOI 广播量
# RPC 成功、超时和重试统计
RPC 成功率、超时率、重试数
# TCP 连接状态、重连次数和半开连接数量
TCP 连接状态
# 数据库连接池占用和慢 SQL 数量
DB 连接池和慢 SQL
# Redis P99 延迟、命中率和内存使用率
Redis 延迟和内存
# Java 堆、ZGC、线程和堆外内存使用情况
JVM 堆、GC、线程、Native Memory
# BattleServer 已落库但还没有完成消费的结果数量
战斗结果待消费数
# 无法处理并进入死信队列的消息数量
死信数量
```

## 9. 推荐决策

### 推荐优先级

```text
# 适合长期建设和掌握核心架构
第一选择：miniServer 作为新 SLG 基础平台
# 适合快速复用登录、GM、WebSocket 等稳定外围能力
第二选择：公司现有项目外围复用 + 新 SLG 核心服务旁路建设
# 只有在指标、代码和所有权都验证通过后才选择
第三选择：其他同事项目，必须通过技术尽调和基准测试后决定
```

### 如果公司强调快速复用现有资源

采用混合路线：

```text
# 继续使用已有且线上稳定的外围能力
旧项目复用：账号、登录、GM、WebSocket、部署、部分玩家基础能力
# 新建适合 SLG 的核心状态、地图、战斗、活动和数据恢复能力
新架构建设：SceneServer、SceneShard、BattleServer、ActivityService、跨服通信和数据恢复
```

### 不建议的路线

```text
# 会影响线上稳定性，也会把旧模型带入新项目
直接把公司旧 GameServer 改成所有 SLG 核心
# 新 SLG 模块不应继续依赖字符串作为核心业务状态
继续使用 String/JSON 作为新模块的核心内存模型
# 同步阻塞调用无法支撑复杂跨服战争链路
继续用同步 DB 和同步 Thrift RPC 串联跨服战争
# 没有压测数据不能承诺万人同图
没有指标和压测就承诺万人同图
# 复制代码但不明确状态拥有者会产生双写和一致性问题
复制其他项目的地图和战斗代码但没有状态边界
```

## 10. 立项前必须完成的验证

在选择最终路线前，三种候选项目都应该完成同一套 PoC：

1. 加载 `1000 x 1000` 地图。
2. 启动多个 `SceneShard`。
3. 创建至少 100 万格子的静态数据。
4. 创建和回收资源、怪物、农田、掉落物。
5. 模拟 1 万个地图玩家对象。
6. 验证 AOI 增量广播。
7. 验证跨 Region 行军和对象转移。
8. 验证热点 Region 独占 Shard。
9. 验证 BattleServer 结果落库、恢复、消费和清理。
10. 模拟 SceneServer、GameServer、BattleServer 重启。
11. 采集 Tick、队列、GC、内存、网络、DB 和 RPC 指标。
12. 输出可比较的容量和故障恢复报告。

最终选择不应该依据“哪个项目代码最多”或“哪个项目已经有玩法”，而应该依据：

```text
# 每类核心状态是否只有一个主要写入者
能否明确拥有状态
# 同一玩家、地图、联盟、战斗的操作是否有顺序和重复保护
能否保证顺序和幂等
# 是否能在 100 万格子和动态对象规模下稳定运行
能否承载地图和动态对象
# 宕机后是否能恢复玩家、地图、任务和战斗结果
能否恢复关键数据
# 是否有可复现的压测和容量报告
能否通过指标证明容量
# 团队是否理解并能长期维护这套架构
能否让团队长期维护
```

## 11. 已确定的地图、RPC 和数据落地方案

本节记录目前讨论后形成的技术基线，后续可以在 PoC 和压测后调整具体数值，但整体边界不建议随意改变。

### 11.1 100 万格子地图方案

#### 11.1.1 静态数据与动态数据分离

一张地图按 `1000 x 1000 = 1,000,000` 个格子设计。静态地图配置和运行时动态状态分开保存：

```text
# 所有格子都有的固定数据，适合使用 byte[]/int[] 等紧凑数组
StaticMapData
  # 地形类型，例如平原、山地、河流
  terrain[]
  # 地图配置 ID，例如城市、资源点、出生点
  configId[]
  # 阻挡、可建造、可刷新等位标记
  flags[]
  # 资源、怪物、农田、掉落物的刷新规则 ID
  spawnRuleId[]

# 只有发生业务变化时才创建的运行时状态
DynamicMapState
  # 格子是否被玩家或联盟占领
  tileId -> TileRuntimeState
  # 资源点、怪物、农田、掉落物、建筑等动态对象
  objectId -> SceneObject
  # 反向索引：一个格子上有哪些动态对象
  tileId -> objectIds
  # 资源刷新、怪物重生、掉落物过期、行军到达等事件
  dueTime -> ScheduledSceneEvent
```

核心原则：

- 查询静态地图不会创建动态对象。
- 玩家占领、采集、驻军、战斗、建造等真正改变状态的操作才创建动态状态。
- 动态对象没有业务意义后要回收，不能让动态对象数量只增不减。
- 资源点、怪物、农田、掉落物统一抽象为类型明确的 `SceneObject`；对象的 `state` 使用类型化 Java 状态对象承载扩展属性，不再把内存模型序列化成 JSON 字符串。
- 不为每个格子创建独立的 Java Timer，统一使用时间轮或到期事件队列。

#### 11.1.2 Chunk、Region 和 SceneShard

逻辑上仍然是一张完整地图，运行时在同一个 `SceneServer` JVM 内划分多个 `SceneShard`：

```text
# 一个 SceneServer JVM 进程
SceneServer
  # 所有 Shard 共享的只读静态地图
  ├── StaticMapData
  # 根据 tileId/regionId 找到本地拥有者
  ├── SceneRouter
  # 地图按 Region 划分，Region 是状态归属单位
  ├── RegionDirectory
  # 每个 Shard 独占一部分 Region 的动态状态
  ├── SceneShard-0
  ├── SceneShard-1
  └── SceneShard-N
```

推荐的层次关系：

```text
# 最小地图数据组织单元，方便局部访问和内存布局
WorldMap
  └── Chunk
        # 例如 32 x 32 个格子
        └── Tile

# 业务状态和负载分配单元，建议例如 64 x 64 个格子
Region
  ├── 多个 Chunk
  ├── 动态对象
  ├── AOI
  └── 到期事件

# 线程串行执行单元，可以管理多个 Region
SceneShard
  ├── Region-A
  ├── Region-B
  └── Region-C
```

约束：

- 一个 Region 在同一时刻只能由一个 SceneShard 拥有。
- SceneShard 内部串行修改自己拥有的地图状态，避免全局锁。
- Shard 之间使用进程内本地消息队列，不直接共享和修改对方对象。
- 第一阶段不做不同进程之间的地图分片；未来容量不足时，再把 SceneShard 迁移成独立进程。
- 高交互的战争区域、关隘、世界 Boss 区域尽量作为完整业务 Zone，由一个 Shard 独占。

热点 Region 的进程内迁移使用独立的 `SceneRegion-Migration-*` 单线程编排，不能由监控线程直接搬动 HashMap：

```text
RegionDirectory 冻结 regionId 路由并生成 ownershipVersion
  -> 新到达的坐标命令进入 Region 暂存队列
  -> 源 SceneShard Tick 导出对象、格子/AOI 索引、观察者和个人迷雾
  -> 迁移线程校验交接包，不修改 SceneObject
  -> 目标 SceneShard Tick 重建对象、AOI 和迷雾索引
  -> RegionDirectory 切换唯一 owner
  -> 暂存命令按 FIFO 释放到目标 Shard
```

- 迁移失败时先把交接包装回源 Shard，再保持原 owner 并释放暂存命令，不能留下无所有者 Region。
- 跨 Shard 的 AOI、迷雾和对象统计在迁移期间等待稳定所有权，不能读到半迁移状态。
- 进程内迁移交接 Java 对象的独占所有权，不做 JSON 序列化；跨进程方案必须另行定义二进制快照、落盘点和恢复协议。
- 当前先提供显式 `migrateRegionAsync(regionId, targetShard)`。自动均衡必须要求连续多个采样周期过阈值，并配置迁移收益、最小驻留时间和冷却时间，防止热点块来回抖动。
- 热点 Region 最好由策划边界和交互范围共同确定；世界 Boss、关隘等高交互中心尽量位于 Region 内部，但即使处于边界也不能让多个 Shard 同时修改同一事件。

#### 11.1.3 主线程与异步线程职责

这里的“主线程”指 `SceneShard EventLoop`，不是 JVM 的 `main` 方法线程。

```text
# 必须在 SceneShard 线程中串行修改的权威状态
SceneShard EventLoop
  # 玩家进入、离开、位置变化
  ├── Player Map State
  # 行军创建、移动、到达和转移
  ├── March State
  # 资源点、怪物、农田、掉落物的生成和销毁
  ├── SceneObject State
  # 占领、驻军、战斗触发和地图归属
  ├── Region State
  # AOI 订阅和增量广播决策
  └── AOI State

# 可以放到异步线程，但结果必须回投 SceneShard 校验版本
Async Executors
  # 数据库、Redis 和快照写入
  ├── DB/Redis IO
  # 复杂路径搜索或大规模计算
  ├── Pathfinding Pool
  # 热点 Region 迁移编排；数据导出/安装仍在 SceneShard Tick
  ├── Region Migration Thread
  # BattleServer 返回的战斗结果处理
  ├── Battle Callback Pool
  # 日志、统计、排行榜和非关键广播
  └── Metrics/Serialization Pool
```

异步结果必须带有：

```text
# 结果属于哪个对象
aggregateId
# 结果生成时的对象版本
objectVersion
# 结果生成时的地图或 Region 版本
regionVersion
# 用于判断是否过期或重复
requestId / taskId
```

如果版本已经变化，SceneShard 应丢弃旧结果或重新计算，不能直接覆盖当前状态。

#### 11.1.4 1 万人同图的同步方式

不做全图广播，而是采用 AOI 和增量消息：

```text
# 玩家只订阅自己的兴趣范围
Player-A
  -> 当前 Sector
  -> 相邻 Sector
  -> 订阅范围内的动态对象变化

# 远处对象降低同步频率，静态地图只在进入时加载
同步策略
  ├── 静态地图：进入地图时加载或按 Chunk 加载
  ├── 附近对象：高频增量同步
  ├── 远处行军：低频摘要同步
  ├── 排行榜和统计：定时或事件驱动同步
  └── 相同变化：合并后再广播
```

地图 Tick 不遍历 100 万格子，只处理：

- 当前 Shard 的命令队列。
- 到期的行军、资源刷新和掉落事件。
- 当前活跃的动态对象。
- 当前 AOI 订阅范围发生的变化。

#### 11.1.5 每个玩家独立的战争迷雾

战争迷雾不能只挂在在线 AOI 订阅对象上，也不能按联盟共用一份。每个玩家在每个场景都有独立的永久探索位图：

```text
PlayerSceneFog
  ├── playerId                 # 玩家归属，禁止与其他玩家共享
  ├── sceneId                  # 本服世界、跨服世界分别记录
  ├── visibleBlocks            # 当前在线视野，只在 SceneShard 内存中存在
  ├── discoveredBlocks         # 历史已探索区域，离线后仍保留
  └── fogVersion               # 防止异步旧快照覆盖新探索结果
```

- 相机移动只更新 `visibleBlocks` 和 AOI 块注册，同时将新块 OR 到该玩家自己的 `discoveredBlocks`。
- 玩家离线只释放 `visibleBlocks`，不能删除 `discoveredBlocks`；永久探索异步落库成功后才能回收内存。
- 登录/进入场景时异步加载 `playerId + sceneId` 位图，然后回投 SceneShard 恢复。
- 联盟共享视野、侦察报告和瞭望塔视野属于可过期的临时情报层；可以在下发时合并，但不能写进个人永久探索记录。

地图寻路复用同一套 Region 索引，但不能只在 Region 中心点之间连线：

```text
静态地图加载完成
  -> 扫描相邻 Region 公共边界
  -> 连续可通行边界生成 Portal 候选区
  -> Region A* 选择粗路径
  -> 粗路径扩成一圈缓冲走廊
  -> 格子 A* 计算每块的实际入口、出口和块内路径
  -> 结果回投起点所属 SceneShard Tick
```

- Region 边只有在公共边界两侧存在相邻可行走格时才成立，山脉、河流、城墙和关闭的城门会切断边。
- 实际出口是最终路径在当前 Region 的最后一个格，实际入口是下一 Region 的第一个格，两者必须在公共边界两侧相邻。
- Region 粗路径和格子细路径都应用当前玩家自己的战争迷雾；粗路径不能借此穿过未探索块。
- Region 内部可能被障碍切成多个连通分量，因此细路径失败时必须使用剩余节点预算回退搜索，不能仅凭 Region 有 Portal 就认定必然可达。
- 动态建筑、部队占格和临时路障必须由 SceneShard 生成不可变快照后交给寻路线程，异步线程禁止直接访问可变地图对象。

#### 11.1.6 行军、车辆标签、目标标签与集结攻城

地图上的“车辆”统一建模为行军对象。类型表达业务目的，标签表达当前特征，目标标签表达目标允许什么操作：

```text
March
  ├── type                     # 攻击、集结成员、集结主车、增援、驻军、采集、侦察、运输、返程
  ├── tagMask                  # 单人、友方、敌方、隐身、高优先级、返程中、等待战斗、不可召回
  ├── target
  │   ├── targetType           # 城市、建筑、资源、怪物、部队、坐标、跨服对象等
  │   ├── targetTagMask        # 可攻击、可集结、可增援、可采集、可占领、可侦察等
  │   └── targetVersion        # 防止异步结果命中过期目标
  ├── armySnapshotVersion      # GameServer 冻结部队时的版本
  ├── path/pathIndex           # SceneServer 权威路径和当前位置
  └── departAt/arrivalAt       # 服务端计算时间，客户端只做表现
```

经典联盟集结流程：

1. GameServer 校验联盟、行军槽和队伍，冻结部队摘要。
2. SceneServer 创建集结；成员车分别寻路到集结点，到达后从 `JOINING` 变为 `READY`。
3. 发车时间到后，SceneShard 一次性冻结参战名单；未赶到的成员标记 `EXCLUDED`。
4. 集结主车前往目标，抵达后请求无状态 BattleServer，集结进入 `BATTLE_PENDING`。
5. BattleServer 先落库战斗结果，再返回唯一 `battleResultId`；SceneShard 幂等应用，重复结果不能重复扣兵、扣城防或发奖。
6. 城市耐久和归属由目标服务更新；伤兵、奖励、战报分别投递到对应玩家 GameServer 队列。
7. 每个成员独立返城，全部参战成员结束后回收集结对象。

所有创建、加入、到达、发车、战斗结果和返程变更都在所属 SceneShard 串行执行。复杂寻路、BattleServer RPC 和持久化可以异步，但结果必须带 `requestId/battleResultId + aggregateId + objectVersion + targetVersion` 回投 SceneShard 校验。

#### 11.1.7 玩家场景投影、地图恢复和线程负载日志

SceneServer 不读取整份玩家养成 BLOB，而是使用 `player_scene` 投影恢复地图：

```text
GameServer 玩家完整数据
  -> 城市/迁城业务产生 player_scene 投影
  -> 按 playerId 固定分区异步入库
  -> scene_id + player_id 唯一键
  -> revision 条件 UPSERT 防止旧快照覆盖新数据

SceneServer 启动
  -> 初始化静态地图紧凑数组
  -> 按 scene_id/player_id 分页读取投影
  -> 恢复玩家主城对象
  -> 恢复每个玩家独立的战争迷雾
  -> 分页读取 scene_object / scene_march / scene_rally 实体
  -> 恢复动态对象、未结束行军、集结及全部成员
  -> 全部成功后启动 Tick 并对外 ready
```

所有需要长期保存的业务对象必须先转换成明确实体类，再通过 EntryHelper/MysqlService 更新；
Handler、SceneShard 和 Store 禁止直接拼 SQL。普通动态对象使用 `SceneObjectEntry`，行军使用
`SceneMarchEntry`，集结及全部成员使用单条 `SceneRallyEntry` Protobuf 聚合快照。玩家数据与场景
聚合复用同一套固定分区 FIFO 队列；失败写入必须在原队列位置退避重试，后续 revision 不得越过。
Future 成功后才能清除内存脏标记；永久失败时停止对应持久化分区并报警。数据库加载失败、
Protobuf 损坏、坏坐标和重复对象 ID 必须中断 SceneServer 启动，禁止降级成空地图。

线上默认周期输出：每个 SceneShard 的忙碌率、Tick 平均/最大耗时、慢 Tick、命令队列、对象/观察者数量；寻路线程池活跃数、积压和耗时；Region 迁移线程的队列、成功/失败/拒绝数和平均/最大耗时；`SceneShard-Tick-*`、`ScenePath-CPU-*`、`SceneRegion-Migration-*` 平台线程 CPU、阻塞与等待变化。逻辑分片指标和实际线程 CPU 必须同时观察。

### 11.2 自研 TCP 统一通信方案

不同时维护自研 TCP、gRPC、Kafka、RocketMQ 和 Redis Pub/Sub。新 SLG 统一使用一套自研 TCP 通信底座，但在上层区分消息语义：

```text
# 一套 TCP 连接和编解码底座
InterServerTransport
  # 请求并等待业务结果，例如查询和创建命令
  ├── Request/Response
  # 投递一个需要目标服务执行的动作
  ├── Command
  # 通知一个已经发生的业务事实
  └── Event
```

通信底座分为两层：

```text
# 传输层：负责网络可靠性，不负责业务重试判断
transport/
  # TCP 长连接、连接池和连接生命周期
  ├── TCP Connection
  # 拆包、粘包、长度字段和消息边界
  ├── Frame Codec
  # 心跳和半开连接检测
  ├── Heartbeat
  # 连接断开后的自动重连
  ├── Reconnect
  # 发送队列上限、限流和背压
  ├── Backpressure
  # CONNECTED/DISCONNECTED/RECONNECTING 等状态
  └── Connection State

# 投递层：负责 ACK、恢复、去重和死信
delivery/
  # 请求-响应关联和超时
  ├── Request/Response
  # 需要执行的业务命令
  ├── Command
  # 异步业务事件
  ├── Event
  # 区分已接收、已处理、已持久化
  ├── ACK
  # 发送方保存未确认消息，支持重启恢复
  ├── Outbox
  # 接收方保存已处理消息，防止重复执行
  ├── Inbox
  # 重试耗尽或无法处理的消息
  └── Dead Letter
```

#### 11.2.1 消息包头

```text
# 单条消息唯一 ID，用于去重和排查
messageId
# 一次业务请求的 ID，重试时保持不变
requestId
# 串联多个服务日志的调用链 ID
traceId
# 发送方服务实例
sourceServerId
# 接收方服务实例
targetServerId
# 玩家、联盟、Region、Battle 等业务聚合对象 ID
aggregateId
# 同一聚合对象的顺序号或状态版本
sequence/version
# 消息的最晚处理时间，禁止无限等待
deadline
# 协议版本，用于灰度发布和兼容
protocolVersion
# 消息业务类型，例如 COMMAND、EVENT、RESPONSE
messageType
```

#### 11.2.2 重连、补发和重试边界

```text
# 连接断开后由传输层自动处理
TCP reconnect
  -> 恢复连接
  -> 恢复心跳
  -> 恢复连接状态

# 是否补发由业务层根据消息类型决定
Business delivery policy
  -> 查询类：可以有限重试
  -> 非幂等命令：必须使用幂等键后再重试
  -> 普通通知：允许丢弃或重新拉取快照
  -> 关键事件：从 Outbox 恢复并补发
```

不能把“写入 TCP Socket 成功”当成业务成功。至少区分：

```text
# 消息已经被对方解析
RECEIVED_ACK
# 目标服务已经完成业务处理
PROCESSED_ACK
# 业务结果已经提交到持久化存储
COMMITTED_ACK
```

### 11.3 数据落地总体方案

数据库不是所有运行时状态的实时镜像。推荐使用：

```text
# 高性能运行态，负责当前 Tick 和即时业务
内存权威状态
  -> 生成状态变化

# 关键消息和不可丢失业务变化
可靠事件 / Outbox
  -> 重试、恢复、幂等

# 可恢复的聚合状态
快照 / MySQL 持久化
  -> 启动加载、故障恢复
```

#### 11.3.1 按数据重要性分层

```text
# 必须可靠、可确认、不可重复执行
强一致数据
  ├── 金币、钻石和付费道具
  ├── 扣费和发奖
  ├── 战斗最终结算
  ├── 联盟资源
  └── 领地归属

# 可以延迟批量保存，但必须可恢复
普通动态数据
  ├── 玩家位置
  ├── 资源点数量
  ├── 普通怪物状态
  ├── 农田和掉落物
  └── 普通活动进度

# 重启后可以重建或重新生成
临时运行时数据
  ├── AOI 订阅关系
  ├── 连接状态
  ├── 临时广播缓存
  └── 非关键表现数据
```

#### 11.3.2 玩家、地图和战斗的落地归属

```text
# 负责玩家资源、建筑、科技、部队和奖励
GameServer
  -> Player Repository

# 负责位置、行军、动态资源点、怪物和地图归属
SceneServer
  -> Scene Snapshot / Scene Repository

# 负责战斗过程计算和已完成战斗结果
BattleServer
  -> Battle Result Repository

# 负责活动阶段、报名、积分和奖励状态
ActivityService
  -> Activity Repository
```

核心规则：

- 一个核心状态只有一个主要写入者。
- BattleServer 不直接修改玩家资源和地图归属。
- SceneServer 不直接修改玩家金币和奖励。
- 异步数据库结果必须回到状态拥有者线程校验后应用。

#### 11.3.3 普通动态状态的异步快照

普通动态状态可以使用：

```text
SceneShard 内存修改
  -> 标记 dirty
  -> 生成当前聚合快照
  -> 投递 DB Writer
  -> 按 aggregateId/版本顺序保存
  -> 成功后清理 dirty 标记
```

当前 MiniServer 的具体映射为：

```text
PlayerSceneProjection -> PlayerSceneEntry      # 主城和个人战争迷雾
SceneObjectProjection -> SceneObjectEntry      # 资源、怪物、农田、掉落物、非玩家建筑
SceneMarchProjection  -> SceneMarchEntry       # 行军完整快照
SceneRallyProjection  -> SceneRallyEntry       # 集结和全部成员的原子快照
```

以上 Store 只负责领域投影与实体转换，SQL 生成、严格分页和 revision UPSERT 统一收敛在
`MysqlService`。AOI 订阅、Region 路由、Tick 指标、寻路缓存和静态策划地图可重建，不落库。

同一个聚合对象不能让旧快照覆盖新快照。建议保存：

```text
# 玩家、Region、SceneObject 等聚合对象 ID
aggregateId
# 聚合对象的递增版本
version
# 当前快照内容
snapshotData
# 快照生成和保存时间
updateTime
```

#### 11.3.4 关键业务的 Outbox/Inbox

关键业务采用：

```text
业务状态变更
  -> 生成唯一 eventId/messageId
  -> 写入 Outbox
  -> 提交业务事务
  -> 异步发送或由 Writer 处理
  -> 接收方 Inbox 去重
  -> 业务成功后 ACK
  -> 重试成功或进入 Dead Letter
```

不能使用无限重试。重试次数、退避、过期时间和是否补偿由事件类型决定。

#### 11.3.5 BattleServer 结果落库和恢复

BattleServer 可以不保存未完成战斗过程，但已完成结果必须先落库再返回成功：

```text
发起方
  -> 创建 battleId 并记录战斗状态
  -> 发送 BattleRequest

BattleServer
  -> 内存计算战斗
  -> 完成后写入 battle_result
  -> 保存成功后返回 BattleResult/ACK

发起方
  -> 应用玩家、地图或联盟结果
  -> 写入消费记录
  -> 返回消费 ACK

BattleServer
  -> 保留已消费结果一段时间
  -> 所有必需消费者完成后延迟清理
```

BattleServer 结果表至少需要：

```text
# 战斗唯一 ID，防止重复落库和重复消费
battleId
# 发起方和目标服务
sourceServerId / targetServerId
# 战斗规则版本，保证结果可解释
ruleVersion
# 计算使用的随机种子
randomSeed
# 完整战斗结果或结果引用
resultData
# COMPUTED/DELIVERED/CONSUMED/CLEANED
status
# 生成、消费和过期时间
createdAt / consumedAt / expireAt
```

发起方启动时分页加载：

```text
# 不一次性加载所有历史战斗结果
查询 targetServerId = 当前服务
  且 status in (COMPUTED, DELIVERED)
  且按 createdAt 分页

# 每条结果先检查是否已经消费
未消费
  -> 投递到对应 PlayerShard/SceneShard/ActivityShard
  -> 业务幂等应用
  -> 写入消费记录
  -> ACK

# 已消费结果不重复扣兵、不重复发奖、不重复改领地
已消费
  -> 直接确认成功
```

未完成的内存战斗可以丢弃，但发起方必须有：

```text
# 战斗创建和超时状态
battleId / createdAt / timeoutAt
# 被锁定的玩家或地图资源
reservation
# 超时后的释放、取消或补偿策略
TIMEOUT / ABANDONED / COMPENSATED
```

### 11.4 数据方案的最终取舍

当前不采用“全项目实时同步写库”或“全项目无条件异步写库”，而采用混合方案：

```text
# 充值、扣费、发奖、战斗结算、领地归属
关键业务
  -> 事务/Outbox
  -> 幂等
  -> 明确 ACK

# 玩家位置、普通资源、怪物、农田和掉落物
普通动态状态
  -> 内存权威
  -> dirty 标记
  -> 异步批量快照

# AOI、连接、表现和可重建缓存
临时状态
  -> 内存或 Redis
  -> 重启后重新建立
```

这套方案的目标是：

- 让地图 Tick 不被数据库 IO 阻塞。
- 让关键业务在服务重启和网络异常后可以恢复。
- 让异步保存具备版本、顺序、重试和幂等能力。
- 让 BattleServer 保持计算简单，结果可以恢复和重新消费。
- 让数据库保存“可恢复状态”，而不是成为每个 Tick 的同步依赖。

### 11.5 BotServer 的 SceneServer 验收入口

BotServer 直接复用项目现有 `NetClient`、包头、CMD 和 Protobuf，不再维护一套测试专用网络协议：

```text
# 功能回归：本服/跨服、边界错误、AOI、缩放分层、个人迷雾、异步 A*、移动和断线重连。
java -jar server/BotServer/target/BotServer-1.0-SNAPSHOT-shaded.jar \
  --test-scene-rpc 127.0.0.1 9101 false

# SceneServer 以 fake-data 模式启动时，增加资源、怪物、农田、掉落物、行军和集结断言。
java -jar server/BotServer/target/BotServer-1.0-SNAPSHOT-shaded.jar \
  --test-scene-rpc 127.0.0.1 9101 true

# 地图状态容量回归：32 条真实 TCP 连接、10000 个 playerId、每连接 128 个在途请求。
java -jar server/BotServer/target/BotServer-1.0-SNAPSHOT-shaded.jar \
  --test-scene-load 127.0.0.1 9101 10000 128
```

容量命令验证的是 1 万个玩家对象、AOI/迷雾状态、SceneShard 命令队列、RPC 背压和完整
离场清理，不代表 1 万条微信 WebSocket 连接；客户端连接容量仍需由 GateServer 的机器人压测验证。
SceneServer RPC 分发按 playerId/aggregateId 哈希到固定条带，同一聚合严格保序，不同玩家可并行
等待对应 SceneShard Tick，禁止恢复成所有连接共享一个同步 Dispatcher 的全局串行模型。
