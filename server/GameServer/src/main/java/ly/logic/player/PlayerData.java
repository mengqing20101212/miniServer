package ly.logic.player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.logic.player.persistence.PlayerModulePersistenceService;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class PlayerData {
    final PlayerEntry playerEntry;
    private final PlayerModulePersistenceService persistenceService;
    private final EnumSet<ModuleEnum> submittedModules = EnumSet.noneOf(ModuleEnum.class);
    private final Map<ModuleEnum, AbstractModule> modules = new EnumMap<>(ModuleEnum.class);

    public PlayerData(PlayerEntry playerEntry) {
        this(playerEntry, PlayerModulePersistenceService.getInstance());
    }

    public PlayerData(PlayerEntry playerEntry, PlayerModulePersistenceService persistenceService) {
        if (playerEntry == null || persistenceService == null) {
            throw new IllegalArgumentException("playerEntry and persistenceService are required");
        }
        this.playerEntry = playerEntry;
        this.persistenceService = persistenceService;
    }

    Map<ModuleEnum, PlayerModuleEntry> loadModuleEntries() {
        Map<ModuleEnum, PlayerModuleEntry> loadedEntries = new EnumMap<>(ModuleEnum.class);
        Map<Integer, PlayerModuleEntry> storedModules = persistenceService.load(playerEntry.getId());
        if (storedModules != null && !storedModules.isEmpty()) {
            loadStoredModules(storedModules, loadedEntries);
        } else {
            loadLegacyModules(loadedEntries);
        }
        return loadedEntries;
    }

    private void loadStoredModules(
            Map<Integer, PlayerModuleEntry> storedModules,
            Map<ModuleEnum, PlayerModuleEntry> loadedEntries) {
        for (PlayerModuleEntry entry : storedModules.values()) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(entry.getModuleId());
            if (moduleType == null) {
                continue;
            }
            entry.markPersisted();
            loadedEntries.put(moduleType, entry);
        }
    }

    private void loadLegacyModules(Map<ModuleEnum, PlayerModuleEntry> loadedEntries) {
        Codec<PlayerModuleData> moduleDataCodec = ProtobufProxy
                .create(PlayerModuleData.class);
        try {
            byte[] legacyBytes = playerEntry.getModules();
            if (legacyBytes == null || legacyBytes.length == 0) {
                return;
            }
            PlayerModuleData legacyData = moduleDataCodec.decode(legacyBytes);
            for (ModuleEnum moduleType : ModuleEnum.values()) {
                byte[] moduleBytes = legacyData.getModuleData(moduleType.getName());
                if (moduleBytes != null && moduleBytes.length > 0) {
                    loadedEntries.put(
                            moduleType,
                            new PlayerModuleEntry(
                                    null,
                                    getPlayerId(),
                                    moduleType.getModuleId(),
                                    moduleType.getDataVersion(),
                                    1L,
                                    moduleBytes,
                                    LocalDateTime.now()));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("decode legacy player modules failed, playerId=" + playerEntry.getId(), e);
        }
    }

    PlayerModuleEntry createModuleEntry(ModuleEnum moduleType) {
        return new PlayerModuleEntry(
                null,
                getPlayerId(),
                moduleType.getModuleId(),
                moduleType.getDataVersion(),
                0L,
                new byte[0],
                LocalDateTime.now());
    }

    public void putModule(ModuleEnum moduleType, AbstractModule module) {
        modules.put(moduleType, module);
    }

    public AbstractModule getModule(ModuleEnum moduleType) {
        return modules.get(moduleType);
    }

    public PlayerEntry getPlayerEntry() {
        return playerEntry;
    }

    /** 更新单个模块的内存快照并递增修订号，实际持久化由任务结束时的批量刷新完成。 */
    public synchronized void markModuleDirty(PlayerModuleEntry moduleEntry, byte[] moduleBytes) {
        if (moduleEntry == null || moduleBytes == null) {
            throw new IllegalArgumentException("moduleEntry and moduleBytes are required");
        }
        ModuleEnum moduleType = ModuleEnum.fromModuleId(moduleEntry.getModuleId());
        AbstractModule module = modules.get(moduleType);
        if (module == null || module.getModuleEntry() != moduleEntry) {
            throw new IllegalArgumentException("moduleEntry is not owned by this player");
        }
        moduleEntry.setDataVersion(moduleType.getDataVersion());
        moduleEntry.setRevision(moduleEntry.getRevision() + 1L);
        moduleEntry.setModuleData(moduleBytes);
        moduleEntry.setUpdateTime(LocalDateTime.now());
    }

    public boolean flushAsync() {
        List<PlayerModuleEntry> snapshots = prepareDirtyModuleSnapshots();
        if (snapshots.isEmpty()) {
            return true;
        }
        if (persistenceService.submit(this, snapshots)) {
            return true;
        }
        releaseSubmittedModules(snapshots);
        return false;
    }

    public synchronized List<PlayerModuleEntry> prepareDirtyModuleSnapshots() {
        List<PlayerModuleEntry> snapshots = new ArrayList<>();
        for (Map.Entry<ModuleEnum, AbstractModule> entry : modules.entrySet()) {
            ModuleEnum moduleType = entry.getKey();
            PlayerModuleEntry moduleEntry = entry.getValue().getModuleEntry();
            if (submittedModules.contains(moduleType)
                    || moduleEntry.getDirtyFieldNames().length == 0) {
                continue;
            }
            submittedModules.add(moduleType);
            snapshots.add(moduleEntry.snapshot());
        }
        return snapshots;
    }

    public synchronized void markModulesPersisted(List<PlayerModuleEntry> snapshots) {
        for (PlayerModuleEntry snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.getModuleId());
            AbstractModule module = modules.get(moduleType);
            PlayerModuleEntry entry = module == null ? null : module.getModuleEntry();
            if (entry != null) {
                if (entry.getId() == null && snapshot.getId() != null) {
                    entry.setId(snapshot.getId());
                }
                if (entry.getRevision().equals(snapshot.getRevision())) {
                    entry.markPersisted();
                }
            }
            submittedModules.remove(moduleType);
        }
    }

    public synchronized void releaseSubmittedModules(List<PlayerModuleEntry> snapshots) {
        for (PlayerModuleEntry snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.getModuleId());
            submittedModules.remove(moduleType);
        }
    }

    public long getPlayerId() {
        return playerEntry.getId();
    }
}
