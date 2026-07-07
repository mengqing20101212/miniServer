package ly.bot.action.impl;

import java.util.HashSet;
import java.util.Set;

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
 * 获取英雄列表动作。
 */
public class HeroListAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;

    private String actionId;

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            actionId = "hero_list_" + System.currentTimeMillis();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());

            if (!context.getSession().sendActionPacket(this, Hero.CS_HeroList.newBuilder().build())) {
                logger.error("获取英雄列表请求发送失败");
                return RobotActionResult.fail("获取英雄列表请求发送失败");
            }
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("获取英雄列表动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public void onResponse(MessagePacket response, RobotActionContext context) {
        try {
            Hero.SC_HeroList heroList = Hero.SC_HeroList.parseFrom(response.getData());
            Set<Integer> existingHeroIds = new HashSet<>();
            for (Hero.HeroInfo heroInfo : heroList.getHeroListList()) {
                existingHeroIds.add(heroInfo.getHeroId());
            }
            context.getDataStore().put("hero", "lastListCount", heroList.getHeroListCount());
            context.getDataStore().put("hero", "existingHeroIds", existingHeroIds);
            if (heroList.getHeroListCount() > 0) {
                Hero.HeroInfo firstHero = heroList.getHeroList(0);
                context.getDataStore().put("hero", "latestHeroUid", firstHero.getHeroUid());
                context.getDataStore().put("hero", "latestHeroId", firstHero.getHeroId());
            }
            logger.info("收到英雄列表响应, count: {}", heroList.getHeroListCount());
        } catch (Exception e) {
            logger.error("解析英雄列表响应失败", e);
        }
        context.getDataStore().put("hero", "lastListTime", System.currentTimeMillis());
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_HeroList_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_HeroList_VALUE;
    }

    @Override
    public String getName() {
        return "HeroListAction";
    }
}
