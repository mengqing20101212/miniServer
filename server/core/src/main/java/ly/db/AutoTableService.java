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
    
    private static AutoTableService instance;
    
    private AutoTableService() {
        // 私有构造函数
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
            // 从MysqlService获取MysqlConnector
            MysqlConnector mysqlConnector = MysqlService.getInstance().getMysqlConnector();
            
            // 生成创建表的SQL文件
            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            generator.generateSqlFromPackage("ly.db.entry"); // 扫描entity包
            
            // 由于MysqlConnector没有直接获取Connection的方法，我们使用MysqlConnector的execute方法执行SQL
            // 这里暂时只生成SQL文件，实际执行将在MysqlService中完成
            System.out.println("SQL文件已生成到 ./generated-sql/ 目录下");
            
            // 尝试执行生成的SQL文件
            executeSqlFileWithMysqlConnector("./generated-sql/create-tables.sql", mysqlConnector);
            executeSqlFileWithMysqlConnector("./generated-sql/alter-tables.sql", mysqlConnector);
            
            System.out.println("自动建表服务执行完成！");
            
        } catch (Exception e) {
            System.err.println("自动建表服务执行失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 使用MysqlConnector执行SQL文件
     */
    private void executeSqlFileWithMysqlConnector(String filePath, MysqlConnector mysqlConnector) {
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(filePath))) {
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
                        executeSingleSqlWithMysqlConnector(sql, mysqlConnector);
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
                    executeSingleSqlWithMysqlConnector(sql, mysqlConnector);
                }
            }
            
        } catch (java.io.IOException e) {
            System.out.println("SQL文件不存在，跳过执行: " + filePath);
            // 如果文件不存在，这是正常的（可能没有需要创建或修改的表）
        } catch (Exception e) {
            System.err.println("执行SQL文件失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 使用MysqlConnector执行单条SQL语句
     */
    private void executeSingleSqlWithMysqlConnector(String sql, MysqlConnector mysqlConnector) {
        try {
            System.out.println("执行SQL: " + sql);
            boolean result = mysqlConnector.execute(sql);
            if (result) {
                System.out.println("SQL执行成功");
            } else {
                System.out.println("SQL执行可能失败");
            }
        } catch (Exception e) {
            // 对于重复执行的SQL，可能会报错，这里做适当处理
            System.out.println("SQL执行可能已存在或有其他问题，继续执行: " + e.getMessage());
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
            // 从MysqlService获取MysqlConnector
            MysqlConnector mysqlConnector = MysqlService.getInstance().getMysqlConnector();
            
            // 重新生成SQL文件（只生成CREATE TABLE部分，因为EntityToSqlGenerator构造函数不需要Connection）
            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            generator.generateSqlFromPackage("ly.db.entry");
            
            // 执行SQL
            executeSqlFileWithMysqlConnector("./generated-sql/create-tables.sql", mysqlConnector);
            executeSqlFileWithMysqlConnector("./generated-sql/alter-tables.sql", mysqlConnector);
            
            System.out.println("表结构刷新完成！");
        } catch (Exception e) {
            System.err.println("刷新表结构失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}