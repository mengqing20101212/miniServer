package ly.bot.scenario;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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
    private Scene.ScenePoint city;
    private int width;
    private int[] regions;
    private final List<Scene.ScenePoint> regionWindows = new ArrayList<>();

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
            city = enter(session, 1, 1);
            enter(observer, 2, 2);
            loadRegionWindows();
            Map<Long, Scene.SceneObjectSnapshot> before = regionTargets();
            long observerBalance = balance(observer, 1);
            Scene.SceneObjectSnapshot mine = chooseTarget(before, Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE);
            long stock = mine.getPersistentState().getAmount();
            require(stock > 100, "矿点余量不足以验证分次采集");
            while (stock > 100) {
                stock -= 100;
                runMarch(mine, 100);
                Scene.SceneObjectSnapshot partial = regionTargets().get(mine.getObjectId());
                require(partial != null && partial.getPoint().equals(mine.getPoint())
                        && partial.getPersistentState().getAmount() == stock, "原矿点未保留实际剩余量");
                System.out.printf("[SLG-GAMEPLAY] partial mine=%d remaining=%d%n", mine.getObjectId(), stock);
            }
            runMarch(mine, stock);
            Map<Long, Scene.SceneObjectSnapshot> afterMine = regionTargets();
            verifyReplacement(before, afterMine, mine);
            Scene.SceneObjectSnapshot monster = chooseTarget(afterMine, Scene.SceneObjectKind.SCENE_OBJECT_MONSTER);
            runMarch(monster, 100);
            verifyReplacement(afterMine, regionTargets(), monster);
            require(balance(observer, 1) == observerBalance, "观察者收到其他玩家的奖励");
            for (RobotSession player : List.of(session, observer))
                invoke(player, Cmd.CMD.CS_SceneLeave, Cmd.CMD.SC_SceneLeave,
                        Scene.csSceneLeave.newBuilder().setSceneId(scene).build(), Scene.scSceneLeave.class);
            System.out.println("[SLG-GAMEPLAY] PASS partial-gather, depleted-mine-respawn, monster-respawn, automatic-return-award, read-only-query, cross-region-AOI");
            completion.complete(null);
        } catch (Throwable failure) { completion.completeExceptionally(failure); }
    }

    private void runMarch(Scene.SceneObjectSnapshot target, long reward) throws Exception {
        Scene.SceneObjectKind kind = target.getKind();
        long before = balance(session, 1);
        int midX = (target.getPoint().getX() + city.getX()) / 2;
        int midY = (target.getPoint().getY() + city.getY()) / 2;
        Scene.scSceneView observing = view(observer, midX, midY);
        require(outside(observing, city) && outside(observing, target.getPoint()),
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
        require(initial.getOrigin().equals(city), "行军未从主城出发");
        require(initial.getOwnerPlayerId() == session.getPlayerId(), "Game未绑定真实玩家");
        require(initial.getTroopCount() == 100 && initial.getPower() == 100, "Game未覆盖客户端兵力战力");
        require(initial.getTargetRegionId() == 25, "用例目标应位于公司地图中央区域25");
        awaitPush(initial.getMarchId(), Scene.SceneMarchStatus.SCENE_MARCH_MOVING);
        awaitPush(initial.getMarchId(), Scene.SceneMarchStatus.SCENE_MARCH_RETURNING);
        require(balance(session, 1) == before, "返程途中已经发奖");
        Scene.csSceneMarchQuery query = Scene.csSceneMarchQuery.newBuilder().setSceneId(scene)
                .setMarchId(initial.getMarchId()).setTargetRegionId(initial.getTargetRegionId()).build();
        awaitPush(initial.getMarchId(), Scene.SceneMarchStatus.SCENE_MARCH_FINISHED);
        // 首次查行军前先通过独立的资产接口确认余额，防止查询触发发奖的回归。
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(30);
        long actual = balance(session, 1);
        while (actual == before && System.nanoTime() < deadline) {
            Thread.sleep(200);
            actual = balance(session, 1);
        }
        require(actual == before + reward, "未查行军时返程奖励没有自动到账");
        Scene.scSceneMarchQuery response = invoke(session, Cmd.CMD.CS_SceneMarchQuery, Cmd.CMD.SC_SceneMarchQuery,
                query, Scene.scSceneMarchQuery.class);
        Scene.SceneMarchSnapshot finished = response.getMarch();
        require(response.getResult() == ErrorMsg.ErrorCode.Ok
                && finished.getStatus() == Scene.SceneMarchStatus.SCENE_MARCH_FINISHED
                && finished.getTargetRegionId() == initial.getTargetRegionId()
                && finished.getVictory() && finished.getRewardAmount() == reward, "已结束行军记录错误");
        invoke(session, Cmd.CMD.CS_SceneMarchQuery, Cmd.CMD.SC_SceneMarchQuery, query, Scene.scSceneMarchQuery.class);
        require(balance(session, 1) == actual, "查询行军改变了余额");
        System.out.printf("[SLG-GAMEPLAY] kind=%s target=(%d,%d) observer=(%d,%d) observerRegion=%d hostRegion=%d balance=%d->%d AOI=moving/returning/removed%n",
                kind, target.getPoint().getX(), target.getPoint().getY(), midX, midY,
                regions[midY * width + midX], finished.getTargetRegionId(), before, actual);
    }

    private void loadRegionWindows() throws Exception {
        Path directory = Path.of(System.getProperty("slg.scene.map-dir", "maps/slg"));
        JSONObject map = JSON.parseObject(Files.readString(directory.resolve("world-regions.json")));
        width = map.getIntValue("width");
        int height = map.getIntValue("height");
        regions = map.getJSONArray("cellRegionIds").toJavaObject(int[].class);
        // 一次九宫格覆盖 3×3 个 32 格视野块，盘点所有与目标区相交的窗口。
        int stride = 96;
        int columns = (width + stride - 1) / stride;
        BitSet windows = new BitSet();
        for (int index = 0; index < regions.length; index++) {
            if (regions[index] == 25) windows.set((index / width / stride) * columns + index % width / stride);
        }
        for (int window = windows.nextSetBit(0); window >= 0; window = windows.nextSetBit(window + 1)) {
            regionWindows.add(point(Math.min(width - 1, (window % columns) * stride + stride / 2),
                    Math.min(height - 1, (window / columns) * stride + stride / 2)));
        }
    }

    private Map<Long, Scene.SceneObjectSnapshot> regionTargets() throws Exception {
        Map<Long, Scene.SceneObjectSnapshot> targets = new LinkedHashMap<>();
        for (Scene.ScenePoint center : regionWindows) {
            for (Scene.SceneObjectSnapshot object : view(session, center.getX(), center.getY()).getObjectsList()) {
                Scene.ScenePoint target = object.getPoint();
                if (regions[target.getY() * width + target.getX()] == 25
                        && (object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE
                        || object.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_MONSTER))
                    targets.put(object.getObjectId(), object);
            }
        }
        return targets;
    }

    private Scene.SceneObjectSnapshot chooseTarget(Map<Long, Scene.SceneObjectSnapshot> targets, Scene.SceneObjectKind kind) {
        for (Scene.SceneObjectSnapshot object : targets.values()) {
            Scene.ScenePoint target = object.getPoint();
            int midX = (target.getX() + city.getX()) / 2;
            int midY = (target.getY() + city.getY()) / 2;
            if (object.getKind() == kind && regions[midY * width + midX] != 25
                    && (kind != Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE || object.getPersistentState().getAmount() > 100))
                return object;
        }
        throw new IllegalStateException("中央区域缺少跨区行军目标 " + kind);
    }

    private void verifyReplacement(Map<Long, Scene.SceneObjectSnapshot> before,
            Map<Long, Scene.SceneObjectSnapshot> after, Scene.SceneObjectSnapshot old) {
        Set<Long> expected = new HashSet<>(before.keySet());
        expected.remove(old.getObjectId());
        require(!after.containsKey(old.getObjectId()) && after.size() == before.size()
                && after.keySet().containsAll(expected), "原区域未按配额替换旧目标");
        Scene.SceneObjectSnapshot fresh = after.values().stream()
                .filter(candidate -> !before.containsKey(candidate.getObjectId())).findFirst().orElseThrow();
        require(fresh.getKind() == old.getKind()
                && fresh.getPersistentState().getConfigId() == old.getPersistentState().getConfigId()
                && (fresh.getKind() == Scene.SceneObjectKind.SCENE_OBJECT_RESOURCE
                ? fresh.getPersistentState().getAmount() >= old.getPersistentState().getAmount()
                : fresh.getPersistentState().getHealth() > 0), "补点模板或初始状态错误");
        System.out.printf("[SLG-GAMEPLAY] respawn kind=%s region=25 old=%d new=%d at=(%d,%d)%n",
                fresh.getKind(), old.getObjectId(), fresh.getObjectId(), fresh.getPoint().getX(), fresh.getPoint().getY());
    }

    private void awaitPush(long marchId, Scene.SceneMarchStatus status) throws Exception {
        long deadline = System.nanoTime() + TimeUnit.MINUTES.toNanos(6);
        while (System.nanoTime() < deadline) {
            Scene.scSceneView view = pushes.poll(1, TimeUnit.SECONDS);
            if (view == null) continue;
            Optional<Scene.SceneObjectSnapshot> march = view.getObjectsList().stream()
                    .filter(object -> object.getObjectId() == marchId).findFirst();
            // 结束行军已退出 AOI，最后一次快照用对象消失表示到家。
            if (status == Scene.SceneMarchStatus.SCENE_MARCH_FINISHED ? march.isEmpty()
                    : march.isPresent() && march.get().getMarch().getStatus() == status) return;
        }
        throw new IllegalStateException("跨区域AOI未收到行军状态 " + status + ", marchId=" + marchId);
    }
    private Scene.ScenePoint enter(RobotSession player, int x, int y) throws Exception {
        Scene.scSceneEnter response = invoke(player, Cmd.CMD.CS_SceneEnter, Cmd.CMD.SC_SceneEnter,
                Scene.csSceneEnter.newBuilder().setSceneId(scene).setPoint(point(x, y)).build(), Scene.scSceneEnter.class);
        require(response.getResult() == ErrorMsg.ErrorCode.Ok, "进入地图失败: " + response.getResult());
        return response.getCityPoint();
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
