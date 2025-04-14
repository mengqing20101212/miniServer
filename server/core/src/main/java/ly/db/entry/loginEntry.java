package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "login")
public class loginEntry extends AbstractEntry {


  /**账号id*/
  @DbMeta.DbMasterKey(name="id")
  @DbMeta.DbField(name="id")
  private Integer id;

  /**账号名称 一般为第三方id*/
  @DbMeta.DbField(name="account")
  private String account;

  /**账号创建时间*/
  @DbMeta.DbField(name="create_time")
  private java.sql.Timestamp create_time;

  /**账号登录时间*/
  @DbMeta.DbField(name="last_login_time")
  private java.sql.Timestamp last_login_time;

  /**账号登出时间*/
  @DbMeta.DbField(name="last_logout_time")
  private java.sql.Timestamp last_logout_time;

  /**token */
  @DbMeta.DbField(name="token")
  private String token;

  /**渠道*/
  @DbMeta.DbField(name="channel")
  private String channel;

  /**该账号下面所有的角色Id信息*/
  @DbMeta.DbField(name="players")
  private String players;
  public void save() {
    loginEntryHelper.save(this);
  }

  public void update() {
    loginEntryHelper.update(this);
  }

  public void delete() {
    loginEntryHelper.delete(this);
  }

  public void asyncSave() {
    loginEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    loginEntryHelper.asyncUpdate(this);
  }

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
  }
  public Integer getId() {
    return id;
  }
 public void setAccount(String Account) {
    this.account = Account;
    autoAddCurVersion();
  }
  public String getAccount() {
    return account;
  }
 public void setCreateTime(java.sql.Timestamp CreateTime) {
    this.create_time = CreateTime;
    autoAddCurVersion();
  }
  public java.sql.Timestamp getCreateTime() {
    return create_time;
  }
 public void setLastLoginTime(java.sql.Timestamp LastLoginTime) {
    this.last_login_time = LastLoginTime;
    autoAddCurVersion();
  }
  public java.sql.Timestamp getLastLoginTime() {
    return last_login_time;
  }
 public void setLastLogoutTime(java.sql.Timestamp LastLogoutTime) {
    this.last_logout_time = LastLogoutTime;
    autoAddCurVersion();
  }
  public java.sql.Timestamp getLastLogoutTime() {
    return last_logout_time;
  }
 public void setToken(String Token) {
    this.token = Token;
    autoAddCurVersion();
  }
  public String getToken() {
    return token;
  }
 public void setChannel(String Channel) {
    this.channel = Channel;
    autoAddCurVersion();
  }
  public String getChannel() {
    return channel;
  }
 public void setPlayers(String Players) {
    this.players = Players;
    autoAddCurVersion();
  }
  public String getPlayers() {
    return players;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "loginEntry{"
+
        ", id="+id+
        ", account="+account+
        ", create_time="+create_time+
        ", last_login_time="+last_login_time+
        ", last_logout_time="+last_logout_time+
        ", token="+token+
        ", channel="+channel+
        ", players="+players
        + '}';
  }
}
