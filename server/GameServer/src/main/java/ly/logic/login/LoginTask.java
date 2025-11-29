package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Login;

public class LoginTask {
    final GameConnectSession session;
    final S2SMessagePacket packet;
    final Login.csLogin request;

    public LoginTask(GameConnectSession session, S2SMessagePacket packet, Login.csLogin request) {
        this.session = session;
        this.packet = packet;
        this.request = request;
    }
}
