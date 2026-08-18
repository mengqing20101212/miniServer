package ly.sceneserver.common;

/** 静态地图 flags[] 的通用位定义。 */
public final class SceneTileFlags {
    /** 单位可以进入该格。 */
    public static final byte WALKABLE = 1;
    /** 该格阻挡视线；第一阶段战争迷雾按块处理，后续射线视野会使用该位。 */
    public static final byte BLOCKS_VISION = 1 << 1;

    private SceneTileFlags() {
    }

    public static boolean isWalkable(byte flags) {
        return (flags & WALKABLE) != 0;
    }
}
