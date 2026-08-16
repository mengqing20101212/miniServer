package ly.bot.action.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.net.NetClient;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.Hero;

/**
 * 英雄升星动作。
 */
public class HeroStarUpAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;

    private final long configuredHeroUid;
    private String actionId;

    public HeroStarUpAction(long heroUid) {
        this.configuredHeroUid = heroUid;
    }

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            Long latestHeroUid = context.getDataStore().getAs("hero", "latestHeroUid", Long.class);
            long heroUid = configuredHeroUid > 0 ? configuredHeroUid : latestHeroUid == null ? 0 : latestHeroUid;
            if (heroUid <= 0) {
                return RobotActionResult.fail("没有可升星的英雄 uid");
            }

            actionId = "hero_star_up_" + System.currentTimeMillis();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());

            if (!context.getSession()
                    .sendActionPacket(this, Hero.CS_HeroStarUp.newBuilder().setHeroUid(heroUid).build())) {
                logger.error("英雄升星请求发送失败, heroUid: {}", heroUid);
                return RobotActionResult.fail("英雄升星请求发送失败");
            }
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("英雄升星动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public void onResponse(MessagePacket response, RobotActionContext context) {
        try {
            Hero.SC_HeroStarUp result = Hero.SC_HeroStarUp.parseFrom(response.getData());
            context.getDataStore().put("hero", "lastStarUpResult", result.getResult().name());
            if (result.hasHeroInfo()) {
                context.getDataStore().put("hero", "latestHeroUid", result.getHeroInfo().getHeroUid());
                context.getDataStore().put("hero", "latestHeroStar", result.getHeroInfo().getStar());
            }
            logger.info("收到英雄升星响应, result: {}", result.getResult());
        } catch (Exception e) {
            logger.error("解析英雄升星响应失败", e);
        }
        context.getDataStore().put("hero", "lastStarUpTime", System.currentTimeMillis());
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_HeroStarUp_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_HeroStarUp_VALUE;
    }

    @Override
    public String getName() {
        return "HeroStarUpAction";
    }
}
