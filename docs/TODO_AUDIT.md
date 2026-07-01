# TODO/FIXME 任务清单

更新时间：2026-07-01

扫描范围：

```powershell
rg -n --glob '!**/target/**' --glob '!logs/**' --glob '!runlogs/**' --glob '!**/generated-sql/**' --glob '!server/config/src/main/java/ly/config/**' "TODO|FIXME|HACK|待处理|待优化|临时|暂时" server docs README.md STARTUP.SKILL.md
```

## 结论

当前显式 `TODO/FIXME/HACK` 不多，主要问题集中在登录首登逻辑、BotServer 测试行为仍有模拟实现、可靠 RPC 里保留了一个运维兜底方法，以及 generated-sql 需要单独确认是否提交。

## P1

### GameServer 首次登录逻辑待实现

位置：

- `server/GameServer/src/main/java/ly/logic/login/LoginManager.java`

现状：

- 登录完成后有 `TODO: 首次登录逻辑待实现`。
- 当前代码会触发 `PLAYER_LOGIN_COMPLETE`，再触发重连事件，但首登流程没有明确入口。

建议：

- 明确首登判定条件，例如新建玩家、modules 为空、账号第一次绑定角色。
- 首登初始化应放到玩家队列内执行，避免和登录线程、DB 加载线程并发修改玩家模块。
- 覆盖新号创建、老号登录、重连登录三类测试。

### generated-sql 改动单独确认

位置：

- `generated-sql/create-tables.sql`
- `server/*/generated-sql/create-tables.sql`

现状：

- 当前工作区里 generated-sql 有未提交改动。
- diff 中包含真实新增表，例如 `rank_history`、GM 配表热更相关表。
- 同时也有大量表顺序重排，不适合混进 Bot/RPC 重构提交。

建议：

- 单独跑一次 DB Entry/SQL 生成流程。
- 确认生成顺序是否稳定。
- 如果稳定，再单独提交 generated-sql。
- 如果顺序不稳定，优先修生成器排序，减少无意义 diff。

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

