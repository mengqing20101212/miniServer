package ly.logic.player.event;

import ly.logic.player.Player;

import java.util.Arrays;

public record PlayerEventParam(Player player, PlayerEventType eventType, Object... args) {
    @Override
    public String toString() {
        return "PlayerEventParam{" +
                "args=" + Arrays.toString(args) +
                '}';
    }
}
