package ly;

import ly.config.ServerTypeEnum;
import ly.logic.login.GamePlayerLoginController;
import ly.net.GameConnectSessionProvider;

/**
 * Hello world!
 */
public class GameServer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length != 3) {
            System.out.println("args error");
            return;
        }
        String nacosUrl = args[0];
        String env = args[1];
        String serverId = args[2];
        ServerContext.addController(new GamePlayerLoginController());
        ServerContext.startUp(nacosUrl, ServerTypeEnum.GAME.getType(), serverId, env, new GameConnectSessionProvider());

    }
}
