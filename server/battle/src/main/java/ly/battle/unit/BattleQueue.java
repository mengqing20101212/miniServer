package ly.battle.unit;

import ly.battle.BattleRules;
import java.util.ArrayList;
import java.util.List;
import static ly.proto.Battle.AttrTypeEnum.SKILL_ENERGY;

/** 单方参战单位及本回合技能出手顺序 */
public class BattleQueue {
    private final List<BattleUnit> battleUnitList = new ArrayList<>(BattleRules.BATTLE_MAX_POSITION);

    /** 入场时登记本方单位；位置和英雄去重由Stage统一校验。 */
    public void add(BattleUnit unit) {
        battleUnitList.add(unit);
    }

    /**
     * 回合加能后生成技能队列：达到100能量且有主动技能的存活单位才能入队。
     * 能量降序、位置升序；返回的新列表在本回合固定，阵亡只跳过原位，不重新排序或补位。
     */
    public List<BattleUnit> skillOrder() {
        List<BattleUnit> result = new ArrayList<>();
        for (BattleUnit unit : battleUnitList) {
            if (unit.isAlive() && unit.getAttrValue(SKILL_ENERGY) >= BattleRules.BATTLE_SKILL_ENERGY_COST
                    && unit.getSkill(BattleRules.BATTLE_ACTIVE_SKILL_SLOT) != null) {
                result.add(unit);
            }
        }
        result.sort((left, right) -> {
            int energy = Long.compare(right.getAttrValue(SKILL_ENERGY), left.getAttrValue(SKILL_ENERGY));
            if (energy != 0) {
                return energy;
            }
            return Integer.compare(left.getPos(), right.getPos());
        });
        return result;
    }
}
