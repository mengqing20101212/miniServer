package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.ConnectionAckPacket;
import org.apache.logging.log4j.core.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
  static Logger logger = LoggerDef.SystemLogger;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket msg)
      throws Exception {
    if (LoggerDef.NetLogger.isDebugEnabled()) {
      LoggerDef.NetLogger.debug(
          String.format(
              "客户端(sid:%s, remote:%s), 收到消息：%s",
              ctx.channel().id(), ctx.channel().remoteAddress(), msg));
    }
    NetClient netClient = ctx.channel().attr(NetClient.SELF_ATTR_KEY).get();
    if (netClient == null) {
      ctx.channel().close();
    } else {
      netClient.addReceivePacket(msg);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.error(
        "客户端发生异常 sid: "
            + ctx.channel().id().asLongText()
            + ", remote:"
            + ctx.channel().remoteAddress(),
        cause);
    NetClient netClient = ctx.channel().attr(NetClient.SELF_ATTR_KEY).get();
    NetClientManager.getInstance().delNetClient(netClient);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    String closeStr = "本端主动断开";
    Boolean selfClosed = ctx.channel().attr(NetService.SELF_CLOSED).get();
    if (Boolean.TRUE.equals(selfClosed)) {
      closeStr = "连接是本端主动关闭的";
    } else {
      closeStr = "连接是对端关闭的";
    }
    logger.info(
        String.format(
            "连接断开 :[%s], sid:%s,  原因: %s",
            ctx.channel().remoteAddress(), ctx.channel().id(), closeStr));
    NetClient netClient = ctx.channel().attr(NetClient.SELF_ATTR_KEY).get();
    NetClientManager.getInstance().delNetClient(netClient);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    logger.info(
        "ClientHandler channelActive 客户端连接成功 "
            + ctx.channel().id().asLongText()
            + ", remote: "
            + ctx.channel().remoteAddress());
    // 连接成功 请求 sessionId
    ctx.channel().writeAndFlush(new ConnectionAckPacket());
  }
}
