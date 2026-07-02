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

  /** 加载备用 A/B 版本，加载成功前不能影响当前正在使用的配置。 */
  default void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {
    loadConfig(logger, configDir);
  }

  /** 切换到备用版本，并返回切换前正在使用的旧对象引用，供延迟释放。 */
  default AbstractConfigManger switchConfig() {
    return null;
  }

  /** 当前管理器对应的配置文件名。 */
  default String getConfigFileName() {
    return "";
  }
}
