package ly;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import org.junit.Test;

import ly.db.MysqlService;
import ly.db.entry.PlayerSceneEntry;

public class PlayerSceneSchemaGenerationTest {
    @Test
    public void generatesProjectionTableForSceneOrderedPaging() throws Exception {
        EntityToSqlGenerator generator = new EntityToSqlGenerator();
        Method method = EntityToSqlGenerator.class.getDeclaredMethod("generateCreateTableSql", Class.class);
        method.setAccessible(true);

        String sql = (String) method.invoke(generator, PlayerSceneEntry.class);

        assertTrue(sql.contains("`fog_data` MEDIUMBLOB NOT NULL"));
        assertTrue(sql.contains("`revision` BIGINT NOT NULL"));
        assertTrue(sql.contains(
                "UNIQUE KEY `uk_player_scene_scene_id_player_id` (`scene_id`, `player_id`)"));
    }

    @Test
    public void mapsDatabaseRowIntoPlayerSceneEntry() {
        LocalDateTime updateTime = LocalDateTime.of(2026, 8, 18, 23, 30);
        PlayerSceneEntry entry = MysqlService.packetEntry(
                Map.ofEntries(
                        Map.entry("id", 1L),
                        Map.entry("player_id", 101L),
                        Map.entry("scene_id", "world-1"),
                        Map.entry("city_object_id", 1_000_101L),
                        Map.entry("alliance_id", 10L),
                        Map.entry("city_x", 30),
                        Map.entry("city_y", 40),
                        Map.entry("city_level", 5),
                        Map.entry("city_state_version", 3),
                        Map.entry("fog_data", new byte[] {1, 2}),
                        Map.entry("data_version", 1),
                        Map.entry("revision", 7L),
                        Map.entry("deleted", 0),
                        Map.entry("update_time", Timestamp.valueOf(updateTime))),
                PlayerSceneEntry.class);

        assertEquals(101L, entry.getPlayerId().longValue());
        assertEquals("world-1", entry.getSceneId());
        assertEquals(1_000_101L, entry.getCityObjectId().longValue());
        assertArrayEquals(new byte[] {1, 2}, entry.getFogData());
        assertEquals(7L, entry.getRevision().longValue());
        assertEquals(updateTime, entry.getUpdateTime());
    }
}
