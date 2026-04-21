package ly;

import ly.EntityToSqlGenerator;

/**
 * 测试实体类到SQL的生成器
 */
public class TestEntityToSql {
    public static void main(String[] args) {
        System.out.println("开始测试EntityToSqlGenerator功能...");
        
        try {
            // 创建生成器实例（不需要数据库连接来生成CREATE TABLE语句）
            EntityToSqlGenerator generator = new EntityToSqlGenerator();
            
            // 生成SQL文件 - 扫描entity包中的所有实体类
            generator.generateSqlFromPackage("ly.db.entry");
            
            System.out.println("SQL文件已生成到 ./generated-sql/ 目录下");
            
            // 输出生成的文件列表
            java.io.File sqlDir = new java.io.File("./generated-sql/");
            if (sqlDir.exists() && sqlDir.isDirectory()) {
                String[] files = sqlDir.list();
                if (files != null) {
                    System.out.println("生成的文件列表:");
                    for (String file : files) {
                        System.out.println("- " + file);
                        
                        // 显示文件内容预览
                        java.io.File sqlFile = new java.io.File("./generated-sql/" + file);
                        System.out.println("内容预览:");
                        java.nio.file.Files.lines(sqlFile.toPath())
                            .limit(20) // 只显示前20行
                            .forEach(line -> System.out.println("  " + line));
                        System.out.println("---");
                    }
                }
            } else {
                System.out.println("SQL目录不存在，请检查是否有权限或其他问题");
                System.out.println("当前工作目录: " + System.getProperty("user.dir"));
            }
            
            System.out.println("EntityToSqlGenerator测试完成！");
            
        } catch (Exception e) {
            System.err.println("测试过程中出现错误:");
            e.printStackTrace();
        }
    }
}