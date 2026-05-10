package ly.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ly.LoggerDef;
import ly.net.packet.AbstractMessagePacket;
import org.slf4j.Logger;

/**
 * 服务端 Netty 入站处理器。
 * <p>
 * 它只处理连接生命周期和包入队，不直接执行业务逻辑。业务处理由各服务自己的 tick 或路由
 * 逻辑从 {@link ConnectSession} 接收队列中取包后完成。
 */
public class ServerHandler extends SimpleChannelInboundHandler<AbstractMessagePacket> {
    static final Logger log = LoggerDef.NetLogger;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AbstractMessagePacket packet) throws Exception {
        // 客户端建立 TCP 后会发 CMD_ACK=0 请求 sid，服务端返回分配的会话 id。
        if (packet.getCmd() == AbstractMessagePacket.CMD_ACK) {
            ConnectSession session = NetService.getInstance().getGameObject(ctx);
            if (session == null || session.getConnector() == null) {
                log.error("ACK failed, session not found for channel[{}], remote:{}", ctx.channel().id(), ctx.channel().remoteAddress());
                ctx.close();
                return;
            }
            // ACK sid 必须和 Connector sid 保持一致，Gate 后续要靠客户端原样带回的 sid 定位连接。
            ctx.channel().writeAndFlush(new AbstractMessagePacket(session.getConnector().getSessionId()));
            return;
        }

        // 普通业务包必须先找到 channelActive 阶段创建的会话。
        ConnectSession session = NetService.getInstance().getGameObject(ctx);
        if (session == null) {
            log.error("Got null gameObject from channel[{}], :{}, packet:{}", ctx.channel().id(), ctx.channel().remoteAddress(), packet);
            ctx.close();
            return;
        }

        // 这里只入队，避免 Netty IO 线程直接执行耗时业务。
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
        // 新连接会通过 GameObjectProvider 创建对应服务自己的 ConnectSession 子类。
        ConnectSession object = NetService.getInstance().addChannel(ctx);
        log.info(String.format("收到新连接sid:%s, GameObjectSid:%d :[%s]", ctx.channel().id(), object.connector.sessionId, ctx.channel().remoteAddress()));
        object.getConnector().setStatus(Connector.CONNECT_STATUS_OPEN);
    }
}
