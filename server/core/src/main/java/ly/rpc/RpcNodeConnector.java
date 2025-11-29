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

/**
 * RPC节点连接器类
 * <p>
 * 负责管理与远程服务器节点的RPC连接，提供消息发送、同步请求和心跳检测等功能。
 * 封装了底层的网络通信细节，简化了不同服务器节点之间的RPC调用。
 */
public class RpcNodeConnector {
    /**
     * 网络客户端实例，负责底层通信
     */
    NetClient client;
    /**
     * 目标服务器ID
     */
    private final String serverId;
    /**
     * 目标服务器IP地址
     */
    private final String ip;
    /**
     * 目标服务器端口
     */
    private final int port;
    /**
     * 默认超时时间（毫秒）
     */
    private final int DEFAULT_TIMEOUT = 1000;


    /**
     * 构造函数
     *
     * @param serverId 目标服务器ID
     * @param ip       目标服务器IP地址
     * @param port     目标服务器端口
     */
    public RpcNodeConnector(String serverId, String ip, int port) {
        this.serverId = serverId;
        this.ip = ip;
        this.port = port;
        // 通过NetClientManager创建新的网络客户端
        this.client = NetClientManager.getInstance().newNetClient(ip, port);
    }

    /**
     * 检查连接是否可用
     *
     * @return 如果连接可用返回true，否则返回false
     */
    public boolean isConnect() {
        if (client == null) {
            return false;
        }
        return client.isReady();
    }

    /**
     * 获取网络客户端实例
     *
     * @return 网络客户端实例
     */
    public NetClient getClient() {
        return client;
    }

    /**
     * 设置网络客户端实例
     *
     * @param client 新的网络客户端实例
     */
    public void setClient(NetClient client) {
        this.client = client;
    }

    /**
     * 获取目标服务器ID
     *
     * @return 服务器ID
     */
    public String getServerId() {
        return serverId;
    }

    /**
     * 获取目标服务器IP地址
     *
     * @return IP地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 获取目标服务器端口
     *
     * @return 端口号
     */
    public int getPort() {
        return port;
    }

    /**
     * 发送心跳包
     * <p>
     * 维持与远程节点的连接活跃状态，防止连接被断开
     */
    public void pingConnect() {
        // 构建心跳数据包，包含当前时间戳和服务器ID
        Server.csRpcPing ping = Server.csRpcPing.newBuilder()
                .setTime(System.currentTimeMillis())
                .setServerId(ServerContext.getServerId())
                .build();
        // 发送心跳消息
        sendProtoMessage(0, Cmd.CMD.CS_RpcPing_VALUE, ping);
    }

    /**
     * 发送Protobuf格式的消息
     *
     * @param guid      全局唯一标识符
     * @param cmd       命令ID
     * @param protoData Protobuf消息对象
     * @return 发送序列，失败返回-1
     */
    public int sendProtoMessage(long guid, int cmd, AbstractMessage protoData) {
        return client.sendS2SMessage(guid, cmd, protoData);
    }

    /**
     * 发送消息包
     *
     * @param packet 消息包对象
     * @return 发送成功返回true，失败返回false
     */
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

    /**
     * 同步发送消息包并等待响应
     *
     * @param packet 要发送的消息包
     * @param timeout 超时时间（毫秒）
     * @return 响应消息包，如果超时或失败则返回null
     */
    public AbstractMessagePacket syncSendPacket(AbstractMessagePacket packet, int timeout) {
        final int sendSeq = packet.getSeq();

        try {
            if (sendPacket(packet)) {
                long startTime = System.currentTimeMillis();
                long endTime = startTime + timeout;

                // 使用简单的轮询方式等待响应，适合虚拟线程环境
                while (System.currentTimeMillis() < endTime) {
                    // 查找对应序列号和命令的响应消息
                    AbstractMessagePacket response = client.getReceiveMsgBySeq(sendSeq, packet.getCmd() + 1);
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

    /**
     * 同步发送消息包并等待响应（使用默认超时时间）
     *
     * @param packet 要发送的消息包
     * @return 响应消息包，如果超时或失败则返回null
     */
    public AbstractMessagePacket syncSendPacket(AbstractMessagePacket packet) {
        return syncSendPacket(packet, DEFAULT_TIMEOUT);
    }

    /**
     * 同步发送Protobuf消息并等待响应（使用默认超时时间）
     *
     * @param guid      全局唯一标识符
     * @param cmd       命令ID
     * @param protoData Protobuf消息对象
     * @return 响应消息对象，如果超时或失败则返回null
     */
    public AbstractMessage syncSendProtoMessage(long guid, int cmd, AbstractMessage protoData) {
        return syncSendProtoMessage(guid, cmd, protoData, DEFAULT_TIMEOUT);
    }

    /**
     * 同步发送Protobuf消息并等待响应
     *
     * @param guid      全局唯一标识符
     * @param cmd       命令ID
     * @param protoData Protobuf消息对象
     * @param timeout   超时时间（毫秒）
     * @return 响应消息对象，如果超时或失败则返回null
     */
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
                // 查找对应序列号和命令的响应消息
                AbstractMessagePacket response = client.getReceiveMsgBySeq(sendReq, cmd + 1);
                if (response != null) {
                    // 解析响应数据包
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

    /**
     * 解析接收到的数据包
     *
     * @param receivedPacket 接收到的数据包
     * @return 解析后的Protobuf消息对象，如果解析失败则返回null
     */
    private AbstractMessage unpackPacket(AbstractMessagePacket receivedPacket) {
        if (receivedPacket instanceof S2SMessagePacket s2sMessagePacket) {
            try {
                // 使用ProtoMessageFactory创建对应的Protobuf消息对象
                return ProtoMessageFactory.createProtoMessage(s2sMessagePacket.getCmd(), s2sMessagePacket.getData());
            } catch (Exception e) {
                LoggerDef.NetLogger.error("Failed to unpack packet, serverId={}, cmd={}, error={}",
                        serverId, s2sMessagePacket.getCmd(), e.getMessage());
            }
        }
        return null;
    }

    /**
     * 获取目标服务器的类型
     *
     * @return 服务器类型枚举，如果无法获取则返回UNKNOWN
     */
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
     * <p>
     * 当不再需要此连接时调用此方法释放相关资源
     */
    public void cleanup() {
        if (isConnect()) {
            // 通过NetClientManager删除网络客户端
            NetClientManager.getInstance().delNetClient(client);
            LoggerDef.NetLogger.info("cleanup rpc node connector, serverId={}", serverId);
        }
    }

}