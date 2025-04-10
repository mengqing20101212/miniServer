package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.AttributeKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import ly.LoggerDef;
import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: NetServcie
 */
public class NetService {
  protected static final AttributeKey<Boolean> SELF_CLOSED = AttributeKey.valueOf("selfClosed");

  /** tcp 收包 缓冲池大小 32K* */
  static final int SO_RCVBUF = 1024 * 32;

  static final Logger log = LoggerDef.SystemLogger;
  GameObjectProvider gameObjectProvider;
  static final int SO_SNDBUF = 1024 * 32;
  private static final NetService INSTANCE = new NetService();
  static EventLoopGroup boss = new NioEventLoopGroup(1);
  static EventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
  List<NetServer> servers = new ArrayList<NetServer>();
  Map<Long, GameObject> gameObjectMaps = new ConcurrentHashMap<>();
  Map<ChannelHandlerContext, GameObject> gameObjectContextMaps = new ConcurrentHashMap<>();
  AtomicInteger sidCreator = new AtomicInteger();

  private NetService() {}

  public static NetService getInstance() {
    return INSTANCE;
  }

  public int createSid() {
    return sidCreator.incrementAndGet();
  }

  public void startUp(GameObjectProvider gameObjectProvider, int... ports) {
    if (ports.length == 0) {
      throw new IllegalArgumentException("No ports provided");
    }
    this.gameObjectProvider = gameObjectProvider;

    Thread.ofVirtual()
        .name("send-packet-task")
        .start(
            () -> {
              while (true) {
                try {
                  Thread.sleep(10L);
                  gameObjectMaps
                      .values()
                      .forEach(
                          gameObject -> {
                            gameObject.sendAllPackets();
                          });
                } catch (InterruptedException e) {
                  e.printStackTrace();
                }
              }
            });

    for (final int port : ports) {
      NetServer netServer = new NetServer(port, "NetServer-" + port);
      servers.add(netServer);
      netServer.startUp(boss, worker);
    }
  }

  public void delChannel(ChannelHandlerContext ctx) {
    GameObject object = gameObjectContextMaps.remove(ctx);
    if (object != null) {
      gameObjectMaps.remove(object.getGuid());
      object.closeChannel();
    }
    log.info(
        String.format("关闭远端连接 sid:%s, :%s  ", ctx.channel().id(), ctx.channel().remoteAddress()));
  }

  public GameObject addChannel(ChannelHandlerContext ctx) {
    GameObject object = gameObjectProvider.createGameObject(ctx);
    object.setConnector(new Connector(ctx, createSid()));
    gameObjectMaps.put(object.getGuid(), object);
    gameObjectContextMaps.put(ctx, object);
    return object;
  }

  public GameObject getGameObject(ChannelHandlerContext channelHandlerContext) {
    return gameObjectContextMaps.get(channelHandlerContext);
  }

  public Map<Long, GameObject> getGameObjectMaps() {
    return gameObjectMaps;
  }
}
