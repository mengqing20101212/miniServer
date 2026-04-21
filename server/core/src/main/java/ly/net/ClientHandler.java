package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;

public class ClientHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
    static Logger logger = LoggerDef.SystemLogger;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket msg)
            throws Exception {
        if (LoggerDef.NetLogger.isDebugEnabled()) {
            LoggerDef.NetLogger.debug("客户端(sid:{}, remote:{}), 收到消息：{}", ctx.channel().id(), ctx.channel().remoteAddress(), msg);
        }
        NetClient netClient = ctx.channel().attr(NetClient.SELF_ATTR_KEY).get();
        if (netClient == null) {
            ctx.channel().close();
            LoggerDef.NetLogger.info("客户端(sid:{}, remote:{}), 连接异常, 关闭连接", ctx.channel().id(), ctx.channel().remoteAddress());
        } else {
            netClient.addReceivePacket(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("客户端发生异常 sid: {}, remote:{}", ctx.channel().id().asLongText(), ctx.channel().remoteAddress(), cause);
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

        logger.info("ClientHandler channelActive 客户端连接成功 {}, remote: {}", ctx.channel().id().asLongText(), ctx.channel().remoteAddress());
        // 连接成功 请求 sessionId
        ctx.channel().writeAndFlush(new AbstractMessagePacket(0));
    }
}
