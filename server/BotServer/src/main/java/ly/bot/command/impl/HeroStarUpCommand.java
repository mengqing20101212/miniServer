package ly.bot.command.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Hero;

/**
 * 英雄升星命令实现
 */
public class HeroStarUpCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final long heroUid;
    private String commandId;

    public HeroStarUpCommand(long heroUid) {
        this.heroUid = heroUid;
    }

    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID
            this.commandId = "hero_star_up_" + System.currentTimeMillis();

            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_HeroStarUp_VALUE);

            // 创建英雄升星请求消息
            Hero.CS_HeroStarUp request = Hero.CS_HeroStarUp.newBuilder()
                    .setHeroUid(heroUid)
                    .build();

            // 发送请求
            int seq = client.getSendSeq();
            int sid = client.isReady() ? client.getSid() : 0;
            long accountId = session.getPlayerInfo() != null ? session.getPlayerInfo().getAccountId() : 0;

            ly.net.packet.AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                    accountId,
                    Cmd.CMD.CS_HeroStarUp_VALUE,
                    request,
                    seq,
                    sid
            );

            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("英雄升星请求已发送，heroUid: {}", heroUid);
            } else {
                logger.error("英雄升星请求发送失败");
            }
        } catch (Exception e) {
            logger.error("执行英雄升星命令失败", e);
        }
    }

    @Override
    public String getCommandId() {
        return commandId != null ? commandId : "hero_star_up_" + System.currentTimeMillis();
    }

    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, NetClient client, ly.bot.session.RobotSession session) {
        if (response.getCmd() == Cmd.CMD.SC_HeroStarUp_VALUE) {
            logger.info("收到英雄升星响应，命令号: {}", response.getCmd());
            // 可以在这里解析响应数据，获取升星后的英雄信息
            session.getDataStore().put("hero", "lastStarUpTime", System.currentTimeMillis());
            session.getDataStore().put("hero", "lastStarUpHeroUid", heroUid);
        }

        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }

    @Override
    public String getName() {
        return "HeroStarUpCommand";
    }
}
