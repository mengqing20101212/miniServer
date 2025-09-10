package ly.logic.player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    private static final PlayerManager instance = new PlayerManager();
    private final Map<Long, Player> playerMap = new ConcurrentHashMap<>();

    public static PlayerManager getInstance() {
        return instance;
    }

    public Player getOnlinePlayer(long playerId) {
        return playerMap.get(playerId);
    }
}
