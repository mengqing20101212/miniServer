package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmRoleMenuEntryHelper {
  public static GmRoleMenuEntry getGmRoleMenuEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(GmRoleMenuEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmRoleMenuEntry GmRoleMenuEntry) {
    return MysqlService.getInstance().save(GmRoleMenuEntry);
  }

  public static boolean update(GmRoleMenuEntry GmRoleMenuEntry, String... fileds) {
    return MysqlService.getInstance().update(GmRoleMenuEntry, fileds);
  }

  public static boolean delete(GmRoleMenuEntry GmRoleMenuEntry) {
    return MysqlService.getInstance().delete(GmRoleMenuEntry);
  }

  public static void asyncSave(GmRoleMenuEntry GmRoleMenuEntry) {
    MysqlService.getInstance().addSaveEntry(GmRoleMenuEntry);
  }

  public static void asyncUpdate(GmRoleMenuEntry GmRoleMenuEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmRoleMenuEntry, fileds);
  }

  public static List<GmRoleMenuEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmRoleMenuEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmRoleMenuEntry.class, null, (Object[]) null);
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
