package ly.sceneserver.common;

/**
 * 场景中会动态出现的对象类型。
 *
 * <p>枚举只描述通用场景基础设施，具体属性放在 {@link SceneObject#state()} 中，由后续业务模块定义。
 */
public enum SceneObjectType {
    PLAYER,
    RESOURCE,
    MONSTER,
    FARM,
    DROP,
    BUILDING,
    DECORATION,
    /** 玩家或联盟在地图上移动的部队/车队。 */
    MARCH,
    /** 集结点或已经成军的联盟集结。 */
    RALLY;

    /** 稳定数据库编码，与 Scene.proto 的 SceneObjectKind 数值保持一致。 */
    public int persistenceCode() {
        return switch (this) {
            case PLAYER -> 0;
            case RESOURCE -> 1;
            case MONSTER -> 2;
            case FARM -> 3;
            case DROP -> 4;
            case BUILDING -> 5;
            case DECORATION -> 6;
            case MARCH -> 7;
            case RALLY -> 8;
        };
    }

    /** 从数据库稳定编码恢复对象类型，未知编码直接拒绝启动。 */
    public static SceneObjectType fromPersistenceCode(int code) {
        return switch (code) {
            case 0 -> PLAYER;
            case 1 -> RESOURCE;
            case 2 -> MONSTER;
            case 3 -> FARM;
            case 4 -> DROP;
            case 5 -> BUILDING;
            case 6 -> DECORATION;
            case 7 -> MARCH;
            case 8 -> RALLY;
            default -> throw new IllegalArgumentException("unknown scene object type code: " + code);
        };
    }

    /** 是否由 scene_object 聚合表保存；其他类型有独立状态所有者或可以从静态配置重建。 */
    public boolean usesSceneObjectEntry() {
        return this == RESOURCE || this == MONSTER || this == FARM || this == DROP || this == BUILDING;
    }
}
