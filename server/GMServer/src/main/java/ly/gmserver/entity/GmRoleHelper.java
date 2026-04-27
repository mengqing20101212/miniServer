package ly.gmserver.entity;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class GmRoleHelper {
    public static GmRoleEntry getGmRoleEntryById(Integer id) {
        return MysqlService.getInstance()
            .selectOnce(GmRoleEntry.class, new String[]{"id"}, id);
    }

    public static List<GmRoleEntry> getAll() {
        return MysqlService.getInstance()
            .selectAll(GmRoleEntry.class, null);
    }

    public static boolean save(GmRoleEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public static boolean update(GmRoleEntry entry, String... fields) {
        return MysqlService.getInstance().update(entry, fields);
    }

    public static boolean delete(GmRoleEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }

    public static List<GmRoleEntry> select(String[] fields, Object... params) {
        if (fields != null && params != null && fields.length == params.length) {
            return MysqlService.getInstance().selectAll(GmRoleEntry.class, fields, params);
        }
        return new ArrayList<>();
    }
}
