package ly.logic.scene;

import ly.ConfigLoadException;
import ly.config.*;
import ly.db.entry.PlayerHeroEntry;
import ly.proto.Battle.*;
import ly.slg.SlgBattleUnits;

final class SlgHeroSnapshot {
    private SlgHeroSnapshot() {}

    static BattleSideSnapshot freeze(long playerId, PlayerHeroEntry hero, int troops) {
        HeroInfoConfig template = HeroInfoConfigManager.getInstance().getConfigMap().get(hero.getHeroId());
        if (template == null) throw new ConfigLoadException("heroInfo 缺少模板 " + hero.getHeroId());
        HeroAttrConfig attrs = HeroAttrConfigManager.getInstance().getConfigList().stream()
                .filter(row -> row.modelName == template.heroStateData && row.level == hero.getLevel())
                .findFirst().orElseThrow(() -> new ConfigLoadException("heroAttr 缺少等级属性 " + hero.getHeroId() + "/" + hero.getLevel()));
        int star = hero.getStar() - 1;
        if (star < 0 || star >= attrs.maxHP.size() || star >= attrs.attack.size() || star >= attrs.defence.size()) {
            throw new ConfigLoadException("heroAttr 缺少星级属性 " + hero.getHeroId() + "/" + hero.getLevel() + "/" + hero.getStar());
        }
        SlgBattleSkillConfig active = SlgBattleSkillConfigManager.getInstance().getConfigList().stream()
                .filter(skill -> skill.heroSkillId > 0 && skill.heroSkillId == template.skill_s1).findFirst().orElse(null);
        PRT_HERO info = PRT_HERO.newBuilder().setHeroId(hero.getId()).setHeroTid(hero.getHeroId())
                .setLevel(hero.getLevel()).setStarRank(hero.getStar()).build();
        BattleUnitSnapshot.Builder unit = SlgBattleUnits.single(info, attrs.maxHP.get(star), attrs.attack.get(star),
                attrs.defence.get(star), troops, active);
        unit.addSoldiers(PRT_FORMATION_SOLDIER.newBuilder().setSoldierDid(1).setCount(troops));
        return BattleSideSnapshot.newBuilder().setPlayer(PRT_MINI_PLAYER.newBuilder().setUid(playerId)).addUnits(unit).build();
    }
}
