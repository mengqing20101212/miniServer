package ly.bot.action.impl;

import org.slf4j.Logger;

import ly.LoggerDef;
import ly.bot.action.RobotAction;
import ly.bot.action.RobotActionContext;
import ly.bot.action.RobotActionResult;

/**
 * 移动动作。
 *
 * <p>当前项目还没有接入真实客户端移动协议，Bot 侧只保留本地模拟数据。
 * 等真实移动协议确定后，只需要在这个 Action 里替换成正式发包逻辑。</p>
 */
public class MoveAction implements RobotAction {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final int MOVE_CMD = 1000;

    @Override
    public RobotActionResult execute(RobotActionContext context) {
        try {
            context.getDataStore().put("movement", "lastMoveActionTime", System.currentTimeMillis());
            logger.debug("机器人移动 Action 当前只做本地模拟，等待接入真实移动协议");
            return RobotActionResult.success();
        } catch (Exception e) {
            logger.error("机器人移动动作执行失败", e);
            return RobotActionResult.fail(e.getMessage());
        }
    }

    @Override
    public int requestCmd() {
        return MOVE_CMD;
    }

    @Override
    public String getName() {
        return "MoveAction";
    }
}
