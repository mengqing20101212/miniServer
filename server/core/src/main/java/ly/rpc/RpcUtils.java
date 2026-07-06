package ly.rpc;

import com.google.protobuf.AbstractMessage;
import ly.config.ServerTypeEnum;
import ly.net.packet.MessagePacket;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务器间 RPC 组件，维护跨节点连接并提供同步/异步调用工具。
 */
public class RpcUtils {

    /**
     * 发送请求  不会等待返回
     *
     * @param serverId 目标服务器id
     * @param packet   要发送的数据包
     */
    public static void request(String serverId, MessagePacket packet) {
        request(serverId, packet, false);
    }

    /** 发送不等待响应的 RPC；saveOnFail 为 true 时发送失败会保存到 Redis 等目标服恢复后补发。 */
    public static void request(String serverId, MessagePacket packet, boolean saveOnFail) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(serverId);
        if (rpcNodeConnector != null) {
            boolean success = rpcNodeConnector.sendPacket(packet);
            if (!success && saveOnFail) {
                ReliableRpcStore.getInstance().save(serverId, packet, "send failed");
            }
        } else if (saveOnFail) {
            ReliableRpcStore.getInstance().save(serverId, packet, "connector unavailable");
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

    /**
     * 同步 RPC 失败后保存为可靠消息。
     * <p>
     * 当策略为 {@link RpcFailSavePolicy#SEND_FAILED_OR_TIMEOUT} 时，发送失败和响应超时都会保存，后续由
     * 可靠 RPC 补发任务重新投递。
     */
    public static <R extends AbstractMessage> R syncRequestOrSaveOnFail(
            String serverId,
            long guid,
            int cmd,
            AbstractMessage protoData,
            RpcFailSavePolicy failSavePolicy) {
        return syncRequestOrSaveOnFail(serverId, guid, cmd, protoData, 1000, failSavePolicy);
    }

    public static <R extends AbstractMessage> R syncRequestOrSaveOnFail(
            String serverId,
            long guid,
            int cmd,
            AbstractMessage protoData,
            int timeout,
            RpcFailSavePolicy failSavePolicy) {
        RpcNodeConnector rpcNodeConnector = RpcService.getInstance().getRpcNodeConnector(serverId);
        if (rpcNodeConnector != null) {
            return (R) rpcNodeConnector.syncSendProtoMessage(guid, cmd, protoData, timeout, failSavePolicy);
        }
        if (failSavePolicy != null && failSavePolicy != RpcFailSavePolicy.NONE) {
            // 连连接器都创建失败时，说明目标节点当前不可达；保存时也必须使用 S2S RPC 外壳。
            ly.net.packet.MessagePacket packet =
                    RpcNodeConnector.createServer2ServerRpcPacket(
                            guid, cmd, protoData, 0, 0, System.nanoTime() ^ Thread.currentThread().threadId());
            ReliableRpcStore.getInstance().save(serverId, packet, "connector unavailable");
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
    public static void broadcastRequest(ServerTypeEnum serverType, MessagePacket packet) {
        List<RpcNodeConnector> rpcNodeConnectors = RpcService.getInstance().getRpcNodeConnectorsByServerType(serverType);
        for (RpcNodeConnector rpcNodeConnector : rpcNodeConnectors) {
            rpcNodeConnector.sendPacket(packet);
        }
    }


}
