package ly.bot.module.impl;

import java.util.List;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;
import ly.bot.action.impl.HeroAddAction;
import ly.bot.action.impl.HeroLevelUpAction;
import ly.bot.action.impl.HeroListAction;
import ly.bot.action.impl.HeroStarUpAction;
import ly.bot.module.RobotModule;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * 英雄模块测试。
 *
 * <p>
 * 按顺序执行英雄列表、添加英雄、再次拉列表、升级、升星。
 * 升级和升星依赖资源，资源不足时服务端会返回错误码；Bot 仍然可以用它验证协议链路是否通。
 * </p>
 */
public class HeroModule implements RobotModule {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final int MAX_WAIT_HERO_UID_ROUNDS = 3;

    /*
     * HeroList 会在模块流程里执行两次，但响应 cmd 都是 SC_HeroList。
     * Bot 当前按响应 cmd 找 Action 处理回包，所以这里必须复用同一个实例，
     * 避免注册表里后一个 HeroListAction 覆盖前一个，导致首个请求的响应统计丢失。
     */
    private final HeroListAction heroListAction = new HeroListAction();

    private final List<RobotAction> steps = List.of(
            heroListAction,
            new HeroAddAction(0, 1),
            heroListAction,
            new HeroLevelUpAction(0, List.of(1)),
            new HeroStarUpAction(0));

    private int stepIndex = 0;
    private boolean completed = false;
    private int waitHeroUidRounds = 0;

    @Override
    public List<RobotAction> setupActions() {
        return steps;
    }

    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        if (completed) {
            return true;
        }
        RobotAction action = steps.get(stepIndex);
        if (requiresHeroUid(action) && !hasLatestHeroUid(session)) {
            waitHeroUidRounds++;
            if (waitHeroUidRounds <= MAX_WAIT_HERO_UID_ROUNDS) {
                logger.info(
                        "英雄模块等待英雄 uid 写入后再执行: {}, waitRound={}",
                        action.getName(),
                        waitHeroUidRounds);
                return false;
            }

            logger.warn("英雄模块缺少英雄 uid，跳过可选动作: {}", action.getName());
            waitHeroUidRounds = 0;
            stepIndex++;
            if (stepIndex >= steps.size()) {
                completed = true;
            }
            return completed;
        }

        RobotActionResult result = action.execute(new RobotActionContext(client, session));
        if (result.isSuccess()) {
            logger.info("英雄模块执行动作成功: {}", action.getName());
            waitHeroUidRounds = 0;
        } else {
            logger.error("英雄模块执行动作失败: {}, reason: {}", action.getName(), result.getMessage());
        }

        stepIndex++;
        if (stepIndex >= steps.size()) {
            completed = true;
        }
        return completed;
    }

    @Override
    public void reset() {
        stepIndex = 0;
        completed = false;
        waitHeroUidRounds = 0;
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getName() {
        return "HeroModule";
    }

    private boolean requiresHeroUid(RobotAction action) {
        return action instanceof HeroLevelUpAction || action instanceof HeroStarUpAction;
    }

    private boolean hasLatestHeroUid(RobotSession session) {
        Long latestHeroUid = session.getDataStore().getAs("hero", "latestHeroUid", Long.class);
        return latestHeroUid != null && latestHeroUid > 0;
    }
}
