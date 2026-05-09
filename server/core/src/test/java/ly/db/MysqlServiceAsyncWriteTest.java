package ly.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MysqlServiceAsyncWriteTest {

  @Test
  public void enqueueAsyncTaskAddsDirtyEntryToMainQueue() {
    MysqlService service = new MysqlService();
    TestEntry entry = new TestEntry();
    entry.setName("dirty");

    service.addSaveEntry(entry);

    assertEquals(1, service.asyncQueueSize());
  }

  @Test
  public void scheduleRetryUsesBackoffAndRetryQueue() {
    MysqlService service = new MysqlService();
    TestEntry entry = new TestEntry();
    entry.setName("retry");
    MysqlService.DbWriteTask task = new MysqlService.DbWriteTask(1, entry);

    service.scheduleRetry(task, null);

    assertEquals(1, task.retryCount);
    assertEquals(1, service.retryQueueSize());
    assertTrue(task.nextRetryAt > System.currentTimeMillis());
    assertEquals(5_000L, service.calculateRetryDelayMillis(1));
    assertEquals(10_000L, service.calculateRetryDelayMillis(2));
    assertEquals(300_000L, service.calculateRetryDelayMillis(20));
  }

  static class TestEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {"name"};
    private String name;

    TestEntry() {
      initDirtyState(DIRTY_FIELDS.length);
    }

    void setName(String name) {
      this.name = name;
      autoAddCurVersion();
      markFieldDirty(0);
    }

    String getName() {
      return name;
    }

    @Override
    protected String[] allDirtyFieldNames() {
      return DIRTY_FIELDS;
    }
  }
}
