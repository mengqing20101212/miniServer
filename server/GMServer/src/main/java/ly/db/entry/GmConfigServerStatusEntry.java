package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** 配表热更时单台服务器的准备/切换状态。 */
@DbMeta.DbTable(name = "gm_config_server_status")
public class GmConfigServerStatusEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
    "id", "publish_id", "version", "server_id", "server_type", "status", "error_msg", "create_time", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "publish_id")
  private String publish_id;

  @DbMeta.DbField(name = "version")
  private String version;

  @DbMeta.DbField(name = "server_id")
  private String server_id;

  @DbMeta.DbField(name = "server_type")
  private String server_type;

  @DbMeta.DbField(name = "status")
  private String status;

  @DbMeta.DbField(name = "error_msg", columnType = "TEXT")
  private String error_msg;

  @DbMeta.DbField(name = "create_time")
  private LocalDateTime create_time;

  @DbMeta.DbField(name = "update_time")
  private LocalDateTime update_time;

  public GmConfigServerStatusEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; autoAddCurVersion(); markFieldDirty(0); }
  public String getPublishId() { return publish_id; }
  public void setPublishId(String publishId) { this.publish_id = publishId; autoAddCurVersion(); markFieldDirty(1); }
  public String getVersion() { return version; }
  public void setVersion(String version) { this.version = version; autoAddCurVersion(); markFieldDirty(2); }
  public String getServerId() { return server_id; }
  public void setServerId(String serverId) { this.server_id = serverId; autoAddCurVersion(); markFieldDirty(3); }
  public String getServerType() { return server_type; }
  public void setServerType(String serverType) { this.server_type = serverType; autoAddCurVersion(); markFieldDirty(4); }
  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; autoAddCurVersion(); markFieldDirty(5); }
  public String getErrorMsg() { return error_msg; }
  public void setErrorMsg(String errorMsg) { this.error_msg = errorMsg; autoAddCurVersion(); markFieldDirty(6); }
  public LocalDateTime getCreateTime() { return create_time; }
  public void setCreateTime(LocalDateTime createTime) { this.create_time = createTime; autoAddCurVersion(); markFieldDirty(7); }
  public LocalDateTime getUpdateTime() { return update_time; }
  public void setUpdateTime(LocalDateTime updateTime) { this.update_time = updateTime; autoAddCurVersion(); markFieldDirty(8); }
}
