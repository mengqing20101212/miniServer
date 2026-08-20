package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** 普通场景动态对象实体访问入口。 */
public final class SceneObjectEntryHelper {
  private static final String[] ACTIVE_PAGE_FIELDS = {"scene_id", "deleted"};

  private SceneObjectEntryHelper() {
  }

  /** 按 object_id 游标加载指定场景的有效动态对象。 */
  public static List<SceneObjectEntry> selectActivePage(
      String sceneId, long afterObjectId, int limit) {
    return MysqlService.getInstance().selectPageAfterStrict(
        SceneObjectEntry.class,
        ACTIVE_PAGE_FIELDS,
        new Object[] {sceneId, 0},
        "object_id",
        afterObjectId,
        limit);
  }

  /** 原子保存完整对象实体；旧 revision 被数据库忽略。 */
  public static boolean upsertIfNewer(SceneObjectEntry entry) {
    return MysqlService.getInstance().upsertIfNewer(entry, "revision");
  }
}
