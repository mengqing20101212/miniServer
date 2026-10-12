package ly.logic.asset;

import ly.logic.player.*;
import ly.logic.resource.module.ResourceModule;

import java.util.*;

/** 消耗集合；相同 ID 合并后检查，必须在所属玩家执行段使用。 */
public final class Consume {
    private final Map<Integer, Long> amounts = new LinkedHashMap<>();

    private Consume() {}

    public static Consume create() {
        return new Consume();
    }

    public static Consume create(int id, long count) {
        return create().addThing(id, count);
    }

    public static Consume create(String text) {
        return create().addThing(text);
    }

    public Consume addThing(String text) {
        if (text == null || text.isBlank() || text.trim().equals("0")) return this;
        Consume parsed = create();
        for (String item : text.split("\\|", -1)) {
            String[] values = item.split(";", -1);
            if (values.length != 2) throw new IllegalArgumentException("消耗格式应为 ID;数量");
            parsed.addThing(Integer.parseInt(values[0].trim()), Long.parseLong(values[1].trim()));
        }
        return addConsume(parsed);
    }

    public Consume addThing(int id, long count) {
        if (id <= 0 || count < 0) throw new IllegalArgumentException("消耗 ID 必须为正，数量不能为负");
        if (count != 0) amounts.put(id, Math.addExact(amounts.getOrDefault(id, 0L), count));
        return this;
    }

    public Consume addConsume(Consume other) {
        Map<Integer, Long> merged = new LinkedHashMap<>(amounts);
        other.amounts.forEach(
                (id, count) -> merged.put(id, Math.addExact(merged.getOrDefault(id, 0L), count)));
        amounts.clear();
        amounts.putAll(merged);
        return this;
    }

    public Map<Integer, Long> toMap() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(amounts));
    }

    public boolean isEnough(Player player) {
        ResourceModule module = resource(player);
        return amounts.entrySet().stream()
                .allMatch(entry -> module.getResource(entry.getKey()) >= entry.getValue());
    }

    public boolean isEnoughAndCost(Player player, ChangeReason reason) {
        Objects.requireNonNull(reason);
        try (ly.db.PersistenceScope scope = ly.db.PersistenceScope.open()) {
            if (!isEnough(player)) return false;
            ResourceModule module = resource(player);
            for (var entry : amounts.entrySet()) {
                if (!module.deductResource(entry.getKey(), entry.getValue()))
                    throw new IllegalStateException("玩家资源在检查后变化，调用未遵守玩家线程归属");
            }
            Map<Integer, Long> changes = toMap();
            ly.db.PersistenceScope.afterFlush(
                    () ->
                            ly.LoggerDef.SystemLogger.info(
                                    "asset consume playerId={}, reason={}, amounts={}",
                                    player.getPlayerId(),
                                    reason,
                                    changes));
            return true;
        }
    }

    static ResourceModule resource(Player player) {
        return (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);
    }
}
