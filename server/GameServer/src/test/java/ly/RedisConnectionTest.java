package ly;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis connection test for GameServer
 */
public class RedisConnectionTest 
{
    /**
     * Test Redis connection
     */
    @Test
    public void testRedisConnection()
    {
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
                System.out.println("Connected to Redis successfully! Ping response: " + pong);
                
                // 测试基本操作
                String testKey = "test_key_" + System.currentTimeMillis();
                String testValue = "test_value";
                
                jedis.set(testKey, testValue);
                String retrievedValue = jedis.get(testKey);
                
                System.out.println("Set key '" + testKey + "' with value: " + testValue);
                System.out.println("Retrieved value: " + retrievedValue);
                
                assertTrue("Values should match", testValue.equals(retrievedValue));
                
                // 清理测试数据
                jedis.del(testKey);
                System.out.println("Test data cleaned up.");
                
                assertTrue("Redis connection test passed", true);
            }
        } catch (Exception e) {
            System.out.println("Redis connection failed!");
            e.printStackTrace();
            assertTrue("Redis connection test failed", false);
        }
    }
}