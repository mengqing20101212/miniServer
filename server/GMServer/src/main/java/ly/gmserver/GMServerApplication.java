package ly.gmserver;

import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.startup.StartupSkillLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ly.gmserver", exclude = DataSourceAutoConfiguration.class)
public class GMServerApplication {
    private static final Logger log = LoggerFactory.getLogger(GMServerApplication.class);

    public static void main(String[] args) {
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveGmArgs();
        System.setProperty("debug", "false");
        System.setProperty("logging.level.root", "INFO");
        System.setProperty("logging.level.org.springframework", "INFO");
        System.setProperty(
                "logging.level.org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLogger",
                "OFF");
        System.setProperty("gmserver.nacosUrl", resolved.nacosUrl);
        System.setProperty("gmserver.env", resolved.env);
        System.setProperty("gmserver.serverId", resolved.serverId);
        if (resolved.springPort != null) {
            System.setProperty("server.port", String.valueOf(resolved.springPort));
        }
        SpringApplication.run(GMServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        String nacosUrl = System.getProperty("gmserver.nacosUrl", "localhost:8848");
        String env = System.getProperty("gmserver.env", "ly");
        String serverId = System.getProperty("gmserver.serverId", "gmServer");
        log.info("GMServer initializing Nacos: url={}, serverId={}, env={}", nacosUrl, serverId, env);
        try {
            ServerContext.startUp(
                nacosUrl,
                ServerTypeEnum.GMSERVER.getType(),
                serverId,
                env
            );
            log.info("GMServer Nacos startup completed successfully");
        } catch (Exception e) {
            log.error("Failed to start ServerContext via Nacos", e);
            throw new RuntimeException("GMServer startup failed due to Nacos/DB initialization error", e);
        }
    }
}
