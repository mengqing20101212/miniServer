package ly.loginserver;

import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 登录服本地配置模型，承载 Spring 与公共启动流程需要读取的登录服参数。
 */
@Component
@ConfigurationProperties(prefix = "loginserver")
public class LoginServerConfig {
  private String nacosUrl;

  @PostConstruct
  public void init() {
    System.out.println("loginServerConfig: " + this);
  }

  // getter and setter
  public String getNacosUrl() {
    return nacosUrl;
  }

  public void setNacosUrl(String nacosUrl) {
    this.nacosUrl = nacosUrl;
  }
}
