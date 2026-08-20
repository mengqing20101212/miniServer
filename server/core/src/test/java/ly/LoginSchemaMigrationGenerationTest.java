package ly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import ly.db.entry.LoginEntry;
import org.junit.Test;

/**
 * 验证旧 login 表可以根据 LoginEntry 的实体定义补齐新增字段。
 */
public class LoginSchemaMigrationGenerationTest {

    @Test
    public void shouldGenerateOnlyMissingAssignedGameServerColumn() {
        EntityToSqlGenerator generator = new EntityToSqlGenerator();

        // 模拟升级前已经运行多年的 login 表：旧字段齐全，只有服务器分配字段尚未创建。
        List<String> existingColumns = List.of(
                "id",
                "account",
                "create_time",
                "last_login_time",
                "last_logout_time",
                "token",
                "channel",
                "players");

        List<String> sqls = generator.generateAlterTableSqls(LoginEntry.class, existingColumns);

        assertEquals(1, sqls.size());
        assertEquals(
                "ALTER TABLE `login` ADD COLUMN `assigned_game_server_id` VARCHAR(255) DEFAULT NULL;",
                sqls.getFirst());
    }

    @Test
    public void shouldCompareExistingColumnNamesIgnoringCase() {
        EntityToSqlGenerator generator = new EntityToSqlGenerator();

        // information_schema/JDBC 驱动在不同平台可能返回不同大小写，不能因此重复补列。
        List<String> existingColumns = List.of(
                "ID",
                "ACCOUNT",
                "CREATE_TIME",
                "LAST_LOGIN_TIME",
                "LAST_LOGOUT_TIME",
                "TOKEN",
                "CHANNEL",
                "PLAYERS",
                "ASSIGNED_GAME_SERVER_ID");

        assertTrue(generator.generateAlterTableSqls(LoginEntry.class, existingColumns).isEmpty());
    }
}
