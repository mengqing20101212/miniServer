package ly.sceneserver.common.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.SceneRuntime;
import ly.sceneserver.common.SceneViewLevel;
import ly.sceneserver.common.SceneViewRequest;

public class ScenePlayerPersistenceServiceTest {
    @Test
    public void samePlayerRetriesInPlaceAndPersistsRevisionsInOrder() throws Exception {
        AtomicInteger failures = new AtomicInteger(2);
        List<Long> attempts = java.util.Collections.synchronizedList(new ArrayList<>());
        List<Long> persisted = java.util.Collections.synchronizedList(new ArrayList<>());
        PlayerSceneStore store = new PlayerSceneStore() {
            @Override
            public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
                return List.of();
            }

            @Override
            public void upsert(PlayerSceneProjection projection) {
                attempts.add(projection.revision());
                if (failures.getAndUpdate(value -> Math.max(0, value - 1)) > 0) {
                    throw new IllegalStateException("temporary db failure");
                }
                persisted.add(projection.revision());
            }
        };

        try (ScenePlayerPersistenceService service =
                new ScenePlayerPersistenceService(store, 1, 8, 3, 1L)) {
            var first = service.submit(projection(101L, 1L, bitSet(1)));
            var second = service.submit(projection(101L, 2L, bitSet(1, 2)));
            first.get(1, TimeUnit.SECONDS);
            second.get(1, TimeUnit.SECONDS);

            assertEquals(List.of(1L, 1L, 1L, 2L), attempts);
            assertEquals(List.of(1L, 2L), persisted);
            assertTrue(service.isHealthy());
        }
    }

    @Test
    public void snapshotAndSubmitPersistsLatestPersonalFogFromShard() throws Exception {
        AtomicReference<PlayerSceneProjection> saved = new AtomicReference<>();
        PlayerSceneStore store = new PlayerSceneStore() {
            @Override
            public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
                return List.of();
            }

            @Override
            public void upsert(PlayerSceneProjection projection) {
                saved.set(projection);
            }
        };
        SceneRuntime runtime = new SceneRuntime();
        try (ScenePlayerPersistenceService service =
                new ScenePlayerPersistenceService(store, 1, 8, 0, 1L)) {
            runtime.addScene(new SceneConfig("world-1", 16, 16, 1, 4, 5));
            runtime.start();
            SceneRuntime.SceneInstance scene = runtime.scene("world-1");
            scene.updateViewAsync(new SceneViewRequest(
                            101L, new ScenePoint(8, 8), 1, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);

            PlayerSceneProjection metadata = projection(101L, 9L, new BitSet());
            service.snapshotAndSubmit(scene, metadata).get(1, TimeUnit.SECONDS);

            assertEquals(9L, saved.get().revision());
            assertTrue(saved.get().discoveredBlocks().cardinality() > 0);
        } finally {
            runtime.close();
        }
    }

    @Test
    public void pendingTasksIncludesQueuedAndExecutingWritesUntilTheirFuturesFinish() throws Exception {
        CountDownLatch writing = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        PlayerSceneStore store = new PlayerSceneStore() {
            @Override
            public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
                return List.of();
            }

            @Override
            public void upsert(PlayerSceneProjection projection) {
                writing.countDown();
                try {
                    release.await(1, TimeUnit.SECONDS);
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("test persistence interrupted", error);
                }
            }
        };

        try (ScenePlayerPersistenceService service =
                new ScenePlayerPersistenceService(store, 1, 8, 0, 1L)) {
            var first = service.submit(projection(101L, 1L, bitSet(1)));
            var second = service.submit(projection(101L, 2L, bitSet(1, 2)));
            assertTrue(writing.await(1, TimeUnit.SECONDS));
            assertEquals(2, service.pendingTasks());

            release.countDown();
            first.get(1, TimeUnit.SECONDS);
            second.get(1, TimeUnit.SECONDS);
            assertEquals(0, service.pendingTasks());
        }
    }

    static PlayerSceneProjection projection(long playerId, long revision, BitSet fog) {
        return new PlayerSceneProjection(
                playerId,
                "world-1",
                1_000_000L + playerId,
                10L,
                new ScenePoint((int) playerId % 10, (int) playerId % 7),
                5,
                3,
                fog,
                1,
                revision,
                false,
                LocalDateTime.of(2026, 8, 18, 12, 0).plusSeconds(revision));
    }

    static BitSet bitSet(int... indices) {
        BitSet result = new BitSet();
        for (int index : indices) {
            result.set(index);
        }
        return result;
    }
}
