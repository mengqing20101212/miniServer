package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.apache.logging.log4j.core.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
  static Logger logger = LoggerDef.NetLogger;

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket msg)
      throws Exception {
    logger.info("客户端收到消息：" + msg);
    // 可以加入 GameObject 或其他业务处理
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.error("客户端发生异常", cause);
    ctx.close();
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    logger.warn("连接被断开");
    // 可在此触发重连机制
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    logger.info("客户端连接成功");
  }
}
