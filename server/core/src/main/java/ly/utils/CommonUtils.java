package ly.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.protobuf.AbstractMessage;
import com.google.protobuf.util.JsonFormat;

import ly.LoggerDef;
import ly.ServerContext;
import ly.config.ServerConfig;

/**
 * 公共工具类，提供时间、随机、位图、KV 解析或通用数据结构等辅助能力。
 */
public class CommonUtils {
    public static <T> T parserYaml(Class<T> clazz, String yamlStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(yamlStr, clazz);
    }

    private static final JsonFormat.Printer PB_JSON_PRINTER = JsonFormat.printer()
            // 单行 JSON，去掉无意义空格
            .omittingInsignificantWhitespace();
    // 字段名保持 proto 原始字段名
    // 如果你想用 accountId/gameServerId 这种驼峰，可以删掉这一行
    // .preservingProtoFieldNames();

    public static String logProto(AbstractMessage msg) {
        try {
            return PB_JSON_PRINTER.print(msg);
        } catch (Exception e) {
            e.printStackTrace();
            // fallback to protobuf text representation on failure
            return msg.toString();
        }
    }

    public static void main(String[] args) {
        String ymalStr = "serverPort: 12001\n"
                + "serverIp: 127.0.0.1\n"
                + "runModule: TEST\n"
                + "configPath: D:\\WORK\\me\\gameProject\\conf\n"
                + "db:\n"
                + "  jdbcUrl: jdbc:mysql://118.25.76.117:3306/pick_money\n"
                + "  userName: root\n"
                + "  passWord: Ly@2026Root!8899\n"
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
            System.out.println(ymalStr);
            ServerConfig serverConfig = parserYaml(ServerConfig.class, ymalStr);
            ServerContext.setServerConfig(serverConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
