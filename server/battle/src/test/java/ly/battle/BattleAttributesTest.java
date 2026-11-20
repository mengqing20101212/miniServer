package ly.battle;

import ly.battle.attr.AttrMap;
import ly.battle.unit.BattleUnit;
import ly.proto.Battle.AttrTypeEnum;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Map;
import static ly.proto.Battle.AttrTypeEnum.*;
import static org.junit.Assert.*;

public class BattleAttributesTest {
    @Test
    public void mixedSoldiersScaleGrowthAndPanelOnceAtEntry() {
        AttrMap growth = new AttrMap("ATK;100");
        AttrMap panel = new AttrMap(growth).addValue("ATK_RATE;200|EQUIP_ATK;30|SOLDIER_ATK_RATE;500|ARM;100");
        AttrMap entry = BattleUnit.calculateEntryAttrs(panel, growth, new BigDecimal("84"), 50);
        assertEquals(201, entry.getFinalValue(ATK));
        assertEquals(150, panel.getFinalValue(ATK));
        assertEquals(100, entry.getFinalValue(ARM));
        assertFalse(entry.battleEntryValues().containsKey(ATK_RATE.getNumber()));
        assertFalse(entry.battleEntryValues().containsKey(EQUIP_ATK.getNumber()));
    }

    @Test
    public void damageRespectsConfiguredGapAndRhythm() {
        Map<AttrTypeEnum, Long> source = Map.of(ATK, 100L, DAMAGE_DEEPEN_RATE, 1000L);
        Map<AttrTypeEnum, Long> target = Map.of(DEF, 100L);
        assertEquals(16, BattleMath.damage(source, target, 1000, new BattleRules.BattleDamageRules(200, 800, 600, 1670)));
        assertEquals(18, BattleMath.damage(source, target, 1000, new BattleRules.BattleDamageRules(200, 800, 600, 2000)));
        assertEquals(33, BattleMath.damage(source, target, 1000, new BattleRules.BattleDamageRules(400, 800, 600, 1670)));
    }
}
