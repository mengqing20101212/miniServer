package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class loginEntryHelper {
  public static loginEntry getloginEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(loginEntry.class, new String[] {"id"}, id);
  }

  public static void save(loginEntry loginEntry) {
    MysqlService.getInstance().save(loginEntry);
  }

  public static void update(loginEntry loginEntry, String... fileds) {
    MysqlService.getInstance().update(loginEntry, fileds);
  }

  public static void delete(loginEntry loginEntry) {
    MysqlService.getInstance().delete(loginEntry);
  }

  public static void asyncSave(loginEntry loginEntry) {
    MysqlService.getInstance().addSaveEntry(loginEntry);
  }

  public static void asyncUpdate(loginEntry loginEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(loginEntry);
  }

  public static List<loginEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length != params.length) {
      return MysqlService.getInstance().selectAll(loginEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
