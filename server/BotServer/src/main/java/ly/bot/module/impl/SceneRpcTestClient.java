package ly.bot.module.impl;

import java.util.concurrent.TimeUnit;

import com.google.protobuf.AbstractMessage;

import ly.ProtoMessageFactory;
import ly.net.NetClient;
import ly.net.NetService;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;

/**
 * BotServer 场景回归测试共用的真实 TCP 客户端。
 *
 * <p>这里没有绕过网络层直接调用 SceneServer：每个请求都经过 Netty 编解码、连接 SID、
 * SceneRpcDispatcher、Controller 和 Protobuf 工厂。功能测试和容量测试共用这一层，避免两套
 * 等待响应和解包代码产生不同判断标准。
 */
final class SceneRpcTestClient implements AutoCloseable {
    private static final long CONNECT_TIMEOUT_MILLIS = 10_000L;
    private static final long DEFAULT_RESPONSE_TIMEOUT_MILLIS = 10_000L;
    private static final long POLL_INTERVAL_MILLIS = 2L;

    private final NetClient client;

    private SceneRpcTestClient(NetClient client) {
        this.client = client;
    }

    /** 连接 SceneServer，并等待服务端握手分配非零 SID。 */
    static SceneRpcTestClient connect(String host, int port) throws InterruptedException {
        NetClient client = new NetClient(host, port, false);
        client.start(NetService.getInstance().getWorkerGroup());
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(CONNECT_TIMEOUT_MILLIS);
        while (System.nanoTime() < deadline) {
            if (client.isReady() && client.getSid() > 0) {
                return new SceneRpcTestClient(client);
            }
            Thread.sleep(POLL_INTERVAL_MILLIS);
        }
        if (client.isConnected()) {
            client.stop();
        }
        throw new IllegalStateException("连接 SceneServer 超时: " + host + ":" + port);
    }

    int sid() {
        return client.getSid();
    }

    /**
     * 发送一个请求并等待指定 CMD 的响应。
     *
     * <p>当前功能回归按请求串行执行，业务 requestId 负责校验幂等字段；链路 seq 只用于
     * 诊断发包失败，不能错误地当成 RPC callId。
     */
    <T> T exchange(
            long guid,
            Cmd.CMD requestCmd,
            AbstractMessage request,
            Cmd.CMD responseCmd,
            Class<T> responseType) throws InterruptedException {
        send(guid, requestCmd, request);
        return decode(await(responseCmd, DEFAULT_RESPONSE_TIMEOUT_MILLIS), responseType);
    }

    /** 只发送请求；容量测试用它做有界批量流水线。 */
    int send(long guid, Cmd.CMD requestCmd, AbstractMessage request) {
        int requestSeq = client.sendS2SMessage(guid, requestCmd.getNumber(), request);
        if (requestSeq < 0) {
            throw new IllegalStateException("Scene RPC 发送失败: cmd=" + requestCmd + ", guid=" + guid);
        }
        return requestSeq;
    }

    /** 等待一条指定协议响应，忽略并打印连接上的其他业务包。 */
    MessagePacket await(Cmd.CMD responseCmd, long timeoutMillis) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
        while (System.nanoTime() < deadline) {
            MessagePacket packet = client.readPacket();
            if (packet == null) {
                Thread.sleep(POLL_INTERVAL_MILLIS);
                continue;
            }
            if (packet.getCmd() == responseCmd.getNumber()) {
                if (packet.getSid() != sid()) {
                    throw new IllegalStateException(
                            "Scene RPC SID 不一致: expected=" + sid() + ", actual=" + packet.getSid());
                }
                return packet;
            }
            System.out.printf(
                    "[SCENE-RPC] ignore packet cmd=%d seq=%d sid=%d%n",
                    packet.getCmd(), packet.getSeq(), packet.getSid());
        }
        throw new IllegalStateException("等待 SceneServer 响应超时: cmd=" + responseCmd);
    }

    /** 使用项目统一 ProtoMessageFactory 解包，保证生成协议注册也在回归范围内。 */
    <T> T decode(MessagePacket packet, Class<T> responseType) {
        Object decoded = ProtoMessageFactory.createProtoMessage(packet.getCmd(), packet.getData());
        if (!responseType.isInstance(decoded)) {
            throw new IllegalStateException(
                    "Scene RPC 响应类型错误: expected=" + responseType.getSimpleName()
                            + ", actual=" + (decoded == null ? "null" : decoded.getClass().getName()));
        }
        return responseType.cast(decoded);
    }

    @Override
    public void close() {
        if (client.isConnected()) {
            client.stop();
        }
    }
}
