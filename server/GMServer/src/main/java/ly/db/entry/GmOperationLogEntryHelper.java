package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmOperationLogEntryHelper {
  public static GmOperationLogEntry getGmOperationLogEntryById(Long id) {
    return MysqlService.getInstance()
        .selectOnce(GmOperationLogEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmOperationLogEntry GmOperationLogEntry) {
    return MysqlService.getInstance().save(GmOperationLogEntry);
  }

  public static boolean update(GmOperationLogEntry GmOperationLogEntry, String... fileds) {
    return MysqlService.getInstance().update(GmOperationLogEntry, fileds);
  }

  public static boolean delete(GmOperationLogEntry GmOperationLogEntry) {
    return MysqlService.getInstance().delete(GmOperationLogEntry);
  }

  public static void asyncSave(GmOperationLogEntry GmOperationLogEntry) {
    MysqlService.getInstance().addSaveEntry(GmOperationLogEntry);
  }

  public static void asyncUpdate(GmOperationLogEntry GmOperationLogEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmOperationLogEntry, fileds);
  }

  public static List<GmOperationLogEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmOperationLogEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmOperationLogEntry.class, null, (Object[]) null);
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
