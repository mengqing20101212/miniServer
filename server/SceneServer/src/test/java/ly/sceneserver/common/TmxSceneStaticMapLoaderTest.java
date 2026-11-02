package ly.sceneserver.common;

import static org.junit.Assert.*;
import java.nio.file.*;
import org.junit.*;
import ly.sceneserver.common.persistence.TmxSceneStaticMapLoader;

public class TmxSceneStaticMapLoaderTest {
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
            runtime.start();
            java.util.Set<String> threads = new java.util.HashSet<>();
            for (int index = 0; index < scene.shardCount(); index++)
                threads.add(scene.shard(index).query(shard -> Thread.currentThread().getName()).get());
            assertEquals(25, threads.size());
        }
    }
}
