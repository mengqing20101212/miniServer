package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** 配表热更服务器状态数据库访问。 */
public class GmConfigServerStatusEntryHelper {
  public static boolean save(GmConfigServerStatusEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  public static boolean update(GmConfigServerStatusEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  public static List<GmConfigServerStatusEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(GmConfigServerStatusEntry.class, fields, params);
    }
    return MysqlService.getInstance().selectAll(GmConfigServerStatusEntry.class, null, (Object[]) null);
  }

  public static GmConfigServerStatusEntry get(String publishId, String serverId) {
    return MysqlService.getInstance()
        .selectOnce(GmConfigServerStatusEntry.class, new String[] {"publish_id", "server_id"}, publishId, serverId);
  }

  public static List<GmConfigServerStatusEntry> listByPublishId(String publishId) {
    return select(new String[] {"publish_id"}, publishId);
  }
}
