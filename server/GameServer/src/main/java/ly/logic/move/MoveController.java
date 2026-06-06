package ly.logic.move;

import ly.logic.player.Player;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Move;

/**
 * 移动消息控制器
 */
public class MoveController implements IGameController {

    // 玩家坐标缓存（简单实现，存内存）
    private static final java.util.concurrent.ConcurrentHashMap<Long, int[]> playerPositions = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Move, this::handleMove);
    }

    /**
     * 处理移动请求
     */
    public void handleMove(GameHandlerContext context, Move.CS_Move request) {
        Player player = context.player();
        long playerId = player.getPlayerId();
        int targetX = request.getTargetX();
        int targetY = request.getTargetY();

        // 获取旧坐标
        int[] oldPos = playerPositions.getOrDefault(playerId, new int[]{0, 0});
        int oldX = oldPos[0];
        int oldY = oldPos[1];

        // 更新坐标
        playerPositions.put(playerId, new int[]{targetX, targetY});

        // 发送响应
        Move.SC_Move.Builder builder = Move.SC_Move.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setCurrentX(targetX);
        builder.setCurrentY(targetY);

        player.sendMsg(Cmd.CMD.SC_Move, builder.build());

        ly.LoggerDef.SystemLogger.info("Player {} moved from ({},{}) to ({},{})",
                playerId, oldX, oldY, targetX, targetY);
    }

    /**
     * 获取玩家坐标
     */
    public static int[] getPlayerPosition(long playerId) {
        return playerPositions.getOrDefault(playerId, new int[]{0, 0});
    }
}
