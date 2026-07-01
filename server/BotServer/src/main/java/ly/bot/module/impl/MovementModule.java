package ly.bot.module.impl;

import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.bot.action.impl.MoveAction;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 机器人行为模块，封装登录、心跳、移动、战斗等可组合行为能力。
 */
public class MovementModule implements RobotModule {
    private boolean completed = false;
    private int step = 0;
    private static final int MAX_MOVEMENTS = 3; // 移动模块执行3次移动
    
    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        RobotActionResult result = new MoveAction().execute(new RobotActionContext(client, session));
        if (!result.isSuccess()) {
            return false;
        }
        
        // 存储移动相关的数据到会话级别存储
        session.getDataStore().put("movement", "lastMoveTime", System.currentTimeMillis());
        session.getDataStore().put("movement", "moveCount", step + 1);
        session.getDataStore().put("movement", "currentPosition", "x:" + (step * 10) + ", y:" + (step * 5));
        
        step++;
        if (step >= MAX_MOVEMENTS) {
            completed = true;
        }
        
        return completed;
    }
    
    @Override
    public void reset() {
        completed = false;
        step = 0;
        // 注意：不再清除会话级别的数据，因为其他模块可能需要这些数据
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public String getName() {
        return "MovementModule";
    }
}
