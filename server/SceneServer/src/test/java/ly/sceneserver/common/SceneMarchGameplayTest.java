package ly.sceneserver.common;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import ly.config.SlgWorldObjectConfig;
import ly.sceneserver.common.persistence.PlayerSceneProjection;
import ly.sceneserver.common.march.*;
import org.junit.Test;

public class SceneMarchGameplayTest {
    private final SlgWorldObjectConfig resource = new SlgWorldObjectConfig(1, "RESOURCE", 1, 1000, 0, 0, 50, 1);
    private final SlgWorldObjectConfig monster = new SlgWorldObjectConfig(2, "MONSTER", 1, 0, 100, 100, 0, 1);

    @Test public void targetRegionOwnsGatheringThroughReturnAndAoiSeesRouteOutsideTargetRegion() throws Exception {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            setupRegions(scene);
            seed(scene, 99, SceneObjectType.RESOURCE, 22, 2, resource);
            List<SceneObject> persisted = Collections.synchronizedList(new ArrayList<>());
            List<SceneMarchSnapshot> home = new ArrayList<>();
            for (int index = 0; index < 2; index++) scene.shard(index).marches().configure(
                    () -> Map.of(1, resource, 2, monster), persisted::add, home::add);
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
            scene.shard(1).marches().configure(() -> Map.of(2, monster), object -> {}, ignored -> {});
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
