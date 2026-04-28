package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GmMenuEntryHelper {
  public static GmMenuEntry getGmMenuEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(GmMenuEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(GmMenuEntry GmMenuEntry) {
    return MysqlService.getInstance().save(GmMenuEntry);
  }

  public static boolean update(GmMenuEntry GmMenuEntry, String... fileds) {
    return MysqlService.getInstance().update(GmMenuEntry, fileds);
  }

  public static boolean delete(GmMenuEntry GmMenuEntry) {
    return MysqlService.getInstance().delete(GmMenuEntry);
  }

  public static void asyncSave(GmMenuEntry GmMenuEntry) {
    MysqlService.getInstance().addSaveEntry(GmMenuEntry);
  }

  public static void asyncUpdate(GmMenuEntry GmMenuEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(GmMenuEntry, fileds);
  }

  public static List<GmMenuEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmMenuEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmMenuEntry.class, null, (Object[]) null);
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
