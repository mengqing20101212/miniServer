package ly.bot.action.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.net.NetClient;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

/**
 * 登录协议动作。
 */
public class LoginAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final String account;
    private final String token;
    private final long accountId;
    private final String channel;
    private final String deviceId;
    private final String gameServerId;
    private String actionId;

    public LoginAction(String account, String token, long accountId, String channel, String deviceId, String gameServerId) {
        this.account = account;
        this.token = token;
        this.accountId = accountId;
        this.channel = channel;
        this.deviceId = deviceId;
        this.gameServerId = gameServerId;
    }

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            NetClient client = context.getClient();
            actionId = "login_" + System.currentTimeMillis() + "_" + account.hashCode();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());

            Login.csLogin.Builder loginBuilder = Login.csLogin.newBuilder()
                    .setAccount(account)
                    .setAccountId(accountId)
                    .setChannel(channel)
                    .setToken(token)
                    .setDeviceId(deviceId)
                    .setIsReconnect(false);
            loginBuilder.setGameServerId(gameServerId != null && !gameServerId.isEmpty() ? gameServerId : "game1001");

            AbstractMessagePacket packet = context.getSession().createPacket(requestCmd(), loginBuilder.build());

            if (!client.send(packet)) {
                logger.error("机器人登录请求发送失败, account: {}", account);
                return RobotActionResult.fail("登录请求发送失败");
            }

            logger.debug("机器人登录请求已发送, account: {}, gameServerId: {}", account, gameServerId);
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("机器人登录动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public void onResponse(AbstractMessagePacket response, RobotActionContext context) {
        context.getSession().handleLoginResponse(response);
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
        logger.info("机器人登录响应处理完成, cmd: {}", response.getCmd());
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_Login_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_Login_VALUE;
    }

    @Override
    public String getName() {
        return "LoginAction";
    }
}
