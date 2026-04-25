package ly;

import org.slf4j.Logger;

/**
 * 配置管理器代理接口，用于统一触发配置加载和开关判断。
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
