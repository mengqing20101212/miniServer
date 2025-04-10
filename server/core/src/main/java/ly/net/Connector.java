package ly.net;

import io.netty.channel.ChannelHandlerContext;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: Connector
 */
public class Connector {
  /** 该socket 刚刚被初始化 不可读写 */
  static int CONNECT_STATUS_INIT = 0;

  /** 该 socket 已 连接 可以读写* */
  static int CONNECT_STATUS_OPEN = 1;

  /** 该 socket 已被关闭 不可读写* */
  static int CONNECT_STATUS_CLOSE = 2;

  ChannelHandlerContext socketChannel;
  int status = CONNECT_STATUS_INIT;
  int sessionId;
  int seq;

  public Connector(ChannelHandlerContext socketChannel, int sessionId) {
    this.sessionId = sessionId;
    this.socketChannel = socketChannel;
  }

  public boolean isConnected() {
    return status == CONNECT_STATUS_OPEN;
  }

  public void close() {
    status = CONNECT_STATUS_CLOSE;
    if (socketChannel != null) {
      socketChannel.close();
    }
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public void write(byte[] msg) throws Exception {
    if (isConnected()) {
      socketChannel.channel().writeAndFlush(msg);
    } else {
      LoggerDef.NetLogger.error(
          String.format(
              "该连接未准备好,不可使用 sid:%d,remoteAddress:%s",
              sessionId, socketChannel.channel().remoteAddress()));
    }
  }

  public synchronized boolean write(AbstractMessagePacket packet) {
    packet.setSid(sessionId);
    packet.setSeq(++seq);
    if (isConnected()) {
      if (LoggerDef.NetLogger.isDebugEnabled()) {
        LoggerDef.NetLogger.debug(String.format("send packet:%s", packet));
      }
      socketChannel.channel().writeAndFlush(packet);
      return true;
    } else {
      LoggerDef.NetLogger.error(
          String.format(
              "该连接未准备好,不可使用 sid:%d,remoteAddress:%s",
              sessionId, socketChannel.channel().remoteAddress()));
    }
    return false;
  }
}
