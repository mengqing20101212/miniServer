package ly.net;

import com.google.protobuf.AbstractMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.net.packet.S2SMessagePacket;
import org.apache.logging.log4j.core.Logger;

public class NetClient {
  static Logger logger = LoggerDef.NetLogger;

  private final String host;
  private final int port;
  private Channel channel;
  private EventLoopGroup group;
  private int sid;
  BlockingQueue<AbstractMessagePacket> receivePacketQueue = new ArrayBlockingQueue<>(1024);
  AtomicInteger sendSeq = new AtomicInteger(0);

  public NetClient(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getId() {
    return channel.id().asLongText();
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getIpPortKey() {
    return host + ":" + port;
  }

  public void start(EventLoopGroup group) {
    this.group = group;
    connectOnce();
  }

  private void connectOnce() {
    Bootstrap bootstrap = new Bootstrap();
    bootstrap
        .group(group)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .option(ChannelOption.SO_SNDBUF, NetService.SO_SNDBUF)
        .option(ChannelOption.SO_RCVBUF, NetService.SO_RCVBUF)
        .handler(
            new ChannelInitializer<Channel>() {
              @Override
              protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                    .addLast(new CommonDecoder())
                    .addLast(new CommonEncoder())
                    .addLast(new ClientHandler());
              }
            });

    try {
      ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
      if (future.isSuccess()) {
        channel = future.channel();
        logger.info("Netty 客户端连接成功：" + host + ":" + port);
      }
    } catch (Exception e) {
      logger.error("客户端连接失败", e);
    }
  }

  public boolean sendS2SMessage(long guid, int cmd, AbstractMessage protoData) {
    S2SMessagePacket messagePacket =
        MessagePacketFactory.createS2SMessagePacket(
            guid, cmd, protoData, sendSeq.getAndIncrement(), sid);
    return send(messagePacket);
  }

  public void setSid(int sid) {
    if (this.sid != 0) {
      this.sid = sid;
    }
  }

  public int getSendSeq() {
    return sendSeq.getAndIncrement();
  }

  public void stop() {
    if (group != null) {
      group.shutdownGracefully();
    }
  }

  public boolean isConnected() {
    return channel != null && channel.isActive();
  }

  public boolean send(AbstractMessagePacket packet) {
    if (isConnected()) {
      sendPacket(packet);
      return true;
    } else {
      int i = 3;
      while (i-- > 0) {
        connectOnce();
        logger.info(String.format("连接不可用，正在重试第 :%d 次", i));
        if (isConnected() && sendPacket(packet)) {
          return true;
        }
      }
      logger.warn("连接不可用，发送失败！");
    }
    return false;
  }

  public AbstractMessagePacket readPacket() {
    return receivePacketQueue.poll();
  }

  public List<AbstractMessagePacket> readAllPackets() {
    if (!receivePacketQueue.isEmpty()) {
      List<AbstractMessagePacket> packets = new ArrayList<>();
      receivePacketQueue.drainTo(packets); // 一次性取出所有内容
      return packets;
    }
    return new ArrayList<>();
  }

  private synchronized boolean sendPacket(AbstractMessagePacket packet) {
    packet.setSid(sid);
    if (LoggerDef.NetLogger.isDebugEnabled()) {
      LoggerDef.NetLogger.debug(String.format("send packet:%s", packet));
    }
    channel.writeAndFlush(packet);
    return true;
  }

  public void addReceivePacket(AbstractMessagePacket packet) {
    setSid(packet.getSid());
    receivePacketQueue.add(packet);
  }

  public Channel getChannel() {
    return channel;
  }

  @Override
  public String toString() {
    return "NetClient{"
        + "host='"
        + host
        + '\''
        + ", port="
        + port
        + ", sid="
        + sid
        + ", sendSeq="
        + sendSeq
        + '}';
  }
}
