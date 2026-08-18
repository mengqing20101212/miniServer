package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SceneRuntimeTest {

    @Test
    public void staticMapUsesSharedArraysAcrossShards() {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 1_000, 1_000, 4, 32, 10));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            scene.staticMap().set(999, 999, (short) 3, 7, (byte) 1, 9);

            assertSame(scene.staticMap(), scene.shard(0).staticMap());
            assertSame(scene.staticMap(), scene.shard(3).staticMap());
            assertEquals(3, scene.staticMap().terrain(999, 999));
            assertEquals(9, scene.staticMap().spawnRuleId(999, 999));
        } finally {
            runtime.close();
        }
    }

    @Test
    public void dynamicObjectsAreOwnedByRoutedShard() throws Exception {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 100, 100, 4, 32, 10));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            runtime.start();
            assertThrows(IllegalStateException.class, () -> scene.staticMap().set(
                    1, 1, SceneTerrainType.PLAIN, 0, SceneTileFlags.WALKABLE, 0));

            SceneObject object = new SceneObject(1, SceneObjectType.RESOURCE, 0, 75, 20, "wood");
            scene.submit(75, 20, shard -> shard.addObject(object)).get(1, TimeUnit.SECONDS);

            assertEquals(1, scene.route(75, 20).objectCount());
            AtomicInteger objectsAtPoint = new AtomicInteger();
            scene.submit(75, 20, shard -> objectsAtPoint.set(shard.objectsAt(75, 20).size()))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(1, objectsAtPoint.get());
        } finally {
            runtime.close();
        }
    }
}
