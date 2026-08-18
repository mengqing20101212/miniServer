package ly.sceneserver.common;

/** A* 内部结果状态，由 RPC Handler 统一映射到公共 ErrorCode。 */
public enum ScenePathStatus {
    OK,
    INVALID_ARGUMENT,
    OUT_OF_BOUNDS,
    PATH_NOT_FOUND,
    LIMIT_EXCEEDED,
    FOG_BLOCKED
}
