package ly;

import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/3/31
 * File: AbstractConfigManger
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
