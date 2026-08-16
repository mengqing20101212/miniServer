package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "login")
public class LoginEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "account", "create_time", "last_login_time", "last_logout_time", "token", "channel", "players", "assigned_game_server_id"
  };


  /**账号id*/
  @DbMeta.DbMasterKey(name="id")
  @DbMeta.DbField(name="id")
  private Integer id;

  /**账号名称 一般为第三方id*/
  @DbMeta.DbField(name="account")
  private String account;

  /**账号创建时间*/
  @DbMeta.DbField(name="create_time")
  private java.time.LocalDateTime create_time;

  /**账号登录时间*/
  @DbMeta.DbField(name="last_login_time")
  private java.time.LocalDateTime last_login_time;

  /**账号登出时间*/
  @DbMeta.DbField(name="last_logout_time")
  private java.time.LocalDateTime last_logout_time;

  /**token */
  @DbMeta.DbField(name="token")
  private String token;

  /**渠道*/
  @DbMeta.DbField(name="channel")
  private String channel;

  /**该账号下面所有的角色Id信息*/
  @DbMeta.DbField(name="players")
  private String players;

  /**开发环境中由服务器开发分配的个人 GameServer。*/
  @DbMeta.DbField(name="assigned_game_server_id")
  private String assigned_game_server_id;
  public LoginEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }
  public void save() {
    LoginEntryHelper.save(this);
  }

  public void update() {
    LoginEntryHelper.update(this);
  }

  public void delete() {
    LoginEntryHelper.delete(this);
  }

  public void asyncSave() {
    LoginEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    LoginEntryHelper.asyncUpdate(this);
  }

 public void setId(Integer Id) {
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Integer getId() {
    return id;
  }
 public void setAccount(String Account) {
    this.account = Account;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getAccount() {
    return account;
  }
 public void setCreateTime(java.time.LocalDateTime CreateTime) {
    this.create_time = CreateTime;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public java.time.LocalDateTime getCreateTime() {
    return create_time;
  }
 public void setLastLoginTime(java.time.LocalDateTime LastLoginTime) {
    this.last_login_time = LastLoginTime;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public java.time.LocalDateTime getLastLoginTime() {
    return last_login_time;
  }
 public void setLastLogoutTime(java.time.LocalDateTime LastLogoutTime) {
    this.last_logout_time = LastLogoutTime;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public java.time.LocalDateTime getLastLogoutTime() {
    return last_logout_time;
  }
 public void setToken(String Token) {
    this.token = Token;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public String getToken() {
    return token;
  }
 public void setChannel(String Channel) {
    this.channel = Channel;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public String getChannel() {
    return channel;
  }
 public void setPlayers(String Players) {
    this.players = Players;
    autoAddCurVersion();
    markFieldDirty(7);
  }
 public String getPlayers() {
    return players;
  }
 public void setAssignedGameServerId(String AssignedGameServerId) {
    this.assigned_game_server_id = AssignedGameServerId;
    autoAddCurVersion();
    markFieldDirty(8);
  }
  public String getAssignedGameServerId() {
    return assigned_game_server_id;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "LoginEntry{"
+
        ", id="+id+
        ", account="+account+
        ", create_time="+create_time+
        ", last_login_time="+last_login_time+
        ", last_logout_time="+last_logout_time+
        ", token="+token+
        ", channel="+channel+
        ", players="+players+
        ", assigned_game_server_id="+assigned_game_server_id
        + '}';
  }
}
