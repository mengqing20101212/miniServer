import java.sql.*;

public class test_real_mysql {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";
        
        System.out.println("正在连接到远程MySQL数据库: " + jdbcUrl);
        
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            
            System.out.println("数据库连接成功！");
            
            // 测试查询
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM player");
            
            if (rs.next()) {
                int count = rs.getInt("count");
                System.out.println("player表中的记录数: " + count);
            }
            
            rs.close();
            stmt.close();
            connection.close();
            
            System.out.println("测试完成");
        } catch (SQLException e) {
            System.out.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}