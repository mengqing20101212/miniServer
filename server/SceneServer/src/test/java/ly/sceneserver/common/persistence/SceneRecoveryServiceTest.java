package ly.sceneserver.common.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import ly.sceneserver.common.SceneConfig;
import ly.sceneserver.common.SceneObject;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.SceneRuntime;
import ly.sceneserver.common.SceneTerrainType;
import ly.sceneserver.common.SceneTileFlags;

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
}
