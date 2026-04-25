package ly;

import org.slf4j.Logger;

/**
 * AbstractConfigManger 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public abstract class AbstractConfigManger {

  /**
   * 加载策划表
   *
   * @param configDir 策划表目录
   * @throw 加载失败
   */
  protected abstract void reload(Logger logger, String configDir) throws ConfigLoadException;

  /**
   * 策划表名称
   *
   * @return 策划表的名称
   */
  public abstract String getConfigFileName();

  protected abstract void clear();

  protected abstract void afterLoad();
}
