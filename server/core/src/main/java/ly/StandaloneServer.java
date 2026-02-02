package ly;

import ly.config.DbConfig;
import ly.config.RedisConfig;
import ly.config.ServerConfig;
import ly.db.MysqlService;
import ly.redis.RedisUtils;

public class StandaloneServer {
    public static void main(String[] args) {
        System.out.println("启动独立服务器模式...");
        
        // 创建服务器配置
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setServerId("standalone-server");
        serverConfig.setServerIp("127.0.0.1");
        serverConfig.setServerPort(9001);
        serverConfig.setConfigPath("./config");
        serverConfig.setRunModule("PROD");

        // 配置数据库
        DbConfig dbConfig = new DbConfig();
        dbConfig.setJdbcUrl("jdbc:mysql://139.224.80.204:3306/pick_money");
        dbConfig.setUserName("root");
        dbConfig.setPassWord("ly.1006897725");
        dbConfig.setMaxPoolSize(20);
        dbConfig.setMinIdle(5);
        dbConfig.setIdleTimeout(30000);
        dbConfig.setConnectionTimeout(5000);
        serverConfig.setDb(dbConfig);

        // 配置Redis
        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setHost("139.224.80.204");
        redisConfig.setPort(6379);
        redisConfig.setPassword("ly.1006897725");
        serverConfig.setRedis(redisConfig);

        // 设置全局服务器配置
        ServerContext.serverConfig = serverConfig;
        ServerContext.ENV = "prod";
        ServerContext.serverType = ly.config.ServerTypeEnum.GATE;

        try {
            // 初始化数据库服务
            System.out.println("初始化数据库服务...");
            MysqlService.getInstance().init(
                serverConfig.getDb().getJdbcUrl(),
                serverConfig.getDb().getUserName(),
                serverConfig.getDb().getPassWord(),
                serverConfig.getDb().getMaxPoolSize(),
                serverConfig.getDb().getMinIdle(),
                serverConfig.getDb().getIdleTimeout(),
                serverConfig.getDb().getConnectionTimeout()
            );
            
            // 初始化Redis服务
            System.out.println("初始化Redis服务...");
            RedisUtils.init();

            System.out.println("服务器启动成功！");
            System.out.println("数据库连接状态: " + (MysqlService.getInstance().getMysqlConnector() != null ? "已连接" : "未连接"));
            System.out.println("Redis连接状态: " + (RedisUtils.redissonClient != null ? "已连接" : "未连接"));

            // 保持服务器运行
            System.out.println("服务器正在运行，按 Ctrl+C 停止...");
            Thread.currentThread().join();
            
        } catch (Exception e) {
            System.err.println("服务器启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}