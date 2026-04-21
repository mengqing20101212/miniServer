package ly.net;

import com.google.protobuf.AbstractMessage;

/**
 * 游戏处理器路由接口
 */
@FunctionalInterface
public interface GameHandlerRouter<R extends AbstractMessage> {
    /**
     * 处理路由
     *
     * @param context 游戏处理器上下文（包含玩家和数据包）
     * @param request 请求
     */
    public abstract void execute(GameHandlerContext context, R request);
}