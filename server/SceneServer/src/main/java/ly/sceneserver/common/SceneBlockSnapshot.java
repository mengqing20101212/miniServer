package ly.sceneserver.common;

/** 一个 AOI 块的聚合快照，用于中远景绘制和客户端战争迷雾标记。 */
public record SceneBlockSnapshot(
        int blockIndex,
        int blockX,
        int blockY,
        boolean visible,
        boolean discovered,
        int objectCount,
        long dataTagMask) {
}
