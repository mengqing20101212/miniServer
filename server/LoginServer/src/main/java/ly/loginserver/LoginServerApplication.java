package ly.loginserver;

import ly.ServerContext;
import ly.config.ServerTypeEnum;
import ly.startup.StartupSkillLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties(LoginServerConfig.class)
public class LoginServerApplication {
    @Autowired
    private LoginServerConfig loginServerConfig;

    public static void main(String[] args) {
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveLoginArgs();
        System.setProperty("debug", "false");
        System.setProperty("logging.level.root", "INFO");
        System.setProperty("logging.level.org.springframework", "INFO");
        System.setProperty(
                "logging.level.org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLogger",
                "OFF");
        System.setProperty("loginserver.nacosUrl", resolved.nacosUrl);
        if (resolved.springPort != null) {
            System.setProperty("server.port", String.valueOf(resolved.springPort));
        }
        SpringApplication.run(LoginServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        StartupSkillLoader.ResolvedServerArgs resolved = StartupSkillLoader.resolveLoginArgs();
        System.out.println("nacosUrl:" + loginServerConfig.getNacosUrl());
        ServerContext.startUp(
                loginServerConfig.getNacosUrl(),
                ServerTypeEnum.LOGIN.getType(),
                resolved.serverId,
                resolved.env,
                new LoginGameObjectProvider());
    }
}
