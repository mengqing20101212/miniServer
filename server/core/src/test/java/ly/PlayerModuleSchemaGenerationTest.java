package ly;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
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
}
