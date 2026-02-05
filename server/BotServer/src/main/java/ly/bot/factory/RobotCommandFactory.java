package ly.bot.factory;

import ly.bot.command.RobotCommand;
import ly.bot.command.impl.*;

/**
 * 机器人命令工厂 - 工厂模式
 */
public class RobotCommandFactory {
    
    public enum CommandType {
        LOGIN,
        HEARTBEAT,
        MOVE
    }
    
    public static RobotCommand createCommand(CommandType type, Object... params) {
        switch (type) {
            case LOGIN:
                if (params.length >= 5) {
                    return new LoginCommand(
                        (String) params[0], // account
                        (String) params[1], // token
                        (Long) params[2],   // accountId
                        (String) params[3], // channel
                        (String) params[4]  // deviceId
                    );
                }
                break;
            case HEARTBEAT:
                return new HeartbeatCommand();
            case MOVE:
                return new MoveCommand();
            default:
                throw new IllegalArgumentException("Unknown command type: " + type);
        }
        throw new IllegalArgumentException("Insufficient parameters for command type: " + type);
    }
}