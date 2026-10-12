package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "share_enum_config")
public class ShareEnumConfigEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {"id", "code", "name", "config_desc"};

  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;
  @DbMeta.DbField(name="code")
  private String code;
  @DbMeta.DbField(name="name")
  private String name;
  @DbMeta.DbField(name="config_desc")
  private String config_desc;
  public ShareEnumConfigEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }
  public void save() {
    ShareEnumConfigEntryHelper.save(this);
  }

  public void update() {
    ShareEnumConfigEntryHelper.update(this);
  }

  public void delete() {
    ShareEnumConfigEntryHelper.delete(this);
  }

  public void asyncSave() {
    ShareEnumConfigEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    ShareEnumConfigEntryHelper.asyncUpdate(this);
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
 public void setCode(String Code) {
    if (java.util.Objects.deepEquals(this.code, Code)) { return; }
    this.code = Code;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getCode() {
    return code;
  }
 public void setName(String Name) {
    if (java.util.Objects.deepEquals(this.name, Name)) { return; }
    this.name = Name;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getName() {
    return name;
  }
 public void setConfigDesc(String ConfigDesc) {
    if (java.util.Objects.deepEquals(this.config_desc, ConfigDesc)) { return; }
    this.config_desc = ConfigDesc;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public String getConfigDesc() {
    return config_desc;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "ShareEnumConfigEntry{"
+
        ", id="+id+
        ", code="+code+
        ", name="+name+
        ", config_desc="+config_desc
        + '}';
  }
}
