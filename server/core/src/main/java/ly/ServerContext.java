package ly;

import ly.config.ActivityInfoConfigManager;
import org.apache.logging.log4j.core.Logger;

public class ServerContext {
  private static final Logger logger = ly.LoggerDef.SystemLogger;

  public static void startUp() {
    long startTime = System.currentTimeMillis();
    logger.info("服务器开始启动");
    ConfigService.getInstance().loadAllConfig(logger, "server");
    ActivityInfoConfigManager.getInstance();

    logger.info("服务器 启动成功 耗时: " + (System.currentTimeMillis() - startTime) + "ms");
  }
}
