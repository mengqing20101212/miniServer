package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.bot.session.RobotSession;
import ly.net.NetClient;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.Move;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 移动命令实现
 */
public class MoveCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    private String commandId;

    @Override
    public void execute(NetClient client, RobotSession session) {
        try {
            this.commandId = "move_" + System.currentTimeMillis();
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_Move_VALUE);

            int targetX = (int) (Math.random() * 100);
            int targetY = (int) (Math.random() * 100);

            Move.CS_Move.Builder builder = Move.CS_Move.newBuilder();
            builder.setTargetX(targetX);
            builder.setTargetY(targetY);

            // 使用 session.createPacket 确保 guid 正确
            AbstractMessagePacket packet = session.createPacket(Cmd.CMD.CS_Move_VALUE, builder.build());

            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("移动命令已发送: ({},{})", targetX, targetY);
            } else {
                logger.error("移动命令发送失败");
            }
        } catch (Exception e) {
            logger.error("执行移动命令失败", e);
        }
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void onResponse(AbstractMessagePacket response, NetClient client, RobotSession session) {
        session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        logger.debug("收到移动响应");
    }

    @Override
    public String getName() {
        return "Move";
    }
}
