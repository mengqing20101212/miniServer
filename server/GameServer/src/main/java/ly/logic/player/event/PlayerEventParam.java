package ly.logic.player.event;

import ly.logic.player.Player;

import java.util.Arrays;

/**
 * 游戏服玩家相关模型，承载玩家连接状态、持久化数据或模块数据。
 */
public record PlayerEventParam(
        Player player,
        PlayerEventType eventType,
        PlayerEventSource source,
        long sourcePlayerId,
        Object... args) {
    public PlayerEventParam(Player player, PlayerEventType eventType, Object... args) {
        this(player, eventType, PlayerEventSource.SELF, player == null ? 0L : player.getPlayerId(), args);
    }

    public static PlayerEventParam of(
            Player player,
            PlayerEventType eventType,
            PlayerEventSource source,
            long sourcePlayerId,
            Object... args) {
        return new PlayerEventParam(
                player,
                eventType,
                source == null ? PlayerEventSource.SELF : source,
                sourcePlayerId,
                args == null ? new Object[0] : args);
    }

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

    public PlayerEventSource getSource() {
        return source;
    }

    public long getSourcePlayerId() {
        return sourcePlayerId;
    }
}
