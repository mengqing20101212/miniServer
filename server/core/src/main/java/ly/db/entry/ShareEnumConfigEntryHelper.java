package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class ShareEnumConfigEntryHelper {
  public static ShareEnumConfigEntry getShareEnumConfigEntryById(Integer id) {
    return MysqlService.getInstance()
        .selectOnce(ShareEnumConfigEntry.class, new String[] {"id"}, id);
  }

  public static void save(ShareEnumConfigEntry ShareEnumConfigEntry) {
    MysqlService.getInstance().save(ShareEnumConfigEntry);
  }

  public static void update(ShareEnumConfigEntry ShareEnumConfigEntry, String... fileds) {
    MysqlService.getInstance().update(ShareEnumConfigEntry, fileds);
  }

  public static void delete(ShareEnumConfigEntry ShareEnumConfigEntry) {
    MysqlService.getInstance().delete(ShareEnumConfigEntry);
  }

  public static void asyncSave(ShareEnumConfigEntry ShareEnumConfigEntry) {
    MysqlService.getInstance().addSaveEntry(ShareEnumConfigEntry);
  }

  public static void asyncUpdate(ShareEnumConfigEntry ShareEnumConfigEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(ShareEnumConfigEntry);
  }

  public static List<ShareEnumConfigEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length != params.length) {
      return MysqlService.getInstance().selectAll(ShareEnumConfigEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
