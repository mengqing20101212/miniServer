package ly.bot.action.impl;

import java.util.Random;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;

/**
 * 战斗模拟动作。
 *
 * <p>当前项目还没有战斗协议，CombatModule 不能继续复用 MoveAction，否则 Bot
 * 报告里的移动次数和战斗次数会互相污染。这里先只维护 combat 数据域，等真实战斗协议
 * 接入后，再把发包和回包校验集中替换到这个 Action 内。</p>
 */
public class SimulatedCombatAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final int SIMULATED_COMBAT_CMD = 0;

    private final Random random = new Random();

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            int actionCount = nextActionCount(context);
            int damage = 8 + random.nextInt(8);
            int opponentHp = Math.max(0, 100 - actionCount * damage);
            boolean hit = random.nextInt(100) >= 20;

            context.getDataStore().put("combat", "lastCombatActionTime", System.currentTimeMillis());
            context.getDataStore().put("combat", "combatActionCount", actionCount);
            context.getDataStore().put("combat", "currentHp", Math.max(1, 100 - actionCount * 5));
            context.getDataStore().put("combat", "currentMp", Math.max(0, 80 - actionCount * 3));
            context.getDataStore().put("combat", "opponentHp", opponentHp);
            context.getDataStore().put("combat", "lastCombatResult", hit ? "hit" : "miss");
            context.getDataStore().put("combat", "lastDamageDealt", hit ? damage : 0);
            context.getDataStore().put("combat", "totalDamageDealt", actionCount * damage);

            logger.debug("机器人战斗 Action 当前只做本地模拟，actionCount: {}, result: {}", actionCount, hit ? "hit" : "miss");
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("机器人战斗模拟动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    private int nextActionCount(RobotActionContext context) {
        Object value = context.getDataStore().get("combat", "combatActionCount");
        if (value instanceof Number number) {
            return number.intValue() + 1;
        }
        return 1;
    }

    @Override
    public int requestCmd() {
        return SIMULATED_COMBAT_CMD;
    }

    @Override
    public String getName() {
        return "SimulatedCombatAction";
    }
}
