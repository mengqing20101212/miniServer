package ly.net;

import com.google.protobuf.AbstractMessage;
import ly.net.packet.AbstractMessagePacket;

/**
 * 处理器路由接口
 */
@FunctionalInterface
public interface IHandlerRouter<S extends ConnectSession, P extends AbstractMessagePacket, R extends AbstractMessage> {
    /**
     * 处理路由
     *
     * @param context 处理器上下文（包含会话和数据包）
     * @param request 请求消息对象
     */
    public abstract void execute(HandlerContext<S, P> context, R request);
}