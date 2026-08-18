package ly.sceneserver.common.march;

/** 行军车辆的可组合标签，使用 long 位掩码保存。 */
public enum SceneMarchTag {
    SOLO(1L << 0),
    RALLY_MEMBER(1L << 1),
    RALLY_MAIN(1L << 2),
    FRIENDLY(1L << 3),
    HOSTILE(1L << 4),
    STEALTH(1L << 5),
    HIGH_PRIORITY(1L << 6),
    RETURNING(1L << 7),
    BATTLE_PENDING(1L << 8),
    CANNOT_RECALL(1L << 9);

    private final long mask;

    SceneMarchTag(long mask) {
        this.mask = mask;
    }

    public long mask() {
        return mask;
    }

    public static boolean contains(long tagMask, SceneMarchTag tag) {
        return (tagMask & tag.mask) != 0L;
    }
}
