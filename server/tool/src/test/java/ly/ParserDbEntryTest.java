package ly;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Types;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class ParserDbEntryTest {
  @Test
  public void generatedEntryShouldContainDirtyFieldTracking() throws Exception {
    ParserDbEntry parser = new ParserDbEntry();
    Path tempDir = Files.createTempDirectory("parser-db-entry-test");
    setField(parser, "targetDir", tempDir.toString());

    Object tableInfo = newInnerInstance(parser, "TableInfo");
    setField(tableInfo, "javaName", "Demo");
    setField(tableInfo, "tableName", "demo_table");

    Object idField = newFieldInfo(parser, "id", "Integer", "主键", true);
    Object nameField = newFieldInfo(parser, "user_name", "String", "用户名", false);

    @SuppressWarnings("unchecked")
    List<Object> fields = (List<Object>) getField(tableInfo, "fields");
    fields.add(idField);
    fields.add(nameField);
    setField(tableInfo, "key", idField);

    @SuppressWarnings("unchecked")
    List<Object> tables = (List<Object>) getField(parser, "tables");
    tables.add(tableInfo);

    invoke(parser, "createDbEntryJava");
    invoke(parser, "createDbEntryHelperJava");

    String entryContent = Files.readString(tempDir.resolve("DemoEntry.java"));
    String helperContent = Files.readString(tempDir.resolve("DemoEntryHelper.java"));

    assertTrue(entryContent.contains("private static final String[] DIRTY_FIELDS"));
    assertTrue(entryContent.contains("initDirtyState(DIRTY_FIELDS.length);"));
    assertTrue(entryContent.contains("markFieldDirty(0);"));
    assertTrue(entryContent.contains("markFieldDirty(1);"));
    assertTrue(entryContent.contains("protected String[] allDirtyFieldNames()"));
    assertTrue(helperContent.contains("asyncUpdate(DemoEntry DemoEntry, String... fileds)"));
    assertTrue(helperContent.contains("addUpdateEntry(DemoEntry, fileds);"));
  }

  @Test
  public void camelCaseConversionShouldMatchGeneratorUsage() {
    assertTrue("ShareEnumConfig".equals(ParserDbEntry.toUpperCamelCase("share_enum_config")));
    assertTrue("ConfigDesc".equals(ParserDbEntry.toCamelCase("config_desc")));
  }

  @Test
  public void mysqlCommonTypesShouldMapToExpectedJavaTypes() throws Exception {
    ParserDbEntry parser = new ParserDbEntry();
    Method method =
        ParserDbEntry.class.getDeclaredMethod("mapSqlTypeToJavaType", int.class, String.class, int.class);
    method.setAccessible(true);

    assertTrue("Byte".equals(method.invoke(parser, Types.TINYINT, "TINYINT", 4)));
    assertTrue("Short".equals(method.invoke(parser, Types.TINYINT, "TINYINT UNSIGNED", 4)));
    assertTrue("Short".equals(method.invoke(parser, Types.SMALLINT, "SMALLINT", 6)));
    assertTrue("Integer".equals(method.invoke(parser, Types.SMALLINT, "SMALLINT UNSIGNED", 6)));
    assertTrue("Long".equals(method.invoke(parser, Types.INTEGER, "INT UNSIGNED", 10)));
    assertTrue("java.math.BigInteger".equals(method.invoke(parser, Types.BIGINT, "BIGINT UNSIGNED", 20)));
    assertTrue("java.math.BigDecimal".equals(method.invoke(parser, Types.DECIMAL, "DECIMAL", 10)));
    assertTrue("String".equals(method.invoke(parser, Types.VARCHAR, "VARCHAR", 255)));
    assertTrue("String".equals(method.invoke(parser, Types.OTHER, "JSON", 0)));
    assertTrue("Integer".equals(method.invoke(parser, Types.DATE, "YEAR", 4)));
    assertTrue("java.time.LocalDate".equals(method.invoke(parser, Types.DATE, "DATE", 0)));
    assertTrue("java.time.LocalTime".equals(method.invoke(parser, Types.TIME, "TIME", 0)));
    assertTrue(
        "java.time.LocalDateTime".equals(
            method.invoke(parser, Types.TIMESTAMP, "TIMESTAMP", 0)));
    assertTrue("Boolean".equals(method.invoke(parser, Types.BIT, "BIT", 1)));
    assertTrue("byte[]".equals(method.invoke(parser, Types.BIT, "BIT", 8)));
    assertTrue("byte[]".equals(method.invoke(parser, Types.BLOB, "BLOB", 0)));
  }

  private Object newFieldInfo(
      ParserDbEntry parser, String name, String javaType, String desc, boolean autoIncrement)
      throws Exception {
    Object fieldInfo = newInnerInstance(parser, "FiledInfo");
    setField(fieldInfo, "name", name);
    setField(fieldInfo, "javaType", javaType);
    setField(fieldInfo, "desc", desc);
    setField(fieldInfo, "autoIncrement", autoIncrement);
    return fieldInfo;
  }

  private Object newInnerInstance(ParserDbEntry parser, String simpleName) throws Exception {
    Class<?> clazz = Class.forName("ly.ParserDbEntry$" + simpleName);
    Constructor<?> constructor = clazz.getDeclaredConstructor(ParserDbEntry.class);
    constructor.setAccessible(true);
    return constructor.newInstance(parser);
  }

  private void invoke(Object target, String methodName) throws Exception {
    Method method = target.getClass().getDeclaredMethod(methodName);
    method.setAccessible(true);
    method.invoke(target);
  }

  private Object getField(Object target, String fieldName) throws Exception {
    Field field = target.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    return field.get(target);
  }

  private void setField(Object target, String fieldName, Object value) throws Exception {
    Field field = target.getClass().getDeclaredField(fieldName);
    field.setAccessible(true);
    field.set(target, value);
  }
}
