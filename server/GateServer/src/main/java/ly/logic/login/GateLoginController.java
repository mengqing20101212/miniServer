package ly.logic.login;

import ly.net.GateConnectSession;
import ly.net.IGateController;
import ly.net.packet.C2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

public class GateLoginController implements IGateController {
    @Override
    public void registerHandlerRouter() {
        clientHandlerRegister(Cmd.CMD.CS_Login, Login.csLogin.class, this::handleLogin);
    }


    private void handleLogin(GateConnectSession session, C2SMessagePacket packet, Login.csLogin request) {
        Login.scLogin.Builder resp = Login.scLogin.newBuilder();
        System.out.printf("1231");
    }

}
