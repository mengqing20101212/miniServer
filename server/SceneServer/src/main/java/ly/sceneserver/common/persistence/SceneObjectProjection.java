package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;
import ly.sceneserver.common.SceneObjectType;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneWorldObjectState;

/** 普通动态对象提交给异步数据库线程的完整不可变快照。 */
public record SceneObjectProjection(
        String sceneId,
        long objectId,
        SceneObjectType objectType,
        long ownerId,
        ScenePoint point,
        int stateVersion,
        long dataTagMask,
        SceneWorldObjectState state,
        long revision,
        boolean deleted,
        LocalDateTime updateTime) implements SceneAggregateProjection {

    public SceneObjectProjection {
        if (sceneId == null || sceneId.isBlank() || objectId <= 0L || objectType == null
                || !objectType.usesSceneObjectEntry() || ownerId < 0L || point == null
                || stateVersion <= 0 || state == null || revision <= 0L || updateTime == null) {
            throw new IllegalArgumentException("invalid scene object projection");
        }
    }

    @Override
    public long aggregateId() {
        return objectId;
    }
}
