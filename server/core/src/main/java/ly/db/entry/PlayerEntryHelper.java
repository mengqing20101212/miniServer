package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class PlayerEntryHelper {
  public static PlayerEntry getPlayerEntryById(Long id) {
    return MysqlService.getInstance()
        .selectOnce(PlayerEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(PlayerEntry PlayerEntry) {
    return MysqlService.getInstance().save(PlayerEntry);
  }

  public static boolean update(PlayerEntry PlayerEntry, String... fileds) {
    return MysqlService.getInstance().update(PlayerEntry, fileds);
  }

  public static boolean delete(PlayerEntry PlayerEntry) {
    return MysqlService.getInstance().delete(PlayerEntry);
  }

  public static void asyncSave(PlayerEntry PlayerEntry) {
    MysqlService.getInstance().addSaveEntry(PlayerEntry);
  }

  public static void asyncUpdate(PlayerEntry PlayerEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(PlayerEntry);
  }

  public static List<PlayerEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(PlayerEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
