package ly.sceneserver.common.march;

/** 行军目标类型；目标 ID 与坐标一起确定地图上的业务实体。 */
public enum SceneTargetType {
    POINT,
    PLAYER_CITY,
    ALLIANCE_CITY,
    ALLIANCE_BUILDING,
    RESOURCE,
    MONSTER,
    RALLY_CAMP,
    TROOP,
    CROSS_SERVER_OBJECT
}
