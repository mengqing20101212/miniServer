package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class LoginEntryHelper {
  public static LoginEntry getLoginEntryById(Integer id) {
    return MysqlService.getInstance().selectOnce(LoginEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(LoginEntry LoginEntry) {
    return MysqlService.getInstance().save(LoginEntry);
  }

  public static boolean update(LoginEntry LoginEntry, String... fileds) {
    return MysqlService.getInstance().update(LoginEntry, fileds);
  }

  public static void delete(LoginEntry LoginEntry) {
    MysqlService.getInstance().delete(LoginEntry);
  }

  public static void asyncSave(LoginEntry LoginEntry) {
    MysqlService.getInstance().addSaveEntry(LoginEntry);
  }

  public static void asyncUpdate(LoginEntry LoginEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(LoginEntry);
  }

  public static List<LoginEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(LoginEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  public static int count() {
    return 0;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
