package ly;

import ly.config.ServerTypeEnum;
import ly.logic.login.GateLoginController;
import ly.logic.login.GateLogoutController;
import ly.net.GateConnectSessionProvider;
import ly.startup.StartupSkillLoader;

/**
 * 网关服启动入口，读取启动参数并注册网关侧协议控制器。
 */
public class GateServer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveServerArgs(ServerTypeEnum.GATE, args);
        ServerContext.addController(new GateLoginController(), new GateLogoutController());
        ServerContext.startUp(resolved.nacosUrl, ServerTypeEnum.GATE.getType(), resolved.serverId, resolved.env, new GateConnectSessionProvider());

    }
}
