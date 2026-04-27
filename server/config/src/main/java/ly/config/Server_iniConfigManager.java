package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ly.utils.KV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: Server_iniConfigManager
 */
public class Server_iniConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final Server_iniConfigManager instance = new Server_iniConfigManager();
  private static final Server_iniConfigManagerImpl instanceImplA =
      new Server_iniConfigManagerImpl();
  private static final Server_iniConfigManagerImpl instanceImplB =
      new Server_iniConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static Server_iniConfigManagerImpl getInstance() {
    if (instance.isSwitched()) {
      return instanceImplA;
    } else {
      return instanceImplB;
    }
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  public static class Server_iniConfigManagerImpl extends AbstractConfigManger {

    List<Server_iniConfig> configList = new ArrayList<Server_iniConfig>();
    Map<Integer, Server_iniConfig> configMap = new HashMap<Integer, Server_iniConfig>();


    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    protected void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      clear();
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        br.readLine(); //先读取一行表头 
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          Server_iniConfig config = new Server_iniConfig();
          try {
            //解析   null
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析   null
            if (!arr[1].trim().isEmpty()) {
            config.name = arr[1].trim();
            }

            //解析   null
            if (!arr[2].trim().isEmpty()) {
            config.des = arr[2].trim();
            }

            //解析   null
            if (!arr[3].trim().isEmpty()) {
            config.dev_default = arr[3].trim();
            }

            //解析   null
            if (!arr[4].trim().isEmpty()) {
            config.a01_default = arr[4].trim();
            }

            //解析   null
            if (!arr[5].trim().isEmpty()) {
            config.b02_default = arr[5].trim();
            }

            //解析   null
            if (!arr[6].trim().isEmpty()) {
            config.c03_default = arr[6].trim();
            }

            //解析   null
            if (!arr[7].trim().isEmpty()) {
            config.d04_default = arr[7].trim();
            }

            //解析   null
            if (!arr[8].trim().isEmpty()) {
            config.e05_default = arr[8].trim();
            }

            //解析   null
            if (!arr[9].trim().isEmpty()) {
            config.f06_default = arr[9].trim();
            }

            //解析   null
            if (!arr[10].trim().isEmpty()) {
            config.g07_default = arr[10].trim();
            }

            //解析   null
            if (!arr[11].trim().isEmpty()) {
            config.h08_default = arr[11].trim();
            }

            //解析   null
            if (!arr[12].trim().isEmpty()) {
            config.i09_default = arr[12].trim();
            }

            //解析   null
            if (!arr[13].trim().isEmpty()) {
            config.weekly_multinode = arr[13].trim();
            }

            //解析   null
            if (!arr[14].trim().isEmpty()) {
            config.korea_dev_default = arr[14].trim();
            }

            //解析   null
            if (!arr[15].trim().isEmpty()) {
            config.korea_weekly_default = arr[15].trim();
            }

            //解析   null
            if (!arr[16].trim().isEmpty()) {
            config.korea_gray_default = arr[16].trim();
            }

            //解析   null
            if (!arr[17].trim().isEmpty()) {
            config.tencent_dev1_default = arr[17].trim();
            }

            //解析   null
            if (!arr[18].trim().isEmpty()) {
            config.tencent_dev2_default = arr[18].trim();
            }

            //解析   null
            if (!arr[19].trim().isEmpty()) {
            config.tencent_dev3_default = arr[19].trim();
            }

            //解析   null
            if (!arr[20].trim().isEmpty()) {
            config.tencent_dev4_default = arr[20].trim();
            }

            //解析   null
            if (!arr[21].trim().isEmpty()) {
            config.tencent_dev5_default = arr[21].trim();
            }

            //解析   null
            if (!arr[22].trim().isEmpty()) {
            config.tencent_gray_default = arr[22].trim();
            }

            //解析   null
            if (!arr[23].trim().isEmpty()) {
            config.tencent_official_default = arr[23].trim();
            }

            //解析   null
            if (!arr[24].trim().isEmpty()) {
            config.tencent_ios_cert_default = arr[24].trim();
            }

            if (!arr[25].trim().isEmpty()) {
            config.tencent_release_default = arr[25].trim();
            }

            if (!arr[26].trim().isEmpty()) {
            config.tencent_stress_test_default = arr[26].trim();
            }

            if (!arr[27].trim().isEmpty()) {
            config.tencent_idc_test_default = arr[27].trim();
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
          configMap.put(config.id, config);
        }
        afterLoad();
      } catch (IOException e) {
        e.printStackTrace();
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    protected void clear() {

      configList.clear();
      configMap.clear();

      // @@@@@自定义clear方法开始区@@@@@


      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        try {
          result.add(Integer.parseInt(part.trim()));
        } catch (NumberFormatException e) {
          // 如果不是数字，则跳过
        }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            try {
              Integer key = Integer.parseInt(keyStr);
              Integer val = Integer.parseInt(valueStr);
              result.add(new KV<>(key, val));
            } catch (NumberFormatException e) {
              // 如果不是数字，则跳过
            }
          }
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<String, String>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            result.add(new KV<>(keyStr, valueStr));
          }
        }
      }
      return result;
    }

    public List<Server_iniConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Server_iniConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "server_ini.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
