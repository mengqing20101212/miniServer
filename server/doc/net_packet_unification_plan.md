# Net 模块 Packet 收敛方案（C2S/S2C/S2S/ACK -> 统一 Packet）

> 目标：把 `ly.net.packet` 下多实现类收敛为一个统一实现，降低维护成本，同时保证线上协议兼容、可灰度迁移。

## 1. 现状与痛点

当前 `core` 中存在多个包实现类：

- `C2SMessagePacket`
- `S2CMessagePacket`
- `S2SMessagePacket`
- `ConnectionAckPacket`
- 抽象基类 `AbstractMessagePacket`
- 工厂 `MessagePacketFactory`

主要问题：

1. **字段高度重复**：`cmd/sid/seq/guid/data` 在多类中重复定义与重复编解码。
2. **编解码分散**：每个类维护自己的 `getHeadLength/getPacketLen/encode/decode`，改协议时改动面大。
3. **路由泛型复杂**：大量业务代码绑定具体包类型（如 Gate 绑定 C2S，Game 绑定 S2S），扩展新类型成本高。
4. **ACK 特例化**：`ConnectionAckPacket` 只多一个 `sessionId`，也走独立类，增加分支。

---

## 2. 目标模型

新增一个统一包类（示例名：`UnifiedMessagePacket`），保留 `type` 表示语义方向：

- `type=0`：S2C
- `type=1`：S2S
- `type=2`：C2S
- `type=3`：ACK

### 2.1 统一字段

统一类固定包含：

- `short length`
- `byte type`
- `int cmd`
- `int sid`
- `int seq`
- `long guid`
- `int time`
- `int ackSessionId`（仅 ACK 使用）
- `byte[] data`

说明：
- 对某一 `type` 不适用的字段，置默认值（0 或空数组）。
- 保持二进制协议不变：即各 `type` 在网络上的字段顺序和旧版本一致。

---

## 3. 编解码设计（关键）

统一类中以 `switch(type)` 实现 `encode/decode`，但**严格复用旧字节布局**。

### 3.1 头长与包长规则

- `COMMON_HEAD = 2(len) + 1(type)`
- `S2C_HEAD = COMMON_HEAD + cmd(4)+seq(4)+sid(4)+time(4)`
- `S2S_HEAD = COMMON_HEAD + cmd(4)+seq(4)+sid(4)+guid(8)`
- `C2S_HEAD = COMMON_HEAD + cmd(4)+sid(4)+guid(8)+seq(4)`（保持当前实现顺序）
- `ACK_HEAD = COMMON_HEAD`

### 3.2 ACK 处理

- `type=ACK` 时，body 仅 `ackSessionId(int)`。
- 这样可删除独立 `ConnectionAckPacket`。

---

## 4. 迁移策略（建议 4 个阶段）

## Phase 0：加观测（不改行为）

- 给 `CommonDecoder` 增加日志/指标：统计每种 `type` 的 QPS 与异常包。
- 输出关键指标：decode失败率、未知type数量、包长非法数量。

> 目的：先掌握真实流量，避免盲改。

## Phase 1：引入统一类 + 兼容工厂

- 新增 `UnifiedMessagePacket`。
- 改造 `MessagePacketFactory`：
  - `createMessagePacket(type)` 先返回统一类；
  - 对外保留 `createS2SMessagePacket/createC2SMessagePacket/createS2CMessagePacket`，但内部构造统一类。
- 暂时保留旧类，作为兼容壳（或适配器）。

> 结果：业务层感知最小，先让编解码统一。

## Phase 2：业务层改泛型与路由

按模块推进（先边缘，后核心）：

1. `BotServer`（风险低）
2. `GateServer`
3. `GameServer`
4. `core.rpc`

改造要点：
- `HandlerContext<S, P>` 的 `P` 从具体包类迁移到统一包类；
- 业务代码通过 `packet.getType()` + 访问器判断语义；
- 删除 `instanceof C2S/S2S/...` 分支。

## Phase 3：删除旧类与收口 API

当全部调用点迁完后：

- 删除 `C2SMessagePacket/S2CMessagePacket/S2SMessagePacket/ConnectionAckPacket`。
- `MessagePacketFactory` 仅保留统一创建方法（可保留过渡别名 1 个版本）。
- 清理测试与文档。

---

## 5. 风险与规避

1. **协议兼容风险（最高）**
   - 规避：先做“字节级回归测试”，保证同输入下新旧编码字节完全一致。

2. **路由泛型改造面大**
   - 规避：先通过适配器过渡，分模块迁移，不一次性全量替换。

3. **ACK 链路脆弱（握手失败会导致全链路不可用）**
   - 规避：先单独压测 ACK + reconnect 场景，再逐步放量。

---

## 6. 测试方案（必须项）

### 6.1 单元测试

- `UnifiedMessagePacket` 针对 4 种 `type` 的 encode/decode 循环测试。
- 边界测试：空 data、超大 data、非法 packetLen、未知 type。

### 6.2 兼容性测试

- 新旧类同字段输入，比较输出字节数组完全一致。
- 旧端发包 -> 新端解包、新端发包 -> 旧端解包 的双向互通测试。

### 6.3 集成测试

- 登录链路：Login -> Gate -> Game 全流程。
- RPC 链路：S2S 请求/同步返回。
- Bot 压测：持续 30 分钟，关注 decode 错误与 seq 校验异常。

---

## 7. 建议的最终代码结构

建议收敛后 `ly.net.packet` 仅保留：

- `MessagePacket`（统一实现类）
- `MessagePacketType`（枚举，替代 magic number）
- `MessagePacketFactory`（可选，主要做构建器/便捷方法）

可选增强：
- `MessagePacketBuilder`，避免构造参数过长。

---

## 8. 落地优先级建议

1. 先做 **统一编码内核**（Phase 1）
2. 再改 **Gate/Game 路由泛型**（Phase 2）
3. 最后清理旧类（Phase 3）

如果你希望，我下一步可以直接给出：

- 一版 `UnifiedMessagePacket` 的可编译代码草案；
- 以及 `CommonDecoder/MessagePacketFactory/GateConnectSession` 的最小改动补丁（先跑通 Gate + Game 主链路）。
