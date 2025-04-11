package ly;

import ly.config.RunModuleEnum;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosService;
import org.slf4j.Logger;

public class ServerContext {
  private static final Logger logger = ly.LoggerDef.SystemLogger;
  public static RunModuleEnum runModule;
  public static ServerConfig serverConfig;
  public static ServerTypeEnum serverType;
  public static String SERVER_ID;
  public static String ENV;

  public static void startUp(String nacosUrl, String serverTypeStr, String serverId, String env) {
    long startTime = System.currentTimeMillis();
    logger.info("服务器开始启动");
    serverType = ServerTypeEnum.getByType(serverTypeStr);
    /** 服务器唯一id */
    SERVER_ID = serverId;
    ENV = env;
    // 初始化 nacos 连接
    NacosService.getInstance().startUp(nacosUrl, serverType, serverId, env);
    // 加载策划表
    ConfigService.getInstance().loadAllConfig(logger, serverConfig.configPath);

    logger.info("服务器 启动成功 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
  }

  public static void setServerConfig(ServerConfig newServerConfig) {
    runModule = RunModuleEnum.getRunModuleEnum(serverConfig.runModule);
    serverConfig = newServerConfig;
    serverConfig.setServerId(SERVER_ID);
  }

  public static String getServerId() {
    return SERVER_ID;
  }
}
