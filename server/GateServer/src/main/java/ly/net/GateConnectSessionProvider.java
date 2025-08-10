package ly.net;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

public class GateConnectSessionProvider implements GameObjectProvider {
    private final AtomicLong guid = new AtomicLong();

    @Override
    public GateConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new GateConnectSession(guid.incrementAndGet());
    }
}
