package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.DbMeta;

/** 玩家模块分行持久化表结构定义。 */
@DbMeta.DbTable(name = "player_module")
public final class PlayerModuleEntry {
  @DbMeta.DbMasterKey(name = "player_id")
  @DbMeta.DbField(name = "player_id")
  private Long playerId;

  @DbMeta.DbMasterKey(name = "module_id")
  @DbMeta.DbField(name = "module_id")
  private Integer moduleId;

  @DbMeta.DbField(name = "data_version", nullable = false)
  private Integer dataVersion;

  @DbMeta.DbField(name = "revision", nullable = false)
  private Long revision;

  @DbMeta.DbField(name = "module_data", columnType = "MEDIUMBLOB", nullable = false)
  private byte[] moduleData;

  @DbMeta.DbField(name = "update_time", columnType = "DATETIME(6)", nullable = false)
  private LocalDateTime updateTime;
}
