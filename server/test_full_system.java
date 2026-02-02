import java.sql.*;
import redis.clients.jedis.Jedis;

public class test_full_system {
    public static void main(String[] args) {
        System.out.println("=== 系统连接测试 ===");
        
        // 测试 MySQL 连接
        testMySQL();
        
        // 测试 Redis 连接
        testRedis();
        
        System.out.println("所有测试完成！");
    }
    
    private static void testMySQL() {
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        System.out.println("\n--- 测试 MySQL 连接 ---");
        System.out.println("连接到: " + jdbcUrl);
        
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("✓ MySQL 连接成功！");
            
            // 测试查询多个表
            Statement stmt = connection.createStatement();
            
            // 检查 player 表
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM player");
            if (rs.next()) {
                System.out.println("  player 表记录数: " + rs.getInt("count"));
            }
            rs.close();
            
            // 检查 login 表
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM login");
            if (rs.next()) {
                System.out.println("  login 表记录数: " + rs.getInt("count"));
            }
            rs.close();
            
            // 检查 share_enum_config 表
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM share_enum_config");
            if (rs.next()) {
                System.out.println("  share_enum_config 表记录数: " + rs.getInt("count"));
            }
            rs.close();
            
            // 检查 share_daily 表
            rs = stmt.executeQuery("SELECT COUNT(*) as count FROM share_daily");
            if (rs.next()) {
                System.out.println("  share_daily 表记录数: " + rs.getInt("count"));
            }
            rs.close();
            
            stmt.close();
            connection.close();
            System.out.println("✓ MySQL 测试完成");
        } catch (SQLException e) {
            System.out.println("✗ MySQL 连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testRedis() {
        System.out.println("\n--- 测试 Redis 连接 ---");
        System.out.println("连接到: localhost:6379");
        
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            jedis.select(0); // 选择数据库 0
            
            // 测试基本操作
            String testKey = "test_key_" + System.currentTimeMillis();
            String testValue = "test_value";
            
            jedis.set(testKey, testValue);
            String retrievedValue = jedis.get(testKey);
            
            if (testValue.equals(retrievedValue)) {
                System.out.println("✓ Redis 连接成功！");
                System.out.println("  测试键值对操作: OK");
            } else {
                System.out.println("✗ Redis 键值对操作失败");
            }
            
            // 清理测试数据
            jedis.del(testKey);
            
            // 测试列表操作
            String listKey = "test_list";
            jedis.lpush(listKey, "item1", "item2", "item3");
            Long listLength = jedis.llen(listKey);
            System.out.println("  列表长度测试: " + listLength);
            
            // 清理测试数据
            jedis.del(listKey);
            
            jedis.close();
            System.out.println("✓ Redis 测试完成");
        } catch (Exception e) {
            System.out.println("✗ Redis 连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}