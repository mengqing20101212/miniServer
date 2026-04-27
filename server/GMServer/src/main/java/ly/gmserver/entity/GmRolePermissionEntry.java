package ly.gmserver.entity;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

@DbMeta.DbTable(name = "gm_role_permission")
public class GmRolePermissionEntry extends AbstractEntry {
    private static final String[] DIRTY_FIELDS = {
        "id", "roleId", "permission"
    };

    @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
    @DbMeta.DbField(name = "id")
    private Integer id;

    @DbMeta.DbField(name = "roleId")
    private Integer roleId;

    @DbMeta.DbField(name = "permission")
    private String permission;

    public GmRolePermissionEntry() {
        initDirtyState(DIRTY_FIELDS.length);
    }

    @Override
    protected String[] allDirtyFieldNames() {
        return DIRTY_FIELDS;
    }

    public void save() {
        GmRolePermissionHelper.save(this);
    }

    public void update() {
        GmRolePermissionHelper.update(this);
    }

    public void delete() {
        GmRolePermissionHelper.delete(this);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        autoAddCurVersion();
        markFieldDirty(0);
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    @Override
    public String toString() {
        return "GmRolePermissionEntry{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", permission=" + permission +
                '}';
    }
}
