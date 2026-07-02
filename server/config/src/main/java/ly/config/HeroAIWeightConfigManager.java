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
 * File: HeroAIWeightConfigManager
 */
public class HeroAIWeightConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final HeroAIWeightConfigManager instance = new HeroAIWeightConfigManager();
  private static final HeroAIWeightConfigManagerImpl instanceImplA = new HeroAIWeightConfigManagerImpl();
  private static final HeroAIWeightConfigManagerImpl instanceImplB = new HeroAIWeightConfigManagerImpl();

  public static HeroAIWeightConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static HeroAIWeightConfigManagerImpl getStandby() {
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
    HeroAIWeightConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class HeroAIWeightConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAIWeightConfig> configList = List.of();
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
      HeroAIWeightConfigChecker checker = new HeroAIWeightConfigChecker();
      checker.checkHeader(logger, configDir);
      List<HeroAIWeightConfig> newList = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 7) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int heroId = 0;
          int modelId = 0;
          String name = null;
          String des = null;
          int CLASS = 0;
          int sequence = 0;
          int weight = 0;
          try {
            // 解析 英雄编号
            if (!arr[0].trim().isEmpty()) {
              heroId = Integer.parseInt(arr[0].trim());
            }

            // 解析 模板编号
            if (!arr[1].trim().isEmpty()) {
              modelId = Integer.parseInt(arr[1].trim());
            }

            // 解析 备注
            if (!arr[2].trim().isEmpty()) {
              name = arr[2].trim();
            }

            // 解析 备注
            if (!arr[3].trim().isEmpty()) {
              des = arr[3].trim();
            }

            // 解析 优先级
            if (!arr[4].trim().isEmpty()) {
              CLASS = Integer.parseInt(arr[4].trim());
            }

            // 解析 次序
            if (!arr[5].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[5].trim());
            }

            // 解析 权重
            if (!arr[6].trim().isEmpty()) {
              weight = Integer.parseInt(arr[6].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          HeroAIWeightConfig config = new HeroAIWeightConfig(heroId, modelId, name, des, CLASS, sequence, weight);
          config.afterLoad();
          newList.add(config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
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

    public List<HeroAIWeightConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "heroAIWeight.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
