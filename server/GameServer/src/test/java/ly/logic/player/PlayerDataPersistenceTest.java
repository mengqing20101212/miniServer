package ly.logic.player;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import ly.db.entry.PlayerEntry;
import ly.db.entry.PlayerModuleEntry;
import ly.logic.hero.module.HeroBean;
import ly.logic.hero.module.HeroModule;
import ly.logic.player.persistence.PlayerModulePersistenceService;
import ly.logic.player.persistence.PlayerModuleStore;
import org.junit.Test;

public class PlayerDataPersistenceTest {

    @Test
    public void migratesLegacyBlobIntoOneSnapshotPerModule() throws Exception {
        RecordingStore store = new RecordingStore();
        PlayerEntry entry = playerEntry(1001L);
        PlayerModuleData legacy = new PlayerModuleData();
        legacy.addModuleData(ModuleEnum.HERO_MODULE.getName(), new byte[] {1, 2});
        legacy.addModuleData(ModuleEnum.RESOURCE_MODULE.getName(), new byte[] {3, 4});
        entry.setModules(ProtobufProxy.create(PlayerModuleData.class).encode(legacy));

        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, false);
        PlayerData playerData = new PlayerData(entry, service);

        assertArrayEquals(new byte[] {1, 2}, playerData.getModuleData(ModuleEnum.HERO_MODULE));
        assertArrayEquals(new byte[] {3, 4}, playerData.getModuleData(ModuleEnum.RESOURCE_MODULE));
        List<PlayerModuleEntry> snapshots = playerData.prepareDirtyModuleSnapshots();
        assertEquals(2, snapshots.size());
        assertTrue(service.persistNow(playerData, snapshots));
        assertEquals(1, store.savedBatches.size());
        assertEquals(2, store.savedBatches.getFirst().size());
        assertTrue(playerData.prepareDirtyModuleSnapshots().isEmpty());
    }

    @Test
    public void prefersModuleRowsAndDoesNotRewriteLoadedData() throws Exception {
        RecordingStore store = new RecordingStore();
        store.loaded.put(
                ModuleEnum.HERO_MODULE.getModuleId(),
                moduleEntry(1002L, ModuleEnum.HERO_MODULE, 7L, new byte[] {9}));
        PlayerEntry entry = playerEntry(1002L);
        PlayerModuleData legacy = new PlayerModuleData();
        legacy.addModuleData(ModuleEnum.RESOURCE_MODULE.getName(), new byte[] {5});
        entry.setModules(ProtobufProxy.create(PlayerModuleData.class).encode(legacy));

        PlayerData playerData = new PlayerData(entry, new PlayerModulePersistenceService(store, false));

        assertArrayEquals(new byte[] {9}, playerData.getModuleData(ModuleEnum.HERO_MODULE));
        assertNull(playerData.getModuleData(ModuleEnum.RESOURCE_MODULE));
        assertTrue(playerData.prepareDirtyModuleSnapshots().isEmpty());
    }

    @Test
    public void deserializesEntryDataIntoRuntimeModule() throws Exception {
        RecordingStore store = new RecordingStore();
        HeroModule storedModule = new HeroModule();
        HeroBean hero = new HeroBean();
        hero.heroUid = 1005L;
        hero.heroId = 5;
        storedModule.heroList.add(hero);
        byte[] moduleData = ProtobufProxy.create(HeroModule.class).encode(storedModule);
        store.loaded.put(
                ModuleEnum.HERO_MODULE.getModuleId(),
                moduleEntry(1005L, ModuleEnum.HERO_MODULE, 4L, moduleData));
        PlayerData playerData = new PlayerData(
                playerEntry(1005L), new PlayerModulePersistenceService(store, false));
        Player player = new Player();
        player.setPlayerData(playerData);

        player.initAllModules();

        HeroModule runtimeModule = (HeroModule) playerData.getModule(ModuleEnum.HERO_MODULE);
        assertEquals(1, runtimeModule.getHeroList().size());
        assertEquals(1005L, runtimeModule.getHeroList().getFirst().heroUid);
    }

    @Test
    public void snapshotsOnlyDirtyModuleAndReleasesItAfterFailure() {
        RecordingStore store = new RecordingStore();
        store.loaded.put(
                ModuleEnum.HERO_MODULE.getModuleId(),
                moduleEntry(1003L, ModuleEnum.HERO_MODULE, 7L, new byte[] {1}));
        store.loaded.put(
                ModuleEnum.RESOURCE_MODULE.getModuleId(),
                moduleEntry(1003L, ModuleEnum.RESOURCE_MODULE, 3L, new byte[] {2}));
        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, false);
        PlayerData playerData = new PlayerData(playerEntry(1003L), service);
        byte[] changed = {6, 7};

        playerData.markModuleDirty(ModuleEnum.HERO_MODULE, changed);
        changed[0] = 99;
        List<PlayerModuleEntry> snapshots = playerData.prepareDirtyModuleSnapshots();

        assertEquals(1, snapshots.size());
        assertEquals(ModuleEnum.HERO_MODULE.getModuleId(), snapshots.getFirst().getModuleId().intValue());
        assertEquals(8L, snapshots.getFirst().getRevision().longValue());
        assertArrayEquals(new byte[] {6, 7}, snapshots.getFirst().getModuleData());
        store.saveResult = false;
        assertTrue(!service.persistNow(playerData, snapshots));
        assertEquals(1, playerData.prepareDirtyModuleSnapshots().size());
    }

    @Test
    public void retriesBatchWhenStoreThrows() throws Exception {
        RetryingStore store = new RetryingStore();
        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, true);
        PlayerData playerData = new PlayerData(playerEntry(1004L), service);
        playerData.markModuleDirty(ModuleEnum.HERO_MODULE, new byte[] {8});
        playerData.markModuleDirty(ModuleEnum.RESOURCE_MODULE, new byte[] {9});

        assertTrue(playerData.flushAsync());
        assertTrue(store.success.await(3, TimeUnit.SECONDS));
        service.shutdown(3_000L);

        assertEquals(2, store.saveCalls.get());
        assertEquals(2, store.lastBatch.size());
    }

    private static PlayerEntry playerEntry(long playerId) {
        PlayerEntry entry = new PlayerEntry();
        entry.setId(playerId);
        return entry;
    }

    private static PlayerModuleEntry moduleEntry(
            long playerId, ModuleEnum moduleType, long revision, byte[] moduleData) {
        return new PlayerModuleEntry(
                playerId,
                moduleType.getModuleId(),
                moduleType.getDataVersion(),
                revision,
                moduleData,
                java.time.LocalDateTime.now());
    }

    private static class RecordingStore implements PlayerModuleStore {
        private final Map<Integer, PlayerModuleEntry> loaded = new HashMap<>();
        private final List<List<PlayerModuleEntry>> savedBatches = new ArrayList<>();
        private boolean saveResult = true;

        @Override
        public Map<Integer, PlayerModuleEntry> load(long playerId) {
            return new HashMap<>(loaded);
        }

        @Override
        public boolean saveBatch(long playerId, List<PlayerModuleEntry> modules) {
            savedBatches.add(List.copyOf(modules));
            return saveResult;
        }
    }

    private static final class RetryingStore implements PlayerModuleStore {
        private final AtomicInteger saveCalls = new AtomicInteger();
        private final CountDownLatch success = new CountDownLatch(1);
        private volatile List<PlayerModuleEntry> lastBatch = List.of();

        @Override
        public Map<Integer, PlayerModuleEntry> load(long playerId) {
            return Map.of();
        }

        @Override
        public boolean saveBatch(long playerId, List<PlayerModuleEntry> modules) {
            if (saveCalls.incrementAndGet() == 1) {
                throw new IllegalStateException("temporary database failure");
            }
            lastBatch = List.copyOf(modules);
            success.countDown();
            return true;
        }
    }
}
