package ly.gmserver.service;

import ly.db.entry.GmRoleEntry;
import ly.db.entry.GmRoleEntryHelper;
import ly.db.entry.GmRolePermissionEntry;
import ly.db.entry.GmRolePermissionEntryHelper;
import ly.db.entry.GmRoleMenuEntry;
import ly.db.entry.GmRoleMenuEntryHelper;
import ly.gmserver.dto.RoleVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GmRoleService {

    public List<RoleVO> listAll() {
        List<GmRoleEntry> roles = GmRoleEntryHelper.select(null);
        return roles.stream().map(this::toVO).collect(Collectors.toList());
    }

    public RoleVO getById(Integer id) {
        GmRoleEntry entry = GmRoleEntryHelper.getGmRoleEntryById(id);
        return entry != null ? toVO(entry) : null;
    }

    public boolean create(String name, String description, List<String> permissions, List<Integer> menuIds) {
        List<GmRoleEntry> exist = GmRoleEntryHelper.select(new String[]{"name"}, name);
        if (!exist.isEmpty()) return false;

        GmRoleEntry entry = new GmRoleEntry();
        entry.setName(name);
        entry.setDescription(description != null ? description : "");
        boolean saved = GmRoleEntryHelper.save(entry);
        if (!saved || entry.getId() == null) return false;

        int roleId = entry.getId();
        savePermissions(roleId, permissions);
        saveRoleMenus(roleId, menuIds);
        return true;
    }

    public boolean update(Integer id, String name, String description, List<String> permissions, List<Integer> menuIds) {
        GmRoleEntry entry = GmRoleEntryHelper.getGmRoleEntryById(id);
        if (entry == null) return false;

        if (name != null && !name.isEmpty()) entry.setName(name);
        if (description != null) entry.setDescription(description);
        GmRoleEntryHelper.update(entry, "name", "description");

        // replace permissions
        List<GmRolePermissionEntry> oldPerms = GmRolePermissionEntryHelper.select(new String[]{"role_id"}, id);
        oldPerms.forEach(GmRolePermissionEntryHelper::delete);
        savePermissions(id, permissions);

        // replace menu ids
        List<GmRoleMenuEntry> oldMenus = GmRoleMenuEntryHelper.select(new String[]{"role_id"}, id);
        oldMenus.forEach(GmRoleMenuEntryHelper::delete);
        saveRoleMenus(id, menuIds);

        return true;
    }

    public boolean delete(Integer id) {
        GmRoleEntry entry = GmRoleEntryHelper.getGmRoleEntryById(id);
        if (entry == null) return false;
        // cascade delete permissions and menus
        List<GmRolePermissionEntry> perms = GmRolePermissionEntryHelper.select(new String[]{"role_id"}, id);
        perms.forEach(GmRolePermissionEntryHelper::delete);
        List<GmRoleMenuEntry> menus = GmRoleMenuEntryHelper.select(new String[]{"role_id"}, id);
        menus.forEach(GmRoleMenuEntryHelper::delete);
        return GmRoleEntryHelper.delete(entry);
    }

    public List<String> getPermissions(Integer roleId) {
        List<GmRolePermissionEntry> perms = GmRolePermissionEntryHelper.select(new String[]{"role_id"}, roleId);
        return perms.stream().map(GmRolePermissionEntry::getPermission).collect(Collectors.toList());
    }

    public List<Integer> getMenuIds(Integer roleId) {
        List<GmRoleMenuEntry> menus = GmRoleMenuEntryHelper.select(new String[]{"role_id"}, roleId);
        return menus.stream().map(GmRoleMenuEntry::getMenuId).collect(Collectors.toList());
    }

    private void savePermissions(int roleId, List<String> permissions) {
        if (permissions == null) return;
        for (String perm : permissions) {
            GmRolePermissionEntry p = new GmRolePermissionEntry();
            p.setRoleId(roleId);
            p.setPermission(perm.trim());
            GmRolePermissionEntryHelper.save(p);
        }
    }

    private void saveRoleMenus(int roleId, List<Integer> menuIds) {
        if (menuIds == null) return;
        for (Integer menuId : menuIds) {
            GmRoleMenuEntry m = new GmRoleMenuEntry();
            m.setRoleId(roleId);
            m.setMenuId(menuId);
            GmRoleMenuEntryHelper.save(m);
        }
    }

    private RoleVO toVO(GmRoleEntry entry) {
        RoleVO vo = new RoleVO();
        vo.setId(entry.getId());
        vo.setName(entry.getName());
        vo.setDescription(entry.getDescription());
        vo.setPermissions(getPermissions(entry.getId()));
        vo.setMenuIds(getMenuIds(entry.getId()));
        return vo;
    }
}
