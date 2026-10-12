package ly.config.hotupdate;

import java.util.*;

/** 协调端持久化状态；READY 仅对本轮指定的节点实例与完整包摘要有效。 */
public final class ConfigPublication {
    public ConfigHotUpdateCommand command;
    public Map<String, String> files = new TreeMap<>();
    public Map<String, String> statuses = new TreeMap<>();
    public String phase = "PREPARING";

    public ConfigPublication() {}

    public void acceptReport(
            String serverId,
            String bootId,
            String digest,
            String status,
            long nodeTimeMillis,
            long coordinatorTimeMillis) {
        if (!Objects.equals(command.digest, digest))
            throw new IllegalArgumentException("节点配置摘要不匹配");
        String expected = command.targets.get(serverId);
        if (expected == null) return; // 新节点可以追上正式版本，但不改变已冻结目标集合。
        if ("PREPARING".equals(phase) && !expected.equals(bootId))
            throw new IllegalStateException("目标节点已重启，必须取消并重新准备发布");
        if (nodeTimeMillis < coordinatorTimeMillis - 2_000
                || nodeTimeMillis > coordinatorTimeMillis + 2_000)
            throw new IllegalStateException("节点时钟偏差超过 2 秒");
        String previous = statuses.get(serverId);
        if ("ACTIVE".equals(previous) && !"ACTIVE".equals(status)) return;
        if ("READY".equals(previous) && ("RECEIVED".equals(status) || "DOWNLOADED".equals(status)))
            return;
        if (!Set.of("READY", "FAILED", "SWITCH_SCHEDULED", "ACTIVE", "CANCELLED").contains(status))
            throw new IllegalArgumentException("无效发布状态");
        statuses.put(serverId, status);
        if ("COMMITTED".equals(phase)
                && command.targets.keySet().stream()
                        .allMatch(id -> "ACTIVE".equals(statuses.get(id)))) phase = "ACTIVE";
    }

    public void commit(long now, long switchAtMillis) {
        if (!"PREPARING".equals(phase)
                || command.targets.isEmpty()
                || !command.targets.keySet().stream()
                        .allMatch(id -> "READY".equals(statuses.get(id))))
            throw new IllegalStateException("全部目标节点 READY 后才能提交");
        if (switchAtMillis < now + 10_000) throw new IllegalArgumentException("切换时间至少提前 10 秒");
        command.commandType = "COMMIT";
        command.switchAtMillis = switchAtMillis;
        phase = "COMMITTED";
    }
}
