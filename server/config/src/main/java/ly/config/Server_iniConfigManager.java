package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: Server_iniConfigManager
 */
public class Server_iniConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final Server_iniConfigManager instance = new Server_iniConfigManager();
  private static final Server_iniConfigManagerImpl instanceImplA = new Server_iniConfigManagerImpl();
  private static final Server_iniConfigManagerImpl instanceImplB = new Server_iniConfigManagerImpl();

  public static Server_iniConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static Server_iniConfigManagerImpl getStandby() {
    return switched.get() ? instanceImplB : instanceImplA;
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  @Override
  public void loadStandbyConfig(Logger logger, String configDir) throws ConfigLoadException {
    getStandby().reload(logger, configDir);
  }

  @Override
  public AbstractConfigManger switchConfig() {
    Server_iniConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class Server_iniConfigManagerImpl extends AbstractConfigManger {
    private List<Server_iniConfig> configList = List.of();
    private Map<Integer, Server_iniConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      Server_iniConfigChecker checker = new Server_iniConfigChecker();
      checker.checkHeader(logger, configDir);
      List<Server_iniConfig> newList = new ArrayList<>();
      Map<Integer, Server_iniConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 28) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String des = null;
          String dev_default = null;
          String a01_default = null;
          String b02_default = null;
          String c03_default = null;
          String d04_default = null;
          String e05_default = null;
          String f06_default = null;
          String g07_default = null;
          String h08_default = null;
          String i09_default = null;
          String weekly_multinode = null;
          String korea_dev_default = null;
          String korea_weekly_default = null;
          String korea_gray_default = null;
          String tencent_dev1_default = null;
          String tencent_dev2_default = null;
          String tencent_dev3_default = null;
          String tencent_dev4_default = null;
          String tencent_dev5_default = null;
          String tencent_gray_default = null;
          String tencent_official_default = null;
          String tencent_ios_cert_default = null;
          String tencent_release_default = null;
          String tencent_stress_test_default = null;
          String tencent_idc_test_default = null;
          try {
            // 解析   null
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析   null
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析   null
            if (!arr[2].trim().isEmpty()) {
              des = arr[2].trim();
            }

            // 解析   null
            if (!arr[3].trim().isEmpty()) {
              dev_default = arr[3].trim();
            }

            // 解析   null
            if (!arr[4].trim().isEmpty()) {
              a01_default = arr[4].trim();
            }

            // 解析   null
            if (!arr[5].trim().isEmpty()) {
              b02_default = arr[5].trim();
            }

            // 解析   null
            if (!arr[6].trim().isEmpty()) {
              c03_default = arr[6].trim();
            }

            // 解析   null
            if (!arr[7].trim().isEmpty()) {
              d04_default = arr[7].trim();
            }

            // 解析   null
            if (!arr[8].trim().isEmpty()) {
              e05_default = arr[8].trim();
            }

            // 解析   null
            if (!arr[9].trim().isEmpty()) {
              f06_default = arr[9].trim();
            }

            // 解析   null
            if (!arr[10].trim().isEmpty()) {
              g07_default = arr[10].trim();
            }

            // 解析   null
            if (!arr[11].trim().isEmpty()) {
              h08_default = arr[11].trim();
            }

            // 解析   null
            if (!arr[12].trim().isEmpty()) {
              i09_default = arr[12].trim();
            }

            // 解析   null
            if (!arr[13].trim().isEmpty()) {
              weekly_multinode = arr[13].trim();
            }

            // 解析   null
            if (!arr[14].trim().isEmpty()) {
              korea_dev_default = arr[14].trim();
            }

            // 解析   null
            if (!arr[15].trim().isEmpty()) {
              korea_weekly_default = arr[15].trim();
            }

            // 解析   null
            if (!arr[16].trim().isEmpty()) {
              korea_gray_default = arr[16].trim();
            }

            // 解析   null
            if (!arr[17].trim().isEmpty()) {
              tencent_dev1_default = arr[17].trim();
            }

            // 解析   null
            if (!arr[18].trim().isEmpty()) {
              tencent_dev2_default = arr[18].trim();
            }

            // 解析   null
            if (!arr[19].trim().isEmpty()) {
              tencent_dev3_default = arr[19].trim();
            }

            // 解析   null
            if (!arr[20].trim().isEmpty()) {
              tencent_dev4_default = arr[20].trim();
            }

            // 解析   null
            if (!arr[21].trim().isEmpty()) {
              tencent_dev5_default = arr[21].trim();
            }

            // 解析   null
            if (!arr[22].trim().isEmpty()) {
              tencent_gray_default = arr[22].trim();
            }

            // 解析   null
            if (!arr[23].trim().isEmpty()) {
              tencent_official_default = arr[23].trim();
            }

            // 解析   null
            if (!arr[24].trim().isEmpty()) {
              tencent_ios_cert_default = arr[24].trim();
            }

            if (!arr[25].trim().isEmpty()) {
              tencent_release_default = arr[25].trim();
            }

            if (!arr[26].trim().isEmpty()) {
              tencent_stress_test_default = arr[26].trim();
            }

            if (!arr[27].trim().isEmpty()) {
              tencent_idc_test_default = arr[27].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          Server_iniConfig config = new Server_iniConfig(id, name, des, dev_default, a01_default, b02_default, c03_default, d04_default, e05_default, f06_default, g07_default, h08_default, i09_default, weekly_multinode, korea_dev_default, korea_weekly_default, korea_gray_default, tencent_dev1_default, tencent_dev2_default, tencent_dev3_default, tencent_dev4_default, tencent_dev5_default, tencent_gray_default, tencent_official_default, tencent_ios_cert_default, tencent_release_default, tencent_stress_test_default, tencent_idc_test_default);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        if (!part.trim().isEmpty()) { result.add(Integer.parseInt(part.trim())); }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) {
          result.add(new KV<>(Integer.parseInt(pair.substring(0, idx).trim()), Integer.parseInt(pair.substring(idx + 1).trim())));
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) { return new ArrayList<>(); }
      List<KV<String, String>> result = new ArrayList<>();
      for (String pair : value.split(",")) {
        int idx = pair.indexOf(":");
        if (idx > 0) { result.add(new KV<>(pair.substring(0, idx).trim(), pair.substring(idx + 1).trim())); }
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
