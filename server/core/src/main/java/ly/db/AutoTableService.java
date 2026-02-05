package ly.db;

import ly.EntityToSqlGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 自动建表服务类
 * 在服务器启动时自动创建或更新表结构
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: AutoTableService
 */
public class AutoTableService {
    
    private MysqlConnector mysqlConnector;
    private static AutoTableService instance;
    
    private AutoTableService() {
        this.mysqlConnector = MysqlConnector.getInstance();
    }
    
    public static synchronized AutoTableService getInstance() {
        if (instance == null) {
            instance = new AutoTableService();
        }
        return instance;
    }
    
    /**
     * 启动自动建表服务
     */
    public void startAutoTableService() {
        System.out.println("开始执行自动建表服务...");
        
        try {
            // 连接到数据库
            Connection connection = mysqlConnector.getConnection();
            
            // 生成创建表的SQL文件
            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            generator.generateSqlFromPackage("ly.db.entry"); // 扫描entity包
            
            // 生成修改表的SQL文件（对比数据库现有表结构）
            generator.generateDiffSqlFromDatabase("ly.db.entry", connection);
            
            // 执行创建表的SQL
            executeSqlFile("./generated-sql/create-tables.sql", connection);
            
            // 执行修改表的SQL（添加新字段）
            executeSqlFile("./generated-sql/alter-tables.sql", connection);
            
            System.out.println("自动建表服务执行完成！");
            
        } catch (Exception e) {
            System.err.println("自动建表服务执行失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 执行SQL文件
     */
    private void executeSqlFile(String filePath, Connection connection) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // 跳过空行和注释
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }
                
                sqlBuilder.append(line);
                
                // 如果遇到分号，说明一个完整的SQL语句结束了
                if (line.endsWith(";")) {
                    String sql = sqlBuilder.toString().trim();
                    if (!sql.isEmpty()) {
                        executeSingleSql(sql, connection);
                    }
                    sqlBuilder.setLength(0); // 清空StringBuilder
                } else {
                    sqlBuilder.append(" "); // 添加空格分隔
                }
            }
            
            // 处理最后一个没有分号结尾的SQL语句
            if (sqlBuilder.length() > 0) {
                String sql = sqlBuilder.toString().trim();
                if (!sql.isEmpty()) {
                    executeSingleSql(sql, connection);
                }
            }
            
        } catch (IOException e) {
            System.out.println("SQL文件不存在，跳过执行: " + filePath);
            // 如果文件不存在，这是正常的（可能没有需要创建或修改的表）
        } catch (Exception e) {
            System.err.println("执行SQL文件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 执行单条SQL语句
     */
    private void executeSingleSql(String sql, Connection connection) {
        try (Statement statement = connection.createStatement()) {
            System.out.println("执行SQL: " + sql);
            statement.execute(sql);
            System.out.println("SQL执行成功");
        } catch (SQLException e) {
            // 对于重复执行的SQL，可能会报错，这里做适当处理
            System.out.println("SQL执行可能已存在或有其他问题，继续执行: " + e.getMessage());
        }
    }
    
    /**
     * 根据实体类手动触发生成和执行SQL
     */
    public void refreshTableStructure() {
        try {
            Connection connection = mysqlConnector.getConnection();
            
            // 重新生成SQL文件
            EntityToSqlGenerator generator = new EntityToSqlGenerator(connection);
            generator.generateSqlFromPackage("ly.db.entry");
            
            // 执行SQL
            executeSqlFile("./generated-sql/create-tables.sql", connection);
            executeSqlFile("./generated-sql/alter-tables.sql", connection);
            
            System.out.println("表结构刷新完成！");
        } catch (Exception e) {
            System.err.println("刷新表结构失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}