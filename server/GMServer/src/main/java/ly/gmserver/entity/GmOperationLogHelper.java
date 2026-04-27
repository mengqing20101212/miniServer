package ly.gmserver.entity;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class GmOperationLogHelper {
    public static GmOperationLogEntry getGmOperationLogEntryById(Long id) {
        return MysqlService.getInstance()
            .selectOnce(GmOperationLogEntry.class, new String[]{"id"}, id);
    }

    public static List<GmOperationLogEntry> getAll() {
        return MysqlService.getInstance()
            .selectAll(GmOperationLogEntry.class, null);
    }

    public static boolean save(GmOperationLogEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public static boolean update(GmOperationLogEntry entry, String... fields) {
        return MysqlService.getInstance().update(entry, fields);
    }

    public static boolean delete(GmOperationLogEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }

    public static void asyncSave(GmOperationLogEntry entry) {
        MysqlService.getInstance().addSaveEntry(entry);
    }

    public static List<GmOperationLogEntry> select(String[] fields, Object... params) {
        if (fields != null && params != null && fields.length == params.length) {
            return MysqlService.getInstance().selectAll(GmOperationLogEntry.class, fields, params);
        }
        return new ArrayList<>();
    }
}
