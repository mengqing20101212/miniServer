import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class test_db_connection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/pick_money";
        String username = "root";
        String password = "Ly.1006897725@MySQL";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL database successfully!");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            
            if(rs.next()){
                System.out.println("MySQL Server Version: " + rs.getString(1));
            }
            
            // 测试插入一条数据到share_enum_config表
            stmt.executeUpdate("INSERT INTO share_enum_config (code, name, config_desc) VALUES ('TEST_CODE', 'Test Name', 'Test Description') ON DUPLICATE KEY UPDATE name='Updated Test Name'");
            System.out.println("Test data inserted successfully!");
            
            // 查询刚插入的数据
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM share_enum_config WHERE code='TEST_CODE'");
            if(rs2.next()){
                System.out.println("Retrieved test data - ID: " + rs2.getInt("id") + ", Code: " + rs2.getString("code") + ", Name: " + rs2.getString("name"));
            }
            
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}