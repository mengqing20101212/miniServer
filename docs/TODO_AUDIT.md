# TODO/FIXME 任务清单

更新时间：2026-11-02

## 当前待办：SLG 完整链路联调

本节记录本次未完成的任务；下方保留原有代码扫描记录，本次没有重新扫描全仓库。

### 已完成的基础工作

- `a90753e`：集合单元格改用分号、冒号填写，继续导出 XML。
- `940afc6`：错误公式行输出日志并跳过，不中断后续行导出。
- `7751015`：公司1200×1200地图、配置中的25个区域线程、目标区域托管行军、基础采集/模拟打怪、返程查询发奖、跨区域AOI推送及双机器人用例。
- Maven 完整构建、本地专项测试通过；实际公司地图已加载并启动。真实机器人完整链路尚未通过。
- 本次测试进程已停止。以上代码仅提交本地 `dev` 分支，尚未 push；远端推送待用户安排。

### 按顺序继续

- [ ] 排查远程 Nacos 的 gRPC 订阅和注册超时。地址仍为 `118.25.76.117:8848`，namespace/env 为 `ly`；HTTP 节点查询正常，gRPC 能建立连接，但 `SubscribeServiceRequest` 持续超时，延长到10秒仍未解决。不能直接认定9848端口未开放。
- [ ] 确认 SSH 登录方式后查看 Nacos 服务端进程、9848监听和对应时间日志。本次现有密钥登录 `root` 被拒绝；未成功登录，未修改远端配置。用户已决定后续处理，先不要继续尝试其他账号或重启远端服务。
- [ ] Nacos 恢复后，按 [启动规范](../STARTUP.SKILL.md) 启动 Login → Game → Scene → Gate。确认8888、8889、9002、9101、9001全部就绪，Scene完成地图恢复，并且Login能发现Game/Gate后，再启动机器人。
- [ ] 运行 `BotServer --test-slg-gameplay 127.0.0.1 8889 game1001 本次唯一账号`。用例会注册主账号及 `_observer` 观察者账号，走真实 Login → Gate → Game → Scene；当前用例针对公司地图中央区域25及默认部队/奖励配置。
- [ ] 核对采集：进入采集阶段，资源点存量减少，返程期间余额不变，返程结束后查询领取的资源实际到账。
- [ ] 核对打怪：模拟战斗胜利后怪物血量归零，返程完成后奖励实际到账；重复查询不重复发奖，观察者不收到他人奖励。
- [ ] 核对跨区域AOI：观察者九宫格只覆盖路线中段、两端均在视野外，也能收到行军出现及结束移除推送；行军全程仍由目标区域托管。
- [ ] 根据实际失败修复必要问题并复跑，保存新的日志及验收结果。只有机器人完整链路通过，才能标记上述联调完成。

### 复跑上下文

- 地图目录：`maps/slg/`。`WorldMap2.tmx`、`world-regions.json` 是本地链接，未进入Git；换机器需准备原始导出文件。源文件来自 `/home/liuyang/WORK/slg/slgtd_server/xml/`。
- 新配表源：`excel/slgWorldObject.xlsx`；修改源表后通过现有生成器导出 `slgWorldObject.xml`，不要手改生成文件。
- 本次运行目录及失败日志：`runlogs/slg-e2e-20261102/`；临时配置和 classpath 保存在其中，未提交。当前日志只能证明启动和登录阻塞，不能证明业务链路通过。
- 本次采用 `-Dminiserver.localConfig=true`，使用临时导出的345张XML；数据库、Redis和节点参数仍从Nacos读取。原因是远端存在无效的历史配置发布通知，不要把临时运行目录当成正式配置部署。
- 部分旧表的缺失类型声明只在临时副本中按现有Java字段类型补齐，原Excel没有批量修改；错误公式行已记日志并跳过。本次没有实现旧表兼容机制。
- 本次 Scene 加载参数为 `-Dslg.scene.map-dir=/home/liuyang/.ssh/miniServer/maps/slg`；为缩短往返等待，联调使用 `-Dslg.march.speed=200`，生产默认值仍为10。
- 本次本地进程显式设置 `miniserver.idNodeId`：Login=101、Game=1001、Scene=1002、Gate=1003。重新启动前核对旧进程和端口。
- 生成运行 classpath 时使用 Maven 的 runtime 依赖范围；不要混入 test 范围的 tool fat jar，本次已发现它会造成Game的日志依赖冲突。
- 真实战斗模块、沿途拦截不属于本轮补做项；继续遵循 `AGENTS.md` 中用户的实现约束，不增加假设性防护或扩大范围。

## 原代码扫描记录（2026-07-01）

扫描范围：

```powershell
rg -n --glob '!**/target/**' --glob '!logs/**' --glob '!runlogs/**' --glob '!**/generated-sql/**' --glob '!server/config/src/main/java/ly/config/**' "TODO|FIXME|HACK|待处理|待优化|临时|暂时" server docs README.md STARTUP.SKILL.md
```

## 当前结论

显式 `TODO/FIXME/HACK` 不多。已经处理首登事件、generated-sql 输出稳定性、BotServer 模拟行为、Bot 登录响应注释和可靠 RPC 手工重试接口清理。

## 已完成

### GameServer 首次登录事件

位置：

- `server/GameServer/src/main/java/ly/logic/login/LoginManager.java`
- `server/GameServer/src/main/java/ly/logic/player/event/PlayerEventType.java`

处理结果：

- 新增 `PLAYER_FIRST_LOGIN` 事件。
- `LoginManager` 在本次创建新玩家后，于登录完成阶段投递首登事件。
- 首登奖励、引导初始化等业务后续可以监听 `PLAYER_FIRST_LOGIN`。

### generated-sql 输出稳定性

位置：

- `server/core/src/main/java/ly/EntityToSqlGenerator.java`
- `generated-sql/create-tables.sql`
- `server/*/generated-sql/create-tables.sql`

处理结果：

- SQL 生成器按表名稳定输出。
- 目录扫描、Jar 扫描、最终实体集合都固定顺序，减少无意义 diff。
- 已重新生成当前跟踪的 `create-tables.sql` 文件。

### BotServer 登录响应注释整理

位置：

- `server/BotServer/src/main/java/ly/bot/session/RobotSession.java`

处理结果：

- 清理了 `handleLoginResponse` 附近“暂时使用默认值”的旧注释。
- 当前注释明确说明：Bot 会从 `SC_Login` 解析玩家信息，解析失败时使用账号信息兜底。

### ReliableRpcMessage 临时强制重试方法

位置：

- `server/core/src/main/java/ly/rpc/ReliableRpcMessage.java`

处理结果：

- `resetRetryForForceReplay()` 没有任何调用方。
- 已删除该临时方法，避免后续误以为存在正式运维入口。

### BotServer CombatModule 不再复用移动 Action

位置：

- `server/BotServer/src/main/java/ly/bot/module/impl/CombatModule.java`
- `server/BotServer/src/main/java/ly/bot/action/impl/SimulatedCombatAction.java`

处理结果：

- 新增 `SimulatedCombatAction`，在没有真实战斗协议前只维护 `combat` 数据域。
- `CombatModule` 改为组织战斗 Action，不再发送或复用移动行为。
- 后续接入真实战斗协议时，只需要替换 `SimulatedCombatAction` 的发包和回包校验逻辑。

## P2

当前没有明确需要立即处理的 P2 项。

## 已排除项

以下命中项不是代码待办：

- README、AI 项目索引里关于 generated-sql 不是临时文件的说明。
- 历史变更文档里的“临时 force reset”记录。
- `ConfigService` 关闭临时 `JarFile` 的注释。
- `LoginController` 关于数据库记录暂时不可用时读取 Redis 缓存的说明。
