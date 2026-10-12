package ly.db;

import static org.junit.Assert.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class RocksServiceTest {
  @Rule public TemporaryFolder temporary = new TemporaryFolder();

  @Test public void failedEntitySurvivesRestartAndLaterSnapshotReplacesIt() throws Exception {
    Path path = temporary.newFolder().toPath();
    List<String> stored = new ArrayList<>();
    RocksService.Sink sink = (owner, records) -> {
      for (RocksService.Record record : records) {
        if (record.key.equals("a")) { throw new IllegalStateException("unavailable entity"); }
        stored.add(record.key);
      }
    };
    try (RocksService rocks = new RocksService(path, sink, 30, false)) {
      rocks.accept("a", "player", "a", new Object[] {10});
      rocks.accept("b", "player", "b", new Object[] {20});
      rocks.drainOnce();
      assertEquals(1, rocks.pendingCount());
      assertThrows(IllegalStateException.class, rocks::awaitCommitted);
    }
    List<Integer> replayed = new ArrayList<>();
    try (RocksService rocks = new RocksService(path, (owner, records) -> {
      for (RocksService.Record record : records) { replayed.add((Integer) record.parameters[0]); }
    }, 1000, false)) {
      assertEquals(1, rocks.pendingCount());
      rocks.accept("a", "player", "a", new Object[] {11});
      rocks.drainOnce();
      rocks.awaitCommitted();
      assertEquals(List.of(11), replayed);
      assertEquals(0, rocks.pendingCount());
    }
  }

  @Test public void committingOldSnapshotCannotDeleteNewSnapshot() throws Exception {
    Path path = temporary.newFolder().toPath();
    CountDownLatch entered = new CountDownLatch(1);
    CountDownLatch release = new CountDownLatch(1);
    List<Integer> replayed = new ArrayList<>();
    try (RocksService rocks = new RocksService(path, (owner, records) -> {
      entered.countDown();
      if (!release.await(2, TimeUnit.SECONDS)) { throw new IllegalStateException("test timeout"); }
      replayed.add((Integer) records.getFirst().parameters[0]);
    }, 1000, false)) {
      rocks.accept("a", "player", "save", new Object[] {10});
      Thread committing = Thread.ofVirtual().start(rocks::drainOnce);
      assertTrue(entered.await(2, TimeUnit.SECONDS));
      rocks.accept("a", "player", "save", new Object[] {11});
      release.countDown();
      committing.join(2000);
      assertFalse(committing.isAlive());
      assertEquals(1, rocks.pendingCount());
      rocks.drainOnce();
      rocks.awaitCommitted();
      assertEquals(List.of(10, 11), replayed);
    }
  }

  @Test public void snapshotsDoNotRetainMutableBusinessReferences() throws Exception {
    byte[] data = {1, 2};
    List<byte[]> replayed = new ArrayList<>();
    try (RocksService rocks = new RocksService(temporary.newFolder().toPath(), (owner, records) -> {
      replayed.add((byte[]) records.getFirst().parameters[0]);
    }, 1000, false)) {
      rocks.accept("a", "player", "save", new Object[] {data});
      data[0] = 9;
      rocks.drainOnce();
      assertArrayEquals(new byte[] {1, 2}, replayed.getFirst());
    }
  }
}
