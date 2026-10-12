package ly.db;

import static org.junit.Assert.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ly.EntityToSqlGenerator;
import ly.db.entry.PlayerHeroEntry;
import ly.db.entry.PlayerResourceEntry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/** 新实体实际经过 SQL 生成、RocksDB 接收、JDBC 提交及删除。 */
public class PlayerEntityRocksJdbcTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void persistsAndDeletesIndependentPlayerRecords() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:player-hybrid;MODE=MySQL;DB_CLOSE_DELAY=-1");
        try (HikariDataSource source = new HikariDataSource(config)) {
            Method generator =
                    EntityToSqlGenerator.class.getDeclaredMethod(
                            "generateCreateTableSql", Class.class);
            generator.setAccessible(true);
            try (Connection connection = source.getConnection();
                    Statement statement = connection.createStatement()) {
                for (Class<?> type : new Class<?>[] {PlayerHeroEntry.class, PlayerResourceEntry.class}) {
                    String sql = (String) generator.invoke(new EntityToSqlGenerator(), type);
                    statement.execute(sql.substring(0, sql.indexOf(" ENGINE=")));
                }
            }
            MysqlService service = new MysqlService();
            try (GlobalIdGenerator ids =
                            new GlobalIdGenerator(
                                    123, temporary.newFolder().toPath().resolve("ids"));
                    RocksService rocks =
                            new RocksService(
                                    temporary.newFolder().toPath(),
                                    new MysqlConnector(source),
                                    5000)) {
                Field binding = MysqlService.class.getDeclaredField("rocksService");
                binding.setAccessible(true);
                binding.set(service, rocks);
                Field idBinding = MysqlService.class.getDeclaredField("idGenerator");
                idBinding.setAccessible(true);
                idBinding.set(service, ids);
                PlayerHeroEntry hero = new PlayerHeroEntry();
                hero.setId(service.generateEntityId());
                hero.setPlayerId(1001L);
                hero.setHeroId(7);
                hero.setLevel(1);
                hero.setStar(1);
                hero.setAwaken(0);
                hero.setExp(0L);
                PlayerResourceEntry resource = new PlayerResourceEntry();
                resource.setPlayerId(1001L);
                resource.setResourceType(1);
                resource.setAmount(100L);
                assertTrue(service.save(hero));
                assertTrue(service.save(resource));
                assertNotNull(resource.getId());
                hero.setLevel(5);
                resource.setAmount(40L);
                service.update(hero);
                service.update(resource);
                rocks.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement()) {
                    try (ResultSet rows =
                            statement.executeQuery(
                                    "SELECT level FROM player_hero WHERE hero_id=7")) {
                        assertTrue(rows.next());
                        assertEquals(5, rows.getInt(1));
                    }
                    try (ResultSet rows =
                            statement.executeQuery(
                                    "SELECT amount FROM player_resource WHERE resource_type=1")) {
                        assertTrue(rows.next());
                        assertEquals(40, rows.getLong(1));
                    }
                }
                assertTrue(service.delete(hero));
                rocks.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery("SELECT COUNT(*) FROM player_hero")) {
                    rows.next();
                    assertEquals(0, rows.getInt(1));
                }
            }
        }
    }
}
