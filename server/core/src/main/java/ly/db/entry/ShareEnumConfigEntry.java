package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: ShareEnumConfigEntry
 */
@DbMeta.DbVersion
public class ShareEnumConfigEntry extends AbstractEntry {
  private long id;
  private String code;
  private String name;
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
}
