package ly.bot.action;

/**
 * Action 执行结果。
 */
public class RobotActionResult {
    private final boolean success;
    private final String message;

    private RobotActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static RobotActionResult success() {
        return new RobotActionResult(true, "");
    }

    public static RobotActionResult fail(String message) {
        return new RobotActionResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
