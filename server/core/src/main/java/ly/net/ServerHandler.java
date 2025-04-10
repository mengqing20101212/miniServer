package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.atomic.AtomicInteger;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.ConnectionAckPacket;
import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: ServerHandler
 */
public class ServerHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
  static final Logger log = LoggerDef.NetLogger;
  static AtomicInteger sessionCreator = new AtomicInteger(1);

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, AbstractMessagePacket abstractMessagePacket)
      throws Exception {
    //    收到消息
    if (LoggerDef.NetLogger.isDebugEnabled()) {
      LoggerDef.NetLogger.debug(
          String.format("receive packet[%s]", abstractMessagePacket.toString()));
    }
    // 收到请求 session 消息，直接返回 sessionID
    if (abstractMessagePacket instanceof ConnectionAckPacket) {
      channelHandlerContext
          .channel()
          .writeAndFlush(new ConnectionAckPacket(sessionCreator.getAndIncrement()));
      return;
    }
    GameObject gameObject = NetService.getInstance().getGameObject(channelHandlerContext);
    if (gameObject == null) {
      log.error(
          String.format(
              "Got null gameObject from channel[%s], :%s, packet:%s",
              channelHandlerContext.channel().id(),
              channelHandlerContext.channel().remoteAddress(),
              abstractMessagePacket));
      channelHandlerContext.close();
      return;
    }
    gameObject.addReceivePacket(abstractMessagePacket);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    super.channelUnregistered(ctx);
    String closeStr = "本端主动断开";
    Boolean selfClosed = ctx.channel().attr(NetService.SELF_CLOSED).get();
    if (Boolean.TRUE.equals(selfClosed)) {
      closeStr = "连接是本端主动关闭的";
    } else {
      closeStr = "连接是对端关闭的";
    }
    log.info(
        String.format(
            "连接断开 :[%s], sid:%s,  原因: %s",
            ctx.channel().remoteAddress(), ctx.channel().id(), closeStr));
    NetService.getInstance().delChannel(ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    super.exceptionCaught(ctx, cause);
    log.error(
        String.format("连接[sid: %s, %s]异常", ctx.channel().id(), ctx.channel().remoteAddress()),
        cause);
    NetService.getInstance().delChannel(ctx);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    super.channelActive(ctx);
    GameObject object = NetService.getInstance().addChannel(ctx);
    log.info(
        String.format(
            "收到新连接sid:%s, GameObjectSid:%d :[%s]",
            ctx.channel().id(), object.connector.sessionId, ctx.channel().remoteAddress()));
    object.getConnector().setStatus(Connector.CONNECT_STATUS_OPEN);
  }
}
