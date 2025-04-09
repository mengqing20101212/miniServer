package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.atomic.AtomicInteger;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.apache.logging.log4j.core.Logger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: ServerHandler
 */
public class ServerHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
  static final Logger log = LoggerDef.NetLogger;
  static AtomicInteger sessionCreator = new AtomicInteger(0);

  @Override
  protected void channelRead0(
      ChannelHandlerContext channelHandlerContext, AbstractMessagePacket abstractMessagePacket)
      throws Exception {
    //    收到消息
    if (LoggerDef.NetLogger.isDebugEnabled()) {
      LoggerDef.NetLogger.debug(
          String.format("receive packet[%s]", abstractMessagePacket.toString()));
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
    log.info(String.format("连接断开:[%s], sid:%s", ctx.channel().remoteAddress(), ctx.channel().id()));
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
    log.error(
        String.format("收到新连接sid:%s, :[%s]", ctx.channel().id(), ctx.channel().remoteAddress()));
    GameObject object = NetService.getInstance().addChannel(ctx);
    object.getConnector().setStatus(Connector.CONNECT_STATUS_OPEN);
  }
}
