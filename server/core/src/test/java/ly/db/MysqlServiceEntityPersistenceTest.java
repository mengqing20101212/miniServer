package ly.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ly.db.entry.PlayerSceneEntry;
import ly.db.entry.SceneMarchEntry;
import ly.db.entry.SceneObjectEntry;
import ly.db.entry.SceneRallyEntry;
import org.junit.Test;

/** 验证场景持久化使用实体元数据生成分页和 revision UPSERT SQL。 */
public class MysqlServiceEntityPersistenceTest {

  @Test
  public void buildsKeysetPageSqlFromPlayerSceneEntityMetadata() {
    MysqlService service = new MysqlService();

    String sql = service.getSelectPageAfterSql(
        PlayerSceneEntry.class,
        new String[] {"scene_id", "deleted"},
        "player_id");

    assertEquals(
        "SELECT * FROM `player_scene` WHERE `scene_id`=? AND `deleted`=? AND `player_id`>?"
            + " ORDER BY `player_id` ASC LIMIT ?",
        sql);
  }

  @Test
  public void buildsKeysetPageSqlForEveryPersistentSceneAggregateEntity() {
    MysqlService service = new MysqlService();

    assertEquals(
        "SELECT * FROM `scene_object` WHERE `scene_id`=? AND `deleted`=? AND `object_id`>?"
            + " ORDER BY `object_id` ASC LIMIT ?",
        service.getSelectPageAfterSql(
            SceneObjectEntry.class, new String[] {"scene_id", "deleted"}, "object_id"));
    assertEquals(
        "SELECT * FROM `scene_march` WHERE `scene_id`=? AND `deleted`=? AND `march_id`>?"
            + " ORDER BY `march_id` ASC LIMIT ?",
        service.getSelectPageAfterSql(
            SceneMarchEntry.class, new String[] {"scene_id", "deleted"}, "march_id"));
    assertEquals(
        "SELECT * FROM `scene_rally` WHERE `scene_id`=? AND `deleted`=? AND `rally_id`>?"
            + " ORDER BY `rally_id` ASC LIMIT ?",
        service.getSelectPageAfterSql(
            SceneRallyEntry.class, new String[] {"scene_id", "deleted"}, "rally_id"));
  }

  @Test
  public void buildsRevisionUpsertFromPlayerSceneEntityMetadata() throws Exception {
    MysqlService service = new MysqlService();
    PlayerSceneEntry entry = newEntry();
    List<Object> params = new ArrayList<>();

    String sql = service.getRevisionUpsertSql(entry, "revision", params);

    assertTrue(sql.startsWith("INSERT INTO `player_scene` (`player_id`, `scene_id`"));
    assertTrue(sql.contains("ON DUPLICATE KEY UPDATE"));
    assertTrue(sql.contains(
        "`city_x`=IF(VALUES(`revision`)>`revision`,VALUES(`city_x`),`city_x`)"));
    assertTrue(sql.endsWith("`revision`=GREATEST(`revision`,VALUES(`revision`))"));
    assertFalse(sql.contains("`scene_id`=IF"));
    assertFalse(sql.contains("`player_id`=IF"));
    assertFalse(sql.contains("`id`"));
    assertEquals(13, params.size());
    assertEquals(101L, params.get(0));
    assertEquals("world-1", params.get(1));
    assertEquals(7L, params.get(10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void rejectsSqlFragmentsThatAreNotEntityColumns() {
    new MysqlService().getSelectPageAfterSql(
        PlayerSceneEntry.class,
        new String[] {"scene_id OR 1=1"},
        "player_id");
  }

  private static PlayerSceneEntry newEntry() {
    PlayerSceneEntry entry = new PlayerSceneEntry();
    entry.setPlayerId(101L);
    entry.setSceneId("world-1");
    entry.setCityObjectId(1_000_101L);
    entry.setAllianceId(10L);
    entry.setCityX(30);
    entry.setCityY(40);
    entry.setCityLevel(5);
    entry.setCityStateVersion(3);
    entry.setFogData(new byte[] {1, 2});
    entry.setDataVersion(1);
    entry.setRevision(7L);
    entry.setDeleted(0);
    entry.setUpdateTime(LocalDateTime.of(2026, 8, 19, 10, 0));
    return entry;
  }
}
