package ly.db;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import ly.LoggerDef;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;

/** Durable, per-entity outbox. Single writer per entity across service processes is required. */
public final class RocksService implements AutoCloseable {
  static { RocksDB.loadLibrary(); }
  private static final byte[] SEQUENCE_KEY = bytes("!sequence");
  private static final byte[] OWNER_KEY = bytes("!owner");
  private final Options options;
  private final WriteOptions syncWrites;
  private final RocksDB db;
  private final Sink sink;
  private final Map<String, Record> pending = new HashMap<>();
  private final List<Map<String, Long>> barriers = new ArrayList<>();
  private final String owner;
  private long sequence;
  private boolean accepting = true;
  private volatile boolean running = true;
  private Thread worker;
  private final long queryTimeoutMillis;

  /** Testable transaction boundary; successful return means the MySQL transaction committed. */
  interface Sink { void commit(String owner, List<Record> records) throws Exception; }

  public static final class Record {
    public int formatVersion = 1;
    public String key;
    public String table;
    public long version;
    public String sql;
    public Object[] parameters;
    public Record() {}
  }

  public RocksService(Path directory, MysqlConnector connector, long queryTimeoutMillis) throws Exception {
    this(directory, new JdbcSink(connector), queryTimeoutMillis, true);
  }

  RocksService(Path directory, Sink sink, long queryTimeoutMillis, boolean startWorker) throws Exception {
    if (queryTimeoutMillis <= 0) { throw new IllegalArgumentException("query timeout must be positive"); }
    this.sink = sink;
    this.queryTimeoutMillis = queryTimeoutMillis;
    Files.createDirectories(directory);
    options = new Options().setCreateIfMissing(true);
    syncWrites = new WriteOptions().setSync(true).setDisableWAL(false);
    RocksDB opened = null;
    try {
      opened = RocksDB.open(options, directory.toAbsolutePath().toString());
      db = opened;
      byte[] ownerBytes = db.get(OWNER_KEY);
      if (ownerBytes == null) {
        ownerBytes = bytes(UUID.randomUUID().toString());
        db.put(syncWrites, OWNER_KEY, ownerBytes);
      }
      owner = new String(ownerBytes, StandardCharsets.UTF_8);
      byte[] sequenceBytes = db.get(SEQUENCE_KEY);
      if (sequenceBytes != null) { sequence = Long.parseLong(new String(sequenceBytes, StandardCharsets.UTF_8)); }
      try (RocksIterator iterator = db.newIterator()) {
        for (iterator.seek(bytes("entity:")); iterator.isValid(); iterator.next()) {
          String key = new String(iterator.key(), StandardCharsets.UTF_8);
          if (!key.startsWith("entity:")) { break; }
          Record record = (Record) FurySerializer.deserialize(iterator.value());
          if (record.formatVersion != 1 || !key.equals("entity:" + record.key) || record.version > sequence) {
            throw new IllegalStateException("Invalid persisted record: " + key);
          }
          pending.put(record.key, record);

        }
        iterator.status();
      }
      if (startWorker) {
        worker = Thread.ofVirtual().name("rocks-mysql-writer").start(this::run);
      }
    } catch (Exception failure) {
      if (opened != null) { opened.close(); }
      syncWrites.close();
      options.close();
      throw failure;
    }
  }

  public synchronized void accept(String key, String table, String sql, Object[] parameters) {
    if (!accepting) { throw new IllegalStateException("RocksService is closing"); }
    Record record = new Record();
    record.key = key;
    record.table = table;
    record.version = sequence + 1;
    record.sql = sql;
    record.parameters = parameters;
    try {
      byte[] serialized = FurySerializer.serialize(record);
      // Decode now to sever every reference to mutable business data.
      Record frozen = (Record) FurySerializer.deserialize(serialized);
      try (WriteBatch batch = new WriteBatch()) {
        batch.put(bytes("entity:" + key), serialized);
        batch.put(SEQUENCE_KEY, bytes(Long.toString(record.version)));
        db.write(syncWrites, batch);
      }
      sequence = record.version;
      pending.put(key, frozen);

      notifyAll();
    } catch (Exception failure) {
      throw new IllegalStateException("RocksDB did not accept entity " + key, failure);
    }
  }

  /** A database-wide barrier also covers arbitrary SQL and JOINs without SQL parsing. */
  public synchronized void awaitCommitted() {
    Map<String, Long> targets = new HashMap<>();
    for (Record record : pending.values()) { targets.put(record.key, record.version); }
    barriers.add(targets);
    try {
      long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(queryTimeoutMillis);
      while (!targets.isEmpty()) {
        if (!running) { throw new IllegalStateException("RocksService stopped while awaiting writes"); }
        long remaining = deadline - System.nanoTime();
        if (remaining <= 0) {
          throw new IllegalStateException("MySQL commit barrier timed out; pending=" + targets.size());
        }
        try { TimeUnit.NANOSECONDS.timedWait(this, remaining); }
        catch (InterruptedException failure) {
          Thread.currentThread().interrupt();
          throw new IllegalStateException("Interrupted awaiting MySQL commit", failure);
        }
      }
    } finally { barriers.remove(targets); }
  }

  private void run() {
    long delay = 100;
    while (running) {
      try {
        boolean success = drainOnce();
        if (success) { delay = 100; } else { delay = Math.min(30_000, delay * 2); }
        synchronized (this) { if (running) { wait(delay); } }
      } catch (InterruptedException failure) { Thread.currentThread().interrupt(); return; }
      catch (Exception failure) { LoggerDef.DbLogger.error("RocksService worker failure; outbox retained", failure); }
    }
  }

  boolean drainOnce() {
    List<Record> records;
    synchronized (this) { records = new ArrayList<>(pending.values()); }
    records.sort(java.util.Comparator.comparingLong(record -> record.version));
    boolean success = true;
    for (int from = 0; from < records.size(); from += 100) {
      List<Record> batch = records.subList(from, Math.min(records.size(), from + 100));
      try { commitAndClean(batch); }
      catch (Exception failure) {
        success = false;
        LoggerDef.DbLogger.error("MySQL batch failed; retry entities separately, outbox retained", failure);
        for (Record record : batch) {
          try { commitAndClean(List.of(record)); }
          catch (Exception singleFailure) {
            LoggerDef.DbLogger.error("Entity still pending: key={}, version={}", record.key, record.version, singleFailure);
          }
        }
      }
    }
    return success;
  }

  private void commitAndClean(List<Record> records) throws Exception {
    sink.commit(owner, records);
    synchronized (this) {
      try (WriteBatch cleanup = new WriteBatch()) {
        for (Record record : records) {
          Record latest = pending.get(record.key);
          if (latest != null && latest.version == record.version) {
            cleanup.delete(bytes("entity:" + record.key));
          }
        }
        db.write(syncWrites, cleanup);
      }
      for (Record record : records) {
        for (Map<String, Long> barrier : barriers) {
          Long target = barrier.get(record.key);
          if (target != null && target <= record.version) { barrier.remove(record.key); }
        }
        Record latest = pending.get(record.key);
        if (latest != null && latest.version == record.version) { pending.remove(record.key); }

      }
      notifyAll();
    }
  }

  synchronized int pendingCount() { return pending.size(); }
  @Override public void close() throws Exception {
    synchronized (this) { accepting = false; running = false; notifyAll(); }
    if (worker != null) {
      worker.interrupt();
      worker.join(15_000);
      if (worker.isAlive()) { throw new IllegalStateException("MySQL writer still active; RocksDB left open"); }
    }
    // Uncommitted data remains durable; shutdown never discards or waits indefinitely for MySQL.
    db.close(); syncWrites.close(); options.close();
  }
  private static byte[] bytes(String value) { return value.getBytes(StandardCharsets.UTF_8); }
  public static String entityKey(String table, Object key) {
    try {
      String type = key instanceof Number ? "number" : "string";
      String value = key.toString();
      byte[] identity = bytes(table.length() + ":" + table + ":" + type + ":" + value);
      return java.util.HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(identity));
    } catch (Exception failure) { throw new IllegalStateException(failure); }
  }

  static final class JdbcSink implements Sink {
    private final MysqlConnector connector;
    JdbcSink(MysqlConnector connector) throws Exception {
      this.connector = connector;
      try (Connection connection = connector.openConnection(); PreparedStatement statement = connection.prepareStatement(
          "CREATE TABLE IF NOT EXISTS rocks_entity_commit (owner_id VARCHAR(36) NOT NULL, entity_key VARCHAR(64) NOT NULL, version BIGINT NOT NULL, PRIMARY KEY(owner_id,entity_key)) ENGINE=InnoDB")) {
        statement.executeUpdate();
      }
    }
    @Override public void commit(String owner, List<Record> records) throws Exception {
      try (Connection connection = connector.openConnection()) {
        connection.setAutoCommit(false);
        try {
          for (Record record : records) {
            try (PreparedStatement init = connection.prepareStatement(
                "INSERT IGNORE INTO rocks_entity_commit(owner_id,entity_key,version) VALUES(?,?,0)")) {
              init.setString(1, owner); init.setString(2, record.key); init.executeUpdate();
            }
            long saved;
            try (PreparedStatement select = connection.prepareStatement(
                "SELECT version FROM rocks_entity_commit WHERE owner_id=? AND entity_key=? FOR UPDATE")) {
              select.setString(1, owner); select.setString(2, record.key);
              try (ResultSet rows = select.executeQuery()) { rows.next(); saved = rows.getLong(1); }
            }
            if (saved >= record.version) { continue; }
            try (PreparedStatement write = connection.prepareStatement(record.sql)) {
              MysqlConnector.addSqlParams(record.parameters, write); write.executeUpdate();
            }
            try (PreparedStatement mark = connection.prepareStatement(
                "UPDATE rocks_entity_commit SET version=? WHERE owner_id=? AND entity_key=?")) {
              mark.setLong(1, record.version); mark.setString(2, owner); mark.setString(3, record.key); mark.executeUpdate();
            }
          }
          connection.commit();
        } catch (Exception failure) { connection.rollback(); throw failure; }
      }
    }
  }
}
