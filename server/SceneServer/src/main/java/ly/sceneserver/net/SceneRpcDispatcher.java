package ly.sceneserver.net;

import java.util.concurrent.atomic.AtomicBoolean;

import ly.net.ConnectSession;
import ly.net.NetService;

/** SceneServer 的服务器间 RPC 业务分发循环，网络层仍完全复用 core。 */
public final class SceneRpcDispatcher implements AutoCloseable {
    private final AtomicBoolean running = new AtomicBoolean();
    private Thread thread;

    public void start() {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        thread = Thread.ofVirtual().name("scene-rpc-dispatcher").start(() -> {
            while (running.get()) {
                boolean dispatched = false;
                for (ConnectSession session : NetService.getInstance().getGameObjectMaps().values()) {
                    if (session instanceof SceneConnectSession sceneSession) {
                        sceneSession.tick();
                        dispatched = true;
                    }
                }
                if (!dispatched) {
                    Thread.onSpinWait();
                } else {
                    try {
                        Thread.sleep(1L);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void close() {
        if (running.compareAndSet(true, false) && thread != null) {
            thread.interrupt();
        }
    }
}
