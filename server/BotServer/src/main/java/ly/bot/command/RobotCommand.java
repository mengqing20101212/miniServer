package ly.bot.command;

import ly.net.NetClient;

/**
 * 机器人命令接口 - 命令模式
 */
public interface RobotCommand {
    void execute(NetClient client);
    String getName();
}