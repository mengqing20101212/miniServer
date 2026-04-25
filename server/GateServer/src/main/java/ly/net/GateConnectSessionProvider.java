package ly.net;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 网关连接会话，封装客户端连接、收发队列和网关转发所需状态。
 */
public class GateConnectSessionProvider implements GameObjectProvider {
    private final AtomicLong guid = new AtomicLong();

    @Override
    public GateConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new GateConnectSession(guid.incrementAndGet());
    }
}
