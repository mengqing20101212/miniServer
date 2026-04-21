package ly.config;

/*
 * Author: liuYang
 * Date: 2025/4/3
 * File: ServerConfig
 */
public class ServerConfig {

  /** 策划表路径 */
  public String configPath = "";

  public DbConfig db;
  public RedisConfig redis;
  public int serverPort;
  public String serverIp;
  public String runModule;

  public String serverId;

  public RedisConfig getRedis() {
    return redis;
  }

  public void setRedis(RedisConfig redis) {
    this.redis = redis;
  }

  public String getConfigPath() {
    return configPath;
  }

  public void setConfigPath(String configPath) {
    this.configPath = configPath;
  }

  public DbConfig getDb() {
    return db;
  }

  public void setDb(DbConfig db) {
    this.db = db;
  }

  public int getServerPort() {
    return serverPort;
  }

  public void setServerPort(int serverPort) {
    this.serverPort = serverPort;
  }

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public String getRunModule() {
    return runModule;
  }

  public void setRunModule(String runModule) {
    this.runModule = runModule;
  }

  public String getServerId() {
    return serverId;
  }

  public void setServerId(String serverId) {
    this.serverId = serverId;
  }

  public static void main(String[] args) {
    ServerConfig serverConfig = new ServerConfig();
    serverConfig.setServerId("game1001");
    serverConfig.setServerIp("127.0.0.1");
    serverConfig.setServerPort(9001);
    serverConfig.setConfigPath("/config");
    DbConfig dbConfig = new DbConfig();
    dbConfig.setJdbcUrl("jdbc:mysql://139.224.80.204:3306/miniServer");
    dbConfig.setUserName("root");
    dbConfig.setPassWord("ly.1006897725");
    serverConfig.setDb(dbConfig);
    RedisConfig redisConfig = new RedisConfig();
    redisConfig.setHost("139.224.80.204");
    redisConfig.setPort(6379);
    redisConfig.setPassword("ly.1006897725");
    serverConfig.setRedis(redisConfig);
    serverConfig.setRunModule("TEST");
    
  }
}
