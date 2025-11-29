package ly.logic.ping;

import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Server;

public class PingController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_RpcPing, this::handlePing);
    }

    public void handlePing(GameHandlerContext context, Server.csRpcPing request) {
    }
}
