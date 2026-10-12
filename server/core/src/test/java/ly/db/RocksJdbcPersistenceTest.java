package ly.db;

import static org.junit.Assert.*;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/** Real JDBC transactions against an isolated MySQL-compatible test database. */
public class RocksJdbcPersistenceTest {
    @Rule public TemporaryFolder temporary = new TemporaryFolder();

    @Test
    public void failedMemberRollsBackWholeBatchThenRecoversTogether() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:rocks-atomic;MODE=MySQL;DB_CLOSE_DELAY=-1");
        try (HikariDataSource source = new HikariDataSource(config)) {
            MysqlConnector connector = new MysqlConnector(source);
            try (Connection connection = source.getConnection();
                    Statement statement = connection.createStatement()) {
                statement.execute(
                        "CREATE TABLE atomic_test(id BIGINT PRIMARY KEY, amount INT"
                            + " CHECK(amount>=0))");
            }
            RocksService.Sink jdbc = new RocksService.JdbcSink(connector);
            Path path = temporary.newFolder().toPath();
            try (RocksService rocks = new RocksService(path, jdbc, 1000, false)) {
                RocksService.Record a = new RocksService.Record();
                a.key = "a";
                a.table = "atomic_test";
                a.sql =
                        "INSERT INTO atomic_test VALUES(?,?) ON DUPLICATE KEY UPDATE"
                            + " amount=VALUES(amount)";
                a.parameters = new Object[] {1L, 10};
                RocksService.Record b = new RocksService.Record();
                b.key = "b";
                b.table = "atomic_test";
                b.sql = a.sql;
                b.parameters = new Object[] {2L, -1};
                rocks.acceptBatch(java.util.List.of(a, b));
                assertFalse(rocks.drainOnce());
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery("SELECT COUNT(*) FROM atomic_test")) {
                    rows.next();
                    assertEquals(0, rows.getInt(1));
                }
            }
            try (RocksService rocks = new RocksService(path, jdbc, 1000, false)) {
                rocks.accept(
                        "b",
                        "atomic_test",
                        "INSERT INTO atomic_test VALUES(?,?) ON DUPLICATE KEY UPDATE"
                            + " amount=VALUES(amount)",
                        new Object[] {2L, 20});
                assertTrue(rocks.drainOnce());
                rocks.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery("SELECT SUM(amount) FROM atomic_test")) {
                    rows.next();
                    assertEquals(30, rows.getInt(1));
                }
            }
        }
    }

    @Test
    public void recoversWritesAndMakesDeletesIdempotent() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:rocks-test;MODE=MySQL;DB_CLOSE_DELAY=-1");
        config.setMaximumPoolSize(2);
        try (HikariDataSource source = new HikariDataSource(config)) {
            MysqlConnector connector = new MysqlConnector(source);
            try (Connection connection = source.getConnection();
                    Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE entity_test(id BIGINT PRIMARY KEY, amount INT)");
            }
            Path path = temporary.newFolder().toPath();
            String save =
                    "INSERT INTO entity_test(id,amount) VALUES(?,?) ON DUPLICATE KEY UPDATE"
                        + " amount=VALUES(amount)";
            try (RocksService rocks = new RocksService(path, connector, 5000)) {
                rocks.accept("one", "entity_test", save, new Object[] {1L, 10});
                rocks.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery(
                                        "SELECT amount FROM entity_test WHERE id=1")) {
                    assertTrue(rows.next());
                    assertEquals(10, rows.getInt(1));
                }
                rocks.accept(
                        "one",
                        "entity_test",
                        "DELETE FROM entity_test WHERE id=?",
                        new Object[] {1L});
                rocks.awaitCommitted();
            }
            try (RocksService reopened = new RocksService(path, connector, 5000)) {
                reopened.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery("SELECT COUNT(*) FROM entity_test")) {
                    rows.next();
                    assertEquals(0, rows.getInt(1));
                }
            }
        }
    }

    @Test
    public void commitBeforeCrashIsNotExecutedTwiceOnRecovery() throws Exception {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:rocks-crash;MODE=MySQL;DB_CLOSE_DELAY=-1");
        try (HikariDataSource source = new HikariDataSource(config)) {
            MysqlConnector connector = new MysqlConnector(source);
            try (Connection connection = source.getConnection();
                    Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE entity_test(id BIGINT PRIMARY KEY, amount INT)");
                statement.execute("INSERT INTO entity_test VALUES(1,0)");
            }
            RocksService.Sink jdbc = new RocksService.JdbcSink(connector);
            Path directory = temporary.newFolder().toPath();
            try (RocksService rocks =
                    new RocksService(
                            directory,
                            (owner, records) -> {
                                jdbc.commit(owner, records);
                                throw new IllegalStateException(
                                        "Simulated crash after MySQL commit before cleanup");
                            },
                            50,
                            false)) {
                rocks.accept(
                        "one",
                        "entity_test",
                        "UPDATE entity_test SET amount=amount+1 WHERE id=?",
                        new Object[] {1L});
                rocks.drainOnce();
                assertEquals(1, rocks.pendingCount());
            }
            try (RocksService restored = new RocksService(directory, connector, 5000)) {
                restored.awaitCommitted();
                try (Connection connection = source.getConnection();
                        Statement statement = connection.createStatement();
                        ResultSet rows =
                                statement.executeQuery(
                                        "SELECT amount FROM entity_test WHERE id=1")) {
                    assertTrue(rows.next());
                    assertEquals(1, rows.getInt(1));
                }
            }
        }
    }
}
