package ly.gmserver.dto;

import java.util.List;

public class MenuVO {
    private Long id;
    private String name;
    private String permission;
    private Long parentId;
    private String path;
    private String icon;
    private Integer sortOrder;
    private List<MenuVO> children;
    private boolean hasMenu;  // for role-menu assignment

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public List<MenuVO> getChildren() { return children; }
    public void setChildren(List<MenuVO> children) { this.children = children; }
    public boolean isHasMenu() { return hasMenu; }
    public void setHasMenu(boolean hasMenu) { this.hasMenu = hasMenu; }
}
