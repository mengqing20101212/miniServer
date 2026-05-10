package ly;

import ly.config.ServerTypeEnum;
import ly.logic.login.GateLoginController;
import ly.logic.login.GateLogoutController;
import ly.net.GateClient;
import ly.net.GateConnectSessionProvider;
import ly.ProtoMessageFactory;
import ly.proto.Cmd;
import ly.proto.Server;
import ly.rpc.RpcService;
import ly.startup.StartupSkillLoader;

/**
 * 网关服启动入口，读取启动参数并注册网关侧协议控制器。
 */
public class GateServer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveServerArgs(ServerTypeEnum.GATE, args);
        ServerContext.addController(new GateLoginController(), new GateLogoutController());
        RpcService.getInstance()
                .setReliableReplayResponseHandler(
                        (message, response) -> {
                            if (response.getCmd() != Cmd.CMD.SC_Gate2GameRpcGameCall_VALUE) {
                                return false;
                            }
                            Server.scGate2GameRpcGameCall resp =
                                    (Server.scGate2GameRpcGameCall)
                                            ProtoMessageFactory.createProtoMessage(response.getCmd(), response.getData());
                            if (resp == null) {
                                return false;
                            }
                            // 重放的是客户端业务包，回包里的 sid 仍然是客户端在 Gate 上的 sid。
                            GateClient client = GateClientManager.getInstance().getClientBySid(resp.getSid());
                            if (client == null) {
                                return false;
                            }
                            client.sendGameResponseToClient(resp);
                            return true;
                        });
        ServerContext.startUp(resolved.nacosUrl, ServerTypeEnum.GATE.getType(), resolved.serverId, resolved.env, new GateConnectSessionProvider());

    }
}
