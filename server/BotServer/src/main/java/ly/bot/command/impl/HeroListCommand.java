package ly.bot.command.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Hero;

/**
 * 获取英雄列表命令实现
 */
public class HeroListCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;

    private String commandId;

    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID
            this.commandId = "hero_list_" + System.currentTimeMillis();

            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_HeroList_VALUE);

            // 创建获取英雄列表请求消息
            Hero.CS_HeroList request = Hero.CS_HeroList.newBuilder().build();

            // 发送请求
            int seq = client.getSendSeq();
            int sid = client.isReady() ? client.getSid() : 0;
            long accountId = session.getPlayerInfo() != null ? session.getPlayerInfo().getAccountId() : 0;

            ly.net.packet.AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                    accountId,
                    Cmd.CMD.CS_HeroList_VALUE,
                    request,
                    seq,
                    sid
            );

            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("获取英雄列表请求已发送");
            } else {
                logger.error("获取英雄列表请求发送失败");
            }
        } catch (Exception e) {
            logger.error("执行获取英雄列表命令失败", e);
        }
    }

    @Override
    public String getCommandId() {
        return commandId != null ? commandId : "hero_list_" + System.currentTimeMillis();
    }

    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, NetClient client, ly.bot.session.RobotSession session) {
        if (response.getCmd() == Cmd.CMD.SC_HeroList_VALUE) {
            logger.info("收到英雄列表响应，命令号: {}", response.getCmd());
            // 可以在这里解析响应数据
            session.getDataStore().put("hero", "lastListTime", System.currentTimeMillis());
        }

        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }

    @Override
    public String getName() {
        return "HeroListCommand";
    }
}
