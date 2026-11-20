package ly.battle;

import ly.proto.Battle.AttrTypeEnum;
import java.math.BigInteger;
import java.util.Map;
import static ly.proto.Battle.AttrTypeEnum.*;
/** 非负战斗整数运算；完整伤害公式只在末尾向下取整，中间值不溢出。 */

public final class BattleMath {
    private static final BigInteger SCALE = BigInteger.valueOf(BattleRules.BATTLE_RATE_BASE);
    private BattleMath() {}

    /** 非负整数乘除：乘法使用大整数防溢出，仅在除法处向下取整，结果越界则抛异常。 */
    public static long multiplyDivide(long value, long rate, long divisor) {
        if (value < 0 || rate < 0 || divisor <= 0) {
            throw new IllegalArgumentException("战斗数值或除数无效");
        }
        BigInteger product = BigInteger.valueOf(value).multiply(BigInteger.valueOf(rate));
        return product.divide(BigInteger.valueOf(divisor)).longValueExact();
    }

    /** 在已算好的ATK上叠加战中攻击提升，千分比换算后取整，不再次计算英雄养成RATE。 */
    public static long attack(Map<AttrTypeEnum, Long> attrs) {
        long multiplier = Math.addExact(BattleRules.BATTLE_RATE_BASE, attrs.getOrDefault(ATTACK_BOOST_RATE, 0L));
        return multiplyDivide(attrs.getOrDefault(ATK, 0L), Math.max(0, multiplier), BattleRules.BATTLE_RATE_BASE);
    }

    /**
     * A²/(A+D) × 配置的伤害节奏系数 × 技能倍率 × 增伤抗性系数 × 减伤系数。
     * A使用运行攻击；除A的属性计算外，伤害公式各项保留分数，最后统一向下取整。
     * 本方法计算未截断伤害，实际护盾吸收量和扣血量由技能落地时限制。
     */
    public static long damage(Map<AttrTypeEnum, Long> source, Map<AttrTypeEnum, Long> target,
                              int rate, BattleRules.BattleDamageRules rules) {
        BigInteger attack = BigInteger.valueOf(attack(source));
        BigInteger defense = BigInteger.valueOf(target.getOrDefault(DEF, 0L));
        BigInteger denominator = attack.add(defense);
        if (denominator.signum() <= 0 || rate <= 0) {
            throw new IllegalArgumentException("伤害公式A+D或倍率无效");
        }
        // 保留配置敏感度乘算的精度：gap的分母为一百万，避免提前截断。
        BigInteger difference = BigInteger.valueOf(source.getOrDefault(DAMAGE_DEEPEN_RATE, 0L));
        difference = difference.subtract(BigInteger.valueOf(target.getOrDefault(DAMAGE_RESIST_RATE, 0L)));
        BigInteger gap = BigInteger.valueOf(BattleRules.BATTLE_DAMAGE_GAP_BASE);
        gap = gap.add(difference.multiply(BigInteger.valueOf(rules.sensitivity())));
        long gapMin = (long) rules.gapMin() * BattleRules.BATTLE_RATE_BASE;
        long gapMax = (long) rules.gapMax() * BattleRules.BATTLE_RATE_BASE;
        gap = gap.max(BigInteger.valueOf(gapMin));
        gap = gap.min(BigInteger.valueOf(gapMax));
        long reduction = target.getOrDefault(DAMAGE_REDUCTION_RATE, 0L);
        reduction = Math.max(0, Math.min(BattleRules.BATTLE_RATE_BASE, reduction));
        BigInteger numerator = attack.multiply(attack);
        numerator = numerator.multiply(BigInteger.valueOf(rules.rhythm()));
        numerator = numerator.multiply(BigInteger.valueOf(rate));
        numerator = numerator.multiply(gap);
        numerator = numerator.multiply(BigInteger.valueOf(BattleRules.BATTLE_RATE_BASE - reduction));
        // 伤害节奏系数、技能倍率、减伤各占一个千分母，gap占两个，共计1000的五次方。
        denominator = denominator.multiply(SCALE.pow(BattleRules.BATTLE_DAMAGE_DIVISOR_POWER));
        return numerator.divide(denominator).longValueExact();
    }
}
