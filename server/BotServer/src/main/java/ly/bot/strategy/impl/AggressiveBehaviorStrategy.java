package ly.bot.strategy.impl;

import ly.bot.strategy.RobotBehaviorStrategy;
import ly.net.NetClient;
import ly.bot.action.RobotActionContext;
import ly.bot.action.impl.HeartbeatAction;
import ly.bot.session.RobotSession;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 激进型行为策略 - 频繁执行各种操作
 */
public class AggressiveBehaviorStrategy implements RobotBehaviorStrategy {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void execute(NetClient client, RobotSession session) {
        new HeartbeatAction().execute(new RobotActionContext(client, session));
        logger.debug("执行激进型行为策略");
    }
    
    @Override
    public String getStrategyName() {
        return "AggressiveBehaviorStrategy";
    }
}
