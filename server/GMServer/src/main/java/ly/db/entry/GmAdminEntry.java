package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "gm_admin")
public class GmAdminEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
        "id",
        "username",
        "password",
        "role_id",
        "status",
        "create_time",
        "update_time",
  };


  /**管理员ID*/
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**用户名*/
  @DbMeta.DbField(name="username")
  private String username;

  /**密码（bcrypt哈希）*/
  @DbMeta.DbField(name="password")
  private String password;

  /**角色ID*/
  @DbMeta.DbField(name="role_id")
  private Long role_id;

  /**状态: 1=启用, 0=禁用*/
  @DbMeta.DbField(name="status")
  private Byte status;

  /**创建时间*/
  @DbMeta.DbField(name="create_time")
  private java.time.LocalDateTime create_time;

  /**更新时间*/
  @DbMeta.DbField(name="update_time")
  private java.time.LocalDateTime update_time;
  public GmAdminEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    GmAdminEntryHelper.save(this);
  }

  public void update() {
    GmAdminEntryHelper.update(this);
  }

  public void delete() {
    GmAdminEntryHelper.delete(this);
  }

  public void asyncSave() {
    GmAdminEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    GmAdminEntryHelper.asyncUpdate(this);
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
 public void setUsername(String Username) {
    if (java.util.Objects.deepEquals(this.username, Username)) { return; }
    this.username = Username;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getUsername() {
    return username;
  }
 public void setPassword(String Password) {
    if (java.util.Objects.deepEquals(this.password, Password)) { return; }
    this.password = Password;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getPassword() {
    return password;
  }
 public void setRoleId(Long RoleId) {
    if (java.util.Objects.deepEquals(this.role_id, RoleId)) { return; }
    this.role_id = RoleId;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public Long getRoleId() {
    return role_id;
  }
 public void setStatus(Byte Status) {
    if (java.util.Objects.deepEquals(this.status, Status)) { return; }
    this.status = Status;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public Byte getStatus() {
    return status;
  }
 public void setCreateTime(java.time.LocalDateTime CreateTime) {
    if (java.util.Objects.deepEquals(this.create_time, CreateTime)) { return; }
    this.create_time = CreateTime;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public java.time.LocalDateTime getCreateTime() {
    return create_time;
  }
 public void setUpdateTime(java.time.LocalDateTime UpdateTime) {
    if (java.util.Objects.deepEquals(this.update_time, UpdateTime)) { return; }
    this.update_time = UpdateTime;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public java.time.LocalDateTime getUpdateTime() {
    return update_time;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "GmAdminEntry{"
+
        ", id="+id+
        ", username="+username+
        ", password="+password+
        ", role_id="+role_id+
        ", status="+status+
        ", create_time="+create_time+
        ", update_time="+update_time
        + '}';
  }
}
