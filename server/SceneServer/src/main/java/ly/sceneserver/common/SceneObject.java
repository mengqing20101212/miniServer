package ly.sceneserver.common;

/**
 * 场景动态对象。
 *
 * <p>对象只允许由所属 SceneShard 的逻辑线程修改。state 使用 Object 是为了给资源点、怪物、
 * 农田、掉落物等业务保留扩展空间；后续稳定下来的热点类型可以替换成明确的 Java 数据结构。
 */
public final class SceneObject {
    private final long objectId;
    private final SceneObjectType type;
    private final long ownerId;
    private int x;
    private int y;
    private Object state;
    private int stateVersion = 1;
    private final long dataTagMask;

    public SceneObject(long objectId, SceneObjectType type, long ownerId, int x, int y, Object state) {
        this(objectId, type, ownerId, x, y, state,
                type == null ? 0L : SceneDataTag.defaultMask(type));
    }

    /**
     * 创建带自定义 AOI 标签的动态对象。
     *
     * <p>例如联盟关卡可以同时带 BUILDING、STRATEGIC 和 COMBAT 标签，使中景和世界视图
     * 都能同步它，而无需让 AOI 层理解具体玩法类。
     */
    public SceneObject(
            long objectId,
            SceneObjectType type,
            long ownerId,
            int x,
            int y,
            Object state,
            long dataTagMask) {
        if (objectId <= 0) {
            throw new IllegalArgumentException("objectId must be positive");
        }
        if (type == null) {
            throw new IllegalArgumentException("type must not be null");
        }
        this.objectId = objectId;
        this.type = type;
        this.ownerId = ownerId;
        this.x = x;
        this.y = y;
        this.state = state;
        this.dataTagMask = dataTagMask;
    }

    public long objectId() {
        return objectId;
    }

    public SceneObjectType type() {
        return type;
    }

    public long ownerId() {
        return ownerId;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Object state() {
        return state;
    }

    public void state(Object state) {
        this.state = state;
        stateVersion++;
    }

    public int stateVersion() {
        return stateVersion;
    }

    public long dataTagMask() {
        return dataTagMask;
    }

    void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
        stateVersion++;
    }
}
