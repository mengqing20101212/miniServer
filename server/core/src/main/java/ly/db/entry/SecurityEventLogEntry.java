package ly.db.entry;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/*
 * 自动生成风格的实体代码，业务扩展请放在自定义区。
 */
@DbMeta.DbTable(name = "security_event_log")
public class SecurityEventLogEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "event_type", "server_id", "ip", "account", "account_id", "player_id", "cmd",
      "sid", "seq", "reason", "extra", "create_time"
  };

  /** 安全事件ID */
  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  /** 事件类型：1=封禁，2=解除封禁，3=拦截，4=频率异常 */
  @DbMeta.DbField(name = "event_type")
  private Integer event_type;

  /** 产生事件的服务器ID */
  @DbMeta.DbField(name = "server_id")
  private String server_id;

  /** 客户端IP */
  @DbMeta.DbField(name = "ip")
  private String ip;

  /** 账号名 */
  @DbMeta.DbField(name = "account")
  private String account;

  /** 账号ID */
  @DbMeta.DbField(name = "account_id")
  private Long account_id;

  /** 角色ID */
  @DbMeta.DbField(name = "player_id")
  private Long player_id;

  /** 协议号 */
  @DbMeta.DbField(name = "cmd")
  private Integer cmd;

  /** 客户端连接ID */
  @DbMeta.DbField(name = "sid")
  private Integer sid;

  /** 客户端序列号 */
  @DbMeta.DbField(name = "seq")
  private Integer seq;

  /** 事件原因 */
  @DbMeta.DbField(name = "reason")
  private String reason;

  /** 额外信息 */
  @DbMeta.DbField(name = "extra")
  private String extra;

  /** 创建时间 */
  @DbMeta.DbField(name = "create_time")
  private java.time.LocalDateTime create_time;

  public SecurityEventLogEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public void save() {
    SecurityEventLogEntryHelper.save(this);
  }

  public void update() {
    SecurityEventLogEntryHelper.update(this);
  }

  public void delete() {
    SecurityEventLogEntryHelper.delete(this);
  }

  public void asyncSave() {
    SecurityEventLogEntryHelper.asyncSave(this);
  }

  public void asyncUpdate() {
    SecurityEventLogEntryHelper.asyncUpdate(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public Integer getEventType() {
    return event_type;
  }

  public void setEventType(Integer eventType) {
    this.event_type = eventType;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public String getServerId() {
    return server_id;
  }

  public void setServerId(String serverId) {
    this.server_id = serverId;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public Long getAccountId() {
    return account_id;
  }

  public void setAccountId(Long accountId) {
    this.account_id = accountId;
    autoAddCurVersion();
    markFieldDirty(5);
  }

  public Long getPlayerId() {
    return player_id;
  }

  public void setPlayerId(Long playerId) {
    this.player_id = playerId;
    autoAddCurVersion();
    markFieldDirty(6);
  }

  public Integer getCmd() {
    return cmd;
  }

  public void setCmd(Integer cmd) {
    this.cmd = cmd;
    autoAddCurVersion();
    markFieldDirty(7);
  }

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
    autoAddCurVersion();
    markFieldDirty(8);
  }

  public Integer getSeq() {
    return seq;
  }

  public void setSeq(Integer seq) {
    this.seq = seq;
    autoAddCurVersion();
    markFieldDirty(9);
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
    autoAddCurVersion();
    markFieldDirty(10);
  }

  public String getExtra() {
    return extra;
  }

  public void setExtra(String extra) {
    this.extra = extra;
    autoAddCurVersion();
    markFieldDirty(11);
  }

  public java.time.LocalDateTime getCreateTime() {
    return create_time;
  }

  public void setCreateTime(java.time.LocalDateTime createTime) {
    this.create_time = createTime;
    autoAddCurVersion();
    markFieldDirty(12);
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}
