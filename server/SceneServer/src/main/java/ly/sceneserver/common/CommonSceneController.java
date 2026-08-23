package ly.sceneserver.common;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import ly.LoggerDef;
import ly.net.ConnectSession;
import ly.net.HandlerContext;
import ly.net.IController;
import ly.net.Server2ServerRpcContext;
import ly.net.packet.MessagePacket;
import ly.proto.Cmd;
import ly.proto.ErrorMsg;
import ly.proto.Scene;
import ly.sceneserver.bootstrap.SceneBootstrap;
import ly.sceneserver.common.march.SceneMarchProtoMapper;
import ly.sceneserver.cross.CrossSceneService;
import ly.sceneserver.local.LocalSceneService;

/**
 * 本服和跨服共用的 SceneServer RPC Controller。
 *
 * <p>同一个 CMD 只在这里注册一次，再根据请求中的 scope 路由到 LocalSceneService 或
 * CrossSceneService，避免 HandlerRouterManager 的重复 CMD 注册问题。
 */
public final class CommonSceneController implements IController {
    private static final long OP_TIMEOUT_MILLIS = 2_000L;
    private final SceneModeService localService = new LocalSceneService();
    private final SceneModeService crossService = new CrossSceneService();

    @Override
    public void registerHandlerRouter() {
        register(Cmd.CMD.CS_SceneQuery, ConnectSession.class, MessagePacket.class,
                Scene.csSceneQuery.class, this::handleQuery);
        register(Cmd.CMD.CS_SceneEnter, ConnectSession.class, MessagePacket.class,
                Scene.csSceneEnter.class, this::handleEnter);
        register(Cmd.CMD.CS_SceneMove, ConnectSession.class, MessagePacket.class,
                Scene.csSceneMove.class, this::handleMove);
        register(Cmd.CMD.CS_SceneMetrics, ConnectSession.class, MessagePacket.class,
                Scene.csSceneMetrics.class, this::handleMetrics);
        register(Cmd.CMD.CS_SceneLeave, ConnectSession.class, MessagePacket.class,
                Scene.csSceneLeave.class, this::handleLeave);
        register(Cmd.CMD.CS_SceneView, ConnectSession.class, MessagePacket.class,
                Scene.csSceneView.class, this::handleView);
        register(Cmd.CMD.CS_ScenePathFind, ConnectSession.class, MessagePacket.class,
                Scene.csScenePathFind.class, this::handlePathFind);
    }

    private void handleQuery(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneQuery request) {
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            if (!request.hasPoint() || request.getRadius() < 0) {
                sendQuery(context.session(), ErrorMsg.ErrorCode.PARAM_ERROR, request, null);
                return;
            }
            Scene.ScenePoint point = request.getPoint();
            if (!inBounds(scene, point)) {
                sendQuery(context.session(), ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS, request, null);
                return;
            }
            List<SceneObjectSnapshot> objects = scene.route(point.getX(), point.getY())
                    .query(shard -> shard.objectSnapshotsAt(point.getX(), point.getY()))
                    .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            sendQuery(context.session(), ErrorMsg.ErrorCode.Ok, request, new QueryData(scene, objects));
        } catch (Exception error) {
            LoggerDef.NetLogger.warn("scene query failed, sceneId={}", request.getSceneId(), error);
            sendQuery(context.session(), sceneResolveError(error), request, null);
        }
    }

    private void handleEnter(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneEnter request) {
        Scene.scSceneEnter.Builder response = Scene.scSceneEnter.newBuilder()
                .setSceneId(request.getSceneId())
                .setRequestId(request.getRequestId());
        if (request.hasPoint()) {
            response.setCurrentPoint(request.getPoint());
        }
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            if (request.getPlayerId() <= 0 || !request.hasPoint()) {
                response.setResult(ErrorMsg.ErrorCode.PARAM_ERROR);
            } else if (!inBounds(scene, request.getPoint())) {
                response.setResult(ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS);
            } else {
                Scene.ScenePoint point = request.getPoint();
                var target = scene.route(point.getX(), point.getY());
                var owner = scene.locateObject(request.getPlayerId()).get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                if (owner != null && owner != target) {
                    // 第一阶段先禁止跨 SceneShard 原子迁移，避免绕过场景线程边界。
                    response.setResult(ErrorMsg.ErrorCode.SCENE_UNSUPPORTED)
                            .setShardIndex(owner.shardIndex());
                    ScenePoint currentPoint = loadObjectPoint(owner, request.getPlayerId());
                    if (currentPoint != null) {
                        response.setCurrentPoint(toProtoPoint(currentPoint));
                    }
                } else {
                    target.submit(shard -> {
                        SceneObject object = shard.object(request.getPlayerId());
                        if (object == null) {
                            shard.addObject(new SceneObject(
                                    request.getPlayerId(),
                                    SceneObjectType.PLAYER,
                                    request.getPlayerId(),
                                    point.getX(),
                                    point.getY(),
                                    new ScenePlayerState(request.getPlayerId(), 1, 100)));
                        } else if (object.x() != point.getX() || object.y() != point.getY()) {
                            shard.moveObject(object.objectId(), point.getX(), point.getY());
                        }
                    }).get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                    // 玩家刚进入时默认观察所在块及周边一圈，保证战争迷雾立即拥有初始状态。
                    scene.updateViewAsync(new SceneViewRequest(
                                    request.getPlayerId(),
                                    new ScenePoint(point.getX(), point.getY()),
                                    1,
                                    SceneViewLevel.DETAIL,
                                    0L))
                            .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                    response.setResult(ErrorMsg.ErrorCode.Ok)
                            .setShardIndex(target.shardIndex())
                            .setCurrentPoint(point);
                }
            }
        } catch (Exception error) {
            LoggerDef.NetLogger.warn("scene enter failed, playerId={}", request.getPlayerId(), error);
            response.setResult(sceneResolveError(error));
        }
        SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneEnter, response.build());
    }

    private void handleMove(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneMove request) {
        Scene.scSceneMove.Builder response = Scene.scSceneMove.newBuilder()
                .setSceneId(request.getSceneId())
                .setRequestId(request.getRequestId());
        if (request.hasTargetPoint()) {
            response.setCurrentPoint(request.getTargetPoint());
        }
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            if (request.getPlayerId() <= 0 || !request.hasTargetPoint()) {
                response.setResult(ErrorMsg.ErrorCode.PARAM_ERROR);
                SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneMove, response.build());
                return;
            }
            Scene.ScenePoint targetPoint = request.getTargetPoint();
            var owner = scene.locateObject(request.getPlayerId()).get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            var target = scene.route(targetPoint.getX(), targetPoint.getY());
            if (owner == null) {
                response.setResult(ErrorMsg.ErrorCode.SCENE_NOT_FOUND);
            } else if (owner != target) {
                response.setResult(ErrorMsg.ErrorCode.SCENE_UNSUPPORTED)
                        .setShardIndex(owner.shardIndex());
                ScenePoint currentPoint = loadObjectPoint(owner, request.getPlayerId());
                if (currentPoint != null) {
                    response.setCurrentPoint(toProtoPoint(currentPoint));
                }
            } else {
                owner.submit(shard -> shard.moveObject(
                                request.getPlayerId(), targetPoint.getX(), targetPoint.getY()))
                        .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                response.setResult(ErrorMsg.ErrorCode.Ok)
                        .setShardIndex(owner.shardIndex())
                        .setCurrentPoint(targetPoint);
            }
        } catch (IndexOutOfBoundsException error) {
            response.setResult(ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS);
        } catch (Exception error) {
            LoggerDef.NetLogger.warn("scene move failed, playerId={}", request.getPlayerId(), error);
            response.setResult(sceneResolveError(error));
        }
        SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneMove, response.build());
    }

    private void handleMetrics(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneMetrics request) {
        Scene.scSceneMetrics.Builder response = Scene.scSceneMetrics.newBuilder()
                .setSceneId(request.getSceneId())
                .setOnlineTarget(Integer.getInteger("slg.scene.fake-online", 10_000));
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            response.setResult(ErrorMsg.ErrorCode.Ok)
                    .setWidth(scene.config().width())
                    .setHeight(scene.config().height())
                    .setShardCount(scene.shardCount())
                    .setTickNumber(scene.maxTickNumber())
                    .setObjectCount(scene.totalObjectCountAsync().get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS));
            long lastTick = 0;
            for (int i = 0; i < scene.shardCount(); i++) {
                lastTick = Math.max(lastTick, scene.shard(i).lastTickMillis());
            }
            response.setLastTickMillis(lastTick);
        } catch (Exception error) {
            response.setResult(sceneResolveError(error));
        }
        SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneMetrics, response.build());
    }

    private void handleLeave(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneLeave request) {
        Scene.scSceneLeave.Builder response = Scene.scSceneLeave.newBuilder()
                .setSceneId(request.getSceneId())
                .setPlayerId(request.getPlayerId())
                .setRequestId(request.getRequestId());
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            var owner = scene.locateObject(request.getPlayerId()).get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            if (owner == null) {
                response.setResult(ErrorMsg.ErrorCode.SCENE_NOT_FOUND);
            } else {
                owner.submit(shard -> shard.removeObject(request.getPlayerId()))
                        .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                scene.removeViewerAsync(request.getPlayerId())
                        .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                response.setResult(ErrorMsg.ErrorCode.Ok);
            }
        } catch (Exception error) {
            response.setResult(sceneResolveError(error));
        }
        SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneLeave, response.build());
    }

    /** 玩家拖动地图或切换缩放层时，重新注册 AOI 块并返回对应层级快照。 */
    private void handleView(HandlerContext<ConnectSession, MessagePacket> context, Scene.csSceneView request) {
        Scene.scSceneView.Builder response = Scene.scSceneView.newBuilder()
                .setSceneId(request.getSceneId())
                .setPlayerId(request.getPlayerId())
                .setRequestId(request.getRequestId())
                .setViewLevel(request.getViewLevel());
        try {
            SceneRuntime.SceneInstance scene = resolve(request.getSceneId(), request.getScope());
            response.setSceneId(scene.config().sceneId());
            if (request.getPlayerId() <= 0
                    || !request.hasCenterPoint()
                    || request.getRadiusBlocks() < 0
                    || request.getViewLevel() == Scene.SceneViewLevel.UNRECOGNIZED) {
                response.setResult(ErrorMsg.ErrorCode.PARAM_ERROR);
            } else if (!inBounds(scene, request.getCenterPoint())) {
                response.setResult(ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS);
            } else {
                SceneViewSnapshot snapshot = scene.updateViewAsync(new SceneViewRequest(
                                request.getPlayerId(),
                                new ScenePoint(
                                        request.getCenterPoint().getX(),
                                        request.getCenterPoint().getY()),
                                request.getRadiusBlocks(),
                                fromProtoViewLevel(request.getViewLevel()),
                                request.getRequestedTagMask()))
                        .get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
                response.setResult(ErrorMsg.ErrorCode.Ok)
                        .setCenterBlock(toProtoPoint(snapshot.centerBlock()))
                        .setTickNumber(snapshot.tickNumber());
                for (SceneObjectSnapshot object : snapshot.objects()) {
                    response.addObjects(toProtoSnapshot(object));
                }
                for (SceneBlockSnapshot block : snapshot.blocks()) {
                    response.addBlocks(Scene.SceneBlockSnapshot.newBuilder()
                            .setBlockIndex(block.blockIndex())
                            .setBlockPoint(toProtoPoint(block.blockX(), block.blockY()))
                            .setVisible(block.visible())
                            .setDiscovered(block.discovered())
                            .setObjectCount(block.objectCount())
                            .setDataTagMask(block.dataTagMask())
                            .build());
                }
                response.addAllDiscoveredBlockIndices(snapshot.discoveredBlockIndices());
            }
        } catch (IndexOutOfBoundsException error) {
            response.setResult(ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS);
        } catch (IllegalArgumentException error) {
            response.setResult(ErrorMsg.ErrorCode.PARAM_ERROR);
        } catch (Exception error) {
            LoggerDef.NetLogger.warn("scene view update failed, playerId={}", request.getPlayerId(), error);
            response.setResult(sceneResolveError(error));
        }
        SceneRpcSupport.sendResponse(context.session(), Cmd.CMD.SC_SceneView, response.build());
    }

    /**
     * 非阻塞寻路入口：A* 在独立 CPU 线程池执行，完成后由 SceneRuntime 回投 Tick。
     * 捕获 callId 是因为原 RPC Handler 返回后 ThreadLocal 上下文会被清理。
     */
    private void handlePathFind(
            HandlerContext<ConnectSession, MessagePacket> context,
            Scene.csScenePathFind request) {
        long callId = Server2ServerRpcContext.currentCallId();
        SceneRuntime.SceneInstance scene;
        try {
            scene = resolve(request.getSceneId(), request.getScope());
        } catch (Exception error) {
            sendPathError(context.session(), callId, request, request.getSceneId(),
                    sceneResolveError(error));
            return;
        }
        try {
            if (request.getPlayerId() <= 0
                    || !request.hasStartPoint()
                    || !request.hasTargetPoint()
                    || request.getFogPolicy() == Scene.SceneFogPolicy.UNRECOGNIZED
                    || request.getMaxVisitedNodes() < 0) {
                sendPathResponse(context.session(), callId, request, scene.config().sceneId(),
                        ScenePathResult.failure(ScenePathStatus.INVALID_ARGUMENT, 0));
                return;
            }
            ScenePathRequest pathRequest = new ScenePathRequest(
                    request.getPlayerId(),
                    new ScenePoint(request.getStartPoint().getX(), request.getStartPoint().getY()),
                    new ScenePoint(request.getTargetPoint().getX(), request.getTargetPoint().getY()),
                    fromProtoFogPolicy(request.getFogPolicy()),
                    request.getMaxVisitedNodes());
            // 先在 RPC stripe 上无阻塞取得全流程容量名额；达到上限时 findPathAsync 立即失败，
            // 不会先创建并堆积等待迷雾快照的寻路虚拟线程。
            var pathFuture = scene.findPathAsync(pathRequest);
            // 寻路可能等待个人迷雾快照、CPU 工作区和下一个 SceneShard Tick。单独使用虚拟线程
            // 顺序等待后发送响应，既不阻塞同条 RPC stripe，也不再用 whenComplete 拆散业务流程。
            Thread.ofVirtual()
                    .name("ScenePath-Response-" + request.getPlayerId())
                    .start(() -> {
                try {
                    ScenePathResult pathResult = pathFuture.get();
                    sendPathResponse(context.session(), callId, request, scene.config().sceneId(), pathResult);
                } catch (InterruptedException error) {
                    Thread.currentThread().interrupt();
                    LoggerDef.NetLogger.warn(
                            "scene path response interrupted, playerId={}", request.getPlayerId(), error);
                    sendPathError(context.session(), callId, request, scene.config().sceneId(),
                            ErrorMsg.ErrorCode.SYSTEM_ERROR);
                } catch (ExecutionException error) {
                    Throwable cause = error.getCause() == null ? error : error.getCause();
                    LoggerDef.NetLogger.warn(
                            "scene path find failed, playerId={}", request.getPlayerId(), cause);
                    sendPathError(context.session(), callId, request, scene.config().sceneId(),
                            ErrorMsg.ErrorCode.SYSTEM_ERROR);
                }
            });
        } catch (IllegalArgumentException error) {
            sendPathResponse(context.session(), callId, request, request.getSceneId(),
                    ScenePathResult.failure(ScenePathStatus.INVALID_ARGUMENT, 0));
        } catch (Exception error) {
            LoggerDef.NetLogger.warn("scene path request failed, playerId={}", request.getPlayerId(), error);
            sendPathError(context.session(), callId, request, scene.config().sceneId(),
                    ErrorMsg.ErrorCode.SYSTEM_ERROR);
        }
    }

    private void sendPathError(
            ConnectSession session,
            long callId,
            Scene.csScenePathFind request,
            String sceneId,
            ErrorMsg.ErrorCode errorCode) {
        Server2ServerRpcContext.run(callId, () -> SceneRpcSupport.sendResponse(
                session,
                Cmd.CMD.SC_ScenePathFind,
                Scene.scScenePathFind.newBuilder()
                        .setResult(errorCode)
                        .setSceneId(sceneId)
                        .setRequestId(request.getRequestId())
                        .build()));
    }

    private void sendPathResponse(
            ConnectSession session,
            long callId,
            Scene.csScenePathFind request,
            String sceneId,
            ScenePathResult pathResult) {
        Server2ServerRpcContext.run(callId, () -> {
            Scene.scScenePathFind.Builder response = Scene.scScenePathFind.newBuilder()
                    .setResult(toErrorCode(pathResult.status()))
                    .setSceneId(sceneId)
                    .setRequestId(request.getRequestId())
                    .setTotalCost(pathResult.totalCost())
                    .setVisitedNodes(pathResult.visitedNodes())
                    .setCompletedTick(pathResult.completedTick());
            for (ScenePoint point : pathResult.points()) {
                response.addPoints(toProtoPoint(point));
            }
            SceneRpcSupport.sendResponse(session, Cmd.CMD.SC_ScenePathFind, response.build());
        });
    }

    private void sendQuery(
            ConnectSession session,
            ErrorMsg.ErrorCode result,
            Scene.csSceneQuery request,
            QueryData data) {
        Scene.scSceneQuery.Builder response = Scene.scSceneQuery.newBuilder()
                .setResult(result)
                .setSceneId(request.getSceneId());
        if (data != null) {
            Scene.ScenePoint point = request.getPoint();
            SceneStaticMap map = data.scene().staticMap();
            response.setTerrain(map.terrain(point.getX(), point.getY()))
                    .setConfigId(map.configId(point.getX(), point.getY()))
                    .setFlags(map.flags(point.getX(), point.getY()))
                    .setSpawnRuleId(map.spawnRuleId(point.getX(), point.getY()));
            for (SceneObjectSnapshot object : data.objects()) {
                response.addObjects(toProtoSnapshot(object));
            }
        }
        SceneRpcSupport.sendResponse(session, Cmd.CMD.SC_SceneQuery, response.build());
    }

    private SceneRuntime.SceneInstance resolve(String sceneId, Scene.SceneScope scope) {
        SceneRuntime runtime = SceneBootstrap.getRuntime();
        if (runtime == null) {
            throw new SceneNotReadyException();
        }
        return new SceneServiceRegistry(runtime, localService, crossService).resolve(sceneId, scope);
    }

    private boolean inBounds(SceneRuntime.SceneInstance scene, Scene.ScenePoint point) {
        return point.getX() >= 0
                && point.getX() < scene.config().width()
                && point.getY() >= 0
                && point.getY() < scene.config().height();
    }

    /** 在对象所属 SceneShard 队列中读取坐标，避免 RPC 线程直接读取可变 SceneObject。 */
    private ScenePoint loadObjectPoint(SceneShard shard, long objectId) throws Exception {
        AtomicReference<ScenePoint> result = new AtomicReference<>();
        shard.submit(current -> {
            SceneObject object = current.object(objectId);
            if (object != null) {
                result.set(new ScenePoint(object.x(), object.y()));
            }
        }).get(OP_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        return result.get();
    }

    private Scene.ScenePoint toProtoPoint(ScenePoint point) {
        return toProtoPoint(point.x(), point.y());
    }

    private static ErrorMsg.ErrorCode sceneResolveError(Exception error) {
        return error instanceof SceneNotReadyException
                ? ErrorMsg.ErrorCode.SCENE_NOT_READY
                : ErrorMsg.ErrorCode.SCENE_NOT_FOUND;
    }

    private static final class SceneNotReadyException extends IllegalStateException {
        private SceneNotReadyException() {
            super("SceneRuntime is restoring data");
        }
    }

    private Scene.ScenePoint toProtoPoint(int x, int y) {
        return Scene.ScenePoint.newBuilder().setX(x).setY(y).build();
    }

    private Scene.SceneObjectKind toProtoKind(SceneObjectType type) {
        return switch (type) {
            case PLAYER -> Scene.SceneObjectKind.SCENE_OBJECT_PLAYER;
            case RESOURCE -> Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE;
            case MONSTER -> Scene.SceneObjectKind.SCENE_OBJECT_MONSTER;
            case FARM -> Scene.SceneObjectKind.SCENE_OBJECT_FARM;
            case DROP -> Scene.SceneObjectKind.SCENE_OBJECT_DROP;
            case BUILDING -> Scene.SceneObjectKind.SCENE_OBJECT_BUILDING;
            case DECORATION -> Scene.SceneObjectKind.SCENE_OBJECT_DECORATION;
            case MARCH -> Scene.SceneObjectKind.SCENE_OBJECT_MARCH;
            case RALLY -> Scene.SceneObjectKind.SCENE_OBJECT_RALLY;
        };
    }

    private Scene.SceneObjectSnapshot toProtoSnapshot(SceneObjectSnapshot object) {
        Scene.SceneObjectSnapshot.Builder builder = Scene.SceneObjectSnapshot.newBuilder()
                .setObjectId(object.objectId())
                .setKind(toProtoKind(object.type()))
                .setOwnerId(object.ownerId())
                .setPoint(toProtoPoint(object.point()))
                .setStateVersion(object.stateVersion())
                .setDataTagMask(object.dataTagMask());
        if (object.march() != null) {
            builder.setMarch(SceneMarchProtoMapper.toProto(object.march()));
        }
        if (object.rally() != null) {
            builder.setRally(SceneMarchProtoMapper.toProto(object.rally()));
        }
        return builder.build();
    }

    private SceneViewLevel fromProtoViewLevel(Scene.SceneViewLevel level) {
        return switch (level) {
            case SCENE_VIEW_DETAIL -> SceneViewLevel.DETAIL;
            case SCENE_VIEW_REGION -> SceneViewLevel.REGION;
            case SCENE_VIEW_WORLD -> SceneViewLevel.WORLD;
            case UNRECOGNIZED -> throw new IllegalArgumentException("unknown scene view level");
        };
    }

    private SceneFogPolicy fromProtoFogPolicy(Scene.SceneFogPolicy policy) {
        return switch (policy) {
            case SCENE_FOG_IGNORE -> SceneFogPolicy.IGNORE;
            case SCENE_FOG_DISCOVERED_ONLY -> SceneFogPolicy.DISCOVERED_ONLY;
            case SCENE_FOG_VISIBLE_ONLY -> SceneFogPolicy.VISIBLE_ONLY;
            case UNRECOGNIZED -> throw new IllegalArgumentException("unknown scene fog policy");
        };
    }

    private ErrorMsg.ErrorCode toErrorCode(ScenePathStatus status) {
        return switch (status) {
            case OK -> ErrorMsg.ErrorCode.Ok;
            case INVALID_ARGUMENT -> ErrorMsg.ErrorCode.PARAM_ERROR;
            case OUT_OF_BOUNDS -> ErrorMsg.ErrorCode.SCENE_OUT_OF_BOUNDS;
            case PATH_NOT_FOUND -> ErrorMsg.ErrorCode.SCENE_PATH_NOT_FOUND;
            case LIMIT_EXCEEDED -> ErrorMsg.ErrorCode.SCENE_PATH_LIMIT_EXCEEDED;
            case FOG_BLOCKED -> ErrorMsg.ErrorCode.SCENE_FOG_BLOCKED;
        };
    }

    private record QueryData(SceneRuntime.SceneInstance scene, List<SceneObjectSnapshot> objects) {
    }
}
