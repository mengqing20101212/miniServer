package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmRoleEntryHelper {
  public static GmRoleEntry getGmRoleEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(GmRoleEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmRoleEntry GmRoleEntry) {
    return MysqlService.getInstance().save(GmRoleEntry);
  }

  public static boolean update(GmRoleEntry GmRoleEntry, String... fileds) {
    return MysqlService.getInstance().update(GmRoleEntry, fileds);
  }

  public static boolean delete(GmRoleEntry GmRoleEntry) {
    return MysqlService.getInstance().delete(GmRoleEntry);
  }

  public static void asyncSave(GmRoleEntry GmRoleEntry) {
    MysqlService.getInstance().addSaveEntry(GmRoleEntry);
  }

  public static void asyncUpdate(GmRoleEntry GmRoleEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmRoleEntry, fileds);
  }

  public static List<GmRoleEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmRoleEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
