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
 * File: AlertContractConfigManager
 */
public class AlertContractConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final AlertContractConfigManager instance = new AlertContractConfigManager();
  private static final AlertContractConfigManagerImpl instanceImplA = new AlertContractConfigManagerImpl();
  private static final AlertContractConfigManagerImpl instanceImplB = new AlertContractConfigManagerImpl();

  public static AlertContractConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static AlertContractConfigManagerImpl getStandby() {
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
    AlertContractConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class AlertContractConfigManagerImpl extends AbstractConfigManger {
    private List<AlertContractConfig> configList = List.of();
    private Map<Integer, AlertContractConfig> configMap = Map.of();

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
      AlertContractConfigChecker checker = new AlertContractConfigChecker();
      checker.checkHeader(logger, configDir);
      List<AlertContractConfig> newList = new ArrayList<>();
      Map<Integer, AlertContractConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 20) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int level = 0;
          String enemyInfo = null;
          String enemyCircuitInfo = null;
          String enemyPara = null;
          String sceneId = null;
          int dropId = 0;
          int firstDrop = 0;
          String dropShow = null;
          String hint = null;
          int tipPic = 0;
          int background = 0;
          int backgroundIn = 0;
          int heroPicId = 0;
          String recommendHeroIds = null;
          String recommendTypes = null;
          int avgLineupLevel = 0;
          int dropDay = 0;
          int dropDayShow = 0;
          int targetLevel = 0;
          try {
            // 解析 关卡组id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 层级
            if (!arr[1].trim().isEmpty()) {
              level = Integer.parseInt(arr[1].trim());
            }

            // 解析 敌人数值信息
            if (!arr[2].trim().isEmpty()) {
              enemyInfo = arr[2].trim();
            }

            // 解析 敌人源核信息
            if (!arr[3].trim().isEmpty()) {
              enemyCircuitInfo = arr[3].trim();
            }

            // 解析 敌人系数
            if (!arr[4].trim().isEmpty()) {
              enemyPara = arr[4].trim();
            }

            // 解析 实际关卡id
            if (!arr[5].trim().isEmpty()) {
              sceneId = arr[5].trim();
            }

            // 解析 奖励id
            if (!arr[6].trim().isEmpty()) {
              dropId = Integer.parseInt(arr[6].trim());
            }

            // 解析 首通奖励
            if (!arr[7].trim().isEmpty()) {
              firstDrop = Integer.parseInt(arr[7].trim());
            }

            // 解析 奖励展示
            if (!arr[8].trim().isEmpty()) {
              dropShow = arr[8].trim();
            }

            // 解析 关卡提示
            if (!arr[9].trim().isEmpty()) {
              hint = arr[9].trim();
            }

            // 解析 扫荡图片背景图片
            if (!arr[10].trim().isEmpty()) {
              tipPic = Integer.parseInt(arr[10].trim());
            }

            // 解析 背景图片（主界面）
            if (!arr[11].trim().isEmpty()) {
              background = Integer.parseInt(arr[11].trim());
            }

            // 解析 背景图片（关卡内布阵和敌人）
            if (!arr[12].trim().isEmpty()) {
              backgroundIn = Integer.parseInt(arr[12].trim());
            }

            // 解析 英雄立绘资源id
            if (!arr[13].trim().isEmpty()) {
              heroPicId = Integer.parseInt(arr[13].trim());
            }

            // 解析 推荐英雄Id组(,)
            if (!arr[14].trim().isEmpty()) {
              recommendHeroIds = arr[14].trim();
            }

            // 解析 推荐类型显示组(1辅 2 群  3单  4控)
            if (!arr[15].trim().isEmpty()) {
              recommendTypes = arr[15].trim();
            }

            // 解析 推荐阵容平均等级
            if (!arr[16].trim().isEmpty()) {
              avgLineupLevel = Integer.parseInt(arr[16].trim());
            }

            // 解析 每日奖励
            if (!arr[17].trim().isEmpty()) {
              dropDay = Integer.parseInt(arr[17].trim());
            }

            // 解析 每日奖励展示
            if (!arr[18].trim().isEmpty()) {
              dropDayShow = Integer.parseInt(arr[18].trim());
            }

            // 解析 阶段层数
            if (!arr[19].trim().isEmpty()) {
              targetLevel = Integer.parseInt(arr[19].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          AlertContractConfig config = new AlertContractConfig(id, level, enemyInfo, enemyCircuitInfo, enemyPara, sceneId, dropId, firstDrop, dropShow, hint, tipPic, background, backgroundIn, heroPicId, recommendHeroIds, recommendTypes, avgLineupLevel, dropDay, dropDayShow, targetLevel);
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

    public List<AlertContractConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AlertContractConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "AlertContract.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
