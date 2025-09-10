package ly.logic.login;

import ly.logic.player.Player;
import ly.net.IGameController;
import ly.net.packet.S2SMessagePacket;
import ly.proto.Cmd;
import ly.proto.Login;

public class GameLogoutController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Logout, Login.csLogout.class, this::logout);
    }

    private void logout(Player player, S2SMessagePacket packet, Login.csLogout request) {

    }


}
