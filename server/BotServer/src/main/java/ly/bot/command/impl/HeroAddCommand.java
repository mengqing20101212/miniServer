package ly.bot.command.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.command.RobotCommand;
import ly.net.NetClient;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Hero;

/**
 * 添加英雄命令实现
 */
public class HeroAddCommand implements RobotCommand {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final int heroId;
    private final int count;
    private String commandId;

    public HeroAddCommand(int heroId, int count) {
        this.heroId = heroId;
        this.count = count;
    }

    public HeroAddCommand(int heroId) {
        this(heroId, 1);
    }

    @Override
    public void execute(NetClient client, ly.bot.session.RobotSession session) {
        try {
            // 创建命令ID
            this.commandId = "hero_add_" + System.currentTimeMillis();

            // 记录请求发送时间
            session.getLatencyStats().recordRequestSent(commandId, Cmd.CMD.CS_HeroAdd_VALUE);

            // 创建添加英雄请求消息
            Hero.CS_HeroAdd request = Hero.CS_HeroAdd.newBuilder()
                    .setHeroId(heroId)
                    .setCount(count)
                    .build();

            // 发送请求
            int seq = client.getSendSeq();
            int sid = client.isReady() ? client.getSid() : 0;
            long accountId = session.getPlayerInfo() != null ? session.getPlayerInfo().getAccountId() : 0;

            ly.net.packet.AbstractMessagePacket packet = MessagePacketFactory.createAbstractMessagePacket(
                    accountId,
                    Cmd.CMD.CS_HeroAdd_VALUE,
                    request,
                    seq,
                    sid
            );

            boolean sent = client.send(packet);
            if (sent) {
                logger.debug("添加英雄请求已发送，heroId: {}, count: {}", heroId, count);
            } else {
                logger.error("添加英雄请求发送失败");
            }
        } catch (Exception e) {
            logger.error("执行添加英雄命令失败", e);
        }
    }

    @Override
    public String getCommandId() {
        return commandId != null ? commandId : "hero_add_" + System.currentTimeMillis();
    }

    @Override
    public void onResponse(ly.net.packet.AbstractMessagePacket response, NetClient client, ly.bot.session.RobotSession session) {
        if (response.getCmd() == Cmd.CMD.SC_HeroAdd_VALUE) {
            logger.info("收到添加英雄响应，命令号: {}", response.getCmd());
            // 可以在这里解析响应数据，获取新增的英雄信息
            session.getDataStore().put("hero", "lastAddTime", System.currentTimeMillis());
            session.getDataStore().put("hero", "lastAddedHeroId", heroId);
        }

        // 记录响应接收时间
        if (commandId != null) {
            session.getLatencyStats().recordResponseReceived(commandId, response.getCmd());
        }
    }

    @Override
    public String getName() {
        return "HeroAddCommand";
    }
}
