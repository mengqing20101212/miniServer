# Net 模块 Packet 收敛记录（去掉 type，去掉 flags，统一包头）

> 目标：`ly.net.packet` 收敛为一个包类；协议头只保留最核心字段，降低认知负担。

> 当前状态：统一包头和 `CMD_ACK` 已在代码中落地。运行时统一使用
> `MessagePacket` + `MessagePacketFactory`，旧 `C2SMessagePacket` 备份文件已删除。

## 1. 约束（按你的要求）

1. 去掉 `type` 字段。
2. 去掉 `flags` 字段。
3. 所有消息使用同一套固定包头。
4. 语义由 `cmd` + 连接上下文决定。

---

## 2. 统一包模型

统一类：`MessagePacket`。

### 2.1 固定包头（所有包一致）

建议固定头（22 字节）：

- `short length`：整包长度（头 + body）
- `int cmd`：消息号
- `int sid`：会话ID
- `int seq`：序列号
- `long guid`：玩家/实体ID
- `int time`：时间戳（秒）

包体：
- `byte[] data`

> ACK 也走普通包：`cmd = CMD_ACK`，`data` 可为空。

---

## 3. 编解码规则

## 3.1 encode 顺序

1. `length`
2. `cmd`
3. `sid`
4. `seq`
5. `guid`
6. `time`
7. `data`

## 3.2 decode 顺序

与 encode 完全一致，不再 `switch(type)`。

## 3.3 路由语义

- 业务分发：按 `cmd`。
- 方向识别：按连接上下文（client channel / server channel）。
- ACK 识别：按 `cmd == CMD_ACK`。

---

## 4. 已落地的迁移结果

- `MessagePacket` 已使用固定 22 字节包头：
  `[length:2][cmd:4][sid:4][seq:4][guid:8][time:4][data:N]`
- `CommonEncoder` / `CommonDecoder` 已按固定头编解码。
- ACK 已改为普通包：`cmd = CMD_ACK`。
- 旧 `C2SMessagePacket.java.bak3` 已删除，不再保留失效备份代码。

### 保留项

- 无。旧的 `createAbstractMessagePacket(...)` 兼容入口已经删除，后续统一使用 `createMessagePacket(...)`。

---

## 5. 代码改造清单

1. 已完成：`MessagePacket` 成为唯一运行时包实现。
2. 已完成：`MessagePacketFactory` 收敛为统一构造。
3. 已完成：`CommonEncoder/CommonDecoder` 改为固定头编解码。
4. 已完成：ACK 下线独立类型，改用 `CMD_ACK`。
5. 已完成：`AbstractMessagePacket` 重命名为 `MessagePacket`，同步更新引用。

---

## 6. 测试要求

1. 编解码闭环测试（含空包体/大包体）。
2. 新协议双向互通测试。
3. 兼容期新旧互通测试（若保留灰度）。
4. Login/Gate/Game + RPC + Bot 回归压测。

---

## 7. 推进顺序

1. 已完成：落地统一包头 + 编解码器。
2. 已完成：Gate / Game / Bot / RPC 都使用统一包。
3. 已完成：删除旧备份类与旧 ACK 类型。
4. 已完成：类名和主要工厂方法命名收口；旧工厂方法兼容入口已删除。

---

## 8. 下一步可执行项

当前没有必须立即执行的 Packet 结构改造。
