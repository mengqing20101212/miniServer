package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 心跳命令实现
 */
public class HeartbeatCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private String commandId; // 用于延迟统计的命令ID
    
    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID用于延迟统计
            this.commandId = "heartbeat_" + System.currentTimeMillis();
            
            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_RpcPing_VALUE);
            
            // 发送心跳包 - 实际发送RPC Ping请求
            int seq = client.getSendSeq(); // 获取并增加序列号
            int sid = client.isReady() ? client.getSid() : 0; // 使用正确的SID
            
            // 创建空的RPC Ping请求（心跳包）
            ly.net.packet.C2SMessagePacket packet = MessagePacketFactory.createC2SMessagePacket(
                0, // guid - 心跳包可能不需要特定guid
                Cmd.CMD.CS_RpcPing_VALUE, // RPC心跳命令
                null, // protobuf数据 - 心跳可能不需要数据
                seq, // 序列号
                sid // sid
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
        return commandId != null ? commandId : "heartbeat_" + System.currentTimeMillis();
    }
    
    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, ly.net.NetClient client, ly.bot.session.RobotSession session) {
        // 处理心跳相关的响应
        logger.debug("收到心跳响应: " + response.getCmd());
        
        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }
    
    @Override
    public String getName() {
        return "HeartbeatCommand";
    }
}