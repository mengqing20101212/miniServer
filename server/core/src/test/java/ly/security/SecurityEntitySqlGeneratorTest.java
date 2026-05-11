package ly.security;

import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import ly.EntityToSqlGenerator;
import org.junit.Test;

/**
 * 验证安全封禁相关 Entity 能进入现有自动建表链路。
 */
public class SecurityEntitySqlGeneratorTest {

  @Test
  public void generateSecurityTablesFromEntity() throws Exception {
    EntityToSqlGenerator generator = new EntityToSqlGenerator();
    generator.generateSqlFromPackage("ly.db.entry");

    String sql = Files.readString(Path.of("generated-sql", "create-tables.sql"), StandardCharsets.UTF_8);
    assertTrue(sql.contains("CREATE TABLE IF NOT EXISTS `security_ban`"));
    assertTrue(sql.contains("`ban_type` INT"));
    assertTrue(sql.contains("`target` VARCHAR(255)"));
    assertTrue(sql.contains("CREATE TABLE IF NOT EXISTS `security_event_log`"));
    assertTrue(sql.contains("`event_type` INT"));
    assertTrue(sql.contains("`seq` INT"));
  }
}
