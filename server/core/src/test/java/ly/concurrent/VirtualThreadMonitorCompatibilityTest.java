package ly.concurrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class VirtualThreadMonitorCompatibilityTest {

    private static final Duration PROCESS_TIMEOUT = Duration.ofSeconds(10);

    @Test
    public void synchronizedBlockingDoesNotPinTheOnlyCarrier() throws Exception {
        assertTrue("JEP 491 requires JDK 24 or newer", Runtime.version().feature() >= 24);

        String javaExecutable = Path.of(
                System.getProperty("java.home"), "bin", isWindows() ? "java.exe" : "java").toString();
        Process process = new ProcessBuilder(
                        javaExecutable,
                        "-Djdk.virtualThreadScheduler.parallelism=1",
                        "-Djdk.virtualThreadScheduler.maxPoolSize=1",
                        "-cp",
                        System.getProperty("java.class.path"),
                        MonitorUnpinningProbe.class.getName())
                .redirectErrorStream(true)
                .start();

        boolean finished = process.waitFor(PROCESS_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
        }
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        assertTrue("virtual-thread monitor probe timed out: " + output, finished);
        assertEquals("virtual-thread monitor probe failed: " + output, 0, process.exitValue());
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    public static final class MonitorUnpinningProbe {

        public static void main(String[] args) throws Exception {
            Object monitor = new Object();
            CountDownLatch blockerEntered = new CountDownLatch(1);
            CountDownLatch releaseBlocker = new CountDownLatch(1);
            CountDownLatch probeRan = new CountDownLatch(1);

            Thread blocker = Thread.ofVirtual().start(() -> {
                synchronized (monitor) {
                    blockerEntered.countDown();
                    try {
                        releaseBlocker.await();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            if (!blockerEntered.await(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("blocking virtual thread did not start");
            }

            Thread probe = Thread.ofVirtual().start(probeRan::countDown);
            if (!probeRan.await(2, TimeUnit.SECONDS)) {
                throw new IllegalStateException("synchronized block pinned the only carrier");
            }

            releaseBlocker.countDown();
            blocker.join();
            probe.join();
        }
    }
}
