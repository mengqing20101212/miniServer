package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/**
 * 玩家场景投影实体访问入口。
 *
 * <p>SceneServer 只依赖本 Helper 和 {@link PlayerSceneEntry}，不直接访问连接器，也不在业务代码中
 * 保存 SQL 字符串。分页和 revision 条件更新由通用实体层统一生成。
 */
public final class PlayerSceneEntryHelper {
  private static final String[] ACTIVE_PAGE_FIELDS = {"scene_id", "deleted"};

  private PlayerSceneEntryHelper() {
  }

  /** 按玩家 ID 游标加载指定场景中尚未删除的投影，供 SceneServer 启动恢复使用。 */
  public static List<PlayerSceneEntry> selectActivePage(
      String sceneId, long afterPlayerId, int limit) {
    return MysqlService.getInstance().selectPageAfterStrict(
        PlayerSceneEntry.class,
        ACTIVE_PAGE_FIELDS,
        new Object[] {sceneId, 0},
        "player_id",
        afterPlayerId,
        limit);
  }

  /**
   * 保存完整场景投影；只有更大的 revision 才能覆盖数据库中的同一玩家场景记录。
   *
   * <p>相同或更小 revision 会被数据库原子忽略，但仍视为一次成功的幂等消费。
   */
  public static boolean upsertIfNewer(PlayerSceneEntry entry) {
    return MysqlService.getInstance().upsertIfNewer(entry, "revision");
  }
}
