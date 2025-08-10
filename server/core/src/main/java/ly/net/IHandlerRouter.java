package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;

@FunctionalInterface
public interface IHandlerRouter {
    /**
     * 处理路由
     *
     * @param session 会话
     * @param packet  包
     * @param request 请求
     */
    public abstract void execute(ConnectSession session, AbstractMessagePacket packet, AbstractMessage request);
}
