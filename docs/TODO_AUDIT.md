# TODO/FIXME 任务清单

更新时间：2026-07-01

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
