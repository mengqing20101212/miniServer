package ly.db;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/** SlgServer-compatible 41 time + 14 node + 8 sequence layout. */
public final class GlobalIdGenerator implements AutoCloseable {
  static final long EPOCH = 1767225600000L;
  private static final long RESERVATION_MILLIS = 1000;
  private final int nodeId;
  private final FileChannel channel;
  private final FileLock lock;
  private long reservedUntil;
  private long lastTimestamp = -1;
  private int sequence;

  public GlobalIdGenerator(int nodeId, Path stateFile) throws Exception {
    if (nodeId < 0 || nodeId > 16383) {
      throw new IllegalArgumentException("idNodeId must be 0..16383");
    }
    this.nodeId = nodeId;
    Files.createDirectories(stateFile.toAbsolutePath().getParent());
    channel = FileChannel.open(stateFile, StandardOpenOption.CREATE,
        StandardOpenOption.READ, StandardOpenOption.WRITE);
    FileLock acquired = null;
    try {
      acquired = channel.tryLock();
      if (acquired == null) { throw new IllegalStateException("ID node state already in use"); }
      ByteBuffer bytes = ByteBuffer.allocate(8);
      int count = 0;
      while (bytes.hasRemaining()) {
        int read = channel.read(bytes, count);
        if (read < 0) { break; }
        count += read;
      }
      if (count != 0 && count != 8) { throw new IllegalStateException("Corrupt ID reservation"); }
      if (count == 8) {
        bytes.flip();
        reservedUntil = bytes.getLong();
      }
      // Refuse startup during an old reservation; do not silently reuse its timestamps.
      if (System.currentTimeMillis() <= reservedUntil) {
        throw new IllegalStateException("ID clock has not passed durable reservation: " + reservedUntil);
      }
      lock = acquired;
    } catch (Exception failure) {
      if (acquired != null) { acquired.release(); }
      channel.close();
      throw failure;
    }
  }

  public synchronized long nextId() {
    try {
      long now = System.currentTimeMillis();
      if (now < EPOCH || now - EPOCH >= (1L << 41) || now < lastTimestamp) {
        throw new IllegalStateException("ID clock outside range or moved backwards");
      }
      if (now == lastTimestamp) {
        sequence++;
        if (sequence > 255) {
          do {
            Thread.sleep(1);
            now = System.currentTimeMillis();
            if (now < lastTimestamp) { throw new IllegalStateException("ID clock moved backwards"); }
          } while (now == lastTimestamp);
          sequence = 0;
        }
      } else { sequence = 0; }
      if (now > reservedUntil) {
        reservedUntil = now + RESERVATION_MILLIS;
        ByteBuffer bytes = ByteBuffer.allocate(8).putLong(reservedUntil);
        bytes.flip();
        long offset = 0;
        while (bytes.hasRemaining()) { offset += channel.write(bytes, offset); }
        channel.force(true);
      }
      lastTimestamp = now;
      return ((now - EPOCH) << 22) | ((long) nodeId << 8) | sequence;
    } catch (InterruptedException failure) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Interrupted generating ID", failure);
    } catch (java.io.IOException failure) {
      throw new IllegalStateException("Cannot persist ID reservation", failure);
    }
  }

  @Override public synchronized void close() throws Exception {
    lock.release();
    channel.close();
  }
}
