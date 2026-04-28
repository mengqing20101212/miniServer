package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmAdminEntryHelper {
  public static GmAdminEntry getGmAdminEntryById(Long id) {
    return MysqlService.getInstance()
        .selectOnce(GmAdminEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmAdminEntry GmAdminEntry) {
    return MysqlService.getInstance().save(GmAdminEntry);
  }

  public static boolean update(GmAdminEntry GmAdminEntry, String... fileds) {
    return MysqlService.getInstance().update(GmAdminEntry, fileds);
  }

  public static boolean delete(GmAdminEntry GmAdminEntry) {
    return MysqlService.getInstance().delete(GmAdminEntry);
  }

  public static void asyncSave(GmAdminEntry GmAdminEntry) {
    MysqlService.getInstance().addSaveEntry(GmAdminEntry);
  }

  public static void asyncUpdate(GmAdminEntry GmAdminEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmAdminEntry, fileds);
  }

  public static List<GmAdminEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmAdminEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmAdminEntry.class, null, (Object[]) null);
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
