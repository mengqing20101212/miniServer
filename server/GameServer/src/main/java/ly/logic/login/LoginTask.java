package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.GameHandlerContext;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Login;

public class LoginTask {
    final GameConnectSession session;
    final S2SMessagePacket packet;
    final Login.csLogin request;

    public LoginTask(GameHandlerContext context, Login.csLogin request) {
        this.session = context.getGameConnectSession();
        this.packet = (S2SMessagePacket) context.packet();
        this.request = request;
    }
}
