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

    private final List<RobotAction> steps = List.of(
            new HeroListAction(),
            new HeroAddAction(0, 1),
            new HeroListAction(),
            new HeroLevelUpAction(0, List.of(1)),
            new HeroStarUpAction(0));

    private int stepIndex = 0;
    private boolean completed = false;

    public List<RobotAction> setupActions() {
        return steps;
    }

    @Override
    public boolean executeStep(NetClient client, RobotSession session) {
        if (completed) {
            return true;
        }
        RobotAction action = steps.get(stepIndex);
        RobotActionResult result = action.execute(new RobotActionContext(client, session));
        if (result.isSuccess()) {
            logger.info("英雄模块执行动作成功: {}", action.getName());
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
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String getName() {
        return "HeroModule";
    }
}
