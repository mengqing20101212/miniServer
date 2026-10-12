package ly.config.hotupdate;

import static org.junit.Assert.*;

import org.junit.Test;

import java.nio.file.*;
import java.util.*;

public class ConfigPublicationTest {
    private ConfigPublication publication() {
        ConfigPublication state = new ConfigPublication();
        state.command = new ConfigHotUpdateCommand();
        state.command.version = "V1";
        state.command.sequence = 1;
        state.command.publishId = "V1";
        state.command.digest = ConfigBundle.digest(Map.of("item.txt", "item"));
        state.command.targets = Map.of("game1", "boot1", "scene1", "boot2");
        return state;
    }

    @Test
    public void allTargetInstancesMustBeReadyBeforeTimedCommitAndActiveConfirmation()
            throws Exception {
        ConfigPublication state = publication();
        long now = System.currentTimeMillis();
        String hash = state.command.digest;
        state.acceptReport("game1", "boot1", hash, "READY", now, now);
        assertThrows(IllegalStateException.class, () -> state.commit(now, now + 15000));
        assertThrows(
                IllegalStateException.class,
                () -> state.acceptReport("scene1", "restarted", hash, "READY", now, now));
        assertThrows(
                IllegalArgumentException.class,
                () -> state.acceptReport("scene1", "boot2", "bad", "READY", now, now));
        assertThrows(
                IllegalStateException.class,
                () -> state.acceptReport("scene1", "boot2", hash, "READY", now - 3000, now));
        state.acceptReport("scene1", "boot2", hash, "READY", now, now);
        assertThrows(IllegalArgumentException.class, () -> state.commit(now, now + 100));
        state.commit(now, now + 15000);
        Path file = Files.createTempDirectory("publication-test-").resolve("publication.json");
        try {
            DurableConfigFile.write(file, state);
            ConfigPublication recovered = DurableConfigFile.read(file, ConfigPublication.class);
            assertEquals("COMMITTED", recovered.phase);
            assertEquals(now + 15000, recovered.command.switchAtMillis);
            recovered.acceptReport("game1", "newboot", hash, "ACTIVE", now, now);
            assertEquals("COMMITTED", recovered.phase);
            recovered.acceptReport("scene1", "boot2", hash, "ACTIVE", now, now);
            assertEquals("ACTIVE", recovered.phase);
            recovered.acceptReport("scene1", "boot2", hash, "FAILED", now, now);
            assertEquals("ACTIVE", recovered.statuses.get("scene1"));
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(file.getParent());
        }
    }

    @Test
    public void bundleDigestIgnoresMapOrderAndRejectsPathTraversal() {
        assertEquals(
                ConfigBundle.digest(Map.of("a.txt", "a", "b.txt", "b")),
                ConfigBundle.digest(new TreeMap<>(Map.of("b.txt", "b", "a.txt", "a"))));
        assertNotEquals(
                ConfigBundle.digest(Map.of("a.txt", "a")),
                ConfigBundle.digest(Map.of("a.txt", "b")));
        assertThrows(
                IllegalArgumentException.class, () -> ConfigBundle.digest(Map.of("../a.txt", "a")));
    }
}
