package ly;

import io.netty.channel.ChannelHandlerContext;
import ly.config.RunModuleEnum;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import ly.db.MysqlService;
import ly.monitor.DeadlockDetector;
import ly.nacos.NacosService;
import ly.net.ConnectSession;
import ly.net.GameObjectProvider;
import ly.net.IController;
import ly.net.NetService;
import ly.redis.RedisUtils;
import ly.rpc.RpcService;
import ly.security.SecurityBanService;
import ly.script.RuntimeScriptController;
import ly.startup.StartupSkillLoader;
import ly.db.AutoTableService;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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

        // Every server receives the same one-shot emergency script route from Core.
        addController(new RuntimeScriptController());

        // 尽早启动 JVM 死锁检测器，覆盖后续 Nacos、配置表、Redis、MySQL、Netty 等所有线程。
        DeadlockDetector.start();
        
        // Nacos 启动会加载当前 serverId 对应的 ServerConfig，并注册服务发现实例。
        NacosService.getInstance().startUp(nacosUrl, serverType, serverId, env);
        
        // 检查 serverConfig 是否成功初始化
        if (serverConfig == null) {
            throw new RuntimeException("服务器配置未能成功加载");
        }
        
        // 配置校验通过后再初始化外部依赖，避免以错误端口或错误数据库参数启动。
        StartupSkillLoader.validateServerConfig(serverType, serverConfig);
        // 标准化路径分隔符，兼容 Nacos 中使用 Windows 路径格式（D:\WORK\...）在 Linux 下运行
        String configPath = resolveConfigPath(serverConfig.configPath);
        serverConfig.configPath = configPath;
        initCoreDependencies(configPath);
        
        // 启动自动建表服务
        AutoTableService.getInstance().startAutoTableService();

        // 表结构补齐后加载封禁数据，运行期拦截只读 Redis，避免每个客户端包打到 MySQL。
        SecurityBanService.getInstance().loadActiveBansFromDb();
        
        NetService.getInstance().startUp(gameObjectProvider, serverConfig.serverPort);
        // 当前服务器端口绑定完成后再补发可靠 RPC，避免目标服处理后回包找不到本服连接。
        RpcService.getInstance().replayReliableMessagesOnStartup();
        if (StartupSkillLoader.isLocalConfigMode()) {
            logger.info("LOCAL_CONFIG 模式已启用，配置目录={}，不监听 GM 配置热更", configPath);
        } else {
            try {
                NacosService.getInstance().startConfigHotUpdateListener();
            } catch (Exception e) {
                logger.error("配置热更监听启动失败", e);
                throw new RuntimeException("配置热更监听启动失败", e);
            }
        }
        logger.info("服务器 启动成功 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static String resolveConfigPath(String configuredPath) {
        if (configuredPath == null || configuredPath.isBlank()) {
            throw new IllegalStateException("服务器配置 configPath 不能为空");
        }
        String resolved = configuredPath;
        if (resolved.contains("${PROJECT_ROOT}")) {
            if (!StartupSkillLoader.isLocalConfigMode()) {
                throw new IllegalStateException("${PROJECT_ROOT} 只能在 LOCAL_CONFIG 模式使用");
            }
            String projectRoot = System.getProperty(StartupSkillLoader.PROJECT_ROOT_PROPERTY);
            if (projectRoot == null || projectRoot.isBlank()) {
                throw new IllegalStateException("LOCAL_CONFIG 模式缺少 -Dminiserver.projectRoot");
            }
            resolved = resolved.replace("${PROJECT_ROOT}", Path.of(projectRoot).toAbsolutePath().normalize().toString());
        }
        return Path.of(resolved.replace('\\', '/')).toAbsolutePath().normalize().toString();
    }

    /**
     * 并发初始化 Nacos 之后才能启动的核心依赖。
     *
     * <p>
     * 配置表、Redis、MySQL 互相没有启动顺序依赖，串行启动会把 IO 等待时间叠加。
     * 这里使用虚拟线程并发执行，任何一个任务失败都会取消剩余任务并中断启动，
     * 防止服务带着半初始化状态继续绑定端口。
     * </p>
     */
    private static void initCoreDependencies(String configPath) {
        List<StartupTask> tasks = List.of(
                new StartupTask("配置表加载", () -> ConfigService.getInstance().loadAllConfig(logger, configPath)),
                new StartupTask("Redis 初始化", RedisUtils::init),
                new StartupTask(
                        "MySQL 初始化",
                        () -> MysqlService.getInstance()
                                .init(
                                        serverConfig.db.jdbcUrl,
                                        serverConfig.db.userName,
                                        serverConfig.db.passWord,
                                        0,
                                        0,
                                        0,
                                        0)));
        List<Future<?>> futures = new ArrayList<>(tasks.size());
        long startTime = System.currentTimeMillis();
        try (ExecutorService executor =
                Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("startup-init-", 0).factory())) {
            for (StartupTask task : tasks) {
                futures.add(
                        executor.submit(
                                () -> {
                                    runStartupTask(task);
                                    return null;
                                }));
            }
            for (int i = 0; i < futures.size(); i++) {
                waitStartupTask(tasks.get(i), futures.get(i), futures);
            }
        }
        logger.info("核心依赖并发初始化完成, cost={}ms", System.currentTimeMillis() - startTime);
    }

    private static void runStartupTask(StartupTask task) throws Exception {
        long begin = System.currentTimeMillis();
        logger.info("开始{}", task.name());
        task.action().run();
        logger.info("{}完成, cost={}ms", task.name(), System.currentTimeMillis() - begin);
    }

    private static void waitStartupTask(StartupTask task, Future<?> future, List<Future<?>> allFutures) {
        try {
            future.get();
        } catch (InterruptedException e) {
            cancelStartupTasks(allFutures);
            Thread.currentThread().interrupt();
            throw new RuntimeException("服务器启动被中断:" + task.name(), e);
        } catch (ExecutionException e) {
            cancelStartupTasks(allFutures);
            Throwable cause = e.getCause() == null ? e : e.getCause();
            logger.error("服务器启动依赖初始化失败, task={}", task.name(), cause);
            throw new RuntimeException("服务器启动依赖初始化失败:" + task.name(), cause);
        }
    }

    private static void cancelStartupTasks(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
    }

    private record StartupTask(String name, StartupAction action) {}

    @FunctionalInterface
    private interface StartupAction {
        void run() throws Exception;
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
