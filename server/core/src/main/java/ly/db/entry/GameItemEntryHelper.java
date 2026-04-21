package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class GameItemEntryHelper {
  public static GameItemEntry getGameItemEntryById(Long id) {
    return MysqlService.getInstance()
        .selectOnce(GameItemEntry.class, new String[]{"id"}, id);
  }

  public static boolean save(GameItemEntry gameItemEntry) {
    return MysqlService.getInstance().save(gameItemEntry);
  }

  public static boolean update(GameItemEntry gameItemEntry, String... fileds) {
    return MysqlService.getInstance().update(gameItemEntry, fileds);
  }

  public static boolean delete(GameItemEntry gameItemEntry) {
    return MysqlService.getInstance().delete(gameItemEntry);
  }

  public static void asyncSave(GameItemEntry gameItemEntry) {
    MysqlService.getInstance().addSaveEntry(gameItemEntry);
  }

  public static void asyncUpdate(GameItemEntry gameItemEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(gameItemEntry);
  }

  public static List<GameItemEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GameItemEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}