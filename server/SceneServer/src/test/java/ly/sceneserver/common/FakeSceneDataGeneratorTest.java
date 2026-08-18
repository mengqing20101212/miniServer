package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneRallyState;

/** 验证 100 万格静态地图和 1 万在线基线假数据可以在启动前加载。 */
public class FakeSceneDataGeneratorTest {

    @Test
    public void generatesMillionCellsAndTenThousandPlayers() {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("world-1", 1_000, 1_000, 4, 32, 100));
            SceneRuntime.SceneInstance scene = runtime.scene("world-1");

            FakeSceneDataGenerator.fillStaticMap(scene.staticMap());
            int worldObjects = FakeSceneDataGenerator.seedWorldObjects(scene);
            int onlinePlayers = FakeSceneDataGenerator.seedPlayers(scene, 10_000);

            assertEquals(1_000, scene.staticMap().width());
            assertEquals(1_000, scene.staticMap().height());
            assertTrue(scene.staticMap().configId(0, 0) >= 1_000);
            assertTrue(worldObjects > 0);
            assertEquals(10_000, onlinePlayers);
            assertEquals(worldObjects + onlinePlayers, scene.totalObjectCount());
            assertTrue(findObject(scene, 30_000_001L).state() instanceof SceneMarchState);
            assertTrue(findObject(scene, 30_000_002L).state() instanceof SceneRallyState);
        } finally {
            runtime.close();
        }
    }

    private static SceneObject findObject(SceneRuntime.SceneInstance scene, long objectId) {
        for (int i = 0; i < scene.shardCount(); i++) {
            SceneObject object = scene.shard(i).object(objectId);
            if (object != null) {
                return object;
            }
        }
        throw new AssertionError("scene object not found: " + objectId);
    }
}
