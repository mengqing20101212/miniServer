# BotServer 战斗协议接入记录

更新时间：2026-07-07

## 当前结论

当前项目还没有真实战斗协议入口，BotServer 暂时不能把 `CombatModule` 切到真实发包。

已检查范围：

- `proto/Cmd.proto`
- `server/proto/src/main/java`
- `server/GameServer/src/main/java`
- `server/BotServer/src/main/java`

现有 `Cmd.proto` 只看到英雄、移动、资源等协议段，没有 `Battle`、`Combat`、`Fight` 相关
CS/SC 命令。GameServer 侧也没有对应 Controller。

## 当前实现

- `CombatModule` 使用 `SimulatedCombatAction`。
- `SimulatedCombatAction` 不发网络包，只维护 `combat` 数据域。
- 这样可以避免继续复用 `MoveAction`，防止 Bot 报告里的移动行为和战斗行为互相污染。

## 接入真实协议时需要补齐

1. 在 `proto/Cmd.proto` 增加战斗协议号，例如：
   - `CS_BattleStart`
   - `SC_BattleStart`
   - `CS_BattleAction`
   - `SC_BattleAction`
   - `CS_BattleEnd`
   - `SC_BattleEnd`
2. 新增对应 `.proto` message，并通过 `proto_win.bat` 重新生成协议 Java。
3. GameServer 新增战斗 Controller，并在 Controller 中注册战斗协议。
4. BotServer 新增真实战斗 Action，替换 `SimulatedCombatAction` 的本地模拟逻辑。
5. BotServer 的 `CombatModule` 保持模块调度职责，不直接拼包。

## 建议接入形态

`CombatModule` 继续只负责步骤组织：

- 前置：确认登录成功、玩家在线、必要资源或队伍数据存在。
- 随机动作：按权重执行战斗开始、战斗操作、战斗结束等 Action。
- 响应处理：每个 Action 自己解析对应 SC 协议，并写入 `combat` 数据域。

真实协议落地后，优先新增：

- `BattleStartAction`
- `BattleActionAction`
- `BattleEndAction`

不要在 `CombatModule` 里直接构造协议包。
