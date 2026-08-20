package ly.sceneserver.common.persistence;

/** 可按聚合 ID 固定分区、按 revision 顺序保存的场景数据库快照。 */
public sealed interface SceneAggregateProjection
        permits SceneObjectProjection, SceneMarchProjection, SceneRallyProjection {
    long aggregateId();

    long revision();

    String sceneId();
}
