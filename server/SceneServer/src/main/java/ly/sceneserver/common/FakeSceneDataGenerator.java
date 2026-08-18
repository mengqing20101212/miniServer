package ly.sceneserver.common;

import java.util.List;

import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneMarchTag;
import ly.sceneserver.common.march.SceneMarchType;
import ly.sceneserver.common.march.SceneRallyState;
import ly.sceneserver.common.march.SceneTargetDescriptor;
import ly.sceneserver.common.march.SceneTargetTag;
import ly.sceneserver.common.march.SceneTargetType;

/**
 * 可重复的假地图和假在线玩家生成器。
 *
 * <p>生成器只用于开发、容量验证和协议联调，不代表正式配置表格式。地图仍然按 1000 x 1000
 * 的数组加载，在线玩家默认可生成 10000 个，用来验证 SceneShard 路由和对象内存规模。
 */
public final class FakeSceneDataGenerator {
    private FakeSceneDataGenerator() {
    }

    public static void fillStaticMap(SceneStaticMap map) {
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                int hash = mix(x * 73856093 ^ y * 19349663);
                short terrain;
                if ((hash & 0x1F) == 0) {
                    terrain = SceneTerrainType.WATER;
                } else if (x % 32 == 0 || y % 32 == 0) {
                    terrain = SceneTerrainType.ROAD;
                } else if ((hash & 0x0F) <= 2) {
                    terrain = SceneTerrainType.FOREST;
                } else if ((hash & 0x3F) == 3) {
                    terrain = SceneTerrainType.MOUNTAIN;
                } else {
                    terrain = SceneTerrainType.PLAIN;
                }
                int configId = 1_000 + (hash & 0x0F);
                byte flags = terrain == SceneTerrainType.WATER
                        ? SceneTileFlags.BLOCKS_VISION
                        : SceneTileFlags.WALKABLE;
                int spawnRuleId = (x % 64 == 0 && y % 64 == 0) ? 10_000 + (hash & 0x0F) : 0;
                map.set(x, y, terrain, configId, flags, spawnRuleId);
            }
        }
    }

    public static int seedPlayers(SceneRuntime.SceneInstance scene, int onlinePlayers) {
        if (onlinePlayers < 0) {
            throw new IllegalArgumentException("onlinePlayers must not be negative");
        }
        int seeded = 0;
        for (int i = 0; i < onlinePlayers; i++) {
            int x = (i * 37) % scene.config().width();
            int y = (i * 73 + i / scene.config().width()) % scene.config().height();
            if (scene.staticMap().flags(x, y) == 0) {
                y = (y + 1) % scene.config().height();
            }
            long playerId = 10_000_000L + i;
            scene.seedObject(new SceneObject(
                    playerId,
                    SceneObjectType.PLAYER,
                    playerId,
                    x,
                    y,
                    new ScenePlayerState(playerId, 1 + i % 50, 100 + i % 10_000)));
            seeded++;
        }
        return seeded;
    }

    /**
     * 在静态地图的刷新点生成少量稀疏世界对象，覆盖后续 SLG 常见对象类型。
     *
     * <p>这些对象只是协议联调数据，不代表正式资源刷新规则；正式规则应在 SceneShard
     * tick 中按配置表生成，并通过异步快照/Outbox 持久化。
     */
    public static int seedWorldObjects(SceneRuntime.SceneInstance scene) {
        int seeded = 0;
        int objectIndex = 0;
        for (int y = 0; y < scene.config().height(); y += 64) {
            for (int x = 0; x < scene.config().width(); x += 64) {
                if (scene.staticMap().spawnRuleId(x, y) == 0 || scene.staticMap().flags(x, y) == 0) {
                    continue;
                }
                SceneObjectType type = switch (objectIndex++ % 4) {
                    case 0 -> SceneObjectType.RESOURCE;
                    case 1 -> SceneObjectType.MONSTER;
                    case 2 -> SceneObjectType.FARM;
                    default -> SceneObjectType.DROP;
                };
                scene.seedObject(new SceneObject(
                        20_000_000L + objectIndex,
                        type,
                        0,
                        x,
                        y,
                        new FakeWorldObjectState(type, scene.staticMap().spawnRuleId(x, y), 1)));
                seeded++;
            }
        }
        seeded += seedMarchAndRally(scene);
        return seeded;
    }

    /** 生成一辆攻击车和一个招募中的集结，供 AOI/协议联调查看类型、标签和目标标签。 */
    private static int seedMarchAndRally(SceneRuntime.SceneInstance scene) {
        ScenePoint origin = findWalkable(scene.staticMap(), 0);
        if (origin == null) {
            return 0;
        }
        int originIndex = origin.y() * scene.config().width() + origin.x();
        ScenePoint targetPoint = findWalkable(scene.staticMap(), originIndex + 1);
        if (targetPoint == null) {
            return 0;
        }
        SceneTargetDescriptor cityTarget = new SceneTargetDescriptor(
                40_000_001L,
                SceneTargetType.ALLIANCE_CITY,
                targetPoint,
                SceneTargetTag.ATTACKABLE.mask()
                        | SceneTargetTag.RALLYABLE.mask()
                        | SceneTargetTag.OCCUPIABLE.mask()
                        | SceneTargetTag.ENEMY.mask()
                        | SceneTargetTag.REQUIRES_VISION.mask(),
                1L);

        SceneMarchState march = new SceneMarchState(
                30_000_001L,
                10_000_000L,
                88L,
                SceneMarchType.ATTACK,
                SceneMarchTag.SOLO.mask() | SceneMarchTag.HOSTILE.mask(),
                origin,
                cityTarget,
                1_000,
                50_000L,
                1L);
        march.assignPath(List.of(origin, targetPoint), 100, 10, 1_000L);
        scene.seedObject(new SceneObject(
                30_000_001L,
                SceneObjectType.MARCH,
                10_000_000L,
                origin.x(),
                origin.y(),
                march));

        SceneRallyState rally = new SceneRallyState(
                30_000_002L,
                10_000_000L,
                88L,
                30_000_003L,
                origin,
                cityTarget,
                5,
                2,
                60_000L,
                1_500,
                80_000L,
                2L);
        scene.seedObject(new SceneObject(
                30_000_002L,
                SceneObjectType.RALLY,
                88L,
                origin.x(),
                origin.y(),
                rally));
        return 2;
    }

    private static ScenePoint findWalkable(SceneStaticMap map, int startIndex) {
        for (int index = Math.max(0, startIndex); index < map.cellCount(); index++) {
            if (SceneTileFlags.isWalkable(map.flagsAt(index))) {
                return new ScenePoint(index % map.width(), index / map.width());
            }
        }
        return null;
    }

    /** 假世界对象状态使用明确类型，避免在内存模型中重新引入 JSON 字符串。 */
    public record FakeWorldObjectState(SceneObjectType type, int ruleId, int stateVersion) {
    }

    private static int mix(int value) {
        value ^= value >>> 16;
        value *= 0x7feb352d;
        value ^= value >>> 15;
        value *= 0x846ca68b;
        return value ^ (value >>> 16);
    }
}
