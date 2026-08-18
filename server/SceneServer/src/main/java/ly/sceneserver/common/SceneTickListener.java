package ly.sceneserver.common;

/** SceneShard 每个 tick 执行的业务回调。回调运行在对应 SceneShard 的逻辑线程。 */
@FunctionalInterface
public interface SceneTickListener {
    void onTick(SceneShard shard, long tickNumber, long nowMillis);
}
