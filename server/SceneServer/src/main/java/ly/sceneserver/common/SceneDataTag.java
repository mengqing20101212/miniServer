package ly.sceneserver.common;

/**
 * AOI 数据标签。
 *
 * <p>标签使用 long 位掩码保存，一个对象可以同时拥有“对象类型”和“战略/战斗”等业务标签。
 * 这样缩放层级与业务筛选只需要做位运算，不需要在每次视野同步时解析对象状态。
 */
public enum SceneDataTag {
    PLAYER(1L << 0),
    RESOURCE(1L << 1),
    MONSTER(1L << 2),
    FARM(1L << 3),
    DROP(1L << 4),
    BUILDING(1L << 5),
    DECORATION(1L << 6),
    STRATEGIC(1L << 7),
    COMBAT(1L << 8),
    MARCH(1L << 9),
    RALLY(1L << 10);

    private final long mask;

    SceneDataTag(long mask) {
        this.mask = mask;
    }

    public long mask() {
        return mask;
    }

    /** 为通用场景对象生成默认标签，具体玩法后续可以在构造对象时追加标签。 */
    public static long defaultMask(SceneObjectType type) {
        return switch (type) {
            case PLAYER -> PLAYER.mask | COMBAT.mask;
            case RESOURCE -> RESOURCE.mask;
            case MONSTER -> MONSTER.mask | COMBAT.mask;
            case FARM -> FARM.mask;
            case DROP -> DROP.mask;
            case BUILDING -> BUILDING.mask | STRATEGIC.mask;
            case DECORATION -> DECORATION.mask;
            case MARCH -> MARCH.mask | COMBAT.mask;
            case RALLY -> RALLY.mask | COMBAT.mask | STRATEGIC.mask;
        };
    }

    public static long allMask() {
        long result = 0L;
        for (SceneDataTag tag : values()) {
            result |= tag.mask;
        }
        return result;
    }
}
