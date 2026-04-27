package ly.gmserver;

import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.startup.StartupSkillLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "ly.gmserver")
public class GMServerApplication {
    private static final Logger log = LoggerFactory.getLogger(GMServerApplication.class);

    @Value("${gmserver.nacos-url:localhost:8848}")
    private String nacosUrl;

    @Value("${gmserver.server-id:gm1001}")
    private String serverId;

    @Value("${gmserver.env:ly}")
    private String env;

    public static void main(String[] args) {
        System.setProperty("debug", "false");
        System.setProperty("logging.level.root", "INFO");
        System.setProperty("logging.level.org.springframework", "INFO");
        SpringApplication.run(GMServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("GMServer starting with nacosUrl={}, serverId={}, env={}", nacosUrl, serverId, env);
        try {
            ServerContext.startUp(
                nacosUrl,
                ServerTypeEnum.GM.getType(),
                serverId,
                env
            );
            log.info("GMServer ServerContext.startUp completed successfully");
        } catch (Exception e) {
            log.error("Failed to start ServerContext, Nacos may be unavailable. Running in limited mode.", e);
            // Continue running in limited mode without Nacos/Rpc
        }
    }
}
