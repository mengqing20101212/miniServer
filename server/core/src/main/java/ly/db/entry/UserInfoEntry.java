package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "user_info")
public class UserInfoEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "username", "nickname", "email", "phone", "register_time", "last_login_time", "level", "exp", "status"
  };

  /**user id */
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**用户名称*/
  @DbMeta.DbField(name="username")
  private String username;

  /**用户昵称*/
  @DbMeta.DbField(name="nickname")
  private String nickname;

  /**邮箱*/
  @DbMeta.DbField(name="email")
  private String email;

  /**手机号*/
  @DbMeta.DbField(name="phone")
  private String phone;

  /**注册时间*/
  @DbMeta.DbField(name="register_time")
  private java.time.LocalDateTime registerTime;

  /**最后登录时间*/
  @DbMeta.DbField(name="last_login_time")
  private java.time.LocalDateTime lastLoginTime;

  /**用户等级*/
  @DbMeta.DbField(name="level")
  private Integer level;

  /**经验值*/
  @DbMeta.DbField(name="exp")
  private Integer exp;

  /**用户状态 0-正常 1-封禁*/
  @DbMeta.DbField(name="status")
  private Integer status;
  public UserInfoEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    UserInfoEntryHelper.save(this);
  }

  public void update() {
    UserInfoEntryHelper.update(this);
  }

  public void delete() {
    UserInfoEntryHelper.delete(this);
  }

  public void asyncSave() {
    UserInfoEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    UserInfoEntryHelper.asyncUpdate(this);
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
 public void setNickname(String Nickname) {
    if (java.util.Objects.deepEquals(this.nickname, Nickname)) { return; }
    this.nickname = Nickname;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public String getNickname() {
    return nickname;
  }
 public void setEmail(String Email) {
    if (java.util.Objects.deepEquals(this.email, Email)) { return; }
    this.email = Email;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public String getEmail() {
    return email;
  }
 public void setPhone(String Phone) {
    if (java.util.Objects.deepEquals(this.phone, Phone)) { return; }
    this.phone = Phone;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public String getPhone() {
    return phone;
  }
 public void setRegistertime(java.time.LocalDateTime Registertime) {
    if (java.util.Objects.deepEquals(this.registerTime, Registertime)) { return; }
    this.registerTime = Registertime;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public java.time.LocalDateTime getRegistertime() {
    return registerTime;
  }
 public void setLastlogintime(java.time.LocalDateTime Lastlogintime) {
    if (java.util.Objects.deepEquals(this.lastLoginTime, Lastlogintime)) { return; }
    this.lastLoginTime = Lastlogintime;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public java.time.LocalDateTime getLastlogintime() {
    return lastLoginTime;
  }
 public void setLevel(Integer Level) {
    if (java.util.Objects.deepEquals(this.level, Level)) { return; }
    this.level = Level;
    autoAddCurVersion();
    markFieldDirty(7);
  }
  public Integer getLevel() {
    return level;
  }
 public void setExp(Integer Exp) {
    if (java.util.Objects.deepEquals(this.exp, Exp)) { return; }
    this.exp = Exp;
    autoAddCurVersion();
    markFieldDirty(8);
  }
  public Integer getExp() {
    return exp;
  }
 public void setStatus(Integer Status) {
    if (java.util.Objects.deepEquals(this.status, Status)) { return; }
    this.status = Status;
    autoAddCurVersion();
    markFieldDirty(9);
  }
  public Integer getStatus() {
    return status;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "UserInfoEntry{"
+
        ", id="+id+
        ", username="+username+
        ", nickname="+nickname+
        ", email="+email+
        ", phone="+phone+
        ", registerTime="+registerTime+
        ", lastLoginTime="+lastLoginTime+
        ", level="+level+
        ", exp="+exp+
        ", status="+status
        + '}';
  }
}
