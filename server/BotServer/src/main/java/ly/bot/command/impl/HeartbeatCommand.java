package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.bot.session.RobotSession;
import ly.net.NetClient;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 心跳命令实现
 */
public class HeartbeatCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    private String commandId;

    @Override
    public void execute(NetClient client, RobotSession session) {
        try {
            this.commandId = "heartbeat_" + System.currentTimeMillis();
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_RpcPing_VALUE);

            int seq = client.getSendSeq();
            AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                Cmd.CMD.CS_RpcPing_VALUE, seq, new byte[0]
            );

            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("心跳包已发送");
            } else {
                logger.error("心跳包发送失败");
            }
        } catch (Exception e) {
            logger.error("执行心跳命令失败", e);
        }
    }

    @Override
    public String getCommandId() {
        return commandId;
    }

    @Override
    public void onResponse(AbstractMessagePacket response, NetClient client, RobotSession session) {
        session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        logger.debug("收到心跳响应");
    }

    @Override
    public String getName() {
        return "Heartbeat";
    }
}
