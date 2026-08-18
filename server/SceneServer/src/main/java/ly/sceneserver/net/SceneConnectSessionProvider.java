package ly.sceneserver.net;

import java.util.concurrent.atomic.AtomicLong;

import io.netty.channel.ChannelHandlerContext;
import ly.net.GameObjectProvider;

/** 创建 SceneServer 的服务器间连接会话。 */
public final class SceneConnectSessionProvider implements GameObjectProvider {
    private final AtomicLong guid = new AtomicLong();

    @Override
    public SceneConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new SceneConnectSession(guid.incrementAndGet());
    }
}
