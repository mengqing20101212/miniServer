package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 移动命令实现
 */
public class MoveCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    @Override
    public void execute(NetClient client) {
        // 发送移动指令
        logger.debug("发送移动命令");
        // 具体的移动逻辑将在后续实现
    }
    
    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, ly.net.NetClient client, ly.bot.session.RobotSession session) {
        // 处理移动相关的响应
        logger.debug("收到移动响应: " + response.getCmd());
    }
    
    @Override
    public String getName() {
        return "MoveCommand";
    }
}