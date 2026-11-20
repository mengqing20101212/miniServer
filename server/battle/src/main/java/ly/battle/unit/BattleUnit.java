package ly.battle.unit;

import ly.battle.BattleRules;
import ly.battle.attr.AttrMap;
import ly.battle.Stage;
import ly.battle.buff.BaseBuff;
import ly.battle.event.BattleEvent;
import ly.battle.skill.BaseSkill;
import ly.proto.Battle.AttrTypeEnum;
import ly.proto.Battle.BattleBuffSpec;
import ly.proto.Battle.PRT_BATTLE_UNIT_STATE;
import ly.proto.Battle.PRT_BATTLE_EFFECT_TYPE;
import ly.proto.Battle.PRT_BATTLE_BUFF_STATE;
import ly.proto.Battle.PRT_ATTR;
import ly.proto.Battle.PRT_ATTR_MAP;
import ly.proto.Battle.PRT_HERO;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import ly.proto.Battle.BattleUnitSnapshot;
import ly.proto.Battle.PRT_FORMATION_SOLDIER;
import java.util.HashSet;
import java.util.Set;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static ly.proto.Battle.AttrTypeEnum.*;
/** 独立战斗属性表：首次入场结算兵种与兵力比例，构造及快照恢复只复制运行属性 */

public final class BattleUnit extends BaseUnit {
    /**
     * 战前面板已包含英雄Buff及独立装备贡献，只补入兵种贡献并按实际兵力缩放。
     * soldierMultiplierTotal 为各兵阶的“直接倍率 × 实际人数”之和，避免混编平均倍率取整。
     * 此方法仅在首次入场前调用；快照恢复直接使用已经计算的入场属性。
     */
    public static AttrMap calculateEntryAttrs(AttrMap panel, AttrMap heroGrowthAttrs,
            BigDecimal soldierMultiplierTotal, long initialSoldiers) {
        long capacity = panel.getFinalValue(ARM);
        if (capacity <= 0 || initialSoldiers <= 0 || initialSoldiers > capacity
                || soldierMultiplierTotal.signum() < 0) {
            throw new IllegalArgumentException("入场兵力或兵种倍率非法");
        }
        AttrMap entryAttrs = new AttrMap();
        // 只复制运行属性，不能携带已结算的装备或养成RATE，避免再次加成。
        for (Map.Entry<AttrTypeEnum, Long> entry : panel.getFinalAttrs().entrySet()) {
            AttrTypeEnum type = entry.getKey();
            if (type == ARM || (type.getNumber() >= SHIELD.getNumber() && type.getNumber() <= SKILL_ENERGY.getNumber())) {
                entryAttrs.setValue(type, entry.getValue().longValue());
            }
        }
        AttrTypeEnum[] attributes = {HP, ATK, DEF};
        AttrTypeEnum[] soldierRates = {SOLDIER_HP_RATE, SOLDIER_ATK_RATE, SOLDIER_DEF_RATE};
        BigDecimal rateScale = BigDecimal.valueOf(AttrMap.RATE_SCALE);
        for (int index = 0; index < attributes.length; index++) {
            AttrTypeEnum type = attributes[index];
            BigDecimal soldierBuff = rateScale.add(BigDecimal.valueOf(panel.getFinalValue(soldierRates[index])));
            if (soldierBuff.signum() < 0) {
                throw new IllegalArgumentException("兵种属性增幅不能小于-100%，attribute=" + type);
            }
            BigDecimal soldierContribution = BigDecimal.valueOf(heroGrowthAttrs.getFinalValue(type))
                    .multiply(soldierMultiplierTotal).multiply(soldierBuff).divide(rateScale);
            BigDecimal heroContribution = BigDecimal.valueOf(panel.getFinalValue(type))
                    .multiply(BigDecimal.valueOf(initialSoldiers));
            long value = heroContribution.add(soldierContribution)
                    .divide(BigDecimal.valueOf(capacity), 0, RoundingMode.FLOOR).longValueExact();
            entryAttrs.setValue(type, value);
        }
        return entryAttrs;
    }

    // 入场面板的HP在本场作为最大生命；CURRENT_HP是战中剩余生命，开战时等于HP。
    // 伤害和治疗只改CURRENT_HP，不改HP；护盾单独存于SHIELD。
    private final AttrMap attrs = new AttrMap();
    private final int pos;
    private final boolean attacker;
    private final int type;
    private final PRT_HERO hero;
    private final int soldierDid;
    private final long initialSoldiers;
    private final List<PRT_FORMATION_SOLDIER> soldiers;
    private final int monsterTid;
    private final List<BaseSkill> skills;
    private final List<BaseBuff> buffs = new ArrayList<>();

    public BattleUnit(long id, boolean attacker, BattleUnitSnapshot snapshot, List<BaseSkill> skills) {
        super(id, snapshot.getHero().getHeroTid());
        this.pos = snapshot.getPosition();
        this.attacker = attacker;
        this.type = snapshot.getUnitType();
        this.hero = snapshot.getHero();
        this.monsterTid = snapshot.getMonsterTid();
        this.skills = List.copyOf(skills);
        this.soldiers = snapshot.getSoldiersList();
        if (hero.getHeroTid() <= 0 || pos < BattleRules.BATTLE_MIN_POSITION || pos > BattleRules.BATTLE_MAX_POSITION
                || type < BattleRules.BATTLE_HERO_UNIT_TYPE || type > BattleRules.BATTLE_MONSTER_UNIT_TYPE) {
            throw new IllegalArgumentException("战斗单位入场数据无效");
        }
        this.soldierDid = soldiers.size() == 1 ? soldiers.getFirst().getSoldierDid() : 0;
        this.initialSoldiers = countSoldiers(soldiers);
        for (Map.Entry<Integer, Long> entry : snapshot.getEntryAttrsMap().entrySet()) {
            AttrTypeEnum key = AttrTypeEnum.forNumber(entry.getKey());
            if (!AttrMap.isBattleEntryAttribute(key)) {
                throw new IllegalArgumentException("快照需要已结算的入场属性，非法属性 ID：" + entry.getKey());
            }
            attrs.setValue(key, entry.getValue());
        }
        if (getAttrValue(HP) <= 0 || getAttrValue(ATK) <= 0 || getAttrValue(DEF) < 0 || getAttrValue(ARM) <= 0) {
            throw new IllegalArgumentException("入场最大HP/攻击/带兵量必须大于0，防御不得为负");
        }
        if (initialSoldiers > getAttrValue(ARM) || getAttrValue(SHIELD) < 0
                || getAttrValue(ATTACK_BOOST_RATE) < -BattleRules.BATTLE_RATE_BASE
                || getAttrValue(DAMAGE_REDUCTION_RATE) < 0
                || getAttrValue(DAMAGE_REDUCTION_RATE) > BattleRules.BATTLE_RATE_BASE
                || getAttrValue(SKILL_ENERGY_GAIN_RATE) < -BattleRules.BATTLE_RATE_BASE) {
            throw new IllegalArgumentException("入场兵数或战斗比例越界");
        }
        long hp = snapshot.hasRemainingHp() ? snapshot.getRemainingHp() : getAttrValue(HP);
        int energy = snapshot.getInitialEnergy();
        if (hp < 0 || hp > getAttrValue(HP) || energy < 0 || energy > BattleRules.BATTLE_MAX_ENERGY) {
            throw new IllegalArgumentException("初始生命或蓄能越界");
        }
        attrs.setValue(CURRENT_HP, hp);
        attrs.setValue(SKILL_ENERGY, energy);
        if (getSkill(BattleRules.BATTLE_NORMAL_SKILL_SLOT) == null) {
            throw new IllegalArgumentException("缺少普攻技能");
        }
    }

    public long getAttrValue(AttrTypeEnum type) { return attrs.getFinalValue(type); }
    /** 将有符号增量累加到本单位属性表；Buff生命周期自行记录对应事件。 */
    public void addAttrDeltas(Map<AttrTypeEnum, Long> delta) {
        for (Map.Entry<AttrTypeEnum, Long> entry : delta.entrySet()) {
            attrs.addFinalValue(entry.getKey(), entry.getValue());
        }
    }
    /** 累加技能或加能产生的实际属性增量，并在同一时点记录战报事件。 */
    public void addAttrDeltasAndRecord(Stage stage, long source, PRT_BATTLE_EFFECT_TYPE type,
                                       Map<AttrTypeEnum, Long> delta) {
        addAttrDeltas(delta);
        recordEffect(stage, source, type, delta, List.of(), List.of(), List.of());
    }
    /** 记录本单位实际落地的属性或Buff效果；增量和Buff状态在当前操作点写入战报。 */
    public void recordEffect(Stage stage, long sourceId, PRT_BATTLE_EFFECT_TYPE type,
                             Map<AttrTypeEnum, Long> deltaAttr, List<PRT_BATTLE_BUFF_STATE> addBuffList,
                             List<PRT_BATTLE_BUFF_STATE> updateBuffList, List<Long> delBuffList) {
        EnumMap<AttrTypeEnum, Long> nonzero = new EnumMap<>(AttrTypeEnum.class);
        for (Map.Entry<AttrTypeEnum, Long> entry : deltaAttr.entrySet()) {
            if (entry.getValue() != 0) {
                nonzero.put(entry.getKey(), entry.getValue());
            }
        }
        stage.updateAliveUnit(this);
        stage.emit(new BattleEvent.Effect(sourceId, getId(), type, nonzero, addBuffList, updateBuffList, delBuffList));
    }

    /** 主动技能开始结算时扣除一次蓄能，并以技能实例记录属性变化。 */
    public void subEnergy(Stage stage, long skillId) {
        long cost = BattleRules.BATTLE_SKILL_ENERGY_COST;
        addAttrDeltasAndRecord(stage, skillId, PRT_BATTLE_EFFECT_TYPE.EFFECT_ATTRIBUTE,
                Map.of(SKILL_ENERGY, -cost));
    }
    /** 同效果Buff刷新时长而不叠加数值；新Buff由本单位创建并交给Buff生命周期登记事件。 */
    public void addOrRefreshBuff(Stage stage, BattleBuffSpec spec, long sourceId) {
        if (!isAlive()) {
            return;
        }
        BaseBuff existing = null;
        for (BaseBuff buff : buffs) {
            if (buff.getSpec().getEffectType() == spec.getEffectType()) {
                existing = buff;
                break;
            }
        }
        if (existing == null) {
            long buffId = stage.nextId();
            BaseBuff buff = BaseBuff.create(buffId, spec, sourceId, stage.getRound());
            buffs.add(buff);
            buff.onAdded(stage, this);
        } else {
            existing.onRefreshed(stage, this, spec, sourceId);
        }
    }
    /** 到期移除前再次确认时长，因为前一个Buff的移除效果可能刷新本实例。 */
    public void removeExpiredBuff(Stage stage, BaseBuff buff) {
        if (buff.getRemainingRounds() > 0) {
            return;
        }
        if (buffs.remove(buff)) {
            buff.onRemoved(stage, this);
        }
    }
    /** 嘲讽是否生效由持有者检查，技能不直接遍历本单位的Buff列表。 */
    public boolean isTaunting() {
        for (BaseBuff buff : buffs) {
            if (buff.isTaunting()) {
                return true;
            }
        }
        return false;
    }
    public boolean isAlive() { return getAttrValue(CURRENT_HP) > 0; }
    public int getPos() { return pos; }
    public boolean isAttacker() { return attacker; }
    public int getType() { return type; }
    public PRT_HERO getHero() { return hero; }
    private static long countSoldiers(List<PRT_FORMATION_SOLDIER> soldiers) {
        long total = 0;
        Set<Integer> seen = new HashSet<>();
        for (PRT_FORMATION_SOLDIER soldier : soldiers) {
            if (soldier.getSoldierDid() <= 0 || soldier.getCount() <= 0 || !seen.add(soldier.getSoldierDid())) {
                throw new IllegalArgumentException("战斗入场分阶兵数非法");
            }
            total = Math.addExact(total, soldier.getCount());
        }
        return total;
    }

    public List<PRT_FORMATION_SOLDIER> getSoldiers() { return soldiers; }

    public int getSoldierDid() { return soldierDid; }
    public long getInitialSoldiers() { return initialSoldiers; }
    public int getMonsterTid() { return monsterTid; }
    public List<BaseBuff> getBuffs() { return buffs; }
    public List<BaseSkill> getSkills() { return skills; }
    public Map<AttrTypeEnum, Long> getFinalAttrs() { return attrs.getFinalAttrs(); }
    public BaseSkill getSkill(int slot) {
        for (BaseSkill skill : skills) {
            if (skill.getSlot() == slot) {
                return skill;
            }
        }
        return null;
    }
    /** 在调用时生成完整协议状态，用于入场或战斗结束后的记录点。 */
    public PRT_BATTLE_UNIT_STATE toPb() {
        PRT_ATTR_MAP.Builder attributes = PRT_ATTR_MAP.newBuilder();
        Map<AttrTypeEnum, Long> values = getFinalAttrs();
        for (AttrTypeEnum attrType : AttrTypeEnum.values()) {
            long amount = values.getOrDefault(attrType, 0L);
            if (amount != 0) {
                PRT_ATTR.Builder attr = PRT_ATTR.newBuilder();
                attr.setAttrType(attrType.getNumber());
                attr.setAtrrVal(amount);
                attributes.addFinalAttrs(attr);
            }
        }
        PRT_BATTLE_UNIT_STATE.Builder state = PRT_BATTLE_UNIT_STATE.newBuilder();
        state.setUnitId(getId());
        state.setAttr(attributes);
        for (BaseBuff buff : buffs) { state.addBuffs(buff.toPb()); }
        return state.build();
    }
}
