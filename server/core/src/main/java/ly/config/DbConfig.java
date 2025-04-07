package ly.config;

/*
 * Author: liuYang
 * Date: 2025/4/7
 * File: DbConfig
 */
public class DbConfig {
  // DB 相关
  public String jdbcUrl = "";

  /** 数据库用户名 */
  public String userName = "";

  /** 数据库密码 */
  public String passWord = "";

  /** 连接池最大连接数 */
  public int maxPoolSize = 0;

  /** 最小空闲连接数 */
  public int minIdle = 0;

  /** 连接空闲时间 */
  public int idleTimeout = 0;

  /** 连接超时时间 */
  public int connectionTimeout = 0;

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassWord() {
    return passWord;
  }

  public void setPassWord(String passWord) {
    this.passWord = passWord;
  }

  public int getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public int getMinIdle() {
    return minIdle;
  }

  public void setMinIdle(int minIdle) {
    this.minIdle = minIdle;
  }

  public int getIdleTimeout() {
    return idleTimeout;
  }

  public void setIdleTimeout(int idleTimeout) {
    this.idleTimeout = idleTimeout;
  }

  public int getConnectionTimeout() {
    return connectionTimeout;
  }

  public void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }
}
