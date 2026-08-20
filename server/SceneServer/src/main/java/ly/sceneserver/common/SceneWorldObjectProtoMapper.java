package ly.sceneserver.common;

import com.google.protobuf.ByteString;
import ly.proto.Scene;

/** 普通场景动态对象状态与 Protobuf 持久化格式的集中转换。 */
public final class SceneWorldObjectProtoMapper {
    private SceneWorldObjectProtoMapper() {
    }

    /** 把不可变领域状态编码为数据库实体保存的 Protobuf 消息。 */
    public static Scene.ScenePersistentObjectState toProto(SceneWorldObjectState state) {
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }
        return Scene.ScenePersistentObjectState.newBuilder()
                .setDataVersion(state.dataVersion())
                .setConfigId(state.configId())
                .setRuleId(state.ruleId())
                .setLevel(state.level())
                .setAmount(state.amount())
                .setHealth(state.health())
                .setRefreshAtMillis(state.refreshAtMillis())
                .setExpireAtMillis(state.expireAtMillis())
                .setStateFlags(state.stateFlags())
                .setExtensionData(ByteString.copyFrom(state.extensionData()))
                .build();
    }

    /** 从数据库 Protobuf 快照恢复类型化领域状态。 */
    public static SceneWorldObjectState fromProto(Scene.ScenePersistentObjectState state) {
        if (state == null) {
            throw new IllegalArgumentException("state cannot be null");
        }
        return new SceneWorldObjectState(
                state.getDataVersion(),
                state.getConfigId(),
                state.getRuleId(),
                state.getLevel(),
                state.getAmount(),
                state.getHealth(),
                state.getRefreshAtMillis(),
                state.getExpireAtMillis(),
                state.getStateFlags(),
                state.getExtensionData().toByteArray());
    }
}
