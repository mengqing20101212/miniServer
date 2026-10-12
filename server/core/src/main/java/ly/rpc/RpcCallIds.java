package ly.rpc;

import java.util.UUID;

/** Stable across retries, independent of connection seq/sid and JVM uptime. */
public final class RpcCallIds {
  private RpcCallIds() {}
  public static long next() {
    long id;
    do { id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE; } while (id == 0);
    return id;
  }
}
