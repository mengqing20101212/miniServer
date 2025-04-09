package ly.net;

import io.netty.channel.EventLoopGroup;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ly.LoggerDef;
import ly.net.packet.S2SMessagePacket;
import org.apache.logging.log4j.core.Logger;
import org.slf4j.LoggerFactory;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: NetClientManager
 */
public class NetClientManager {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(NetClientManager.class);
  static Logger logger = LoggerDef.SystemLogger;

  private EventLoopGroup group;
  private static NetClientManager instance = new NetClientManager();
  Map<String, NetClient> netClientMap = new ConcurrentHashMap<>();
  Map<String, NetClient> channelIdClientMap = new ConcurrentHashMap<>();

  private NetClientManager() {
    group = NetService.worker;
  }

  public static NetClientManager getInstance() {
    return instance;
  }

  public NetClient getNetClient(String id) {
    return channelIdClientMap.get(id);
  }

  public NetClient getNetClient(String ip, int port) {
    String key = ip + ":" + port;
    if (netClientMap.containsKey(key)) {
      return netClientMap.get(key);
    }
    return null;
  }

  public NetClient connetNetClient(String ip, int port) {
    NetClient netClient = getNetClient(ip, port);
    if (netClient == null) {
      netClient = new NetClient(ip, port);
      netClient.start(group);
      if (netClient.isConnected()) {
        addNewClient(netClient);
      } else {
        logger.error("connetNetClient Failed to connect to " + ip + ":" + port);
      }
    }
    return netClient;
  }

  private void addNewClient(NetClient netClient) {
    logger.info("addNewClient " + netClient);
    channelIdClientMap.put(netClient.getId(), netClient);
    netClientMap.put(netClient.getIpPortKey(), netClient);
  }

  public void delNetClient(String id) {
    NetClient client = channelIdClientMap.remove(id);
    if (client != null) {
      netClientMap.remove(client.getIpPortKey());
      client.stop();
    }
    logger.info("delNetClient " + client);
  }
}
