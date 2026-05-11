package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** GM 配表文件数据库访问。 */
public class GmConfigFileEntryHelper {
  public static boolean save(GmConfigFileEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  public static boolean update(GmConfigFileEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  public static List<GmConfigFileEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmConfigFileEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmConfigFileEntry.class, null, (Object[]) null);
  }

  public static GmConfigFileEntry get(String version, String fileName) {
    return MysqlService.getInstance()
        .selectOnce(GmConfigFileEntry.class, new String[] {"version", "file_name"}, version, fileName);
  }

  public static List<GmConfigFileEntry> listByVersion(String version) {
    return select(new String[] {"version"}, version);
  }
}
