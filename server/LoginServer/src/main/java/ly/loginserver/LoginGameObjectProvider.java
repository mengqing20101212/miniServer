package ly.loginserver;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.atomic.AtomicInteger;
import ly.net.GameObject;
import ly.net.GameObjectProvider;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginGameObjectProvider
 */
public class LoginGameObjectProvider implements GameObjectProvider {
  AtomicInteger guidCreate = new AtomicInteger(0);

  @Override
  public GameObject createGameObject(ChannelHandlerContext ctx) {
    return new GameObject(guidCreate.getAndIncrement());
  }
}
