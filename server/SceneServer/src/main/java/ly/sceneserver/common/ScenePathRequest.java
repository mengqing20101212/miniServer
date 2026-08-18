package ly.sceneserver.common;

/** 提交到独立寻路线程池的只读请求。 */
public record ScenePathRequest(
        long playerId,
        ScenePoint start,
        ScenePoint target,
        SceneFogPolicy fogPolicy,
        int maxVisitedNodes) {

    public static final int DEFAULT_MAX_VISITED_NODES = 100_000;
    public static final int HARD_MAX_VISITED_NODES = 500_000;

    public ScenePathRequest {
        if (playerId <= 0 || start == null || target == null || fogPolicy == null) {
            throw new IllegalArgumentException("invalid scene path request");
        }
        if (maxVisitedNodes < 0 || maxVisitedNodes > HARD_MAX_VISITED_NODES) {
            throw new IllegalArgumentException("maxVisitedNodes out of range");
        }
    }

    public int effectiveMaxVisitedNodes() {
        return maxVisitedNodes == 0 ? DEFAULT_MAX_VISITED_NODES : maxVisitedNodes;
    }
}
