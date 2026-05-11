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
 * File: BattleFieldConfigManager
 */
public class BattleFieldConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final BattleFieldConfigManager instance = new BattleFieldConfigManager();
  private static final BattleFieldConfigManagerImpl instanceImplA = new BattleFieldConfigManagerImpl();
  private static final BattleFieldConfigManagerImpl instanceImplB = new BattleFieldConfigManagerImpl();

  public static BattleFieldConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static BattleFieldConfigManagerImpl getStandby() {
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
    BattleFieldConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class BattleFieldConfigManagerImpl extends AbstractConfigManger {
    private List<BattleFieldConfig> configList = List.of();
    private Map<Integer, BattleFieldConfig> configMap = Map.of();

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
      BattleFieldConfigChecker checker = new BattleFieldConfigChecker();
      checker.checkHeader(logger, configDir);
      List<BattleFieldConfig> newList = new ArrayList<>();
      Map<Integer, BattleFieldConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 16) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String posListRed = null;
          String posListBlue = null;
          String frontAllRed = null;
          String frontAllBlue = null;
          String BFCenterRed = null;
          String BFCenterBlue = null;
          String BFCenter = null;
          String cameraListRed = null;
          String cameraListBlue = null;
          String posListRedSummon = null;
          String posListBlueSummon = null;
          String sceneConfig = null;
          String sceneConfigEnemy = null;
          int lineupType = 0;
          int lineupTypeEnemy = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 我方点位List
            if (!arr[1].trim().isEmpty()) {
              posListRed = arr[1].trim();
            }

            // 解析 敌方点位List
            if (!arr[2].trim().isEmpty()) {
              posListBlue = arr[2].trim();
            }

            // 解析 我方全体前
            if (!arr[3].trim().isEmpty()) {
              frontAllRed = arr[3].trim();
            }

            // 解析 敌方全体前
            if (!arr[4].trim().isEmpty()) {
              frontAllBlue = arr[4].trim();
            }

            // 解析 我方战场中心
            if (!arr[5].trim().isEmpty()) {
              BFCenterRed = arr[5].trim();
            }

            // 解析 敌方战场中心
            if (!arr[6].trim().isEmpty()) {
              BFCenterBlue = arr[6].trim();
            }

            // 解析 战场中心
            if (!arr[7].trim().isEmpty()) {
              BFCenter = arr[7].trim();
            }

            // 解析 选择目标镜头list我方
            if (!arr[8].trim().isEmpty()) {
              cameraListRed = arr[8].trim();
            }

            // 解析 选择目标镜头list敌方
            if (!arr[9].trim().isEmpty()) {
              cameraListBlue = arr[9].trim();
            }

            // 解析 我方点位List
            if (!arr[10].trim().isEmpty()) {
              posListRedSummon = arr[10].trim();
            }

            // 解析 敌方点位List
            if (!arr[11].trim().isEmpty()) {
              posListBlueSummon = arr[11].trim();
            }

            // 解析 场景镜头配置
            if (!arr[12].trim().isEmpty()) {
              sceneConfig = arr[12].trim();
            }

            // 解析 场景配置敌方
            if (!arr[13].trim().isEmpty()) {
              sceneConfigEnemy = arr[13].trim();
            }

            // 解析 我方站位连线类型
            if (!arr[14].trim().isEmpty()) {
              lineupType = Integer.parseInt(arr[14].trim());
            }

            // 解析 敌方站位连线类型
            if (!arr[15].trim().isEmpty()) {
              lineupTypeEnemy = Integer.parseInt(arr[15].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          BattleFieldConfig config = new BattleFieldConfig(id, posListRed, posListBlue, frontAllRed, frontAllBlue, BFCenterRed, BFCenterBlue, BFCenter, cameraListRed, cameraListBlue, posListRedSummon, posListBlueSummon, sceneConfig, sceneConfigEnemy, lineupType, lineupTypeEnemy);
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

    public List<BattleFieldConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleFieldConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleField.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
