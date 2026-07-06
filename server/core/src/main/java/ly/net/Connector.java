package ly.net;

import io.netty.channel.ChannelHandlerContext;
import ly.net.packet.MessagePacket;

import java.net.InetSocketAddress;

import static ly.LoggerDef.NetLogger;

/**
 * 业务会话到 Netty Channel 的写入适配器。
 * <p>
 * {@link ConnectSession} 通过 Connector 写包和关闭连接，从而避免业务对象直接依赖
 * Channel 细节。status 是轻量连接状态，防止向未打开或已关闭连接写入。
 */
public class Connector {

    /**
     * 该socket 刚刚被初始化 不可读写
     */
    static int CONNECT_STATUS_INIT = 0;

    /**
     * 该 socket 已 连接 可以读写*
     */
    static int CONNECT_STATUS_OPEN = 1;

    /**
     * 该 socket 已被关闭 不可读写*
     */
    static int CONNECT_STATUS_CLOSE = 2;

    ChannelHandlerContext socketChannel;
    int status = CONNECT_STATUS_INIT;
    int sessionId;

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
            socketChannel.channel().attr(NetService.SELF_CLOSED).set(true);
            socketChannel.close();
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSessionId() {
        return sessionId;
    }

    public String getRemoteIp() {
        if (socketChannel == null || socketChannel.channel() == null) {
            return "";
        }
        if (socketChannel.channel().remoteAddress() instanceof InetSocketAddress address) {
            return address.getAddress() != null ? address.getAddress().getHostAddress() : address.getHostString();
        }
        return String.valueOf(socketChannel.channel().remoteAddress());
    }

    /*
     * public void write(byte[] msg) throws Exception {
     * if (isConnected()) {
     * socketChannel.channel().writeAndFlush(msg);
     * } else {
     * NetLogger.error("该连接未准备好,不可使用 sid:{},remoteAddress:{}", sessionId,
     * socketChannel.channel().remoteAddress());
     * }
     * }
     */

    /**
     * 写出协议包。
     * <p>
     * 如果调用方没有设置 sid，则自动写入服务端为当前连接分配的 sessionId。
     * 方法加 synchronized 是为了避免多线程同时复用同一个 packet/channel 时打乱状态设置。
     */
    public synchronized boolean write(MessagePacket packet) {
        if (packet.getSid() == 0) {
            packet.setSid(sessionId);
        }

        if (isConnected()) {
            // if (NetLogger.isDebugEnabled()) {
            // NetLogger.debug("send packet:{}", packet);
            // }
            socketChannel.channel().writeAndFlush(packet);
            return true;
        } else {
            NetLogger.error("该连接未准备好,不可使用 sid:{},remoteAddress:{}", sessionId, socketChannel.channel().remoteAddress());
        }
        return false;
    }
}
