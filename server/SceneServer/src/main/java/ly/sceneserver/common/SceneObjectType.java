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
    RALLY
}
