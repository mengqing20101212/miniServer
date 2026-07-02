package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_role_menu")
public class GmRoleMenuEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "role_id",
        "menu_id",
  };


  /**ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Integer id;

  /**角色ID*/
  @DbMeta.DbField(name="role_id")
  private Integer role_id;

  /**菜单ID*/
  @DbMeta.DbField(name="menu_id")
  private Integer menu_id;
  public GmRoleMenuEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmRoleMenuEntryHelper.save(this);
  }

  public void update() {
    GmRoleMenuEntryHelper.update(this);
  }

  public void delete() {
    GmRoleMenuEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmRoleMenuEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmRoleMenuEntryHelper.asyncUpdate(this);
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
 public void setMenuId(Integer MenuId) {
    this.menu_id = MenuId;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public Integer getMenuId() {
    return menu_id;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmRoleMenuEntry{"
+
        ", id="+id+
        ", role_id="+role_id+
        ", menu_id="+menu_id
        + '}';
  }
}
