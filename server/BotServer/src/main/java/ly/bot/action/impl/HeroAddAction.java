package ly.bot.action.impl;

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
 * 添加英雄动作。
 */
public class HeroAddAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final int[] DEFAULT_HERO_IDS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    private final int heroId;
    private final int count;
    private String actionId;

    public HeroAddAction(int heroId, int count) {
        this.heroId = heroId;
        this.count = count;
    }

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            NetClient client = context.getClient();
            actionId = "hero_add_" + System.currentTimeMillis();
            context.getSession().getLatencyStats().recordRequestSent(actionId, requestCmd());
            int selectedHeroId = selectHeroId(context);

            MessagePacket packet = context.getSession().createPacket(
                    requestCmd(),
                    Hero.CS_HeroAdd.newBuilder().setHeroId(selectedHeroId).setCount(count).build());

            if (!client.send(packet)) {
                logger.error("添加英雄请求发送失败, heroId: {}, count: {}", selectedHeroId, count);
                return RobotActionResult.fail("添加英雄请求发送失败");
            }
            context.getDataStore().put("hero", "lastAddRequestHeroId", selectedHeroId);
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("添加英雄动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private int selectHeroId(RobotActionContext context) {
        if (heroId > 0) {
            return heroId;
        }
        Set<Integer> existingHeroIds =
                context.getDataStore().getAs("hero", "existingHeroIds", Set.class);
        if (existingHeroIds != null) {
            for (int candidate : DEFAULT_HERO_IDS) {
                if (!existingHeroIds.contains(candidate)) {
                    return candidate;
                }
            }
        }
        return DEFAULT_HERO_IDS[0];
    }

    @Override
    public void onResponse(MessagePacket response, RobotActionContext context) {
        try {
            Hero.SC_HeroAdd result = Hero.SC_HeroAdd.parseFrom(response.getData());
            context.getDataStore().put("hero", "lastAddResult", result.getResult().name());
            context.getDataStore().put("hero", "lastAddCount", result.getHeroListCount());
            if (result.getHeroListCount() > 0) {
                Hero.HeroInfo hero = result.getHeroList(0);
                context.getDataStore().put("hero", "latestHeroUid", hero.getHeroUid());
                context.getDataStore().put("hero", "latestHeroId", hero.getHeroId());
            }
            logger.info("收到添加英雄响应, result: {}, count: {}", result.getResult(), result.getHeroListCount());
        } catch (Exception e) {
            logger.error("解析添加英雄响应失败", e);
        }
        context.getDataStore().put("hero", "lastAddTime", System.currentTimeMillis());
        Object requestedHeroId = context.getDataStore().get("hero", "lastAddRequestHeroId");
        context.getDataStore().put("hero", "lastAddedHeroId", requestedHeroId == null ? heroId : requestedHeroId);
        if (actionId != null) {
            context.getSession().getLatencyStats().recordResponseReceived(actionId, response.getCmd());
        }
    }

    @Override
    public int requestCmd() {
        return Cmd.CMD.CS_HeroAdd_VALUE;
    }

    @Override
    public int responseCmd() {
        return Cmd.CMD.SC_HeroAdd_VALUE;
    }

    @Override
    public String getName() {
        return "HeroAddAction";
    }
}
