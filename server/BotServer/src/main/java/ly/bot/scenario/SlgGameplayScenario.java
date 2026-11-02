package ly.bot.scenario;

import java.util.*;
import java.util.concurrent.*;
import com.google.protobuf.AbstractMessage;
import ly.ProtoMessageFactory;
import ly.bot.action.*;
import ly.bot.action.impl.SceneRobotAction;
import ly.bot.session.RobotSession;
import ly.net.packet.MessagePacket;
import ly.proto.*;

public final class SlgGameplayScenario implements RobotScenario {
    private final CompletableFuture<Void> completion = new CompletableFuture<>();
    private final RobotSession observer;
    private final BlockingQueue<Scene.scSceneView> pushes = new LinkedBlockingQueue<>();
    private final String scene = System.getProperty("slg.bot.scene-id", "world-1");
    private RobotSession session;

    public SlgGameplayScenario(RobotSession observer) { this.observer = observer; }
    public CompletableFuture<Void> completion() { return completion; }

    @Override public void onLogin(RobotSession session) {
        this.session = session;
        observer.registerPushAction(new RobotAction() {
            @Override public RobotActionResult execute(RobotActionContext context) { return RobotActionResult.success(); }
            @Override public int requestCmd() { return 0; }
            @Override public int responseCmd() { return Cmd.CMD.SC_SceneChanged_VALUE; }
            @Override public String getName() { return "SLG-AOI-observer"; }
            @Override public void onResponse(MessagePacket packet, RobotActionContext context) {
                Scene.scSceneChanged changed = (Scene.scSceneChanged) ProtoMessageFactory.createProtoMessage(
                        packet.getCmd(), packet.getData());
                pushes.offer(changed.getView());
            }
        });
        Thread.ofVirtual().name("SLG-Gameplay-Robot").start(this::run);
    }

    private void run() {
        try {
            enter(session, 1, 1);
            enter(observer, 2, 2);
            Map<Scene.SceneObjectKind, Scene.SceneObjectSnapshot> targets = findTargets();
            long observerBalance = balance(observer, 1);
            for (Scene.SceneObjectKind kind : List.of(Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE,
                    Scene.SceneObjectKind.SCENE_OBJECT_MONSTER)) {
                runMarch(kind, targets.get(kind));
            }
            require(balance(observer, 1) == observerBalance, "观察者收到其他玩家的奖励");
            for (RobotSession player : List.of(session, observer))
                invoke(player, Cmd.CMD.CS_SceneLeave, Cmd.CMD.SC_SceneLeave,
                        Scene.csSceneLeave.newBuilder().setSceneId(scene).build(), Scene.scSceneLeave.class);
            System.out.println("[SLG-GAMEPLAY] PASS gather, monster, return-award, duplicate-claim, cross-region-AOI");
            completion.complete(null);
        } catch (Throwable failure) { completion.completeExceptionally(failure); }
    }

    private void runMarch(Scene.SceneObjectKind kind, Scene.SceneObjectSnapshot target) throws Exception {
        long before = balance(session, 1);
        int midX = (target.getPoint().getX() + 1) / 2;
        int midY = (target.getPoint().getY() + 1) / 2;
        Scene.scSceneView observing = view(observer, midX, midY);
        require(outside(observing, point(1, 1)) && outside(observing, target.getPoint()),
                "观察视野应只经过路线，两端均在九宫格外");
        pushes.clear();
        Scene.scSceneMarchStart start = invoke(session, Cmd.CMD.CS_SceneMarchStart, Cmd.CMD.SC_SceneMarchStart,
                Scene.csSceneMarchStart.newBuilder().setSceneId(scene).setPlayerId(session.getPlayerId() + 9_000_000_000L)
                        .setTargetId(target.getObjectId()).setTargetPoint(target.getPoint())
                        .setType(kind == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE
                                ? Scene.SceneMarchType.SCENE_MARCH_GATHER : Scene.SceneMarchType.SCENE_MARCH_ATTACK)
                        .setTroopCount(999999).setPower(999999).build(), Scene.scSceneMarchStart.class);
        require(start.getResult() == ErrorMsg.ErrorCode.Ok, "发车失败: " + start.getResult());
        Scene.SceneMarchSnapshot initial = start.getMarch();
        require(initial.getOwnerPlayerId() == session.getPlayerId(), "Game未绑定真实玩家");
        require(initial.getTroopCount() == 100 && initial.getPower() == 100, "Game未覆盖客户端兵力战力");
        require(initial.getTargetRegionId() == 25, "用例目标应位于公司地图中央区域25");
        awaitPush(initial.getMarchId(), true);
        Scene.csSceneMarchQuery query = Scene.csSceneMarchQuery.newBuilder().setSceneId(scene)
                .setMarchId(initial.getMarchId()).setTargetRegionId(initial.getTargetRegionId()).build();
        Set<Scene.SceneMarchStatus> statuses = EnumSet.noneOf(Scene.SceneMarchStatus.class);
        long deadline = System.nanoTime() + TimeUnit.MINUTES.toNanos(2);
        Scene.SceneMarchSnapshot finished = null;
        while (System.nanoTime() < deadline) {
            Scene.scSceneMarchQuery response = invoke(session, Cmd.CMD.CS_SceneMarchQuery, Cmd.CMD.SC_SceneMarchQuery,
                    query, Scene.scSceneMarchQuery.class);
            require(response.getResult() == ErrorMsg.ErrorCode.Ok, "查询行军失败: " + response.getResult());
            Scene.SceneMarchSnapshot march = response.getMarch();
            require(march.getTargetRegionId() == initial.getTargetRegionId(), "行军中途改变托管区域");
            if (statuses.add(march.getStatus()))
                System.out.printf("[SLG-GAMEPLAY] march=%d status=%s hostRegion=%d%n",
                        march.getMarchId(), march.getStatus(), march.getTargetRegionId());
            if (march.getStatus() == Scene.SceneMarchStatus.SCENE_MARCH_RETURNING)
                require(balance(session, 1) == before, "返程未完成就发奖");
            if (march.getStatus() == Scene.SceneMarchStatus.SCENE_MARCH_FINISHED) { finished = march; break; }
            Thread.sleep(200);
        }
        require(finished != null && finished.getVictory() && finished.getRewardAmount() == 100, "返程或奖励结果错误");
        require(statuses.contains(Scene.SceneMarchStatus.SCENE_MARCH_RETURNING), "未观测到返程阶段");
        if (kind == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE)
            require(statuses.contains(Scene.SceneMarchStatus.SCENE_MARCH_GATHERING), "未观测到采集阶段");
        require(balance(session, 1) == before + finished.getRewardAmount(), "实际余额未到账");
        invoke(session, Cmd.CMD.CS_SceneMarchQuery, Cmd.CMD.SC_SceneMarchQuery, query, Scene.scSceneMarchQuery.class);
        require(balance(session, 1) == before + finished.getRewardAmount(), "重复查询重复发奖");
        awaitPush(initial.getMarchId(), false);
        Scene.scSceneView after = view(session, target.getPoint().getX(), target.getPoint().getY());
        Scene.SceneObjectSnapshot updated = after.getObjectsList().stream()
                .filter(object -> object.getObjectId() == target.getObjectId()).findFirst().orElseThrow();
        require(kind == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE
                        ? updated.getPersistentState().getAmount() == target.getPersistentState().getAmount() - 100
                        : updated.getPersistentState().getHealth() == 0, "目标状态未更新");
        System.out.printf("[SLG-GAMEPLAY] kind=%s target=(%d,%d) observer=(%d,%d) hostRegion=%d balance=%d->%d AOI=appear/remove%n",
                kind, target.getPoint().getX(), target.getPoint().getY(), midX, midY,
                finished.getTargetRegionId(), before, balance(session, 1));
    }

    private Map<Scene.SceneObjectKind, Scene.SceneObjectSnapshot> findTargets() throws Exception {
        Map<Scene.SceneObjectKind, Scene.SceneObjectSnapshot> targets = new EnumMap<>(Scene.SceneObjectKind.class);
        for (int[] camera : new int[][]{{600, 450}, {664, 450}, {536, 482}, {600, 514}, {664, 514}}) {
            for (Scene.SceneObjectSnapshot object : view(session, camera[0], camera[1]).getObjectsList()) {
                if (object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE && object.getPersistentState().getAmount() >= 100
                        || object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_MONSTER && object.getPersistentState().getHealth() > 0)
                    targets.putIfAbsent(object.getKind(), object);
            }
            if (targets.size() == 2) return targets;
        }
        throw new IllegalStateException("中央区域缺少采集或怪物目标");
    }

    private void awaitPush(long marchId, boolean present) throws Exception {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(20);
        while (System.nanoTime() < deadline) {
            Scene.scSceneView view = pushes.poll(1, TimeUnit.SECONDS);
            if (view != null && view.getObjectsList().stream().anyMatch(object -> object.getObjectId() == marchId) == present) return;
        }
        throw new IllegalStateException("跨区域AOI未收到行军" + (present ? "出现" : "移除") + "推送");
    }
    private void enter(RobotSession player, int x, int y) throws Exception {
        Scene.scSceneEnter response = invoke(player, Cmd.CMD.CS_SceneEnter, Cmd.CMD.SC_SceneEnter,
                Scene.csSceneEnter.newBuilder().setSceneId(scene).setPoint(point(x, y)).build(), Scene.scSceneEnter.class);
        require(response.getResult() == ErrorMsg.ErrorCode.Ok, "进入地图失败: " + response.getResult());
    }
    private Scene.scSceneView view(RobotSession player, int x, int y) throws Exception {
        Scene.scSceneView response = invoke(player, Cmd.CMD.CS_SceneView, Cmd.CMD.SC_SceneView,
                Scene.csSceneView.newBuilder().setSceneId(scene).setCenterPoint(point(x, y))
                        .setRadiusBlocks(1).build(), Scene.scSceneView.class);
        require(response.getResult() == ErrorMsg.ErrorCode.Ok, "读取九宫格失败: " + response.getResult());
        return response;
    }
    private long balance(RobotSession player, int resource) throws Exception {
        return invoke(player, Cmd.CMD.CS_ResourceQuery, Cmd.CMD.SC_ResourceQuery,
                Resource.CS_ResourceQuery.newBuilder().addResourceTypes(resource).build(), Resource.SC_ResourceQuery.class)
                .getResourcesOrDefault(resource, 0);
    }
    private static Scene.ScenePoint point(int x, int y) { return Scene.ScenePoint.newBuilder().setX(x).setY(y).build(); }
    private static boolean outside(Scene.scSceneView view, Scene.ScenePoint point) {
        return Math.abs(view.getCenterBlock().getX() - point.getX() / 32) > 1
                || Math.abs(view.getCenterBlock().getY() - point.getY() / 32) > 1;
    }
    private <T extends AbstractMessage> T invoke(RobotSession player, Cmd.CMD command, Cmd.CMD reply,
            AbstractMessage request, Class<T> type) throws Exception {
        SceneRobotAction<T> action = new SceneRobotAction<>(command, reply, request, type, command.name());
        require(action.execute(new RobotActionContext(null, player)).isSuccess(), "发包失败: " + command);
        return action.responseFuture().get(30, TimeUnit.SECONDS);
    }
    private static void require(boolean condition, String message) {
        if (!condition) throw new IllegalStateException(message);
    }
}
