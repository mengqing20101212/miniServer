package ly.net;

import ly.net.packet.MessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Server;

/**
 * 服务器间 RPC 执行上下文。
 *
 * <p>目标服收到 {@code csServer2Server} 后，会在当前业务线程记录 callId。
 * 业务处理器仍然可以按原来的方式发送内部响应包，发送队列入口会把响应自动包装成
 * {@code scServer2Server}，并原样带回 callId。
 */
public final class Server2ServerRpcContext {
    private static final ThreadLocal<Long> CURRENT_CALL_ID = new ThreadLocal<>();

    private Server2ServerRpcContext() {
    }

    public static void run(long callId, Runnable task) {
        Long oldCallId = CURRENT_CALL_ID.get();
        CURRENT_CALL_ID.set(callId);
        try {
            task.run();
        } finally {
            if (oldCallId == null) {
                CURRENT_CALL_ID.remove();
            } else {
                CURRENT_CALL_ID.set(oldCallId);
            }
        }
    }

    /**
     * 捕获当前服务器间 RPC 的 callId，供真正异步的 Handler 在回调线程中恢复上下文。
     *
     * <p>返回 0 表示当前请求不是通用 Server2Server RPC，恢复时仍可安全调用 {@link #run}。
     */
    public static long currentCallId() {
        Long callId = CURRENT_CALL_ID.get();
        return callId == null ? 0L : callId;
    }

    public static MessagePacket wrapResponseIfNeeded(MessagePacket packet) {
        Long callId = CURRENT_CALL_ID.get();
        if (callId == null || callId <= 0 || packet == null) {
            return packet;
        }
        if (packet.getCmd() == Cmd.CMD.SC_Server2Server_VALUE) {
            return packet;
        }
        Server.scServer2Server response =
                Server.scServer2Server.newBuilder()
                        .setType(Server.ServerMsgType.protobufMsg)
                        .setCmd(packet.getCmd())
                        .setData(com.google.protobuf.ByteString.copyFrom(packet.getData()))
                        .setCallId(callId)
                        .build();
        return MessagePacketFactory.createMessagePacket(
                packet.getGuid(),
                Cmd.CMD.SC_Server2Server_VALUE,
                response,
                0,
                0);
    }
}
