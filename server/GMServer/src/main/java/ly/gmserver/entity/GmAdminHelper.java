package ly.gmserver.entity;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class GmAdminHelper {
    public static GmAdminEntry getGmAdminEntryById(Long id) {
        return MysqlService.getInstance()
            .selectOnce(GmAdminEntry.class, new String[]{"id"}, id);
    }

    public static GmAdminEntry getByUsername(String username) {
        return MysqlService.getInstance()
            .selectOnce(GmAdminEntry.class, new String[]{"username"}, username);
    }

    public static List<GmAdminEntry> getAll() {
        return MysqlService.getInstance()
            .selectAll(GmAdminEntry.class, null);
    }

    public static boolean save(GmAdminEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public static boolean update(GmAdminEntry entry, String... fields) {
        return MysqlService.getInstance().update(entry, fields);
    }

    public static boolean delete(GmAdminEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }

    public static void asyncSave(GmAdminEntry entry) {
        MysqlService.getInstance().addSaveEntry(entry);
    }

    public static void asyncUpdate(GmAdminEntry entry, String... fields) {
        MysqlService.getInstance().addUpdateEntry(entry, fields);
    }

    public static List<GmAdminEntry> select(String[] fields, Object... params) {
        if (fields != null && params != null && fields.length == params.length) {
            return MysqlService.getInstance().selectAll(GmAdminEntry.class, fields, params);
        }
        return new ArrayList<>();
    }
}
