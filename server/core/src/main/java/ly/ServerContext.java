package ly;

import io.netty.channel.ChannelHandlerContext;
import ly.config.RunModuleEnum;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import ly.db.MysqlService;
import ly.nacos.NacosService;
import ly.net.ConnectSession;
import ly.net.GameObjectProvider;
import ly.net.IController;
import ly.net.NetService;
import ly.redis.RedisUtils;
import ly.startup.StartupSkillLoader;
import ly.db.AutoTableService;
import org.slf4j.Logger;

public class ServerContext {
    private static final Logger logger = ly.LoggerDef.SystemLogger;
    public static RunModuleEnum runModule;
    public static ServerConfig serverConfig;
    public static ServerTypeEnum serverType;
    public static String SERVER_ID;
    public static String ENV;

    public static void startUp(String nacosUrl, String serverTypeStr, String serverId, String env, GameObjectProvider gameObjectProvider) {
        if (nacosUrl == null || nacosUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("nacosUrl 不能为空");
        }
        if (serverTypeStr == null || serverTypeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("serverTypeStr 不能为空");
        }
        if (serverId == null || serverId.trim().isEmpty()) {
            throw new IllegalArgumentException("serverId 不能为空");
        }
        if (env == null || env.trim().isEmpty()) {
            throw new IllegalArgumentException("env 不能为空");
        }
        if (gameObjectProvider == null) {
            throw new IllegalArgumentException("gameObjectProvider 不能为空");
        }
        
        long startTime = System.currentTimeMillis();
        logger.info("服务器开始启动");
        serverType = ServerTypeEnum.getByType(serverTypeStr);
        if (serverType == null) {
            throw new IllegalArgumentException("无效的服务器类型: " + serverTypeStr);
        }
        
        /** 服务器唯一id */
        SERVER_ID = serverId;
        ENV = env;
        
        // 初始化 nacos 连接
        NacosService.getInstance().startUp(nacosUrl, serverType, serverId, env);
        
        // 检查 serverConfig 是否成功初始化
        if (serverConfig == null) {
            throw new RuntimeException("服务器配置未能成功加载");
        }
        
        // 加载策划表
        StartupSkillLoader.validateServerConfig(serverType, serverConfig);
        ConfigService.getInstance().loadAllConfig(logger, serverConfig.configPath);
        RedisUtils.init();
        MysqlService.getInstance().init(serverConfig.db.jdbcUrl, serverConfig.db.userName, serverConfig.db.passWord, 0, 0, 0, 0);
        
        // 启动自动建表服务
        AutoTableService.getInstance().startAutoTableService();
        
        NetService.getInstance().startUp(gameObjectProvider, serverConfig.serverPort);
        logger.info("服务器 启动成功 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    public static void startUp(String nacosUrl, String serverTypeStr, String serverId, String env) {
        startUp(nacosUrl, serverTypeStr, serverId, env, new GameObjectProvider() {

            @Override
            public ConnectSession createGameObject(ChannelHandlerContext ctx) {
                return new ConnectSession(1);
            }
        });
    }

    public static void setServerConfig(ServerConfig newServerConfig) {
        if (newServerConfig == null) {
            logger.error("setServerConfig: newServerConfig 不能为 null");
            return;
        }
        if (SERVER_ID == null) {
            logger.warn("SERVER_ID 未设置，可能影响服务器配置初始化");
        }
        
        serverConfig = newServerConfig;
        runModule = RunModuleEnum.getRunModuleEnum(serverConfig.runModule);
        serverConfig.setServerId(SERVER_ID);
    }

    public static String getServerId() {
        return SERVER_ID;
    }

    public static void addController(IController... controllers) {
        for (IController controller : controllers) {
            controller.registerHandlerRouter();
        }
    }
}
