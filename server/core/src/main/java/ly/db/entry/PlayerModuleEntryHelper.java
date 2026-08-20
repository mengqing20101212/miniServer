package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** 玩家模块实体访问入口，避免 GameServer 的业务持久化代码直接拼接 SQL。 */
public final class PlayerModuleEntryHelper {
  private PlayerModuleEntryHelper() {
  }

  /** 严格加载玩家全部模块；数据库故障会抛出异常，不会被误判为玩家没有模块数据。 */
  public static List<PlayerModuleEntry> selectByPlayerId(long playerId) {
    return MysqlService.getInstance().selectAllStrict(
        PlayerModuleEntry.class, new String[] {"player_id"}, playerId);
  }

  /** 同步插入一个新的玩家模块实体。 */
  public static boolean save(PlayerModuleEntry entry) {
    return MysqlService.getInstance().save(entry);
  }

  /** 根据实体主键和脏字段同步更新玩家模块。 */
  public static boolean update(PlayerModuleEntry entry, String... fields) {
    return MysqlService.getInstance().update(entry, fields);
  }

  /** 把新玩家模块写入通用异步落库队列。 */
  public static void asyncSave(PlayerModuleEntry entry) {
    MysqlService.getInstance().addSaveEntry(entry);
  }

  /** 把玩家模块更新写入通用异步落库队列。 */
  public static void asyncUpdate(PlayerModuleEntry entry, String... fields) {
    MysqlService.getInstance().addUpdateEntry(entry, fields);
  }
}
