package ly.logic.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;

import ly.db.entry.PlayerEntry;
import ly.logic.player.persistence.PlayerModulePersistenceService;
import ly.logic.player.persistence.PlayerModuleRecord;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public class PlayerData {
    final PlayerEntry playerEntry;
    private final PlayerModulePersistenceService persistenceService;
    private final Map<ModuleEnum, ModuleState> moduleStates = new EnumMap<>(ModuleEnum.class);
    private final Map<String, AbstractModule> modules = new HashMap<>();

    public PlayerData(PlayerEntry playerEntry) {
        this(playerEntry, PlayerModulePersistenceService.getInstance());
    }

    public PlayerData(PlayerEntry playerEntry, PlayerModulePersistenceService persistenceService) {
        if (playerEntry == null || persistenceService == null) {
            throw new IllegalArgumentException("playerEntry and persistenceService are required");
        }
        this.playerEntry = playerEntry;
        this.persistenceService = persistenceService;
        Map<Integer, PlayerModuleRecord> storedModules = persistenceService.load(playerEntry.getId());
        if (storedModules != null && !storedModules.isEmpty()) {
            loadStoredModules(storedModules);
            return;
        }
        loadLegacyModules();
    }

    private void loadStoredModules(Map<Integer, PlayerModuleRecord> storedModules) {
        for (PlayerModuleRecord record : storedModules.values()) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(record.moduleId());
            if (moduleType == null) {
                continue;
            }
            moduleStates.put(
                    moduleType,
                    new ModuleState(record.dataVersion(), record.revision(), record.revision(), record.revision(), record.data()));
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
                            new ModuleState(moduleType.getDataVersion(), 1L, 0L, 0L, moduleBytes));
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("decode legacy player modules failed, playerId=" + playerEntry.getId(), e);
        }
    }

    public synchronized byte[] getModuleData(ModuleEnum moduleType) {
        ModuleState state = moduleStates.get(moduleType);
        return state == null ? null : Arrays.copyOf(state.data, state.data.length);
    }

    public synchronized boolean hasModuleData(ModuleEnum moduleType) {
        ModuleState state = moduleStates.get(moduleType);
        return state != null && state.data.length > 0;
    }

    public void putModule(ModuleEnum moduleType, AbstractModule module) {
        modules.put(moduleType.getName(), module);
    }

    public AbstractModule getModule(ModuleEnum moduleType) {
        return modules.get(moduleType.getName());
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
        long revision = previous == null ? 1L : previous.revision + 1L;
        long persistedRevision = previous == null ? 0L : previous.persistedRevision;
        long submittedRevision = previous == null ? 0L : previous.submittedRevision;
        moduleStates.put(
                moduleType,
                new ModuleState(moduleType.getDataVersion(), revision, persistedRevision, submittedRevision, moduleBytes));
    }

    public boolean flushAsync() {
        List<PlayerModuleRecord> snapshots = prepareDirtyModuleSnapshots();
        if (snapshots.isEmpty()) {
            return true;
        }
        if (persistenceService.submit(this, snapshots)) {
            return true;
        }
        releaseSubmittedModules(snapshots);
        return false;
    }

    public synchronized List<PlayerModuleRecord> prepareDirtyModuleSnapshots() {
        List<PlayerModuleRecord> snapshots = new ArrayList<>();
        for (Map.Entry<ModuleEnum, ModuleState> entry : moduleStates.entrySet()) {
            ModuleState state = entry.getValue();
            if (state.revision <= state.persistedRevision || state.revision <= state.submittedRevision) {
                continue;
            }
            state.submittedRevision = state.revision;
            snapshots.add(new PlayerModuleRecord(
                    entry.getKey().getModuleId(), state.dataVersion, state.revision, state.data));
        }
        return snapshots;
    }

    public synchronized void markModulesPersisted(List<PlayerModuleRecord> snapshots) {
        for (PlayerModuleRecord snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.moduleId());
            ModuleState state = moduleStates.get(moduleType);
            if (state != null) {
                state.persistedRevision = Math.max(state.persistedRevision, snapshot.revision());
            }
        }
    }

    public synchronized void releaseSubmittedModules(List<PlayerModuleRecord> snapshots) {
        for (PlayerModuleRecord snapshot : snapshots) {
            ModuleEnum moduleType = ModuleEnum.fromModuleId(snapshot.moduleId());
            ModuleState state = moduleStates.get(moduleType);
            if (state != null && state.submittedRevision <= snapshot.revision()) {
                state.submittedRevision = state.persistedRevision;
            }
        }
    }

    public long getPlayerId() {
        return playerEntry.getId();
    }

    private static final class ModuleState {
        private final int dataVersion;
        private long revision;
        private long persistedRevision;
        private long submittedRevision;
        private final byte[] data;

        private ModuleState(
                int dataVersion,
                long revision,
                long persistedRevision,
                long submittedRevision,
                byte[] data) {
            this.dataVersion = dataVersion;
            this.revision = revision;
            this.persistedRevision = persistedRevision;
            this.submittedRevision = submittedRevision;
            this.data = Arrays.copyOf(data, data.length);
        }
    }

}
