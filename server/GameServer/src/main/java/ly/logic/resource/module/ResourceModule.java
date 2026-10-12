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

    private final java.util.Map<Integer, Long> pendingNotifications =
            new java.util.LinkedHashMap<>();

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
        recordResourceChange(resourceType, amount);
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
        recordResourceChange(resourceType, -amount);
        return true;
    }

    /** 获取资源数量 */
    public long getResource(int resourceType) {
        ly.db.entry.PlayerResourceEntry entry = resources.get(resourceType);
        return entry == null ? 0L : entry.getAmount();
    }

    private void recordResourceChange(int resourceType, long amount) {
        pendingNotifications.merge(resourceType, amount, Math::addExact);
        ly.db.PersistenceScope.afterFlush(this, this::notifyResourceChanges);
    }

    /** 保存边界接收成功后，一条消息推送本轮合并后的资产变化。 */
    private void notifyResourceChanges() {
        java.util.Map<Integer, Long> changes = new java.util.LinkedHashMap<>(pendingNotifications);
        pendingNotifications.clear();
        changes.values().removeIf(amount -> amount == 0);
        if (changes.isEmpty()) return;
        try {
            Resource.SC_ResourceChange message =
                    Resource.SC_ResourceChange.newBuilder().putAllChanges(changes).build();
            player.sendMsg(ly.proto.Cmd.CMD.SC_ResourceChange, message);
        } catch (NoClassDefFoundError | Exception error) {
            ly.LoggerDef.SystemLogger.warn("资源变更通知失败, playerId={}", player.getPlayerId(), error);
        }
    }
}
