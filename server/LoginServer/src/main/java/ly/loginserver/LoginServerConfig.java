package ly.loginserver;

import javax.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * Author: liuYang
 * Date: 2025/4/11
 * File: LoginServerConfig
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
