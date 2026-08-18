package ly.sceneserver.common;

/**
 * 客户端地图缩放对应的数据层级。
 *
 * <p>近景返回全部动态对象；中景只返回玩家、怪物、建筑等重要对象；世界视图只返回战略对象。
 * 所有层级都会返回块级聚合计数，客户端不需要为了绘制远景接收每个掉落物或装饰物。
 */
public enum SceneViewLevel {
    DETAIL(SceneDataTag.allMask()),
    REGION(SceneDataTag.PLAYER.mask()
            | SceneDataTag.MONSTER.mask()
            | SceneDataTag.BUILDING.mask()
            | SceneDataTag.STRATEGIC.mask()
            | SceneDataTag.COMBAT.mask()),
    WORLD(SceneDataTag.BUILDING.mask() | SceneDataTag.STRATEGIC.mask());

    private final long defaultObjectTagMask;

    SceneViewLevel(long defaultObjectTagMask) {
        this.defaultObjectTagMask = defaultObjectTagMask;
    }

    /** 0 表示使用当前缩放层默认标签；显式标签也不能突破当前层允许的数据范围。 */
    public long effectiveObjectTagMask(long requestedTagMask) {
        return requestedTagMask == 0L
                ? defaultObjectTagMask
                : defaultObjectTagMask & requestedTagMask;
    }
}
