package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
@DbMeta.DbTable(name = "player")
public class PlayerEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "name", "createTime", "loginTime", "logoutTime", "level", "vipLevel", "modules", "guidId", "account"
  };


  /**player id */
  @DbMeta.DbMasterKey(name="id", autoIncrement=true)
  @DbMeta.DbField(name="id")
  private Long id;

  /**玩家名称*/
  @DbMeta.DbField(name="name")
  private String name;

  /**创建时间*/
  @DbMeta.DbField(name="createTime")
  private java.time.LocalDateTime createTime;

  /**登录时间*/
  @DbMeta.DbField(name="loginTime")
  private java.time.LocalDateTime loginTime;

  /**登出时间*/
  @DbMeta.DbField(name="logoutTime")
  private java.time.LocalDateTime logoutTime;

  /**等级*/
  @DbMeta.DbField(name="level")
  private Integer level;

  /**vip Level*/
  @DbMeta.DbField(name="vipLevel")
  private Integer vipLevel;

  /**各个模块的养成数据*/
  @DbMeta.DbField(name="modules")
  private byte[] modules;

  /**工会id*/
  @DbMeta.DbField(name="guidId")
  private Long guidId;

  /**账户id*/
  @DbMeta.DbField(name="account")
  private String account;

  public PlayerEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }
  public void save() {
    PlayerEntryHelper.save(this);
  }

  public void update() {
    PlayerEntryHelper.update(this);
  }

  public void delete() {
    PlayerEntryHelper.delete(this);
  }

  public void asyncSave() {
    PlayerEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    PlayerEntryHelper.asyncUpdate(this);
  }

 public void setId(Long Id) {
    this.id = Id;
    autoAddCurVersion();
    markFieldDirty(0);
  }
  public Long getId() {
    return id;
  }
 public void setName(String Name) {
    this.name = Name;
    autoAddCurVersion();
    markFieldDirty(1);
  }
  public String getName() {
    return name;
  }
 public void setCreatetime(java.time.LocalDateTime Createtime) {
    this.createTime = Createtime;
    autoAddCurVersion();
    markFieldDirty(2);
  }
  public java.time.LocalDateTime getCreatetime() {
    return createTime;
  }
 public void setLogintime(java.time.LocalDateTime Logintime) {
    this.loginTime = Logintime;
    autoAddCurVersion();
    markFieldDirty(3);
  }
  public java.time.LocalDateTime getLogintime() {
    return loginTime;
  }
 public void setLogouttime(java.time.LocalDateTime Logouttime) {
    this.logoutTime = Logouttime;
    autoAddCurVersion();
    markFieldDirty(4);
  }
  public java.time.LocalDateTime getLogouttime() {
    return logoutTime;
  }
 public void setLevel(Integer Level) {
    this.level = Level;
    autoAddCurVersion();
    markFieldDirty(5);
  }
  public Integer getLevel() {
    return level;
  }
 public void setViplevel(Integer Viplevel) {
    this.vipLevel = Viplevel;
    autoAddCurVersion();
    markFieldDirty(6);
  }
  public Integer getViplevel() {
    return vipLevel;
  }
 public void setModules(byte[] Modules) {
    this.modules = Modules;
    autoAddCurVersion();
    markFieldDirty(7);
  }
  public byte[] getModules() {
    return modules;
  }
 public void setGuidid(Long Guidid) {
    this.guidId = Guidid;
    autoAddCurVersion();
    markFieldDirty(8);
  }
  public Long getGuidid() {
    return guidId;
  }
 public void setAccount(String Account) {
    this.account = Account;
    autoAddCurVersion();
    markFieldDirty(9);
  }
  public String getAccount() {
    return account;
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@

  @Override
  public String toString() {
    return "PlayerEntry{"
+
        ", id="+id+
        ", name="+name+
        ", createTime="+createTime+
        ", loginTime="+loginTime+
        ", logoutTime="+logoutTime+
        ", level="+level+
        ", vipLevel="+vipLevel+
        ", modules="+modules+
        ", guidId="+guidId+
        ", account="+account
        + '}';
  }
}
