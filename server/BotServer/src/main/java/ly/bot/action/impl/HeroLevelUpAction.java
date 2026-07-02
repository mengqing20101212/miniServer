package ly.bot.action.impl;

import java.util.List;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.net.NetClient;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Cmd;
import ly.proto.Hero;

/**
 * 英雄升级动作。
 */
public class HeroLevelUpAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final long configuredHeroUid;
    private final List<Integer> expItemIds;
    private String actionId;

    public HeroLevelUpAction(long heroUid, List<Integer> expItemIds) {
        this.configuredHeroUid = heroUid;
        this.expItemIds = expItemIds;
    }

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            Long latestHeroUid = context.getDataStore().getAs("hero", "latestHeroUid", Long.class);
            long heroUid = configuredHeroUid > 0 ? configuredHeroUid : latestHeroUid == null ? 0 : latestHeroUid;
            if (heroUid <= 0) {
                return RobotActionResult.fail("没有可升级的英雄 uid");
            }

            NetClient client = context.getClient();
            actionId = "hero_level_up_" + System.currentTimeMillis();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());

            Hero.CS_HeroLevelUp.Builder builder = Hero.CS_HeroLevelUp.newBuilder().setHeroUid(heroUid);
            expItemIds.forEach(builder::addExpItemIds);

            AbstractMessagePacket packet = context.getSession().createPacket(requestCmd(), builder.build());

            if (!client.send(packet)) {
                logger.error("英雄升级请求发送失败, heroUid: {}", heroUid);
                return RobotActionResult.fail("英雄升级请求发送失败");
            }
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("英雄升级动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public void onResponse(AbstractMessagePacket response, RobotActionContext context) {
        try {
            Hero.SC_HeroLevelUp result = Hero.SC_HeroLevelUp.parseFrom(response.getData());
            context.getDataStore().put("hero", "lastLevelUpResult", result.getResult().name());
            if (result.hasHeroInfo()) {
                context.getDataStore().put("hero", "latestHeroUid", result.getHeroInfo().getHeroUid());
                context.getDataStore().put("hero", "latestHeroLevel", result.getHeroInfo().getLevel());
            }
            logger.info("收到英雄升级响应, result: {}", result.getResult());
        } catch (Exception e) {
            logger.error("解析英雄升级响应失败", e);
        }
        context.getDataStore().put("hero", "lastLevelUpTime", System.currentTimeMillis());
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_HeroLevelUp_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_HeroLevelUp_VALUE;
    }

    @Override
    public String getName() {
        return "HeroLevelUpAction";
    }
}
