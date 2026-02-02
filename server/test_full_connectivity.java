import ly.db.MysqlConnector;
import ly.redis.RedisUtils;
import redis.clients.jedis.Jedis;

public class test_full_connectivity {
    public static void main(String[] args) {
        System.out.println("=== 开始测试完整的连接性 ===");
        
        // 测试 MySQL 连接
        System.out.println("\n1. 测试 MySQL 连接...");
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        MysqlConnector mysqlConnector = new MysqlConnector(jdbcUrl, username, password, 10, 2, 30000, 5000);
        
        // 测试查询
        var mysqlResult = mysqlConnector.select("SELECT COUNT(*) as count FROM player");
        System.out.println("MySQL 查询结果: " + mysqlResult);
        
        // 测试 Redis 连接
        System.out.println("\n2. 测试 Redis 连接...");
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.select(0); // 选择数据库 0
            String testKey = "test_connectivity_" + System.currentTimeMillis();
            String testValue = "connected_successfully";
            
            jedis.setex(testKey, 60, testValue); // 设置键值，60秒过期
            String retrievedValue = jedis.get(testKey);
            
            System.out.println("Redis 写入测试: " + (testValue.equals(retrievedValue) ? "成功" : "失败"));
            System.out.println("Redis 读取值: " + retrievedValue);
            
            // 使用 RedisUtils 测试
            System.out.println("\n3. 测试 RedisUtils 工具类...");
            RedisUtils.setWithExpire("connectivity_test", "OK", 60, java.util.concurrent.TimeUnit.SECONDS);
            String utilsResult = (String) RedisUtils.get("connectivity_test");
            System.out.println("RedisUtils 测试结果: " + utilsResult);
            
            jedis.close();
        } catch (Exception e) {
            System.out.println("Redis 连接失败: " + e.getMessage());
            e.printStackTrace();
        }
        
        mysqlConnector.shutdown();
        System.out.println("\n=== 连接性测试完成 ===");
    }
}