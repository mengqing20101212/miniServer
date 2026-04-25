package ly.net;

import io.netty.channel.EventLoopGroup;
import ly.LoggerDef;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 出站 NetClient 管理器。
 * <p>
 * 复用到同一 ip:port 的客户端连接，并统一持有客户端使用的 worker EventLoopGroup。
 * RPC 层通过这里创建和清理到其他服务器节点的 TCP 连接。
 */
public class NetClientManager {
    static Logger logger = LoggerDef.SystemLogger;

    private EventLoopGroup group;
    private static NetClientManager instance = new NetClientManager();
    Map<String, NetClient> netClientMap = new ConcurrentHashMap<>(1204);

    private NetClientManager() {
        group = NetService.worker;
    }

    public static NetClientManager getInstance() {
        return instance;
    }

    public NetClient getNetClient(String ip, int port) {
        String key = ip + ":" + port;
        if (netClientMap.containsKey(key)) {
            return netClientMap.get(key);
        }
        return null;
    }

    /**
     * 该接口创建的 NetClient IP port 会被复用
     *
     * @param ip
     * @param port
     * @return 新的 NetClient
     */
    public NetClient reconnetNetClient(String ip, int port) {
        NetClient netClient = getNetClient(ip, port);
        if (netClient == null) {
            netClient = new NetClient(ip, port, true);
            netClient.start(group);
        }
        return netClient;
    }

    /**
     * 每次调用 创建新的 NetClient ，该连接 不会被复用
     *
     * @param ip
     * @param port
     * @return 新创建的 NetClient , 注意 外部使用的时候需要判断该连接是否准备好，可能未创建成功处于3次握手中
     */
    public NetClient newNetClient(String ip, int port) {
        NetClient netClient = new NetClient(ip, port, false);
        netClient.start(group);
        return netClient;
    }

    void addNewClient(NetClient netClient) {
        logger.info(
                "addNewClient "
                        + netClient
                        + ", Channel:"
                        + netClient.getChannel()
                        + ", canUse:"
                        + netClient.isReady());
        if (netClient.isMultiplex()) {
            netClientMap.put(netClient.getIpPortKey(), netClient);
        }
    }

    public void delNetClient(NetClient client) {
        if (client != null) {
            NetClient ipNetClient = netClientMap.get(client.getIpPortKey());
            if (ipNetClient != null && ipNetClient == client) {
                netClientMap.remove(client.getIpPortKey());
            }
            client.stop();
        }
        logger.info("delNetClient " + client);
    }
}
