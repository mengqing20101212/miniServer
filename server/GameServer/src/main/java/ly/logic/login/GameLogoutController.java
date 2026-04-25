package ly.logic.login;

import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Login;

/**
 * 游戏服协议控制器，负责注册并处理对应业务消息。
 */
public class GameLogoutController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Logout, this::logout);
    }

    private void logout(GameHandlerContext context, Login.csLogout request) {

    }


}
