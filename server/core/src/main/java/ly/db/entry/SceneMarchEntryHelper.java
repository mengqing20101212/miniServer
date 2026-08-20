package ly.db.entry;

import java.util.List;
import ly.db.MysqlService;

/** 场景行军聚合实体访问入口。 */
public final class SceneMarchEntryHelper {
  private static final String[] ACTIVE_PAGE_FIELDS = {"scene_id", "deleted"};

  private SceneMarchEntryHelper() {
  }

  /** 按 march_id 游标加载指定场景的未删除行军，避免 OFFSET 在大表上退化。 */
  public static List<SceneMarchEntry> selectActivePage(
      String sceneId, long afterMarchId, int limit) {
    return MysqlService.getInstance().selectPageAfterStrict(
        SceneMarchEntry.class,
        ACTIVE_PAGE_FIELDS,
        new Object[] {sceneId, 0},
        "march_id",
        afterMarchId,
        limit);
  }

  /** 原子保存完整行军实体；旧 revision 被数据库忽略。 */
  public static boolean upsertIfNewer(SceneMarchEntry entry) {
    return MysqlService.getInstance().upsertIfNewer(entry, "revision");
  }
}
