package ly.logic.asset;

import ly.logic.hero.module.HeroModule;
import ly.config.HeroInfoConfig;
import ly.config.HeroInfoConfigManager;
import ly.db.PersistenceScope;
import ly.proto.Cmd;
import ly.logic.player.*;

import java.util.*;

/** 奖励组织与发放；资源包含货币和堆叠道具，英雄明确指定，避免不同配置 ID 空间冲突。 */
public final class Award {
    private final Map<Integer, Long> resources = new LinkedHashMap<>();
    private static final long FRAGMENTS_PER_DUPLICATE = 100;
    private final Map<Integer, Long> heroes = new LinkedHashMap<>();
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
        return addHero(heroId, 1);
    }

    public Award addHero(int heroId, long count) {
        requireUnexecuted();
        if (heroId <= 0 || count <= 0) throw new IllegalArgumentException("英雄 ID 和数量必须为正");
        heroes.merge(heroId, count, Math::addExact);
        return this;
    }

    public Award addAward(Award other) {
        requireUnexecuted();
        Map<Integer, Long> merged = new LinkedHashMap<>(resources);
        other.resources.forEach(
                (id, count) -> merged.put(id, Math.addExact(merged.getOrDefault(id, 0L), count)));
        Map<Integer, Long> mergedHeroes = new LinkedHashMap<>(heroes);
        other.heroes.forEach((id, count) -> mergedHeroes.merge(id, count, Math::addExact));
        resources.clear();
        resources.putAll(merged);
        heroes.clear();
        heroes.putAll(mergedHeroes);
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
            Map<Integer, Long> amounts = new LinkedHashMap<>(resources);
            List<Integer> newHeroes = resolveHeroRewards(module, amounts);
            for (Map.Entry<Integer, Long> resource : amounts.entrySet())
                Math.addExact(Consume.resource(player).getResource(resource.getKey()), resource.getValue());
            executed = true;
            for (int hero : newHeroes) module.addHero(hero);
            for (Map.Entry<Integer, Long> resource : amounts.entrySet())
                Consume.resource(player).addResource(resource.getKey(), resource.getValue());
            if (!newHeroes.isEmpty() && player.getGamePlayer() != null) {
                PersistenceScope.afterFlush(module, () -> player.pushMsg(Cmd.CMD.SC_HeroList, module.ownedHeroList()));
            }
            Map<Integer, Long> changes = amounts;
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

    private List<Integer> resolveHeroRewards(HeroModule module, Map<Integer, Long> amounts) {
        List<Integer> newHeroes = new ArrayList<>();
        for (Map.Entry<Integer, Long> reward : heroes.entrySet()) {
            int heroId = reward.getKey();
            HeroInfoConfig config = HeroInfoConfigManager.getInstance().getConfigMap().get(heroId);
            if (config == null) throw new IllegalArgumentException("英雄配置不存在: " + heroId);
            long duplicates = reward.getValue();
            if (!module.hasHero(heroId)) {
                newHeroes.add(heroId);
                duplicates--;
            }
            if (duplicates > 0) {
                if (config.heroDebris <= 0) throw new IllegalArgumentException("缺少英雄专属碎片: " + heroId);
                amounts.merge(config.heroDebris, Math.multiplyExact(duplicates, FRAGMENTS_PER_DUPLICATE), Math::addExact);
            }
        }
        if (!newHeroes.isEmpty() && module.getHeroList().size() + newHeroes.size() > module.getMaxHeroCount())
            throw new IllegalArgumentException("英雄槽位已满");
        return newHeroes;
    }

    private void requireUnexecuted() {
        if (executed) throw new IllegalStateException("奖励已经执行");
    }
}
