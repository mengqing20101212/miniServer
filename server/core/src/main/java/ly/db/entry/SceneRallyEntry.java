package ly.db.entry;

import java.time.LocalDateTime;
import java.util.Arrays;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/**
 * 场景集结聚合实体。
 *
 * <p>完整 Protobuf 快照包含全部成员，确保集结状态和成员状态由一次 revision UPSERT 原子保存，
 * 不拆成多张表制造跨表中间状态。
 */
@DbMeta.DbTable(name = "scene_rally", uniqueKeys = {"scene_id,rally_id"})
public final class SceneRallyEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "scene_id", "rally_id", "leader_player_id", "alliance_id", "current_x", "current_y",
      "rally_status", "launch_at_millis", "applied_battle_result_id", "state_version",
      "snapshot_data", "data_version", "revision", "deleted", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  /** 数据库自增主键；业务唯一性由 scene_id + rally_id 保证。 */
  private Long id;

  /** 集结当前所在逻辑场景。 */
  @DbMeta.DbField(name = "scene_id", nullable = false)
  private String sceneId;

  /** 场景内全局集结 ID，同时也是 SceneObject ID。 */
  @DbMeta.DbField(name = "rally_id", nullable = false)
  private Long rallyId;

  /** 集结队长玩家 ID。 */
  @DbMeta.DbField(name = "leader_player_id", nullable = false)
  private Long leaderPlayerId;

  /** 集结联盟 ID，所有成员加入时必须匹配。 */
  @DbMeta.DbField(name = "alliance_id", nullable = false)
  private Long allianceId;

  /** 集结对象当前 X 格坐标。 */
  @DbMeta.DbField(name = "current_x", nullable = false)
  private Integer currentX;

  /** 集结对象当前 Y 格坐标。 */
  @DbMeta.DbField(name = "current_y", nullable = false)
  private Integer currentY;

  /** SceneRallyStatus 的稳定 Protobuf 枚举编号，供运维查询。 */
  @DbMeta.DbField(name = "rally_status", nullable = false)
  private Integer rallyStatus;

  /** 计划发车时间戳，毫秒。 */
  @DbMeta.DbField(name = "launch_at_millis", nullable = false)
  private Long launchAtMillis;

  /** 已幂等应用的 BattleServer 结果 ID，0 表示尚未应用。 */
  @DbMeta.DbField(name = "applied_battle_result_id", nullable = false)
  private Long appliedBattleResultId;

  /** 集结领域状态机版本。 */
  @DbMeta.DbField(name = "state_version", nullable = false)
  private Integer stateVersion;

  /** SceneRallySnapshot 的 Protobuf 二进制，内部包含全部成员。 */
  @DbMeta.DbField(name = "snapshot_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] snapshotData;

  /** snapshot_data 的业务结构版本。 */
  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  /** 通用 SceneObject 的单调递增落库版本，负责拒绝异步旧快照。 */
  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  /** 集结全部结束且可回收后写 1，启动恢复只读取 0。 */
  @DbMeta.DbField(name = "deleted", nullable = false)
  private Integer deleted;

  /** 聚合快照生成时间。 */
  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;

  public SceneRallyEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  /** 按 scene_id + rally_id 原子保存集结及成员，数据库拒绝较小 revision。 */
  public boolean upsertIfNewer() {
    return SceneRallyEntryHelper.upsertIfNewer(this);
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getSceneId() { return sceneId; }
  public void setSceneId(String value) { sceneId = value; changed(0); }
  public Long getRallyId() { return rallyId; }
  public void setRallyId(Long value) { rallyId = value; changed(1); }
  public Long getLeaderPlayerId() { return leaderPlayerId; }
  public void setLeaderPlayerId(Long value) { leaderPlayerId = value; changed(2); }
  public Long getAllianceId() { return allianceId; }
  public void setAllianceId(Long value) { allianceId = value; changed(3); }
  public Integer getCurrentX() { return currentX; }
  public void setCurrentX(Integer value) { currentX = value; changed(4); }
  public Integer getCurrentY() { return currentY; }
  public void setCurrentY(Integer value) { currentY = value; changed(5); }
  public Integer getRallyStatus() { return rallyStatus; }
  public void setRallyStatus(Integer value) { rallyStatus = value; changed(6); }
  public Long getLaunchAtMillis() { return launchAtMillis; }
  public void setLaunchAtMillis(Long value) { launchAtMillis = value; changed(7); }
  public Long getAppliedBattleResultId() { return appliedBattleResultId; }
  public void setAppliedBattleResultId(Long value) { appliedBattleResultId = value; changed(8); }
  public Integer getStateVersion() { return stateVersion; }
  public void setStateVersion(Integer value) { stateVersion = value; changed(9); }
  public byte[] getSnapshotData() {
    return snapshotData == null ? new byte[0] : Arrays.copyOf(snapshotData, snapshotData.length);
  }
  public void setSnapshotData(byte[] value) {
    snapshotData = value == null ? new byte[0] : Arrays.copyOf(value, value.length);
    changed(10);
  }
  public Integer getDataVersion() { return dataVersion; }
  public void setDataVersion(Integer value) { dataVersion = value; changed(11); }
  public Long getRevision() { return revision; }
  public void setRevision(Long value) { revision = value; changed(12); }
  public Integer getDeleted() { return deleted; }
  public void setDeleted(Integer value) { deleted = value; changed(13); }
  public LocalDateTime getUpdateTime() { return updateTime; }
  public void setUpdateTime(LocalDateTime value) { updateTime = value; changed(14); }

  private void changed(int fieldIndex) {
    autoAddCurVersion();
    markFieldDirty(fieldIndex);
  }
}
