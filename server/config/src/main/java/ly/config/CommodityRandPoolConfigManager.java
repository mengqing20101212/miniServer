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
 * File: CommodityRandPoolConfigManager
 */
public class CommodityRandPoolConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CommodityRandPoolConfigManager instance = new CommodityRandPoolConfigManager();
  private static final CommodityRandPoolConfigManagerImpl instanceImplA = new CommodityRandPoolConfigManagerImpl();
  private static final CommodityRandPoolConfigManagerImpl instanceImplB = new CommodityRandPoolConfigManagerImpl();

  public static CommodityRandPoolConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CommodityRandPoolConfigManagerImpl getStandby() {
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
    CommodityRandPoolConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CommodityRandPoolConfigManagerImpl extends AbstractConfigManger {
    private List<CommodityRandPoolConfig> configList = List.of();
    private Map<Integer, CommodityRandPoolConfig> configMap = Map.of();

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
      CommodityRandPoolConfigChecker checker = new CommodityRandPoolConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CommodityRandPoolConfig> newList = new ArrayList<>();
      Map<Integer, CommodityRandPoolConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 21) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int grade = 0;
          int school = 0;
          int group = 0;
          String des = null;
          String poolOneLevel = null;
          String poolOne = null;
          String poolTwoLevel = null;
          String poolTwo = null;
          String poolThreeLevel = null;
          String poolThree = null;
          String poolFourLevel = null;
          String poolFour = null;
          String poolFiveLevel = null;
          String poolFive = null;
          String poolSixLevel = null;
          String poolSix = null;
          String poolSevenLevel = null;
          String poolSeven = null;
          String poolEightLevel = null;
          String poolEight = null;
          try {
            // 解析 商品随机池ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 一级商店id
            if (!arr[1].trim().isEmpty()) {
              grade = Integer.parseInt(arr[1].trim());
            }

            // 解析 二级商店id
            if (!arr[2].trim().isEmpty()) {
              school = Integer.parseInt(arr[2].trim());
            }

            // 解析 商品组id
            if (!arr[3].trim().isEmpty()) {
              group = Integer.parseInt(arr[3].trim());
            }

            // 解析 注释
            if (!arr[4].trim().isEmpty()) {
              des = arr[4].trim();
            }

            // 解析 随机组1等级条件
            if (!arr[5].trim().isEmpty()) {
              poolOneLevel = arr[5].trim();
            }

            // 解析 随机组1
            if (!arr[6].trim().isEmpty()) {
              poolOne = arr[6].trim();
            }

            // 解析 随机组2等级条件
            if (!arr[7].trim().isEmpty()) {
              poolTwoLevel = arr[7].trim();
            }

            // 解析 随机组2
            if (!arr[8].trim().isEmpty()) {
              poolTwo = arr[8].trim();
            }

            // 解析 随机组3等级条件
            if (!arr[9].trim().isEmpty()) {
              poolThreeLevel = arr[9].trim();
            }

            // 解析 随机组3
            if (!arr[10].trim().isEmpty()) {
              poolThree = arr[10].trim();
            }

            // 解析 随机组4等级条件
            if (!arr[11].trim().isEmpty()) {
              poolFourLevel = arr[11].trim();
            }

            // 解析 随机组4
            if (!arr[12].trim().isEmpty()) {
              poolFour = arr[12].trim();
            }

            // 解析 随机组5等级条件
            if (!arr[13].trim().isEmpty()) {
              poolFiveLevel = arr[13].trim();
            }

            // 解析 随机组5
            if (!arr[14].trim().isEmpty()) {
              poolFive = arr[14].trim();
            }

            // 解析 随机组6等级条件
            if (!arr[15].trim().isEmpty()) {
              poolSixLevel = arr[15].trim();
            }

            // 解析 随机组6
            if (!arr[16].trim().isEmpty()) {
              poolSix = arr[16].trim();
            }

            // 解析 随机组7等级条件
            if (!arr[17].trim().isEmpty()) {
              poolSevenLevel = arr[17].trim();
            }

            // 解析 随机组7
            if (!arr[18].trim().isEmpty()) {
              poolSeven = arr[18].trim();
            }

            // 解析 随机组8等级条件
            if (!arr[19].trim().isEmpty()) {
              poolEightLevel = arr[19].trim();
            }

            // 解析 随机组8
            if (!arr[20].trim().isEmpty()) {
              poolEight = arr[20].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CommodityRandPoolConfig config = new CommodityRandPoolConfig(id, grade, school, group, des, poolOneLevel, poolOne, poolTwoLevel, poolTwo, poolThreeLevel, poolThree, poolFourLevel, poolFour, poolFiveLevel, poolFive, poolSixLevel, poolSix, poolSevenLevel, poolSeven, poolEightLevel, poolEight);
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

    public List<CommodityRandPoolConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CommodityRandPoolConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "commodityRandPool.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
