package ly.sceneserver.net;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import ly.LoggerDef;
import ly.net.ConnectSession;
import ly.net.HandlerRouterManager;
import ly.net.NetService;
import ly.net.packet.MessagePacket;

/**
 * SceneServer 的服务器间 RPC 有序并行分发器，网络层仍完全复用 core。
 *
 * <p>Netty IO 线程只负责把完整包放入 {@link ConnectSession}；本类再按包头 guid（场景协议中
 * 即 playerId/aggregateId）哈希到固定条带。同一个 guid 永远落到同一个单消费者队列，因此
 * 进入、移动、寻路、离开的提交顺序不会被打乱；不同玩家可以在不同虚拟线程并行等待 SceneShard
 * Tick 返回。SceneObject 的真正读写仍只发生在 SceneShard Tick 线程，不会破坏场景线程边界。
 *
 * <p>旧实现只有一个 dispatcher 线程，并且 Handler 内部会同步等待 Tick Future，最终导致所有
 * 连接和所有玩家全局串行。条带化解决的是 RPC 编排并发，不是让业务线程直接修改地图。
 */
public final class SceneRpcDispatcher implements AutoCloseable {
    private static final int DEFAULT_STRIPE_COUNT = 32;
    private static final int DEFAULT_QUEUE_CAPACITY = 4_096;

    private final AtomicBoolean running = new AtomicBoolean();
    private final DispatchStripe[] stripes;
    private Thread collectorThread;

    public SceneRpcDispatcher() {
        int requestedStripes = Integer.getInteger("slg.scene.rpc-stripes", DEFAULT_STRIPE_COUNT);
        int stripeCount = normalizePowerOfTwo(requestedStripes);
        int queueCapacity = Math.max(128,
                Integer.getInteger("slg.scene.rpc-queue-capacity", DEFAULT_QUEUE_CAPACITY));
        this.stripes = new DispatchStripe[stripeCount];
        for (int index = 0; index < stripeCount; index++) {
            stripes[index] = new DispatchStripe(index, queueCapacity);
        }
    }

    public void start() {
        if (!running.compareAndSet(false, true)) {
            return;
        }
        for (DispatchStripe stripe : stripes) {
            stripe.start();
        }
        collectorThread = Thread.ofVirtual().name("scene-rpc-collector").start(this::collectLoop);
        LoggerDef.SystemLogger.info(
                "Scene RPC dispatcher started, stripes={}, queueCapacity={}",
                stripes.length,
                stripes[0].queue.remainingCapacity());
    }

    /**
     * 扫描连接收包队列只做 drain 和路由，不在 collector 上执行任何会等待 Tick 的 Handler。
     * 没有业务包时短暂休眠，避免空服持续自旋占用一个 CPU 核。
     */
    private void collectLoop() {
        while (running.get()) {
            boolean dispatched = false;
            for (ConnectSession session : NetService.getInstance().getGameObjectMaps().values()) {
                if (!(session instanceof SceneConnectSession)) {
                    continue;
                }
                List<MessagePacket> packets = session.getReceivePacketList();
                for (MessagePacket packet : packets) {
                    dispatched = true;
                    if (!stripe(packet.getGuid()).offer(new DispatchTask(session, packet))) {
                        return;
                    }
                }
            }
            if (!dispatched) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }

    private DispatchStripe stripe(long guid) {
        int hash = Long.hashCode(guid);
        hash ^= hash >>> 16;
        return stripes[hash & (stripes.length - 1)];
    }

    /** 向上规整为 1 到 256 之间的 2 次幂，便于使用位运算稳定路由。 */
    private static int normalizePowerOfTwo(int requested) {
        int bounded = Math.max(1, Math.min(256, requested));
        int normalized = 1;
        while (normalized < bounded) {
            normalized <<= 1;
        }
        return normalized;
    }

    @Override
    public void close() {
        if (!running.compareAndSet(true, false)) {
            return;
        }
        if (collectorThread != null) {
            collectorThread.interrupt();
        }
        for (DispatchStripe stripe : stripes) {
            stripe.close();
        }
    }

    private record DispatchTask(ConnectSession session, MessagePacket packet) {
    }

    private final class DispatchStripe implements AutoCloseable {
        private final int index;
        private final BlockingQueue<DispatchTask> queue;
        private Thread workerThread;

        private DispatchStripe(int index, int queueCapacity) {
            this.index = index;
            this.queue = new ArrayBlockingQueue<>(queueCapacity);
        }

        private void start() {
            workerThread = Thread.ofVirtual()
                    .name("scene-rpc-stripe-" + index)
                    .start(this::runLoop);
        }

        /**
         * 队列满时阻塞 collector，形成明确背压，不能悄悄丢掉玩家命令。
         * close 通过中断 collector 解除 put 等待。
         */
        private boolean offer(DispatchTask task) {
            try {
                queue.put(task);
                return true;
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        private void runLoop() {
            while (running.get() || !queue.isEmpty()) {
                try {
                    DispatchTask task = queue.take();
                    HandlerRouterManager.execute(task.session(), task.packet());
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    return;
                } catch (Throwable error) {
                    LoggerDef.SystemLogger.error("Scene RPC stripe execute failed, stripe={}", index, error);
                }
            }
        }

        @Override
        public void close() {
            if (workerThread != null) {
                workerThread.interrupt();
            }
        }
    }
}
