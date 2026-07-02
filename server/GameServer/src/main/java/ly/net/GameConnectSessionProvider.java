package ly.net;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

/**
 * GameConnectSessionProvider 的核心定义，承载所在包对应的业务模型或辅助逻辑。
 */
public class GameConnectSessionProvider implements GameObjectProvider {
    private final AtomicLong guid = new AtomicLong();

    @Override
    public GameConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new GameConnectSession(guid.incrementAndGet());
    }
}
