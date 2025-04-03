package ly.db.entry;

import ly.db.DbMeta;
import ly.db.MysqlService;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: ShareEnumConfigHelper
 */
public class ShareEnumConfigHelper {
  public static ShareEnumConfigEntry getShareEnumConfigEntryById(int id) {
    return MysqlService.getInstance()
        .selectOnce(ShareEnumConfigEntry.class, new String[] {"id"}, id);
  }
}
