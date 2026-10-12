package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_menu")
public class GmMenuEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "name",
        "permission",
        "parent_id",
        "path",
        "icon",
        "sort_order",
  };


  /**菜单ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**菜单名称*/
  @DbMeta.DbField(name="name")
  private String name;

  /**所需权限（空=所有角色可见）*/
  @DbMeta.DbField(name="permission")
  private String permission;

  /**父菜单ID*/
  @DbMeta.DbField(name="parent_id")
  private Long parent_id;

  /**前端路由路径*/
  @DbMeta.DbField(name="path")
  private String path;

  /**图标*/
  @DbMeta.DbField(name="icon")
  private String icon;

  /**排序号*/
  @DbMeta.DbField(name="sort_order")
  private Integer sort_order;
  public GmMenuEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmMenuEntryHelper.save(this);
  }

  public void update() {
    GmMenuEntryHelper.update(this);
  }

  public void delete() {
    GmMenuEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmMenuEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmMenuEntryHelper.asyncUpdate(this);
  }

 public void setId(Long Id) {
    checkPrimaryKeyChange(this.id, Id);
    if (java.util.Objects.deepEquals(this.id, Id)) { return; }
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Long getId() {
    return id;
  }
 public void setName(String Name) {
    if (java.util.Objects.deepEquals(this.name, Name)) { return; }
    this.name = Name;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getName() {
    return name;
  }
 public void setPermission(String Permission) {
    if (java.util.Objects.deepEquals(this.permission, Permission)) { return; }
    this.permission = Permission;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getPermission() {
    return permission;
  }
 public void setParentId(Long ParentId) {
    if (java.util.Objects.deepEquals(this.parent_id, ParentId)) { return; }
    this.parent_id = ParentId;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public Long getParentId() {
    return parent_id;
  }
 public void setPath(String Path) {
    if (java.util.Objects.deepEquals(this.path, Path)) { return; }
    this.path = Path;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public String getPath() {
    return path;
  }
 public void setIcon(String Icon) {
    if (java.util.Objects.deepEquals(this.icon, Icon)) { return; }
    this.icon = Icon;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public String getIcon() {
    return icon;
  }
 public void setSortOrder(Integer SortOrder) {
    if (java.util.Objects.deepEquals(this.sort_order, SortOrder)) { return; }
    this.sort_order = SortOrder;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public Integer getSortOrder() {
    return sort_order;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmMenuEntry{"
+
        ", id="+id+
        ", name="+name+
        ", permission="+permission+
        ", parent_id="+parent_id+
        ", path="+path+
        ", icon="+icon+
        ", sort_order="+sort_order
        + '}';
  }
}
