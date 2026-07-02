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
 * File: HeroAwakenConfigManager
 */
public class HeroAwakenConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAwakenConfigManager instance = new HeroAwakenConfigManager();
  private static final HeroAwakenConfigManagerImpl instanceImplA = new HeroAwakenConfigManagerImpl();
  private static final HeroAwakenConfigManagerImpl instanceImplB = new HeroAwakenConfigManagerImpl();

  public static HeroAwakenConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroAwakenConfigManagerImpl getStandby() {
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
    HeroAwakenConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroAwakenConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAwakenConfig> configList = List.of();
    private Map<Integer, HeroAwakenConfig> configMap = Map.of();

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
      HeroAwakenConfigChecker checker = new HeroAwakenConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroAwakenConfig> newList = new ArrayList<>();
      Map<Integer, HeroAwakenConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 25) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int modelName = 0;
          int sequence = 0;
          int awakenType = 0;
          int awakenskill_1 = 0;
          int awakenskill_2 = 0;
          int awakenskill_3 = 0;
          int awakenskill_s1 = 0;
          int awakenskill_s2 = 0;
          int moreAwakenSkill1 = 0;
          int moreAwakenSkill2 = 0;
          int moreAwakenSkill3 = 0;
          int attrType = 0;
          int attrNum = 0;
          String awakenPhase = null;
          int awakenIcon = 0;
          String awakenTitle = null;
          String awakenAttrDes = null;
          String awakenItem = null;
          int awakenCurrencyType = 0;
          int awakenCurrencyNum = 0;
          int isReset = 0;
          String retainItem = null;
          int currencyType = 0;
          int currencyNum = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 英雄模板名
            if (!arr[1].trim().isEmpty()) {
              modelName = Integer.parseInt(arr[1].trim());
            }

            // 解析 觉醒次序
            if (!arr[2].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[2].trim());
            }

            // 解析 觉醒类型
            if (!arr[3].trim().isEmpty()) {
              awakenType = Integer.parseInt(arr[3].trim());
            }

            // 解析 觉醒后常规技能
            if (!arr[4].trim().isEmpty()) {
              awakenskill_1 = Integer.parseInt(arr[4].trim());
            }

            // 解析   null
            if (!arr[5].trim().isEmpty()) {
              awakenskill_2 = Integer.parseInt(arr[5].trim());
            }

            // 解析   null
            if (!arr[6].trim().isEmpty()) {
              awakenskill_3 = Integer.parseInt(arr[6].trim());
            }

            // 解析 觉醒超级技能
            if (!arr[7].trim().isEmpty()) {
              awakenskill_s1 = Integer.parseInt(arr[7].trim());
            }

            // 解析 觉醒超级技能
            if (!arr[8].trim().isEmpty()) {
              awakenskill_s2 = Integer.parseInt(arr[8].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[9].trim().isEmpty()) {
              moreAwakenSkill1 = Integer.parseInt(arr[9].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[10].trim().isEmpty()) {
              moreAwakenSkill2 = Integer.parseInt(arr[10].trim());
            }

            // 解析 多段觉醒技能
            if (!arr[11].trim().isEmpty()) {
              moreAwakenSkill3 = Integer.parseInt(arr[11].trim());
            }

            // 解析 属性类型
            if (!arr[12].trim().isEmpty()) {
              attrType = Integer.parseInt(arr[12].trim());
            }

            // 解析 属性数值
            if (!arr[13].trim().isEmpty()) {
              attrNum = Integer.parseInt(arr[13].trim());
            }

            // 解析 觉醒阶段
            if (!arr[14].trim().isEmpty()) {
              awakenPhase = arr[14].trim();
            }

            // 解析 觉醒图标
            if (!arr[15].trim().isEmpty()) {
              awakenIcon = Integer.parseInt(arr[15].trim());
            }

            // 解析 觉醒标题
            if (!arr[16].trim().isEmpty()) {
              awakenTitle = arr[16].trim();
            }

            // 解析 觉醒属性描述
            if (!arr[17].trim().isEmpty()) {
              awakenAttrDes = arr[17].trim();
            }

            // 解析 觉醒材料需求
            if (!arr[18].trim().isEmpty()) {
              awakenItem = arr[18].trim();
            }

            // 解析 觉醒货币类型
            if (!arr[19].trim().isEmpty()) {
              awakenCurrencyType = Integer.parseInt(arr[19].trim());
            }

            // 解析 觉醒货币数量
            if (!arr[20].trim().isEmpty()) {
              awakenCurrencyNum = Integer.parseInt(arr[20].trim());
            }

            // 解析 是否还原
            if (!arr[21].trim().isEmpty()) {
              isReset = Integer.parseInt(arr[21].trim());
            }

            // 解析 还原返还
            if (!arr[22].trim().isEmpty()) {
              retainItem = arr[22].trim();
            }

            // 解析 分解消耗货币类型
            if (!arr[23].trim().isEmpty()) {
              currencyType = Integer.parseInt(arr[23].trim());
            }

            // 解析 分解消耗货币数量
            if (!arr[24].trim().isEmpty()) {
              currencyNum = Integer.parseInt(arr[24].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroAwakenConfig config = new HeroAwakenConfig(id, modelName, sequence, awakenType, awakenskill_1, awakenskill_2, awakenskill_3, awakenskill_s1, awakenskill_s2, moreAwakenSkill1, moreAwakenSkill2, moreAwakenSkill3, attrType, attrNum, awakenPhase, awakenIcon, awakenTitle, awakenAttrDes, awakenItem, awakenCurrencyType, awakenCurrencyNum, isReset, retainItem, currencyType, currencyNum);
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

    public List<HeroAwakenConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, HeroAwakenConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "heroAwaken.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {}

    /**
     * 根据英雄觉醒模板ID和觉醒等级获取配置
     */
    public HeroAwakenConfig getByHeroAwakenDataAndLevel(int heroAwakenData, int awakenLevel) {
      for (HeroAwakenConfig config : configList) {
        if (config.modelName == heroAwakenData && config.sequence == awakenLevel) {
          return config;
        }
      }
      return null;
    }
    // @@@@@自定义方法结束区@@@@@
  }
}
