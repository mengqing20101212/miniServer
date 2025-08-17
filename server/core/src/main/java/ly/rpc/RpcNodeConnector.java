package ly.rpc;

import com.google.protobuf.AbstractMessage;
import ly.LoggerDef;
import ly.ProtoMessageFactory;
import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.nacos.NacosService;
import ly.net.NetClient;
import ly.net.NetClientManager;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Server;

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
                LoggerDef.NetLogger.error("send packet failed, serverId={}, packet={}", serverId, packet);
                return false;
            } else {
                LoggerDef.ProtoLogger.info("send packet  {}|{}", serverId, packet.toSimpleString());
                return true;
            }
        }
        return false;
    }

    public synchronized AbstractMessagePacket syncSendPacket(AbstractMessagePacket packet, int timeout) {
        final int sendSeq = packet.getSeq();
        if (sendPacket(packet)) {
            long timeoutTime = System.currentTimeMillis() + timeout;
            AbstractMessagePacket receivedPacket = client.getReceiveMsgBySeq(sendSeq);
            while (System.currentTimeMillis() < timeoutTime && (receivedPacket = client.getReceiveMsgBySeq(sendSeq)) == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return receivedPacket;
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
            LoggerDef.NetLogger.error("send proto message failed, serverId={}, guid={}, cmd={}", serverId, guid, cmd);
            return null;
        }
        AbstractMessagePacket receivedPacket = client.getReceiveMsgBySeq(sendReq);
        if (receivedPacket == null) {
            long timeoutTime = System.currentTimeMillis() + timeout;

            while (System.currentTimeMillis() < timeoutTime && (receivedPacket = client.getReceiveMsgBySeq(sendReq)) == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (receivedPacket != null) {
            return unpackPacket(receivedPacket);
        }

        return null;
    }

    private AbstractMessage unpackPacket(AbstractMessagePacket receivedPacket) {
        if (receivedPacket instanceof S2SMessagePacket s2sMessagePacket) {
            return ProtoMessageFactory.createProtoMessage(s2sMessagePacket.getCmd(), s2sMessagePacket.getData());
        }
        return null;
    }

    public ServerTypeEnum getServerType() {
        if (NacosService.getInstance().getNodeMap().get(serverId) == null) {
            LoggerDef.NetLogger.error("get server type failed, serverId={}", serverId);
            return ServerTypeEnum.UNKNOWN;
        }
        return NacosService.getInstance().getNodeMap().get(serverId).getServerType();
    }

}
