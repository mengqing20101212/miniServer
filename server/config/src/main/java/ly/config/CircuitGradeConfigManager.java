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
 * File: CircuitGradeConfigManager
 */
public class CircuitGradeConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CircuitGradeConfigManager instance = new CircuitGradeConfigManager();
  private static final CircuitGradeConfigManagerImpl instanceImplA = new CircuitGradeConfigManagerImpl();
  private static final CircuitGradeConfigManagerImpl instanceImplB = new CircuitGradeConfigManagerImpl();

  public static CircuitGradeConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CircuitGradeConfigManagerImpl getStandby() {
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
    CircuitGradeConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CircuitGradeConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitGradeConfig> configList = List.of();
    private Map<Integer, CircuitGradeConfig> configMap = Map.of();

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
      CircuitGradeConfigChecker checker = new CircuitGradeConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CircuitGradeConfig> newList = new ArrayList<>();
      Map<Integer, CircuitGradeConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 18) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String description = null;
          int quality = 0;
          int lv1 = 0;
          int lv2 = 0;
          int lv3 = 0;
          int lv4 = 0;
          int lv5 = 0;
          int lv6 = 0;
          int lv7 = 0;
          int lv8 = 0;
          int lv9 = 0;
          int lv10 = 0;
          int lv11 = 0;
          int lv12 = 0;
          int lv13 = 0;
          int lv14 = 0;
          int lv15 = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 回路描述
            if (!arr[1].trim().isEmpty()) {
              description = arr[1].trim();
            }

            // 解析 品质
            if (!arr[2].trim().isEmpty()) {
              quality = Integer.parseInt(arr[2].trim());
            }

            // 解析 等级1评分
            if (!arr[3].trim().isEmpty()) {
              lv1 = Integer.parseInt(arr[3].trim());
            }

            // 解析 等级2评分
            if (!arr[4].trim().isEmpty()) {
              lv2 = Integer.parseInt(arr[4].trim());
            }

            // 解析 等级3评分
            if (!arr[5].trim().isEmpty()) {
              lv3 = Integer.parseInt(arr[5].trim());
            }

            // 解析 等级4评分
            if (!arr[6].trim().isEmpty()) {
              lv4 = Integer.parseInt(arr[6].trim());
            }

            // 解析 等级5评分
            if (!arr[7].trim().isEmpty()) {
              lv5 = Integer.parseInt(arr[7].trim());
            }

            // 解析 等级6评分
            if (!arr[8].trim().isEmpty()) {
              lv6 = Integer.parseInt(arr[8].trim());
            }

            // 解析 等级7评分
            if (!arr[9].trim().isEmpty()) {
              lv7 = Integer.parseInt(arr[9].trim());
            }

            // 解析 等级8评分
            if (!arr[10].trim().isEmpty()) {
              lv8 = Integer.parseInt(arr[10].trim());
            }

            // 解析 等级9评分
            if (!arr[11].trim().isEmpty()) {
              lv9 = Integer.parseInt(arr[11].trim());
            }

            // 解析 等级10评分
            if (!arr[12].trim().isEmpty()) {
              lv10 = Integer.parseInt(arr[12].trim());
            }

            // 解析 等级11评分
            if (!arr[13].trim().isEmpty()) {
              lv11 = Integer.parseInt(arr[13].trim());
            }

            // 解析 等级12评分
            if (!arr[14].trim().isEmpty()) {
              lv12 = Integer.parseInt(arr[14].trim());
            }

            // 解析 等级13评分
            if (!arr[15].trim().isEmpty()) {
              lv13 = Integer.parseInt(arr[15].trim());
            }

            // 解析 等级14评分
            if (!arr[16].trim().isEmpty()) {
              lv14 = Integer.parseInt(arr[16].trim());
            }

            // 解析 等级15评分
            if (!arr[17].trim().isEmpty()) {
              lv15 = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CircuitGradeConfig config = new CircuitGradeConfig(id, description, quality, lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lv10, lv11, lv12, lv13, lv14, lv15);
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

    public List<CircuitGradeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitGradeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitGrade.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
