package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

public final class GmRuntimeScriptEntryHelper {
  private GmRuntimeScriptEntryHelper() {}

  public static boolean save(GmRuntimeScriptEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  public static boolean update(GmRuntimeScriptEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  public static List<GmRuntimeScriptEntry> listAll() {
    return MysqlService.getInstance()
        .selectAll(GmRuntimeScriptEntry.class, null, (Object[]) null);
  }

  public static GmRuntimeScriptEntry getByExecutionId(String executionId) {
    return MysqlService.getInstance()
        .selectOnce(
            GmRuntimeScriptEntry.class,
            new String[] {"execution_id"},
            executionId);
  }
}
