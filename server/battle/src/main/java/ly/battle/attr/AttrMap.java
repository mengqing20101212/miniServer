package ly.battle.attr;

import ly.proto.Battle.AttrTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 属性贡献与派生结果。百分比以千分比存储（11 表示 1.1%），固定值保持自身单位。
 * 养成修改只写 baseAttrs；finalAttrs 中英雄四组和装备三组的基础键为计算后的最终数值。
 * 普通修改只标记过期，读取最终值时按需重算；WithCall 方法立即重算。
 * 战斗入场完成后，可通过 addFinalValue 直接修改运行最终值；基础百分比变化使用
 * addBaseRateAndUpdateFinal，只调整受影响的派生属性，不覆盖其他战斗状态。
 * 实例由所属玩家/对象线程管理，不支持并发修改。对外返回不可变的独立快照。
 *
 * <p>科技、装备、英雄、Buff 等系统可以提供各自的贡献，但本类不记录贡献来源。
 * 同一来源 merge 两次就会累加两次，来源的替换、移除和生命周期由上层系统管理。
 * 英雄四组与装备三组使用 (基础值 + BASE_ADD) * (1000 + RATE) / 1000 + EXT_ADD，最终向下取整。
 * HP/ATK/DEF 另加对应装备组的最终值；装备贡献不乘英雄成长倍率。
 * 战斗专用属性（护盾、加深、抵抗、蓄能加速、临时攻防增幅、兵种增幅）保留原值，
 * 由战斗公式在各自阶段使用，不在这里再次放大 HP/ATK/DEF/ARM。
 * 护盾可用于独立战斗状态表；不能把养成属性重算结果覆盖到已消耗的战斗护盾。
 *
 * <p>所有返回 AttrMap 的修改方法都返回当前实例，支持链式调用。
 * 未知 ID、非法字符串抛出 IllegalArgumentException，空枚举抛出 NullPointerException。
 * 原始贡献累加及最终结果超出 long 范围时抛出 ArithmeticException；公式中间值不溢出。
 * WithCall 先提交原始贡献再重算；派生计算失败不会回滚原始贡献，需修正后重新计算。
 */
public final class AttrMap {
    /**
     * 比例单位：1000 表示 100%；仅用于比例，固定数值不进行此缩放。
     */
    public static final long RATE_SCALE = 1000L;

    /**
     * 唯一的计算输入：所有来源累积的原始贡献，包含固定值及千分比。
     */
    private final EnumMap<AttrTypeEnum, Long> baseAttrs = new EnumMap<>(AttrTypeEnum.class);
    /**
     * 有效属性结果；养成模式下是重算缓存，战斗模式下还包含直接修改的运行状态。
     */
    private EnumMap<AttrTypeEnum, Long> finalAttrs = new EnumMap<>(AttrTypeEnum.class);
    /**
     * 原始贡献更新前置为 true，完整重算成功后才清除。
     */
    private boolean dirty;

    /**
     * 创建空属性表，所有属性读取为零。
     */
    public AttrMap() {
    }

    /**
     * 从配置字符串累积原始贡献，首次读取最终值时再计算。
     */
    public AttrMap(String value) {
        addValue(value);
    }

    /**
     * 复制原始贡献，两个实例互不影响。
     */
    public AttrMap(AttrMap other) {
        baseAttrs.putAll(Objects.requireNonNull(other, "other").baseAttrs);
        dirty = true;
    }

    /**
     * 累加单项原始贡献，不立即重算。允许负数，用于扣除贡献；结果为零时移除该键。
     *
     * @param type  属性，不能为 null
     * @param value 本次增加的值，比例按千分比传入，固定值按自身单位传入
     * @return 当前实例
     */
    public AttrMap addValue(AttrTypeEnum type, long value) {
        Objects.requireNonNull(type, "type");
        long result = Math.addExact(baseAttrs.getOrDefault(type, 0L), value);
        dirty = true;
        if (result == 0) baseAttrs.remove(type);
        else baseAttrs.put(type, result);
        return this;
    }
    public AttrMap addValue(AttrTypeEnum type, double value) {
        return addValue(type,(long)(value * RATE_SCALE));
    }

    /**
     * 按属性 ID 累加；type 是配置 ID，不是枚举 ordinal。
     */
    public AttrMap addValue(int type, long value) {
        return addValue(requireType(type), value);
    }

    /**
     * 批量累加，格式为 HP;1000|HP_RATE;100 或 1;1000|3;100，名称与 ID 可混用。
     * 同名项累加，null 或空白不修改；格式先整体校验，累加溢出时保留已完成的修改。
     */
    public AttrMap addValue(String value) {
        for (Entry entry : parse(value)) {
            addValue(entry.type(), entry.value());
        }
        return this;
    }

    /**
     * 覆盖单项原始贡献，保留其他属性；零值移除该键，不立即重算。
     */
    public AttrMap setValue(AttrTypeEnum type, long value) {
        Objects.requireNonNull(type, "type");
        dirty = true;
        if (value == 0) baseAttrs.remove(type);
        else baseAttrs.put(type, value);
        return this;
    }

    /**
     * 按属性 ID 覆盖单项原始贡献。
     */
    public AttrMap setValue(int type, long value) {
        return setValue(requireType(type), value);
    }

    /**
     * 只覆盖字符串中指定的项，保留其他项；同名项最后一项生效。
     * null 或空白不修改，整批格式校验通过后才提交。需要完整重建时先 clear。
     */
    public AttrMap setValue(String value) {
        for (Entry entry : parse(value)) {
            setValue(entry.type(), entry.value());
        }
        return this;
    }

    /**
     * 仅合并原始贡献，避免对已经计算的最终属性重复加成，不修改来源对象。
     * 支持合并自身，效果为原始贡献翻倍；本方法不做来源去重。
     */
    public AttrMap merge(AttrMap other) {
        Objects.requireNonNull(other, "other");
        for (Map.Entry<AttrTypeEnum, Long> entry : other.baseAttrs.entrySet()) {
            addValue(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 删除指定原始贡献；最终值仍可能包含基础加值及额外加成，并不一定为零。
     */
    public AttrMap removeValue(AttrTypeEnum type) {
        Objects.requireNonNull(type, "type");
        dirty = true;
        baseAttrs.remove(type);
        return this;
    }

    /**
     * 按属性 ID 删除原始贡献，不存在的合法属性不改变原始值。
     */
    public AttrMap removeValue(int type) {
        return removeValue(requireType(type));
    }

    /**
     * 清空全部原始贡献；下一次读取最终值会清除旧的派生结果。
     */
    public AttrMap clear() {
        dirty = true;
        baseAttrs.clear();
        return this;
    }

    /**
     * 读取原始贡献，不触发重算；不存在时返回零。
     */
    public long getBaseValue(AttrTypeEnum type) {
        return baseAttrs.getOrDefault(Objects.requireNonNull(type, "type"), 0L);
    }

    /**
     * 按属性 ID 读取原始贡献；未知 ID 抛出异常，不作为零值处理。
     */
    public long getBaseValue(int type) {
        return getBaseValue(requireType(type));
    }

    /**
     * 读取有效属性，dirty 时先完整重算，不存在时返回零。
     * HP/ATK/DEF/ARM 返回公式计算后的整数；BASE_ADD、RATE、EXT_ADD 保留原始贡献。
     */
    public long getFinalValue(AttrTypeEnum type) {
        Objects.requireNonNull(type, "type");
        ensureCalculated();
        return finalAttrs.getOrDefault(type, 0L);
    }

    /**
     * 按属性 ID 读取有效属性，必要时自动重算。
     */
    public long getFinalValue(int type) {
        return getFinalValue(requireType(type));
    }

    /** 战中直接修改一项最终属性；不修改基础贡献，也不使整表缓存过期。 */
    public void addFinalValue(AttrTypeEnum type, long delta) {
        Objects.requireNonNull(type, "type");
        ensureCalculated();
        if (delta == 0) {
            return;
        }
        long next = Math.addExact(finalAttrs.getOrDefault(type, 0L), delta);
        if (next == 0) {
            finalAttrs.remove(type);
        } else {
            finalAttrs.put(type, next);
        }
    }

    /**
     * 战中基础RATE变化：修改基础倍率，并将该倍率导致的实际派生值差额加到最终属性。
     * 已直接写入最终属性的扣血或固定加成不会被通用整表重算覆盖。
     * 返回本次应写入战报的RATE和派生属性增量。
     */
    public Map<AttrTypeEnum, Long> addBaseRateAndUpdateFinal(AttrTypeEnum rateType, long delta) {
        Objects.requireNonNull(rateType, "rateType");
        AttrTypeEnum derivedType;
        switch (rateType) {
            case HP_RATE -> derivedType = AttrTypeEnum.HP;
            case ATK_RATE -> derivedType = AttrTypeEnum.ATK;
            case DEF_RATE -> derivedType = AttrTypeEnum.DEF;
            case ARM_RATE -> derivedType = AttrTypeEnum.ARM;
            default -> throw new IllegalArgumentException("不是基础属性倍率：" + rateType);
        }
        ensureCalculated();
        if (delta == 0) {
            return Map.of();
        }
        long newRate = Math.addExact(baseAttrs.getOrDefault(rateType, 0L), delta);
        long oldDerived = AttrCalculator.calculateDerived(baseAttrs, derivedType);
        EnumMap<AttrTypeEnum, Long> changedBase = new EnumMap<>(baseAttrs);
        if (newRate == 0) {
            changedBase.remove(rateType);
        } else {
            changedBase.put(rateType, newRate);
        }
        long newDerived = AttrCalculator.calculateDerived(changedBase, derivedType);
        long derivedDelta = Math.subtractExact(newDerived, oldDerived);
        long finalDerived = Math.addExact(finalAttrs.getOrDefault(derivedType, 0L), derivedDelta);
        long finalRate = Math.addExact(finalAttrs.getOrDefault(rateType, 0L), delta);

        if (newRate == 0) {
            baseAttrs.remove(rateType);
        } else {
            baseAttrs.put(rateType, newRate);
        }
        if (finalDerived == 0) {
            finalAttrs.remove(derivedType);
        } else {
            finalAttrs.put(derivedType, finalDerived);
        }
        if (finalRate == 0) {
            finalAttrs.remove(rateType);
        } else {
            finalAttrs.put(rateType, finalRate);
        }
        EnumMap<AttrTypeEnum, Long> changes = new EnumMap<>(AttrTypeEnum.class);
        changes.put(rateType, delta);
        if (derivedDelta != 0) {
            changes.put(derivedType, derivedDelta);
        }
        return changes;
    }

    /**
     * 返回原始贡献的只读快照，不随实例的后续修改变化；快照中省略零值键。
     */
    public Map<AttrTypeEnum, Long> getBaseAttrs() {
        return Collections.unmodifiableMap(new EnumMap<>(baseAttrs));
    }

    /**
     * 必要时重算后返回有效属性的只读快照，快照不随后续修改变化，省略零值键。
     */
    public Map<AttrTypeEnum, Long> getFinalAttrs() {
        ensureCalculated();
        return Collections.unmodifiableMap(new EnumMap<>(finalAttrs));
    }

    /** 快照只冻结结算后的运行属性，装备贡献和成长倍率不随英雄进入下一次计算。 */
    public Map<Integer, Long> battleEntryValues() {
        ensureCalculated();
        Map<Integer, Long> values = new HashMap<>();
        for (Map.Entry<AttrTypeEnum, Long> entry : finalAttrs.entrySet()) {
            if (isBattleEntryAttribute(entry.getKey())) {
                values.put(entry.getKey().getNumber(), entry.getValue());
            }
        }
        return values;
    }

    public static boolean isBattleEntryAttribute(AttrTypeEnum type) {
        return switch (type) {
            case HP, ATK, DEF, ARM, SHIELD, DAMAGE_DEEPEN_RATE, DAMAGE_RESIST_RATE, SKILL_ENERGY_GAIN_RATE,
                    DAMAGE_REDUCTION_RATE, ATTACK_BOOST_RATE, SOLDIER_ATK_RATE, SOLDIER_DEF_RATE, SOLDIER_HP_RATE -> true;
            case null, default -> false;
        };
    }

    /**
     * 返回结果缓存是否需要刷新；此查询本身不触发计算。
     */
    public boolean isDirty() {
        return dirty;
    }

    /**
     * 强制从原始贡献完整计算，即使未标记 dirty 也执行；重复调用不会重复叠加。
     * 全部计算成功后才替换结果。溢出时不发布半成品，已过期的缓存仍保持过期状态。
     */
    public AttrMap recalculate() {
        EnumMap<AttrTypeEnum, Long> next = AttrCalculator.calculate(baseAttrs);
        finalAttrs = next;
        dirty = false;
        return this;
    }

    /**
     * 兼容最初方案中的命名，新代码也可直接用 recalculate。
     */
    public AttrMap call() {
        recalculate();
        return this;
    }

    /**
     * 累加单项原始贡献后立即完整重算。
     */
    public AttrMap addValueWithCall(AttrTypeEnum type, long value) {
        return addValue(type, value).recalculate();
    }

    /**
     * 按属性 ID 累加后立即完整重算。
     */
    public AttrMap addValueWithCall(int type, long value) {
        return addValue(type, value).recalculate();
    }

    /**
     * 整批字符串累加完成后只重算一次，不为每个配置项分别重算。
     */
    public AttrMap addValueWithCall(String value) {
        return addValue(value).recalculate();
    }

    /**
     * 覆盖单项原始贡献后立即完整重算。
     */
    public AttrMap setValueWithCall(AttrTypeEnum type, long value) {
        return setValue(type, value).recalculate();
    }

    /**
     * 按属性 ID 覆盖后立即完整重算。
     */
    public AttrMap setValueWithCall(int type, long value) {
        return setValue(type, value).recalculate();
    }

    /**
     * 批量覆盖指定项后只重算一次，保留字符串未提及的原始贡献。
     */
    public AttrMap setValueWithCall(String value) {
        return setValue(value).recalculate();
    }

    /**
     * 合并来源的原始贡献后立即完整重算，不读取来源的最终结果。
     */
    public AttrMap mergeWithCall(AttrMap other) {
        return merge(other).recalculate();
    }

    /**
     * 删除指定原始贡献后立即重算，同时刷新受其影响的最终属性。
     */
    public AttrMap removeValueWithCall(AttrTypeEnum type) {
        return removeValue(type).recalculate();
    }

    /**
     * 按属性 ID 删除原始贡献后立即完整重算。
     */
    public AttrMap removeValueWithCall(int type) {
        return removeValue(type).recalculate();
    }

    /**
     * 清空原始贡献并立即生成空结果，正常返回时 dirty 为 false。
     */
    public AttrMap clearWithCall() {
        return clear().recalculate();
    }

    /**
     * 统一的读取屏障：只允许读取与当前原始贡献对应的完整结果。
     */
    private void ensureCalculated() {
        if (dirty) recalculate();
    }

    /**
     * 校验外部传入的配置 ID，避免拼错或配置遗漏被静默忽略。
     */
    private static AttrTypeEnum requireType(int id) {
        AttrTypeEnum type = AttrTypeEnum.forNumber(id);
        if (id == 0 || type == null) throw new IllegalArgumentException("Unknown AttrTypeEnum id: " + id);
        return type;
    }

    /**
     * 解析全部配置项，不修改属性表。保留重复项和原顺序，由 add/set 决定叠加或覆盖。
     * 分隔时保留空项，以便拒绝末尾多余的竖线、缺失数值等不完整配置。
     */
    private static List<Entry> parse(String value) {
        if (value == null || value.isBlank()) return List.of();
        List<Entry> entries = new ArrayList<>();
        for (String item : value.split("\\|", -1)) {
            String[] parts = item.split(";", -1);
            if (parts.length != 2) throw new IllegalArgumentException("Invalid attribute entry: " + item);
            try {
                String key = parts[0].trim();
                AttrTypeEnum type = !key.isEmpty() && Character.isLetter(key.charAt(0))
                        ? requireType(AttrTypeEnum.valueOf(key).getNumber()) : requireType(Integer.parseInt(key));
                entries.add(new Entry(type, Long.parseLong(parts[1].trim())));
            } catch (IllegalArgumentException error) {
                throw new IllegalArgumentException("Invalid attribute entry: " + item, error);
            }
        }
        return entries;
    }

    /**
     * 已校验的单条原始贡献，仅在一次字符串批量操作中使用。
     */
    private record Entry(AttrTypeEnum type, long value) {
    }
}
