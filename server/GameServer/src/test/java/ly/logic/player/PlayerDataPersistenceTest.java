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
import ly.logic.resource.module.ResourceModule;
import ly.logic.resource.module.ResourceModuleData;
import org.junit.Test;

public class PlayerDataPersistenceTest {

    @Test
    public void migratesLegacyBlobThroughRuntimeModules() throws Exception {
        RecordingStore store = new RecordingStore();
        PlayerEntry entry = playerEntry(1001L);
        HeroModule legacyHero = new HeroModule();
        HeroBean hero = new HeroBean();
        hero.heroUid = 101L;
        hero.heroId = 7;
        legacyHero.heroList.add(hero);
        ResourceModuleData legacyResources = new ResourceModuleData();
        legacyResources.resources.put(1, 500L);
        PlayerModuleData legacy = new PlayerModuleData();
        legacy.addModuleData(
                ModuleEnum.HERO_MODULE.getName(),
                ProtobufProxy.create(HeroModule.class).encode(legacyHero));
        legacy.addModuleData(
                ModuleEnum.RESOURCE_MODULE.getName(),
                ProtobufProxy.create(ResourceModuleData.class).encode(legacyResources));
        entry.setModules(ProtobufProxy.create(PlayerModuleData.class).encode(legacy));

        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, true);
        PlayerData playerData = new PlayerData(entry, service);
        Player player = player(playerData);
        player.initAllModules();

        assertTrue(store.legacyCleared.await(3, TimeUnit.SECONDS));
        service.shutdown(3_000L);
        HeroModule runtimeHero = (HeroModule) playerData.getModule(ModuleEnum.HERO_MODULE);
        ResourceModule runtimeResources = (ResourceModule) playerData.getModule(ModuleEnum.RESOURCE_MODULE);
        assertEquals(101L, runtimeHero.getHeroList().getFirst().heroUid);
        assertEquals(500L, runtimeResources.getResource(1));
        assertEquals(1, store.savedBatches.size());
        assertEquals(ModuleEnum.values().length, store.savedBatches.getFirst().size());
        assertNull(entry.getModules());
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
        Map<ModuleEnum, PlayerModuleEntry> loadedModules = playerData.loadModuleEntries();
        mountModule(player(playerData), ModuleEnum.HERO_MODULE, loadedModules.get(ModuleEnum.HERO_MODULE));

        assertArrayEquals(new byte[] {9}, loadedModules.get(ModuleEnum.HERO_MODULE).getModuleData());
        assertNull(loadedModules.get(ModuleEnum.RESOURCE_MODULE));
        assertTrue(playerData.prepareDirtyModuleSnapshots().isEmpty());
    }

    @Test
    public void resumesPartialLegacyMigrationWithoutOverwritingStoredModules() throws Exception {
        RecordingStore store = new RecordingStore();
        HeroModule storedHero = new HeroModule();
        HeroBean hero = new HeroBean();
        hero.heroUid = 202L;
        hero.heroId = 9;
        storedHero.heroList.add(hero);
        store.loaded.put(
                ModuleEnum.HERO_MODULE.getModuleId(),
                moduleEntry(
                        1008L,
                        ModuleEnum.HERO_MODULE,
                        4L,
                        ProtobufProxy.create(HeroModule.class).encode(storedHero)));

        ResourceModuleData legacyResources = new ResourceModuleData();
        legacyResources.resources.put(2, 800L);
        PlayerModuleData legacy = new PlayerModuleData();
        legacy.addModuleData(
                ModuleEnum.RESOURCE_MODULE.getName(),
                ProtobufProxy.create(ResourceModuleData.class).encode(legacyResources));
        PlayerEntry entry = playerEntry(1008L);
        entry.setModules(ProtobufProxy.create(PlayerModuleData.class).encode(legacy));

        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, true);
        PlayerData playerData = new PlayerData(entry, service);
        Player player = player(playerData);
        player.initAllModules();

        assertTrue(store.legacyCleared.await(3, TimeUnit.SECONDS));
        service.shutdown(3_000L);
        HeroModule runtimeHero = (HeroModule) playerData.getModule(ModuleEnum.HERO_MODULE);
        ResourceModule runtimeResources = (ResourceModule) playerData.getModule(ModuleEnum.RESOURCE_MODULE);
        assertEquals(202L, runtimeHero.getHeroList().getFirst().heroUid);
        assertEquals(800L, runtimeResources.getResource(2));
        assertEquals(1, store.savedBatches.size());
        assertEquals(ModuleEnum.values().length - 1, store.savedBatches.getFirst().size());
        assertTrue(store.savedBatches.getFirst().stream()
                .noneMatch(module -> module.getModuleId() == ModuleEnum.HERO_MODULE.getModuleId()));
        assertNull(entry.getModules());
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
    public void deserializesLegacyResourcePayloadThroughAbstractModule() throws Exception {
        ResourceModuleData storedData = new ResourceModuleData();
        storedData.resources.put(7, 99L);
        byte[] moduleData = ProtobufProxy.create(ResourceModuleData.class).encode(storedData);

        ResourceModule module = (ResourceModule) AbstractModule.deserialize(ResourceModule.class, moduleData);

        assertEquals(99L, module.getResource(7));
    }

    @Test
    public void moduleSaveUpdatesItsManagedEntry() {
        RecordingStore store = new RecordingStore();
        PlayerData playerData = new PlayerData(
                playerEntry(1007L), new PlayerModulePersistenceService(store, false));
        Player player = player(playerData);
        PlayerModuleEntry entry = playerData.createModuleEntry(ModuleEnum.HERO_MODULE);
        HeroModule module = new HeroModule();
        module.init(player, ModuleEnum.HERO_MODULE, entry, false);

        assertTrue(module.saveData());

        assertTrue(entry == ((AbstractModule) module).getModuleEntry());
        assertEquals(1L, entry.getRevision().longValue());
        assertTrue(entry.getModuleData().length > 0);
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
        Player player = player(playerData);
        PlayerModuleEntry heroEntry = playerData.loadModuleEntries().get(ModuleEnum.HERO_MODULE);
        mountModule(player, ModuleEnum.HERO_MODULE, heroEntry);
        byte[] changed = {6, 7};

        playerData.markModuleDirty(heroEntry, changed);
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
    public void waitsForInsertIdBeforeSubmittingANewerRevision() {
        RecordingStore store = new RecordingStore();
        PlayerData playerData = new PlayerData(
                playerEntry(1006L), new PlayerModulePersistenceService(store, false));
        PlayerModuleEntry heroEntry = playerData.createModuleEntry(ModuleEnum.HERO_MODULE);
        mountModule(player(playerData), ModuleEnum.HERO_MODULE, heroEntry);

        playerData.markModuleDirty(heroEntry, new byte[] {1});
        List<PlayerModuleEntry> first = playerData.prepareDirtyModuleSnapshots();
        playerData.markModuleDirty(heroEntry, new byte[] {2});

        assertTrue(playerData.prepareDirtyModuleSnapshots().isEmpty());
        first.getFirst().setId(9001L);
        playerData.markModulesPersisted(first);

        List<PlayerModuleEntry> second = playerData.prepareDirtyModuleSnapshots();
        assertEquals(1, second.size());
        assertEquals(9001L, second.getFirst().getId().longValue());
        assertArrayEquals(new byte[] {2}, second.getFirst().getModuleData());
    }

    @Test
    public void retriesBatchWhenStoreThrows() throws Exception {
        RetryingStore store = new RetryingStore();
        PlayerModulePersistenceService service = new PlayerModulePersistenceService(store, true);
        PlayerData playerData = new PlayerData(playerEntry(1004L), service);
        Player player = player(playerData);
        PlayerModuleEntry heroEntry = playerData.createModuleEntry(ModuleEnum.HERO_MODULE);
        PlayerModuleEntry resourceEntry = playerData.createModuleEntry(ModuleEnum.RESOURCE_MODULE);
        mountModule(player, ModuleEnum.HERO_MODULE, heroEntry);
        mountModule(player, ModuleEnum.RESOURCE_MODULE, resourceEntry);
        playerData.markModuleDirty(heroEntry, new byte[] {8});
        playerData.markModuleDirty(resourceEntry, new byte[] {9});

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

    private static Player player(PlayerData playerData) {
        Player player = new Player();
        player.setPlayerData(playerData);
        return player;
    }

    private static AbstractModule mountModule(
            Player player, ModuleEnum moduleType, PlayerModuleEntry moduleEntry) {
        try {
            AbstractModule module = moduleType.getModule().getClass().getDeclaredConstructor().newInstance();
            module.init(player, moduleType, moduleEntry, false);
            return module;
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static PlayerModuleEntry moduleEntry(
            long playerId, ModuleEnum moduleType, long revision, byte[] moduleData) {
        return new PlayerModuleEntry(
                10000L + moduleType.getModuleId(),
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
        private final CountDownLatch legacyCleared = new CountDownLatch(1);
        private boolean saveResult = true;
        private long nextId = 20_000L;

        @Override
        public Map<Integer, PlayerModuleEntry> load(long playerId) {
            return new HashMap<>(loaded);
        }

        @Override
        public boolean saveBatch(long playerId, List<PlayerModuleEntry> modules) {
            savedBatches.add(List.copyOf(modules));
            if (saveResult) {
                modules.stream()
                        .filter(module -> module.getId() == null)
                        .forEach(module -> module.setId(nextId++));
            }
            return saveResult;
        }

        @Override
        public boolean clearLegacyModuleData(PlayerEntry playerEntry) {
            playerEntry.setModules(null);
            playerEntry.markPersisted();
            legacyCleared.countDown();
            return true;
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
