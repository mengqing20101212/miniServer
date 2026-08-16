package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.HandlerContext;
import ly.net.packet.MessagePacket;
import ly.proto.Login;

/**
 * 游戏服登录任务，保存一次登录在异步登录协程中需要的上下文。
 */
public class LoginTask {
    final GameConnectSession session;
    final MessagePacket packet;
    final Login.csLogin request;
    final long callId;

    public LoginTask(HandlerContext<GameConnectSession, MessagePacket> context, Login.csLogin request) {
        this(context.session(), context.packet(), request, 0);
    }

    public LoginTask(
            GameConnectSession session,
            MessagePacket packet,
            Login.csLogin request,
            long callId) {
        this.session = session;
        this.packet = packet;
        this.request = request;
        this.callId = callId;
    }
}
