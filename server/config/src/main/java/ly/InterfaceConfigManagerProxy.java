package ly;

import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/3/31
 * File: InterfaceConfigManager
 */
public interface InterfaceConfigManagerProxy {
  /**
   * 加载策划表
   *
   * @param configDir 策划表目录
   * @throw 加载失败
   */
  public abstract void loadConfig(Logger logger, String configDir) throws ConfigLoadException;
}
