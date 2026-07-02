package ly.db.entry;

import java.util.Comparator;
import java.util.List;
import ly.db.MysqlService;

/** GM 配表热更版本记录数据库访问。 */
public class GmConfigVersionEntryHelper {
  public static boolean save(GmConfigVersionEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  public static boolean update(GmConfigVersionEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  public static List<GmConfigVersionEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmConfigVersionEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmConfigVersionEntry.class, null, (Object[]) null);
  }

  public static GmConfigVersionEntry getByVersion(String version) {
    return MysqlService.getInstance()
        .selectOnce(GmConfigVersionEntry.class, new String[] {"version"}, version);
  }

  public static List<GmConfigVersionEntry> listAll() {
    return select(null).stream()
        .sorted(Comparator.comparing(GmConfigVersionEntry::getUpdateTime, Comparator.nullsLast(Comparator.reverseOrder())))
        .toList();
  }
}
