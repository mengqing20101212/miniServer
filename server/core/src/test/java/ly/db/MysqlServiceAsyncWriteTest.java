package ly.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson2.JSON;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import ly.ServerContext;
import ly.config.ServerTypeEnum;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class MysqlServiceAsyncWriteTest {
  @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @After
  public void tearDown() {
    ServerContext.ENV = null;
    ServerContext.serverType = null;
    ServerContext.SERVER_ID = null;
  }

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

  @Test
  public void replayDeadLettersBeforeStartupExecutesTasksAndArchivesFile() throws Exception {
    TestMysqlService service = new TestMysqlService(true);
    Path root = temporaryFolder.newFolder("db-dead-letter").toPath();
    Path dayDir = root.resolve("20260509");
    Files.createDirectories(dayDir);

    TestEntry entry = new TestEntry();
    entry.setName("dead-letter");
    MysqlService.DeadLetterRecord record = new MysqlService.DeadLetterRecord();
    record.type = 1;
    record.fields = new String[] {"name"};
    record.serializedEntry = serialize(entry);

    Files.writeString(
        dayDir.resolve("db-write-failed.log"),
        JSON.toJSONString(record) + System.lineSeparator(),
        StandardCharsets.UTF_8);

    int loadedCount = service.replayDeadLettersBeforeStartup(root);

    assertEquals(1, loadedCount);
    assertEquals(1, service.processedCount);
    assertTrue(Files.isDirectory(dayDir.resolve("loaded")));
    try (var files = Files.walk(dayDir.resolve("loaded"))) {
      assertTrue(files.anyMatch(path -> path.getFileName().toString().endsWith(".done")));
    }
  }

  @Test(expected = IllegalStateException.class)
  public void replayDeadLettersBeforeStartupFailsStartupWhenTaskFails() throws Exception {
    TestMysqlService service = new TestMysqlService(false);
    Path root = temporaryFolder.newFolder("db-dead-letter-failed").toPath();
    Path dayDir = root.resolve("20260509");
    Files.createDirectories(dayDir);

    TestEntry entry = new TestEntry();
    entry.setName("dead-letter");
    MysqlService.DeadLetterRecord record = new MysqlService.DeadLetterRecord();
    record.type = 1;
    record.serializedEntry = serialize(entry);

    Files.writeString(
        dayDir.resolve("db-write-failed.log"),
        JSON.toJSONString(record) + System.lineSeparator(),
        StandardCharsets.UTF_8);

    service.replayDeadLettersBeforeStartup(root);
  }

  @Test
  public void currentDeadLetterRootUsesCurrentServerIdentity() {
    ServerContext.ENV = "ly";
    ServerContext.serverType = ServerTypeEnum.GAME;
    ServerContext.SERVER_ID = "game1001";

    Path root = new MysqlService().currentDeadLetterRoot();

    assertTrue(root.endsWith(Path.of("ly", "GAME", "game1001")));
  }

  private static String serialize(AbstractEntry entry) throws Exception {
    try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes)) {
      out.writeObject(entry);
      out.flush();
      return Base64.getEncoder().encodeToString(bytes.toByteArray());
    }
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

  static class TestMysqlService extends MysqlService {
    private final boolean processResult;
    private int processedCount;

    TestMysqlService(boolean processResult) {
      this.processResult = processResult;
    }

    @Override
    boolean processWriteTask(DbWriteTask task) {
      processedCount++;
      return processResult;
    }
  }
}
