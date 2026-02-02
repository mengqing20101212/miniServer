import redis.clients.jedis.Jedis;

public class test_redis_remote {
    public static void main(String[] args) {
        System.out.println("测试连接到远程Redis服务器: 139.224.80.204:6379");
        
        try {
            // 尝试连接到远程Redis服务器
            Jedis jedis = new Jedis("139.224.80.204", 6379);
            jedis.auth("ly.1006897725"); // 使用密码认证
            
            String testKey = "test_connection_" + System.currentTimeMillis();
            jedis.setex(testKey, 60, "test_value");
            String value = jedis.get(testKey);
            
            if ("test_value".equals(value)) {
                System.out.println("✓ 远程Redis连接成功！");
                System.out.println("  测试键值对操作: OK");
                
                // 清理测试数据
                jedis.del(testKey);
            } else {
                System.out.println("✗ 远程Redis连接失败 - 键值对操作异常");
            }
            
            jedis.close();
        } catch (Exception e) {
            System.out.println("✗ 远程Redis连接失败: " + e.getMessage());
            System.out.println("  尝试连接到本地Redis...");
            
            try {
                // 尝试连接到本地Redis
                Jedis localJedis = new Jedis("localhost", 6379);
                
                String testKey = "local_test_connection_" + System.currentTimeMillis();
                localJedis.setex(testKey, 60, "local_test_value");
                String value = localJedis.get(testKey);
                
                if ("local_test_value".equals(value)) {
                    System.out.println("✓ 本地Redis连接成功！");
                    System.out.println("  测试键值对操作: OK");
                    
                    // 清理测试数据
                    localJedis.del(testKey);
                } else {
                    System.out.println("✗ 本地Redis连接失败 - 键值对操作异常");
                }
                
                localJedis.close();
            } catch (Exception localEx) {
                System.out.println("✗ 本地Redis也连接失败: " + localEx.getMessage());
            }
        }
        
        System.out.println("Redis连接测试完成");
    }
}