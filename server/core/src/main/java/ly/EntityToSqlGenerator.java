package ly;

import ly.db.DbMeta;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * 根据注解实体类自动生成建表SQL的工具类
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: EntityToSqlGenerator
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
        Map<String, String> createTableSqls = new HashMap<>();

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
        Map<String, List<String>> alterTableSqls = new HashMap<>();

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
     * 扫描包中所有带@DbTable注解的实体类
     */
    private Set<Class<?>> findEntityClasses(String packageName) throws IOException, ClassNotFoundException {
        Set<Class<?>> classes = new HashSet<>();
        String packagePath = packageName.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<java.net.URL> resources = classLoader.getResources(packagePath);
        
        while (resources.hasMoreElements()) {
            java.net.URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                File directory = new File(resource.getFile());
                if (directory.exists()) {
                    scanDirectory(directory, packageName, classes);
                }
            }
        }
        
        return classes;
    }

    /**
     * 扫描目录中的类文件
     */
    private void scanDirectory(File directory, String packageName, Set<Class<?>> classes) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files == null) return;
        
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
        String primaryKey = null;
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(DbMeta.DbField.class)) {
                String columnDefinition = generateColumnDefinition(field);
                if (columnDefinition != null) {
                    columnDefinitions.add(columnDefinition);
                    
                    // 检查是否为主键
                    if (field.isAnnotationPresent(DbMeta.DbMasterKey.class)) {
                        DbMeta.DbMasterKey masterKey = field.getAnnotation(DbMeta.DbMasterKey.class);
                        String keyName = masterKey.name().isEmpty() ? field.getName() : masterKey.name();
                        if (masterKey.autoIncrement()) {
                            primaryKey = "`" + keyName + "`";
                        } else {
                            primaryKey = "`" + keyName + "`";
                        }
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
        if (primaryKey != null) {
            sql.append(",\n  PRIMARY KEY (").append(primaryKey).append(")");
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
        String fieldType = mapJavaTypeToSqlType(field.getType());
        
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
        } else {
            // 普通字段，默认允许为空，除非特别指定
            columnDef.append(" DEFAULT NULL");
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
     * 生成ALTER TABLE语句来添加缺失的列
     */
    private List<String> generateAlterTableSqls(Class<?> entityClass, List<String> existingColumns) {
        List<String> alterSqls = new ArrayList<>();
        Field[] fields = entityClass.getDeclaredFields();
        
        for (Field field : fields) {
            if (field.isAnnotationPresent(DbMeta.DbField.class)) {
                DbMeta.DbField dbField = field.getAnnotation(DbMeta.DbField.class);
                String fieldName = dbField.name().isEmpty() ? field.getName() : dbField.name();
                
                if (!existingColumns.contains(fieldName)) {
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
            for (String sql : sqlStatements) {
                writer.write(sql);
                writer.write("\n\n");
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