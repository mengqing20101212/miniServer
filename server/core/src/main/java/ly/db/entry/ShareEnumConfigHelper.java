package ly.db.entry;

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

  public static void save(ShareEnumConfigEntry shareEnumConfigEntry) {
    MysqlService.getInstance().save(shareEnumConfigEntry);
  }

  public static void update(ShareEnumConfigEntry shareEnumConfigEntry, String... fileds) {
    MysqlService.getInstance().update(shareEnumConfigEntry, fileds);
  }

  public static void delete(ShareEnumConfigEntry shareEnumConfigEntry) {
    MysqlService.getInstance().delete(shareEnumConfigEntry);
  }

  public static void asyncSave(ShareEnumConfigEntry shareEnumConfigEntry) {
    MysqlService.getInstance().addSaveEntry(shareEnumConfigEntry);
  }

  public static void asyncUpdate(ShareEnumConfigEntry shareEnumConfigEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(shareEnumConfigEntry);
  }
}
