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
 * File: ScoreConfigManager
 */
public class ScoreConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ScoreConfigManager instance = new ScoreConfigManager();
  private static final ScoreConfigManagerImpl instanceImplA = new ScoreConfigManagerImpl();
  private static final ScoreConfigManagerImpl instanceImplB = new ScoreConfigManagerImpl();

  public static ScoreConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ScoreConfigManagerImpl getStandby() {
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
    ScoreConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ScoreConfigManagerImpl extends AbstractConfigManger {
    private List<ScoreConfig> configList = List.of();
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
      ScoreConfigChecker checker = new ScoreConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ScoreConfig> newList = new ArrayList<>();
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
          int level = 0;
          int heroLevelTarget = 0;
          int breakTarget = 0;
          int advanceTarget = 0;
          int awakenTarget = 0;
          int suitTarget = 0;
          int supportTarget = 0;
          float heroLevelCoefficient = 0F;
          float breakCoefficient = 0F;
          float advanceCoefficient = 0F;
          float awakenCoefficient = 0F;
          float suitCoefficient = 0F;
          float supportCoefficient = 0F;
          try {
            // 解析 账号等级
            if (!arr[0].trim().isEmpty()) {
              level = Integer.parseInt(arr[0].trim());
            }

            // 解析 角色等级目标
            if (!arr[1].trim().isEmpty()) {
              heroLevelTarget = Integer.parseInt(arr[1].trim());
            }

            // 解析 突破目标
            if (!arr[2].trim().isEmpty()) {
              breakTarget = Integer.parseInt(arr[2].trim());
            }

            // 解析 进阶目标
            if (!arr[3].trim().isEmpty()) {
              advanceTarget = Integer.parseInt(arr[3].trim());
            }

            // 解析 觉醒目标
            if (!arr[4].trim().isEmpty()) {
              awakenTarget = Integer.parseInt(arr[4].trim());
            }

            // 解析 源核目标
            if (!arr[5].trim().isEmpty()) {
              suitTarget = Integer.parseInt(arr[5].trim());
            }

            // 解析 表彰目标
            if (!arr[6].trim().isEmpty()) {
              supportTarget = Integer.parseInt(arr[6].trim());
            }

            // 解析 角色等级系数
            if (!arr[7].trim().isEmpty()) {
              heroLevelCoefficient = Float.parseFloat(arr[7].trim());
            }

            // 解析 突破系数
            if (!arr[8].trim().isEmpty()) {
              breakCoefficient = Float.parseFloat(arr[8].trim());
            }

            // 解析 进阶系数
            if (!arr[9].trim().isEmpty()) {
              advanceCoefficient = Float.parseFloat(arr[9].trim());
            }

            // 解析 觉醒系数
            if (!arr[10].trim().isEmpty()) {
              awakenCoefficient = Float.parseFloat(arr[10].trim());
            }

            // 解析 源核系数
            if (!arr[11].trim().isEmpty()) {
              suitCoefficient = Float.parseFloat(arr[11].trim());
            }

            // 解析 表彰系数
            if (!arr[12].trim().isEmpty()) {
              supportCoefficient = Float.parseFloat(arr[12].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ScoreConfig config = new ScoreConfig(level, heroLevelTarget, breakTarget, advanceTarget, awakenTarget, suitTarget, supportTarget, heroLevelCoefficient, breakCoefficient, advanceCoefficient, awakenCoefficient, suitCoefficient, supportCoefficient);
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

    public List<ScoreConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "score.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
