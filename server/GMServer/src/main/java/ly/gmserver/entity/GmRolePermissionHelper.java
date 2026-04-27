package ly.gmserver.entity;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

public class GmRolePermissionHelper {
    public static GmRolePermissionEntry getGmRolePermissionEntryById(Integer id) {
        return MysqlService.getInstance()
            .selectOnce(GmRolePermissionEntry.class, new String[]{"id"}, id);
    }

    public static List<GmRolePermissionEntry> getByRoleId(Integer roleId) {
        return MysqlService.getInstance()
            .selectAll(GmRolePermissionEntry.class, new String[]{"roleId"}, roleId);
    }

    public static List<GmRolePermissionEntry> getAll() {
        return MysqlService.getInstance()
            .selectAll(GmRolePermissionEntry.class, null);
    }

    public static boolean save(GmRolePermissionEntry entry) {
        return MysqlService.getInstance().save(entry);
    }

    public static boolean update(GmRolePermissionEntry entry, String... fields) {
        return MysqlService.getInstance().update(entry, fields);
    }

    public static boolean delete(GmRolePermissionEntry entry) {
        return MysqlService.getInstance().delete(entry);
    }

    public static boolean deleteByRoleId(Integer roleId) {
        List<GmRolePermissionEntry> list = getByRoleId(roleId);
        boolean success = true;
        for (GmRolePermissionEntry entry : list) {
            if (!delete(entry)) {
                success = false;
            }
        }
        return success;
    }

    public static List<GmRolePermissionEntry> select(String[] fields, Object... params) {
        if (fields != null && params != null && fields.length == params.length) {
            return MysqlService.getInstance().selectAll(GmRolePermissionEntry.class, fields, params);
        }
        return new ArrayList<>();
    }
}
