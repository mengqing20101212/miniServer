package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.HandlerContext;
import ly.net.packet.AbstractMessagePacket;
import ly.proto.Login;

/**
 * 游戏服登录任务对象，记录一次登录流程中跨线程处理所需的上下文。
 */
public class LoginTask {
    final GameConnectSession session;
    final AbstractMessagePacket packet;
    final Login.csLogin request;

    public LoginTask(HandlerContext<GameConnectSession, AbstractMessagePacket> context, Login.csLogin request) {
        this.session = context.session();
        this.packet = (AbstractMessagePacket) context.packet();
        this.request = request;
    }
}
