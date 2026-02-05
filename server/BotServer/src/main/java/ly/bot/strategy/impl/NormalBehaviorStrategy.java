package ly.bot.strategy.impl;

import ly.bot.strategy.RobotBehaviorStrategy;
import ly.net.NetClient;
import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 普通型行为策略 - 模拟普通玩家行为
 */
public class NormalBehaviorStrategy implements RobotBehaviorStrategy {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void execute(NetClient client) {
        // 执行常规命令
        logger.debug("执行普通型行为策略");
    }
    
    @Override
    public String getStrategyName() {
        return "NormalBehaviorStrategy";
    }
}