package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.HandlerContext;
import ly.net.IGameController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

public class GamePlayerLoginController implements IGameController {


    @Override
    public void registerHandlerRouter() {
//        gameHandlerRegister(Cmd.CMD.CS_Login, this::handleLogin);
        clientHandlerRegister(Cmd.CMD.CS_Login, Login.csLogin.class, this::handleLogin);
    }


    public void handleLogin(HandlerContext<GameConnectSession, S2SMessagePacket> context, Login.csLogin request) {
        LoginTask task = new LoginTask(context, request);
        LoginManager.getInstance().addLoginTask(task);
    }


}
