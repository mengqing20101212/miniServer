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
                if (params.length >= 7) {
                    return new LoginCommand(
                        (String) params[0],
                        (String) params[1],
                        (Long) params[2],
                        (String) params[3],
                        (String) params[4],
                        (String) params[5],
                        (Long) params[6]
                    );
                } else if (params.length >= 6) {
                    // 6个参数：account, token, accountId, channel, deviceId, gameServerId
                    return new LoginCommand(
                        (String) params[0], // account
                        (String) params[1], // token
                        (Long) params[2],   // accountId
                        (String) params[3], // channel
                        (String) params[4], // deviceId
                        (String) params[5]  // gameServerId
                    );
                } else if (params.length >= 5) {
                    // 5个参数：account, token, accountId, channel, deviceId
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
