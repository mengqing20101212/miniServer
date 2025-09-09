package ly.net;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

public class GameConnectSessionProvider implements GameObjectProvider {
    private final AtomicLong guid = new AtomicLong();

    @Override
    public GameConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new GameConnectSession(guid.incrementAndGet());
    }
}
