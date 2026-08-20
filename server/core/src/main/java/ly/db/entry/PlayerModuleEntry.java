package ly.db.entry;

import java.time.LocalDateTime;
import java.util.Arrays;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** 玩家模块分行持久化对象。 */
@DbMeta.DbTable(name = "player_module", uniqueKeys = {"player_id,module_id"})
public final class PlayerModuleEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "player_id", "module_id", "data_version", "revision", "module_data", "update_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  /** 数据库自增主键；业务唯一性由 player_id + module_id 保证。 */
  private Long id;

  /** 玩家唯一 ID。 */
  @DbMeta.DbField(name = "player_id", nullable = false)
  private Long playerId;

  /** 玩家模块稳定编号，不保存 Java 类名。 */
  @DbMeta.DbField(name = "module_id", nullable = false)
  private Integer moduleId;

  /** module_data 的业务结构版本，用于协议升级和数据迁移。 */
  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  /** 单调递增业务版本，异步旧快照不能覆盖新状态。 */
  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  /** 模块自己的 Protobuf 二进制，禁止保存 JSON 字符串或 Java 原生序列化对象。 */
  @DbMeta.DbField(name = "module_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] moduleData;

  /** 该不可变模块快照的生成时间。 */
  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;

  public PlayerModuleEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  public PlayerModuleEntry(
      Long id,
      long playerId,
      int moduleId,
      int dataVersion,
      long revision,
      byte[] moduleData,
      LocalDateTime updateTime) {
    this();
    this.id = id;
    setPlayerId(playerId);
    setModuleId(moduleId);
    setDataVersion(dataVersion);
    setRevision(revision);
    setModuleData(moduleData);
    setUpdateTime(updateTime);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  /** 同步插入当前模块实体。 */
  public boolean save() {
    return PlayerModuleEntryHelper.save(this);
  }

  /** 根据实体主键更新当前脏字段。 */
  public boolean update(String... fields) {
    return PlayerModuleEntryHelper.update(this, fields);
  }

  /** 把新实体放入通用异步落库队列。 */
  public void asyncSave() {
    PlayerModuleEntryHelper.asyncSave(this);
  }

  /** 把当前实体更新放入通用异步落库队列。 */
  public void asyncUpdate(String... fields) {
    PlayerModuleEntryHelper.asyncUpdate(this, fields);
  }

  public PlayerModuleEntry snapshot() {
    return new PlayerModuleEntry(id, playerId, moduleId, dataVersion, revision, moduleData, updateTime);
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
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public Integer getModuleId() {
    return moduleId;
  }

  public void setModuleId(Integer moduleId) {
    this.moduleId = moduleId;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public Integer getDataVersion() {
    return dataVersion;
  }

  public void setDataVersion(Integer dataVersion) {
    this.dataVersion = dataVersion;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public Long getRevision() {
    return revision;
  }

  public void setRevision(Long revision) {
    this.revision = revision;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public byte[] getModuleData() {
    return moduleData == null ? new byte[0] : Arrays.copyOf(moduleData, moduleData.length);
  }

  public void setModuleData(byte[] moduleData) {
    this.moduleData = moduleData == null ? new byte[0] : Arrays.copyOf(moduleData, moduleData.length);
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
    autoAddCurVersion();
    markFieldDirty(5);
  }
}
