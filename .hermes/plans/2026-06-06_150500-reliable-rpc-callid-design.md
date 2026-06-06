# 可靠 RPC callId 方案设计

**目标**: 让 GateServer 的可靠 RPC 补发机制能够正确识别 GameServer 返回的 `SC_ErrorCode`，
避免"玩家不存在"等错误码被误判为"未响应"，导致无限退避重试。

**创建时间**: 2026-06-06

---

## 1. 问题根因

当前可靠 RPC 补发流程:

```
GateServer                          GameServer
    |                                   |
    |-- CS_Gate2GameRpcGameCall ------->|
    |   (inner: csHeroList, guid=123)   |
    |                                   |
    |   Case A: 正常处理                 |
    |<-- SC_Gate2GameRpcGameCall -------|
    |   (inner: scHeroList)             |
    |   seq = innerSeq + 1              |   ← Gate 匹配这个 ✅
    |                                   |
    |   Case B: 玩家不存在               |
    |<-- SC_ErrorCode ------------------|
    |   (msgId=cmd, errorCode=11)       |
    |   seq = innerSeq + 1              |   ← Gate 不认识这个 ❌
```

`ReliableRpcStore.waitAndHandleReplayResponse()` 只查找 `SC_Gate2GameRpcGameCall`，
不处理 `SC_ErrorCode`。GameServer 返回的错误码被留在 NetClient 接收队列里，
10 秒后超时 → 增加退避 → 永远重试。

### 为什么不能简单地用 seq 匹配 SC_ErrorCode?

`SC_ErrorCode` 是通用错误响应，可能来自多个路径:
- `Gate2GameRpcGameCallController` 的 `sendErrorMsg()` → 带 innerSeq + 1
- `LoginManager.sendErrorMsg()` → 带 loginSeq + 1
- `Player.sendErrorCode()` → 带 lastSeq + 1 或 0

如果同时有多个请求并发，仅靠 seq + cmd 可能匹配到错误的 `SC_ErrorCode`。
需要一个唯一标识来精确关联"请求-响应"。

---

## 2. 方案概述

在 `csGate2GameRpcGameCall` 和 `scErrorCode` 中各加一个 `callId` 字段:

```
csGate2GameRpcGameCall {
    ... existing fields ...
    int64 callId = 6;  // Gate 侧生成的唯一调用标识
}

scErrorCode {
    ... existing fields ...
    int64 callId = 3;  // 从请求中原样带回
}
```

- Gate 发送 RPC 时生成唯一 `callId`，写入 `csGate2GameRpcGameCall.callId`
- GameServer 处理失败时，从请求中取出 `callId`，写入 `scErrorCode.callId` 后返回
- Gate 的 replay handler 同时匹配 `SC_Gate2GameRpcGameCall` 和 `SC_ErrorCode`，
  通过 `callId` 确认是本次 RPC 的响应
- `callId` 默认值为 0 (proto3)，不影响现有不设 `callId` 的普通错误码流程

---

## 3. 协议变更

### 3.1 Server.proto

```protobuf
message csGate2GameRpcGameCall{
  int32 cmd = 1;
  int32 sid = 2;
  int64 guid = 3;
  int32 seq = 4;
  bytes data = 5;
  int64 callId = 6;  // NEW: 可靠 RPC 唯一调用 ID，普通 RPC 为 0
}
```

### 3.2 ErrorMsg.proto

```protobuf
message scErrorCode{
  int32 msgId = 1;       // 产生错误的消息号
  ErrorCode errorCode = 2;
  int64 callId = 3;      // NEW: 来自 csGate2GameRpcGameCall.callId，普通错误为 0
}
```

proto3 中新增字段默认值为 0，完全向后兼容。

---

## 4. 代码变更

### 4.1 GateServer 侧: 生成 callId

**文件**: `GateClient.java` → `sendPacketToGameServerSync()`

```java
// 生成唯一 callId
long callId = System.nanoTime() ^ Thread.currentThread().threadId();

Server.csGate2GameRpcGameCall.Builder req = Server.csGate2GameRpcGameCall.newBuilder();
req.setCmd(csPacket.getCmd());
req.setSid(csPacket.getSid());
req.setGuid(csPacket.getGuid());
req.setSeq(csPacket.getSeq());
req.setData(ByteString.copyFrom(csPacket.getData()));
req.setCallId(callId);  // NEW
```

**文件**: `GateClient.java` → `saveReliableRpcIfNeeded()`

补发保存时也要带上 `callId`，确保 replay 时能用。

### 4.2 GameServer 侧: 透传 callId

**文件**: `Gate2GameRpcGameCallController.java`

从请求中取出 `callId`，传给 `sendErrorMsg`:

```java
(context, req) -> {
    final int cmd = req.getCmd();
    final int seq = req.getSeq();
    final long guid = req.getGuid();
    final long callId = req.getCallId();  // NEW

    // ... existing logic ...

    if (player == null) {
        sendErrorMsgWithCallId(context.session(), guid, ErrorMsg.ErrorCode.PLAYER_NOT_EXIST,
                               seq, cmd, callId);  // NEW
        return;
    }
    if (banned) {
        sendErrorMsgWithCallId(context.session(), guid, ErrorMsg.ErrorCode.SYSTEM_ERROR,
                               seq, cmd, callId);  // NEW
        return;
    }
    // ... normal path unchanged ...
}
```

新增辅助方法或修改 `sendErrorMsg`:

**方案 A (推荐): 新增带 callId 的重载**

```java
// GameConnectSession.java
public void sendErrorMsg(long playerId, ErrorMsg.ErrorCode errorCode, int req, int cmd, long callId) {
    ErrorMsg.scErrorCode errorMsg = ErrorMsg.scErrorCode.newBuilder()
            .setErrorCode(errorCode)
            .setMsgId(cmd)
            .setCallId(callId)   // NEW
            .build();
    sendClientMsg(Cmd.CMD.SC_ErrorCode_VALUE, req + 1, playerId, errorMsg);
}
```

**方案 B: 直接修改现有 sendErrorMsg**

给现有方法加 `callId` 参数，所有调用点传 0（proto3 默认值，不影响客户端）。
缺点是改动点多。

### 4.3 GateServer 侧: 匹配 SC_ErrorCode

**文件**: `ReliableRpcStore.java` → `waitAndHandleReplayResponse()`

```java
private boolean waitAndHandleReplayResponse(RpcNodeConnector connector, ReliableRpcMessage message) {
    Server.csGate2GameRpcGameCall request = ...;
    if (request == null) return false;

    long callId = request.getCallId();
    int expectedSeq = request.getSeq() + 1;

    long deadline = System.currentTimeMillis() + 10_000L;
    while (System.currentTimeMillis() < deadline) {
        // 1. 尝试匹配正常响应
        AbstractMessagePacket response = connector.getClient()
                .getReceiveMsgBySeq(expectedSeq, Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE);
        if (response != null) {
            // 正常响应，删除可靠消息
            return handleSuccess(message, response);
        }

        // 2. 尝试匹配错误码响应 (NEW)
        AbstractMessagePacket errorPkt = connector.getClient()
                .getReceiveMsgBySeq(expectedSeq, Cmd.CMD.SC_ErrorCode_VALUE);
        if (errorPkt != null && callId != 0) {
            ErrorMsg.scErrorCode scError = (ErrorMsg.scErrorCode)
                    ProtoMessageFactory.createProtoMessage(Cmd.CMD.SC_ErrorCode_VALUE, errorPkt.getData());
            if (scError != null && scError.getCallId() == callId) {
                // Game 已处理（返回了业务错误），视为送达成功，删除可靠消息
                LoggerDef.NetLogger.info(
                    "[ReplayWait] received SC_ErrorCode for callId={}, errorCode={}, treating as delivered",
                    callId, scError.getErrorCode());
                return true;
            }
        }

        Thread.sleep(10L);
    }
    return false;
}
```

### 4.4 GateServer 侧: 重放时保留 callId

**文件**: `GateClient.java` → `saveReliableRpcIfNeeded()`

保存可靠消息时，`csGate2GameRpcGameCall` 中已包含 `callId`。
replay 时 `message.toPacket()` 序列化的数据自然携带 `callId`，无需额外处理。

---

## 5. 向后兼容性

| 场景 | callId 值 | 行为 |
|------|-----------|------|
| 普通同步 RPC (非可靠) | 0 | 不影响，Gate 不检查 SC_ErrorCode.callId |
| 新 Gate + 旧 Game | callId > 0 | Game 不设置 scErrorCode.callId (默认0)，Gate 收到 callId=0 时退化为"忽略该 SC_ErrorCode" |
| 旧 Gate + 新 Game | 0 | Game 收到 callId=0，不设置 scErrorCode.callId，行为不变 |
| 新 Gate + 新 Game | callId > 0 | 完整匹配流程生效 |

---

## 6. 影响范围

| 文件 | 变更类型 | 说明 |
|------|---------|------|
| `proto/Server.proto` | 修改 | csGate2GameRpcGameCall 加 callId 字段 |
| `proto/ErrorMsg.proto` | 修改 | scErrorCode 加 callId 字段 |
| `server/proto/` | 自动生成 | 重新生成 protobuf Java 类 |
| `server/GateServer/.../GateClient.java` | 修改 | 生成 callId，发送和保存时携带 |
| `server/GameServer/.../Gate2GameRpcGameCallController.java` | 修改 | 取出 callId，传给 sendErrorMsg |
| `server/GameServer/.../GameConnectSession.java` | 修改 | sendErrorMsg 新增 callId 参数重载 |
| `server/core/.../ReliableRpcStore.java` | 修改 | waitAndHandleReplayResponse 匹配 SC_ErrorCode |

---

## 7. 测试验证

### 7.1 单元测试

- `ReliableRpcStoreTest`: mock NetClient，验证收到带 callId 的 SC_ErrorCode 后返回 true

### 7.2 集成测试

1. 启动 Login → Game → Gate
2. 用 Bot 登录一个玩家
3. 停止 Game，从客户端发一个业务请求（会触发 Gate 保存可靠 RPC）
4. 重启 Game，观察 Gate 日志:
   - 应看到 `[ReplayWait] received SC_ErrorCode for callId=xxx, errorCode=PLAYER_NOT_EXIST`
   - 可靠消息应被删除，不再重试

### 7.3 现有 Redis 消息清理

部署后，之前积压的 3 条消息的 `csGate2GameRpcGameCall` 中没有 `callId`（默认0），
`waitAndHandleReplayResponse` 中 `callId == 0` 时不会匹配 SC_ErrorCode。
这些旧消息需要手动从 Redis 删除:

```bash
redis-cli DEL "rpc:reliable:ly:gate1001:game1001"
```

---

## 8. 备选方案对比

| 方案 | 优点 | 缺点 |
|------|------|------|
| **A: callId 方案 (推荐)** | 精确匹配，无歧义 | 需要改 proto，有生成-构建-部署链路 |
| B: 仅靠 seq + cmd 匹配 | 不改 proto | 并发时可能误匹配；需要排除其他来源的 SC_ErrorCode |
| C: GameServer 统一返回 SC_Gate2GameRpcGameCall | 不改 proto | 需要改所有 sendErrorMsg 调用点，侵入性大 |

---

## 9. 执行顺序

1. 修改 proto 文件 (Server.proto, ErrorMsg.proto)
2. 重新生成 proto Java 类
3. 修改 GameConnectSession (新增 sendErrorMsg 重载)
4. 修改 Gate2GameRpcGameCallController (传递 callId)
5. 修改 GateClient (生成 callId)
6. 修改 ReliableRpcStore (匹配 SC_ErrorCode)
7. 编译验证
8. 提交 git
