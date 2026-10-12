package ly.logic.player;

import static org.junit.Assert.*;

import ly.db.AbstractEntry;
import ly.db.PersistenceScope;
import ly.db.entry.*;
import ly.logic.hero.module.HeroModule;
import ly.logic.login.PlayerLogicModule;
import ly.logic.resource.module.ResourceModule;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataPersistenceTest {
    private static final class Store extends PlayerDataTestFactory.MemoryStore {
        final List<AbstractEntry> saved = new ArrayList<>();
        final List<AbstractEntry> removed = new ArrayList<>();
        List<PlayerHeroEntry> heroes = List.of();
        List<PlayerResourceEntry> resources = List.of();
        List<PlayerModuleEntry> blobs = List.of();
        boolean reject;

        public List<PlayerHeroEntry> loadHeroEntries(long id) {
            return heroes;
        }

        public List<PlayerResourceEntry> loadResourceEntries(long id) {
            return resources;
        }

        public List<PlayerModuleEntry> loadModuleEntries(long id) {
            return blobs;
        }

        public boolean acceptEntitySnapshot(AbstractEntry entry) {
            if (reject) {
                return false;
            }
            saved.add(entry);
            return true;
        }

        public boolean acceptEntityDeletion(AbstractEntry entry) {
            removed.add(entry);
            return !reject;
        }
    }

    private Player player(Store store) {
        PlayerEntry entry = new PlayerEntry();
        entry.setId(1001L);
        Player player = new Player();
        player.setPlayerData(new PlayerData(entry, store));
        player.initAllModules();
        store.saved.clear();
        return player;
    }

    @Test
    public void coalescesBlobUntilBoundaryAndDoesNotRewriteUnchangedModule() throws Exception {
        Store store = new Store();
        Player player = player(store);
        PlayerLogicModule module =
                (PlayerLogicModule)
                        player.getPlayerData().getModule(ModuleEnum.PLAYER_LOGIC_MODULE);
        long revision = ((AbstractModule) module).getModuleEntry().getRevision();
        try (PersistenceScope scope = PersistenceScope.open()) {
            module.setLastLoginTimer(1);
            module.setLastLoginTimer(2);
            module.setLastLoginTimer(3);
            assertTrue(store.saved.isEmpty());
            assertEquals(
                    revision, ((AbstractModule) module).getModuleEntry().getRevision().longValue());
        }
        assertEquals(1, store.saved.size());
        PlayerModuleEntry row = (PlayerModuleEntry) store.saved.getFirst();
        PlayerLogicModule decoded =
                (PlayerLogicModule)
                        AbstractModule.deserialize(PlayerLogicModule.class, row.getModuleData());
        assertEquals(3, decoded.getLastLoginTimer());
        player.getPlayerData().flushPendingChanges();
        module.setLastLoginTimer(3);
        assertEquals(1, store.saved.size());
    }

    @Test
    public void flushBarrierSeesLatestBlobAndLaterChangesAreSavedAgain() {
        Store store = new Store();
        Player player = player(store);
        PlayerLogicModule module =
                (PlayerLogicModule)
                        player.getPlayerData().getModule(ModuleEnum.PLAYER_LOGIC_MODULE);
        try (PersistenceScope scope = PersistenceScope.open()) {
            module.setLastLoginTimer(10);
            PersistenceScope.flushCurrent();
            assertEquals(1, store.saved.size());
            module.setLastLoginTimer(20);
        }
        assertEquals(2, store.saved.size());
    }

    @Test
    public void changesOneLoadedHeroAndResourceWithoutEncodingTheirCollections() {
        Store store = new Store();
        PlayerHeroEntry hero = new PlayerHeroEntry();
        hero.setId(7L);
        hero.setPlayerId(1001L);
        hero.setHeroId(1);
        hero.setLevel(1);
        hero.setStar(1);
        hero.setAwaken(0);
        hero.setExp(0L);
        PlayerResourceEntry resource = new PlayerResourceEntry();
        resource.setId(8L);
        resource.setPlayerId(1001L);
        resource.setResourceType(1);
        resource.setAmount(100L);
        store.heroes = List.of(hero);
        store.resources = List.of(resource);
        Player player = player(store);
        ResourceModule resources =
                (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);
        try (PersistenceScope scope = PersistenceScope.open()) {
            hero.setLevel(2);
            hero.setLevel(3);
            resources.deductResource(1, 10);
            assertTrue(store.saved.isEmpty());
        }
        assertEquals(2, store.saved.size());
        assertTrue(store.saved.contains(hero));
        assertTrue(store.saved.contains(resource));
        assertEquals(90, resources.getResource(1));
    }

    @Test
    public void createsAndDeletesEntitiesAutomatically() throws Exception {
        ly.logic.hero.HeroTestConfig.load();
        Store store = new Store();
        Player player = player(store);
        HeroModule module = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        int heroId =
                ly.config.HeroInfoConfigManager.getInstance()
                        .getConfigMap()
                        .keySet()
                        .iterator()
                        .next();
        PlayerHeroEntry hero;
        try (PersistenceScope scope = PersistenceScope.open()) {
            hero = module.addHero(heroId);
        }
        assertEquals(1, store.saved.size());
        assertTrue(hero.getId() > 0);
        assertTrue(module.removeHero(hero.getId()));
        assertEquals(List.of(hero), store.removed);
        assertThrows(IllegalStateException.class, () -> hero.setLevel(5));
    }

    @Test
    public void coroutineDoesNotCompleteSuccessfullyBeforeBlobAcceptance() {
        Store store = new Store();
        Player player = player(store);
        PlayerLogicModule module =
                (PlayerLogicModule)
                        player.getPlayerData().getModule(ModuleEnum.PLAYER_LOGIC_MODULE);
        store.reject = true;
        ly.logic.player.coroutine.PlayerCoroutineTask<Boolean> task =
                new ly.logic.player.coroutine.PlayerCoroutineTask<>(
                        0,
                        1001,
                        "测试保存边界",
                        target -> {
                            module.setLastLoginTimer(55);
                            return true;
                        });
        task.execute(player);
        assertTrue(task.future().isCompletedExceptionally());
        store.reject = false;
        player.getPlayerData().flushPendingChanges();
        assertEquals(1, store.saved.size());
    }

    @Test
    public void rejectedSnapshotRemainsDirtyForRetryAndExceptionsStillFlush() {
        Store store = new Store();
        Player player = player(store);
        PlayerLogicModule module =
                (PlayerLogicModule)
                        player.getPlayerData().getModule(ModuleEnum.PLAYER_LOGIC_MODULE);
        store.reject = true;
        assertThrows(
                IllegalStateException.class,
                () -> {
                    try (PersistenceScope scope = PersistenceScope.open()) {
                        module.setLastLoginTimer(99);
                    }
                });
        store.reject = false;
        player.getPlayerData().flushPendingChanges();
        assertEquals(1, store.saved.size());
        assertThrows(
                IllegalArgumentException.class,
                () -> {
                    try (PersistenceScope scope = PersistenceScope.open()) {
                        module.setLastLoginTimer(100);
                        throw new IllegalArgumentException();
                    }
                });
        assertEquals(2, store.saved.size());
    }
}
