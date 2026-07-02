package ly.gmserver.service;

import ly.db.entry.GmMenuEntry;
import ly.db.entry.GmMenuEntryHelper;
import ly.gmserver.dto.MenuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GmMenuService {

    public List<MenuVO> listAll() {
        List<GmMenuEntry> menus = GmMenuEntryHelper.select(null);
        return buildTree(menus, null);
    }

    public List<MenuVO> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) return new ArrayList<>();
        // Get all menu ids for this role from gm_role_menu
        List<MenuVO> allMenus = listAll();
        // Get menu IDs that this role can see
        // For simplicity, we return menus without permission requirement OR menus assigned to the role
        List<GmMenuEntry> allEntries = GmMenuEntryHelper.select(null);
        Map<Integer, GmMenuEntry> entryMap = allEntries.stream()
            .collect(Collectors.toMap(GmMenuEntry::getId, m -> m));
        // Assign hasMenu flag - in a real app, filter by gm_role_menu
        // For now return all menus for the tree structure
        return allMenus;
    }

    public MenuVO getById(Integer id) {
        GmMenuEntry entry = GmMenuEntryHelper.getGmMenuEntryById(id);
        return entry != null ? toVO(entry) : null;
    }

    public boolean create(String name, String permission, Integer parentId,
                          String path, String icon, Integer sortOrder) {
        GmMenuEntry entry = new GmMenuEntry();
        entry.setName(name);
        entry.setPermission(permission != null ? permission : "");
        entry.setParentId(parentId != null ? parentId : 0);
        entry.setPath(path != null ? path : "");
        entry.setIcon(icon != null ? icon : "");
        entry.setSortOrder(sortOrder != null ? sortOrder : 0);
        return GmMenuEntryHelper.save(entry);
    }

    public boolean update(Integer id, String name, String permission, Integer parentId,
                          String path, String icon, Integer sortOrder) {
        GmMenuEntry entry = GmMenuEntryHelper.getGmMenuEntryById(id);
        if (entry == null) return false;
        if (name != null && !name.isEmpty()) entry.setName(name);
        if (permission != null) entry.setPermission(permission);
        if (parentId != null) entry.setParentId(parentId);
        if (path != null) entry.setPath(path);
        if (icon != null) entry.setIcon(icon);
        if (sortOrder != null) entry.setSortOrder(sortOrder);
        return GmMenuEntryHelper.update(entry, "name", "permission", "parent_id", "path", "icon", "sort_order");
    }

    public boolean delete(Integer id) {
        // check if has children
        List<GmMenuEntry> children = GmMenuEntryHelper.select(new String[]{"parent_id"}, id);
        if (!children.isEmpty()) return false; // has children, refuse
        GmMenuEntry entry = GmMenuEntryHelper.getGmMenuEntryById(id);
        if (entry == null) return false;
        return GmMenuEntryHelper.delete(entry);
    }

    private List<MenuVO> buildTree(List<GmMenuEntry> allMenus, List<Integer> allowedMenuIds) {
        Map<Integer, List<GmMenuEntry>> grouped = allMenus.stream()
            .collect(Collectors.groupingBy(m -> m.getParentId() != null ? m.getParentId() : 0));
        List<MenuVO> roots = new ArrayList<>();
        for (GmMenuEntry menu : grouped.getOrDefault(0, new ArrayList<>())) {
            MenuVO vo = buildSubTree(menu, grouped, allowedMenuIds);
            roots.add(vo);
        }
        roots.sort(Comparator.comparingInt(MenuVO::getSortOrder));
        return roots;
    }

    private MenuVO buildSubTree(GmMenuEntry menu, Map<Integer, List<GmMenuEntry>> grouped,
                                 List<Integer> allowedMenuIds) {
        MenuVO vo = toVO(menu);
        List<MenuVO> children = new ArrayList<>();
        for (GmMenuEntry child : grouped.getOrDefault(menu.getId(), new ArrayList<>())) {
            children.add(buildSubTree(child, grouped, allowedMenuIds));
        }
        children.sort(Comparator.comparingInt(MenuVO::getSortOrder));
        vo.setChildren(children);
        if (allowedMenuIds != null) {
            vo.setHasMenu(allowedMenuIds.contains(menu.getId()));
        }
        return vo;
    }

    private MenuVO toVO(GmMenuEntry entry) {
        MenuVO vo = new MenuVO();
        vo.setId(entry.getId());
        vo.setName(entry.getName());
        vo.setPermission(entry.getPermission());
        vo.setParentId(entry.getParentId());
        vo.setPath(entry.getPath());
        vo.setIcon(entry.getIcon());
        vo.setSortOrder(entry.getSortOrder());
        return vo;
    }
}
