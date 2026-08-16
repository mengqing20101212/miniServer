package ly.logic.player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
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
    private final Map<ModuleEnum, ModuleState> moduleStates = new EnumMap<>(ModuleEnum.class);
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
        Map<Integer, PlayerModuleEntry> storedModules = persistenceService.load(playerEntry.getId());
        if (storedModules != null && !storedModules.isEmpty()) {
            loadStoredModules(storedModules);
            return;
        }
        loadLegacyModules();
    }

    private void loadStoredModules(Map<Integer, PlayerModuleEntry> storedModules) {
        for (PlayerModuleEntry entry : storedModules.values()) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(entry.getModuleId());
            if (moduleType == null) {
                continue;
            }
            long revision = entry.getRevision();
            moduleStates.put(
                    moduleType,
                    new ModuleState(entry.snapshot(), revision, revision));
        }
    }

    private void loadLegacyModules() {
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
                    moduleStates.put(
                            moduleType,
                            new ModuleState(
                                    new PlayerModuleEntry(
                                            null,
                                            getPlayerId(),
                                            moduleType.getModuleId(),
                                            moduleType.getDataVersion(),
                                            1L,
                                            moduleBytes,
                                            LocalDateTime.now()),
                                    0L,
                                    0L));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("decode legacy player modules failed, playerId=" + playerEntry.getId(), e);
        }
    }

    public synchronized byte[] getModuleData(ModuleEnum moduleType) {
        ModuleState state = moduleStates.get(moduleType);
        return state == null ? null : state.entry.getModuleData();
    }

    public synchronized PlayerModuleEntry getModuleEntry(ModuleEnum moduleType) {
        ModuleState state = moduleStates.get(moduleType);
        return state == null ? null : state.entry.snapshot();
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
    public synchronized void markModuleDirty(ModuleEnum moduleType, byte[] moduleBytes) {
        if (moduleType == null || moduleBytes == null) {
            throw new IllegalArgumentException("moduleType and moduleBytes are required");
        }
        ModuleState previous = moduleStates.get(moduleType);
        long revision = previous == null ? 1L : previous.entry.getRevision() + 1L;
        long persistedRevision = previous == null ? 0L : previous.persistedRevision;
        long submittedRevision = previous == null ? 0L : previous.submittedRevision;
        moduleStates.put(
                moduleType,
                new ModuleState(
                        new PlayerModuleEntry(
                                previous == null ? null : previous.entry.getId(),
                                getPlayerId(),
                                moduleType.getModuleId(),
                                moduleType.getDataVersion(),
                                revision,
                                moduleBytes,
                                LocalDateTime.now()),
                        persistedRevision,
                        submittedRevision));
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
        for (Map.Entry<ModuleEnum, ModuleState> entry : moduleStates.entrySet()) {
            ModuleState state = entry.getValue();
            long revision = state.entry.getRevision();
            if (revision <= state.persistedRevision
                    || revision <= state.submittedRevision
                    || state.submittedRevision > state.persistedRevision) {
                continue;
            }
            state.submittedRevision = revision;
            snapshots.add(state.entry.snapshot());
        }
        return snapshots;
    }

    public synchronized void markModulesPersisted(List<PlayerModuleEntry> snapshots) {
        for (PlayerModuleEntry snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.getModuleId());
            ModuleState state = moduleStates.get(moduleType);
            if (state != null) {
                if (state.entry.getId() == null && snapshot.getId() != null) {
                    state.entry.setId(snapshot.getId());
                }
                state.persistedRevision = Math.max(state.persistedRevision, snapshot.getRevision());
            }
        }
    }

    public synchronized void releaseSubmittedModules(List<PlayerModuleEntry> snapshots) {
        for (PlayerModuleEntry snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.getModuleId());
            ModuleState state = moduleStates.get(moduleType);
            if (state != null && state.submittedRevision <= snapshot.getRevision()) {
                state.submittedRevision = state.persistedRevision;
            }
        }
    }

    public long getPlayerId() {
        return playerEntry.getId();
    }

    private static final class ModuleState {
        private final PlayerModuleEntry entry;
        private long persistedRevision;
        private long submittedRevision;

        private ModuleState(
                PlayerModuleEntry entry,
                long persistedRevision,
                long submittedRevision) {
            this.entry = entry;
            this.persistedRevision = persistedRevision;
            this.submittedRevision = submittedRevision;
        }
    }

}
