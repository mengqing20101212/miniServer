package ly.net;

import ly.net.packet.MessagePacket;

/**
 * 处理器上下文，封装会话和数据包
 */
public record HandlerContext<S extends ConnectSession, P extends MessagePacket>(
        S session,
        P packet
) {
    /**
     * 获取会话对象
     */
    @Override
    public S session() {
        return session;
    }

    /**
     * 获取数据包对象
     */
    @Override
    public P packet() {
        return packet;
    }

    /**
     * 获取命令ID
     */
    public int getCmd() {
        return packet.getCmd();
    }

    /**
     * 获取会话ID
     */
    public long getSessionId() {
        return session.getGuid();
    }
}
