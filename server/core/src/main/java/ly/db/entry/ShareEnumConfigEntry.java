package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: ShareEnumConfigEntry
 */
@DbMeta.DbTable(name = "share_enum_config")
public class ShareEnumConfigEntry extends AbstractEntry {
  @DbMeta.DbMasterKey(autoIncrement = true, name = "id")
  @DbMeta.DbField(name = "id")
  private long id;

  @DbMeta.DbField(name = "code")
  private String code;

  @DbMeta.DbField(name = "name")
  private String name;

  @DbMeta.DbField(name = "config_desc")
  private String config_desc;

  public ShareEnumConfigEntry() {}

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
    autoAddCurVersion();
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
    autoAddCurVersion();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    autoAddCurVersion();
  }

  public String getConfig_desc() {
    return config_desc;
  }

  public void setConfig_desc(String config_desc) {
    this.config_desc = config_desc;
    autoAddCurVersion();
  }

  public void save() {
    ShareEnumConfigHelper.save(this);
  }

  public void update() {
    ShareEnumConfigHelper.update(this);
  }

  public void delete() {
    ShareEnumConfigHelper.delete(this);
  }

  public void asyncSave() {
    ShareEnumConfigHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    ShareEnumConfigHelper.asyncUpdate(this);
  }

  @Override
  public String toString() {
    return "ShareEnumConfigEntry{"
        + "id="
        + id
        + ", code='"
        + code
        + '\''
        + ", name='"
        + name
        + '\''
        + ", config_desc='"
        + config_desc
        + '\''
        + '}';
  }
}
