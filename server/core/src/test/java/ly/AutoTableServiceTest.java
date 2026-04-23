package ly;

import ly.db.AutoTableService;
import ly.db.MysqlService;

public class AutoTableServiceTest {
    public static void main(String[] args) {
        try {
            // 初始化数据库连接
            String jdbcUrl = "jdbc:mysql://118.25.76.117:3306/pick_money";
            String username = "root";
            String password = "Ly@2026Root!8899";
            
            // 初始化MysqlService
            MysqlService.getInstance().init(jdbcUrl, username, password, 5, 2, 30000, 30000);
            
            System.out.println("开始测试自动建表服务...");
            
            // 启动自动建表服务
            AutoTableService.getInstance().startAutoTableService();
            
            System.out.println("自动建表服务测试完成！");
            
            // 检查生成的SQL文件
            java.io.File createSqlFile = new java.io.File("./generated-sql/create-tables.sql");
            java.io.File alterSqlFile = new java.io.File("./generated-sql/alter-tables.sql");
            
            System.out.println("CREATE TABLE SQL文件是否存在: " + createSqlFile.exists());
            System.out.println("ALTER TABLE SQL文件是否存在: " + alterSqlFile.exists());
            
            if (createSqlFile.exists()) {
                System.out.println("CREATE TABLE SQL内容预览:");
                java.nio.file.Files.lines(createSqlFile.toPath())
                    .limit(10)
                    .forEach(System.out::println);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
