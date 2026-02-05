package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 心跳命令实现
 */
public class HeartbeatCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void execute(NetClient client) {
        // 发送心跳包
        logger.debug("发送心跳命令");
        // 具体的心跳逻辑将在后续实现
    }
    
    @Override
    public String getName() {
        return "HeartbeatCommand";
    }
}