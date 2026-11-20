package ly.sceneserver.common;

import static org.junit.Assert.*;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import ly.config.SlgWorldObjectConfig;
import org.junit.*;
import ly.sceneserver.common.persistence.TmxSceneStaticMapLoader;

public class TmxSceneStaticMapLoaderTest {
    @org.junit.Rule public final MonsterBattleFixture battle = new MonsterBattleFixture();
    @Test public void loadsActualCompanyMapAndCreatesOneExecutionShardPerConfiguredRegion() throws Exception {
        String directory = System.getProperty("slg.test.map-dir");
        Assume.assumeNotNull(directory);
        TmxSceneStaticMapLoader loader = new TmxSceneStaticMapLoader(Path.of(directory));
        assertEquals(25, loader.regionCount());
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("company", 1200, 1200, loader.regionCount(), 32, 100));
            SceneRuntime.SceneInstance scene = runtime.scene("company");
            loader.load(scene.config(), scene.staticMap());
            scene.configureRegions();
            assertEquals(1_440_000, scene.staticMap().cellCount());
            assertEquals(25, scene.regionCount());
            assertEquals(25, scene.shardCount());
            for (int y = 0; y < 1200; y += 71) for (int x = 0; x < 1200; x += 67) {
                int id = scene.staticMap().regionId(x, y);
                int expected = scene.staticMap().regionDefinitions().indexOf(id);
                assertEquals(expected, scene.route(x, y).shardIndex());
            }
            List<SlgWorldObjectConfig> rules = List.of(
                    new SlgWorldObjectConfig(1, "RESOURCE", 1, 1000, 0, 0, 5000, 10, 900, 30, 20),
                    new SlgWorldObjectConfig(2, "MONSTER", 1, 0, 1, 100, 0, 10, 60, 10, 20));
            AtomicLong ids = new AtomicLong();
            SceneWorldSpawner.install(scene, () -> rules, ids::incrementAndGet, (o, deleted) -> {}, System.currentTimeMillis());
            for (int i = 0; i < scene.shardCount(); i++) {
                SceneShard shard = scene.shard(i);
                assertEquals(10, shard.worldObjects().stream().filter(o -> o.type() == SceneObjectType.RESOURCE).count());
                assertEquals(10, shard.worldObjects().stream().filter(o -> o.type() == SceneObjectType.MONSTER).count());
                for (SceneObject object : shard.worldObjects()) {
                    assertSame(shard, scene.route(object.x(), object.y()));
                    assertTrue(SceneTileFlags.isWalkable(scene.staticMap().flags(object.x(), object.y())));
                    assertEquals(1, shard.objectsAt(object.x(), object.y()).size());
                }
            }
            runtime.start();
            java.util.Set<String> threads = new java.util.HashSet<>();
            for (int index = 0; index < scene.shardCount(); index++)
                threads.add(scene.shard(index).query(shard -> Thread.currentThread().getName()).get());
            assertEquals(25, threads.size());
        }
    }
}
