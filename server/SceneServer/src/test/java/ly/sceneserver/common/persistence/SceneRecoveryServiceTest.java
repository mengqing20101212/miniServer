package ly.sceneserver.common.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.time.LocalDateTime;

import org.junit.Test;

import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.SceneObject;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.SceneRuntime;
import ly.sceneserver.common.SceneTerrainType;
import ly.sceneserver.common.SceneTileFlags;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneWorldObjectState;
import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneMarchTag;
import ly.sceneserver.common.march.SceneMarchType;
import ly.sceneserver.common.march.SceneRallyState;
import ly.sceneserver.common.march.SceneTargetDescriptor;
import ly.sceneserver.common.march.SceneTargetTag;
import ly.sceneserver.common.march.SceneTargetType;

public class SceneRecoveryServiceTest {
    @Test
    public void staticMapCitiesAndPerPlayerFogAreRestoredBeforeTickStarts() {
        List<PlayerSceneProjection> rows = new ArrayList<>(List.of(
                ScenePlayerPersistenceServiceTest.projection(
                        101L, 3L, ScenePlayerPersistenceServiceTest.bitSet(1, 2)),
                ScenePlayerPersistenceServiceTest.projection(
                        202L, 7L, ScenePlayerPersistenceServiceTest.bitSet(8))));
        rows.sort(Comparator.comparingLong(PlayerSceneProjection::playerId));
        PlayerSceneStore store = new PlayerSceneStore() {
            @Override
            public List<PlayerSceneProjection> loadActivePage(String sceneId, long afterPlayerId, int limit) {
                return rows.stream()
                        .filter(row -> row.sceneId().equals(sceneId) && row.playerId() > afterPlayerId)
                        .limit(limit)
                        .toList();
            }

            @Override
            public void upsert(PlayerSceneProjection projection) {
                throw new UnsupportedOperationException();
            }
        };

        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("world-1", 16, 16, 2, 4, 10));
            SceneRecoveryService recovery = new SceneRecoveryService(
                    (config, map) -> {
                        for (int y = 0; y < map.height(); y++) {
                            for (int x = 0; x < map.width(); x++) {
                                map.set(x, y, SceneTerrainType.PLAIN, 99,
                                        SceneTileFlags.WALKABLE, 0);
                            }
                        }
                    },
                    store,
                    1);

            SceneRecoveryReport report = recovery.restore(runtime);
            SceneRuntime.SceneInstance scene = runtime.scene("world-1");

            assertEquals(1, report.sceneCount());
            assertEquals(256L, report.staticCellCount());
            assertEquals(2L, report.restoredPlayerCount());
            assertEquals(2L, report.restoredCityCount());
            assertEquals(3L, report.restoredFogBlockCount());
            assertEquals(99, scene.staticMap().configId(15, 15));

            PlayerSceneProjection playerOne = rows.get(0);
            SceneObject city = scene.route(playerOne.cityPoint().x(), playerOne.cityPoint().y())
                    .object(playerOne.cityObjectId());
            assertNotNull(city);
            assertEquals(SceneObjectType.BUILDING, city.type());
            assertEquals(playerOne.playerId(), city.ownerId());

            var playerOneFog = scene.route(playerOne.cityPoint().x(), playerOne.cityPoint().y())
                    .discoveredBlocksSnapshot(playerOne.playerId());
            var playerTwoFog = scene.route(rows.get(1).cityPoint().x(), rows.get(1).cityPoint().y())
                    .discoveredBlocksSnapshot(rows.get(1).playerId());
            assertTrue(playerOneFog.get(1));
            assertFalse(playerOneFog.get(8));
            assertTrue(playerTwoFog.get(8));
            assertFalse(runtime.isStarted());
        } finally {
            runtime.close();
        }
    }

    @Test
    public void typedObjectMarchAndRallyEntitiesRestoreBeforeTickStarts() {
        SceneWorldObjectState resourceState = new SceneWorldObjectState(
                1, 3001, 7, 2, 8_000L, 0L, 20_000L, 0L, 3L, new byte[] {9});
        SceneObjectProjection object = new SceneObjectProjection(
                "world-1", 5_001L, SceneObjectType.RESOURCE, 0L,
                new ScenePoint(2, 3), 6, 17L, resourceState,
                6L, false, LocalDateTime.of(2026, 8, 19, 12, 0));

        SceneTargetDescriptor target = new SceneTargetDescriptor(
                9_001L,
                SceneTargetType.ALLIANCE_CITY,
                new ScenePoint(20, 20),
                SceneTargetTag.ATTACKABLE.mask() | SceneTargetTag.RALLYABLE.mask(),
                4L);
        SceneMarchState marchState = new SceneMarchState(
                1_001L, 101L, 88L, SceneMarchType.ATTACK,
                SceneMarchTag.SOLO.mask(), new ScenePoint(0, 0), target,
                1_000, 50_000L, 12L);
        marchState.assignPath(
                List.of(new ScenePoint(0, 0), new ScenePoint(1, 0), target.point()),
                100, 50, 1_000L);
        SceneMarchProjection march = new SceneMarchProjection(
                "world-1", new ScenePoint(1, 0), marchState.snapshot(),
                1, 3L, false, LocalDateTime.of(2026, 8, 19, 12, 1));

        SceneRallyState rallyState = new SceneRallyState(
                3_001L, 101L, 88L, 2_001L,
                new ScenePoint(10, 10), target, 5, 1, 30_000L,
                1_000, 50_000L, 20L);
        SceneRallyProjection rally = new SceneRallyProjection(
                "world-1", new ScenePoint(10, 10), rallyState.snapshot(),
                1, 1L, false, LocalDateTime.of(2026, 8, 19, 12, 2));

        SceneAggregateStore aggregateStore = aggregateStore(object, march, rally);
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("world-1", 32, 32, 2, 4, 10));
            PlayerSceneStore emptyPlayers = new PlayerSceneStore() {
                @Override
                public List<PlayerSceneProjection> loadActivePage(
                        String sceneId, long afterPlayerId, int limit) {
                    return List.of();
                }

                @Override
                public void upsert(PlayerSceneProjection projection) {
                    throw new UnsupportedOperationException();
                }
            };
            SceneRecoveryReport report = new SceneRecoveryService(
                    (config, map) -> { }, emptyPlayers, aggregateStore, 1).restore(runtime);

            assertEquals(1L, report.restoredSceneObjectCount());
            assertEquals(1L, report.restoredMarchCount());
            assertEquals(1L, report.restoredRallyCount());
            SceneObject restoredObject = runtime.scene("world-1").route(2, 3).object(5_001L);
            SceneObject restoredMarch = runtime.scene("world-1").route(1, 0).object(1_001L);
            SceneObject restoredRally = runtime.scene("world-1").route(10, 10).object(3_001L);
            assertEquals(resourceState, restoredObject.state());
            assertEquals(6, restoredObject.stateVersion());
            assertTrue(restoredMarch.state() instanceof SceneMarchState);
            assertEquals(marchState.snapshot(), ((SceneMarchState) restoredMarch.state()).snapshot());
            assertTrue(restoredRally.state() instanceof SceneRallyState);
            assertEquals(rallyState.snapshot(), ((SceneRallyState) restoredRally.state()).snapshot());
            assertFalse(runtime.isStarted());
        } finally {
            runtime.close();
        }
    }

    private static SceneAggregateStore aggregateStore(
            SceneObjectProjection object,
            SceneMarchProjection march,
            SceneRallyProjection rally) {
        return new SceneAggregateStore() {
            @Override
            public List<SceneObjectProjection> loadActiveObjectPage(
                    String sceneId, long afterObjectId, int limit) {
                return object.objectId() > afterObjectId ? List.of(object) : List.of();
            }

            @Override
            public List<SceneMarchProjection> loadActiveMarchPage(
                    String sceneId, long afterMarchId, int limit) {
                return march.aggregateId() > afterMarchId ? List.of(march) : List.of();
            }

            @Override
            public List<SceneRallyProjection> loadActiveRallyPage(
                    String sceneId, long afterRallyId, int limit) {
                return rally.aggregateId() > afterRallyId ? List.of(rally) : List.of();
            }

            @Override
            public void upsert(SceneObjectProjection projection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void upsert(SceneMarchProjection projection) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void upsert(SceneRallyProjection projection) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
