package ly.sceneserver.common.march;

/**
 * 地图行军/车辆类型。
 *
 * <p>类型决定状态机和目标能力校验；同一类型还可以叠加多个 {@link SceneMarchTag}，
 * 供客户端表现、AOI 过滤和活动规则使用。
 */
public enum SceneMarchType {
    /** 单支部队攻击城市、怪物或其他可攻击目标。 */
    ATTACK,
    /** 集结成员先前往集结点。 */
    RALLY_MEMBER,
    /** 集结完成后由盟主/队长发出的主车。 */
    RALLY_ARMY,
    /** 增援友方城市或部队。 */
    REINFORCE,
    /** 驻守友方城市或联盟建筑。 */
    GARRISON,
    /** 前往资源点采集。 */
    GATHER,
    /** 侦察目标。 */
    SCOUT,
    /** 向友方目标运输资源。 */
    TRANSPORT,
    /** 任务完成或撤回后的返程。 */
    RETURN
}
