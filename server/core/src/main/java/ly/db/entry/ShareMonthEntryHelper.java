package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShareMonthEntryHelper {
  public static ShareMonthEntry getShareMonthEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(ShareMonthEntry.class, new String[] {"id"}, id);
  }

  public static void save(ShareMonthEntry ShareMonthEntry) {
    MysqlService.getInstance().save(ShareMonthEntry);
  }

  public static void update(ShareMonthEntry ShareMonthEntry, String... fileds) {
    MysqlService.getInstance().update(ShareMonthEntry, fileds);
  }

  public static void delete(ShareMonthEntry ShareMonthEntry) {
    MysqlService.getInstance().delete(ShareMonthEntry);
  }

  public static void asyncSave(ShareMonthEntry ShareMonthEntry) {
    MysqlService.getInstance().addSaveEntry(ShareMonthEntry);
  }

  public static void asyncUpdate(ShareMonthEntry ShareMonthEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(ShareMonthEntry);
  }

  public static List<ShareMonthEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length != params.length) {
      return MysqlService.getInstance().selectAll(ShareMonthEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
