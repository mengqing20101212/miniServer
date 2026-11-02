package ly.sceneserver.common;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import ly.config.SlgWorldObjectConfig;

public final class SceneWorldSpawner {
    private SceneWorldSpawner() {}

    public static void seed(SceneRuntime.SceneInstance scene, List<SlgWorldObjectConfig> rules,
                            LongSupplier ids, Consumer<SceneObject> persist) {
        Map<SceneShard, Map<Integer, Integer>> counts = new HashMap<>();
        for (int index = 0; index < scene.shardCount(); index++) {
            SceneShard shard = scene.shard(index);
            Map<Integer, Integer> local = new HashMap<>();
            for (SceneObject object : shard.worldObjects())
                if (object.state() instanceof SceneWorldObjectState state)
                    local.merge(state.configId(), 1, Integer::sum);
            counts.put(shard, local);
        }
        SceneStaticMap map = scene.staticMap();
        int nextRule = 0;
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (rules.isEmpty()) return;
                if (!SceneTileFlags.isWalkable(map.flags(x, y)) || Math.floorMod(x * 31 + y * 17, 997) != 0) continue;
                SceneShard shard = scene.route(x, y);
                SlgWorldObjectConfig rule = rules.get(nextRule++ % rules.size());
                Map<Integer, Integer> local = counts.get(shard);
                if (local.getOrDefault(rule.id, 0) >= rule.countPerRegion || !shard.objectsAt(x, y).isEmpty()) continue;
                SceneObjectType kind = SceneObjectType.valueOf(rule.kind);
                SceneWorldObjectState state = new SceneWorldObjectState(1, rule.id, rule.id, 1,
                        rule.amount, rule.health, 0, 0, 0, new byte[0]);
                SceneObject object = new SceneObject(ids.getAsLong(), kind, 0, x, y, state);
                scene.seedObject(object);
                persist.accept(object);
                local.merge(rule.id, 1, Integer::sum);
            }
        }
    }
}
