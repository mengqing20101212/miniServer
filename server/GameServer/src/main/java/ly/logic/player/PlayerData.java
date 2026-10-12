package ly.logic.player;

import ly.db.AbstractEntry;
import ly.db.PersistenceScope;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerHeroEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.db.entry.PlayerResourceEntry;
import ly.db.player.MysqlPlayerPersistenceStore;
import ly.db.player.PlayerPersistenceStore;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 由玩家执行线程收集两种存储的变化，并同步提交至 RocksDB。 */
public class PlayerData {
    private final PlayerEntry playerEntry;
    private final PlayerPersistenceStore persistenceStore;
    private final Map<ModuleEnum, AbstractModule> modules = new EnumMap<>(ModuleEnum.class);
    private final Set<AbstractEntry> dirtyEntities =
            Collections.newSetFromMap(new IdentityHashMap<>());
    private final Set<AbstractEntry> deletedEntities =
            Collections.newSetFromMap(new IdentityHashMap<>());

    public PlayerData(PlayerEntry entry) {
        this(entry, new MysqlPlayerPersistenceStore());
    }

    public PlayerData(PlayerEntry entry, PlayerPersistenceStore persistenceStore) {
        if (entry == null || persistenceStore == null) {
            throw new IllegalArgumentException("entry and persistenceStore required");
        }
        this.playerEntry = entry;
        this.persistenceStore = persistenceStore;
        entry.bindPersistenceChangeListener(() -> changed(entry));
    }

    Map<ModuleEnum, PlayerModuleEntry> loadModuleEntries() {
        Map<ModuleEnum, PlayerModuleEntry> result = new EnumMap<>(ModuleEnum.class);
        for (PlayerModuleEntry entry : persistenceStore.loadModuleEntries(getPlayerId())) {
            requireOwner(entry.getPlayerId());
            ModuleEnum type = ModuleEnum.fromModuleId(entry.getModuleId());
            if (type != null && type != ModuleEnum.RESOURCE_MODULE) {
                entry.markPersisted();
                result.put(type, entry);
            }
        }
        return result;
    }

    public List<PlayerHeroEntry> loadHeroEntries() {
        List<PlayerHeroEntry> entries = persistenceStore.loadHeroEntries(getPlayerId());
        for (PlayerHeroEntry entry : entries) {
            requireOwner(entry.getPlayerId());
        }
        return entries;
    }

    public List<PlayerResourceEntry> loadResourceEntries() {
        List<PlayerResourceEntry> entries = persistenceStore.loadResourceEntries(getPlayerId());
        for (PlayerResourceEntry entry : entries) {
            requireOwner(entry.getPlayerId());
        }
        return entries;
    }

    private void requireOwner(Long id) {
        if (id == null || id != getPlayerId()) {
            throw new IllegalStateException("player entity owner mismatch");
        }
    }

    public void registerLoadedEntity(AbstractEntry entry) {
        entry.markPersisted();
        entry.bindPersistenceChangeListener(() -> changed(entry));
    }

    public void registerNewEntity(AbstractEntry entry) {
        entry.bindPersistenceChangeListener(() -> changed(entry));
        changed(entry);
    }

    private void changed(AbstractEntry entry) {
        if (deletedEntities.contains(entry)) {
            throw new IllegalStateException("deletedEntities player entry cannot change");
        }
        dirtyEntities.add(entry);
        schedulePersistenceFlush();
    }

    public void registerDeletedEntity(AbstractEntry entry) {
        entry.bindPersistenceChangeListener(
                () -> {
                    throw new IllegalStateException("deletedEntities player entry cannot change");
                });
        dirtyEntities.remove(entry);
        deletedEntities.add(entry);
        schedulePersistenceFlush();
    }

    public void schedulePersistenceFlush() {
        PersistenceScope.registerSnapshotPreparation(this, this::persistPendingChanges);
    }

    PlayerModuleEntry createModuleEntry(ModuleEnum type) {
        return new PlayerModuleEntry(
                null,
                getPlayerId(),
                type.getModuleId(),
                type.getDataVersion(),
                0L,
                new byte[0],
                LocalDateTime.now());
    }

    public void putModule(ModuleEnum type, AbstractModule module) {
        modules.put(type, module);
    }

    public AbstractModule getModule(ModuleEnum type) {
        return modules.get(type);
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }

    public long generateEntityId() {
        return persistenceStore.generateEntityId();
    }

    public long getPlayerId() {
        return playerEntry.getId();
    }

    /** 返回前已经由持久化层接收；MySQL 提交仍由 RocksService 后台完成。 */
    public boolean flushPendingChanges() {
        persistPendingChanges();
        return true;
    }

    private void persistPendingChanges() {
        java.util.List<AbstractEntry> saves = new java.util.ArrayList<>(dirtyEntities);
        java.util.List<AbstractEntry> deletes = new java.util.ArrayList<>(deletedEntities);
        java.util.List<AbstractModule> blobs = new java.util.ArrayList<>();
        for (AbstractModule module : modules.values()) {
            PlayerModuleEntry entry = module.prepareDirtyModuleBlob();
            if (entry != null) {
                saves.add(entry);
                blobs.add(module);
            }
        }
        if (saves.isEmpty() && deletes.isEmpty()) return;
        if (!persistenceStore.acceptBatch(saves, deletes))
            throw new IllegalStateException("玩家保存批次未被持久化层接收");
        for (AbstractEntry entry : saves) entry.markPersisted();
        for (AbstractModule module : blobs) module.acceptPreparedModuleBlob();
        dirtyEntities.removeAll(saves);
        deletedEntities.removeAll(deletes);
    }
}
