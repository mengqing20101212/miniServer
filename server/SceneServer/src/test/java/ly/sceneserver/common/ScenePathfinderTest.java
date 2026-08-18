package ly.sceneserver.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

/**
 * 验证 Region/Portal 粗路径、格子细路径、地形代价、静态阻挡、个人战争迷雾和 Tick 回投。
 *
 * <p>测试不仅检查“是否到达”，还检查 100 万格地图的 Region 数量、唯一 Portal 对应的
 * 实际入口/出口，以及节点上限是否真正限制 CPU 搜索规模。
 */
public class ScenePathfinderTest {

    @Test
    public void pathfinderSupportsMillionCellStaticMap() {
        // 标准 SLG 世界地图：1000 x 1000 格，全部使用低代价道路，便于验证路径长度。
        SceneStaticMap map = new SceneStaticMap(1_000, 1_000);
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                map.set(x, y, SceneTerrainType.ROAD, 0, SceneTileFlags.WALKABLE, 0);
            }
        }
        // 32 x 32 格一个 Region，两个方向都向上取整，所以粗图应为 32 x 32 = 1024 节点。
        ScenePathfinder pathfinder = new ScenePathfinder(SceneTerrainCostProvider.defaults());
        SceneRegionGraph graph = pathfinder.prepare(map, 32);
        assertEquals(1_024, graph.regionCount());
        assertTrue(graph.portalRunCount() > 0);

        ScenePathResult result = pathfinder.find(
                map,
                new ScenePathRequest(
                        1,
                        new ScenePoint(0, 0),
                        new ScenePoint(999, 0),
                        SceneFogPolicy.IGNORE,
                        5_000),
                unrestrictedVisibility(32, 32));

        assertEquals(ScenePathStatus.OK, result.status());
        assertEquals(1_000, result.points().size());
        assertEquals(new ScenePoint(999, 0), result.points().getLast());
    }

    @Test
    public void astarAvoidsBlockedTerrainAndUsesTerrainCosts() {
        SceneStaticMap map = walkableMap(10, 5);
        for (int y = 0; y < map.height(); y++) {
            if (y != 2) {
                map.set(4, y, SceneTerrainType.WATER, 0, SceneTileFlags.BLOCKS_VISION, 0);
            }
        }
        ScenePathfinder pathfinder = new ScenePathfinder(SceneTerrainCostProvider.defaults());
        ScenePathResult result = pathfinder.find(
                map,
                new ScenePathRequest(
                        1,
                        new ScenePoint(0, 0),
                        new ScenePoint(9, 0),
                        SceneFogPolicy.IGNORE,
                        1_000),
                unrestrictedVisibility(10));

        assertEquals(ScenePathStatus.OK, result.status());
        assertTrue(result.points().contains(new ScenePoint(4, 2)));
        assertTrue(result.totalCost() > 9 * 10);
    }

    @Test
    public void astarCanChooseLongerRoadWithLowerTotalCost() {
        SceneStaticMap map = walkableMap(7, 3);
        for (int x = 0; x < map.width(); x++) {
            map.set(x, 0, SceneTerrainType.ROAD, 0, SceneTileFlags.WALKABLE, 0);
        }
        ScenePathResult result = new ScenePathfinder(SceneTerrainCostProvider.defaults()).find(
                map,
                new ScenePathRequest(
                        1,
                        new ScenePoint(0, 1),
                        new ScenePoint(6, 1),
                        SceneFogPolicy.IGNORE,
                        1_000),
                unrestrictedVisibility(7));

        assertEquals(ScenePathStatus.OK, result.status());
        assertTrue(result.points().stream().anyMatch(point -> point.y() == 0));
        assertTrue(result.points().size() > 7);
        assertTrue(result.totalCost() < 60);
    }

    @Test
    public void hierarchicalPathUsesRealBoundaryPortalsAndExposesRegionEntryExit() {
        SceneStaticMap map = walkableMap(12, 4);
        // Region 0 -> 1 只允许从 (3,3) 跨到 (4,3)。
        for (int y = 0; y < map.height(); y++) {
            if (y != 3) {
                block(map, 3, y);
                block(map, 4, y);
            }
        }
        // Region 1 -> 2 只允许从 (7,0) 跨到 (8,0)。
        for (int y = 0; y < map.height(); y++) {
            if (y != 0) {
                block(map, 7, y);
                block(map, 8, y);
            }
        }

        ScenePathResult result = new ScenePathfinder(SceneTerrainCostProvider.defaults(), 0).find(
                map,
                new ScenePathRequest(
                        1,
                        new ScenePoint(1, 1),
                        new ScenePoint(10, 1),
                        SceneFogPolicy.IGNORE,
                        1_000),
                unrestrictedVisibility(4, 3));

        assertEquals(ScenePathStatus.OK, result.status());
        var segments = result.regionSegments(map.width(), 4);
        assertEquals(3, segments.size());
        assertEquals(new ScenePoint(3, 3), segments.get(0).exit());
        assertEquals(new ScenePoint(4, 3), segments.get(1).entry());
        assertEquals(new ScenePoint(7, 0), segments.get(1).exit());
        assertEquals(new ScenePoint(8, 0), segments.get(2).entry());
        assertTrue(segments.get(1).points().size() > 2);
    }

    @Test
    public void regionGraphRejectsAdjacentBlocksWithoutWalkableBoundaryPortal() {
        SceneStaticMap map = walkableMap(8, 4);
        // 同时封死公共边界两侧，确保不存在任何一对可跨越格；两个 Region 不能仅因相邻就连边。
        for (int y = 0; y < map.height(); y++) {
            block(map, 3, y);
            block(map, 4, y);
        }

        ScenePathResult result = new ScenePathfinder(SceneTerrainCostProvider.defaults(), 0).find(
                map,
                new ScenePathRequest(
                        1,
                        new ScenePoint(1, 1),
                        new ScenePoint(6, 1),
                        SceneFogPolicy.IGNORE,
                        1_000),
                unrestrictedVisibility(4, 2));

        assertEquals(ScenePathStatus.PATH_NOT_FOUND, result.status());
    }

    @Test
    public void fogSupportsVisibleAndDiscoveredPolicies() throws Exception {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 12, 4, 1, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            fillWalkable(scene.staticMap());
            runtime.start();

            scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(1, 1), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);
            scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(6, 1), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);
            scene.updateViewAsync(new SceneViewRequest(
                            1, new ScenePoint(1, 1), 0, SceneViewLevel.DETAIL, 0L))
                    .get(1, TimeUnit.SECONDS);

            ScenePathResult discovered = scene.findPathAsync(new ScenePathRequest(
                            1,
                            new ScenePoint(1, 1),
                            new ScenePoint(6, 1),
                            SceneFogPolicy.DISCOVERED_ONLY,
                            1_000))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(ScenePathStatus.OK, discovered.status());

            ScenePathResult visible = scene.findPathAsync(new ScenePathRequest(
                            1,
                            new ScenePoint(1, 1),
                            new ScenePoint(6, 1),
                            SceneFogPolicy.VISIBLE_ONLY,
                            1_000))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(ScenePathStatus.FOG_BLOCKED, visible.status());
        } finally {
            runtime.close();
        }
    }

    @Test
    public void pathResultReturnsToSceneTickAndSearchHasHardLimit() throws Exception {
        SceneRuntime runtime = new SceneRuntime();
        try {
            runtime.addScene(new SceneConfig("test", 30, 30, 1, 8, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            fillWalkable(scene.staticMap());
            runtime.start();

            AtomicReference<String> callbackThread = new AtomicReference<>();
            ScenePathResult success = scene.findPathAsync(new ScenePathRequest(
                            1,
                            new ScenePoint(0, 0),
                            new ScenePoint(29, 29),
                            SceneFogPolicy.IGNORE,
                            10_000))
                    .thenApply(result -> {
                        callbackThread.set(Thread.currentThread().getName());
                        return result;
                    })
                    .get(1, TimeUnit.SECONDS);
            assertEquals(ScenePathStatus.OK, success.status());
            assertTrue(success.completedTick() > 0);
            assertTrue(callbackThread.get().startsWith("SceneShard-Tick-"));

            ScenePathResult limited = scene.findPathAsync(new ScenePathRequest(
                            1,
                            new ScenePoint(0, 0),
                            new ScenePoint(29, 29),
                            SceneFogPolicy.IGNORE,
                            1))
                    .get(1, TimeUnit.SECONDS);
            assertEquals(ScenePathStatus.LIMIT_EXCEEDED, limited.status());
            assertEquals(1, limited.visitedNodes());
        } finally {
            runtime.close();
        }
    }

    private static SceneStaticMap walkableMap(int width, int height) {
        SceneStaticMap map = new SceneStaticMap(width, height);
        fillWalkable(map);
        return map;
    }

    private static void fillWalkable(SceneStaticMap map) {
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                map.set(x, y, SceneTerrainType.PLAIN, 0, SceneTileFlags.WALKABLE, 0);
            }
        }
    }

    private static void block(SceneStaticMap map, int x, int y) {
        map.set(x, y, SceneTerrainType.WATER, 0, SceneTileFlags.BLOCKS_VISION, 0);
    }

    private static SceneVisibilitySnapshot unrestrictedVisibility(int blockColumns) {
        return new SceneVisibilitySnapshot(1, blockColumns, new java.util.BitSet(), new java.util.BitSet());
    }

    private static SceneVisibilitySnapshot unrestrictedVisibility(int regionSize, int blockColumns) {
        return new SceneVisibilitySnapshot(
                regionSize, blockColumns, new java.util.BitSet(), new java.util.BitSet());
    }
}
