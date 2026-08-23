package ly.logic.scene;

import com.google.protobuf.AbstractMessage;

import ly.LoggerDef;
import ly.logic.player.Player;
import ly.net.GameHandlerContext;
import ly.net.IGameController;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;
import ly.rpc.RpcFailSavePolicy;
import ly.rpc.RpcNodeConnector;
import ly.rpc.RpcService;

/**
 * GameServer 的玩家态场景代理。
 *
 * <p>客户端只能连接 GateServer，不能直连 SceneServer。Scene 请求先由 Gate 转发到
 * GameServer 的玩家 FIFO 队列，本 Controller 在玩家线程中把请求里的 playerId 强制
 * 改成当前登录玩家，再复用 core 的通用 TCP RPC 调用 SceneServer。Scene 回包随后仍
 * 通过 {@link Player#sendMsg(Cmd.CMD, AbstractMessage)} 返回 Gate 和原客户端。</p>
 *
 * <p>这里不依赖 SceneServer 工程，只依赖公共 proto/core。进入、移动、离开属于状态命令，
 * 发送失败时进入可靠 Outbox；响应超时不自动重放，因为当前 Scene Handler 还没有真正按
 * requestId 保存幂等执行记录。查询、AOI 和寻路属于可重新发起的请求，不进入 Outbox。</p>
 */
public final class SceneProxyController implements IGameController {
    private static final String DEFAULT_SCENE_SERVER_ID = "scene1001";
    private static final int NORMAL_TIMEOUT_MILLIS = 5_000;
    private static final int PATH_TIMEOUT_MILLIS = 30_000;

    @Override
    public void registerHandlerRouter() {
        gameHandlerRegister(Cmd.CMD.CS_SceneQuery, this::handleQuery);
        gameHandlerRegister(Cmd.CMD.CS_SceneEnter, this::handleEnter);
        gameHandlerRegister(Cmd.CMD.CS_SceneMove, this::handleMove);
        gameHandlerRegister(Cmd.CMD.CS_SceneLeave, this::handleLeave);
        gameHandlerRegister(Cmd.CMD.CS_SceneView, this::handleView);
        gameHandlerRegister(Cmd.CMD.CS_ScenePathFind, this::handlePathFind);
    }

    private void handleQuery(GameHandlerContext context, Scene.csSceneQuery request) {
        Scene.scSceneQuery response = call(
                context.player(),
                Cmd.CMD.CS_SceneQuery,
                request,
                Scene.scSceneQuery.class,
                NORMAL_TIMEOUT_MILLIS,
                RpcFailSavePolicy.NONE);
        if (response == null) {
            response = Scene.scSceneQuery.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(request.getSceneId())
                    .build();
        }
        context.player().sendMsg(Cmd.CMD.SC_SceneQuery, response);
    }

    private void handleEnter(GameHandlerContext context, Scene.csSceneEnter request) {
        Player player = context.player();
        Scene.csSceneEnter authoritative = bindPlayerId(request, player.getPlayerId());
        Scene.scSceneEnter response = call(
                player,
                Cmd.CMD.CS_SceneEnter,
                authoritative,
                Scene.scSceneEnter.class,
                NORMAL_TIMEOUT_MILLIS,
                RpcFailSavePolicy.SEND_FAILED_ONLY);
        if (response == null) {
            response = Scene.scSceneEnter.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(authoritative.getSceneId())
                    .setRequestId(authoritative.getRequestId())
                    .setCurrentPoint(authoritative.getPoint())
                    .build();
        }
        player.sendMsg(Cmd.CMD.SC_SceneEnter, response);
    }

    private void handleMove(GameHandlerContext context, Scene.csSceneMove request) {
        Player player = context.player();
        Scene.csSceneMove authoritative = bindPlayerId(request, player.getPlayerId());
        Scene.scSceneMove response = call(
                player,
                Cmd.CMD.CS_SceneMove,
                authoritative,
                Scene.scSceneMove.class,
                NORMAL_TIMEOUT_MILLIS,
                RpcFailSavePolicy.SEND_FAILED_ONLY);
        if (response == null) {
            response = Scene.scSceneMove.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(authoritative.getSceneId())
                    .setRequestId(authoritative.getRequestId())
                    .setCurrentPoint(authoritative.getTargetPoint())
                    .build();
        }
        player.sendMsg(Cmd.CMD.SC_SceneMove, response);
    }

    private void handleLeave(GameHandlerContext context, Scene.csSceneLeave request) {
        Player player = context.player();
        Scene.csSceneLeave authoritative = bindPlayerId(request, player.getPlayerId());
        Scene.scSceneLeave response = call(
                player,
                Cmd.CMD.CS_SceneLeave,
                authoritative,
                Scene.scSceneLeave.class,
                NORMAL_TIMEOUT_MILLIS,
                RpcFailSavePolicy.SEND_FAILED_ONLY);
        if (response == null) {
            response = Scene.scSceneLeave.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(authoritative.getSceneId())
                    .setPlayerId(player.getPlayerId())
                    .setRequestId(authoritative.getRequestId())
                    .build();
        }
        player.sendMsg(Cmd.CMD.SC_SceneLeave, response);
    }

    private void handleView(GameHandlerContext context, Scene.csSceneView request) {
        Player player = context.player();
        Scene.csSceneView authoritative = bindPlayerId(request, player.getPlayerId());
        Scene.scSceneView response = call(
                player,
                Cmd.CMD.CS_SceneView,
                authoritative,
                Scene.scSceneView.class,
                NORMAL_TIMEOUT_MILLIS,
                RpcFailSavePolicy.NONE);
        if (response == null) {
            response = Scene.scSceneView.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(authoritative.getSceneId())
                    .setPlayerId(player.getPlayerId())
                    .setViewLevel(authoritative.getViewLevel())
                    .setRequestId(authoritative.getRequestId())
                    .build();
        }
        player.sendMsg(Cmd.CMD.SC_SceneView, response);
    }

    private void handlePathFind(GameHandlerContext context, Scene.csScenePathFind request) {
        Player player = context.player();
        Scene.csScenePathFind authoritative = bindPlayerId(request, player.getPlayerId());
        Scene.scScenePathFind response = call(
                player,
                Cmd.CMD.CS_ScenePathFind,
                authoritative,
                Scene.scScenePathFind.class,
                PATH_TIMEOUT_MILLIS,
                RpcFailSavePolicy.NONE);
        if (response == null) {
            response = Scene.scScenePathFind.newBuilder()
                    .setResult(ErrorMsg.ErrorCode.SCENE_NOT_READY)
                    .setSceneId(authoritative.getSceneId())
                    .setRequestId(authoritative.getRequestId())
                    .build();
        }
        player.sendMsg(Cmd.CMD.SC_ScenePathFind, response);
    }

    /**
     * 使用项目已有 RpcService 发送标准 csServer2Server 外壳并校验响应类型。
     * 同步等待发生在当前玩家自己的虚拟线程中，因此不会阻塞 Netty 入站线程，也不会破坏
     * 同一玩家命令顺序；不同玩家仍然可以并发调用 SceneServer。
     */
    private <T extends AbstractMessage> T call(
            Player player,
            Cmd.CMD requestCmd,
            AbstractMessage request,
            Class<T> responseType,
            int timeoutMillis,
            RpcFailSavePolicy failSavePolicy) {
        String sceneServerId = System.getProperty("slg.scene.server-id", DEFAULT_SCENE_SERVER_ID);
        RpcNodeConnector connector = RpcService.getInstance().getRpcNodeConnector(sceneServerId);
        if (connector == null || !connector.isConnect()) {
            LoggerDef.NetLogger.warn(
                    "scene rpc connector unavailable, playerId={}, sceneServerId={}, cmd={}",
                    player.getPlayerId(),
                    sceneServerId,
                    requestCmd);
            return null;
        }
        AbstractMessage response = connector.syncSendProtoMessage(
                player.getPlayerId(),
                requestCmd.getNumber(),
                request,
                timeoutMillis,
                failSavePolicy);
        if (!responseType.isInstance(response)) {
            LoggerDef.NetLogger.warn(
                    "scene rpc response type mismatch, playerId={}, cmd={}, expected={}, actual={}",
                    player.getPlayerId(),
                    requestCmd,
                    responseType.getSimpleName(),
                    response == null ? "null" : response.getClass().getName());
            return null;
        }
        return responseType.cast(response);
    }

    // 以下方法保持 package-private，单元测试会用伪造 playerId 验证所有玩家态协议都被覆盖。
    static Scene.csSceneEnter bindPlayerId(Scene.csSceneEnter request, long playerId) {
        return request.toBuilder().setPlayerId(playerId).build();
    }

    static Scene.csSceneMove bindPlayerId(Scene.csSceneMove request, long playerId) {
        return request.toBuilder().setPlayerId(playerId).build();
    }

    static Scene.csSceneLeave bindPlayerId(Scene.csSceneLeave request, long playerId) {
        return request.toBuilder().setPlayerId(playerId).build();
    }

    static Scene.csSceneView bindPlayerId(Scene.csSceneView request, long playerId) {
        return request.toBuilder().setPlayerId(playerId).build();
    }

    static Scene.csScenePathFind bindPlayerId(Scene.csScenePathFind request, long playerId) {
        return request.toBuilder().setPlayerId(playerId).build();
    }
}
