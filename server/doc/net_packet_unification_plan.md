# Net 模块 Packet 收敛方案（去掉 type 字段，统一包头）

> 目标：将 `ly.net.packet` 多实现类收敛为**一个包类**，并且移除 `type` 字段；所有消息使用同一套固定包头。

## 1. 约束与结论

根据你的要求，新的协议约束是：

1. 不再使用 `type`（不区分 S2C/S2S/C2S/ACK 的头部类型字节）。
2. 所有包头字段、顺序、长度完全一致。
3. 语义（请求/响应/ACK/跨服）由 `cmd` 语义和路由上下文判断，而不是由 `type` 判断。

---

## 2. 统一包模型（建议）

统一类：`MessagePacket`（或 `UnifiedMessagePacket`）。

### 2.1 固定包头（所有报文一致）

建议固定头如下（总长 26 字节）：

- `short length`：整包长度（头 + body）
- `int cmd`：消息号（唯一语义入口）
- `int sid`：会话ID
- `int seq`：序列号
- `long guid`：玩家/实体唯一ID
- `int time`：秒级时间戳
- `int flags`：扩展位（ACK、压缩、加密、重传标记等）

包体：
- `byte[] data`

> 注：ACK 不再是独立包类型，改为 `cmd = CMD_ACK`（预留命令号）+ `flags` 标记（可选）+ `data` 可空。

### 2.2 为什么需要 `flags`

去掉 `type` 后，仍需低成本携带控制语义，`flags` 用于承载：

- `ACK` 位
- `ERROR` 位
- `COMPRESS` 位
- `ENCRYPT` 位

这样可避免再次引入“多包类分支”。

---

## 3. 编解码规则（统一）

## 3.1 统一编码

`encode` 永远按固定顺序写：

1. `length`
2. `cmd`
3. `sid`
4. `seq`
5. `guid`
6. `time`
7. `flags`
8. `data`

## 3.2 统一解码

`decode` 永远按相同顺序读，不再 `switch(type)`。

## 3.3 路由判定

- 业务路由主要依赖 `cmd`。
- 通道/连接上下文决定方向（客户端连接、服务器连接）。
- ACK 通过 `cmd` 或 `flags` 识别。

---

## 4. 与现网兼容（重点）

由于旧协议有 `type` 且各类型头部不同，不能直接硬切。建议两阶段：

### 阶段 A：双协议解码 + 新协议编码开关

- `CommonDecoder` 支持识别旧格式与新格式（通过魔数/version 或连接级协商）。
- 编码默认仍发旧协议；灰度环境开启新协议发送。

### 阶段 B：全量切换

- 所有节点升级并验证后，统一切换为新协议头。
- 删除旧的 `C2S/S2C/S2S/ConnectionAck` 与旧解码分支。

> 如果你们允许短暂不兼容发布（全服停机切换），可以省略双协议阶段。

---

## 5. 代码改造清单

1. 新增 `ly.net.packet.MessagePacket`（唯一包实现）。
2. 新增 `MessagePacketFlag`（bit 位常量）。
3. `MessagePacketFactory` 改为只创建统一包。
4. `CommonEncoder/CommonDecoder` 改为固定头编解码。
5. `HandlerContext`/路由泛型逐步改为 `MessagePacket`。
6. `ConnectionAckPacket` 删除，ACK 改为普通 `cmd`。

---

## 6. 测试要求（必须）

1. **编码解码闭环测试**：固定头字段全覆盖（含空 body / 大 body）。
2. **互通测试**：
   - 新协议客户端 -> 新协议服务端
   - 新协议服务端 -> 新协议客户端
3. **迁移期兼容测试**（若采用双协议）：
   - 旧发新收、新发旧收
4. **核心链路回归**：Login/Gate/Game、RPC 同步调用、Bot 压测。

---

## 7. 推进顺序（最小风险）

1. 先落地统一 `MessagePacket` + 编解码器（保留旧协议兼容）。
2. 再改 Gate/Game/Bot/RPC 的包类型引用。
3. 最后删旧类和旧协议分支。

---

## 8. 建议的下一步实现

我可以下一步直接提交一版最小可运行补丁，包含：

- `MessagePacket` 新类（无 type，固定头）
- `CommonEncoder/CommonDecoder` 最小改造
- ACK 从 `ConnectionAckPacket` 切到 `CMD_ACK`
- 保留兼容开关（可配）以支持灰度
