package ly.gmserver.entity;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class GmMenuHelper {
    public static GmMenuEntry getGmMenuEntryById(Integer id) {
        return MysqlService.getInstance()
            .selectOnce(GmMenuEntry.class, new String[]{"id"}, id);
    }

    public static List<GmMenuEntry> getAll() {
        return MysqlService.getInstance()
            .selectAll(GmMenuEntry.class, null);
    }

    public static List<GmMenuEntry> getByParentId(Integer parentId) {
        return MysqlService.getInstance()
            .selectAll(GmMenuEntry.class, new String[]{"parentId"}, parentId);
    }

    public static boolean save(GmMenuEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public static boolean update(GmMenuEntry entry, String... fields) {
        return MysqlService.getInstance().update(entry, fields);
    }

    public static boolean delete(GmMenuEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }

    public static List<GmMenuEntry> select(String[] fields, Object... params) {
        if (fields != null && params != null && fields.length == params.length) {
            return MysqlService.getInstance().selectAll(GmMenuEntry.class, fields, params);
        }
        return new ArrayList<>();
    }
}
