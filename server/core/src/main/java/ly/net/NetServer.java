package ly.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ly.LoggerDef;
import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: NetServer
 */
public class NetServer {
  static Logger logger = LoggerDef.SystemLogger;
  private int port;
  private String serverName;
  ServerBootstrap bootstrap;

  public NetServer(int port, String serverName) {
    this.port = port;
    this.serverName = serverName;
    bootstrap = new ServerBootstrap();
  }

  public int getPort() {
    return port;
  }

  public boolean startUp(EventLoopGroup boss, EventLoopGroup worker) {
    try {
      bootstrap
          .group(boss, worker)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024) // backlog 是 ServerSocket 的选项
          .childOption(
              ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // childOption 是针对客户端连接的 SocketChannel
          .childOption(ChannelOption.TCP_NODELAY, true)
          .childOption(ChannelOption.SO_KEEPALIVE, true)
          .childOption(ChannelOption.SO_SNDBUF, NetService.SO_SNDBUF)
          .childOption(ChannelOption.SO_RCVBUF, NetService.SO_RCVBUF)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel
                      .pipeline()
                      .addLast(new CommonDecoder())
                      .addLast(new CommonEncoder())
                      .addLast(new ServerHandler());
                }
              });

      bootstrap.bind(port).syncUninterruptibly();
      logger.info(String.format("服务器启动成功:[%s], port:%s", serverName, port));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  public void shutdown() {
    if (bootstrap != null) {
      bootstrap.clone();
    }
  }
}
