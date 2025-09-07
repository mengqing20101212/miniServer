package ly.logic.login;

import ly.net.GateConnectSession;
import ly.net.IGateController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

public class GateLogoutController implements IGateController {
    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.SC_Logout, GateConnectSession.class, S2SMessagePacket.class, Login.scLogout.class, this::logout);
    }

    private void logout(GateConnectSession session, S2SMessagePacket packet, Login.scLogout request) {
        
    }
}
