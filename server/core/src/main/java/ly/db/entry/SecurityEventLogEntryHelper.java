package ly.db.entry;

import java.util.ArrayList;
import java.util.List;

import ly.db.MysqlService;

/*
 * 自动生成风格的Helper代码，业务扩展请放在自定义区。
 */
public class SecurityEventLogEntryHelper {
  public static SecurityEventLogEntry getSecurityEventLogEntryById(Long id) {
    return MysqlService.getInstance().selectOnce(SecurityEventLogEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(SecurityEventLogEntry securityEventLogEntry) {
    return MysqlService.getInstance().save(securityEventLogEntry);
  }

  public static boolean update(SecurityEventLogEntry securityEventLogEntry, String... fileds) {
    return MysqlService.getInstance().update(securityEventLogEntry, fileds);
  }

  public static boolean delete(SecurityEventLogEntry securityEventLogEntry) {
    return MysqlService.getInstance().delete(securityEventLogEntry);
  }

  public static void asyncSave(SecurityEventLogEntry securityEventLogEntry) {
    MysqlService.getInstance().addSaveEntry(securityEventLogEntry);
  }

  public static void asyncUpdate(SecurityEventLogEntry securityEventLogEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(securityEventLogEntry, fileds);
  }

  public static List<SecurityEventLogEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(SecurityEventLogEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  public static List<SecurityEventLogEntry> selectAll() {
    return MysqlService.getInstance().selectAll(SecurityEventLogEntry.class, null);
  }

  // @@@@@自定义方法结束区@@@@@
}
