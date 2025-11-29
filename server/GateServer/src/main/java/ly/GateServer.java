package ly;

import ly.config.ServerTypeEnum;
import ly.net.GateConnectSessionProvider;

/**
 * Hello world!
 */
public class GateServer {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        if (args.length != 3) {
            System.out.println("args error");
            return;
        }
        String nacosUrl = args[0];
        String env = args[1];
        String serverId = args[2];
        ServerContext.startUp(nacosUrl, ServerTypeEnum.GATE.getType(), serverId, env, new GateConnectSessionProvider());

    }
}
