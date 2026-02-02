import ly.db.MysqlConnector;
import ly.redis.RedisUtils;
import ly.config.ServerConfig;
import ly.config.RedisConfig;
import ly.ServerContext;

public class test_server_config {
    public static void main(String[] args) {
        System.out.println("=== 服务器配置验证 ===");
        
        // 模拟服务器配置
        System.out.println("\n1. 模拟服务器配置加载...");
        ServerConfig config = new ServerConfig();
        config.redis = new RedisConfig();
        config.redis.host = "localhost";
        config.redis.port = 6379;
        config.redis.password = null; // 如果有密码则设置
        
        // 设置全局上下文
        ServerContext.serverConfig = config;
        
        // 初始化 Redis 工具类
        System.out.println("2. 初始化 RedisUtils...");
        RedisUtils.init();
        
        // 测试 MySQL 连接
        System.out.println("\n3. 测试 MySQL 连接...");
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        MysqlConnector mysqlConnector = new MysqlConnector(jdbcUrl, username, password, 10, 2, 30000, 5000);
        
        // 测试查询
        var mysqlResult = mysqlConnector.select("SELECT COUNT(*) as count FROM player");
        System.out.println("MySQL 查询结果: " + mysqlResult);
        
        // 测试 Redis 连接 (使用 RedisUtils)
        System.out.println("\n4. 测试 Redis 连接 (使用 RedisUtils)...");
        try {
            RedisUtils.setWithExpire("server_connectivity_test", "OK", 60, java.util.concurrent.TimeUnit.SECONDS);
            String utilsResult = (String) RedisUtils.get("server_connectivity_test");
            System.out.println("RedisUtils 测试结果: " + utilsResult);
            
            // 检查 TTL
            long ttl = RedisUtils.getExpire("server_connectivity_test");
            System.out.println("Redis key TTL: " + ttl + " ms");
            
        } catch (Exception e) {
            System.out.println("RedisUtils 测试失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        mysqlConnector.shutdown();
        System.out.println("\n=== 服务器配置验证完成 ===");
    }
}