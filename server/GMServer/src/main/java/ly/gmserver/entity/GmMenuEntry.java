package ly.gmserver.entity;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

@DbMeta.DbTable(name = "gm_menu")
public class GmMenuEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {
        "id", "name", "permission", "parentId", "path", "icon", "sortOrder"
    };

    @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
    @DbMeta.DbField(name = "id")
    private Integer id;

    @DbMeta.DbField(name = "name")
    private String name;

    @DbMeta.DbField(name = "permission")
    private String permission;

    @DbMeta.DbField(name = "parentId")
    private Integer parentId;

    @DbMeta.DbField(name = "path")
    private String path;

    @DbMeta.DbField(name = "icon")
    private String icon;

    @DbMeta.DbField(name = "sortOrder")
    private Integer sortOrder;

    public GmMenuEntry() {
        initDirtyState(DIRTY_FIELDS.length);
    }

    @Override
    protected String[] allDirtyFieldNames() {
        return DIRTY_FIELDS;
    }

    public void save() {
        GmMenuHelper.save(this);
    }

    public void update() {
        GmMenuHelper.update(this);
    }

    public void delete() {
        GmMenuHelper.delete(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        autoAddCurVersion();
        markFieldDirty(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        autoAddCurVersion();
        markFieldDirty(1);
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
        autoAddCurVersion();
        markFieldDirty(2);
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
        autoAddCurVersion();
        markFieldDirty(3);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        autoAddCurVersion();
        markFieldDirty(4);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        autoAddCurVersion();
        markFieldDirty(5);
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
        autoAddCurVersion();
        markFieldDirty(6);
    }

    @Override
    public String toString() {
        return "GmMenuEntry{" +
                "id=" + id +
                ", name=" + name +
                ", permission=" + permission +
                ", parentId=" + parentId +
                ", path=" + path +
                ", icon=" + icon +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
