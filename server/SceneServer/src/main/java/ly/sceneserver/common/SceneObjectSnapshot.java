package ly.sceneserver.common;

import ly.sceneserver.common.march.SceneMarchSnapshot;
import ly.sceneserver.common.march.SceneRallySnapshot;

/** 在 SceneShard 线程内创建的不可变对象快照，可安全交给 RPC 或其他异步线程读取。 */
public record SceneObjectSnapshot(
        long objectId,
        SceneObjectType type,
        long ownerId,
        ScenePoint point,
        int stateVersion,
        long dataTagMask,
        SceneMarchSnapshot march,
        SceneRallySnapshot rally) {

    public SceneObjectSnapshot(
            long objectId,
            SceneObjectType type,
            long ownerId,
            ScenePoint point,
            int stateVersion,
            long dataTagMask) {
        this(objectId, type, ownerId, point, stateVersion, dataTagMask, null, null);
    }
}
