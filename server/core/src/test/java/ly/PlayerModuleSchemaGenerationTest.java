package ly;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import ly.db.MysqlService;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerModuleEntry;
import org.junit.Test;

public class PlayerModuleSchemaGenerationTest {
    @Test
    public void generatesCompositeKeyAndModuleBlobConstraints() throws Exception {
        EntityToSqlGenerator generator = new EntityToSqlGenerator();
        Method method = EntityToSqlGenerator.class.getDeclaredMethod("generateCreateTableSql", Class.class);
        method.setAccessible(true);

        String moduleSql = (String) method.invoke(generator, PlayerModuleEntry.class);
        String playerSql = (String) method.invoke(generator, PlayerEntry.class);

        assertTrue(moduleSql.contains("PRIMARY KEY (`player_id`, `module_id`)"));
        assertTrue(moduleSql.contains("`module_data` MEDIUMBLOB NOT NULL"));
        assertTrue(moduleSql.contains("`update_time` DATETIME(6) NOT NULL"));
        assertTrue(playerSql.contains("`modules` MEDIUMBLOB DEFAULT NULL"));
    }

    @Test
    public void mapsDatabaseRowIntoPlayerModuleEntry() {
        LocalDateTime updateTime = LocalDateTime.of(2026, 8, 16, 12, 30);
        PlayerModuleEntry entry = MysqlService.packetEntry(
                Map.of(
                        "player_id", 101L,
                        "module_id", 2,
                        "data_version", 1,
                        "revision", 7L,
                        "module_data", new byte[] {3, 4},
                        "update_time", Timestamp.valueOf(updateTime)),
                PlayerModuleEntry.class);

        assertEquals(101L, entry.getPlayerId().longValue());
        assertEquals(2, entry.getModuleId().intValue());
        assertEquals(7L, entry.getRevision().longValue());
        assertArrayEquals(new byte[] {3, 4}, entry.getModuleData());
        assertEquals(updateTime, entry.getUpdateTime());
    }
}
