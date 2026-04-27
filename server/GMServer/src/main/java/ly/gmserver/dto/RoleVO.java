package ly.gmserver.dto;

import java.util.List;

public class RoleVO {
    private Integer id;
    private String name;
    private String description;
    private List<String> permissions;
    private List<Integer> menuIds;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }
    public List<Integer> getMenuIds() { return menuIds; }
    public void setMenuIds(List<Integer> menuIds) { this.menuIds = menuIds; }
}
