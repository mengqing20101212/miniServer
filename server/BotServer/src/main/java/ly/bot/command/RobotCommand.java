package ly.bot.command;

import ly.bot.session.RobotSession;
import ly.net.NetClient;
import ly.net.packet.AbstractMessagePacket;

/**
 * 机器人命令接口 - 命令模式
 */
public interface RobotCommand {
    void execute(NetClient client, RobotSession session);
    String getCommandId();
    void onResponse(AbstractMessagePacket response, NetClient client, RobotSession session);
    String getName();
}