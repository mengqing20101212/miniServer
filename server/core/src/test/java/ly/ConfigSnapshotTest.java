package ly;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;

import java.nio.file.*;
import java.util.*;

public class ConfigSnapshotTest {
    public static final class NumberTable extends AbstractConfigManger {
        int value;

        protected void reload(Logger logger, String directory) {
            try {
                value = Integer.parseInt(Files.readString(Path.of(directory, "numbers.txt")));
            } catch (Exception error) {
                throw new ConfigLoadException("invalid number");
            }
        }

        public String getConfigFileName() {
            return "numbers.txt";
        }

        protected void clear() {
            throw new AssertionError("old snapshot must not be cleared");
        }

        protected void afterLoad() {}
    }

    @Test
    public void wholeSnapshotSwitchPreservesRunningTaskAndNextReloadDoesNotReuseOldTables()
            throws Exception {
        ConfigService service = ConfigService.getInstance();
        List<InterfaceConfigManagerProxy> original = new ArrayList<>(service.configManagerList);
        java.lang.reflect.Field active = ConfigService.class.getDeclaredField("active");
        active.setAccessible(true);
        ConfigSnapshot previous = service.snapshot();
        Path directory = Files.createTempDirectory("snapshot-test-");
        service.configManagerList.clear();
        service.configManagerList.add(
                new InterfaceConfigManagerProxy() {
                    public AbstractConfigManger createTable() {
                        return new NumberTable();
                    }

                    public String getConfigFileName() {
                        return "numbers.txt";
                    }
                });
        try {
            Files.writeString(directory.resolve("numbers.txt"), "1");
            service.loadAllConfig(null, directory.toString());
            ConfigSnapshot old = service.snapshot();
            try (ConfigScope scope = ConfigScope.open()) {
                Files.writeString(directory.resolve("numbers.txt"), "2");
                service.loadAllConfigToStandby(null, directory.toString(), "V2");
                service.schedulePreparedSwitch("V2", System.currentTimeMillis() + 50, () -> {});
                service.awaitVersion("V2", 2000);
                assertEquals(1, service.table(NumberTable.class).value);
                assertEquals("startup", ConfigScope.capture().version());
                try (ConfigScope inherited = ConfigScope.open()) {
                    assertEquals(1, service.table(NumberTable.class).value);
                }
                assertEquals(2, service.snapshot().table(NumberTable.class).value);
            }
            assertEquals(2, service.table(NumberTable.class).value);
            Files.writeString(directory.resolve("numbers.txt"), "bad");
            assertThrows(
                    ConfigLoadException.class,
                    () -> service.loadAllConfigToStandby(null, directory.toString(), "bad"));
            assertEquals("V2", service.getActiveVersion());
            Files.writeString(directory.resolve("numbers.txt"), "3");
            service.loadAllConfigToStandby(null, directory.toString(), "V3");
            service.schedulePreparedSwitch("V3", System.currentTimeMillis(), () -> {});
            service.awaitVersion("V3", 2000);
            assertEquals(1, old.table(NumberTable.class).value);
            assertNotSame(old.table(NumberTable.class), service.table(NumberTable.class));
        } finally {
            service.configManagerList.clear();
            service.configManagerList.addAll(original);
            active.set(service, previous);
            service.resume();
            Files.deleteIfExists(directory.resolve("numbers.txt"));
            Files.deleteIfExists(directory);
        }
    }
}
