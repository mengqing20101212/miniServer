package ly;

import ly.config.ServerTypeEnum;
import ly.logic.login.GamePlayerLoginController;
import ly.logic.login.GameLogoutController;
import ly.logic.ping.PingController;
import ly.logic.player.Gate2GameRpcGameCallController;
import ly.net.GameConnectSessionProvider;
import ly.startup.StartupSkillLoader;

/**
 * 游戏服启动入口，初始化公共上下文并注册游戏逻辑控制器。
 */
public class GameServer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveServerArgs(ServerTypeEnum.GAME, args);
        ServerContext.addController(
                new GamePlayerLoginController(),
                new GameLogoutController(),
                new Gate2GameRpcGameCallController(),
                new PingController());
        ServerContext.startUp(resolved.nacosUrl, ServerTypeEnum.GAME.getType(), resolved.serverId, resolved.env, new GameConnectSessionProvider());

    }
}
