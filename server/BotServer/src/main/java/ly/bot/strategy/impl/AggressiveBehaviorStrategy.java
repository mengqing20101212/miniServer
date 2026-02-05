package ly.bot.strategy.impl;

import ly.bot.strategy.RobotBehaviorStrategy;
import ly.net.NetClient;
import ly.bot.command.RobotCommand;
import ly.bot.factory.RobotCommandFactory;
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
        // 执行一系列命令
        RobotCommand loginCmd = RobotCommandFactory.createCommand(
            RobotCommandFactory.CommandType.LOGIN,
            "robot_user_aggressive",
            "robot_token_aggressive", 
            1000001L,
            "robot_channel",
            "robot_device_aggressive"
        );
        loginCmd.execute(client, session);
        
        logger.debug("执行激进型行为策略");
    }
    
    @Override
    public String getStrategyName() {
        return "AggressiveBehaviorStrategy";
    }
}