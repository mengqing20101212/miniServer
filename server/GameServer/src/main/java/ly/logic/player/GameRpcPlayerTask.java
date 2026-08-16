package ly.logic.player;

import ly.net.GameConnectSession;
import ly.net.packet.MessagePacket;

/**
 * Gate 转发到 Game 的非登录业务任务。
 *
 * <p>该任务只保存一次请求的必要上下文。离线玩家 lazy load 可能访问 DB，
 * 由 {@link GameRpcPlayerLoadManager} 在独立协程中处理，避免阻塞 RPC 入站线程。</p>
 */
public class GameRpcPlayerTask {
    final GameConnectSession session;
    final MessagePacket packet;
    final long callId;

    public GameRpcPlayerTask(GameConnectSession session, MessagePacket packet, long callId) {
        this.session = session;
        this.packet = packet;
        this.callId = callId;
    }
}
