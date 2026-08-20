package ly;

import ly.db.DbMeta;
import ly.db.MysqlConnector;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URLDecoder;
import java.sql.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * 数据库实体到建表 SQL 的生成器。
 * <p>
 * 扫描带 {@link ly.db.DbMeta.DbTable}、{@link ly.db.DbMeta.DbField} 等注解的 Entry 类，
 * 生成 CREATE TABLE 或 ALTER TABLE 语句，供本地初始化和表结构补齐使用。
 */
public class EntityToSqlGenerator {

    private String targetDir = "./generated-sql";

    public EntityToSqlGenerator() {
        // 创建目标目录
        File dir = new File(targetDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 从指定包中扫描所有带@DbTable注解的实体类并生成SQL
     */
    public void generateSqlFromPackage(String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> entityClasses = findEntityClasses(packageName);
        generateSqlFiles(entityClasses);
    }

    /**
     * 生成SQL文件
     */
    public void generateSqlFiles(Set<Class<?>> entityClasses) throws IOException {
        Map<String, String> createTableSqls = new TreeMap<>();

        for (Class<?> entityClass : entityClasses) {
            String tableName = getTableName(entityClass);
            if (tableName != null) {
                String createSql = generateCreateTableSql(entityClass);
                createTableSqls.put(tableName, createSql);
            }
        }

        // 写入创建表的SQL文件
        writeSqlToFile("create-tables.sql", createTableSqls.values());
    }
    
    /**
     * 生成比较现有表结构和实体类差异的SQL（需要数据库连接）
     */
    public void generateDiffSqlFromDatabase(String packageName, Connection connection) throws IOException, ClassNotFoundException {
        Set<Class<?>> entityClasses = findEntityClasses(packageName);
        Map<String, List<String>> alterTableSqls = new TreeMap<>();

        for (Class<?> entityClass : entityClasses) {
            String tableName = getTableName(entityClass);
            if (tableName != null) {
                List<String> existingColumns = getExistingColumns(tableName, connection);
                List<String> alterSqls = generateAlterTableSqls(entityClass, existingColumns);
                if (!alterSqls.isEmpty()) {
                    alterTableSqls.put(tableName, alterSqls);
                }
            }
        }

        // 写入修改表的SQL文件
        List<String> allAlterSqls = new ArrayList<>();
        for (List<String> alterSqlList : alterTableSqls.values()) {
            allAlterSqls.addAll(alterSqlList);
        }
        writeSqlToFile("alter-tables.sql", allAlterSqls);
    }

    /**
     * 根据当前数据库实际列生成“只补缺失列”的 ALTER TABLE 语句。
     *
     * <p>启动自动建表原来只执行 {@code CREATE TABLE IF NOT EXISTS}。该语句可以创建新表，
     * 但不会给已经存在的旧表补充后来新增的实体字段，例如 {@code login} 表新增的
     * {@code assigned_game_server_id}。这里仍然以 Entry 实体及其 {@link DbMeta.DbField}
     * 注解为唯一结构来源，先查 information_schema，再只生成数据库中不存在的列。
     *
     * <p>本方法只负责生成语句，不直接执行 DDL。执行时仍由 {@code AutoTableService}
     * 统一记录日志和处理多个服务同时启动时可能出现的重复补列竞争。
     */
    public List<String> generateMissingColumnSqlsFromDatabase(
            String packageName, MysqlConnector mysqlConnector)
            throws IOException, ClassNotFoundException {
        Objects.requireNonNull(mysqlConnector, "mysqlConnector");

        Set<Class<?>> entityClasses = findEntityClasses(packageName);
        Map<String, List<String>> alterTableSqls = new TreeMap<>();
        for (Class<?> entityClass : entityClasses) {
            String tableName = getTableName(entityClass);
            if (tableName == null) {
                continue;
            }
            List<String> existingColumns = getExistingColumns(tableName, mysqlConnector);
            List<String> missingColumnSqls = generateAlterTableSqls(entityClass, existingColumns);
            if (!missingColumnSqls.isEmpty()) {
                alterTableSqls.put(tableName, missingColumnSqls);
            }
        }

        List<String> result = new ArrayList<>();
        for (List<String> tableSqls : alterTableSqls.values()) {
            result.addAll(tableSqls);
        }
        return result;
    }

    /**
     * 扫描包中所有带@DbTable注解的实体类
     */
    private Set<Class<?>> findEntityClasses(String packageName) throws IOException, ClassNotFoundException {
        List<Class<?>> scannedClasses = new ArrayList<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<java.net.URL> resources = classLoader.getResources(packagePath);
        
        while (resources.hasMoreElements()) {
            java.net.URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    scanDirectory(directory, packageName, scannedClasses);
                }
            } else if (resource.getProtocol().equals("jar")) {
                scanJar(resource, packagePath, scannedClasses);
            }
        }

        Set<Class<?>> classes = new LinkedHashSet<>();
        scannedClasses.stream()
                .sorted(
                        Comparator.comparing((Class<?> clazz) -> Optional.ofNullable(getTableName(clazz)).orElse(""))
                                .thenComparing(Class::getName))
                .forEach(classes::add);
        return classes;
    }

    /**
     * 扫描目录中的类文件
     */
    private void scanDirectory(File directory, String packageName, Collection<Class<?>> classes) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) return;

        Arrays.sort(files, Comparator.comparing(File::getName));
        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
                    classes.add(clazz);
                }
            }
        }
    }

    /**
     * 扫描依赖 jar 中的实体类。
     *
     * <p>服务通过 Maven 或打包产物启动时，core 模块通常以 jar 形式出现在 classpath 中。
     * 如果只扫描 file 目录，启动自动建表会漏掉 core 里的公共 Entity。
     */
    private void scanJar(java.net.URL resource, String packagePath, Collection<Class<?>> classes)
            throws IOException, ClassNotFoundException {
        JarURLConnection connection = (JarURLConnection) resource.openConnection();
        try (JarFile jarFile = connection.getJarFile()) {
            Enumeration<JarEntry> entries = jarFile.entries();
            List<String> classNames = new ArrayList<>();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (entry.isDirectory() || !name.startsWith(packagePath) || !name.endsWith(".class")) {
                    continue;
                }
                String classFileName = URLDecoder.decode(name, java.nio.charset.StandardCharsets.UTF_8)
                        .replace('/', '.');
                String className = classFileName.substring(0, classFileName.length() - 6);
                classNames.add(className);
            }
            Collections.sort(classNames);
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if (clazz.isAnnotationPresent(DbMeta.DbTable.class)) {
                    classes.add(clazz);
                }
            }
        }
    }

    /**
     * 获取表名
     */
    private String getTableName(Class<?> entityClass) {
        DbMeta.DbTable tableAnnotation = entityClass.getAnnotation(DbMeta.DbTable.class);
        if (tableAnnotation != null) {
            String tableName = tableAnnotation.name();
            if (tableName != null && !tableName.trim().isEmpty()) {
                return tableName;
            }
        }
        // 如果注解中没有指定表名，则根据类名推断
        String className = entityClass.getSimpleName();
        if (className.endsWith("Entry")) {
            className = className.substring(0, className.length() - 5); // 移除"Entry"后缀
        }
        return camelToSnake(className);
    }

    /**
     * 生成创建表的SQL
     */
    private String generateCreateTableSql(Class<?> entityClass) {
        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS `").append(tableName).append("` (\n");
        
        Field[] fields = entityClass.getDeclaredFields();
        List<String> columnDefinitions = new ArrayList<>();
        List<String> primaryKeys = new ArrayList<>();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(DbMeta.DbField.class)) {
                String columnDefinition = generateColumnDefinition(field);
                if (columnDefinition != null) {
                    columnDefinitions.add(columnDefinition);
                    
                    // 检查是否为主键
                    if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
                        DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
                        String keyName = masterKey.name().isEmpty() ? field.getName() : masterKey.name();
                        primaryKeys.add("`" + keyName + "`");
                    }
                }
            }
        }
        
        // 添加列定义
        for (int i = 0; i < columnDefinitions.size(); i++) {
            sql.append("  ").append(columnDefinitions.get(i));
            if (i < columnDefinitions.size() - 1) {
                sql.append(",");
            }
            sql.append("\n");
        }
        
        // 添加主键约束
        if (!primaryKeys.isEmpty()) {
            sql.append(",\n  PRIMARY KEY (").append(String.join(", ", primaryKeys)).append(")");
        }

        DbMeta.DbTable table = entityClass.getAnnotation(DbMeta.DbTable.class);
        for (String uniqueKey : table.uniqueKeys()) {
            List<String> columns = Arrays.stream(uniqueKey.split(","))
                    .map(String::trim)
                    .filter(column -> !column.isEmpty())
                    .toList();
            if (columns.isEmpty()) {
                continue;
            }
            String indexName = "uk_" + tableName + "_" + String.join("_", columns);
            String quotedColumns = columns.stream()
                    .map(column -> "`" + column + "`")
                    .collect(Collectors.joining(", "));
            sql.append(",\n  UNIQUE KEY `")
                    .append(indexName)
                    .append("` (")
                    .append(quotedColumns)
                    .append(")");
        }
        
        sql.append("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;");
        
        return sql.toString();
    }

    /**
     * 生成列定义
     */
    private String generateColumnDefinition(Field field) {
        DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
        String fieldName = dbField.name().isEmpty() ? field.getName() : dbField.name();
        String fieldType =
                dbField.columnType().isBlank()
                        ? mapJavaTypeToSqlType(field.getType())
                        : dbField.columnType();
        
        if (fieldType == null) {
            System.err.println("无法映射Java类型 " + field.getType() + " 到SQL类型");
            return null;
        }
        
        StringBuilder columnDef = new StringBuilder();
        columnDef.append("`").append(fieldName).append("` ").append(fieldType);
        
        // 检查是否为主键及是否自增
        if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
            DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
            if (masterKey.autoIncrement()) {
                columnDef.append(" AUTO_INCREMENT");
            }
            columnDef.append(" NOT NULL");
        } else if (dbField.nullable()) {
            columnDef.append(" DEFAULT NULL");
        } else {
            columnDef.append(" NOT NULL");
        }
        
        return columnDef.toString();
    }

    /**
     * 映射Java类型到SQL类型
     */
    private String mapJavaTypeToSqlType(Class<?> javaType) {
        if (javaType == String.class) {
            return "VARCHAR(255)";
        } else if (javaType == Integer.class || javaType == int.class) {
            return "INT";
        } else if (javaType == Long.class || javaType == long.class) {
            return "BIGINT";
        } else if (javaType == Float.class || javaType == float.class) {
            return "FLOAT";
        } else if (javaType == Double.class || javaType == double.class) {
            return "DOUBLE";
        } else if (javaType == Boolean.class || javaType == boolean.class) {
            return "TINYINT(1)";
        } else if (javaType == java.time.LocalDateTime.class) {
            return "DATETIME";
        } else if (javaType == java.time.LocalDate.class) {
            return "DATE";
        } else if (javaType == java.time.LocalTime.class) {
            return "TIME";
        } else if (javaType == Byte.class || javaType == byte.class) {
            return "TINYINT";
        } else if (javaType == Short.class || javaType == short.class) {
            return "SMALLINT";
        } else if (javaType == byte[].class) {
            return "BLOB";
        } else if (javaType == java.math.BigDecimal.class) {
            return "DECIMAL(10,2)";
        } else {
            // 默认使用TEXT类型
            System.out.println("未知类型 " + javaType + " 使用默认类型 TEXT");
            return "TEXT";
        }
    }

    /**
     * 获取已存在的列
     */
    private List<String> getExistingColumns(String tableName, Connection connection) {
        List<String> columns = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, "%");
            while (rs.next()) {
                columns.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException e) {
            System.err.println("获取表 " + tableName + " 的列信息失败: " + e.getMessage());
        }
        return columns;
    }

    /**
     * 通过连接池查询当前 schema 中某张表的列名。
     *
     * <p>表名作为参数绑定，不参与 SQL 字符串拼接；查询失败时使用严格查询抛出异常，
     * 避免把“数据库不可用”误认为“所有列都缺失”并继续生成大量无效 DDL。
     */
    private List<String> getExistingColumns(String tableName, MysqlConnector mysqlConnector) {
        List<Map<String, Object>> rows = mysqlConnector.selectStrict(
                "SELECT COLUMN_NAME FROM information_schema.COLUMNS "
                        + "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?",
                tableName);
        List<String> columns = new ArrayList<>(rows.size());
        for (Map<String, Object> row : rows) {
            Object columnName = row.get("COLUMN_NAME");
            if (columnName != null) {
                columns.add(columnName.toString());
            }
        }
        return columns;
    }

    /**
     * 生成ALTER TABLE语句来添加缺失的列
     */
    List<String> generateAlterTableSqls(Class<?> entityClass, List<String> existingColumns) {
        List<String> alterSqls = new ArrayList<>();
        Set<String> normalizedExistingColumns = existingColumns.stream()
                .filter(Objects::nonNull)
                .map(column -> column.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
        Field[] fields = entityClass.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(DbMeta.DbField.class)) {
                DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
                String fieldName = dbField.name().isEmpty() ? field.getName() : dbField.name();
                
                // MySQL 在 Windows 与 Linux 上的大小写行为可能不同，这里按列名忽略大小写比较。
                if (!normalizedExistingColumns.contains(fieldName.toLowerCase(Locale.ROOT))) {
                    String columnDefinition = generateColumnDefinition(field);
                    if (columnDefinition != null) {
                        String alterSql = "ALTER TABLE `" + getTableName(entityClass) + "` ADD COLUMN " + columnDefinition + ";";
                        alterSqls.add(alterSql);
                    }
                }
            }
        }
        
        return alterSqls;
    }

    /**
     * 将SQL写入文件
     */
    private void writeSqlToFile(String fileName, Collection<String> sqlStatements) throws IOException {
        File file = new File(targetDir, fileName);
        try (FileWriter writer = new FileWriter(file)) {
            Iterator<String> iterator = sqlStatements.iterator();
            while (iterator.hasNext()) {
                writer.write(iterator.next());
                writer.write('\n');
                // 语句之间保留一个空行便于审查，但文件末尾不再产生多余空白行。
                if (iterator.hasNext()) {
                    writer.write('\n');
                }
            }
        }
        System.out.println("SQL文件已生成: " + file.getAbsolutePath());
    }

    /**
     * 驼峰命名转下划线命名
     */
    private String camelToSnake(String camelCase) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) sb.append('_');
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
