package ly.sceneserver.common.march;

import ly.sceneserver.common.ScenePoint;

/** 发车时冻结的目标描述；targetVersion 用于到达时检查目标是否已经迁城、换主或销毁。 */
public record SceneTargetDescriptor(
        long targetId,
        SceneTargetType type,
        ScenePoint point,
        long tagMask,
        long targetVersion) {

    public SceneTargetDescriptor {
        if (targetId < 0 || type == null || point == null || targetVersion < 0) {
            throw new IllegalArgumentException("invalid scene target");
        }
    }

    public boolean hasTag(SceneTargetTag tag) {
        return SceneTargetTag.contains(tagMask, tag);
    }

    /** 按目标能力统一校验车辆类型，关系、免战罩和活动阶段仍由上层规则继续判断。 */
    public boolean supports(SceneMarchType marchType) {
        return switch (marchType) {
            case ATTACK -> hasTag(SceneTargetTag.ATTACKABLE);
            case RALLY_ARMY -> hasTag(SceneTargetTag.ATTACKABLE)
                    && hasTag(SceneTargetTag.RALLYABLE);
            case RALLY_MEMBER -> type == SceneTargetType.RALLY_CAMP;
            case REINFORCE, GARRISON -> hasTag(SceneTargetTag.REINFORCEABLE);
            case GATHER -> hasTag(SceneTargetTag.GATHERABLE);
            case SCOUT -> hasTag(SceneTargetTag.SCOUTABLE);
            case TRANSPORT -> hasTag(SceneTargetTag.FRIENDLY);
            case RETURN -> true;
        };
    }
}
