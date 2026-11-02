package ly.sceneserver.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.function.Consumer;
import ly.sceneserver.common.march.SceneMarchSnapshot;

/** AOI订阅与区域归属独立，路线穿过视野时由各区域汇总公开快照。 */
public final class SceneAoiService {
    private final SceneRuntime.SceneInstance scene;
    private final Map<Long, Subscription> subscriptions = new ConcurrentHashMap<>();
    SceneAoiService(SceneRuntime.SceneInstance scene) { this.scene = scene; }

    public void subscribe(SceneViewRequest request, Consumer<SceneViewSnapshot> changed) {
        subscriptions.put(request.playerId(), new Subscription(request, changed));
    }
    public void remove(long playerId) { subscriptions.remove(playerId); }

    public void marchChanged(SceneMarchSnapshot march) {
        for (Subscription subscription : subscriptions.values()) {
            SceneViewRequest view = subscription.request();
            int size = scene.config().regionSize();
            int x = view.center().x() / size, y = view.center().y() / size;
            int radius = view.radiusBlocks();
            if (!SceneRouteVisibility.intersects(march.origin(), march.target().point(),
                    (x - radius) * size, (y - radius) * size,
                    (x + radius + 1) * size - 1, (y + radius + 1) * size - 1)) continue;
            scene.updateViewAsync(view).thenAccept(subscription.changed()).exceptionally(error -> {
                ly.LoggerDef.NetLogger.warn("scene AOI change failed, playerId={}", view.playerId(), error);
                return null;
            });
        }
    }
    private record Subscription(SceneViewRequest request, Consumer<SceneViewSnapshot> changed) {}
}
