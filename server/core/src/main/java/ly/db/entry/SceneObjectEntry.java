package ly.db.entry;

import java.time.LocalDateTime;
import java.util.Arrays;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/**
 * 普通场景动态对象实体。
 *
 * <p>资源点、怪物、农田、掉落物和非玩家建筑等稀疏对象使用该表。在线玩家、玩家主城、
 * 行军和集结分别由会话状态、{@link PlayerSceneEntry}、{@link SceneMarchEntry} 和
 * {@link SceneRallyEntry} 管理，避免同一权威状态重复落库。
 */
@DbMeta.DbTable(name = "scene_object", uniqueKeys = {"scene_id,object_id"})
public final class SceneObjectEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "scene_id", "object_id", "object_type", "owner_id", "x", "y", "state_version",
      "data_tag_mask", "state_data", "data_version", "revision", "deleted", "update_time"
  };

  /** 数据库自增主键，只用于表内定位。 */
  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  /** 逻辑场景 ID，本服和跨服场景使用不同值。 */
  @DbMeta.DbField(name = "scene_id", nullable = false)
  private String sceneId;

  /** 场景内动态对象唯一 ID。 */
  @DbMeta.DbField(name = "object_id", nullable = false)
  private Long objectId;

  /** 稳定对象类型编码，不保存 Java 类名。 */
  @DbMeta.DbField(name = "object_type", nullable = false)
  private Integer objectType;

  /** 对象所属玩家或联盟 ID；系统对象使用 0。 */
  @DbMeta.DbField(name = "owner_id", nullable = false)
  private Long ownerId;

  /** 对象当前格子 X 坐标。 */
  @DbMeta.DbField(name = "x", nullable = false)
  private Integer x;

  /** 对象当前格子 Y 坐标。 */
  @DbMeta.DbField(name = "y", nullable = false)
  private Integer y;

  /** SceneShard 内对象状态版本。 */
  @DbMeta.DbField(name = "state_version", nullable = false)
  private Integer stateVersion;

  /** AOI 数据标签位掩码。 */
  @DbMeta.DbField(name = "data_tag_mask", nullable = false)
  private Long dataTagMask;

  /** 类型化 Protobuf 状态，不允许写入 JSON 字符串或 Java 原生序列化数据。 */
  @DbMeta.DbField(name = "state_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] stateData;

  /** state_data 的业务结构版本。 */
  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  /** 单调递增的落库版本，用于拒绝异步旧快照。 */
  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  /** 逻辑删除标记，1 表示对象已被场景回收。 */
  @DbMeta.DbField(name = "deleted", nullable = false)
  private Integer deleted;

  /** 快照生成时间。 */
  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;

  public SceneObjectEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  /** 按 scene_id + object_id 唯一键保存，并拒绝较小 revision。 */
  public boolean upsertIfNewer() {
    return SceneObjectEntryHelper.upsertIfNewer(this);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSceneId() {
    return sceneId;
  }

  public void setSceneId(String sceneId) {
    this.sceneId = sceneId;
    changed(0);
  }

  public Long getObjectId() {
    return objectId;
  }

  public void setObjectId(Long objectId) {
    this.objectId = objectId;
    changed(1);
  }

  public Integer getObjectType() {
    return objectType;
  }

  public void setObjectType(Integer objectType) {
    this.objectType = objectType;
    changed(2);
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
    changed(3);
  }

  public Integer getX() {
    return x;
  }

  public void setX(Integer x) {
    this.x = x;
    changed(4);
  }

  public Integer getY() {
    return y;
  }

  public void setY(Integer y) {
    this.y = y;
    changed(5);
  }

  public Integer getStateVersion() {
    return stateVersion;
  }

  public void setStateVersion(Integer stateVersion) {
    this.stateVersion = stateVersion;
    changed(6);
  }

  public Long getDataTagMask() {
    return dataTagMask;
  }

  public void setDataTagMask(Long dataTagMask) {
    this.dataTagMask = dataTagMask;
    changed(7);
  }

  public byte[] getStateData() {
    return stateData == null ? new byte[0] : Arrays.copyOf(stateData, stateData.length);
  }

  public void setStateData(byte[] stateData) {
    this.stateData = stateData == null ? new byte[0] : Arrays.copyOf(stateData, stateData.length);
    changed(8);
  }

  public Integer getDataVersion() {
    return dataVersion;
  }

  public void setDataVersion(Integer dataVersion) {
    this.dataVersion = dataVersion;
    changed(9);
  }

  public Long getRevision() {
    return revision;
  }

  public void setRevision(Long revision) {
    this.revision = revision;
    changed(10);
  }

  public Integer getDeleted() {
    return deleted;
  }

  public void setDeleted(Integer deleted) {
    this.deleted = deleted;
    changed(11);
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
    changed(12);
  }

  private void changed(int fieldIndex) {
    autoAddCurVersion();
    markFieldDirty(fieldIndex);
  }
}
