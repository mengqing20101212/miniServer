package ly.sceneserver.common.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneWorldObjectState;
import ly.sceneserver.common.SceneObject;

/** 验证所有场景聚合复用同一套固定分区、失败原地重试的异步落库语义。 */
public class SceneAggregatePersistenceServiceTest {
    @Test
    public void sameAggregateRetriesInPlaceAndPersistsRevisionsInOrder() throws Exception {
        AtomicInteger failures = new AtomicInteger(2);
        List<Long> attempts = java.util.Collections.synchronizedList(new ArrayList<>());
        List<Long> persisted = java.util.Collections.synchronizedList(new ArrayList<>());
        SceneAggregateStore store = new EmptyAggregateStore() {
            @Override
            public void upsert(SceneObjectProjection projection) {
                attempts.add(projection.revision());
                if (failures.getAndUpdate(value -> Math.max(0, value - 1)) > 0) {
                    throw new IllegalStateException("temporary db failure");
                }
                persisted.add(projection.revision());
            }
        };

        try (SceneAggregatePersistenceService service =
                new SceneAggregatePersistenceService(store, 1, 8, 3, 1L)) {
            var first = service.submit(objectProjection(1L));
            var second = service.submit(objectProjection(2L));
            first.get(1, TimeUnit.SECONDS);
            second.get(1, TimeUnit.SECONDS);

            assertEquals(List.of(1L, 1L, 1L, 2L), attempts);
            assertEquals(List.of(1L, 2L), persisted);
            assertTrue(service.isHealthy());
            assertEquals(0, service.pendingTasks());
        }
    }

    @Test
    public void softDeleteUsesANewerRevisionThanTheLastActiveSnapshot() {
        SceneWorldObjectState state = new SceneWorldObjectState(
                1, 3001, 7, 2, 8_000L, 0L, 20_000L, 0L, 0L, new byte[0]);
        SceneObject object = new SceneObject(
                5_001L, SceneObjectType.RESOURCE, 0L, 2, 3, state);

        SceneAggregateProjection active = SceneAggregateProjectionFactory.snapshot(
                "world-1", object, 1, false, LocalDateTime.of(2026, 8, 19, 12, 0));
        SceneAggregateProjection deleted = SceneAggregateProjectionFactory.snapshot(
                "world-1", object, 1, true, LocalDateTime.of(2026, 8, 19, 12, 1));

        assertEquals(1L, active.revision());
        assertEquals(2L, deleted.revision());
    }

    private static SceneObjectProjection objectProjection(long revision) {
        return new SceneObjectProjection(
                "world-1",
                5_001L,
                SceneObjectType.RESOURCE,
                0L,
                new ScenePoint(2, 3),
                Math.toIntExact(revision),
                1L,
                new SceneWorldObjectState(
                        1, 3001, 7, 2, 8_000L, 0L, 20_000L, 0L, 0L, new byte[0]),
                revision,
                false,
                LocalDateTime.of(2026, 8, 19, 12, 0).plusSeconds(revision));
    }

    /** 测试只覆盖普通对象写入，其余方法保持空实现以突出统一调度语义。 */
    private static class EmptyAggregateStore implements SceneAggregateStore {
        @Override
        public List<SceneObjectProjection> loadActiveObjectPage(
                String sceneId, long afterObjectId, int limit) {
            return List.of();
        }

        @Override
        public List<SceneMarchProjection> loadActiveMarchPage(
                String sceneId, long afterMarchId, int limit) {
            return List.of();
        }

        @Override
        public List<SceneRallyProjection> loadActiveRallyPage(
                String sceneId, long afterRallyId, int limit) {
            return List.of();
        }

        @Override
        public void upsert(SceneObjectProjection projection) {
        }

        @Override
        public void upsert(SceneMarchProjection projection) {
        }

        @Override
        public void upsert(SceneRallyProjection projection) {
        }
    }
}
