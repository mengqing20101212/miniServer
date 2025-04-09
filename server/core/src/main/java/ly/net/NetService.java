package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ly.LoggerDef;
import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: NetServcie
 */
public class NetService {
  /** tcp 收包 缓冲池大小 32K* */
  static final int SO_RCVBUF = 1024 * 32;

  static final Logger log = LoggerDef.SystemLogger;

  static final int SO_SNDBUF = 1024 * 32;
  private static final NetService INSTANCE = new NetService();
  static EventLoopGroup boss = new NioEventLoopGroup(1);
  static EventLoopGroup worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
  List<NetServer> servers = new ArrayList<NetServer>();
  IGuidCreator guidCreator;
  Map<Long, GameObject> gameObjectMaps = new ConcurrentHashMap<>();
  Map<ChannelHandlerContext, GameObject> gameObjectContextMaps = new ConcurrentHashMap<>();

  private NetService() {}

  public static NetService getInstance() {
    return INSTANCE;
  }

  public void startUp(IGuidCreator guidCreator, int... ports) {
    if (ports.length == 0) {
      throw new IllegalArgumentException("No ports provided");
    }
    this.guidCreator = guidCreator;

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
    }
    object.closeChannel();
    log.info(
        String.format("关闭远端连接 sid:%s, :%s  ", ctx.channel().id(), ctx.channel().remoteAddress()));
  }

  public void addChannel(ChannelHandlerContext ctx) {
    GameObject object = new GameObject(guidCreator.createGuid());
    object.setConnector(new Connector(ctx, ctx.channel().id().asLongText()));
    gameObjectMaps.put(object.getGuid(), object);
    gameObjectContextMaps.put(ctx, object);
  }

  public GameObject getGameObject(ChannelHandlerContext channelHandlerContext) {
    return gameObjectMaps.get(channelHandlerContext.channel().id());
  }

  public static void main(String[] args) {
    getInstance()
        .startUp(
            new IGuidCreator() {
              @Override
              public long createGuid() {
                return 0;
              }
            },
            5525,
            5526,
            5527);
  }
}
