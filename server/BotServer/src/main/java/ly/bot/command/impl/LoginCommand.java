package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Login;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 登录命令实现
 */
public class LoginCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private final String account;
    private final String token;
    private final long accountId;
    private final String channel;
    private final String deviceId;
    
    public LoginCommand(String account, String token, long accountId, String channel, String deviceId) {
        this.account = account;
        this.token = token;
        this.accountId = accountId;
        this.channel = channel;
        this.deviceId = deviceId;
    }
    
    @Override
    public void execute(NetClient client) {
        try {
            // 创建登录请求消息
            Login.csLogin.Builder loginBuilder = Login.csLogin.newBuilder();
            loginBuilder.setAccount(account);
            loginBuilder.setAccountId(accountId);
            loginBuilder.setChannel(channel);
            loginBuilder.setToken(token);
            loginBuilder.setDeviceId(deviceId);
            loginBuilder.setIsReconnect(false);
            
            Login.csLogin loginRequest = loginBuilder.build();
            
            // 发送登录请求到GateServer
            ly.net.packet.C2SMessagePacket packet = MessagePacketFactory.createC2SMessagePacket(
                accountId, // guid
                Cmd.CMD.CS_Login_VALUE, // 登录命令
                loginRequest, // protobuf数据
                client.getSendSeq(), // 序列号
                client.isReady() ? client.getSid() : 0 // sid
            );
            
            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("登录请求已发送 for account: {}", account);
            } else {
                logger.error("登录请求发送失败 for account: {}", account);
            }
        } catch (Exception e) {
            logger.error("执行登录命令失败", e);
        }
    }
    
    @Override
    public String getName() {
        return "LoginCommand";
    }
}