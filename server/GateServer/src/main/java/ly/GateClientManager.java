package ly;

import ly.net.GateClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GateClientManager {
    private static GateClientManager gateClientManager = new GateClientManager();

    private final Map<Long, GateClient> clientMap = new ConcurrentHashMap<>();

    private GateClientManager() {
        
    }

    public static GateClientManager getInstance() {
        return gateClientManager;
    }

    public void addClient(GateClient client) {
        clientMap.put(client.getSessionGuid(), client);
    }

    public GateClient getClient(long guid) {
        return clientMap.get(guid);
    }

    public void removeClient(long guid) {
        clientMap.remove(guid);
    }

}
