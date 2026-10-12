package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** GM 配表热更版本记录。 */
@DbMeta.DbTable(name = "gm_config_version")
public class GmConfigVersionEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
    "id", "version", "status", "switch_at_millis", "operator", "remark", "create_time", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "version")
  private String version;

  @DbMeta.DbField(name = "status")
  private String status;

  @DbMeta.DbField(name = "switch_at_millis")
  private Long switch_at_millis;

  @DbMeta.DbField(name = "operator")
  private String operator;

  @DbMeta.DbField(name = "remark")
  private String remark;

  @DbMeta.DbField(name = "create_time")
  private LocalDateTime create_time;

  @DbMeta.DbField(name = "update_time")
  private LocalDateTime update_time;

  public GmConfigVersionEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmConfigVersionEntryHelper.save(this);
  }

  public void update(String... fields) {
    GmConfigVersionEntryHelper.update(this, fields);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    checkPrimaryKeyChange(this.id, id);
    if (java.util.Objects.deepEquals(this.id, id)) { return; }
    this.id = id;
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    if (java.util.Objects.deepEquals(this.version, version)) { return; }
    this.version = version;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    if (java.util.Objects.deepEquals(this.status, status)) { return; }
    this.status = status;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public Long getSwitchAtMillis() {
    return switch_at_millis;
  }

  public void setSwitchAtMillis(Long switchAtMillis) {
    if (java.util.Objects.deepEquals(this.switch_at_millis, switchAtMillis)) { return; }
    this.switch_at_millis = switchAtMillis;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    if (java.util.Objects.deepEquals(this.operator, operator)) { return; }
    this.operator = operator;
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    if (java.util.Objects.deepEquals(this.remark, remark)) { return; }
    this.remark = remark;
    autoAddCurVersion();
    markFieldDirty(5);
  }

  public LocalDateTime getCreateTime() {
    return create_time;
  }

  public void setCreateTime(LocalDateTime createTime) {
    if (java.util.Objects.deepEquals(this.create_time, createTime)) { return; }
    this.create_time = createTime;
    autoAddCurVersion();
    markFieldDirty(6);
  }

  public LocalDateTime getUpdateTime() {
    return update_time;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    if (java.util.Objects.deepEquals(this.update_time, updateTime)) { return; }
    this.update_time = updateTime;
    autoAddCurVersion();
    markFieldDirty(7);
  }
}
