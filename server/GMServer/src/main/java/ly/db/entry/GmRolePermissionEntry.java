package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_role_permission")
public class GmRolePermissionEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "role_id",
        "permission",
  };


  /**ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Integer id;

  /**角色ID*/
  @DbMeta.DbField(name="role_id")
  private Integer role_id;

  /**权限标识*/
  @DbMeta.DbField(name="permission")
  private String permission;
  public GmRolePermissionEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmRolePermissionEntryHelper.save(this);
  }

  public void update() {
    GmRolePermissionEntryHelper.update(this);
  }

  public void delete() {
    GmRolePermissionEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmRolePermissionEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmRolePermissionEntryHelper.asyncUpdate(this);
  }

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Integer getId() {
    return id;
  }
 public void setRoleId(Integer RoleId) {
    this.role_id = RoleId;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public Integer getRoleId() {
    return role_id;
  }
 public void setPermission(String Permission) {
    this.permission = Permission;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getPermission() {
    return permission;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmRolePermissionEntry{"
+
        ", id="+id+
        ", role_id="+role_id+
        ", permission="+permission
        + '}';
  }
}
