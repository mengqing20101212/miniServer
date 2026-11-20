# miniServer 开发约定

本仓库使用 Java 25，Maven 聚合入口是 [server/pom.xml](server/pom.xml)。开始任务先检查分支和工作区差异，保留已有改动。判断一个功能是否生效，从当前服务的启动注册追到执行、保存和通知。

## 按改动找入口

| 要改什么 | 从哪里开始 |
| --- | --- |
| 登录、接入和公共通信 | LoginServer 负责账号登录和选服，GateServer 维护客户端连接并向 GameServer 转发。服务注册、网络、RPC 和存储的公共实现位于 [core](server/core/src/main/java/ly)。 |
| 玩家玩法和资产 | 新玩家模块挂到 [ModuleEnum](server/GameServer/src/main/java/ly/logic/player/ModuleEnum.java)，新协议控制器在 [GameServer](server/GameServer/src/main/java/ly/GameServer.java) 注册。在线玩家状态由玩家队列修改，资产变更复用[奖励与消耗入口](docs/AWARD_CONSUME.md)。 |
| 世界玩法 | [Game 场景代理](server/GameServer/src/main/java/ly/logic/scene/SceneProxyController.java) 绑定玩家身份和出征数据，[Scene 协议入口](server/SceneServer/src/main/java/ly/sceneserver/common/CommonSceneController.java) 将地图操作交给所属区域。世界对象和行军由 SceneServer 保存，玩家资产和结算凭据由 GameServer 保存。 |
| 协议、策划表和数据库实体 | 协议改 [proto](proto) 源定义，重新生成消息类和工厂。策划表改 [excel](excel)，类型以 Excel 表头为准。生成规则统一改 [tool](server/tool/src/main/java/ly) 中对应的生成器，再生成产物。`generated-sql/create-tables.sql` 是已跟踪的生成结果。 |
| 后台运营和联调 | 管理接口、权限和配表发布在 [GMServer](server/GMServer/src/main/java/ly/gmserver)。真实协议验证和压测复用 [BotServer](server/BotServer/src/main/java/ly/BotServer.java) 的场景入口。 |

## 容易改错的边界

- **地图区域与视野。** 一个逻辑场景是一张独立地图。当前 1200×1200 地图由 `world-regions.json` 划分玩法区域，每个配置区域对应一个执行线程。AOI 九宫格决定可见范围，可跨区域观察。AOI 优化等压测后再决定。[地图输入](maps/slg/README.md) · [AOI 实现](server/SceneServer/src/main/java/ly/sceneserver/common/SceneAoiService.java)
- **行军与结算。** 行军从主城出发，往返由目标区域托管，途经格子不转移归属。采集、打怪返程到家时触发 Game 发奖。结束记录按 ID 查询，退出 Tick 和 AOI 扫描。查询行军只读。[行军状态推进](server/SceneServer/src/main/java/ly/sceneserver/common/march/SceneMarchService.java) · [玩家结算](server/GameServer/src/main/java/ly/logic/scene/SlgModule.java)
- **配置生命周期。** 启动时并行加载 XML，全部表就绪后再处理跨表关系。热更整体替换配置快照。常驻业务对象保存配置 ID，使用时取表。基础掉落串在代码里统一解析，策划无需维护结构体定义。[配置服务](server/config/src/main/java/ly/ConfigService.java) · [热更流程](docs/CONFIG_SNAPSHOT_HOT_UPDATE.md)

## 实现约束

- 遵循现有工具和流程，在用户确认的范围内改动。调整公共职责前，先说明具体调用方、状态归属和执行线程。
- 不为推测的低概率竞态或边界额外加防护。确有需要时，先说明会怎样出错、发生概率和新增代码量，等用户同意再做。
- 外部数据在入口校验一次。内部直接使用已经确定类型的值。
- 主方法保留正常业务流程，校验和复杂细节放在相应子函数。仅在逻辑复用、逻辑复杂或代表独立概念时抽取函数。
- Java 业务和测试变量显式声明类型。中文注释只解释不明显的约束、时机和原因。自动生成文件一律改源头再生成。

## 验证与交接

在 `server/` 下用 Maven Wrapper 运行受影响模块的测试，例如 `bash mvnw -pl GameServer,SceneServer -am test`。测试调用真实实现，尽量少用 Mock，不复制业务逻辑。验证自动发奖或推送时，先观察余额或消息到达，再查业务记录。交付时区分本地测试、机器人实服结果和待验项。

启动参数、Nacos 环境及顺序以 [STARTUP.SKILL.md](STARTUP.SKILL.md) 为准。生成和调试步骤查[开发流程](docs/DEV_WORKFLOW.md)，未完成事项记在[待办清单](docs/TODO_AUDIT.md)。排查运行问题时按时间核对 `logs/` 和 `runlogs/`。本文件只维护已经确认、今后开发还会用到的约定。
