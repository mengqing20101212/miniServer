package ly.sceneserver.common;

/** 把策划地形编号转换成 A* 移动代价；负数表示不可到达。 */
@FunctionalInterface
public interface SceneTerrainCostProvider {
    int movementCost(short terrain, byte flags);

    /** A* 启发函数可使用的最小正移动代价；自定义 lambda 默认返回 1，保证启发函数不高估。 */
    default int minimumMovementCost() {
        return 1;
    }

    /** 第一阶段默认代价，后续可由 config 表构建实现并注入 ScenePathfinder。 */
    static SceneTerrainCostProvider defaults() {
        return new SceneTerrainCostProvider() {
            @Override
            public int movementCost(short terrain, byte flags) {
                if (!SceneTileFlags.isWalkable(flags)) {
                    return -1;
                }
                return switch (terrain) {
                    case SceneTerrainType.ROAD -> 6;
                    case SceneTerrainType.PLAIN -> 10;
                    case SceneTerrainType.FOREST -> 15;
                    case SceneTerrainType.MOUNTAIN -> 30;
                    default -> 10;
                };
            }

            @Override
            public int minimumMovementCost() {
                return 6;
            }
        };
    }
}
