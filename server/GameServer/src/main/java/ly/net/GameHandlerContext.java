package ly.net;

import ly.logic.player.Player;
import ly.net.packet.MessagePacket;

/**
 * 游戏处理器上下文，封装玩家和S2S数据包
 */
public record GameHandlerContext(
        Player player,
        MessagePacket packet
) {
    /**
     * 获取玩家对象
     */
    @Override
    public Player player() {
        return player;
    }

    /**
     * 获取数据包对象
     */
    @Override
    public MessagePacket packet() {
        return packet;
    }

    /**
     * 获取命令ID
     */
    public int getCmd() {
        return packet.getCmd();
    }

    /**
     * 获取玩家ID
     */
    public long getPlayerId() {
        return player.getPlayerId();
    }

    public GameConnectSession getGameConnectSession() {
        return player.getGamePlayer().getSession();
    }

    /**
     * 获取会话ID
     */
    public long getSessionId() {
        return player.getGamePlayer().getSessionGuid();
    }
}