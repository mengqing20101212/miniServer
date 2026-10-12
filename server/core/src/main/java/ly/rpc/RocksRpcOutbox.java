package ly.rpc;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.db.FurySerializer;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteOptions;

/** Independent command records; never coalesce different RPCs as entity snapshots do. */
final class RocksRpcOutbox implements AutoCloseable {
  static { RocksDB.loadLibrary(); }
  private final Options options = new Options().setCreateIfMissing(true);
  private final WriteOptions writes = new WriteOptions().setSync(true).setDisableWAL(false);
  private final RocksDB db;
  private final Map<String, ReliableRpcMessage> pending = new HashMap<>();
  private boolean closed;
  RocksRpcOutbox(Path directory) throws Exception {
    RocksDB opened = null;
    try {
      Files.createDirectories(directory);
      opened = RocksDB.open(options, directory.toAbsolutePath().toString());
      db = opened;
      try (RocksIterator iterator = db.newIterator()) {
        for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
          ReliableRpcMessage message = (ReliableRpcMessage) FurySerializer.deserialize(iterator.value());
          if (!message.getMsgId().equals(new String(iterator.key(), StandardCharsets.UTF_8))) {
            throw new IllegalStateException("Invalid RPC outbox record");
          }
          pending.put(message.getMsgId(), message);
        }
        iterator.status();
      }
    } catch (Exception failure) {
      if (opened != null) { opened.close(); }
      writes.close(); options.close(); throw failure;
    }
  }
  synchronized void put(ReliableRpcMessage message) throws Exception {
    requireOpen();
    byte[] encoded = FurySerializer.serialize(message);
    ReliableRpcMessage copy = (ReliableRpcMessage) FurySerializer.deserialize(encoded);
    db.put(writes, key(message.getMsgId()), encoded);
    pending.put(copy.getMsgId(), copy);
  }
  synchronized void remove(String id) throws Exception {
    requireOpen(); db.delete(writes, key(id)); pending.remove(id);
  }
  synchronized List<ReliableRpcMessage> snapshot(String target) {
    requireOpen();
    List<ReliableRpcMessage> messages = new ArrayList<>();
    for (ReliableRpcMessage message : pending.values()) {
      if (target.equals(message.getTargetServerId())) {
        messages.add((ReliableRpcMessage) FurySerializer.deserialize(FurySerializer.serialize(message)));
      }
    }
    messages.sort(Comparator.comparingLong(ReliableRpcMessage::getCreatedAt).thenComparing(ReliableRpcMessage::getMsgId));
    return messages;
  }
  synchronized java.util.Set<String> targets() {
    requireOpen();
    java.util.Set<String> targets = new java.util.HashSet<>();
    for (ReliableRpcMessage message : pending.values()) { targets.add(message.getTargetServerId()); }
    return targets;
  }
  private static byte[] key(String id) { return id.getBytes(StandardCharsets.UTF_8); }
  private void requireOpen() { if (closed) { throw new IllegalStateException("RPC outbox closed"); } }
  @Override public synchronized void close() {
    if (closed) { return; }
    closed = true; db.close(); writes.close(); options.close();
  }
}
