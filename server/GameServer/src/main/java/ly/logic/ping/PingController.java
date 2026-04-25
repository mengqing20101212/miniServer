package ly.logic.ping;

import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.Server;

/**
 * 游戏服协议控制器，负责注册并处理对应业务消息。
 */
public class PingController implements IGameController {
    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_RpcPing, this::handlePing);
    }

    public void handlePing(GameHandlerContext context, Server.csRpcPing request) {
    }
}
