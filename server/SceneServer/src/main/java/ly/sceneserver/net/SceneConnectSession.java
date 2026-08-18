package ly.sceneserver.net;

import ly.net.ConnectSession;
import ly.net.HandlerRouterManager;
import ly.net.packet.MessagePacket;

/** SceneServer 的服务器间连接会话，复用 core 的收包队列和通用路由。 */
public final class SceneConnectSession extends ConnectSession {
    public SceneConnectSession(long guid) {
        super(guid);
    }

    /** 由 SceneServer 业务分发线程调用，不在 Netty IO 线程直接执行业务。 */
    @Override
    public void tick() {
        for (MessagePacket packet : getReceivePacketList()) {
            HandlerRouterManager.execute(this, packet);
        }
    }
}
