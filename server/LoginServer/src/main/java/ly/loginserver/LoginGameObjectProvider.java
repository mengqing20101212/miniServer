package ly.loginserver;

import io.netty.channel.ChannelHandlerContext;
import ly.net.ConnectSession;
import ly.net.GameObjectProvider;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginGameObjectProvider
 */
public class LoginGameObjectProvider implements GameObjectProvider {
    AtomicInteger guidCreate = new AtomicInteger(0);

    @Override
    public ConnectSession createGameObject(ChannelHandlerContext ctx) {
        return new ConnectSession(guidCreate.getAndIncrement());
    }
}
