package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.IGameController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

public class GameLoginController implements IGameController {


    @Override
    public void registerHandlerRouter() {
        clientHandlerRegister(Cmd.CMD.CS_Login, Login.csLogin.class, this::handleLogin);
    }


    public void handleLogin(GameConnectSession session, S2SMessagePacket packet, Login.csLogin request) {
        LoginTask task = new LoginTask(session, packet, request);
        LoginManager.getInstance().addLoginTask(task);
    }


}
