package ly.sceneserver.common;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import ly.config.SlgWorldObjectConfig;
import ly.sceneserver.common.persistence.PlayerSceneProjection;
import ly.sceneserver.common.march.*;
import org.junit.Test;

public class SceneMarchGameplayTest {
    private final SlgWorldObjectConfig resource = new SlgWorldObjectConfig(1, "RESOURCE", 1, 1000, 0, 0, 50, 1, 900, 30, 20);
    private final SlgWorldObjectConfig monster = new SlgWorldObjectConfig(2, "MONSTER", 1, 0, 100, 100, 0, 1, 60, 10, 20);

    @Test public void targetRegionOwnsGatheringThroughReturnAndAoiSeesRouteOutsideTargetRegion() throws Exception {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            setupRegions(scene);
            seed(scene, 99, SceneObjectType.RESOURCE, 22, 2, resource);
            List<SceneObject> persisted = Collections.synchronizedList(new ArrayList<>());
            List<SceneMarchSnapshot> home = new ArrayList<>();
            for (int index = 0; index < 2; index++) scene.shard(index).marches().configure(
                    () -> Map.of(1, resource, 2, monster), (object, deleted) -> persisted.add(object), home::add);
            runtime.start();
            List<PlayerSceneProjection> cities = new ArrayList<>();
            AtomicLong ids = new AtomicLong(1000);
            ScenePoint city = scene.ensureCity(10, ids::incrementAndGet, cities::add);
            assertEquals(101, scene.staticMap().regionId(city.x(), city.y()));
            scene.updateViewAsync(new SceneViewRequest(10, new ScenePoint(23, 7), 1, SceneViewLevel.DETAIL, 0)).get();
            assertEquals(city, scene.ensureCity(10, ids::incrementAndGet, cities::add));
            assertEquals(1, cities.size());
            long now = System.currentTimeMillis();
            SceneMarchSnapshot started = scene.route(22, 2).query(shard -> shard.marches().start(
                    77, 10, scene.cityPoint(10), 99, new ScenePoint(22, 2),
                    SceneMarchType.GATHER, 100, 100, now, "game1001")).get(2, TimeUnit.SECONDS);
            assertEquals(city, started.origin());
            assertEquals(202, started.targetRegionId());
            SceneViewSnapshot view = scene.updateViewAsync(new SceneViewRequest(20,
                    new ScenePoint(9, 2), 1, SceneViewLevel.DETAIL, 0)).get(2, TimeUnit.SECONDS);
            assertTrue(view.objects().stream().anyMatch(object -> object.objectId() == 77));
            assertFalse(view.objects().stream().anyMatch(object -> object.objectId() == 99));
            scene.shard(1).query(shard -> {
                assertTrue(shard.objectsAt(22, 2).stream().noneMatch(object -> object.type() == SceneObjectType.MARCH));
                shard.marches().tick(started.arrivalAtMillis());
                SceneMarchSnapshot gathering = shard.marches().ownedSnapshot(77, 10);
                assertEquals(SceneMarchStatus.GATHERING, gathering.status());
                shard.marches().tick(gathering.gatherEndAtMillis());
                SceneMarchSnapshot returning = shard.marches().ownedSnapshot(77, 10);
                assertEquals(SceneMarchStatus.RETURNING, returning.status());
                assertTrue(home.isEmpty());
                shard.marches().tick(returning.arrivalAtMillis());
                shard.marches().tick(returning.arrivalAtMillis() + 1);
                return null;
            }).get(2, TimeUnit.SECONDS);
            SceneMarchSnapshot finished = scene.shard(1).query(shard -> shard.marches().ownedSnapshot(77, 10)).get();
            assertEquals(SceneMarchStatus.FINISHED, finished.status());
            assertEquals(1, home.size());
            assertEquals(finished.marchId(), home.getFirst().marchId());
            assertEquals(finished.rewardAmount(), home.getFirst().rewardAmount());
            assertTrue(scene.shard(1).query(shard -> shard.marches().marches().isEmpty()).get());
            try (SceneRuntime restarted = new SceneRuntime(0, 200, 1000)) {
                restarted.addScene(scene.config());
                SceneRuntime.SceneInstance recovered = restarted.scene("test");
                setupRegions(recovered);
                recovered.seedObject(new SceneObject(finished.marchId(), SceneObjectType.MARCH,
                        finished.ownerPlayerId(), 22, 2, SceneMarchState.restore(finished)));
                assertEquals(finished, recovered.shard(1).marches().ownedSnapshot(77, 10));
                assertTrue(recovered.shard(1).marches().marches().isEmpty());
            }
            assertEquals(100, finished.rewardAmount());
            assertEquals(202, finished.targetRegionId());
            assertEquals(finished, SceneMarchProtoMapper.fromProto(SceneMarchProtoMapper.toProto(finished)));
            assertTrue(persisted.stream().anyMatch(object -> object.objectId() == 99
                    && ((SceneWorldObjectState) object.state()).amount() == 900));
        }
    }

    @Test public void fakeBattleAwardsOnlyVictoryAndDeadMonsterCannotBeStartedAgain() throws Exception {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            setupRegions(scene);
            seed(scene, 99, SceneObjectType.MONSTER, 22, 2, monster);
            scene.shard(1).marches().configure(() -> Map.of(2, monster), (object, deleted) -> {}, ignored -> {});
            runtime.start();
            scene.shard(1).query(shard -> {
                long now = System.currentTimeMillis();
                SceneMarchSnapshot weak = shard.marches().start(70, 10, new ScenePoint(1, 2), 99,
                        new ScenePoint(22, 2), SceneMarchType.ATTACK, 100, 10, now, "game1001");
                shard.marches().tick(weak.arrivalAtMillis());
                assertEquals(0, shard.marches().ownedSnapshot(70, 10).rewardAmount());
                SceneMarchSnapshot strong = shard.marches().start(71, 10, new ScenePoint(1, 2), 99,
                        new ScenePoint(22, 2), SceneMarchType.ATTACK, 100, 100, now, "game1001");
                shard.marches().tick(strong.arrivalAtMillis());
                assertTrue(shard.marches().ownedSnapshot(71, 10).victory());
                assertEquals(100, shard.marches().ownedSnapshot(71, 10).rewardAmount());
                assertThrows(IllegalArgumentException.class, () -> shard.marches().start(72, 10,
                        new ScenePoint(1, 2), 99, new ScenePoint(22, 2), SceneMarchType.ATTACK, 100, 100, now, "game1001"));
                return null;
            }).get(2, TimeUnit.SECONDS);
        }
    }

    @Test public void restoredPartialMineSurvivesUntilEmptiedAndTargetsRefillInTheirRegion() throws Exception {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            setupRegions(scene);
            scene.seedObject(new SceneObject(99, SceneObjectType.RESOURCE, 0, 22, 2,
                    new SceneWorldObjectState(1, 1, 1, 1, 130, 0, 0, 0, 0, new byte[0])));
            scene.seedObject(new SceneObject(98, SceneObjectType.MONSTER, 0, 20, 2,
                    new SceneWorldObjectState(1, 2, 2, 1, 0, 0, 0, 0, 0, new byte[0])));
            List<Long> removed = new ArrayList<>();
            List<SceneMarchSnapshot> home = new ArrayList<>();
            BiConsumer<SceneObject, Boolean> store = (object, deleted) -> {
                if (deleted) removed.add(object.objectId());
            };
            AtomicLong ids = new AtomicLong(1000);
            long now = System.currentTimeMillis();
            SceneWorldSpawner.install(scene, () -> List.of(resource, monster), ids::incrementAndGet, store, now);
            assertEquals(List.of(98L), removed);
            SceneShard host = scene.shard(1);
            host.marches().configure(() -> Map.of(1, resource, 2, monster), store, home::add);
            runtime.start();
            BlockingQueue<SceneViewSnapshot> changes = new LinkedBlockingQueue<>();
            scene.aoi().subscribe(new SceneViewRequest(20, new ScenePoint(16, 4), 2, SceneViewLevel.DETAIL, 0), changes::add);
            long replacementId = host.query(shard -> {
                ScenePoint mine = new ScenePoint(22, 2);
                ScenePoint origin = new ScenePoint(1, 0);
                assertEquals(130, ((SceneWorldObjectState) shard.object(99).state()).amount());
                for (int i = 0; i < 2; i++) {
                    long id = 70 + i;
                    SceneMarchSnapshot trip = shard.marches().start(id, 10, origin, 99, mine,
                            SceneMarchType.GATHER, 100, 100, now, "game");
                    shard.marches().tick(trip.arrivalAtMillis());
                    shard.marches().tick(shard.marches().ownedSnapshot(id, 10).gatherEndAtMillis());
                    shard.marches().tick(shard.marches().ownedSnapshot(id, 10).arrivalAtMillis());
                    if (i == 0) assertEquals(30, ((SceneWorldObjectState) shard.object(99).state()).amount());
                }
                assertNull(shard.object(99));
                SceneObject replacement = shard.worldObjects().stream()
                        .filter(o -> o.type() == SceneObjectType.RESOURCE).findFirst().orElseThrow();
                assertEquals(1000, ((SceneWorldObjectState) replacement.state()).amount());
                SceneObject wild = shard.worldObjects().stream()
                        .filter(o -> o.type() == SceneObjectType.MONSTER).findFirst().orElseThrow();
                SceneMarchSnapshot attack = shard.marches().start(72, 10, origin, wild.objectId(),
                        new ScenePoint(wild.x(), wild.y()), SceneMarchType.ATTACK, 100, 100, now, "game");
                shard.marches().tick(attack.arrivalAtMillis());
                assertNull(shard.object(wild.objectId()));
                assertEquals(List.of(98L, 99L, wild.objectId()), removed);
                assertEquals(2, shard.worldObjects().size());
                for (SceneObject object : shard.worldObjects()) {
                    assertEquals(202, shard.staticMap().regionId(object.x(), object.y()));
                }
                assertEquals(2, home.size());
                assertEquals(100, home.get(0).rewardAmount());
                assertEquals(30, home.get(1).rewardAmount());
                return replacement.objectId();
            }).get(2, TimeUnit.SECONDS);
            SceneViewSnapshot refreshed = changes.poll(2, TimeUnit.SECONDS);
            assertNotNull("新矿点所在视野没有收到推送", refreshed);
            assertTrue(refreshed.objects().stream().anyMatch(object -> object.objectId() == replacementId));
            assertFalse(refreshed.objects().stream().anyMatch(object -> object.objectId() == 99));
        }
    }

    @Test public void periodicCheckRefillsOnlyMissingSlotsAndHonorsBatchSize() {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            setupRegions(scene);
            SlgWorldObjectConfig rule = new SlgWorldObjectConfig(1, "RESOURCE", 1, 1000, 0, 0, 50, 3, 900, 30, 1);
            AtomicLong ids = new AtomicLong(1000);
            SceneWorldSpawner.install(scene, () -> List.of(rule), ids::incrementAndGet, (o, deleted) -> {}, 0);
            SceneShard shard = scene.shard(1);
            for (SceneObject object : List.copyOf(shard.worldObjects())) shard.removeObject(object.objectId());
            long due = (900 + 202 % 31) * 1000L;
            shard.worldSpawner.tick(due - 1);
            assertTrue(shard.worldObjects().isEmpty());
            shard.worldSpawner.tick(due);
            assertEquals(1, shard.worldObjects().size());
            shard.worldSpawner.tick(due + 5);
            assertEquals(2, shard.worldObjects().size());
            shard.worldSpawner.tick(due + 10);
            assertEquals(3, shard.worldObjects().size());
            assertEquals(3, scene.shard(0).worldObjects().size());
        }
    }

    private void setupRegions(SceneRuntime.SceneInstance scene) {
        SceneStaticMap map = scene.staticMap();
        int[] regions = new int[map.cellCount()];
        for (int y = 0; y < map.height(); y++) for (int x = 0; x < map.width(); x++) {
            // x=10/11的区域边界穿过同一个AOI格，验证两种分区不互相替代。
            regions[y * map.width() + x] = x < 11 ? 101 : 202;
            map.set(x, y, SceneTerrainType.PLAIN, 0, SceneTileFlags.WALKABLE, 0);
        }
        map.loadRegionIds(regions);
        map.loadSpawnRegions(Set.of(101));
        map.loadRegionDefinitions(List.of(101, 202));
        scene.configureRegions();
        assertNotSame(scene.route(10, 2), scene.route(11, 2));
    }

    private void seed(SceneRuntime.SceneInstance scene, long id, SceneObjectType kind,
                      int x, int y, SlgWorldObjectConfig rule) {
        scene.seedObject(new SceneObject(id, kind, 0, x, y, new SceneWorldObjectState(1,
                rule.id, 0, 1, rule.amount, rule.health, 0, 0, 0, new byte[0])));
    }
}
