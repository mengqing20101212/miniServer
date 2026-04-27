package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_operation_log")
public class GmOperationLogEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "admin_id",
        "username",
        "action",
        "target_type",
        "target_id",
        "detail",
        "ip",
        "result",
        "created_at",
  };


  /**日志ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**管理员ID*/
  @DbMeta.DbField(name="admin_id")
  private Long admin_id;

  /**管理员用户名*/
  @DbMeta.DbField(name="username")
  private String username;

  /**操作类型*/
  @DbMeta.DbField(name="action")
  private String action;

  /**目标类型*/
  @DbMeta.DbField(name="target_type")
  private String target_type;

  /**目标ID*/
  @DbMeta.DbField(name="target_id")
  private String target_id;

  /**操作详情（JSON）*/
  @DbMeta.DbField(name="detail")
  private String detail;

  /**操作IP*/
  @DbMeta.DbField(name="ip")
  private String ip;

  /**结果 SUCCESS/FAIL*/
  @DbMeta.DbField(name="result")
  private String result;

  /**操作时间*/
  @DbMeta.DbField(name="created_at")
  private java.time.LocalDateTime created_at;
  public GmOperationLogEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmOperationLogEntryHelper.save(this);
  }

  public void update() {
    GmOperationLogEntryHelper.update(this);
  }

  public void delete() {
    GmOperationLogEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmOperationLogEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmOperationLogEntryHelper.asyncUpdate(this);
  }

 public void setId(Long Id) {
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Long getId() {
    return id;
  }
 public void setAdminId(Long AdminId) {
    this.admin_id = AdminId;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public Long getAdminId() {
    return admin_id;
  }
 public void setUsername(String Username) {
    this.username = Username;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getUsername() {
    return username;
  }
 public void setAction(String Action) {
    this.action = Action;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public String getAction() {
    return action;
  }
 public void setTargetType(String TargetType) {
    this.target_type = TargetType;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public String getTargetType() {
    return target_type;
  }
 public void setTargetId(String TargetId) {
    this.target_id = TargetId;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public String getTargetId() {
    return target_id;
  }
 public void setDetail(String Detail) {
    this.detail = Detail;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public String getDetail() {
    return detail;
  }
 public void setIp(String Ip) {
    this.ip = Ip;
    autoAddCurVersion();
    markFieldDirty(7);
  }
  public String getIp() {
    return ip;
  }
 public void setResult(String Result) {
    this.result = Result;
    autoAddCurVersion();
    markFieldDirty(8);
  }
  public String getResult() {
    return result;
  }
 public void setCreatedAt(java.time.LocalDateTime CreatedAt) {
    this.created_at = CreatedAt;
    autoAddCurVersion();
    markFieldDirty(9);
  }
  public java.time.LocalDateTime getCreatedAt() {
    return created_at;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmOperationLogEntry{"
+
        ", id="+id+
        ", admin_id="+admin_id+
        ", username="+username+
        ", action="+action+
        ", target_type="+target_type+
        ", target_id="+target_id+
        ", detail="+detail+
        ", ip="+ip+
        ", result="+result+
        ", created_at="+created_at
        + '}';
  }
}
