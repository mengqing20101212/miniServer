package ly.logic.login;

import ly.net.GameConnectSession;
import ly.net.IGameController;
import ly.net.HandlerContext;
import ly.net.packet.AbstractMessagePacket;
import ly.net.packet.MessagePacketFactory;
import ly.proto.Cmd;
import ly.proto.Login;

/**
 * 游戏服协议控制器，负责注册并处理对应业务消息。
 */
public class GameLogoutController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        clientHandlerRegister(Cmd.CMD.CS_Logout, Login.csLogout.class, this::logout);
    }

    private void logout(HandlerContext<GameConnectSession, AbstractMessagePacket> context, Login.csLogout request) {
        Login.scLogout response =
                Login.scLogout.newBuilder()
                        .setAccount(request.getAccount())
                        .setAccountId(request.getAccountId())
                        .setGameServerId(request.getGameServerId())
                        .setLogoutReason(request.getLogoutReason())
                        .build();
        AbstractMessagePacket packet =
                MessagePacketFactory.createAbstractMessagePacket(
                        request.getAccountId(), Cmd.CMD.SC_Logout_VALUE, response, 0, 0);
        context.session().addSendPacket(packet);
    }
}
