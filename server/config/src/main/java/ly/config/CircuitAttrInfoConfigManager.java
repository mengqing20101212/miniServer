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
 * File: CircuitAttrInfoConfigManager
 */
public class CircuitAttrInfoConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CircuitAttrInfoConfigManager instance = new CircuitAttrInfoConfigManager();
  private static final CircuitAttrInfoConfigManagerImpl instanceImplA = new CircuitAttrInfoConfigManagerImpl();
  private static final CircuitAttrInfoConfigManagerImpl instanceImplB = new CircuitAttrInfoConfigManagerImpl();

  public static CircuitAttrInfoConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CircuitAttrInfoConfigManagerImpl getStandby() {
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
    CircuitAttrInfoConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CircuitAttrInfoConfigManagerImpl extends AbstractConfigManger {
    private List<CircuitAttrInfoConfig> configList = List.of();
    private Map<Integer, CircuitAttrInfoConfig> configMap = Map.of();

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
      CircuitAttrInfoConfigChecker checker = new CircuitAttrInfoConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CircuitAttrInfoConfig> newList = new ArrayList<>();
      Map<Integer, CircuitAttrInfoConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 13) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int type = 0;
          String description = null;
          String skillDescription = null;
          int attrType = 0;
          int attrNum = 0;
          int upgradeAdd = 0;
          String name = null;
          int pos = 0;
          String quality = null;
          int sequence = 0;
          int skillId = 0;
          int extraSkillId = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 类型
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 描述
            if (!arr[2].trim().isEmpty()) {
              description = arr[2].trim();
            }

            // 解析 技能描述
            if (!arr[3].trim().isEmpty()) {
              skillDescription = arr[3].trim();
            }

            // 解析 属性类型
            if (!arr[4].trim().isEmpty()) {
              attrType = Integer.parseInt(arr[4].trim());
            }

            // 解析 属性数值
            if (!arr[5].trim().isEmpty()) {
              attrNum = Integer.parseInt(arr[5].trim());
            }

            // 解析 升级增量
            if (!arr[6].trim().isEmpty()) {
              upgradeAdd = Integer.parseInt(arr[6].trim());
            }

            // 解析 回路名称
            if (!arr[7].trim().isEmpty()) {
              name = arr[7].trim();
            }

            // 解析 位置
            if (!arr[8].trim().isEmpty()) {
              pos = Integer.parseInt(arr[8].trim());
            }

            // 解析 品质
            if (!arr[9].trim().isEmpty()) {
              quality = arr[9].trim();
            }

            // 解析 次序
            if (!arr[10].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[10].trim());
            }

            // 解析 附带技能Id
            if (!arr[11].trim().isEmpty()) {
              skillId = Integer.parseInt(arr[11].trim());
            }

            // 解析 扩展技能Id
            if (!arr[12].trim().isEmpty()) {
              extraSkillId = Integer.parseInt(arr[12].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CircuitAttrInfoConfig config = new CircuitAttrInfoConfig(id, type, description, skillDescription, attrType, attrNum, upgradeAdd, name, pos, quality, sequence, skillId, extraSkillId);
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

    public List<CircuitAttrInfoConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CircuitAttrInfoConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "circuitAttrInfo.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
