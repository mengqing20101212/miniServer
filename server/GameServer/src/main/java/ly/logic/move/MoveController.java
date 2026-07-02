package ly.logic.move;

import ly.logic.player.Player;
import ly.logic.player.PlayerManager;
import ly.logic.player.coroutine.CoroutineUtils;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Move;

/**
 * 移动消息控制器
 */
public class MoveController implements IGameController {

    private static final java.util.concurrent.ConcurrentHashMap<Long, int[]> playerPositions = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_Move, this::handleMove);
    }

    public void handleMove(GameHandlerContext context, Move.csMove request) {
        Player player = context.player();
        long playerId = player.getPlayerId();
        int targetX = request.getTargetX();
        int targetY = request.getTargetY();

        int[] oldPos = playerPositions.getOrDefault(playerId, new int[]{0, 0});
        playerPositions.put(playerId, new int[]{targetX, targetY});

        Move.scMove.Builder builder = Move.scMove.newBuilder();
        builder.setResult(ErrorMsg.ErrorCode.Ok);
        builder.setCurrentX(targetX);
        builder.setCurrentY(targetY);
        if (request.getObservePlayerId() > 0) {
            Player observedPlayer = PlayerManager.getInstance().getOnlinePlayer(request.getObservePlayerId());
            if (observedPlayer == null) {
                builder.setResult(ErrorMsg.ErrorCode.PLAYER_NOT_EXIST);
            } else {
                // 在 PlayerA 的队列里同步读取 PlayerB，实际 getLevel 会投递到 PlayerB 自己的队列执行。
                int observedLevel = CoroutineUtils.timeout(3_000).on(observedPlayer).getLevel();
                builder.setObservedPlayerId(observedPlayer.getPlayerId());
                builder.setObservedLevel(observedLevel);
            }
        }

        player.sendMsg(Cmd.CMD.SC_Move, builder.build());

        ly.LoggerDef.SystemLogger.info("Player {} moved from ({},{}) to ({},{})",
                playerId, oldPos[0], oldPos[1], targetX, targetY);
    }

    public static int[] getPlayerPosition(long playerId) {
        return playerPositions.getOrDefault(playerId, new int[]{0, 0});
    }
}
