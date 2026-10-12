package ly.rpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import ly.net.packet.MessagePacket;

/** One tracker per target connection. Registration always precedes sending. */
public final class RpcResponseTracker implements AutoCloseable {
  private static final Semaphore GLOBAL = new Semaphore(8192);
  private static final ScheduledThreadPoolExecutor TIMER = new ScheduledThreadPoolExecutor(1, runnable -> {
    Thread thread = new Thread(runnable, "rpc-response-timeout");
    thread.setDaemon(true);
    return thread;
  });
  static { TIMER.setRemoveOnCancelPolicy(true); }
  private record Key(long callId, int cmd) {}
  private final Map<Key, CompletableFuture<MessagePacket>> pending = new HashMap<>();
  private final int capacity;
  private boolean closed;
  public RpcResponseTracker() { this(256); }
  RpcResponseTracker(int capacity) {
    if (capacity < 1) { throw new IllegalArgumentException("capacity must be positive"); }
    this.capacity = capacity;
  }

  public synchronized CompletableFuture<MessagePacket> register(long callId, int cmd, long timeoutMillis) {
    if (callId <= 0 || timeoutMillis <= 0) {
      return CompletableFuture.failedFuture(new IllegalArgumentException("positive callId and timeout required"));
    }
    Key key = new Key(callId, cmd);
    if (closed) { return CompletableFuture.failedFuture(new RpcException(RpcException.Code.CLOSED, "RPC closed")); }
    if (pending.containsKey(key) || pending.size() >= capacity || !GLOBAL.tryAcquire()) {
      return CompletableFuture.failedFuture(new RpcException(RpcException.Code.BUSY, "RPC in-flight limit or duplicate callId"));
    }
    CompletableFuture<MessagePacket> future = new CompletableFuture<>();
    pending.put(key, future);
    ScheduledFuture<?> timeout = TIMER.schedule(() -> {
      release(key, future);
      future.completeExceptionally(new RpcException(RpcException.Code.TIMEOUT, "RPC wait timed out; remote outcome unknown"));
    },
        timeoutMillis, TimeUnit.MILLISECONDS);
    future.whenComplete((result, error) -> {
      timeout.cancel(false);
      release(key, future);
    });
    return future;
  }

  public boolean receive(long callId, MessagePacket packet) {
    CompletableFuture<MessagePacket> future;
    synchronized (this) { future = pending.get(new Key(callId, packet.getCmd())); }
    if (future == null) { return false; }
    release(new Key(callId, packet.getCmd()), future);
    return future.complete(packet);
  }

  private synchronized void release(Key key, CompletableFuture<MessagePacket> future) {
    if (pending.remove(key, future)) { GLOBAL.release(); }
  }

  /** Connection loss ends waiting immediately. It does not cancel remote execution. */
  public void disconnected() { failAll(new RpcException(RpcException.Code.TRANSPORT, "RPC connection lost; remote outcome unknown")); }
  private void failAll(RpcException error) {
    List<CompletableFuture<MessagePacket>> futures;
    synchronized (this) { futures = List.copyOf(pending.values()); }
    for (CompletableFuture<MessagePacket> future : futures) { future.completeExceptionally(error); }
  }
  public synchronized int size() { return pending.size(); }
  @Override public void close() {
    synchronized (this) { closed = true; }
    failAll(new RpcException(RpcException.Code.CLOSED, "RPC closed; remote outcome unknown"));
  }
}
