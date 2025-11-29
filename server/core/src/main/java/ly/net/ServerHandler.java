package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.ConnectionAckPacket;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: ServerHandler
 */
public class ServerHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
    static final Logger log = LoggerDef.NetLogger;
    static AtomicInteger sessionCreator = new AtomicInteger(1);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket packet) throws Exception {
        // 连接确认包特殊处理
        if (packet instanceof ConnectionAckPacket) {
            ctx.channel().writeAndFlush(new ConnectionAckPacket(sessionCreator.getAndIncrement()));
            return;
        }

        // 获取会话
        ConnectSession session = NetService.getInstance().getGameObject(ctx);
        if (session == null) {
            log.error("Got null gameObject from channel[{}], :{}, packet:{}", ctx.channel().id(), ctx.channel().remoteAddress(), packet);
            ctx.close();
            return;
        }

        // 处理消息分发 - 使用新的HandlerContext机制
        session.addReceivePacket(packet);
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
        log.info(String.format("连接断开 :[%s], sid:%s,  原因: %s", ctx.channel().remoteAddress(), ctx.channel().id(), closeStr));
        NetService.getInstance().delChannel(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error(String.format("连接[sid: %s, %s]异常", ctx.channel().id(), ctx.channel().remoteAddress()), cause);
        NetService.getInstance().delChannel(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ConnectSession object = NetService.getInstance().addChannel(ctx);
        log.info(String.format("收到新连接sid:%s, GameObjectSid:%d :[%s]", ctx.channel().id(), object.connector.sessionId, ctx.channel().remoteAddress()));
        object.getConnector().setStatus(Connector.CONNECT_STATUS_OPEN);
    }
}