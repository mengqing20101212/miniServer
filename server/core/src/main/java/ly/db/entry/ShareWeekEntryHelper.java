package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShareWeekEntryHelper {
  public static ShareWeekEntry getShareWeekEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(ShareWeekEntry.class, new String[] {"id"}, id);
  }

  public static void save(ShareWeekEntry ShareWeekEntry) {
    MysqlService.getInstance().save(ShareWeekEntry);
  }

  public static void update(ShareWeekEntry ShareWeekEntry, String... fileds) {
    MysqlService.getInstance().update(ShareWeekEntry, fileds);
  }

  public static void delete(ShareWeekEntry ShareWeekEntry) {
    MysqlService.getInstance().delete(ShareWeekEntry);
  }

  public static void asyncSave(ShareWeekEntry ShareWeekEntry) {
    MysqlService.getInstance().addSaveEntry(ShareWeekEntry);
  }

  public static void asyncUpdate(ShareWeekEntry ShareWeekEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(ShareWeekEntry);
  }

  public static List<ShareWeekEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length != params.length) {
      return MysqlService.getInstance().selectAll(ShareWeekEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
