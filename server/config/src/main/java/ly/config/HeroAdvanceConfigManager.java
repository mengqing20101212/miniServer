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
 * File: HeroAdvanceConfigManager
 */
public class HeroAdvanceConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAdvanceConfigManager instance = new HeroAdvanceConfigManager();
  private static final HeroAdvanceConfigManagerImpl instanceImplA = new HeroAdvanceConfigManagerImpl();
  private static final HeroAdvanceConfigManagerImpl instanceImplB = new HeroAdvanceConfigManagerImpl();

  public static HeroAdvanceConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroAdvanceConfigManagerImpl getStandby() {
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
    HeroAdvanceConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroAdvanceConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAdvanceConfig> configList = List.of();
    private Map<Integer, HeroAdvanceConfig> configMap = Map.of();

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
      HeroAdvanceConfigChecker checker = new HeroAdvanceConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroAdvanceConfig> newList = new ArrayList<>();
      Map<Integer, HeroAdvanceConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 14) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int modelName = 0;
          int sequence = 0;
          String attrType = null;
          String attrNum = null;
          int skillPoint = 0;
          int skillPointNum = 0;
          String advanceItem = null;
          int replaceItems = 0;
          String describe = null;
          int getItem = 0;
          int drop = 0;
          int dropShow = 0;
          String itemDes = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 英雄模板名
            if (!arr[1].trim().isEmpty()) {
              modelName = Integer.parseInt(arr[1].trim());
            }

            // 解析 进阶次序
            if (!arr[2].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[2].trim());
            }

            // 解析 属性类型
            if (!arr[3].trim().isEmpty()) {
              attrType = arr[3].trim();
            }

            // 解析 属性数值
            if (!arr[4].trim().isEmpty()) {
              attrNum = arr[4].trim();
            }

            // 解析 技能点
            if (!arr[5].trim().isEmpty()) {
              skillPoint = Integer.parseInt(arr[5].trim());
            }

            // 解析 技能点数量
            if (!arr[6].trim().isEmpty()) {
              skillPointNum = Integer.parseInt(arr[6].trim());
            }

            // 解析 进阶材料需求
            if (!arr[7].trim().isEmpty()) {
              advanceItem = arr[7].trim();
            }

            // 解析 进阶替换物
            if (!arr[8].trim().isEmpty()) {
              replaceItems = Integer.parseInt(arr[8].trim());
            }

            // 解析 属性描述
            if (!arr[9].trim().isEmpty()) {
              describe = arr[9].trim();
            }

            // 解析 获取途径道具ID
            if (!arr[10].trim().isEmpty()) {
              getItem = Integer.parseInt(arr[10].trim());
            }

            // 解析 掉落ID
            if (!arr[11].trim().isEmpty()) {
              drop = Integer.parseInt(arr[11].trim());
            }

            // 解析 掉落展示
            if (!arr[12].trim().isEmpty()) {
              dropShow = Integer.parseInt(arr[12].trim());
            }

            // 解析 物品描述
            if (!arr[13].trim().isEmpty()) {
              itemDes = arr[13].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroAdvanceConfig config = new HeroAdvanceConfig(id, modelName, sequence, attrType, attrNum, skillPoint, skillPointNum, advanceItem, replaceItems, describe, getItem, drop, dropShow, itemDes);
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

    public List<HeroAdvanceConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAdvanceConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroAdvance.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
