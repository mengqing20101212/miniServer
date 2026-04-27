package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmRolePermissionEntryHelper {
  public static GmRolePermissionEntry getGmRolePermissionEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(GmRolePermissionEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmRolePermissionEntry GmRolePermissionEntry) {
    return MysqlService.getInstance().save(GmRolePermissionEntry);
  }

  public static boolean update(GmRolePermissionEntry GmRolePermissionEntry, String... fileds) {
    return MysqlService.getInstance().update(GmRolePermissionEntry, fileds);
  }

  public static boolean delete(GmRolePermissionEntry GmRolePermissionEntry) {
    return MysqlService.getInstance().delete(GmRolePermissionEntry);
  }

  public static void asyncSave(GmRolePermissionEntry GmRolePermissionEntry) {
    MysqlService.getInstance().addSaveEntry(GmRolePermissionEntry);
  }

  public static void asyncUpdate(GmRolePermissionEntry GmRolePermissionEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmRolePermissionEntry, fileds);
  }

  public static List<GmRolePermissionEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmRolePermissionEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
