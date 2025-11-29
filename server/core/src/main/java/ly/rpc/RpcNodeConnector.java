package ly.rpc;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;
import ly.net.NetClient;
import ly.net.NetClientManager;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;
import java.util.Map;

public class RpcNodeConnector {
    NetClient client;
    private final String serverId;
    private final String ip;
    private final int port;
    /**
     * 默认超时时间
     */
    private final int DEFAULT_TIMEOUT = 1000;
    

    
    
    public RpcNodeConnector(String serverId, String ip, int port) {
        this.serverId = serverId;
        this.ip = ip;
        this.port = port;
        this.client = NetClientManager.getInstance().newNetClient(ip, port);
    }

    public boolean isConnect() {
        if (client == null) {
            return false;
        }
        return client.isReady();
    }

    public NetClient getClient() {
        return client;
    }

    public void setClient(NetClient client) {
        this.client = client;
    }

    public String getServerId() {
        return serverId;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    // 发送心跳包
    public void pingConnect() {
        Server.csRpcPing ping = Server.csRpcPing.newBuilder().setTime(System.currentTimeMillis()).setServerId(ServerContext.getServerId()).build();
        sendProtoMessage(0, Cmd.CMD.CS_RpcPing_VALUE, ping);
    }

    public int sendProtoMessage(long guid, int cmd, AbstractMessage protoData) {
        return client.sendS2SMessage(guid, cmd, protoData);
    }

    public boolean sendPacket(AbstractMessagePacket packet) {
        if (isConnect()) {
            boolean success = client.send(packet);
            if (!success) {
                // 使用warn级别替代error级别，避免日志风暴
                LoggerDef.NetLogger.warn("send packet failed, serverId={}", serverId);
                return false;
            }
            // 避免重复创建字符串对象，仅在调试级别记录
            if (LoggerDef.ProtoLogger.isDebugEnabled()) {
                LoggerDef.ProtoLogger.debug("send packet {}", packet.toSimpleString());
            }
            return true;
        }
        return false;
    }

    public AbstractMessagePacket syncSendPacket(AbstractMessagePacket packet, int timeout) {
        final int sendSeq = packet.getSeq();
        
        try {
            if (sendPacket(packet)) {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + timeout;
                
                // 使用简单的轮询方式等待响应，适合虚拟线程环境
                while (System.currentTimeMillis() < endTime) {
                    AbstractMessagePacket response = client.getReceiveMsgBySeq(sendSeq);
                    if (response != null) {
                        return response;
                    }
                    
                    // 短暂睡眠，让出CPU时间片
                    Thread.sleep(10);
                }
                
                // 超时返回null
                return null;
            }
        } catch (Exception e) {
            LoggerDef.NetLogger.warn("RPC call failed, serverId={}, seq={}, error={}", serverId, sendSeq, e.getMessage());
        } 
        return null;
    }

    public AbstractMessagePacket syncSendPacket(AbstractMessagePacket packet) {
        return syncSendPacket(packet, DEFAULT_TIMEOUT);
    }

    public AbstractMessage syncSendProtoMessage(long guid, int cmd, AbstractMessage protoData) {
        return syncSendProtoMessage(guid, cmd, protoData, DEFAULT_TIMEOUT);
    }

    public AbstractMessage syncSendProtoMessage(long guid, int cmd, AbstractMessage protoData, int timeout) {
        int sendReq = sendProtoMessage(guid, cmd, protoData);
        if (sendReq == -1) {
            LoggerDef.NetLogger.warn("send proto message failed, serverId={}, guid={}, cmd={}", serverId, guid, cmd);
            return null;
        }
        
        try {
            
            long startTime = System.currentTimeMillis();
            long endTime = startTime + timeout;
            
            // 使用简单的轮询方式等待响应
            while (System.currentTimeMillis() < endTime) {
                AbstractMessagePacket response = client.getReceiveMsgBySeq(sendReq);
                if (response != null) {
                    return unpackPacket(response);
                }
                
                // 短暂睡眠，让出CPU时间片
                Thread.sleep(10);
            }
            
            // 超时返回null
            return null;
        } catch (Exception e) {
            LoggerDef.NetLogger.warn("RPC proto call failed, serverId={}, cmd={}, error={}", serverId, cmd, e.getMessage());
        } 
        return null;
    }

    private AbstractMessage unpackPacket(AbstractMessagePacket receivedPacket) {
        if (receivedPacket instanceof S2SMessagePacket s2sMessagePacket) {
            try {
                return ProtoMessageFactory.createProtoMessage(s2sMessagePacket.getCmd(), s2sMessagePacket.getData());
            } catch (Exception e) {
                LoggerDef.NetLogger.error("Failed to unpack packet, serverId={}, cmd={}, error={}", 
                    serverId, s2sMessagePacket.getCmd(), e.getMessage());
            }
        }
        return null;
    }

    public ServerTypeEnum getServerType() {
        Map<String, ?> nodeMap = NacosService.getInstance().getNodeMap();
        if (nodeMap == null || nodeMap.get(serverId) == null) {
            LoggerDef.NetLogger.warn("get server type failed, serverId={}", serverId);
            return ServerTypeEnum.UNKNOWN;
        }
        Object nodeInfo = nodeMap.get(serverId);
        if (nodeInfo instanceof NacosServerNode serverNode) {
            return serverNode.getServerType();
        }
        LoggerDef.NetLogger.warn("node info type mismatch, serverId={}", serverId);
        return ServerTypeEnum.UNKNOWN;
    }
    
    /**
     * 清理资源，避免内存泄漏
     */
    public void cleanup() {
        if (isConnect()) {
            NetClientManager.getInstance().delNetClient(client);
            LoggerDef.NetLogger.info("cleanup rpc node connector, serverId={}", serverId);
        }
    }

}
