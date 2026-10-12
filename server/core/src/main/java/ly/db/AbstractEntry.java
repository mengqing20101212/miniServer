package ly.db;

import java.io.Serializable;

/** Entity versions describe RocksDB acceptance, not MySQL commit. */
public abstract class AbstractEntry implements Serializable {
  private static final long serialVersionUID = 1L;
  private long curVersion;
  private long saveVersion;
  private transient boolean managed;
  private transient boolean deleted;
  boolean canSave() { return curVersion != saveVersion; }
  protected void autoAddCurVersion() {
    if (deleted) { throw new IllegalStateException("Deleted entity cannot be modified"); }
    curVersion++;
  }
  protected final void checkPrimaryKeyChange(Object oldValue, Object newValue) {
    if (managed && !java.util.Objects.equals(oldValue, newValue)) {
      throw new IllegalStateException("Managed entity primary key is immutable");
    }
  }
  protected final void initDirtyState(int ignored) {}
  protected abstract String[] allDirtyFieldNames();
  protected final void markFieldDirty(int ignored) {
    if (managed && MysqlService.getInstance().isRocksEnabled()) { PersistenceScope.changed(this); }
  }
  /** Compatibility for callers; persistence always snapshots every mapped column. */
  public final String[] getDirtyFieldNames() {
    if (canSave()) { return allDirtyFieldNames().clone(); }
    return new String[0];
  }
  final long changeVersion() { return curVersion; }
  final void markAccepted(long version) {
    saveVersion = version;
    managed = true;
    if (!canSave()) { PersistenceScope.accepted(this); }
  }
  final void markDeleted() { deleted = true; managed = false; PersistenceScope.accepted(this); }
  public final void markPersisted() { markAccepted(curVersion); }
}
