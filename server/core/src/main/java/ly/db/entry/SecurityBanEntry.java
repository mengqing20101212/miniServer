package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成风格的实体代码，业务扩展请放在自定义区。
 */
@DbMeta.DbTable(name = "security_ban")
public class SecurityBanEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "ban_type", "target", "reason", "source", "status", "start_time", "end_time",
      "operator", "create_time", "update_time"
  };

  /** 封禁记录ID */
  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  /** 封禁类型：1=IP，2=账号，3=角色，4=设备 */
  @DbMeta.DbField(name = "ban_type")
  private Integer ban_type;

  /** 封禁目标，按类型保存IP、账号名、角色ID或设备ID */
  @DbMeta.DbField(name = "target")
  private String target;

  /** 封禁原因 */
  @DbMeta.DbField(name = "reason")
  private String reason;

  /** 来源：1=GM手动，2=系统自动 */
  @DbMeta.DbField(name = "source")
  private Integer source;

  /** 状态：1=生效中，2=已解除 */
  @DbMeta.DbField(name = "status")
  private Integer status;

  /** 封禁开始时间 */
  @DbMeta.DbField(name = "start_time")
  private java.time.LocalDateTime start_time;

  /** 封禁结束时间，空表示永久封禁 */
  @DbMeta.DbField(name = "end_time")
  private java.time.LocalDateTime end_time;

  /** 操作人 */
  @DbMeta.DbField(name = "operator")
  private String operator;

  /** 创建时间 */
  @DbMeta.DbField(name = "create_time")
  private java.time.LocalDateTime create_time;

  /** 更新时间 */
  @DbMeta.DbField(name = "update_time")
  private java.time.LocalDateTime update_time;

  public SecurityBanEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    SecurityBanEntryHelper.save(this);
  }

  public void update() {
    SecurityBanEntryHelper.update(this);
  }

  public void delete() {
    SecurityBanEntryHelper.delete(this);
  }

  public void asyncSave() {
    SecurityBanEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    SecurityBanEntryHelper.asyncUpdate(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public Integer getBanType() {
    return ban_type;
  }

  public void setBanType(Integer banType) {
    this.ban_type = banType;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public Integer getSource() {
    return source;
  }

  public void setSource(Integer source) {
    this.source = source;
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
    autoAddCurVersion();
    markFieldDirty(5);
  }

  public java.time.LocalDateTime getStartTime() {
    return start_time;
  }

  public void setStartTime(java.time.LocalDateTime startTime) {
    this.start_time = startTime;
    autoAddCurVersion();
    markFieldDirty(6);
  }

  public java.time.LocalDateTime getEndTime() {
    return end_time;
  }

  public void setEndTime(java.time.LocalDateTime endTime) {
    this.end_time = endTime;
    autoAddCurVersion();
    markFieldDirty(7);
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
    autoAddCurVersion();
    markFieldDirty(8);
  }

  public java.time.LocalDateTime getCreateTime() {
    return create_time;
  }

  public void setCreateTime(java.time.LocalDateTime createTime) {
    this.create_time = createTime;
    autoAddCurVersion();
    markFieldDirty(9);
  }

  public java.time.LocalDateTime getUpdateTime() {
    return update_time;
  }

  public void setUpdateTime(java.time.LocalDateTime updateTime) {
    this.update_time = updateTime;
    autoAddCurVersion();
    markFieldDirty(10);
  }

  // @@@@@自定义方法开始区@@@@@

  public boolean isActive(java.time.LocalDateTime now) {
    return Integer.valueOf(1).equals(status)
        && (end_time == null || end_time.isAfter(now));
  }

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "SecurityBanEntry{"
        + "id=" + id
        + ", ban_type=" + ban_type
        + ", target=" + target
        + ", reason=" + reason
        + ", source=" + source
        + ", status=" + status
        + ", start_time=" + start_time
        + ", end_time=" + end_time
        + ", operator=" + operator
        + ", create_time=" + create_time
        + ", update_time=" + update_time
        + '}';
  }
}
