package ly;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * System integration test for GameServer
 */
public class SystemIntegrationTest 
{
    /**
     * Test full system connectivity (MySQL + Redis)
     */
    @Test
    public void testFullSystemConnectivity()
    {
        boolean mysqlSuccess = false;
        boolean redisSuccess = false;
        
        // Test MySQL connection
        String mysqlUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String mysqlUsername = "root";
        String mysqlPassword = "ly.1006897725";

        try {
            Connection conn = DriverManager.getConnection(mysqlUrl, mysqlUsername, mysqlPassword);
            System.out.println("✓ Connected to MySQL database successfully!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            
            if(rs.next()){
                System.out.println("MySQL Server Version: " + rs.getString(1));
            }
            
            // 测试插入一条数据到share_enum_config表
            stmt.executeUpdate("INSERT INTO share_enum_config (code, name, config_desc) VALUES ('INTEGRATION_TEST', 'Integration Test', 'System Integration Test') ON DUPLICATE KEY UPDATE name='Updated Integration Test'");
            System.out.println("Test data inserted to MySQL successfully!");
            
            conn.close();
            mysqlSuccess = true;
        } catch (SQLException e) {
            System.out.println("✗ MySQL connection failed!");
            e.printStackTrace();
        }
        
        // Test Redis connection
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        
        try (JedisPool jedisPool = new JedisPool(poolConfig, "139.224.80.204", 6379, 2000, "ly.1006897725")) {
            try (Jedis jedis = jedisPool.getResource()) {
                // 测试连接
                String pong = jedis.ping();
                System.out.println("✓ Connected to Redis successfully! Ping response: " + pong);
                
                // 测试基本操作
                String testKey = "integration_test_key_" + System.currentTimeMillis();
                String testValue = "integration_test_value";
                
                jedis.set(testKey, testValue);
                String retrievedValue = jedis.get(testKey);
                
                System.out.println("Set key '" + testKey + "' with value: " + testValue);
                System.out.println("Retrieved value: " + retrievedValue);
                
                // 清理测试数据
                jedis.del(testKey);
                
                redisSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("✗ Redis connection failed!");
            e.printStackTrace();
        }
        
        // 汇总结果
        System.out.println("\n=== System Integration Test Results ===");
        System.out.println("MySQL Connection: " + (mysqlSuccess ? "PASS" : "FAIL"));
        System.out.println("Redis Connection: " + (redisSuccess ? "PASS" : "FAIL"));
        System.out.println("Overall Result: " + (mysqlSuccess && redisSuccess ? "PASS" : "FAIL"));
        
        assertTrue("System integration test passed", mysqlSuccess && redisSuccess);
    }
}