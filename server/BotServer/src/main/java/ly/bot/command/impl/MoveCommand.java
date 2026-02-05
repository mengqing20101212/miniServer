package ly.bot.command.impl;

import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import org.slf4j.Logger;
import ly.LoggerDef;

/**
 * 移动命令实现
 */
public class MoveCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;
    
    private String commandId; // 用于延迟统计的命令ID
    
    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID用于延迟统计
            this.commandId = "move_" + System.currentTimeMillis();
            
            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, 1000); // 使用一个假设的移动命令ID
            
            // 发送移动指令 - 这里使用一个假设的移动命令
            int seq = client.getSendSeq(); // 获取并增加序列号
            int sid = client.isReady() ? client.getSid() : 0; // 使用正确的SID
            
            // 创建移动请求包（这里我们使用一个占位符命令，实际游戏中应使用真实的移动协议）
            ly.net.packet.C2SMessagePacket packet = MessagePacketFactory.createC2SMessagePacket(
                0, // guid
                1000, // 假设的移动命令ID
                null, // protobuf数据
                seq, // 序列号
                sid // sid
            );
            
            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("移动命令已发送");
            } else {
                logger.error("移动命令发送失败");
            }
        } catch (Exception e) {
            logger.error("执行移动命令失败", e);
        }
    }
    
    @Override
    public String getCommandId() {
        return commandId != null ? commandId : "move_" + System.currentTimeMillis();
    }
    
    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, ly.net.NetClient client, ly.bot.session.RobotSession session) {
        // 处理移动相关的响应
        logger.debug("收到移动响应: " + response.getCmd());
        
        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }
    
    @Override
    public String getName() {
        return "MoveCommand";
    }
}