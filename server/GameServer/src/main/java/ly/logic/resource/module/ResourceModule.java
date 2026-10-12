package ly.logic.resource.module;

import ly.logic.player.AbstractModule;
import ly.logic.player.event.PlayerEventParam;
import ly.logic.player.event.PlayerEventType;
import ly.proto.Resource;

import java.util.Collections;
import java.util.List;

/** 资源模块 */
public class ResourceModule extends AbstractModule {
    private final java.util.Map<Integer, ly.db.entry.PlayerResourceEntry> resources =
            new java.util.HashMap<>();

    @Override
    protected boolean usesBlobPersistence() {
        return false;
    }

    @Override
    public void onLoadData() {
        for (ly.db.entry.PlayerResourceEntry entry : player.getPlayerData().loadResourceEntries()) {
            resources.put(entry.getResourceType(), entry);
            player.getPlayerData().registerLoadedEntity(entry);
        }
    }

    @Override
    public void onOpenFunction() {}

    @Override
    public List<PlayerEventType> getRegisterEventTypes() {
        return Collections.emptyList();
    }

    @Override
    public void onEvent(PlayerEventParam param) {
        // 不处理事件
    }

    /** 增加资源 */
    public boolean addResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = getResource(resourceType);
        long next = Math.addExact(current, amount);
        ly.db.entry.PlayerResourceEntry entry = resources.get(resourceType);
        if (entry == null) {
            entry = new ly.db.entry.PlayerResourceEntry();
            entry.setPlayerId(player.getPlayerId());
            entry.setResourceType(resourceType);
            entry.setAmount(next);
            resources.put(resourceType, entry);
            player.getPlayerData().registerNewEntity(entry);
        } else {
            entry.setAmount(next);
        }
        notifyResourceChange(resourceType, amount);
        return true;
    }

    /** 扣除资源 */
    public boolean deductResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = getResource(resourceType);
        if (current < amount) {
            return false;
        }
        resources.get(resourceType).setAmount(current - amount);
        notifyResourceChange(resourceType, -amount);
        return true;
    }

    /** 获取资源数量 */
    public long getResource(int resourceType) {
        ly.db.entry.PlayerResourceEntry entry = resources.get(resourceType);
        return entry == null ? 0L : entry.getAmount();
    }

    /** 通知资源变化 */
    private void notifyResourceChange(int resourceType, long amount) {
        try {
            // 发送 SC_ResourceChange 消息
            Resource.SC_ResourceChange.Builder builder = Resource.SC_ResourceChange.newBuilder();
            builder.putChanges(resourceType, amount);
            player.sendMsg(ly.proto.Cmd.CMD.SC_ResourceChange, builder.build());
        } catch (NoClassDefFoundError | Exception e) {
            // protobuf 版本兼容性兜底：当前运行时 protobuf 版本可能不支持 Map 反射
            System.err.println(
                    "WARN: notifyResourceChange failed (protobuf compat): " + e.getMessage());
        }
    }
}
