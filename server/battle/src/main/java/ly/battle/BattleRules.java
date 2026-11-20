package ly.battle;

public final class BattleRules {
    private BattleRules() {}

    public static final int BATTLE_MIN_POSITION = 1;
    public static final int BATTLE_MAX_POSITION = 5;
    public static final int BATTLE_FRONT_ROW_MAX_POSITION = 2;
    public static final int BATTLE_MIN_ROUND_LIMIT = 1;
    public static final int BATTLE_MAX_ROUND_LIMIT = 100;
    public static final int BATTLE_NORMAL_SKILL_SLOT = 0;
    public static final int BATTLE_ACTIVE_SKILL_SLOT = 1;
    public static final int BATTLE_MAX_SKILL_SLOT = 5;
    public static final int BATTLE_HERO_UNIT_TYPE = 0;
    public static final int BATTLE_MONSTER_UNIT_TYPE = 1;
    public static final int BATTLE_RATE_BASE = 1000;
    public static final int BATTLE_ROUND_ENERGY_GAIN = 20;
    public static final int BATTLE_SKILL_ENERGY_COST = 100;
    public static final int BATTLE_MAX_ENERGY = 200;
    public static final int BATTLE_BUFF_IMMEDIATE = 1;
    public static final int BATTLE_DAMAGE_DIVISOR_POWER = 5;
    public static final long BATTLE_DAMAGE_GAP_BASE = (long) BATTLE_RATE_BASE * BATTLE_RATE_BASE;

    public record BattleDamageRules(int rhythm, int sensitivity, int gapMin, int gapMax) {
        public BattleDamageRules {
            if (rhythm <= 0 || sensitivity < 0 || gapMin <= 0 || gapMax < gapMin) {
                throw new IllegalArgumentException("战斗公式常量范围无效");
            }
        }
    }
}
