package ly.bot.module.impl;

import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.bot.action.impl.SimulatedCombatAction;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 机器人战斗模块。
 *
 * <p>当前项目还没有真实战斗协议，因此这里只组织战斗模拟 Action，不再复用移动 Action。
 * 后续接入战斗协议时，只需要替换 Action 实现，模块调度逻辑不用变。</p>
 */
public class CombatModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_COMBAT_ACTIONS = 10;
    private final SimulatedCombatAction combatAction = new SimulatedCombatAction();
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        RobotActionResult result = combatAction.execute(new RobotActionContext(client, session));
        if (!result.isSuccess()) {
            return false;
        }
        
        step++;
        if (step >= MAX_COMBAT_ACTIONS) {
            completed = true;
            session.getDataStore().put("combat", "combatFinished", true);
        }
        
        return completed;
    }
    
    @Override
    public void reset() {
        completed = false;
        step = 0;
        // 保留 combat 数据域，便于后续模块和报告读取本轮模拟结果。
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "CombatModule";
    }
}
