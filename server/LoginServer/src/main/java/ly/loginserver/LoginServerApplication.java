package ly.loginserver;

import javax.annotation.PostConstruct;
import ly.ServerContext;
import ly.config.ServerTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LoginServerConfig.class)
public class LoginServerApplication {
  @Autowired private LoginServerConfig loginServerConfig;

  public static void main(String[] args) {
    SpringApplication.run(LoginServerApplication.class, args);
  }

  @PostConstruct
  public void init() {
    System.out.println("nacosUrl:" + loginServerConfig.getNacosUrl());
    ServerContext.startUp(
        loginServerConfig.getNacosUrl(),
        ServerTypeEnum.LOGIN.getType(),
        "loginServer",
        "ly",
        new LoginGameObjectProvider());
  }
}
