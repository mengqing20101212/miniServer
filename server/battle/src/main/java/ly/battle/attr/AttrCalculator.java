package ly.battle.attr;

import ly.proto.Battle.AttrTypeEnum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EnumMap;
import java.util.Map;

import static ly.proto.Battle.AttrTypeEnum.*;

/**
 * 英雄四组与装备三组属性共用加成公式，只读原始贡献，每次生成独立结果。
 * 每组最终值 = FLOOR((基础值 + BASE_ADD) * (1000 + RATE) / 1000 + EXT_ADD)。
 * 英雄攻防血另外累加对应装备组的最终值，装备不乘英雄成长倍率。
 * BASE_ADD、RATE、EXT_ADD 在结果表中仍保持原始贡献及原单位。
 */
final class AttrCalculator {
    private static final BigDecimal SCALE = BigDecimal.valueOf(AttrMap.RATE_SCALE);

    private AttrCalculator() {}

    static EnumMap<AttrTypeEnum, Long> calculate(Map<AttrTypeEnum, Long> base) {
        EnumMap<AttrTypeEnum, Long> result = new EnumMap<>(AttrTypeEnum.class);
        for (AttrTypeEnum type : AttrTypeEnum.values()) {
            // 不设 default：新增枚举时必须明确其计算规则。
            long value = switch (type) {
                case ATTR_UNSPECIFIED, UNRECOGNIZED -> 0L;
                case HP -> calculate(base, HP, HP_BASE_ADD, HP_RATE, HP_EXT_ADD);
                case ATK -> calculate(base, ATK, ATK_BASE_ADD, ATK_RATE, ATK_EXT_ADD);
                case DEF -> calculate(base, DEF, DEF_BASE_ADD, DEF_RATE, DEF_EXT_ADD);
                case ARM -> calculate(base, ARM, ARM_BASE_ADD, ARM_RATE, ARM_EXT_ADD);
                case EQUIP_HP -> calculate(base, EQUIP_HP, EQUIP_HP_BASE_ADD, EQUIP_HP_RATE, EQUIP_HP_EXT_ADD);
                case EQUIP_ATK -> calculate(base, EQUIP_ATK, EQUIP_ATK_BASE_ADD, EQUIP_ATK_RATE, EQUIP_ATK_EXT_ADD);
                case EQUIP_DEF -> calculate(base, EQUIP_DEF, EQUIP_DEF_BASE_ADD, EQUIP_DEF_RATE, EQUIP_DEF_EXT_ADD);
                case HP_BASE_ADD, HP_RATE, HP_EXT_ADD,
                     ATK_BASE_ADD, ATK_RATE, ATK_EXT_ADD,
                     DEF_BASE_ADD, DEF_RATE, DEF_EXT_ADD,
                     ARM_BASE_ADD, ARM_RATE, ARM_EXT_ADD,
                     EQUIP_HP_BASE_ADD, EQUIP_HP_RATE, EQUIP_HP_EXT_ADD,
                     EQUIP_ATK_BASE_ADD, EQUIP_ATK_RATE, EQUIP_ATK_EXT_ADD,
                     EQUIP_DEF_BASE_ADD, EQUIP_DEF_RATE, EQUIP_DEF_EXT_ADD,
                     SHIELD, DAMAGE_DEEPEN_RATE, DAMAGE_RESIST_RATE, SKILL_ENERGY_GAIN_RATE,
                     DAMAGE_REDUCTION_RATE, ATTACK_BOOST_RATE,
                     SOLDIER_ATK_RATE, SOLDIER_DEF_RATE, SOLDIER_HP_RATE, CURRENT_HP, SKILL_ENERGY -> base.getOrDefault(type, 0L);
            };
            if (value != 0) result.put(type, value);
        }
        return result;
    }

    /** 战斗中基础百分比变化时只计算受影响的派生属性。 */
    static long calculateDerived(Map<AttrTypeEnum, Long> base, AttrTypeEnum type) {
        return switch (type) {
            case HP -> calculate(base, HP, HP_BASE_ADD, HP_RATE, HP_EXT_ADD);
            case ATK -> calculate(base, ATK, ATK_BASE_ADD, ATK_RATE, ATK_EXT_ADD);
            case DEF -> calculate(base, DEF, DEF_BASE_ADD, DEF_RATE, DEF_EXT_ADD);
            case ARM -> calculate(base, ARM, ARM_BASE_ADD, ARM_RATE, ARM_EXT_ADD);
            default -> throw new IllegalArgumentException("不是派生属性：" + type);
        };
    }

    /** 使用精确十进制计算，避免中间加法/乘法溢出；只在完整公式后取整并检查 long 范围。 */
    private static long calculate(Map<AttrTypeEnum, Long> base, AttrTypeEnum attr,
                                  AttrTypeEnum baseAdd, AttrTypeEnum rate, AttrTypeEnum extAdd) {
        return value(base, attr).add(value(base, baseAdd))
                .multiply(SCALE.add(value(base, rate))).divide(SCALE)
                .add(value(base, extAdd)).add(equipmentValue(base, attr)).setScale(0, RoundingMode.FLOOR).longValueExact();
    }

    /** 装备先结算自身三组加成，再加入英雄成长结果，不乘英雄成长倍率。 */
    private static BigDecimal equipmentValue(Map<AttrTypeEnum, Long> base, AttrTypeEnum attr) {
        switch (attr) {
            case HP: return BigDecimal.valueOf(calculate(base, EQUIP_HP, EQUIP_HP_BASE_ADD, EQUIP_HP_RATE, EQUIP_HP_EXT_ADD));
            case ATK: return BigDecimal.valueOf(calculate(base, EQUIP_ATK, EQUIP_ATK_BASE_ADD, EQUIP_ATK_RATE, EQUIP_ATK_EXT_ADD));
            case DEF: return BigDecimal.valueOf(calculate(base, EQUIP_DEF, EQUIP_DEF_BASE_ADD, EQUIP_DEF_RATE, EQUIP_DEF_EXT_ADD));
            default: return BigDecimal.ZERO;
        }
    }

    private static BigDecimal value(Map<AttrTypeEnum, Long> base, AttrTypeEnum type) {
        return BigDecimal.valueOf(base.getOrDefault(type, 0L));
    }
}
