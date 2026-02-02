import ly.db.MysqlConnector;

public class test_mysql_only {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        System.out.println("正在连接到远程MySQL数据库...");
        MysqlConnector mysqlConnector = new MysqlConnector(jdbcUrl, username, password, 10, 2, 30000, 5000);
        
        // 测试查询
        var result = mysqlConnector.select("SELECT COUNT(*) as count FROM player");
        System.out.println("查询结果: " + result);
        
        mysqlConnector.shutdown();
        System.out.println("测试完成");
    }
}