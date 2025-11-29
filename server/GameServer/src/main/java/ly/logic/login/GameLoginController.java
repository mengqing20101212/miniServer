package ly.logic.login;

import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Login;

public class GameLoginController implements IGameController {


    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Login, this::handleLogin);
    }


    public void handleLogin(GameHandlerContext context, Login.csLogin request) {
        LoginTask task = new LoginTask(context, request);
        LoginManager.getInstance().addLoginTask(task);
    }


}
