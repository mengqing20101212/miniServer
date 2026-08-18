package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.BitSet;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/** 验证热点 Region 迁移的数据完整性、命令顺序和失败边界。 */
public class SceneRegionMigrationTest {

    @Test
    public void migrationMovesObjectsAoiAndPersonalFogToTargetShard() throws Exception {
        SceneRuntime runtime = new SceneRuntime(0L, 200, 100);
        try {
            runtime.addScene(new SceneConfig("migration", 128, 64, 2, 32, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("migration");
            int regionIndex = 0;
            long playerId = 1001L;
            scene.seedObject(new SceneObject(
                    9001L, SceneObjectType.RESOURCE, 0L, 10, 10, "wood"));
            runtime.start();

            SceneViewSnapshot before = scene.updateViewAsync(new SceneViewRequest(
                    playerId, new ScenePoint(10, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(2, TimeUnit.SECONDS);
            assertEquals(1, before.objects().size());
            assertEquals(0, scene.regionOwnerShardIndex(regionIndex));

            SceneRegionMigrationResult result = scene.migrateRegionAsync(regionIndex, 1)
                    .get(2, TimeUnit.SECONDS);

            assertEquals(0, result.sourceShardIndex());
            assertEquals(1, result.targetShardIndex());
            assertEquals(1, result.migratedObjectCount());
            assertEquals(1, result.migratedPlayerStateCount());
            assertEquals(1, scene.regionOwnerShardIndex(regionIndex));
            assertEquals(1, scene.route(10, 10).shardIndex());
            assertEquals(0, scene.shard(0).query(SceneShard::objectCount).get().intValue());
            assertEquals(1, scene.shard(1).query(SceneShard::objectCount).get().intValue());

            // 再次拉取视野，证明目标 Shard 已重建 objectsByBlock 和 viewersByBlock，而不只是
            // 修改了 RegionDirectory 的路由数字。
            SceneViewSnapshot after = scene.updateViewAsync(new SceneViewRequest(
                    playerId, new ScenePoint(10, 10), 0, SceneViewLevel.DETAIL, 0L))
                    .get(2, TimeUnit.SECONDS);
            assertEquals(1, after.objects().size());
            assertEquals(9001L, after.objects().getFirst().objectId());
            assertTrue(after.discoveredBlockIndices().contains(regionIndex));

            BitSet discovered = scene.discoveredBlocksSnapshotAsync(playerId)
                    .get(2, TimeUnit.SECONDS);
            assertTrue(discovered.get(regionIndex));
        } finally {
            runtime.close();
        }
    }

    @Test
    public void commandsSubmittedDuringMigrationAreReleasedToNewOwnerInOrder() throws Exception {
        SceneRuntime runtime = new SceneRuntime(0L, 200, 100);
        CountDownLatch sourceTickEntered = new CountDownLatch(1);
        CountDownLatch releaseSourceTick = new CountDownLatch(1);
        try {
            runtime.addScene(new SceneConfig("buffered-command", 64, 32, 2, 32, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("buffered-command");
            runtime.start();

            // 先占住源 Shard Tick，让迁移线程能够完成“冻结路由”，但导出任务暂时排在阻塞命令后。
            scene.shard(0).submit(shard -> {
                sourceTickEntered.countDown();
                try {
                    releaseSourceTick.await();
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(error);
                }
            });
            assertTrue(sourceTickEntered.await(1, TimeUnit.SECONDS));

            CompletableFuture<SceneRegionMigrationResult> migration = scene.migrateRegionAsync(0, 1);
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(1);
            while (!scene.isRegionMigrating(0) && System.nanoTime() < deadline) {
                Thread.onSpinWait();
            }
            assertTrue(scene.isRegionMigrating(0));

            AtomicInteger executedShard = new AtomicInteger(-1);
            CompletableFuture<Void> buffered = scene.submit(
                    10, 10, shard -> executedShard.set(shard.shardIndex()));
            // Region 尚未切换时，该命令不允许绕过迁移直接落入源 Shard。
            assertEquals(-1, executedShard.get());
            CompletableFuture<Integer> aggregateSnapshot = scene.totalObjectCountAsync();
            // 跨全部 Shard 的快照必须等待所有权稳定，不能在源已导出、目标未安装时返回 0。
            assertFalse(aggregateSnapshot.isDone());

            releaseSourceTick.countDown();
            migration.get(2, TimeUnit.SECONDS);
            buffered.get(2, TimeUnit.SECONDS);
            assertEquals(0, aggregateSnapshot.get(2, TimeUnit.SECONDS).intValue());
            assertEquals(1, executedShard.get());
        } finally {
            releaseSourceTick.countDown();
            runtime.close();
        }
    }

    @Test
    public void invalidMigrationDoesNotChangeCurrentOwner() throws Exception {
        SceneRuntime runtime = new SceneRuntime(0L, 200, 100);
        try {
            runtime.addScene(new SceneConfig("invalid-migration", 64, 32, 2, 32, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("invalid-migration");
            runtime.start();
            assertEquals(0, scene.regionOwnerShardIndex(0));

            ExecutionException error = assertThrows(
                    ExecutionException.class,
                    () -> scene.migrateRegionAsync(0, 0).get(2, TimeUnit.SECONDS));
            assertNotNull(error.getCause());
            assertTrue(error.getCause() instanceof IllegalArgumentException);
            assertEquals(0, scene.regionOwnerShardIndex(0));
            assertTrue(!scene.isRegionMigrating(0));
        } finally {
            runtime.close();
        }
    }

    @Test
    public void targetValidationFailureRollsDataAndBufferedRoutingBackToSource() throws Exception {
        SceneRuntime runtime = new SceneRuntime(0L, 200, 100);
        try {
            runtime.addScene(new SceneConfig("rollback", 64, 32, 2, 32, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("rollback");
            // 故意在两个 Shard 预置重复 objectId，模拟目标端完整性校验失败。真实启动恢复会更早
            // 拒绝这种坏数据；这里专门验证迁移失败不能让源 Region 丢失。
            scene.seedObject(new SceneObject(
                    7001L, SceneObjectType.RESOURCE, 0L, 10, 10, "source"));
            scene.seedObject(new SceneObject(
                    7001L, SceneObjectType.RESOURCE, 0L, 40, 10, "target-conflict"));
            runtime.start();

            ExecutionException error = assertThrows(
                    ExecutionException.class,
                    () -> scene.migrateRegionAsync(0, 1).get(2, TimeUnit.SECONDS));
            assertNotNull(error.getCause());
            assertEquals(0, scene.regionOwnerShardIndex(0));
            assertFalse(scene.isRegionMigrating(0));
            assertEquals(1, scene.shard(0).query(SceneShard::objectCount).get().intValue());
            assertEquals(1, scene.shard(1).query(SceneShard::objectCount).get().intValue());
            assertNotNull(scene.shard(0).query(shard -> shard.object(7001L)).get());
        } finally {
            runtime.close();
        }
    }
}
