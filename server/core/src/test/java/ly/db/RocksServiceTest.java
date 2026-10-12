package ly.db;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RocksServiceTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void atomicBatchSurvivesRestartAndIsNeverDowngradedToSeparateWrites() throws Exception {
        Path directory = temporary.newFolder().toPath();
        java.util.List<Integer> sizes = new java.util.ArrayList<>();
        try (RocksService rocks =
                new RocksService(
                        directory,
                        (owner, records) -> {
                            sizes.add(records.size());
                            throw new IllegalStateException("database unavailable");
                        },
                        1000,
                        false)) {
            RocksService.Record hero = new RocksService.Record();
            hero.key = "hero";
            hero.table = "hero";
            hero.sql = "save";
            hero.parameters = new Object[] {1};
            RocksService.Record resource = new RocksService.Record();
            resource.key = "resource";
            resource.table = "resource";
            resource.sql = "save";
            resource.parameters = new Object[] {10};
            rocks.acceptBatch(java.util.List.of(hero, resource));
            rocks.drainOnce();
            assertEquals(java.util.List.of(2), sizes);
            assertEquals(2, rocks.pendingCount());
        }
        java.util.List<String> replayed = new java.util.ArrayList<>();
        try (RocksService rocks =
                new RocksService(
                        directory,
                        (owner, records) -> {
                            assertEquals(2, records.size());
                            records.forEach(record -> replayed.add(record.key));
                        },
                        1000,
                        false)) {
            rocks.accept("hero", "hero", "save", new Object[] {2});
            rocks.drainOnce();
            rocks.awaitCommitted();
            assertTrue(replayed.containsAll(java.util.List.of("hero", "resource")));
        }
    }

    @Test
    public void failedEntitySurvivesRestartAndLaterSnapshotReplacesIt() throws Exception {
        Path path = temporary.newFolder().toPath();
        List<String> stored = new ArrayList<>();
        RocksService.Sink sink =
                (owner, records) -> {
                    for (RocksService.Record record : records) {
                        if (record.key.equals("a")) {
                            throw new IllegalStateException("unavailable entity");
                        }
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
        try (RocksService rocks =
                new RocksService(
                        path,
                        (owner, records) -> {
                            for (RocksService.Record record : records) {
                                replayed.add((Integer) record.parameters[0]);
                            }
                        },
                        1000,
                        false)) {
            assertEquals(1, rocks.pendingCount());
            rocks.accept("a", "player", "a", new Object[] {11});
            rocks.drainOnce();
            rocks.awaitCommitted();
            assertEquals(List.of(11), replayed);
            assertEquals(0, rocks.pendingCount());
        }
    }

    @Test
    public void committingOldSnapshotCannotDeleteNewSnapshot() throws Exception {
        Path path = temporary.newFolder().toPath();
        CountDownLatch entered = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        List<Integer> replayed = new ArrayList<>();
        try (RocksService rocks =
                new RocksService(
                        path,
                        (owner, records) -> {
                            entered.countDown();
                            if (!release.await(2, TimeUnit.SECONDS)) {
                                throw new IllegalStateException("test timeout");
                            }
                            replayed.add((Integer) records.getFirst().parameters[0]);
                        },
                        1000,
                        false)) {
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

    @Test
    public void snapshotsDoNotRetainMutableBusinessReferences() throws Exception {
        byte[] data = {1, 2};
        List<byte[]> replayed = new ArrayList<>();
        try (RocksService rocks =
                new RocksService(
                        temporary.newFolder().toPath(),
                        (owner, records) -> {
                            replayed.add((byte[]) records.getFirst().parameters[0]);
                        },
                        1000,
                        false)) {
            rocks.accept("a", "player", "save", new Object[] {data});
            data[0] = 9;
            rocks.drainOnce();
            assertArrayEquals(new byte[] {1, 2}, replayed.getFirst());
        }
    }
}
