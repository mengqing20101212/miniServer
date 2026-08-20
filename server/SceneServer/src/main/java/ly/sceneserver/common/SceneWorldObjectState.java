package ly.sceneserver.common;

import java.util.Arrays;

/**
 * 资源、怪物、农田、掉落物和非玩家建筑的可恢复状态。
 *
 * <p>热点通用字段保持强类型，具体玩法额外数据必须使用该玩法自己的 Protobuf 编码后放入
 * extensionData，禁止放置 JSON 字符串或 Java 原生序列化对象。
 */
public record SceneWorldObjectState(
        int dataVersion,
        int configId,
        int ruleId,
        int level,
        long amount,
        long health,
        long refreshAtMillis,
        long expireAtMillis,
        long stateFlags,
        byte[] extensionData) {

    public SceneWorldObjectState {
        if (dataVersion <= 0 || configId < 0 || ruleId < 0 || level < 0
                || amount < 0L || health < 0L || refreshAtMillis < 0L || expireAtMillis < 0L) {
            throw new IllegalArgumentException("invalid persistent scene object state");
        }
        extensionData = extensionData == null
                ? new byte[0]
                : Arrays.copyOf(extensionData, extensionData.length);
    }

    @Override
    public byte[] extensionData() {
        return Arrays.copyOf(extensionData, extensionData.length);
    }

    /** record 默认会按 byte[] 引用比较，这里改为按二进制内容比较，便于恢复校验和去重。 */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SceneWorldObjectState state)) {
            return false;
        }
        return dataVersion == state.dataVersion
                && configId == state.configId
                && ruleId == state.ruleId
                && level == state.level
                && amount == state.amount
                && health == state.health
                && refreshAtMillis == state.refreshAtMillis
                && expireAtMillis == state.expireAtMillis
                && stateFlags == state.stateFlags
                && Arrays.equals(extensionData, state.extensionData);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(dataVersion);
        result = 31 * result + Integer.hashCode(configId);
        result = 31 * result + Integer.hashCode(ruleId);
        result = 31 * result + Integer.hashCode(level);
        result = 31 * result + Long.hashCode(amount);
        result = 31 * result + Long.hashCode(health);
        result = 31 * result + Long.hashCode(refreshAtMillis);
        result = 31 * result + Long.hashCode(expireAtMillis);
        result = 31 * result + Long.hashCode(stateFlags);
        return 31 * result + Arrays.hashCode(extensionData);
    }
}
