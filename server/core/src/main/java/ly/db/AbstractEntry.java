package ly.db;

import java.io.Serializable;
import ly.utils.BitSwitchState;

/**
 * 所有数据库实体的基类。
 * <p>
 * 生成的 Entry setter 会在字段变化时递增版本号并标记脏字段。{@link MysqlService}
 * 根据版本号判断是否需要保存，并可根据脏字段列表生成局部 UPDATE。
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

  /** 字段发生业务变更时递增当前版本，生成代码通常在 setter 中调用。 */
  protected void autoAddCurVersion() {
    curVersion++;
  }

  /**
   * 初始化脏字段位图。
   *
   * @param fieldCount 可跟踪的普通数据库字段数量，不包含自增主键
   */
  protected final void initDirtyState(int fieldCount) {
    this.dirtyState = fieldCount <= 0 ? null : BitSwitchState.of(fieldCount);
  }

  /** 返回字段索引到数据库列名的映射，由生成的 Entry 实现。 */
  protected abstract String[] allDirtyFieldNames();

  /** 标记指定字段索引已变更。 */
  protected final void markFieldDirty(int fieldIndex) {
    if (dirtyState == null) {
      return;
    }
    dirtyState.enable(fieldIndex);
  }

  /** 返回当前变更过的数据库列名，用于生成局部 UPDATE。 */
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

  /** 标记对象已经和数据库同步，并清空脏字段状态。 */
  public final void markPersisted() {
    saveVersion = curVersion;
    if (dirtyState != null) {
      dirtyState.clear();
    }
  }
}
