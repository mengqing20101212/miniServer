package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShareDailyEntryHelper {
  public static ShareDailyEntry getShareDailyEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(ShareDailyEntry.class, new String[] {"id"}, id);
  }

  public static void save(ShareDailyEntry ShareDailyEntry) {
    MysqlService.getInstance().save(ShareDailyEntry);
  }

  public static void update(ShareDailyEntry ShareDailyEntry, String... fileds) {
    MysqlService.getInstance().update(ShareDailyEntry, fileds);
  }

  public static void delete(ShareDailyEntry ShareDailyEntry) {
    MysqlService.getInstance().delete(ShareDailyEntry);
  }

  public static void asyncSave(ShareDailyEntry ShareDailyEntry) {
    MysqlService.getInstance().addSaveEntry(ShareDailyEntry);
  }

  public static void asyncUpdate(ShareDailyEntry ShareDailyEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(ShareDailyEntry);
  }

  public static List<ShareDailyEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length != params.length) {
      return MysqlService.getInstance().selectAll(ShareDailyEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
