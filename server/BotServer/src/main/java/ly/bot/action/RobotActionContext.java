package ly.bot.action;

import ly.bot.data.RobotSessionDataStore;
import ly.bot.session.RobotSession;
import ly.net.NetClient;

/**
 * Action 执行上下文。
 *
 * <p>集中提供 session、网络连接和机器人共享数据，避免 Action 继续扩散零散参数。</p>
 */
public class RobotActionContext {
    private final NetClient client;
    private final RobotSession session;

    public RobotActionContext(NetClient client, RobotSession session) {
        this.client = client;
        this.session = session;
    }

    public NetClient getClient() {
        return client;
    }

    public RobotSession getSession() {
        return session;
    }

    public RobotSessionDataStore getDataStore() {
        return session.getDataStore();
    }
}
