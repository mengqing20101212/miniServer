package ly.rpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.ServerContext;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;
import ly.redis.RedisUtils;

/**
 * RPC 可靠消息 Redis outbox。
 *
 * <p>这里只保存“当前服务器发往目标服务器”的待补发消息。目标服恢复后仍然通过正常 TCP RPC
 * 通道补发，不直接在目标服本地执行，避免绕过连接、session 和现有路由校验。
 */
public class ReliableRpcStore {
  private static final ReliableRpcStore INSTANCE = new ReliableRpcStore();
  private static final int MAX_RETRY_COUNT = 20;
  private static final long INITIAL_RETRY_DELAY_MILLIS = 5_000L;
  // 目标服故障可能持续很久，最大退避放到 8 小时，避免长故障期间每几分钟持续冲击目标服。
  private static final long MAX_RETRY_DELAY_MILLIS = TimeUnit.HOURS.toMillis(8);
  // 补发成功路径也要限频，防止目标服刚恢复时被历史积压 RPC 打满。
  private static final long REPLAY_INTERVAL_MILLIS = 100L;
  private static final long MESSAGE_EXPIRE_MILLIS = TimeUnit.HOURS.toMillis(72);

  private ReliableRpcStore() {}

  public static ReliableRpcStore getInstance() {
    return INSTANCE;
  }

  public boolean save(String targetServerId, AbstractMessagePacket packet, String reason) {
    if (RedisUtils.redissonClient == null) {
      LoggerDef.NetLogger.error("save reliable rpc failed, redis not initialized, target={}", targetServerId);
      return false;
    }
    if (targetServerId == null || targetServerId.isBlank() || packet == null) {
      LoggerDef.NetLogger.error("save reliable rpc failed, param invalid, target={}", targetServerId);
      return false;
    }

    String sourceServerId = currentServerId();
    // Redis list 作为发送方 outbox：同一个 source -> target 的消息保存在同一条队列里。
    ReliableRpcMessage message =
        ReliableRpcMessage.from(
            nextMsgId(sourceServerId, targetServerId),
            sourceServerId,
            targetServerId,
            packet,
            reason);
    RedisUtils.listAdd(queueKey(sourceServerId, targetServerId), message);
    LoggerDef.NetLogger.warn(
        "saved reliable rpc, msgId={}, target={}, cmd={}, reason={}",
        message.getMsgId(),
        targetServerId,
        message.getCmd(),
        reason);
    return true;
  }

  public void replayForTarget(String targetServerId) {
    replayForTarget(currentServerId(), targetServerId);
  }

  void replayForTarget(String sourceServerId, String targetServerId) {
    if (RedisUtils.redissonClient == null || targetServerId == null || targetServerId.isBlank()) {
      return;
    }
    String key = queueKey(sourceServerId, targetServerId);
    List<ReliableRpcMessage> messages = RedisUtils.listGetAll(key);
    if (messages.isEmpty()) {
      return;
    }

    RpcNodeConnector connector = RpcService.getInstance().getRpcNodeConnector(targetServerId);
    if (connector == null || !connector.isConnect()) {
      return;
    }

    // listGetAll 只是快照；成功补发后按 msgId 删除，失败则更新重试信息后放回队列。
    long lastReplayAt = 0L;
    long now = System.currentTimeMillis();
    for (ReliableRpcMessage message : messages) {
      if (message == null) {
        continue;
      }
      if (message.getNextRetryAt() > now) {
        LoggerDef.NetLogger.debug(
            "reliable rpc in backoff, msgId={}, target={}, cmd={}, retryCount={}, nextRetryAt={}, waitMs={}",
            message.getMsgId(),
            targetServerId,
            message.getCmd(),
            message.getRetryCount(),
            message.getNextRetryAt(),
            message.getNextRetryAt() - now);
        continue;
      }
      // 超过保留时间或重试次数后不再补发，避免永远占用 Redis 队列。
      if (now - message.getCreatedAt() > MESSAGE_EXPIRE_MILLIS
          || message.getRetryCount() >= MAX_RETRY_COUNT) {
        RedisUtils.listRemove(key, message);
        LoggerDef.NetLogger.error(
            "reliable rpc expired, msgId={}, target={}, cmd={}, retryCount={}",
            message.getMsgId(),
            targetServerId,
            message.getCmd(),
            message.getRetryCount());
        continue;
      }

      sleepForReplayRateLimit(lastReplayAt);
      lastReplayAt = System.currentTimeMillis();
      // 为补发包生成唯一 callId，用于匹配回包
      long callId = System.nanoTime() ^ Thread.currentThread().threadId();
      AbstractMessagePacket replayPacket = buildReplayPacket(message, callId);
      if (replayPacket == null) {
        LoggerDef.NetLogger.error("build replay packet failed, msgId={}", message.getMsgId());
        continue;
      }
      if (connector.sendPacket(replayPacket)) {
        if (requiresReplayResponse(message) && !waitAndHandleReplayResponse(connector, message, callId)) {
          RedisUtils.listRemove(key, message);
          message.increaseRetryCount(System.currentTimeMillis() + calculateRetryDelayMillis(message.getRetryCount() + 1));
          RedisUtils.listAdd(key, message);
          LoggerDef.NetLogger.warn(
              "replay reliable rpc response timeout, msgId={}, target={}, retryCount={}",
              message.getMsgId(),
              targetServerId,
              message.getRetryCount());
          return;
        }
        RedisUtils.listRemove(key, message);
        LoggerDef.NetLogger.info(
            "replayed reliable rpc, msgId={}, target={}, cmd={}",
            message.getMsgId(),
            targetServerId,
            message.getCmd());
      } else {
        // 补发失败后放回队列尾部，下一轮按 nextRetryAt 再尝试。
        RedisUtils.listRemove(key, message);
        message.increaseRetryCount(System.currentTimeMillis() + calculateRetryDelayMillis(message.getRetryCount() + 1));
        RedisUtils.listAdd(key, message);
        LoggerDef.NetLogger.warn(
            "replay reliable rpc failed, msgId={}, target={}, retryCount={}",
            message.getMsgId(),
            targetServerId,
            message.getRetryCount());
        return;
      }
    }
  }

  private boolean requiresReplayResponse(ReliableRpcMessage message) {
    return message.getCmd() == Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE;
  }

  private boolean waitAndHandleReplayResponse(RpcNodeConnector connector, ReliableRpcMessage message, long callId) {
    int expectedCmd = Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE;
    LoggerDef.NetLogger.info(
        "[ReplayWait] waiting for response, msgId={}, callId={}, cmd=SC_Gate2GameRpcGameCall, queueSize={}",
        message.getMsgId(), callId, connector.getClient().getReceivePacketQueueSize());

    long deadline = System.currentTimeMillis() + 10_000L;
    while (System.currentTimeMillis() < deadline) {
      AbstractMessagePacket response =
          connector.getClient().getReceiveMsgByCallId(callId, expectedCmd);
      if (response != null) {
        // 校验 callId
        Server.scGate2GameRpcGameCall resp =
            (Server.scGate2GameRpcGameCall)
                ProtoMessageFactory.createProtoMessage(expectedCmd, response.getData());
        if (resp == null || resp.getCallId() != callId) {
          // callId 不匹配，放回队列继续等
          LoggerDef.NetLogger.warn(
              "[ReplayWait] callId mismatch, expected={}, got={}, put back",
              callId, resp != null ? resp.getCallId() : "null");
          connector.getClient().putBackPacket(response);
          continue;
        }
        // 校验内层 cmd
        int innerCmd = resp.getCmd();
        if (innerCmd == Cmd.CMD.SC_ErrorCode_VALUE) {
          // 错误码回包，Game 已处理，算送达成功
          LoggerDef.NetLogger.info(
              "[ReplayWait] received SC_ErrorCode via SC_Gate2GameRpcGameCall, callId={}, treating as delivered",
              callId);
        } else {
          // 正常业务回包
          LoggerDef.NetLogger.info(
              "[ReplayWait] received normal response, callId={}, innerCmd={}, treating as delivered",
              callId, innerCmd);
        }
        return true;
      }
      try {
        Thread.sleep(10L);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return false;
      }
    }
    LoggerDef.NetLogger.warn("[ReplayWait] response TIMEOUT after 10s, msgId={}, callId={}", message.getMsgId(), callId);
    return false;
  }

  /**
   * 构建补发包：从原始消息重建 csGate2GameRpcGameCall，写入 callId。
   */
  private AbstractMessagePacket buildReplayPacket(ReliableRpcMessage message, long callId) {
    if (message.getCmd() != Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE) {
      return message.toPacket();
    }
    try {
      Server.csGate2GameRpcGameCall original =
          (Server.csGate2GameRpcGameCall)
              ProtoMessageFactory.createProtoMessage(Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE, message.toPacket().getData());
      if (original == null) {
        return message.toPacket();
      }
      Server.csGate2GameRpcGameCall withCallId =
          Server.csGate2GameRpcGameCall.newBuilder(original)
              .setCallId(callId)
              .build();
      AbstractMessagePacket packet =
          new AbstractMessagePacket(
              message.toPacket().getGuid(),
              Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE,
              0, 0,
              withCallId.toByteArray());
      return packet;
    } catch (Exception e) {
      LoggerDef.NetLogger.error("buildReplayPacket failed, msgId={}", message.getMsgId(), e);
      return message.toPacket();
    }
  }

  public void replayAllAvailableTargets() {
    RpcService.getInstance()
        .getConnectedTargetServerIds()
        .forEach(this::replayForTarget);
  }

  long calculateRetryDelayMillis(int retryCount) {
    // 指数退避：5s、10s、20s... 最大 8 小时。
    long delay = INITIAL_RETRY_DELAY_MILLIS;
    for (int i = 1; i < retryCount; i++) {
      if (delay >= MAX_RETRY_DELAY_MILLIS / 2) {
        return MAX_RETRY_DELAY_MILLIS;
      }
      delay *= 2;
    }
    return Math.min(delay, MAX_RETRY_DELAY_MILLIS);
  }

  String queueKey(String sourceServerId, String targetServerId) {
    // 按环境、发送方、目标方隔离队列，避免不同服务器互相消费可靠 RPC。
    return "rpc:reliable:"
        + safeSegment(ServerContext.ENV, "unknown-env")
        + ":"
        + safeSegment(sourceServerId, "unknown-source")
        + ":"
        + safeSegment(targetServerId, "unknown-target");
  }

  private String nextMsgId(String sourceServerId, String targetServerId) {
    long seq = RedisUtils.incr("rpc:reliable:id:" + safeSegment(sourceServerId, "unknown-source"));
    return safeSegment(sourceServerId, "unknown-source")
        + "-"
        + safeSegment(targetServerId, "unknown-target")
        + "-"
        + System.currentTimeMillis()
        + "-"
        + seq;
  }

  private String currentServerId() {
    return ServerContext.getServerId() == null ? "unknown-source" : ServerContext.getServerId();
  }

  private static String safeSegment(String value, String defaultValue) {
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    return value.replaceAll("[^a-zA-Z0-9._-]", "_");
  }

  private void sleepForReplayRateLimit(long lastReplayAt) {
    long waitMillis = REPLAY_INTERVAL_MILLIS - (System.currentTimeMillis() - lastReplayAt);
    if (waitMillis <= 0) {
      return;
    }
    try {
      Thread.sleep(waitMillis);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
