package ly.db;

import static org.junit.Assert.*;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GlobalIdGeneratorTest {
  @Rule public TemporaryFolder temporary = new TemporaryFolder();
  @Test public void generatesUniqueIdsAndProtectsRestartReservation() throws Exception {
    Path state = temporary.newFolder().toPath().resolve("ids");
    Set<Long> ids = ConcurrentHashMap.newKeySet();
    try (GlobalIdGenerator generator = new GlobalIdGenerator(123, state)) {
      Thread[] writers = new Thread[4];
      for (int i = 0; i < writers.length; i++) {
        writers[i] = Thread.ofVirtual().start(() -> {
          for (int j = 0; j < 500; j++) { ids.add(generator.nextId()); }
        });
      }
      for (Thread writer : writers) { writer.join(); }
      assertEquals(2000, ids.size());
      for (long id : ids) { assertTrue(id > 0); assertEquals(123L, (id >>> 8) & 16383L); }
      assertThrows(Exception.class, () -> new GlobalIdGenerator(123, state));
    }
    // Deterministic persisted future reservation, independent of test machine speed.
    java.nio.file.Files.write(state, java.nio.ByteBuffer.allocate(8)
        .putLong(System.currentTimeMillis() + 60_000).array());
    assertThrows(IllegalStateException.class, () -> new GlobalIdGenerator(123, state));
  }
}
