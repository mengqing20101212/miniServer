package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.BitSet;

import org.junit.Test;

/** 验证 AOI 块注册、缩放分层和历史探索状态。 */
public class SceneAoiTest {

    @Test
    public void viewMovesBetweenBlocksAndFiltersObjectsByZoomLevel() throws Exception {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 128, 64, 2, 32, 10));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            scene.seedObject(new SceneObject(1, SceneObjectType.PLAYER, 1, 5, 5, "player"));
            scene.seedObject(new SceneObject(2, SceneObjectType.RESOURCE, 0, 10, 10, "wood"));
            scene.seedObject(new SceneObject(3, SceneObjectType.BUILDING, 1, 20, 10, "city"));
            scene.seedObject(new SceneObject(4, SceneObjectType.DROP, 0, 100, 10, "drop"));
            runtime.start();

            SceneViewSnapshot detail = scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(10, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(3, detail.objects().size());
            assertEquals(1, detail.blocks().size());
            assertEquals(Integer.valueOf(1), scene.shard(0)
                    .query(shard -> shard.viewerCountAtBlock(0))
                    .get(1, TimeUnit.SECONDS));

            SceneViewSnapshot world = scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(10, 10), 0, SceneViewLevel.WORLD, 0L))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(1, world.objects().size());
            assertEquals(SceneObjectType.BUILDING, world.objects().getFirst().type());

            SceneViewSnapshot moved = scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(100, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(Integer.valueOf(0), scene.shard(0)
                    .query(shard -> shard.viewerCountAtBlock(0))
                    .get(1, TimeUnit.SECONDS));
            assertEquals(Integer.valueOf(1), scene.shard(1)
                    .query(shard -> shard.viewerCountAtBlock(3))
                    .get(1, TimeUnit.SECONDS));
            assertTrue(moved.discoveredBlockIndices().contains(0));
            assertTrue(moved.discoveredBlockIndices().contains(3));
            assertFalse(moved.objects().isEmpty());
            assertEquals(SceneObjectType.DROP, moved.objects().getFirst().type());
        } finally {
            runtime.close();
        }
    }

    @Test
    public void fogIsIndependentPerPlayerAndSurvivesViewerLeave() throws Exception {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 128, 32, 2, 32, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            runtime.start();

            scene.updateViewAsync(new SceneViewRequest(
                            101, new ScenePoint(10, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);
            scene.updateViewAsync(new SceneViewRequest(
                            202, new ScenePoint(100, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);

            BitSet playerOneFog = scene.discoveredBlocksSnapshotAsync(101)
                    .get(1, TimeUnit.SECONDS);
            BitSet playerTwoFog = scene.discoveredBlocksSnapshotAsync(202)
                    .get(1, TimeUnit.SECONDS);
            assertTrue(playerOneFog.get(0));
            assertFalse(playerOneFog.get(3));
            assertFalse(playerTwoFog.get(0));
            assertTrue(playerTwoFog.get(3));

            scene.removeViewerAsync(101).get(1, TimeUnit.SECONDS);
            SceneVisibilitySnapshot afterLeave = scene.visibilitySnapshotAsync(101)
                    .get(1, TimeUnit.SECONDS);
            assertTrue(afterLeave.visibleBlocks().isEmpty());
            assertTrue(afterLeave.discoveredBlocks().get(0));

            scene.evictDiscoveredBlocksAsync(101).get(1, TimeUnit.SECONDS);
            assertTrue(scene.discoveredBlocksSnapshotAsync(101)
                    .get(1, TimeUnit.SECONDS)
                    .isEmpty());
        } finally {
            runtime.close();
        }
    }
}
