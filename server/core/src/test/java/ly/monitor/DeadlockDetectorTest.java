package ly.monitor;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class DeadlockDetectorTest {

    @Test
    public void shouldDumpCurrentThreadStacks() {
        String dump = DeadlockDetector.dumpAllThreads();

        assertTrue("线程dump应该包含当前测试线程", dump.contains(Thread.currentThread().getName()));
        assertTrue("线程dump应该包含线程总数", dump.contains("JVM线程总数="));
    }

    @Test
    public void shouldFindJvmMonitorDeadlock() throws Exception {
        Object lockA = new Object();
        Object lockB = new Object();
        CountDownLatch lockedFirstMonitor = new CountDownLatch(2);
        CountDownLatch startSecondMonitor = new CountDownLatch(1);

        Thread threadA = newDeadlockThread(
                "deadlock-test-a",
                lockA,
                lockB,
                lockedFirstMonitor,
                startSecondMonitor);
        Thread threadB = newDeadlockThread(
                "deadlock-test-b",
                lockB,
                lockA,
                lockedFirstMonitor,
                startSecondMonitor);

        threadA.start();
        threadB.start();
        assertTrue("测试线程没有进入第一把锁", lockedFirstMonitor.await(2, TimeUnit.SECONDS));
        startSecondMonitor.countDown();

        long deadline = System.currentTimeMillis() + 3_000L;
        boolean found = false;
        while (System.currentTimeMillis() < deadline) {
            long[] deadlockedThreadIds = DeadlockDetector.checkNow();
            found = contains(deadlockedThreadIds, threadA.threadId())
                    && contains(deadlockedThreadIds, threadB.threadId());
            if (found) {
                break;
            }
            Thread.sleep(50L);
        }

        assertTrue("没有检测到测试构造的JVM死锁", found);
    }

    private Thread newDeadlockThread(
            String name,
            Object firstLock,
            Object secondLock,
            CountDownLatch lockedFirstMonitor,
            CountDownLatch startSecondMonitor) {
        Thread thread = new Thread(
                () -> {
                    synchronized (firstLock) {
                        lockedFirstMonitor.countDown();
                        await(startSecondMonitor);
                        synchronized (secondLock) {
                            // 这里不会执行到；测试线程会停在第二把锁上形成 JVM monitor 死锁。
                        }
                    }
                },
                name);
        thread.setDaemon(true);
        return thread;
    }

    private void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
    }

    private boolean contains(long[] values, long expected) {
        return Arrays.stream(values).anyMatch(value -> value == expected);
    }
}
