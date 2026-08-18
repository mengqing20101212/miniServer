# MiniServer SLG 第一阶段基础骨架

## 当前落地范围

本阶段建设服务器底座，并落地行军、目标标签和联盟集结的领域状态机与协议快照；资源生产、完整联盟战、跨服匹配以及客户端命令链路仍属于后续业务接入：

- 新增独立 `SceneServer` Maven 工程，直接依赖 `config` 和 `proto`，并复用现有 `core` 的网络、RPC、Nacos 和日志能力。
- `SceneServer` 内按 `common`、`local`、`cross` package 分开公共场景能力、本服场景和跨服场景。
- 一个 SceneServer JVM 可以承载多个逻辑场景。
- 一个逻辑场景可以拆成多个 `SceneShard`；X 轴条带只负责 Region 初始分配，运行期由 `SceneRegionDirectory` 维护可迁移的 Region 所有权。
- 1000 x 1000 地图的静态字段使用一维紧凑数组，不创建 100 万个 `Tile` 对象；当前四类静态字段理论上约占 11 MB。
- 多个 SceneShard 共享一份 `SceneStaticMap`，避免静态地图重复占用内存。
- 动态对象使用稀疏 `Map<Long, SceneObject>` 保存，支持玩家、资源点、怪物、农田、掉落物等类型；假数据生成器会生成这些对象用于协议联调。
- 动态对象带 `dataTagMask` 和 `stateVersion`，AOI 可以按对象类型、战斗、战略等标签筛选并让客户端去重。
- 使用 `region-size` 把地图划成 AOI 块，同时维护“块 -> 对象”和“块 -> 观察者”倒排索引；玩家移动视野时只注册相交块，不做全图广播。
- 支持 DETAIL、REGION、WORLD 三种缩放数据层：近景返回对象明细，中景/世界视图逐步收敛为重要对象和块级聚合。
- 每个玩家、每个场景独立保存“当前可见块”和“历史已探索块”两个 BitSet；不同玩家之间绝不共用永久探索记录。
- 支持独立 CPU 线程池执行 Region/Portal 粗路径和四方向格子加权 A*，根据道路、平原、森林、山地、不可通行 flags 与个人迷雾计算每块入口、出口和块内路径。
- 已建立攻击、集结成员、集结主车、增援、驻军、采集、侦察、运输和返程等行军类型，以及行军标签、目标类型和目标能力标签。
- 已建立联盟集结的招募、成员赴集结点、到时冻结发车名单、主车攻城、幂等应用 BattleServer 结果、成员返程和结束状态机。
- 已建立 `player_scene` 玩家场景投影、按玩家分区串行的异步入库服务，以及 SceneServer 启动时分页恢复主城和个人战争迷雾的流程。
- 已实现同一 SceneServer JVM 内的热点 Region 单线程迁移，迁移对象、格子/AOI 索引、观察者订阅和每个玩家独立的战争迷雾。
- 已增加 SceneShard、寻路池、Region 迁移线程和实际平台线程的周期负载日志，可观察慢 Tick、队列积压和线程 CPU。
- 动态对象只能由所属 SceneShard 的逻辑线程修改。
- 按坐标的跨线程业务操作通过 `SceneInstance.submit(x, y, ...)` 投递；业务代码不能先取出 SceneShard 再绕过动态 Region 路由。
- SceneShard 按固定 tick 执行队列，第一阶段默认 100ms。
- SceneServer 默认启用；`-Dslg.scene.enabled=false` 可关闭场景运行时。

## 线程边界

### SceneShard 逻辑线程

- 处理场景内动态对象创建、移动、删除。
- 处理资源点刷新、怪物刷新、农田状态推进等确定性 tick 逻辑。
- 处理同一 SceneShard 内的碰撞、占格和场景状态变更。
- 不执行同步 MySQL、Redis、HTTP 或慢 RPC。

### 异步线程

- 数据库落库、批量快照、Outbox 投递。
- RPC、文件、配置加载和地图文件解析。
- 日志、指标和战报写入。
- `ScenePath-CPU-*` 专用平台线程执行 A*；默认最多 4 个线程，可通过 JVM 参数调整。
- `SceneRegion-Migration-*` 单平台线程串行编排热点 Region 迁移；对象导出和安装仍回投源、目标 SceneShard Tick。

异步操作完成后必须通过 `SceneShard.submit(...)` 把结果投递回场景线程，不能从异步线程直接修改 `SceneObject`。

按地图坐标回投时必须优先使用 `SceneInstance.submit(x, y, ...)`。`SceneShard.submit(...)` 只适用于已经明确持有固定 Shard 的内部流程；热点 Region 迁移期间，只有前者会自动暂存命令并在所有权切换后投递给新 Shard。

寻路线程只读取场景启动后只读的 `terrain[]` 和 `flags[]`，战争迷雾由 SceneShard 在 Tick 中复制为不可变块快照。A* 完成后必须回投起点所属 SceneShard，再恢复通用 RPC 的 `callId` 并发送结果。

### GameServer 玩家队列

现有 GameServer 已经有 `GamePlayer` FIFO 队列和玩家虚拟线程。SLG 场景事件如果影响玩家状态，应先在 SceneShard 计算结果，再通过玩家队列投递玩家变更；不要让场景线程直接修改玩家模块数据。

### RPC 接入

SceneServer 不新增通信框架，直接使用 `core` 中的 `RpcService`、`RpcNodeConnector`、通用 `csServer2Server` 外壳和 `HandlerRouterManager`。本服、跨服共用的场景协议只注册一套公共 Handler，再根据 `sceneScope` 路由到本服或跨服服务。

GameServer 或其他调用方不需要依赖 SceneServer 的业务包，只依赖现有 `core`/`proto`，按下面方式发起场景 RPC：

```java
// 通过 Nacos 中注册的 SceneServer serverId 获取现有通用 TCP RPC 连接。
RpcNodeConnector sceneConnector = RpcService.getInstance()
        .getRpcNodeConnector("scene1001");

// 本服进入场景请求；请求和响应都由现有 csServer2Server 外壳携带 callId。
Scene.csSceneEnter request = Scene.csSceneEnter.newBuilder()
        .setSceneId("world-1")
        .setScope(Scene.SceneScope.SCENE_SCOPE_LOCAL)
        .setPlayerId(playerId)
        // 所有场景坐标都复用 ScenePoint，不再散落成 currentX/currentY 字段。
        .setPoint(Scene.ScenePoint.newBuilder().setX(100).setY(100))
        .setRequestId(requestId)
        .build();
Scene.scSceneEnter response = (Scene.scSceneEnter) sceneConnector
        .syncSendProtoMessage(playerId, Cmd.CMD.CS_SceneEnter_VALUE, request, 2_000,
                RpcFailSavePolicy.SEND_FAILED_OR_TIMEOUT);
```

查询类请求可使用 `RpcFailSavePolicy.NONE`；进入、移动、离开等改变场景状态的命令必须带幂等 `requestId` 或业务版本，再根据业务选择失败保存和补发策略。SceneServer Handler 不直接操作调用方的玩家对象，响应成功后由 GameServer 玩家队列应用玩家侧状态。

### AOI、缩放和战争迷雾协议

`CS_SceneView/SC_SceneView` 用于相机移动和缩放切换。`centerPoint` 是相机中心，可以与玩家实体位置不同；`radiusBlocks` 的单位是 AOI 块，不是地图格子。

| 层级 | 单体对象 | 块级数据 | 典型用途 |
|---|---|---|---|
| DETAIL | 全部对象 | 对象总数、标签并集 | 近景操作、拾取、局部战斗 |
| REGION | 玩家、怪物、建筑、战斗对象 | 对象总数、标签并集 | 区域行军和战况 |
| WORLD | 建筑、战略对象 | 对象总数、标签并集 | 世界地图、联盟态势 |

响应中的 `discoveredBlockIndices` 是当前玩家自己的历史探索块，用于客户端绘制战争迷雾。当前可见块会登记到对应 SceneShard 的 `viewersByBlock`，后续脏块增量广播可以直接找到订阅玩家。

战争迷雾必须拆成两种不同生命周期的数据：

| 数据 | 归属与生命周期 | 用途 |
|---|---|---|
| `visibleBlocks` | 玩家当前在线相机订阅；移动视野或离开场景时立即更新/释放 | 当前可见判断、实时 AOI 推送、`VISIBLE_ONLY` 寻路 |
| `discoveredBlocks` | 玩家 + 场景维度的个人永久记录；离开场景不能删除 | 地图解锁显示、`DISCOVERED_ONLY` 寻路、登录恢复 |

具体约束：

- 玩家 A 解锁的块不能出现在玩家 B 的 `discoveredBlocks` 中，即使两人属于同一联盟。
- 联盟共享视野、侦察报告和瞭望塔效果属于有过期时间的临时情报层，只能在返回客户端时与个人可见结果合并，不能反向写入个人永久探索记录。
- 玩家进入场景前，由 GameServer 或场景快照服务异步加载个人探索记录，再投递 SceneShard 执行 `restoreDiscoveredBlocks`。
- 玩家离开只执行 `removeViewer`，释放在线 AOI 订阅但保留探索记录；异步落库成功且玩家不在线后，才允许显式 `evictDiscoveredBlocks`。
- 持久化建议按 `playerId + sceneId` 保存压缩后的位图和 `fogVersion`，用版本或 compare-and-set 防止旧快照覆盖新探索结果。

`CS_ScenePathFind/SC_ScenePathFind` 支持三种迷雾策略：

- `SCENE_FOG_IGNORE`：系统任务或服务器内部寻路，不检查玩家视野。
- `SCENE_FOG_DISCOVERED_ONLY`：路径只能经过历史探索块。
- `SCENE_FOG_VISIBLE_ONLY`：路径只能经过当前可见块。

A* 使用曼哈顿启发、原生 `int[]` 工作区和无对象最小堆；线程工作区重复使用，不会为每个节点创建 Java 对象。`maxVisitedNodes` 默认 100000、硬上限 500000，达到限制返回 `SCENE_PATH_LIMIT_EXCEEDED`，避免热点请求无限占用 CPU。

### Region 粗路径、Portal 和块内细路径

寻路不把 100 万格直接组成全局对象图，而是复用 AOI 的 `region-size` 建立两层路径：

1. 静态地图加载完成后先冻结 `terrain[]/flags[]`，并在 Tick 启动之前扫描相邻 Region 的公共边界。
2. 公共边界上只有同时存在一对相邻可行走格，两个 Region 才连边；连续可通行边界段记为一个 Portal 候选区。
3. Region A* 在约 1024 个块上选择粗路径，并使用各 Region 的平均地形代价作为边权参考。
4. 粗路径扩成有限 Region 走廊，格子级 A* 只在走廊中搜索，继续检查 `terrain[]`、`flags[]` 和当前玩家自己的迷雾快照。
5. 最终格子路径跨越 Region 边界时，前一块的最后一个格子就是实际出口，后一块的第一个格子就是实际入口；`ScenePathResult.regionSegments(...)` 可以直接拆出每块的入口、出口和块内路径。
6. 如果某个 Region 内部被障碍切成多个不连通区域，粗图虽然有边但细路径失败，则使用剩余 `maxVisitedNodes` 预算回退到全图格子 A*，不能直接误判无路。

入口和出口不能固定为 Region 中心点，也不能只由策划手工指定。策划地图负责地形、道路、城门和不可通行 flags；服务端根据这些静态约束生成 Portal 候选区，再由每次请求的起终点、地形代价和个人迷雾决定实际使用哪一对边界格。动态城市、临时路障和部队占格仍需在 Tick 中生成只读阻挡快照后交给异步寻路线程，不能让寻路线程直接读取可变 SceneShard 对象。

### 热点 Region 单线程迁移

当前支持同一 SceneServer JVM 内把热点 Region 从一个 SceneShard 迁移到另一个 SceneShard。迁移过程不复制静态 `terrain[]/flags[]`，只交接该 Region 的动态权威状态：

```text
自动均衡器或 GM 选定 regionId + targetShard
  -> RegionDirectory 生成 ownershipVersion，冻结该 Region 的新坐标命令
  -> 源 SceneShard Tick 处理完更早的 FIFO 命令
  -> 源 Tick 导出并移除 SceneObject、occupants、objectsByBlock
  -> 同时导出 viewersByBlock、玩家 visibleBlocks 和 discoveredBlocks
  -> SceneRegion-Migration-* 专用线程校验数据包
  -> 目标 SceneShard Tick 安装对象、AOI 和个人迷雾索引
  -> RegionDirectory 原子切换 owner
  -> 迁移期间暂存的命令按原顺序释放到目标 Shard
  -> 失败则数据装回源 Shard，owner 不变，暂存命令回到源队列
```

关键约束：

- 同一时刻只允许一个 Region 迁移，避免多个大块同时争抢内存带宽并放大 ZGC 压力。
- 迁移线程只持有交接数据并校验元数据，绝不直接修改 `SceneObject.state`；真正删除和安装只能发生在源、目标 Tick。
- `ownershipVersion` 是 fencing token。迟到的旧导出、旧安装和旧提交不能覆盖新所有权。
- 跨多个 Shard 的 AOI/迷雾聚合请求使用异步租约避开切换窗口，不会读到“源已删除、目标未安装”的半份状态。
- 进程内迁移采用独占 Java 对象引用交接，不做 JSON 序列化。未来如果扩展为跨进程迁移，必须增加明确的 Protobuf/二进制快照和持久化恢复协议。
- 当前公开 API 是 `SceneInstance.migrateRegionAsync(regionIndex, targetShardIndex)`。它执行迁移决定，但不会自行判断热点。
- 自动热点判定后续应至少观察 Region 命令速率、活跃对象数、AOI 观察者数和连续 Tick 耗时，并设置连续采样阈值、迁移收益估算和冷却时间，防止 Region 在 Shard 之间来回抖动。

## 行军、车辆标记和目标标记

这里把客户端看到的“车”统一建模为 `SceneMarchState`。`type` 表示这辆车要做什么，`tagMask` 表示它当前具有什么附加特征；两者不能混在一个枚举里，否则返程、隐身、敌我关系和战斗等待状态会造成类型爆炸。

### 行军类型

| 类型 | 含义 | 到达后的典型状态 |
|---|---|---|
| `ATTACK` | 单人攻击城市、建筑、部队等目标 | 等待 BattleServer 战斗结果 |
| `RALLY_MEMBER` | 联盟成员先到队长的集结点 | 等待集结发车 |
| `RALLY_ARMY` | 冻结成员后形成的集结主车 | 到达目标后等待集结战斗结果 |
| `REINFORCE` | 增援盟友城市、建筑或部队 | 进入目标的增援队列 |
| `GARRISON` | 驻守城市或联盟建筑 | 到达后成为驻军 |
| `GATHER` | 采集资源点 | 到达后进入采集状态 |
| `SCOUT` | 侦察目标 | 生成个人或联盟情报报告后返程 |
| `TRANSPORT` | 运输资源或道具 | 交付后返程 |
| `RETURN` | 不再执行目标行为的返程车 | 到达出发点后结束 |

行军标签使用位掩码组合，当前包括 `SOLO`、`RALLY_MEMBER`、`RALLY_MAIN`、`FRIENDLY`、`HOSTILE`、`STEALTH`、`HIGH_PRIORITY`、`RETURNING`、`BATTLE_PENDING` 和 `CANNOT_RECALL`。AOI 可以按标签控制显示频率和字段，例如敌方隐身车只向满足侦察条件的玩家下发，世界层只下发高优先级或集结主车摘要。

### 目标类型与能力标签

`SceneTargetDescriptor` 同时保存目标 ID、目标类型、坐标、能力标签和 `targetVersion`：

- 目标类型包括坐标、玩家城市、联盟城市、联盟建筑、资源点、怪物、集结营地、部队和跨服对象。
- 能力标签包括可攻击、可集结、可增援、可采集、可占领、可侦察、移动中、友方、敌方、中立和需要视野。
- 创建行军时先用目标标签校验类型是否匹配。例如 `GATHER` 只能指向 `GATHERABLE`，`RALLY_ARMY` 必须同时满足 `ATTACKABLE + RALLYABLE`。
- `targetVersion` 是发车时看到的目标版本。寻路完成、抵达和战斗结果回投 SceneShard 时都要再次校验，防止攻击已经迁城、换归属或失效的旧目标。

`SceneMarchState` 只保存地图移动和战斗调用需要的冻结摘要：玩家、联盟、兵量、战力、`armySnapshotVersion`、路径、路径下标和服务端计算的出发/到达时间。英雄、兵种养成和实际扣兵仍归 GameServer；SceneServer 不直接修改玩家数据。

## 经典联盟集结攻城流程

```text
GameServer 冻结队伍和行军槽位
  -> SceneServer 创建集结，队长处于 READY
  -> 联盟成员加入并各自寻路到集结点
  -> 成员到达后由 JOINING 变为 READY
  -> 到达发车时间，SceneShard 一次性冻结本次参战名单
  -> 未赶到的成员标记 EXCLUDED，不参与本次战斗
  -> 创建 RALLY_ARMY 主车并沿服务端路径前往城市
  -> 主车到达后提交 BattleServer，集结进入 BATTLE_PENDING
  -> BattleServer 落库并返回唯一 battleResultId
  -> SceneShard 按 rallyId + targetId + targetVersion 幂等应用结果
  -> 城市耐久/归属由城市状态服务应用，玩家伤兵和奖励投递各自 GameServer 队列
  -> 每个参战成员独立返城；全部完成后回收集结对象
```

当前状态机已经覆盖：同联盟校验、容量、加入截止时间、最低发车人数、只有队长可发车/取消、未到达成员排除、发车名单冻结、战斗结果防重、不同结果禁止覆盖、成员独立返程和集结最终结束。

以下规则必须由命令层接入时继续补齐：联盟实时成员资格、玩家行军槽位、队伍冻结/解冻、保护罩与免战、目标归属和可攻击关系、集结加速/减速、队长踢人、自动取消与超时补偿、跨 SceneShard 对象转移、城市耐久扣减、奖励邮件和战报消费。它们不能只靠客户端判断。

## 玩家数据入库与地图启动恢复

### 数据归属

不能让 SceneServer 读取并反序列化整个 `PlayerEntry.modules`，否则地图服会依赖英雄、背包和养成模块。当前新增独立的 `player_scene` 场景投影表，只保存重建地图需要的数据：

```text
player_scene
  ├── player_id + scene_id       # 唯一键，也是分页恢复索引
  ├── city_object_id             # 主城在地图中的全局对象 ID
  ├── alliance_id                # 地图敌我和联盟显示需要的摘要
  ├── city_x / city_y            # 主城坐标
  ├── city_level                 # 地图展示摘要，不等于完整养成数据
  ├── city_state_version         # 主城状态版本
  ├── fog_data                   # 该玩家自己的 BitSet 压缩字节
  ├── data_version               # 数据格式版本，供后续迁移
  ├── revision                   # 单调递增的业务版本
  ├── deleted                    # 迁服/删除使用的逻辑删除标记
  └── update_time
```

GameServer 仍然拥有玩家完整养成数据；SceneServer 拥有地图位置、AOI、迷雾和地图对象。城市升级或迁城命令完成后，业务层生成新的场景投影并增加 `revision`，不要把任意 Java 对象或 JSON 字符串写入该表。

### 异步入库顺序和成功判定

`ScenePlayerPersistenceService` 按 `playerId` 哈希到固定 FIFO 分区：

1. SceneShard 先生成不可变业务快照，`snapshotAndSubmit` 再从各分片取得该玩家最新迷雾。
2. 同一玩家的 revision 1、2、3 一定在同一工作队列顺序执行；失败任务在原位置退避重试，后续版本不能越过。
3. MySQL 使用 `scene_id + player_id` 唯一键和 revision 条件 UPSERT。相同 revision 重放被忽略，较小 revision 不能覆盖较新数据。
4. 返回的 `CompletableFuture` 成功，才代表数据库已经接受该版本；在成功前不能清除内存脏标记或迷雾快照。
5. 单个分区超过最大重试次数会停止该分区并写错误/死信日志，禁止跳过失败版本继续保存后续数据。队列有容量上限，满载时显式失败并触发背压，不能无限堆积导致 OOM。

这提供的是“顺序、幂等、重试和可检测失败”，不是把异步调用伪装成绝对不会失败。线上还需要对持久化分区健康、队列深度、最终失败和数据库延迟配置告警。

### SceneServer 启动顺序

```text
Nacos/Config/MySQL 初始化并完成自动建表
  -> 创建 SceneRuntime，但暂不启动 Tick，也不发布 ready
  -> SceneStaticMapLoader 初始化 terrain/configId/flags/spawnRuleId 数组
  -> 按 scene_id + player_id 分页读取 player_scene
  -> 校验坐标、版本和严格递增分页顺序
  -> 恢复玩家主城 SceneObject
  -> 将每个玩家自己的 fog_data 分配到对应 SceneShard
  -> 所有场景恢复成功后启动 Tick、负载日志和 RPC 可用状态
```

任何数据库异常、坏坐标、重复对象 ID 或无序分页都会中断启动，不能把“加载失败”当成“数据库没有玩家”。恢复期间 RPC 返回 `SCENE_NOT_READY`。启动日志会输出场景数、静态格子数、恢复玩家/主城数、迷雾块数和耗时。

`SceneStaticMapLoader` 已经成为可替换接口。当前非假数据模式使用全平原可行走加载器保证骨架可运行，假数据模式使用确定性假地图；正式接入时必须用 `config` 策划表或版本化二进制地图实现替换，并校验地图版本和校验和。

当前玩家投影可以恢复主城和个人迷雾。资源点、怪物可根据静态刷新规则重新生成；未结束行军、集结、驻军和正在采集状态后续仍需独立的场景对象快照/事件表，不能塞进 `player_scene`。

## 周期线程负载日志

默认每 60 秒输出三类指标：

- `SceneShard load`：场景/分片、最近执行线程、逻辑忙碌率、Tick 频率、平均/最大/最近 Tick 耗时、慢 Tick 和失败数、命令增量、当前/峰值队列、对象数、活跃观察者、迷雾玩家数和 Tick 延迟。
- `ScenePath pool load`：线程数、活跃线程、队列、任务增量、失败/拒绝数、平均/最大寻路耗时和线程池忙碌率。
- `SceneRegion migration load`：单线程池活跃数、迁移积压、成功/失败/拒绝增量、平均/最大迁移耗时。
- `Scene platform thread load`：每个 `SceneShard-Tick-*`、`ScenePath-CPU-*`、`SceneRegion-Migration-*` 平台线程的 JVM CPU 百分比、状态、阻塞次数增量和等待次数增量。

多个 SceneShard 会复用调度线程，因此逻辑分片忙碌率和实际线程 CPU 必须同时看。超过慢 Tick、队列积压、寻路失败/拒绝或 Tick 长时间未完成时会输出 WARN；也可通过 `SceneRuntime.logLoadNow()` 立即打印。

## 启动参数

```text
-Dslg.scene.enabled=true
-Dslg.scene.local-id=world-1
-Dslg.scene.cross-id=cross-1
-Dslg.scene.width=1000
-Dslg.scene.height=1000
-Dslg.scene.shards=4
-Dslg.scene.region-size=32
-Dslg.scene.tick-millis=100
-Dslg.scene.path.threads=4
-Dslg.scene.path.region-padding=1
-Dslg.scene.region-migration.queue-capacity=128
-Dslg.scene.load-log-seconds=60
-Dslg.scene.slow-tick-millis=200
-Dslg.scene.load.queue-warn=1000
-Dslg.scene.restore.page-size=1000
-Dslg.scene.persistence.partitions=4
-Dslg.scene.persistence.queue-capacity=5000
-Dslg.scene.persistence.max-retries=20
-Dslg.scene.persistence.initial-retry-millis=200
-Dslg.scene.fake-data=true
-Dslg.scene.fake-online=10000
```

`region-size` 同时决定 AOI 注册、块级战争迷雾、两级寻路粗图和热点迁移粒度。1000 x 1000 地图、32 格一个块时共有 32 x 32 = 1024 个块；每个玩家每类迷雾 BitSet 的原始位数据约 128 字节。Region 启动时按 X 轴条带分配，运行期可以迁往其他 SceneShard。

## 第一阶段容量标准

| 指标 | 基线 | 说明 |
|---|---:|---|
| 地图尺寸 | 1000 x 1000 | 1,000,000 个逻辑格子 |
| 静态地图 | 共享一份数组 | SceneShard 不重复保存静态数组 |
| SceneShard | 4 | 同一 SceneServer JVM 内的逻辑分片 |
| Region 迁移线程 | 1 | 串行搬运热点块，禁止直接修改场景对象 |
| Tick | 100ms | 场景状态推进基线，禁止在 Tick 内同步 DB/RPC |
| 在线玩家 | 10,000 | 假数据联调和容量基线，不等于最终压测结论 |
| 动态对象 | 稀疏 Map | 仅为有实体的格子创建对象 |
| AOI | 32 x 32 格/块 | 维护块对象和块观察者索引，不做全图明细下发 |
| 寻路线程 | 默认 1-4 个平台线程 | 与 Tick 隔离，数量可配置 |
| Region 寻路 | 32 x 32 格/块，约 1024 块 | 启动时扫描 Portal，先粗路径再格子细路径 |
| A* 搜索上限 | 默认 100000 节点 | 硬上限 500000 节点 |
| RPC | 通用 Server2Server | 业务 cmd 放在统一 RPC 外壳内 |
| 个人迷雾 | 每玩家每场景独立 BitSet | 在线视野与永久探索分离，离线不丢失 |
| 行军状态 | SceneShard 串行修改 | 类型、标签、目标版本、冻结部队版本齐全 |
| 集结状态 | SceneShard 串行修改 | 发车名单冻结，BattleServer 结果按 ID 幂等 |
| 玩家投影恢复 | 1000 条/页 | 静态地图后恢复主城和每玩家独立迷雾，失败则不 ready |
| 投影入库 | 4 个玩家哈希分区 | 同玩家 FIFO、版本 UPSERT、失败原位重试 |
| 负载日志 | 60 秒 | 同时输出分片忙碌率、线程池和平台线程 CPU |

假数据启动示例：

```text
# 开启 SceneServer 的 1000 x 1000 双场景运行时和 1 万在线基线数据。
-Dslg.scene.enabled=true
-Dslg.scene.fake-data=true
-Dslg.scene.fake-online=10000
```

启动日志至少应能看到：地图场景 id、假在线玩家数量、世界对象数量、SceneShard 数量和首个 tick 指标。

### 验收指标

- 静态地图可以完成 100 万格加载，不能创建 100 万个 `Tile` 对象。
- SceneShard 之间不能直接读写对方的动态对象。
- 按坐标的业务操作通过 `SceneInstance.submit` 和 RegionDirectory 路由后串行执行。
- 热点 Region 迁移后，对象、AOI 订阅和个人迷雾仍只能存在于唯一所有者 Shard。
- Tick 内不得出现同步 MySQL、Redis、HTTP 或慢 RPC。
- 1 万假在线对象可以完成启动、进入、查询、移动和离开协议联调。
- 视野移动会取消旧块注册、登记新块，并保留历史探索块。
- 两个玩家探索不同区域时，永久迷雾数据不能互相泄漏；玩家离开场景后探索记录仍可恢复。
- 不同缩放层返回不同对象标签集合，远景仍保留块级聚合数据。
- 寻路不占用 Tick 线程，结果必须在 `SceneShard-Tick-*` 回调并携带 `completedTick`。
- 不可通行地形、搜索上限、当前可见和历史探索约束必须返回明确错误码。
- 场景 RPC 响应必须携带通用 RPC 的 callId，由 core 负责匹配和可靠补发。
- 行军类型必须与目标能力标签匹配，目标版本过期不能继续应用异步结果。
- 集结发车只冻结已到达成员，BattleServer 同一结果重放不得重复扣兵或重复发奖。
- 数据库加载失败不能启动空地图；恢复完成前 RPC 必须返回 `SCENE_NOT_READY`。
- 两个玩家的主城和迷雾可从 `player_scene` 分页恢复，恢复后的迷雾仍互相隔离。
- 同一玩家投影的失败版本必须先重试成功，后续 revision 才能入库；旧 revision 重放不得覆盖新数据。
- 周期日志必须能定位具体 scene/shard 的慢 Tick 和积压，并显示寻路线程池及平台线程 CPU。

## 后续接入顺序

1. 用 `config` 策划表替换假地图的地形编号、移动代价和刷新规则。
2. 在现有 AOI 订阅索引上增加脏块/对象增量广播，并由 GameServer 转发客户端。
3. 在 GameServer 城市/迁城模块和 SceneServer 命令层接入现有玩家投影写入 API，并在成功落库后回收离线迷雾内存。
4. 用真实 `config` 策划表或版本化二进制地图替换当前平原/假地图加载器，增加地图版本和校验和。
5. 为现有行军/集结状态机补齐 GameServer 命令、行军槽位与部队冻结/解冻，并增加协议幂等键。
6. 在已有整 Region 迁移基础上，实现跨 Region 行军对象原子转移、时间轮/最小堆到期调度和 Tick 自动推进。
7. 增加动态建筑、部队占格和临时阻挡快照，供两级 A* 判断动态不可达；再根据压测结果增加路径缓存、Portal 预计算或跨进程分段寻路。
8. 接入 BattleServer 请求、结果落库、战报、城市耐久/归属和玩家伤兵/奖励队列。
9. 增加资源点、怪物、农田、掉落物、未结束行军和集结的场景快照/恢复、SceneEvent 和 Outbox。
10. 增加跨服活动场景；跨服场景继续复用 `SceneRuntime` 和 `SceneShard`，通过启动参数加载不同场景配置。

## 当前限制

- 已支持同 JVM 内整 Region 热点迁移，但暂不支持单个行军跨 Region 时的原子对象转移；当前跨分片移动返回 `SCENE_UNSUPPORTED`。
- 暂不支持进程间地图迁移。
- 已支持 AOI 注册和分层快照，但暂未实现对象变化后的自动增量推送；目前由视野请求返回快照。
- 玩家场景投影表、启动恢复和异步写入服务已接入；GameServer 的正式城市/迁城业务还未调用投影写入入口。
- 正式静态地图策划表尚未定义，当前非假数据模式只加载可行走平原；不能作为上线地图内容。
- 战争迷雾当前按 AOI 块判断，不包含基于障碍物的射线视野、高度遮挡或联盟临时情报层。
- A* 当前已实现 Region/Portal 粗路径和四方向格子细路径，但仍只读取静态地形与个人迷雾，不读取 Tick 中的动态部队/建筑阻挡，也未实现 JPS 或跨进程分段寻路。
- 行军和集结已具备领域状态机、协议快照与 SceneShard 修改入口，但尚未新增客户端/服务器命令 Handler、自动 Tick 调度、单个行军跨 Region 转移和 GameServer 部队冻结链路。
- BattleServer 的集结输入/结果 DTO 与幂等应用已定义，但真实 RPC、城市状态写入、伤兵、奖励和战报消费仍未串联。
- `player_scene` 只恢复主城和个人迷雾；资源/怪物刷新状态、未结束行军、集结、驻军和采集尚未建立独立持久化表。
- SceneServer 已提供服务器间 AOI/寻路协议；GameServer 到微信客户端的数据转换和增量推送仍需随客户端地图协议确定。
