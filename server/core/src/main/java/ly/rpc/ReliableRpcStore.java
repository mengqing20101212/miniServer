package ly.rpc;

import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import ly.LoggerDef;
import ly.ServerContext;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;

/** Failure-only RocksDB outbox. Normal successful calls never touch this store. */
public class ReliableRpcStore implements AutoCloseable {
  private static final ReliableRpcStore INSTANCE = new ReliableRpcStore();
  private static final long MAX_RETRY_DELAY_MILLIS = TimeUnit.HOURS.toMillis(8);
  private final Set<String> replaying = ConcurrentHashMap.newKeySet();
  private final Semaphore replaySlots = new Semaphore(8);
  private volatile RocksRpcOutbox outbox;
  private final java.util.function.Function<String, RpcNodeConnector> connectors;

  private volatile boolean closed;
  private int replayCursor;
  private ReliableRpcStore() { connectors = target -> RpcService.getInstance().getRpcNodeConnector(target); }
  ReliableRpcStore(Path directory, java.util.function.Function<String, RpcNodeConnector> connectors) {
    this.connectors = connectors;
    initialize(directory);
  }
  int pendingCount(String target) { return outbox.snapshot(target).size(); }

  public static ReliableRpcStore getInstance() { return INSTANCE; }

  public synchronized void initialize(Path directory) {
    if (closed) { throw new IllegalStateException("RPC outbox closed"); }
    if (outbox != null) { return; }
    try { outbox = new RocksRpcOutbox(directory); }
    catch (Exception error) { throw new IllegalStateException("Cannot open RPC outbox: " + directory, error); }
  }

  public void initialize() {
    ly.config.DbConfig config = ServerContext.serverConfig == null
        ? new ly.config.DbConfig() : ServerContext.serverConfig.db;
    Path directory = Path.of(System.getProperty("miniserver.rpc.rocksPath",
        System.getProperty("miniserver.rocksPath", config.rocksPath)))
        .resolve(safeSegment(ServerContext.ENV, "local"))
        .resolve(safeSegment(ServerContext.getServerId(), "server")).resolve("rpc-outbox");
    initialize(directory);
  }

  /** Explicit upgrade operation only; normal RPC/replay does not access Redis. */
  public synchronized int importLegacyRedis(String target) {
    if (closed || outbox == null || target == null || target.isBlank()) {
      throw new IllegalStateException("Initialize the RPC outbox and provide a target before importing");
    }
    if (!replaying.isEmpty()) { throw new IllegalStateException("Stop replay before importing legacy messages"); }
    String key = "rpc:reliable:" + safeSegment(ServerContext.ENV, "unknown-env") + ":"
        + safeSegment(ServerContext.getServerId(), "unknown-source") + ":" + safeSegment(target, "unknown-target");
    int count = 0;
    if (ly.redis.RedisUtils.redissonClient == null) { throw new IllegalStateException("Initialize legacy Redis before importing"); }
    org.redisson.api.RList<ReliableRpcMessage> legacy = ly.redis.RedisUtils.redissonClient.getList(key);
    java.util.List<ReliableRpcMessage> messages = legacy.readAll();
    for (ReliableRpcMessage message : messages) {
      if (!target.equals(message.getTargetServerId())) { throw new IllegalStateException("Legacy target mismatch"); }
      try {
        outbox.put(message);
        if (!legacy.remove(message)) { throw new IllegalStateException("Legacy message concurrently changed"); }
        count++;
      } catch (Exception error) {
        throw new IllegalStateException("Legacy RPC import stopped; original remaining messages retained", error);
      }
    }
    return count;
  }

  public boolean save(String target, MessagePacket packet, String reason) {
    if (target == null || target.isBlank() || packet == null) {
      LoggerDef.NetLogger.error("RPC failure not persisted: invalid target or packet, target={}", target);
      return false;
    }
    long callId = callId(packet);
    String id = target + ":" + packet.getCmd() + ":" + (callId > 0 ? callId : UUID.randomUUID());
    // Log first: the deliberately accepted crash window ends after the synchronous WAL write.
    LoggerDef.NetLogger.warn("RPC failed; persisting for replay, requestId={}, target={}, cmd={}, reason={}",
        id, target, packet.getCmd(), reason);
    try {
      if (outbox == null) { initialize(); }
      ReliableRpcMessage message = ReliableRpcMessage.from(id, ServerContext.getServerId(), target, packet, reason);
      outbox.put(message);
      return true;
    } catch (Exception error) {
      LoggerDef.NetLogger.error("RPC FAILURE NOT PERSISTED, requestId={}, target={}, cmd={}, reason={}",
          id, target, packet.getCmd(), reason, error);
      return false;
    }
  }

  /** All trigger sources share one lease per target; disk and network work never block a tick. */
  public synchronized void replayForTarget(String target) {
    RocksRpcOutbox current = outbox;
    if (closed || current == null || target == null || !replaying.add(target)) { return; }
    if (!replaySlots.tryAcquire()) { replaying.remove(target); return; }
    Thread.ofVirtual().name("rpc-outbox-" + target).start(() -> {
      try { replay(current, target); }
      catch (Exception error) { LoggerDef.NetLogger.error("RPC replay failed; records retained, target={}", target, error); }
      finally { replaying.remove(target); replaySlots.release(); }
    });
  }

  private void replay(RocksRpcOutbox current, String target) throws Exception {
    for (ReliableRpcMessage message : current.snapshot(target)) {
      if (closed) { return; }
      if (message.getNextRetryAt() > System.currentTimeMillis()) { continue; }
      MessagePacket packet = message.toPacket();
      int responseCmd = responseCmd(packet.getCmd());
      // An unacknowledged one-way packet cannot safely be marked delivered or auto-retried.
      if (responseCmd == 0 || callId(packet) <= 0) {
        LoggerDef.NetLogger.error("RPC retained for manual handling: no replay acknowledgement, requestId={}, target={}, cmd={}",
            message.getMsgId(), target, packet.getCmd());
        postpone(current, message);
        continue;
      }
      RpcNodeConnector connector = connectors.apply(target);
      if (connector == null || !connector.isConnect()) { postpone(current, message); return; }
      MessagePacket response;
      try { response = connector.requestAsync(packet, responseCmd, 10_000).get(); }
      catch (java.util.concurrent.ExecutionException error) {
        LoggerDef.NetLogger.warn("RPC replay unconfirmed; retained, requestId={}, target={}", message.getMsgId(), target, error.getCause());
        postpone(current, message); continue;
      }
      boolean confirmed = validateResponse(message, response);
      // Forwarding a confirmed Gate response is local notification. Its failure must not re-execute business.
      if (confirmed && message.getCmd() == Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE) {
        try {
          if (!RpcService.getInstance().handleReliableReplayResponse(message, response)) {
            LoggerDef.NetLogger.warn("RPC completed but local response forwarding failed, requestId={}", message.getMsgId());
          }
        } catch (Exception error) { LoggerDef.NetLogger.warn("RPC completed but local notification failed, requestId={}", message.getMsgId(), error); }
      }
      if (confirmed) { current.remove(message.getMsgId()); }
      else { postpone(current, message); }
      Thread.sleep(100); // Bounded replay rate, outside logic and IO threads.
    }
  }

  private void postpone(RocksRpcOutbox current, ReliableRpcMessage message) throws Exception {
    message.increaseRetryCount(System.currentTimeMillis() + calculateRetryDelayMillis(message.getRetryCount() + 1));
    current.put(message); // Atomic replacement: never delete before updating retry state.
  }

  static boolean validateResponse(ReliableRpcMessage message, MessagePacket response) {
    try {
      if (message.getCmd() == Cmd.CMD.CS_Server2Server_VALUE) {
        Server.csServer2Server request = Server.csServer2Server.parseFrom(message.toPacket().getData());
        Server.scServer2Server result = Server.scServer2Server.parseFrom(response.getData());
        return response.getCmd() == Cmd.CMD.SC_Server2Server_VALUE && result.getCallId() == request.getCallId()
            && (result.getCmd() == request.getCmd() + 1 || result.getCmd() == Cmd.CMD.SC_ErrorCode_VALUE);
      }
      Server.csGate2GameRpcGameCall request = Server.csGate2GameRpcGameCall.parseFrom(message.toPacket().getData());
      Server.scGate2GameRpcGameCall result = Server.scGate2GameRpcGameCall.parseFrom(response.getData());
      return response.getCmd() == Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE && result.getCallId() == request.getCallId()
          && (result.getClientCmd() == request.getClientCmd() + 1 || result.getClientCmd() == Cmd.CMD.SC_ErrorCode_VALUE);
    } catch (Exception error) { return false; }
  }

  public static long callId(MessagePacket packet) {
    try {
      if (packet.getCmd() == Cmd.CMD.CS_Server2Server_VALUE) { return Server.csServer2Server.parseFrom(packet.getData()).getCallId(); }
      if (packet.getCmd() == Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE) { return Server.csGate2GameRpcGameCall.parseFrom(packet.getData()).getCallId(); }
    } catch (Exception error) { LoggerDef.NetLogger.warn("Cannot decode RPC request identity, cmd={}", packet.getCmd(), error); }
    return 0;
  }
  static int responseCmd(int cmd) {
    if (cmd == Cmd.CMD.CS_Server2Server_VALUE) { return Cmd.CMD.SC_Server2Server_VALUE; }
    if (cmd == Cmd.CMD.CS_Gate2GameRpcGameCall_VALUE) { return Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE; }
    return 0;
  }
  public synchronized void replayAllAvailableTargets() {
    RocksRpcOutbox current = outbox;
    if (closed || current == null) { return; }
    java.util.List<String> targets = new java.util.ArrayList<>(current.targets());
    targets.sort(String::compareTo);
    if (targets.isEmpty()) { return; }
    int start = replayCursor % targets.size();
    // Rotate the starting target so a few long outages cannot monopolize all replay slots.
    replayCursor = (start + Math.min(8, targets.size())) % targets.size();
    for (int i = 0; i < targets.size(); i++) { replayForTarget(targets.get((start + i) % targets.size())); }
  }
  long calculateRetryDelayMillis(int retryCount) {
    long delay = 5000;
    for (int i = 1; i < retryCount; i++) {
      if (delay >= MAX_RETRY_DELAY_MILLIS / 2) { return MAX_RETRY_DELAY_MILLIS; }
      delay *= 2;
    }
    return Math.min(delay, MAX_RETRY_DELAY_MILLIS);
  }
  private static String safeSegment(String value, String fallback) {
    return value == null || value.isBlank() ? fallback : value.replaceAll("[^a-zA-Z0-9._-]", "_");
  }
  @Override public synchronized void close() {
    closed = true;
    if (outbox != null) { outbox.close(); }
  }
}
