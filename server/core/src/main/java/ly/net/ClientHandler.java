package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.apache.logging.log4j.core.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
  static Logger logger = LoggerDef.SystemLogger;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket msg)
      throws Exception {
    if (LoggerDef.NetLogger.isDebugEnabled()) {
      LoggerDef.NetLogger.debug(
          String.format(
              "客户端(sid:%s, remote:%s)收到消息：%s",
              ctx.channel().id(), ctx.channel().remoteAddress(), msg));
    }
    // 可以加入 GameObject 或其他业务处理
    NetClient netClient =
        NetClientManager.getInstance().getNetClient(ctx.channel().id().asLongText());
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
    ctx.close();
    NetClientManager.getInstance().delNetClient(ctx.channel().id().asLongText());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    logger.warn(
        "连接被断开 sid: "
            + ctx.channel().id().asLongText()
            + ", remote: "
            + ctx.channel().remoteAddress());
    // 可在此触发重连机制
    NetClientManager.getInstance().delNetClient(ctx.channel().id().asLongText());
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {

    logger.info(
        "客户端连接成功 "
            + ctx.channel().id().asLongText()
            + ", remote: "
            + ctx.channel().remoteAddress());
  }
}
