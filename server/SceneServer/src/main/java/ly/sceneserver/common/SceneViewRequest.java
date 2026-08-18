package ly.sceneserver.common;

/** 玩家移动视野或切换缩放层时提交给各 SceneShard 的 AOI 请求。 */
public record SceneViewRequest(
        long playerId,
        ScenePoint center,
        int radiusBlocks,
        SceneViewLevel viewLevel,
        long requestedTagMask) {

    public SceneViewRequest {
        if (playerId <= 0) {
            throw new IllegalArgumentException("playerId must be positive");
        }
        if (center == null || viewLevel == null || radiusBlocks < 0) {
            throw new IllegalArgumentException("invalid scene view request");
        }
    }
}
