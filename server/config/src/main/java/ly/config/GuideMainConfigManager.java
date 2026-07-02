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
 * File: GuideMainConfigManager
 */
public class GuideMainConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuideMainConfigManager instance = new GuideMainConfigManager();
  private static final GuideMainConfigManagerImpl instanceImplA = new GuideMainConfigManagerImpl();
  private static final GuideMainConfigManagerImpl instanceImplB = new GuideMainConfigManagerImpl();

  public static GuideMainConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuideMainConfigManagerImpl getStandby() {
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
    GuideMainConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuideMainConfigManagerImpl extends AbstractConfigManger {
    private List<GuideMainConfig> configList = List.of();
    private Map<Integer, GuideMainConfig> configMap = Map.of();

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
      GuideMainConfigChecker checker = new GuideMainConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuideMainConfig> newList = new ArrayList<>();
      Map<Integer, GuideMainConfig> newMap = new HashMap<>();
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
          String beizhu = null;
          int checkNext = 0;
          String guideCond = null;
          String guidePara1 = null;
          int isMask = 0;
          String stepList = null;
          int keyStep = 0;
          int vanishType = 0;
          String vanishPara = null;
          int canSkip = 0;
          int weight = 0;
          int isLocal = 0;
          int autoTrigger = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 备注
            if (!arr[1].trim().isEmpty()) {
              beizhu = arr[1].trim();
            }

            // 解析 是否自动触发下一步
            if (!arr[2].trim().isEmpty()) {
              checkNext = Integer.parseInt(arr[2].trim());
            }

            // 解析 引导触发条件
            if (!arr[3].trim().isEmpty()) {
              guideCond = arr[3].trim();
            }

            // 解析 触发参数1
            if (!arr[4].trim().isEmpty()) {
              guidePara1 = arr[4].trim();
            }

            // 解析 是否有遮罩
            if (!arr[5].trim().isEmpty()) {
              isMask = Integer.parseInt(arr[5].trim());
            }

            // 解析 步骤列表
            if (!arr[6].trim().isEmpty()) {
              stepList = arr[6].trim();
            }

            // 解析 关键步骤
            if (!arr[7].trim().isEmpty()) {
              keyStep = Integer.parseInt(arr[7].trim());
            }

            // 解析 提前完成类型
            if (!arr[8].trim().isEmpty()) {
              vanishType = Integer.parseInt(arr[8].trim());
            }

            // 解析 提前完成参数
            if (!arr[9].trim().isEmpty()) {
              vanishPara = arr[9].trim();
            }

            // 解析 引导是否可以跳过
            if (!arr[10].trim().isEmpty()) {
              canSkip = Integer.parseInt(arr[10].trim());
            }

            // 解析 引导权重
            if (!arr[11].trim().isEmpty()) {
              weight = Integer.parseInt(arr[11].trim());
            }

            // 解析 是否是单机
            if (!arr[12].trim().isEmpty()) {
              isLocal = Integer.parseInt(arr[12].trim());
            }

            // 解析 是否自动触发
            if (!arr[13].trim().isEmpty()) {
              autoTrigger = Integer.parseInt(arr[13].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuideMainConfig config = new GuideMainConfig(id, beizhu, checkNext, guideCond, guidePara1, isMask, stepList, keyStep, vanishType, vanishPara, canSkip, weight, isLocal, autoTrigger);
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

    public List<GuideMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuideMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guideMain.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
