# TODO/FIXME 任务清单

更新时间：2026-11-20

## SLG 完整链路联调

- [x] 在远程 Nacos 的 `ly` 环境注册 Login、Game、Scene、Gate，本地五个端口就绪，Scene 完成两张 1200×1200 地图恢复后，两个新账号通过真实网关登录。
- [x] 同一资源点连续采集。初始 130，首次带回 100，原 ID 保留 30；第二次带回 30，旧点消失。盘点第 25 区，新 ID 在该区补出，其他目标 ID 和区域目标总数不变。
- [x] 模拟击杀普通怪后原区域补刷新怪，返程自动发奖 100。返程中余额不变，到家后先用资产接口验账，再查已结束的行军。重复查询余额不变，观察账号余额不变。
- [x] 第二个账号分别在区域 23 和 22 观察前往区域 25 的采集与打怪路线，两端均在视野之外，实收出征、返程及到家移除的主动推送。

11 月 2 日的 Nacos 订阅超时在本次联调中未复现。本次首登失败由开发库 `pick_money.login.id` 仍为 INT 导致，经确认仅执行了已有迁移中的该列扩容，随后新账号正常落库。复跑命令及配表见[开发流程](DEV_WORKFLOW.md#启动与运行流程)。

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
