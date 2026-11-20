package ly.sceneserver.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import ly.config.SlgWorldObjectConfig;

/** 启动时分配各区域的静态候选格，开服后只在所属区域线程补缺。 */
public final class SceneWorldSpawner {
    private final SceneShard shard;
    private final int[] cells;
    private final Supplier<List<SlgWorldObjectConfig>> rules;
    private final LongSupplier ids;
    private final BiConsumer<SceneObject, Boolean> store;
    private final SceneAoiService aoi;
    private final Map<Integer, Long> nextChecks = new HashMap<>();
    private long nextCheck;

    private SceneWorldSpawner(SceneShard shard, int[] cells, Supplier<List<SlgWorldObjectConfig>> rules,
            LongSupplier ids, BiConsumer<SceneObject, Boolean> store, SceneAoiService aoi) {
        this.shard = shard;
        this.cells = cells;
        this.rules = rules;
        this.ids = ids;
        this.store = store;
        this.aoi = aoi;
    }

    public static void install(SceneRuntime.SceneInstance scene, Supplier<List<SlgWorldObjectConfig>> rules,
            LongSupplier ids, BiConsumer<SceneObject, Boolean> store, long now) {
        SceneStaticMap map = scene.staticMap();
        List<IntStream.Builder> regions = new ArrayList<>();
        for (int i = 0; i < scene.shardCount(); i++) regions.add(IntStream.builder());
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (SceneTileFlags.isWalkable(map.flags(x, y)) && map.regionId(x, y) != 0) {
                    regions.get(scene.route(x, y).shardIndex()).add(y * map.width() + x);
                }
            }
        }
        for (int i = 0; i < scene.shardCount(); i++) {
            SceneShard shard = scene.shard(i);
            SceneWorldSpawner spawner = new SceneWorldSpawner(shard, regions.get(i).build().toArray(),
                    rules, ids, store, scene.aoi());
            shard.worldSpawner = spawner;
            spawner.restore(now);
        }
    }

    private void restore(long now) {
        // 旧存档会保留已耗尽的实例，清掉后再计数。未采空的矿沿用存档库存。
        for (SceneObject object : List.copyOf(shard.worldObjects())) {
            if (object.state() instanceof SceneWorldObjectState state
                    && ((object.type() == SceneObjectType.RESOURCE && state.amount() == 0)
                    || (object.type() == SceneObjectType.MONSTER && state.health() == 0))) {
                store.accept(object, true);
                shard.removeObject(object.objectId());
            }
        }
        nextCheck = Long.MAX_VALUE;
        for (SlgWorldObjectConfig rule : rules.get()) {
            replenish(rule, rule.countPerRegion);
            int regionId = shard.staticMap().regionDefinitions().get(shard.shardIndex());
            long due = now + (rule.checkIntervalSec + regionId % (rule.jitterSec + 1L)) * 1000L;
            nextChecks.put(rule.id, due);
            nextCheck = Math.min(nextCheck, due);
        }
        if (nextChecks.isEmpty()) nextCheck = now + 1000;
    }

    void tick(long now) {
        if (now < nextCheck) return;
        nextCheck = Long.MAX_VALUE;
        for (SlgWorldObjectConfig rule : rules.get()) {
            long due = nextChecks.getOrDefault(rule.id, now);
            if (due <= now) {
                int added = replenish(rule, rule.batchSize);
                // 超过一批的缺口留到下一帧，落点不足则等下次巡检。
                due = added == rule.batchSize ? now + shard.config().tickMillis() : now + rule.checkIntervalSec * 1000L;
                nextChecks.put(rule.id, due);
            }
            nextCheck = Math.min(nextCheck, due);
        }
        if (nextCheck == Long.MAX_VALUE) nextCheck = now + 1000;
    }

    int replenish(SlgWorldObjectConfig rule, int budget) {
        SceneObjectType kind = SceneObjectType.valueOf(rule.kind);
        int remaining = rule.countPerRegion;
        for (SceneObject object : shard.worldObjects()) {
            if (object.type() == kind && object.state() instanceof SceneWorldObjectState state
                    && state.configId() == rule.id) remaining--;
        }
        int needed = Math.min(remaining, budget);
        if (needed <= 0 || cells.length == 0) return 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int added = 0;
        int width = shard.config().width();
        for (int checked = 0; checked < cells.length && added < needed; checked++) {
            int selected = random.nextInt(checked, cells.length);
            int cell = cells[selected];
            cells[selected] = cells[checked];
            cells[checked] = cell;
            int x = cell % width;
            int y = cell / width;
            if (!shard.objectsAt(x, y).isEmpty()) continue;
            SceneWorldObjectState state = new SceneWorldObjectState(1, rule.id, rule.id, 1,
                    rule.amount, rule.health, 0, 0, 0, new byte[0]);
            SceneObject object = new SceneObject(ids.getAsLong(), kind, 0, x, y, state);
            shard.addObject(object);
            store.accept(object, false);
            aoi.pointChanged(new ScenePoint(x, y));
            added++;
        }
        return added;
    }
}
