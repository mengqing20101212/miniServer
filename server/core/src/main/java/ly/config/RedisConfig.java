package ly.config;

/*
 * Author: liuYang
 * Date: 2025/4/7
 * File: RedisConfig
 */
public class RedisConfig {
  public String host;
  public int port;
  public String password;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
