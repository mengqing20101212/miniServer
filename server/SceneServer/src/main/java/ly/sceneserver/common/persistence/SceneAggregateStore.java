package ly.sceneserver.common.persistence;

import java.util.List;

/**
 * 场景长期聚合的数据库仓储边界。
 *
 * <p>实现必须通过 Entry/EntryHelper 访问数据库，禁止在 SceneServer 业务代码中保存 SQL。
 */
public interface SceneAggregateStore {
    List<SceneObjectProjection> loadActiveObjectPage(String sceneId, long afterObjectId, int limit);

    List<SceneMarchProjection> loadActiveMarchPage(String sceneId, long afterMarchId, int limit);

    List<SceneRallyProjection> loadActiveRallyPage(String sceneId, long afterRallyId, int limit);

    void upsert(SceneObjectProjection projection);

    void upsert(SceneMarchProjection projection);

    void upsert(SceneRallyProjection projection);
}
