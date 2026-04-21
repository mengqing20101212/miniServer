package ly;

import ly.net.GamePlayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameClientManager {
    private static GameClientManager gateClientManager = new GameClientManager();

    private final Map<Long, GamePlayer> clientMap = new ConcurrentHashMap<>();

    private GameClientManager() {

    }

    public static GameClientManager getInstance() {
        return gateClientManager;
    }

    public void addClient(GamePlayer client) {
        clientMap.put(client.getSessionGuid(), client);
    }

    public GamePlayer getClient(long guid) {
        return clientMap.get(guid);
    }

    public void removeClient(long guid) {
        clientMap.remove(guid);
    }

}
