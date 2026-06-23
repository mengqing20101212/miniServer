package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class RankHistoryEntryHelper {
  public static RankHistoryEntry getRankHistoryEntryById(Long id) {
    return MysqlService.getInstance().selectOnce(RankHistoryEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(RankHistoryEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  public static boolean update(RankHistoryEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  public static boolean delete(RankHistoryEntry entry) {
    return MysqlService.getInstance().delete(entry);
  }

  public static void asyncSave(RankHistoryEntry entry) {
    MysqlService.getInstance().addSaveEntry(entry);
  }

  public static List<RankHistoryEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(RankHistoryEntry.class, fields, params);
    }
    return new ArrayList<>();
  }
}
