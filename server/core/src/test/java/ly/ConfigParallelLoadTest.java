package ly;

import static org.junit.Assert.*;
import java.util.*;
import java.util.concurrent.*;
import org.junit.Test;
import org.slf4j.Logger;

public class ConfigParallelLoadTest {
    private final CountDownLatch loading = new CountDownLatch(2);
    private final List<Thread> threads = Collections.synchronizedList(new ArrayList<>());
    private int processed;
    private boolean fail;

    abstract class Table extends AbstractConfigManger {
        boolean loaded;
        protected void reload(Logger logger, String directory) {
            threads.add(Thread.currentThread());
            loading.countDown();
            try {
                if (Runtime.getRuntime().availableProcessors() > 1)
                    assertTrue("两个表应并行解析", loading.await(5, TimeUnit.SECONDS));
            } catch (InterruptedException error) { throw new RuntimeException(error); }
            if (fail && this instanceof Right) throw new ConfigLoadException("bad XML");
            loaded = true;
        }
        protected void clear() {}
        public String getConfigFileName() { return getClass().getSimpleName() + ".xml"; }
        protected void finishLoad(Logger logger) {
            ConfigService service = ConfigService.getInstance();
            assertEquals("candidate", ConfigScope.capture().version());
            assertTrue(service.table(Left.class).loaded);
            assertTrue(service.table(Right.class).loaded);
            processed++;
        }
        protected void afterLoad() { assertEquals(2, processed); }
    }
    class Left extends Table {}
    class Right extends Table {}

    @Test public void allTablesCompleteBeforeCrossTableProcessing() {
        withTables(() -> {
            ConfigSnapshot result = ConfigService.getInstance().loadSnapshot(null, "", "candidate");
            assertTrue(result.table(Left.class).loaded);
            assertEquals(2, processed);
            assertFalse(threads.contains(Thread.currentThread()));
            if (Runtime.getRuntime().availableProcessors() > 1) assertNotSame(threads.get(0), threads.get(1));
        });
    }

    @Test public void failedCandidateDoesNotRunCrossTableProcessingOrReplaceActiveSnapshot() {
        fail = true;
        withTables(() -> {
            ConfigService service = ConfigService.getInstance();
            ConfigSnapshot previous = service.snapshot();
            ConfigLoadException error = assertThrows(ConfigLoadException.class,
                    () -> service.loadAllConfigToStandby(null, "", "candidate"));
            assertTrue(error.getMessage().contains("bad XML"));
            assertEquals(0, processed);
            assertSame(previous, service.snapshot());
        });
    }

    private void withTables(Runnable test) {
        ConfigService service = ConfigService.getInstance();
        List<InterfaceConfigManagerProxy> original = new ArrayList<>(service.configManagerList);
        service.configManagerList.clear();
        for (Table table : List.of(new Left(), new Right())) {
            service.configManagerList.add(new InterfaceConfigManagerProxy() {
                public AbstractConfigManger createTable() { return table; }
                public String getConfigFileName() { return table.getConfigFileName(); }
            });
        }
        try { test.run(); }
        finally { service.configManagerList.clear(); service.configManagerList.addAll(original); }
    }
}
