package ly.sceneserver.common.persistence;

import java.time.LocalDateTime;

import ly.sceneserver.common.SceneObject;
import ly.sceneserver.common.ScenePoint;
import ly.sceneserver.common.SceneWorldObjectState;
import ly.sceneserver.common.march.SceneMarchSnapshot;
import ly.sceneserver.common.march.SceneMarchState;
import ly.sceneserver.common.march.SceneRallySnapshot;
import ly.sceneserver.common.march.SceneRallyState;

/**
 * 在 SceneShard Tick 线程中把可变场景对象冻结成不可变数据库投影。
 *
 * <p>数据库线程只接收本工厂产生的值对象，绝不持有或读取 SceneObject/SceneMarchState 等
 * Tick 线程内的可变对象，避免异步序列化时读到一半更新的数据。
 */
public final class SceneAggregateProjectionFactory {
    private SceneAggregateProjectionFactory() {
    }

    /**
     * 为需要长期保存的场景对象创建完整快照。
     *
     * <p>玩家主城虽然也是 BUILDING，但其 state 是 PlayerSceneProjection，应走
     * PlayerSceneEntry；静态装饰、AOI 订阅和 Region 路由属于可重建状态，不应调用本方法。
     */
    public static SceneAggregateProjection snapshot(
            String sceneId,
            SceneObject object,
            int dataVersion,
            boolean deleted,
            LocalDateTime updateTime) {
        if (sceneId == null || sceneId.isBlank() || object == null
                || dataVersion <= 0 || updateTime == null) {
            throw new IllegalArgumentException("invalid scene aggregate snapshot parameters");
        }
        ScenePoint point = new ScenePoint(object.x(), object.y());
        // 软删除本身也是一次状态变化，必须使用更大的 revision；否则数据库会把与最后一条
        // 活跃快照同版本的 deleted=1 当成重复消息忽略，重启后旧对象会被再次恢复。
        long revision = deleted
                ? Math.addExact((long) object.stateVersion(), 1L)
                : object.stateVersion();
        return switch (object.type()) {
            case MARCH -> {
                SceneMarchSnapshot snapshot = object.state() instanceof SceneMarchState state
                        ? state.snapshot()
                        : object.state() instanceof SceneMarchSnapshot state ? state : null;
                if (snapshot == null) {
                    throw new IllegalStateException("march object has no typed state: " + object.objectId());
                }
                yield new SceneMarchProjection(
                        sceneId, point, snapshot, dataVersion, revision, deleted, updateTime);
            }
            case RALLY -> {
                SceneRallySnapshot snapshot = object.state() instanceof SceneRallyState state
                        ? state.snapshot()
                        : object.state() instanceof SceneRallySnapshot state ? state : null;
                if (snapshot == null) {
                    throw new IllegalStateException("rally object has no typed state: " + object.objectId());
                }
                yield new SceneRallyProjection(
                        sceneId, point, snapshot, dataVersion, revision, deleted, updateTime);
            }
            default -> {
                if (!object.type().usesSceneObjectEntry()
                        || !(object.state() instanceof SceneWorldObjectState state)) {
                    throw new IllegalArgumentException(
                            "object is reconstructible or belongs to another entity: " + object.objectId());
                }
                yield new SceneObjectProjection(
                        sceneId,
                        object.objectId(),
                        object.type(),
                        object.ownerId(),
                        point,
                        object.stateVersion(),
                        object.dataTagMask(),
                        state,
                        revision,
                        deleted,
                        updateTime);
            }
        };
    }
}
