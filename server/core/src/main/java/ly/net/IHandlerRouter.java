package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;

@FunctionalInterface
public interface IHandlerRouter<S extends ConnectSession, P extends AbstractMessagePacket, R extends AbstractMessage> {
    /**
     * 处理路由
     *
     * @param session 会话
     * @param packet  包
     * @param request 请求
     */
    public abstract void execute(S session, P packet, R request);
}
