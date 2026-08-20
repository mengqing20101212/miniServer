package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.march.SceneMarchSnapshot;

/** 行军提交给异步数据库线程的完整不可变聚合快照。 */
public record SceneMarchProjection(
        String sceneId,
        ScenePoint currentPoint,
        SceneMarchSnapshot snapshot,
        int dataVersion,
        long revision,
        boolean deleted,
        LocalDateTime updateTime) implements SceneAggregateProjection {

    public SceneMarchProjection {
        if (sceneId == null || sceneId.isBlank() || currentPoint == null || snapshot == null
                || snapshot.marchId() <= 0L || dataVersion <= 0 || revision <= 0L
                || updateTime == null) {
            throw new IllegalArgumentException("invalid scene march projection");
        }
    }

    @Override
    public long aggregateId() {
        return snapshot.marchId();
    }
}
