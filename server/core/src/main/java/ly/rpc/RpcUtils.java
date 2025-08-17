package ly.rpc;

import com.google.protobuf.AbstractMessage;
import ly.config.ServerTypeEnum;
import ly.net.packet.AbstractMessagePacket;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RpcUtils {

    /**
     * 发送请求  不会等待返回
     *
     * @param serverId 目标服务器id
     * @param packet   要发送的数据包
     */
    public static void request(String serverId, AbstractMessagePacket packet) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(serverId);
        if (rpcNodeConnector != null) {
            rpcNodeConnector.sendPacket(packet);
        }
    }


    /***
     * 发送请求  会等待返回 同步阻塞
     * @param serverId 目标服务器id
     * @param guid  全局唯一id
     * @param cmd   命令id
     * @param protoData     要发送的protobuf数据
     * @return 目标服务器返回的protobuf数据
     * @param <R>  目标服务器返回的protobuf数据类型
     */
    public static <R extends AbstractMessage> R syncRequest(String serverId, long guid, int cmd, AbstractMessage protoData) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(serverId);
        if (rpcNodeConnector != null) {
            return (R) rpcNodeConnector.syncSendProtoMessage(guid, cmd, protoData);
        }
        return null;
    }


    public static <R extends AbstractMessage> CompletableFuture<R> asyncRequest(String serverId, long guid, int cmd, AbstractMessage protoData) {
        CompletableFuture<R> future = new CompletableFuture<>();
        future.completeAsync(() -> syncRequest(serverId, guid, cmd, protoData));
        return future;
    }

    /***
     * 广播请求  不会等待返回
     * @param serverType  目标服务器类型
     * @param packet   要发送的数据包
     */
    public static void broadcastRequest(ServerTypeEnum serverType, AbstractMessagePacket packet) {
        List<RpcNodeConnector> rpcNodeConnectors = RpcService.getInstance().getRpcNodeConnectorsByServerType(serverType);
        for (RpcNodeConnector rpcNodeConnector : rpcNodeConnectors) {
            rpcNodeConnector.sendPacket(packet);
        }
    }


}
