import redis.clients.jedis.Jedis;

public class RedisTest {
    public static void main(String[] args) {
        try {
            // 连接到本地Redis服务器
            Jedis jedis = new Jedis("localhost", 6379);
            System.out.println("Connected to Redis successfully!");
            
            // 测试存储和读取数据
            jedis.set("test_key", "Hello Redis!");
            String value = jedis.get("test_key");
            System.out.println("Retrieved value: " + value);
            
            // 测试其他操作
            jedis.lpush("test_list", "item1", "item2", "item3");
            System.out.println("List length: " + jedis.llen("test_list"));
            
            // 设置一个有过期时间的键
            jedis.setex("temp_key", 60, "Temporary value");
            System.out.println("Temp key TTL: " + jedis.ttl("temp_key"));
            
            // 关闭连接
            jedis.close();
            System.out.println("Redis connection closed.");
        } catch (Exception e) {
            System.out.println("Redis connection failed!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}