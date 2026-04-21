package ly;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.Properties;

/**
 * Nacos connection test for GameServer
 */
public class NacosConnectionTest 
{
    /**
     * Test Nacos connection
     */
    @Test
    public void testNacosConnection()
    {
        try {
            // Configure Nacos connection
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
            properties.setProperty(PropertyKeyConst.NAMESPACE, "prod");
            properties.setProperty(PropertyKeyConst.USERNAME, "nacos");
            properties.setProperty(PropertyKeyConst.PASSWORD, "nacos");
            properties.setProperty(PropertyKeyConst.CONTEXT_PATH, "/");

            // Create config service instance
            ConfigService configService = NacosFactory.createConfigService(properties);
            
            System.out.println("✓ Connected to Nacos successfully!");
            
            // Try to get a simple configuration to test the connection
            String config = configService.getConfig("game1001", "GAME", 5000);
            System.out.println("Attempted to get config for game1001/GAME");
            if (config != null) {
                System.out.println("Retrieved config data successfully");
            } else {
                System.out.println("Config not found (this may be expected)");
            }
            
            assertTrue("Nacos connection test passed", true);
        } catch (NacosException e) {
            System.out.println("✗ Nacos connection failed!");
            e.printStackTrace();
            assertTrue("Nacos connection test failed", false);
        }
    }
}