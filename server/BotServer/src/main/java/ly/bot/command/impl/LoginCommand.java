package ly.bot.command.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Login;

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
    private final String gameServerId;
    
    public LoginCommand(String account, String token, long accountId, String channel, String deviceId) {
        this.account = account;
        this.token = token;
        this.accountId = accountId;
        this.channel = channel;
        this.deviceId = deviceId;
        this.gameServerId = null; // 默认为空
    }
    
    public LoginCommand(String account, String token, long accountId, String channel, String deviceId, String gameServerId) {
        this.account = account;
        this.token = token;
        this.accountId = accountId;
        this.channel = channel;
        this.deviceId = deviceId;
        this.gameServerId = gameServerId;
    }
    
    private String commandId; // 用于延迟统计的命令ID
    
    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID用于延迟统计
            this.commandId = "login_" + System.currentTimeMillis() + "_" + account.hashCode();
            
            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_Login_VALUE);
            
            // 创建登录请求消息
            Login.csLogin.Builder loginBuilder = Login.csLogin.newBuilder();
            loginBuilder.setAccount(account);
            loginBuilder.setAccountId(accountId);
            loginBuilder.setChannel(channel);
            loginBuilder.setToken(token);
            loginBuilder.setDeviceId(deviceId);
            loginBuilder.setIsReconnect(false);
            
            // 设置游戏服务器ID，如果提供了的话
            if (gameServerId != null && !gameServerId.isEmpty()) {
                loginBuilder.setGameServerId(gameServerId);
            } else {
                // 如果没有提供gameServerId，可以设置一个默认值或从其他地方获取
                loginBuilder.setGameServerId("game1001"); // 使用默认游戏服务器ID
            }
            
            Login.csLogin loginRequest = loginBuilder.build();
            
            // 发送登录请求到GateServer
            int seq = client.getSendSeq(); // 获取并增加序列号
            int sid = client.isReady() ? client.getSid() : 0; // 使用正确的SID
            ly.net.packet.AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                accountId, // guid
                Cmd.CMD.CS_Login_VALUE, // 登录命令
                loginRequest, // protobuf数据
                seq, // 序列号
                sid // sid
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
    public String getCommandId() {
        return commandId != null ? commandId : "login_" + System.currentTimeMillis();
    }

    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, ly.net.NetClient client, ly.bot.session.RobotSession session) {
        // 处理登录响应
        if (response.getCmd() == ly.proto.Cmd.CMD.SC_Login_VALUE) {
            // 登录成功的响应处理
            System.out.println("收到登录成功响应: " + response);
            // 设置登录成功状态
            session.setLoginSuccess(true);
        } else {
            System.out.println("收到其他响应: " + response.getCmd());
        }
        
        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }

    @Override
    public String getName() {
        return "LoginCommand";
    }
}