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
import ly.rpc.RpcService;
import ly.security.SecurityBanService;
import ly.startup.StartupSkillLoader;
import ly.db.AutoTableService;
import org.slf4j.Logger;

/**
 * 服务器全局上下文，串联配置、Nacos、Redis、数据库、网络和控制器注册流程。
 * <p>
 * 各业务服的 main 方法通常只负责解析启动参数、注册控制器，然后调用这里的
 * {@link #startUp(String, String, String, String, GameObjectProvider)}。启动完成后，
 * {@link #serverConfig}、{@link #serverType}、{@link #SERVER_ID} 等静态字段会作为
 * 其他核心服务的全局读取入口。
 */
public class ServerContext {
    private static final Logger logger = ly.LoggerDef.SystemLogger;
    public static RunModuleEnum runModule;
    public static ServerConfig serverConfig;
    public static ServerTypeEnum serverType;
    public static String SERVER_ID;
    public static String ENV;

    /**
     * 按固定顺序启动一台服务器节点。
     * <p>
     * 启动顺序不能随意调整：Nacos 会先拉取服务器配置并回调
     * {@link #setServerConfig(ServerConfig)}，随后才能初始化配置表、Redis、MySQL 和
     * Netty 监听端口。{@code gameObjectProvider} 决定新连接创建哪一种
     * {@link ConnectSession} 子类，因此网关服、游戏服会传入自己的 Provider。
     *
     * @param nacosUrl Nacos 地址，例如 {@code localhost:8848}
     * @param serverTypeStr 服务器类型字符串，对应 {@link ServerTypeEnum#getType()}
     * @param serverId 当前节点唯一 id
     * @param env Nacos namespace，同时也作为服务分组
     * @param gameObjectProvider 连接会话工厂
     */
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
        
        // 服务器唯一 id 会写入后续注册到 Nacos 的节点元数据。
        SERVER_ID = serverId;
        ENV = env;
        
        // Nacos 启动会加载当前 serverId 对应的 ServerConfig，并注册服务发现实例。
        NacosService.getInstance().startUp(nacosUrl, serverType, serverId, env);
        
        // 检查 serverConfig 是否成功初始化
        if (serverConfig == null) {
            throw new RuntimeException("服务器配置未能成功加载");
        }
        
        // 配置校验通过后再初始化外部依赖，避免以错误端口或错误数据库参数启动。
        StartupSkillLoader.validateServerConfig(serverType, serverConfig);
        ConfigService.getInstance().loadAllConfig(logger, serverConfig.configPath);
        RedisUtils.init();
        MysqlService.getInstance().init(serverConfig.db.jdbcUrl, serverConfig.db.userName, serverConfig.db.passWord, 0, 0, 0, 0);
        
        // 启动自动建表服务
        AutoTableService.getInstance().startAutoTableService();

        // 表结构补齐后加载封禁数据，运行期拦截只读 Redis，避免每个客户端包打到 MySQL。
        SecurityBanService.getInstance().loadActiveBansFromDb();
        
        NetService.getInstance().startUp(gameObjectProvider, serverConfig.serverPort);
        // 当前服务器端口绑定完成后再补发可靠 RPC，避免目标服处理后回包找不到本服连接。
        RpcService.getInstance().replayReliableMessagesOnStartup();
        logger.info("服务器 启动成功 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    /**
     * 使用默认 {@link ConnectSession} 的启动入口，主要用于测试或不区分连接类型的服务。
     */
    public static void startUp(String nacosUrl, String serverTypeStr, String serverId, String env) {
        startUp(nacosUrl, serverTypeStr, serverId, env, new GameObjectProvider() {

            @Override
            public ConnectSession createGameObject(ChannelHandlerContext ctx) {
                return new ConnectSession(1);
            }
        });
    }

    /**
     * 写入当前节点的服务器配置。
     * <p>
     * 该方法通常由 {@link NacosService} 在读取 Nacos 配置后调用。业务代码不要在运行中
     * 随意覆盖这里的配置，否则 Redis/MySQL/Netty 等已经初始化的服务不会自动跟随重建。
     */
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

    /**
     * 注册协议控制器。
     * <p>
     * 控制器的 {@link IController#registerHandlerRouter()} 会把 CMD 与处理器绑定到路由表；
     * 因此必须在 {@link #startUp(String, String, String, String, GameObjectProvider)}
     * 之前调用，保证连接收到消息时路由已经存在。
     */
    public static void addController(IController... controllers) {
        for (IController controller : controllers) {
            controller.registerHandlerRouter();
        }
    }
}
