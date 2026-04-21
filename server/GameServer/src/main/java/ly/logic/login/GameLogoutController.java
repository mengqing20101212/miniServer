package ly.logic.login;

import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Login;

public class GameLogoutController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Logout, this::logout);
    }

    private void logout(GameHandlerContext context, Login.csLogout request) {

    }


}
