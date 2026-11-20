package ly.sceneserver.common;

import java.nio.file.Path;
import java.util.Map;
import ly.ConfigScope;
import ly.ConfigSnapshot;
import ly.config.SlgBattleSkillConfigManager.SlgBattleSkillConfigManagerImpl;
import ly.config.SlgMonsterConfigManager.SlgMonsterConfigManagerImpl;
import ly.proto.Battle.*;
import ly.slg.SlgBattleUnits;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MonsterBattleFixture extends ExternalResource {
    private ConfigScope scope;

    @Override protected void before() {
        Logger logger = LoggerFactory.getLogger(MonsterBattleFixture.class);
        String dir = Path.of("src/test/resources/monster").toAbsolutePath().toString();
        SlgMonsterConfigManagerImpl monsters = new SlgMonsterConfigManagerImpl();
        SlgBattleSkillConfigManagerImpl skills = new SlgBattleSkillConfigManagerImpl();
        monsters.reload(logger, dir);
        skills.reload(logger, dir);
        scope = ConfigScope.use(new ConfigSnapshot("march-test", Map.of(
                SlgMonsterConfigManagerImpl.class, monsters, SlgBattleSkillConfigManagerImpl.class, skills)));
    }

    @Override protected void after() { scope.close(); }

    BattleSideSnapshot weak() { return side(5, 5); }
    BattleSideSnapshot strong() { return side(5000, 1000); }

    private BattleSideSnapshot side(long hp, long attack) {
        return BattleSideSnapshot.newBuilder().addUnits(SlgBattleUnits.single(
                PRT_HERO.newBuilder().setHeroTid(2).build(), hp, attack, 10, 100, null)).build();
    }
}
