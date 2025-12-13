package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.HandlerContext;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Login;

public class LoginTask {
    final GameConnectSession session;
    final S2SMessagePacket packet;
    final Login.csLogin request;

    public LoginTask(HandlerContext<GameConnectSession, S2SMessagePacket> context, Login.csLogin request) {
        this.session = context.session();
        this.packet = (S2SMessagePacket) context.packet();
        this.request = request;
    }
}
