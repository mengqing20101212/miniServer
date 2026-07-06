package ly.bot.action.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.net.NetClient;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;

/**
 * 心跳协议动作。
 */
public class HeartbeatAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;

    private String actionId;

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            NetClient client = context.getClient();
            actionId = "heartbeat_" + System.currentTimeMillis();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());

            Server.csRpcPing ping = Server.csRpcPing.newBuilder()
                    .setTime(System.currentTimeMillis())
                    .setServerId("bot-" + context.getSession().getBotId())
                    .build();
            MessagePacket packet = context.getSession().createPacket(requestCmd(), ping);

            if (!client.send(packet)) {
                logger.error("机器人心跳发送失败");
                return RobotActionResult.fail("心跳发送失败");
            }
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("机器人心跳动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public void onResponse(MessagePacket response, RobotActionContext context) {
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
        logger.debug("收到机器人心跳响应, cmd: {}", response.getCmd());
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_RpcPing_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_RpcPing_VALUE;
    }

    @Override
    public String getName() {
        return "HeartbeatAction";
    }
}
