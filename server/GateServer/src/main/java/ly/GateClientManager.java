package ly;

import ly.net.GateClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 网关侧客户端管理器，维护会话 id 与网关客户端对象的映射关系。
 */
public class GateClientManager {
    private static GateClientManager gateClientManager = new GateClientManager();

    private final Map<Long, GateClient> clientMap = new ConcurrentHashMap<>();
    private final Map<Integer, GateClient> sidClientMap = new ConcurrentHashMap<>();

    private GateClientManager() {
        
    }

    public static GateClientManager getInstance() {
        return gateClientManager;
    }

    public void addClient(GateClient client) {
        clientMap.put(client.getSessionGuid(), client);
        sidClientMap.put(client.getClientSid(), client);
    }

    public GateClient getClient(long guid) {
        return clientMap.get(guid);
    }

    public GateClient getClientBySid(int sid) {
        return sidClientMap.get(sid);
    }

    public void removeClient(long guid) {
        GateClient client = clientMap.remove(guid);
        if (client != null) {
            sidClientMap.remove(client.getClientSid());
        }
    }

}
