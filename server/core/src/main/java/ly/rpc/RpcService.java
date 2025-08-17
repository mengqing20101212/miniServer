package ly.rpc;

import ly.LoggerDef;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcService {
    private static RpcService rpcService = new RpcService();
    private final Map<String, RpcNodeConnector> rpcNodeConnectorMap = new ConcurrentHashMap<>();

    private RpcService() {

    }

    public static RpcService getInstance() {
        return rpcService;
    }

    public RpcNodeConnector getRpcNodeConnector(String serverId) {
        if (rpcNodeConnectorMap.containsKey(serverId)) {
            return rpcNodeConnectorMap.get(serverId);
        }
        return createRpcNodeConnector(serverId);
    }

    public List<RpcNodeConnector> getRpcNodeConnectorsByServerType(ServerTypeEnum serverType) {
        List<NacosServerNode> nodeList = NacosService.getInstance().getNodeList(serverType);
        List<RpcNodeConnector> rpcNodeConnectors = new ArrayList<>();
        nodeList.forEach(node -> {
            rpcNodeConnectors.add(getRpcNodeConnector(node.getServerId()));
        });
        return rpcNodeConnectors;
    }

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

    //TODO  nacos 节点删除事件
    public void onNodeDeleted(String serverId) {
        rpcNodeConnectorMap.remove(serverId);
    }


    //TODO 定时发送心跳包
    public void onSecondTick() {
        for (RpcNodeConnector rpcNodeConnector : rpcNodeConnectorMap.values()) {
            rpcNodeConnector.pingConnect();
        }
    }
}
