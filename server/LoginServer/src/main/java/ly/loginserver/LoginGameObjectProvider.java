package ly.loginserver;

import io.netty.channel.ChannelHandlerContext;
import ly.net.ConnectSession;
import ly.net.GameObjectProvider;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录服连接对象工厂，负责为公共网络层创建登录服会话对象。
 */
public class LoginGameObjectProvider implements GameObjectProvider {
    AtomicInteger guidCreate = new AtomicInteger(0);

    @Override
    public ConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new ConnectSession(guidCreate.getAndIncrement());
    }
}
