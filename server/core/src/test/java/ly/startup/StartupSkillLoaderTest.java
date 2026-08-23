package ly.startup;

import static org.junit.Assert.assertEquals;

import java.util.List;

import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import org.junit.Test;

/** 保证仓库启动文件新增字段与 Java 映射保持同步，避免所有服务在 main 入口解析时退出。 */
public class StartupSkillLoaderTest {
    @Test
    public void sceneRuntimeBaselineCanBeParsedFromCanonicalStartupSkill() {
        StartupSkillLoader.StartupServer scene =
                StartupSkillLoader.loadRequired().startup.scene;

        assertEquals(Integer.valueOf(60), scene.loadLogSeconds);
        assertEquals(Integer.valueOf(200), scene.slowTickMillis);
        assertEquals(Integer.valueOf(1), scene.pathRegionPadding);
        assertEquals(Integer.valueOf(4), scene.pathParallelism);
        assertEquals(Integer.valueOf(10_000), scene.pathMaxPending);
        assertEquals(Integer.valueOf(128), scene.regionMigrationQueueCapacity);
        assertEquals(Integer.valueOf(1_000), scene.restorePageSize);
        assertEquals(Integer.valueOf(4), scene.persistencePartitions);
        assertEquals(
                List.of("login", "game", "scene", "gate", "bot"),
                StartupSkillLoader.loadRequired().startup.validation.startupOrder);
    }

    @Test
    public void sceneFallbackKeepsSharedConfigButUsesItsOwnNodeIdentity() {
        ServerConfig fallback = new ServerConfig();
        fallback.serverId = "gate1001";
        fallback.serverPort = 9001;
        fallback.configPath = "shared-config-path";

        StartupSkillLoader.applyFallbackNodeConfig(ServerTypeEnum.SCENE, fallback);

        assertEquals("scene1001", fallback.serverId);
        assertEquals(9101, fallback.serverPort);
        assertEquals("shared-config-path", fallback.configPath);
    }
}
