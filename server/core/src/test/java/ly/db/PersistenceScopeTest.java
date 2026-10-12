package ly.db;

import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.db.entry.PlayerEntry;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PersistenceScopeTest {
  @Rule public TemporaryFolder temporary = new TemporaryFolder();

  @Test public void generatedSettersCoalesceAndQueryFlushesCurrentTask() throws Exception {
    MysqlService service = MysqlService.getInstance();
    List<RocksService.Record> saved = java.util.Collections.synchronizedList(new ArrayList<>());
    try (RocksService rocks = new RocksService(temporary.newFolder().toPath(),
        (owner, records) -> saved.addAll(records), 1000, true)) {
      Field binding = MysqlService.class.getDeclaredField("rocksService");
      binding.setAccessible(true);
      Object previous = binding.get(service);
      binding.set(service, rocks);
      try {
        Map<String, Object> row = new HashMap<>();
        row.put("id", 101L); row.put("level", 1); row.put("name", "old");
        PlayerEntry entry = MysqlService.packetEntry(row, PlayerEntry.class);
        assertNotNull(entry);
        try (PersistenceScope scope = PersistenceScope.open()) {
          entry.setLevel(2);
          entry.setName("new");
          entry.setLevel(3);
          assertEquals(0, rocks.pendingCount());
          assertTrue(saved.isEmpty());
          service.beforeQuery();
          assertEquals(1, saved.size());
          assertFalse(((AbstractEntry) entry).canSave());
          assertTrue(java.util.Arrays.asList(saved.getFirst().parameters).contains(3));
          assertTrue(java.util.Arrays.asList(saved.getFirst().parameters).contains("new"));
          entry.setLevel(3);
        }
        assertEquals(1, saved.size());
        // A second task must persist its own snapshot even though the previous one was clean.
        try (PersistenceScope scope = PersistenceScope.open()) { entry.setLevel(4); }
        service.beforeQuery();
        assertEquals(2, saved.size());
      } finally { binding.set(service, previous); }
    }
  }
}
