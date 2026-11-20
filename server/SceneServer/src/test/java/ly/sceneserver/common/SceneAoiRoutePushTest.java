package ly.sceneserver.common;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.*;
import ly.config.SlgWorldObjectConfig;
import ly.sceneserver.common.march.*;
import org.junit.Test;

public class SceneAoiRoutePushTest {
    @org.junit.Rule public final MonsterBattleFixture battle = new MonsterBattleFixture();
    @Test public void viewerAcrossRegionBoundaryReceivesRouteAndCompletionRemoval() throws Exception {
        try (SceneRuntime runtime = new SceneRuntime(0, 200, 1000)) {
            runtime.addScene(new SceneConfig("test", 24, 8, 2, 4, 5));
            SceneRuntime.SceneInstance scene = runtime.scene("test");
            SceneStaticMap map = scene.staticMap();
            int[] regions = new int[map.cellCount()];
            for (int y = 0; y < map.height(); y++) for (int x = 0; x < map.width(); x++) {
                regions[y * map.width() + x] = x < 11 ? 101 : 202;
                map.set(x, y, SceneTerrainType.PLAIN, 0, SceneTileFlags.WALKABLE, 0);
            }
            map.loadRegionIds(regions); map.loadRegionDefinitions(List.of(101, 202)); scene.configureRegions();
            SlgWorldObjectConfig rule = new SlgWorldObjectConfig(2, "MONSTER", 1, 0, 1, 100, 0, 1, 60, 10, 20);
            scene.seedObject(new SceneObject(99, SceneObjectType.MONSTER, 0, 22, 2,
                    new SceneWorldObjectState(1, 2, 0, 1, 0, 100, 0, 0, 0, new byte[0])));
            scene.shard(1).marches().configure(() -> Map.of(2, rule), (object, deleted) -> {}, ignored -> {});
            scene.shard(1).marches().onChanged(scene.aoi()::marchChanged);
            BlockingQueue<SceneViewSnapshot> changes = new LinkedBlockingQueue<>();
            scene.aoi().subscribe(new SceneViewRequest(20, new ScenePoint(9, 2), 1, SceneViewLevel.DETAIL, 0), changes::add);
            runtime.start();
            SceneMarchSnapshot march = scene.shard(1).query(shard -> shard.marches().start(77, 10,
                    new ScenePoint(1, 2), 99, new ScenePoint(22, 2), SceneMarchType.ATTACK, 100, 100,
                    System.currentTimeMillis(), "game1001", battle.strong())).get();
            SceneViewSnapshot visible = changes.poll(2, TimeUnit.SECONDS);
            assertNotNull(visible);
            assertTrue(visible.objects().stream().anyMatch(object -> object.objectId() == 77));
            scene.shard(1).submit(shard -> {
                shard.marches().tick(march.arrivalAtMillis());
                shard.marches().tick(shard.marches().ownedSnapshot(77, 10).arrivalAtMillis());
            }).get();
            SceneViewSnapshot removed = null;
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(2);
            while (System.nanoTime() < deadline) {
                SceneViewSnapshot next = changes.poll(100, TimeUnit.MILLISECONDS);
                if (next != null && next.objects().stream().noneMatch(object -> object.objectId() == 77)) { removed = next; break; }
            }
            assertNotNull("行军结束后完整快照移除路线", removed);
        }
    }
}
