package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** 场景集结聚合实体访问入口。 */
public final class SceneRallyEntryHelper {
  private static final String[] ACTIVE_PAGE_FIELDS = {"scene_id", "deleted"};

  private SceneRallyEntryHelper() {
  }

  /** 按 rally_id 游标加载指定场景的未删除集结，快照中包含全部成员。 */
  public static List<SceneRallyEntry> selectActivePage(
      String sceneId, long afterRallyId, int limit) {
    return MysqlService.getInstance().selectPageAfterStrict(
        SceneRallyEntry.class,
        ACTIVE_PAGE_FIELDS,
        new Object[] {sceneId, 0},
        "rally_id",
        afterRallyId,
        limit);
  }

  /** 原子保存集结和成员聚合；旧 revision 被数据库忽略。 */
  public static boolean upsertIfNewer(SceneRallyEntry entry) {
    return MysqlService.getInstance().upsertIfNewer(entry, "revision");
  }
}
