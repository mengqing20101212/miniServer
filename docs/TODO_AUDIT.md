# TODO/FIXME 任务清单

更新时间：2026-07-01

扫描范围：

```powershell
rg -n --glob '!**/target/**' --glob '!logs/**' --glob '!runlogs/**' --glob '!**/generated-sql/**' --glob '!server/config/src/main/java/ly/config/**' "TODO|FIXME|HACK|待处理|待优化|临时|暂时" server docs README.md STARTUP.SKILL.md
```

## 当前结论

显式 `TODO/FIXME/HACK` 不多。已经处理首登事件和 generated-sql 输出稳定性，剩余主要是 BotServer 模拟行为、Bot 登录响应注释、可靠 RPC 手工重试接口是否正式化。

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

## P2

### BotServer CombatModule 仍然用移动 Action 代替战斗

位置：

- `server/BotServer/src/main/java/ly/bot/module/impl/CombatModule.java`

现状：

- `CombatModule` 当前使用 `MoveAction` 代替战斗行为。

建议：

- 等战斗协议稳定后新增 `CombatAction`。
- `CombatModule` 只组织战斗相关 Action，不再复用移动行为。
- 测试报告中增加战斗请求、战斗回包、失败码校验。

### BotServer RobotSession 登录响应注释和实际实现需要整理

位置：

- `server/BotServer/src/main/java/ly/bot/session/RobotSession.java`

现状：

- `handleLoginResponse` 附近还有旧注释，描述为“暂时使用默认值”。
- 实际代码已经通过 `buildPlayerInfoFromLoginResponse` 解析 `SC_Login`。

建议：

- 清理旧注释，改成当前真实行为。
- 如果 `SC_Login` 后续补充更多玩家基础字段，统一在 `buildPlayerInfoFromLoginResponse` 中解析。

## P3

### ReliableRpcMessage 强制重置退避方法需要明确用途

位置：

- `server/core/src/main/java/ly/rpc/ReliableRpcMessage.java`

现状：

- `resetRetryForForceReplay()` 标注为临时方法，用于 Redis 中积压消息强制重试。

建议：

- 如果仍然需要运维功能，改名为正式接口，例如 `resetRetryForManualReplay()`。
- 调用入口需要有日志，记录操作人、消息 ID、原 retryCount、原 nextRetryAt。
- 如果已不需要，删除方法和调用方。

## 已排除项

以下命中项不是代码待办：

- README、AI 项目索引里关于 generated-sql 不是临时文件的说明。
- 历史变更文档里的“临时 force reset”记录。
- `ConfigService` 关闭临时 `JarFile` 的注释。
- `LoginController` 关于数据库记录暂时不可用时读取 Redis 缓存的说明。

