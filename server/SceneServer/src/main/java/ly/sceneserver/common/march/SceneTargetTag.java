package ly.sceneserver.common.march;

/**
 * 目标能力和关系标签。
 *
 * <p>发车前按标签校验行为，例如集结攻击必须同时具备 ATTACKABLE 和 RALLYABLE，
 * 避免在 Handler 中为每种城市、怪物和活动对象复制一套判断。
 */
public enum SceneTargetTag {
    ATTACKABLE(1L << 0),
    RALLYABLE(1L << 1),
    REINFORCEABLE(1L << 2),
    GATHERABLE(1L << 3),
    OCCUPIABLE(1L << 4),
    SCOUTABLE(1L << 5),
    MOVING(1L << 6),
    FRIENDLY(1L << 7),
    ENEMY(1L << 8),
    NEUTRAL(1L << 9),
    REQUIRES_VISION(1L << 10);

    private final long mask;

    SceneTargetTag(long mask) {
        this.mask = mask;
    }

    public long mask() {
        return mask;
    }

    public static boolean contains(long tagMask, SceneTargetTag tag) {
        return (tagMask & tag.mask) != 0L;
    }
}
