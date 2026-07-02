package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_role")
public class GmRoleEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "name",
        "description",
  };


  /**角色ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Integer id;

  /**角色名称*/
  @DbMeta.DbField(name="name")
  private String name;

  /**角色描述*/
  @DbMeta.DbField(name="description")
  private String description;
  public GmRoleEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmRoleEntryHelper.save(this);
  }

  public void update() {
    GmRoleEntryHelper.update(this);
  }

  public void delete() {
    GmRoleEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmRoleEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmRoleEntryHelper.asyncUpdate(this);
  }

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Integer getId() {
    return id;
  }
 public void setName(String Name) {
    this.name = Name;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getName() {
    return name;
  }
 public void setDescription(String Description) {
    this.description = Description;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getDescription() {
    return description;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmRoleEntry{"
+
        ", id="+id+
        ", name="+name+
        ", description="+description
        + '}';
  }
}
