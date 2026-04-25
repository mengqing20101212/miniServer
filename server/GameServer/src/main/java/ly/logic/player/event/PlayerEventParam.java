package ly.logic.player.event;

import ly.logic.player.Player;

import java.util.Arrays;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public record PlayerEventParam(Player player, PlayerEventType eventType, Object... args) {
    @Override
    public String toString() {
        return "PlayerEventParam{" +
                "args=" + Arrays.toString(args) +
                '}';
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerEventType getEventType() {
        return eventType;
    }

    public Object[] getArgs() {
        return args;
    }
}
