import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class test_remote_mysql {
    public static void main(String[] args) {
        String url = "jdbc:mysql://139.224.80.204:3306/pick_money";
        String username = "root";
        String password = "ly.1006897725";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to remote MySQL database successfully!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            
            if(rs.next()){
                System.out.println("Remote MySQL Server Version: " + rs.getString(1));
            }
            
            // 测试插入一条数据到share_enum_config表
            stmt.executeUpdate("INSERT INTO share_enum_config (code, name, config_desc) VALUES ('REMOTE_TEST', 'Remote Test', 'Remote Connection Test') ON DUPLICATE KEY UPDATE name='Updated Remote Test'");
            System.out.println("Test data inserted successfully!");
            
            // 查询刚插入的数据
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM share_enum_config WHERE code='REMOTE_TEST'");
            if(rs2.next()){
                System.out.println("Retrieved test data - ID: " + rs2.getInt("id") + ", Code: " + rs2.getString("code") + ", Name: " + rs2.getString("name"));
            }
            
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
