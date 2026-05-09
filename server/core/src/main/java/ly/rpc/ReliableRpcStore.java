package ly.rpc;

import java.util.List;
import java.util.concurrent.TimeUnit;
import ly.LoggerDef;
import ly.ServerContext;
import ly.net.packet.AbstractMessagePacket;
import ly.redis.RedisUtils;

/** RPC 可靠消息 Redis outbox，保存发送失败或超时后需要补发的消息。 */
public class ReliableRpcStore {
  private static final ReliableRpcStore INSTANCE = new ReliableRpcStore();
  private static final int MAX_RETRY_COUNT = 20;
  private static final long INITIAL_RETRY_DELAY_MILLIS = 5_000L;
  private static final long MAX_RETRY_DELAY_MILLIS = TimeUnit.HOURS.toMillis(8);
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

    long lastReplayAt = 0L;
    long now = System.currentTimeMillis();
    for (ReliableRpcMessage message : messages) {
      if (message == null) {
        continue;
      }
      if (message.getNextRetryAt() > now) {
        continue;
      }
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
      if (connector.sendPacket(message.toPacket())) {
        RedisUtils.listRemove(key, message);
        LoggerDef.NetLogger.info(
            "replayed reliable rpc, msgId={}, target={}, cmd={}",
            message.getMsgId(),
            targetServerId,
            message.getCmd());
      } else {
        message.increaseRetryCount(System.currentTimeMillis() + calculateRetryDelayMillis(message.getRetryCount() + 1));
        RedisUtils.listRemove(key, message);
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

  public void replayAllAvailableTargets() {
    RpcService.getInstance()
        .getConnectedTargetServerIds()
        .forEach(this::replayForTarget);
  }

  long calculateRetryDelayMillis(int retryCount) {
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
