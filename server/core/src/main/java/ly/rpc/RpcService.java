package ly.rpc;

import ly.LoggerDef;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器间 RPC 组件，维护跨节点连接并提供同步/异步调用工具。
 * <p>
 * 该服务不主动保存所有 Nacos 节点连接，而是按 serverId 懒加载
 * {@link RpcNodeConnector}。节点被 Nacos 删除时，应调用 {@link #onNodeDeleted(String)}
 * 清理对应连接。
 */
public class RpcService {
    private static RpcService rpcService = new RpcService();
    private final Map<String, RpcNodeConnector> rpcNodeConnectorMap = new ConcurrentHashMap<>();

    private RpcService() {

    }

    public static RpcService getInstance() {
        return rpcService;
    }

    /**
     * 获取指定服务器的 RPC 连接器。
     * <p>
     * 已存在连接直接复用，不存在时从 Nacos 节点表读取 ip/port 并创建新连接。
     */
    public RpcNodeConnector getRpcNodeConnector(String serverId) {
        if (rpcNodeConnectorMap.containsKey(serverId)) {
            return rpcNodeConnectorMap.get(serverId);
        }
        return createRpcNodeConnector(serverId);
    }

    /** 获取某一类服务器的全部可用 RPC 连接器。 */
    public List<RpcNodeConnector> getRpcNodeConnectorsByServerType(ServerTypeEnum serverType) {
        List<NacosServerNode> nodeList = NacosService.getInstance().getNodeList(serverType);
        List<RpcNodeConnector> rpcNodeConnectors = new ArrayList<>();
        nodeList.forEach(node -> {
            rpcNodeConnectors.add(getRpcNodeConnector(node.getServerId()));
        });
        return rpcNodeConnectors;
    }

    /**
     * 根据 Nacos 节点信息创建连接器。
     * <p>
     * 节点不存在、不可用或 TCP 未连接成功时返回 null，调用方需要自行降级或重试。
     */
    private RpcNodeConnector createRpcNodeConnector(String serverId) {
        NacosServerNode nacosServerNode = NacosService.getInstance().getNodeMap().get(serverId);
        if (nacosServerNode == null) {
            LoggerDef.NetLogger.error("RpcService createRpcNodeConnector NacosServerNode not found for serverId={}", serverId);
            return null;
        }
        if (!nacosServerNode.canUse()) {
            LoggerDef.NetLogger.error("RpcService createRpcNodeConnector NacosServerNode not canUse for serverId={}", serverId);
            return null;
        }
        RpcNodeConnector rpcNodeConnector = new RpcNodeConnector(serverId, nacosServerNode.getIp(), nacosServerNode.getPort());
        if (rpcNodeConnector.isConnect()) {
            LoggerDef.NetLogger.info("RpcService createRpcNodeConnector success for serverId={}, ip={}, port={}", serverId, nacosServerNode.getIp(), nacosServerNode.getPort());
            rpcNodeConnectorMap.put(serverId, rpcNodeConnector);
            return rpcNodeConnector;
        }
        return null;
    }

    /** 处理 Nacos 节点删除事件，释放对应 TCP 客户端资源。 */
    public void onNodeDeleted(String serverId) {
        RpcNodeConnector connector = rpcNodeConnectorMap.remove(serverId);
        if (connector != null) {
            connector.cleanup(); // 清理资源避免内存泄漏
            LoggerDef.NetLogger.info("RpcService cleaned up connector for deleted node: {}", serverId);
        }
    }


    /** 周期心跳入口，由外部定时器每秒调用一次。 */
    public void onSecondTick() {
        for (RpcNodeConnector rpcNodeConnector : rpcNodeConnectorMap.values()) {
            rpcNodeConnector.pingConnect();
        }
    }
}
