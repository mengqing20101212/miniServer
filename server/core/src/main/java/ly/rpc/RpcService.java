package ly.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import ly.LoggerDef;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;

/** 服务器间 RPC 组件，维护跨节点连接并提供可靠消息补发入口。 */
public class RpcService {
  private static final RpcService rpcService = new RpcService();
  private final Map<String, RpcNodeConnector> rpcNodeConnectorMap = new ConcurrentHashMap<>();
  private final AtomicBoolean reliableReplayWorkerStarted = new AtomicBoolean();
  private volatile ReliableRpcReplayResponseHandler reliableReplayResponseHandler;

  private RpcService() {}

  public static RpcService getInstance() {
    return rpcService;
  }

  /** 获取指定服务器的 RPC 连接器；不存在时从 Nacos 节点表懒加载。 */
  public RpcNodeConnector getRpcNodeConnector(String serverId) {
    if (rpcNodeConnectorMap.containsKey(serverId)) {
      return rpcNodeConnectorMap.get(serverId);
    }
    return createRpcNodeConnector(serverId);
  }

  /** 获取某一类服务器的全部可用 RPC 连接器。 */
  public List<RpcNodeConnector> getRpcNodeConnectorsByServerType(ServerTypeEnum serverType) {
    if (!isRpcConnectableType(serverType)) {
      return new ArrayList<>();
    }
    List<NacosServerNode> nodeList = NacosService.getInstance().getNodeList(serverType);
    List<RpcNodeConnector> rpcNodeConnectors = new ArrayList<>();
    nodeList.forEach(
        node -> {
          RpcNodeConnector connector = getRpcNodeConnector(node.getServerId());
          if (connector != null) {
            rpcNodeConnectors.add(connector);
          }
        });
    return rpcNodeConnectors;
  }

  /** 根据 Nacos 节点信息创建连接器。 */
  private synchronized RpcNodeConnector createRpcNodeConnector(String serverId) {
    if (serverId == null || serverId.isBlank() || serverId.equals(ly.ServerContext.getServerId())) {
      return null;
    }
    RpcNodeConnector existing = rpcNodeConnectorMap.get(serverId);
    if (existing != null && existing.isConnect()) {
      return existing;
    }
    NacosServerNode nacosServerNode = NacosService.getInstance().getNodeMap().get(serverId);
    if (nacosServerNode == null) {
      LoggerDef.NetLogger.error(
          "RpcService createRpcNodeConnector NacosServerNode not found for serverId={}", serverId);
      return null;
    }
    if (!isRpcConnectableType(nacosServerNode.getServerType())) {
      return null;
    }
    if (!nacosServerNode.canUse()) {
      LoggerDef.NetLogger.error(
          "RpcService createRpcNodeConnector NacosServerNode not canUse for serverId={}", serverId);
      return null;
    }
    RpcNodeConnector rpcNodeConnector =
        new RpcNodeConnector(serverId, nacosServerNode.getIp(), nacosServerNode.getPort());
    if (rpcNodeConnector.isConnect()) {
      LoggerDef.NetLogger.info(
          "RpcService createRpcNodeConnector success for serverId={}, ip={}, port={}",
          serverId,
          nacosServerNode.getIp(),
          nacosServerNode.getPort());
      rpcNodeConnectorMap.put(serverId, rpcNodeConnector);
      return rpcNodeConnector;
    }
    return null;
  }

  /** 处理 Nacos 节点删除事件，释放对应 TCP 客户端资源。 */
  public void onNodeDeleted(String serverId) {
    RpcNodeConnector connector = rpcNodeConnectorMap.remove(serverId);
    if (connector != null) {
      connector.cleanup();
      LoggerDef.NetLogger.info("RpcService cleaned up connector for deleted node: {}", serverId);
    }
  }

  /** 目标节点上线或恢复可用后，异步补发当前服务器保存的可靠 RPC。 */
  public void onNodeAvailable(String serverId) {
    if (serverId == null || serverId.isBlank()) {
      return;
    }
    if (serverId.equals(ly.ServerContext.getServerId())) {
      return;
    }
    NacosServerNode node = NacosService.getInstance().getNodeMap().get(serverId);
    if (node != null && !isRpcConnectableType(node.getServerType())) {
      return;
    }
    Thread.ofVirtual()
        .name("reliable-rpc-replay-" + serverId)
        .start(() -> ReliableRpcStore.getInstance().replayForTarget(serverId));
  }

  public Set<String> getConnectedTargetServerIds() {
    return rpcNodeConnectorMap.entrySet().stream()
        .filter(entry -> entry.getValue() != null && entry.getValue().isConnect())
        .map(Map.Entry::getKey)
        .collect(Collectors.toSet());
  }

  /** 注册可靠 RPC 重放回包处理器，业务模块可用它把重放后的回包继续投递到自己的连接上下文。 */
  public void setReliableReplayResponseHandler(ReliableRpcReplayResponseHandler handler) {
    this.reliableReplayResponseHandler = handler;
  }

  boolean handleReliableReplayResponse(ReliableRpcMessage message, ly.net.packet.MessagePacket response) {
    ReliableRpcReplayResponseHandler handler = reliableReplayResponseHandler;
    return handler != null && handler.handle(message, response);
  }

  /** 本服启动后扫描当前可连接目标，避免错过目标服已经在线的补发机会。 */
  public void replayReliableMessagesOnStartup() {
    startReliableReplayWorker();
    Thread.ofVirtual()
        .name("reliable-rpc-replay-startup")
        .start(
            () -> {
              // 先尝试为当前 Nacos 缓存中的节点建立连接，再补发对应 outbox。
              for (ServerTypeEnum serverType : ServerTypeEnum.values()) {
                if (isRpcConnectableType(serverType)) {
                  getRpcNodeConnectorsByServerType(serverType);
                }
              }
              ReliableRpcStore.getInstance().replayAllAvailableTargets();
            });
  }

  private void startReliableReplayWorker() {
    if (!reliableReplayWorkerStarted.compareAndSet(false, true)) {
      return;
    }
    Thread.ofVirtual()
        .name("reliable-rpc-replay-worker")
        .start(
            () -> {
              while (!Thread.currentThread().isInterrupted()) {
                try {
                  replayKnownTargets();
                  Thread.sleep(5_000L);
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                } catch (Exception e) {
                  LoggerDef.NetLogger.error("reliable rpc replay worker error", e);
                }
              }
            });
  }

  private void replayKnownTargets() {
    // Nacos 可能先通知节点上线，目标端口稍后才真正绑定；周期性重建连接可以覆盖这段窗口。
    for (ServerTypeEnum serverType : ServerTypeEnum.values()) {
      if (isRpcConnectableType(serverType)) {
        getRpcNodeConnectorsByServerType(serverType);
      }
    }
    ReliableRpcStore.getInstance().replayAllAvailableTargets();
  }

  private boolean isRpcConnectableType(ServerTypeEnum serverType) {
    return serverType != null
        && serverType != ServerTypeEnum.UNKNOWN
        && serverType != ServerTypeEnum.GM
        && serverType != ServerTypeEnum.GMSERVER;
  }

  /** 周期心跳入口，由外部定时器每秒调用一次。 */
  public void onSecondTick() {
    for (RpcNodeConnector rpcNodeConnector : rpcNodeConnectorMap.values()) {
      rpcNodeConnector.pingConnect();
    }
    // 心跳后顺手补发已到重试时间的可靠 RPC，不额外引入新定时器。
    ReliableRpcStore.getInstance().replayAllAvailableTargets();
  }
}
