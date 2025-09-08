package ly.net;

import io.netty.channel.ChannelHandlerContext;
import ly.net.packet.AbstractMessagePacket;

import static ly.LoggerDef.NetLogger;

/*
 * Author: liuYang
 * Date: 2025/4/8
 * File: Connector
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

   /* public void write(byte[] msg) throws Exception {
        if (isConnected()) {
            socketChannel.channel().writeAndFlush(msg);
        } else {
            NetLogger.error("该连接未准备好,不可使用 sid:{},remoteAddress:{}", sessionId, socketChannel.channel().remoteAddress());
        }
    }*/

    public synchronized boolean write(AbstractMessagePacket packet) {
        if (packet.getSid() == 0) {
            packet.setSid(sessionId);
        }

        if (isConnected()) {
            if (NetLogger.isDebugEnabled()) {
                NetLogger.debug("send packet:{}", packet);
            }
            socketChannel.channel().writeAndFlush(packet);
            return true;
        } else {
            NetLogger.error("该连接未准备好,不可使用 sid:{},remoteAddress:{}", sessionId, socketChannel.channel().remoteAddress());
        }
        return false;
    }
}
