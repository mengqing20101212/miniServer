package ly.config;

/**
 * Redis 工具/配置组件，封装缓存键、连接池和常用 Redis 操作。
 */
public class RedisConfig {
  public String host;
  public int port;
  public String password;
  public int timeout;

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

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
