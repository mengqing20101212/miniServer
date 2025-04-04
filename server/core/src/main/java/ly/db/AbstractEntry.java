package ly.db;

import java.io.Serializable;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: AbstractEntry
 */
public abstract class AbstractEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 当前数据的版本 */
  private int curVersion;

  /** 已经入库的数据版本 */
  private int saveVersion;

  /**
   * 当前数据是否需要保存，数据没变的情况下 不保存的
   *
   * @return true 需要保存；false 不需要保存
   */
  boolean canSave() {
    if (curVersion != saveVersion) {
      saveVersion = curVersion;
      return true;
    }
    return false;
  }

  protected void autoAddCurVersion() {
    curVersion++;
  }
}
