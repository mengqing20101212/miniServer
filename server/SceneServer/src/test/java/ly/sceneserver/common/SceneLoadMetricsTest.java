package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

public class SceneLoadMetricsTest {
    @Test
    public void shardPublishesQueueTickAndOwnershipLoadWithoutReadingMapsOffThread() throws Exception {
        SceneRuntime runtime = new SceneRuntime(0L, 1, 100);
        try {
            runtime.addScene(
                    new SceneConfig("load-test", 32, 32, 1, 8, 5),
                    (shard, tick, now) -> LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(2)));
            runtime.start();
            SceneRuntime.SceneInstance scene = runtime.scene("load-test");
            scene.submit(2, 3, shard -> shard.addObject(new SceneObject(
                            1L, SceneObjectType.RESOURCE, 0L, 2, 3, "wood")))
                    .get(1, TimeUnit.SECONDS);

            long deadline = System.currentTimeMillis() + 1_000L;
            SceneShardLoadSnapshot snapshot;
            do {
                snapshot = scene.shard(0).loadSnapshotAndResetPeaks();
                if (snapshot.totalCommands() >= 1L && snapshot.slowTickCount() >= 1L) {
                    break;
                }
                Thread.sleep(5L);
            } while (System.currentTimeMillis() < deadline);

            assertTrue(snapshot.tickNumber() > 0L);
            assertTrue(snapshot.totalCommands() >= 1L);
            assertTrue(snapshot.slowTickCount() >= 1L);
            assertTrue(snapshot.totalTickNanos() > 0L);
            assertTrue(snapshot.lastThreadName().startsWith("SceneShard-Tick-"));
            assertEquals(1, snapshot.objectCount());
            runtime.logLoadNow();
        } finally {
            runtime.close();
        }
    }
}
