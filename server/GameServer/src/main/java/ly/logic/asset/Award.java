package ly.logic.asset;

import ly.logic.hero.module.HeroModule;
import ly.logic.player.*;

import java.util.*;

/** 奖励组织与发放；资源包含货币和堆叠道具，英雄明确指定，避免不同配置 ID 空间冲突。 */
public final class Award {
    private final Map<Integer, Long> resources = new LinkedHashMap<>();
    private final Set<Integer> heroes = new LinkedHashSet<>();
    private boolean executed;

    private Award() {}

    public static Award create() {
        return new Award();
    }

    public static Award create(int id, long count) {
        return create().addThing(id, count);
    }

    public static Award create(String text) {
        return create().addThing(text);
    }

    public Award addThing(String text) {
        Award parsed = create();
        Consume.create(text).toMap().forEach(parsed::addThing);
        return addAward(parsed);
    }

    public Award addThing(int id, long count) {
        requireUnexecuted();
        if (id <= 0 || count < 0) throw new IllegalArgumentException("奖励 ID 必须为正，数量不能为负");
        if (count != 0) resources.put(id, Math.addExact(resources.getOrDefault(id, 0L), count));
        return this;
    }

    public Award addHero(int heroId) {
        requireUnexecuted();
        if (heroId <= 0 || !heroes.add(heroId)) throw new IllegalArgumentException("英雄奖励无效或重复");
        return this;
    }

    public Award addAward(Award other) {
        requireUnexecuted();
        Map<Integer, Long> merged = new LinkedHashMap<>(resources);
        other.resources.forEach(
                (id, count) -> merged.put(id, Math.addExact(merged.getOrDefault(id, 0L), count)));
        Set<Integer> mergedHeroes = new LinkedHashSet<>(heroes);
        for (int id : other.heroes)
            if (!mergedHeroes.add(id)) throw new IllegalArgumentException("重复英雄奖励");
        resources.clear();
        resources.putAll(merged);
        heroes.clear();
        heroes.addAll(mergedHeroes);
        return this;
    }

    public Map<Integer, Long> toMap() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(resources));
    }

    public Award award(Player player, ChangeReason reason) {
        Objects.requireNonNull(reason);
        requireUnexecuted();
        try (ly.db.PersistenceScope scope = ly.db.PersistenceScope.open()) {
            HeroModule module =
                    (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
            if (!heroes.isEmpty() && !module.canAddHeroes(heroes))
                throw new IllegalArgumentException("英雄配置、容量或重复校验失败");
            for (var resource : resources.entrySet())
                Math.addExact(
                        Consume.resource(player).getResource(resource.getKey()),
                        resource.getValue());
            executed = true; // 防止同一对象再次执行；不替代持久化业务流水幂等。
            for (int hero : heroes) {
                if (module.addHero(hero) == null) throw new IllegalStateException("英雄发放失败");
            }
            for (var resource : resources.entrySet())
                Consume.resource(player).addResource(resource.getKey(), resource.getValue());
            Map<Integer, Long> changes = toMap();
            ly.db.PersistenceScope.afterFlush(
                    () ->
                            ly.LoggerDef.SystemLogger.info(
                                    "asset award playerId={}, reason={}, amounts={}",
                                    player.getPlayerId(),
                                    reason,
                                    changes));
        }
        return this;
    }

    private void requireUnexecuted() {
        if (executed) throw new IllegalStateException("奖励已经执行");
    }
}
