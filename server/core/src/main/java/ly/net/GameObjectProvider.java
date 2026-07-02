package ly.net;

import io.netty.channel.ChannelHandlerContext;

/**
 * 新连接会话工厂。
 * <p>
 * NetService 在 channelActive 时调用它创建业务侧 {@link ConnectSession}，不同服务可返回
 * 自己的会话子类，例如网关连接或游戏服连接。
 */
public interface GameObjectProvider {
    public ConnectSession createGameObject(ChannelHandlerContext ctx);
}
