package ly.db.entry;

import java.time.LocalDateTime;
import java.util.Arrays;

import ly.db.AbstractEntry;
import ly.db.DbMeta;

/**
 * 玩家场景投影数据库记录。
 *
 * <p>它只保存 SceneServer 重建地图所需的数据，不承载英雄、背包等 GameServer 养成模块。
 */
@DbMeta.DbTable(name = "player_scene", uniqueKeys = {"scene_id,player_id"})
public final class PlayerSceneEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "player_id", "scene_id", "city_object_id", "alliance_id", "city_x", "city_y",
      "city_level", "city_state_version", "fog_data", "data_version", "revision", "deleted",
      "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "player_id", nullable = false)
  private Long playerId;

  @DbMeta.DbField(name = "scene_id", nullable = false)
  private String sceneId;

  @DbMeta.DbField(name = "city_object_id", nullable = false)
  private Long cityObjectId;

  @DbMeta.DbField(name = "alliance_id", nullable = false)
  private Long allianceId;

  @DbMeta.DbField(name = "city_x", nullable = false)
  private Integer cityX;

  @DbMeta.DbField(name = "city_y", nullable = false)
  private Integer cityY;

  @DbMeta.DbField(name = "city_level", nullable = false)
  private Integer cityLevel;

  @DbMeta.DbField(name = "city_state_version", nullable = false)
  private Integer cityStateVersion;

  @DbMeta.DbField(name = "fog_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] fogData;

  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  @DbMeta.DbField(name = "deleted", nullable = false)
  private Integer deleted;

  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;

  public PlayerSceneEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPlayerId() {
    return playerId;
  }

  public void setPlayerId(Long playerId) {
    this.playerId = playerId;
    changed(0);
  }

  public String getSceneId() {
    return sceneId;
  }

  public void setSceneId(String sceneId) {
    this.sceneId = sceneId;
    changed(1);
  }

  public Long getCityObjectId() {
    return cityObjectId;
  }

  public void setCityObjectId(Long cityObjectId) {
    this.cityObjectId = cityObjectId;
    changed(2);
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
    changed(3);
  }

  public Integer getCityX() {
    return cityX;
  }

  public void setCityX(Integer cityX) {
    this.cityX = cityX;
    changed(4);
  }

  public Integer getCityY() {
    return cityY;
  }

  public void setCityY(Integer cityY) {
    this.cityY = cityY;
    changed(5);
  }

  public Integer getCityLevel() {
    return cityLevel;
  }

  public void setCityLevel(Integer cityLevel) {
    this.cityLevel = cityLevel;
    changed(6);
  }

  public Integer getCityStateVersion() {
    return cityStateVersion;
  }

  public void setCityStateVersion(Integer cityStateVersion) {
    this.cityStateVersion = cityStateVersion;
    changed(7);
  }

  public byte[] getFogData() {
    return fogData == null ? new byte[0] : Arrays.copyOf(fogData, fogData.length);
  }

  public void setFogData(byte[] fogData) {
    this.fogData = fogData == null ? new byte[0] : Arrays.copyOf(fogData, fogData.length);
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
