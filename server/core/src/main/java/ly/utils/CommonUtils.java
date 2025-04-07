package ly.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import ly.ServerContext;
import ly.config.ServerConfig;

/*
 * Author: liuYang
 * Date: 2025/4/7
 * File: CommonUtils
 */
public class CommonUtils {
  public static <T> T parserYaml(Class<T> clazz, String yamlStr) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    return mapper.readValue(yamlStr, clazz);
  }

  public static void main(String[] args) {
    String ymalStr =
        "serverPort: 12001\n"
            + "serverIp: 127.0.0.1\n"
            + "runModule: TEST\n"
            + "configPath: D:\\WORK\\me\\gameProject\\conf\n"
            + "db:\n"
            + "  jdbcUrl: jdbc:mysql://139.224.80.204:3306/pick_money\n"
            + "  userName: root\n"
            + "  passWord: ly.1006897725\n"
            + "  maxPoolSize: 10\n"
            + "  minIdle: 3\n"
            + "  idleTimeout: 30000\n"
            + "  connectionTimeout: 2000\n"
            + "  \n"
            + "redis:\n"
            + "  host: 139.224.80.204\n"
            + "  port: 6379\n"
            + "  password: ly.1006897725\n";

    try {
      ServerConfig serverConfig = parserYaml(ServerConfig.class, ymalStr);
      ServerContext.setServerConfig(serverConfig);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
