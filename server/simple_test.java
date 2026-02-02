import java.sql.*;
import redis.clients.jedis.Jedis;

public class simple_test {
    public static void main(String[] args) {
        System.out.println("=== 简单连接测试 ===");
        
        // 测试 MySQL 连接
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        System.out.println("1. 测试 MySQL 连接...");
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("   ✓ MySQL 连接成功！");
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM player");
            if (rs.next()) {
                System.out.println("   ✓ player 表记录数: " + rs.getInt("count"));
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("   ✗ MySQL 连接失败: " + e.getMessage());
        }
        
        System.out.println("\n2. 测试 Redis 连接...");
        try {
            Jedis jedis = new Jedis("localhost", 6379);
            String testKey = "simple_test_" + System.currentTimeMillis();
            jedis.setex(testKey, 60, "test_value");
            String value = jedis.get(testKey);
            if ("test_value".equals(value)) {
                System.out.println("   ✓ Redis 连接成功！");
            } else {
                System.out.println("   ✗ Redis 连接失败");
            }
            jedis.del(testKey);
            jedis.close();
        } catch (Exception e) {
            System.out.println("   ✗ Redis 连接失败: " + e.getMessage());
        }
        
        System.out.println("\n=== 测试完成 ===");
    }
}