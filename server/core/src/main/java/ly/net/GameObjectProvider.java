package ly.net;

import io.netty.channel.ChannelHandlerContext;

/*
 * Author: liuYang
 * Date: 2025/4/9
 * File: GameObjectProvider
 */
public interface GameObjectProvider {
  public GameObject createGameObject(ChannelHandlerContext ctx);
}
