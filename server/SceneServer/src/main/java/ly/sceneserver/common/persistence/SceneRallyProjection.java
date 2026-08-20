package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.march.SceneRallySnapshot;

/** 集结及其全部成员提交给异步数据库线程的完整不可变聚合快照。 */
public record SceneRallyProjection(
        String sceneId,
        ScenePoint currentPoint,
        SceneRallySnapshot snapshot,
        int dataVersion,
        long revision,
        boolean deleted,
        LocalDateTime updateTime) implements SceneAggregateProjection {

    public SceneRallyProjection {
        if (sceneId == null || sceneId.isBlank() || currentPoint == null || snapshot == null
                || snapshot.rallyId() <= 0L || dataVersion <= 0 || revision <= 0L
                || updateTime == null) {
            throw new IllegalArgumentException("invalid scene rally projection");
        }
    }

    @Override
    public long aggregateId() {
        return snapshot.rallyId();
    }
}
