package ly;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;

import ly.config.*;
import ly.config.hotupdate.*;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/** 本地 HTTP + 真实版本目录和提交日志，模拟通知丢失后的重启恢复。 */
public class ConfigHotUpdateRecoveryTest {
    private static void resetRuntime() throws Exception {
        for (String name : List.of("prepared", "committed")) {
            var field = ConfigHotUpdateRuntime.class.getDeclaredField(name);
            field.setAccessible(true);
            field.set(null, null);
        }
        var sequence = ConfigHotUpdateRuntime.class.getDeclaredField("appliedSequence");
        sequence.setAccessible(true);
        sequence.setLong(null, 0);
        var status = ConfigHotUpdateRuntime.class.getDeclaredField("lastStatus");
        status.setAccessible(true);
        status.set(null, "");
    }

    @Test
    public void preparedVersionDoesNotActivateUntilDurableCommitAndRestartUsesLocalBundle()
            throws Exception {
        ConfigService service = ConfigService.getInstance();
        List<InterfaceConfigManagerProxy> factories = new ArrayList<>(service.configManagerList);
        ConfigSnapshot originalSnapshot = service.snapshot();
        var active = ConfigService.class.getDeclaredField("active");
        active.setAccessible(true);
        ServerConfig previousConfig = ServerContext.serverConfig;
        String previousId = ServerContext.SERVER_ID, previousEnv = ServerContext.ENV;
        ServerTypeEnum previousType = ServerContext.serverType;
        String previousPath = System.getProperty("miniserver.config.statePath");
        Path root = Files.createTempDirectory("hotupdate-recovery-");
        List<String> reports = new CopyOnWriteArrayList<>();
        HttpServer http = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        ObjectMapper json = new ObjectMapper();
        byte[] response =
                json.writeValueAsBytes(
                        Map.of("data", List.of(Map.of("fileName", "numbers.txt", "content", "2"))));
        java.util.concurrent.atomic.AtomicBoolean downloadAvailable =
                new java.util.concurrent.atomic.AtomicBoolean(true);
        http.createContext(
                "/download",
                exchange -> {
                    if (!downloadAvailable.get()) {
                        exchange.sendResponseHeaders(503, -1);
                        exchange.close();
                        return;
                    }
                    exchange.sendResponseHeaders(200, response.length);
                    exchange.getResponseBody().write(response);
                    exchange.close();
                });
        http.createContext(
                "/report",
                exchange -> {
                    reports.add(
                            new String(
                                    exchange.getRequestBody().readAllBytes(),
                                    java.nio.charset.StandardCharsets.UTF_8));
                    exchange.sendResponseHeaders(200, -1);
                    exchange.close();
                });
        http.start();
        try {
            resetRuntime();
            service.configManagerList.clear();
            service.configManagerList.add(
                    new InterfaceConfigManagerProxy() {
                        public AbstractConfigManger createTable() {
                            return new ConfigSnapshotTest.NumberTable();
                        }

                        public String getConfigFileName() {
                            return "numbers.txt";
                        }
                    });
            System.setProperty("miniserver.config.statePath", root.resolve("state").toString());
            ServerContext.serverConfig = new ServerConfig();
            ServerContext.serverConfig.configPath = root.toString();
            ServerContext.SERVER_ID = "game1";
            ServerContext.ENV = "test";
            ServerContext.serverType = ServerTypeEnum.GAME;
            Files.writeString(root.resolve("numbers.txt"), "1");
            service.loadAllConfig(null, root.toString());
            ConfigHotUpdateCommand command = new ConfigHotUpdateCommand();
            command.commandType = "PREPARE";
            command.publishId = "V2";
            command.version = "V2";
            command.sequence = 2;
            command.digest = ConfigBundle.digest(Map.of("numbers.txt", "2"));
            command.targets = Map.of("game1", ConfigHotUpdateRuntime.BOOT_ID);
            command.downloadUrl = "http://127.0.0.1:" + http.getAddress().getPort() + "/download";
            command.reportUrl = "http://127.0.0.1:" + http.getAddress().getPort() + "/report";
            command.targets = Map.of("other-node", "other-boot");
            for (String phase : List.of("PREPARE", "COMMIT")) {
                command.commandType = phase;
                ConfigHotUpdateRuntime.handle(json.writeValueAsString(command));
                ConfigHotUpdateRuntime.recoverBeforeServing(json.writeValueAsString(command));
                assertEquals("startup", service.getActiveVersion());
                assertFalse(Files.exists(root.resolve("state/committed.json")));
                assertTrue(reports.isEmpty());
            }
            command.commandType = "PREPARE";
            command.targets = Map.of("game1", ConfigHotUpdateRuntime.BOOT_ID);
            ConfigHotUpdateRuntime.handle(json.writeValueAsString(command));
            assertEquals("startup", service.getActiveVersion());
            assertTrue(reports.stream().anyMatch(body -> body.contains("status=READY")));
            command.commandType = "COMMIT";
            command.switchAtMillis = System.currentTimeMillis() + 150;
            ConfigHotUpdateRuntime.handle(json.writeValueAsString(command));
            assertTrue(Files.exists(root.resolve("state/committed.json")));
            service.awaitVersion("V2", 3000);
            // 等待 ACTIVE 回调完全结束，随后模拟所有进程内状态丢失。
            long deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(3);
            while (reports.stream().noneMatch(body -> body.contains("status=ACTIVE"))) {
                if (System.nanoTime() > deadline) fail("ACTIVE not reported");
                Thread.sleep(10);
            }
            downloadAvailable.set(false);
            reports.clear(); // 恢复不依赖下载服务可用。
            resetRuntime();
            service.loadAllConfig(null, root.toString());
            ConfigHotUpdateRuntime.recoverBeforeServing(null);
            deadline = System.nanoTime() + java.util.concurrent.TimeUnit.SECONDS.toNanos(3);
            while (reports.stream().noneMatch(body -> body.contains("status=ACTIVE"))) {
                if (System.nanoTime() > deadline) fail("recovered ACTIVE not reported");
                Thread.sleep(10);
            }
            assertEquals("V2", service.getActiveVersion());
            assertEquals(2, service.table(ConfigSnapshotTest.NumberTable.class).value);
            assertEquals("1", Files.readString(root.resolve("numbers.txt"))); // 原目录从未被提前覆盖。
        } finally {
            http.stop(0);
            resetRuntime();
            service.resume();
            service.configManagerList.clear();
            service.configManagerList.addAll(factories);
            active.set(service, originalSnapshot);
            ServerContext.serverConfig = previousConfig;
            ServerContext.SERVER_ID = previousId;
            ServerContext.ENV = previousEnv;
            ServerContext.serverType = previousType;
            if (previousPath == null) System.clearProperty("miniserver.config.statePath");
            else System.setProperty("miniserver.config.statePath", previousPath);
            try (var paths = Files.walk(root)) {
                for (Path path : paths.sorted(Comparator.reverseOrder()).toList())
                    Files.delete(path);
            }
        }
    }
}
