package ly.db.entry;

import java.time.LocalDateTime;
import java.util.Arrays;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/**
 * 场景行军聚合实体。
 *
 * <p>常用检索字段单独成列，完整路径、目标和冻结部队摘要使用 Protobuf 二进制快照保存。
 */
@DbMeta.DbTable(name = "scene_march", uniqueKeys = {"scene_id,march_id"})
public final class SceneMarchEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "scene_id", "march_id", "owner_player_id", "alliance_id", "current_x", "current_y",
      "march_status", "arrival_at_millis", "state_version", "snapshot_data", "data_version",
      "revision", "deleted", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  /** 数据库自增主键；业务唯一性由 scene_id + march_id 保证。 */
  private Long id;

  /** 行军当前所在逻辑场景。 */
  @DbMeta.DbField(name = "scene_id", nullable = false)
  private String sceneId;

  /** 场景内全局行军 ID，同时也是 SceneObject ID。 */
  @DbMeta.DbField(name = "march_id", nullable = false)
  private Long marchId;

  /** 发起行军的玩家 ID。 */
  @DbMeta.DbField(name = "owner_player_id", nullable = false)
  private Long ownerPlayerId;

  /** 发车时冻结的联盟 ID，用于关系和跨服校验。 */
  @DbMeta.DbField(name = "alliance_id", nullable = false)
  private Long allianceId;

  /** 行军当前 X 格坐标，便于恢复时直接路由到目标 SceneShard。 */
  @DbMeta.DbField(name = "current_x", nullable = false)
  private Integer currentX;

  /** 行军当前 Y 格坐标。 */
  @DbMeta.DbField(name = "current_y", nullable = false)
  private Integer currentY;

  /** SceneMarchStatus 的稳定 Protobuf 枚举编号，供运维查询。 */
  @DbMeta.DbField(name = "march_status", nullable = false)
  private Integer marchStatus;

  /** 预计到达时间戳，毫秒。 */
  @DbMeta.DbField(name = "arrival_at_millis", nullable = false)
  private Long arrivalAtMillis;

  /** 行军领域状态机版本。 */
  @DbMeta.DbField(name = "state_version", nullable = false)
  private Integer stateVersion;

  /** SceneMarchSnapshot 的 Protobuf 二进制。 */
  @DbMeta.DbField(name = "snapshot_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] snapshotData;

  /** snapshot_data 的业务结构版本。 */
  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  /** 通用 SceneObject 的单调递增落库版本，负责拒绝异步旧快照。 */
  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  /** 行军结束且可回收后写 1，启动恢复只读取 0。 */
  @DbMeta.DbField(name = "deleted", nullable = false)
  private Integer deleted;

  /** 快照生成时间。 */
  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;

  public SceneMarchEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  /** 按 scene_id + march_id 原子保存，数据库拒绝较小 revision。 */
  public boolean upsertIfNewer() {
    return SceneMarchEntryHelper.upsertIfNewer(this);
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getSceneId() { return sceneId; }
  public void setSceneId(String value) { sceneId = value; changed(0); }
  public Long getMarchId() { return marchId; }
  public void setMarchId(Long value) { marchId = value; changed(1); }
  public Long getOwnerPlayerId() { return ownerPlayerId; }
  public void setOwnerPlayerId(Long value) { ownerPlayerId = value; changed(2); }
  public Long getAllianceId() { return allianceId; }
  public void setAllianceId(Long value) { allianceId = value; changed(3); }
  public Integer getCurrentX() { return currentX; }
  public void setCurrentX(Integer value) { currentX = value; changed(4); }
  public Integer getCurrentY() { return currentY; }
  public void setCurrentY(Integer value) { currentY = value; changed(5); }
  public Integer getMarchStatus() { return marchStatus; }
  public void setMarchStatus(Integer value) { marchStatus = value; changed(6); }
  public Long getArrivalAtMillis() { return arrivalAtMillis; }
  public void setArrivalAtMillis(Long value) { arrivalAtMillis = value; changed(7); }
  public Integer getStateVersion() { return stateVersion; }
  public void setStateVersion(Integer value) { stateVersion = value; changed(8); }
  public byte[] getSnapshotData() {
    return snapshotData == null ? new byte[0] : Arrays.copyOf(snapshotData, snapshotData.length);
  }
  public void setSnapshotData(byte[] value) {
    snapshotData = value == null ? new byte[0] : Arrays.copyOf(value, value.length);
    changed(9);
  }
  public Integer getDataVersion() { return dataVersion; }
  public void setDataVersion(Integer value) { dataVersion = value; changed(10); }
  public Long getRevision() { return revision; }
  public void setRevision(Long value) { revision = value; changed(11); }
  public Integer getDeleted() { return deleted; }
  public void setDeleted(Integer value) { deleted = value; changed(12); }
  public LocalDateTime getUpdateTime() { return updateTime; }
  public void setUpdateTime(LocalDateTime value) { updateTime = value; changed(13); }

  private void changed(int fieldIndex) {
    autoAddCurVersion();
    markFieldDirty(fieldIndex);
  }
}
