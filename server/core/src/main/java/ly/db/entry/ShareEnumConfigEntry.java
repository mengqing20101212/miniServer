package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "share_enum_config")
public class ShareEnumConfigEntry extends AbstractEntry {

  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Integer id;
  @DbMeta.DbField(name="code")
  private String code;
  @DbMeta.DbField(name="name")
  private String name;
  @DbMeta.DbField(name="config_desc")
  private String config_desc;
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

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
  }
  public Integer getId() {
    return id;
  }
 public void setCode(String Code) {
    this.code = Code;
    autoAddCurVersion();
  }
  public String getCode() {
    return code;
  }
 public void setName(String Name) {
    this.name = Name;
    autoAddCurVersion();
  }
  public String getName() {
    return name;
  }
 public void setConfigDesc(String ConfigDesc) {
    this.config_desc = ConfigDesc;
    autoAddCurVersion();
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
