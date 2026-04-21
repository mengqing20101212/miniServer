package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "user_info")
public class UserInfoEntry extends AbstractEntry {

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
    this.id = Id;
    autoAddCurVersion();
  }
  public Long getId() {
    return id;
  }
 public void setUsername(String Username) {
    this.username = Username;
    autoAddCurVersion();
  }
  public String getUsername() {
    return username;
  }
 public void setNickname(String Nickname) {
    this.nickname = Nickname;
    autoAddCurVersion();
  }
  public String getNickname() {
    return nickname;
  }
 public void setEmail(String Email) {
    this.email = Email;
    autoAddCurVersion();
  }
  public String getEmail() {
    return email;
  }
 public void setPhone(String Phone) {
    this.phone = Phone;
    autoAddCurVersion();
  }
  public String getPhone() {
    return phone;
  }
 public void setRegistertime(java.time.LocalDateTime Registertime) {
    this.registerTime = Registertime;
    autoAddCurVersion();
  }
  public java.time.LocalDateTime getRegistertime() {
    return registerTime;
  }
 public void setLastlogintime(java.time.LocalDateTime Lastlogintime) {
    this.lastLoginTime = Lastlogintime;
    autoAddCurVersion();
  }
  public java.time.LocalDateTime getLastlogintime() {
    return lastLoginTime;
  }
 public void setLevel(Integer Level) {
    this.level = Level;
    autoAddCurVersion();
  }
  public Integer getLevel() {
    return level;
  }
 public void setExp(Integer Exp) {
    this.exp = Exp;
    autoAddCurVersion();
  }
  public Integer getExp() {
    return exp;
  }
 public void setStatus(Integer Status) {
    this.status = Status;
    autoAddCurVersion();
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