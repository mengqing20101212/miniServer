package ly.db;

import java.io.Serializable;
import ly.utils.BitSwitchState;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: AbstractEntry
 */
public abstract class AbstractEntry implements Serializable {
  private static final long serialVersionUID = 1L;

  /** 当前数据版本 */
  private int curVersion;

  /** 已保存的数据版本 */
  private int saveVersion;

  private transient BitSwitchState dirtyState;

  /**
   * 当前数据是否需要保存
   *
   * @return true 需要保存；false 不需要保存
   */
  boolean canSave() {
    return curVersion != saveVersion;
  }

  protected void autoAddCurVersion() {
    curVersion++;
  }

  protected final void initDirtyState(int fieldCount) {
    this.dirtyState = fieldCount <= 0 ? null : BitSwitchState.of(fieldCount);
  }

  protected abstract String[] allDirtyFieldNames();

  protected final void markFieldDirty(int fieldIndex) {
    if (dirtyState == null) {
      return;
    }
    dirtyState.enable(fieldIndex);
  }

  public final String[] getDirtyFieldNames() {
    String[] dirtyFieldNames = allDirtyFieldNames();
    if (dirtyState == null || dirtyFieldNames == null || dirtyFieldNames.length == 0) {
      return new String[0];
    }
    int size = 0;
    for (int i = 0; i < dirtyFieldNames.length; i++) {
      if (dirtyState.isEnabled(i)) {
        size++;
      }
    }
    String[] result = new String[size];
    int index = 0;
    for (int i = 0; i < dirtyFieldNames.length; i++) {
      if (dirtyState.isEnabled(i)) {
        result[index++] = dirtyFieldNames[i];
      }
    }
    return result;
  }

  public final void markPersisted() {
    saveVersion = curVersion;
    if (dirtyState != null) {
      dirtyState.clear();
    }
  }
}
