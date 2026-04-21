# Net 模块 Packet 收敛方案（去掉 type，去掉 flags，统一包头）

> 目标：`ly.net.packet` 收敛为一个包类；协议头只保留最核心字段，降低认知负担。

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

## 4. 迁移方案（兼容优先）

由于旧协议包含 `type`，建议两阶段：

### 阶段 A：兼容期

- `CommonDecoder` 同时支持旧协议和新协议（通过版本/握手协商区分）。
- 编码默认发旧协议；灰度逐步切新协议。

### 阶段 B：收口期

- 全节点升级后切到新协议。
- 删除旧包类：`C2S/S2C/S2S/ConnectionAck`。
- 删除旧解码分支。

---

## 5. 代码改造清单

1. 新增 `ly.net.packet.MessagePacket`（唯一实现类）。
2. `MessagePacketFactory` 收敛为统一构造。
3. `CommonEncoder/CommonDecoder` 改为固定头编解码。
4. `HandlerContext` / 各 Router 泛型改用 `MessagePacket`。
5. `ConnectionAckPacket` 下线，改 `CMD_ACK`。

---

## 6. 测试要求

1. 编解码闭环测试（含空包体/大包体）。
2. 新协议双向互通测试。
3. 兼容期新旧互通测试（若保留灰度）。
4. Login/Gate/Game + RPC + Bot 回归压测。

---

## 7. 推进顺序

1. 落地 `MessagePacket` + 编解码器（兼容旧协议）。
2. 逐模块替换引用（Gate -> Game -> Bot -> RPC）。
3. 删除旧类与兼容逻辑。

---

## 8. 下一步可执行项

如你确认，我下一步直接给你提交代码补丁：

- `MessagePacket` 新类（22字节统一头）
- `CommonEncoder/CommonDecoder` 最小改造
- `CMD_ACK` 替代 `ConnectionAckPacket`
