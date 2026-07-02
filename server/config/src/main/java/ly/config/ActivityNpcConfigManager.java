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
 * File: ActivityNpcConfigManager
 */
public class ActivityNpcConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ActivityNpcConfigManager instance = new ActivityNpcConfigManager();
  private static final ActivityNpcConfigManagerImpl instanceImplA = new ActivityNpcConfigManagerImpl();
  private static final ActivityNpcConfigManagerImpl instanceImplB = new ActivityNpcConfigManagerImpl();

  public static ActivityNpcConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ActivityNpcConfigManagerImpl getStandby() {
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
    ActivityNpcConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ActivityNpcConfigManagerImpl extends AbstractConfigManger {
    private List<ActivityNpcConfig> configList = List.of();
    private Map<Integer, ActivityNpcConfig> configMap = Map.of();

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
      ActivityNpcConfigChecker checker = new ActivityNpcConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ActivityNpcConfig> newList = new ArrayList<>();
      Map<Integer, ActivityNpcConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 19) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String config_name = null;
          String characterModelId = null;
          String defaultAni = null;
          String bornShowAni = null;
          String clickType = null;
          String clickAniList = null;
          String clickCameraList = null;
          String moveDistance = null;
          String cameraDistance = null;
          String clickText = null;
          String groupText = null;
          String ActivityType = null;
          String param_1 = null;
          String param_2 = null;
          String npcGrounpId = null;
          String showPriority = null;
          String decorationId = null;
          String decorationPoint = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 配置名字辅助列
            if (!arr[1].trim().isEmpty()) {
              config_name = arr[1].trim();
            }

            // 解析 活动名称
            if (!arr[2].trim().isEmpty()) {
              characterModelId = arr[2].trim();
            }

            // 解析 默认动作
            if (!arr[3].trim().isEmpty()) {
              defaultAni = arr[3].trim();
            }

            // 解析 欢迎动作
            if (!arr[4].trim().isEmpty()) {
              bornShowAni = arr[4].trim();
            }

            // 解析 点击反馈类型(0-不可点，1-通用点击逻辑，2-特殊
            if (!arr[5].trim().isEmpty()) {
              clickType = arr[5].trim();
            }

            // 解析 点击反馈动作组
            if (!arr[6].trim().isEmpty()) {
              clickAniList = arr[6].trim();
            }

            // 解析 点击反馈镜头组
            if (!arr[7].trim().isEmpty()) {
              clickCameraList = arr[7].trim();
            }

            // 解析 推镜的镜头左右偏移（正是左负是右,左右是角度，上下是距离）
            if (!arr[8].trim().isEmpty()) {
              moveDistance = arr[8].trim();
            }

            // 解析 推镜的距离配置
            if (!arr[9].trim().isEmpty()) {
              cameraDistance = arr[9].trim();
            }

            // 解析 点击反馈文本
            if (!arr[10].trim().isEmpty()) {
              clickText = arr[10].trim();
            }

            // 解析 组合动作的文本
            if (!arr[11].trim().isEmpty()) {
              groupText = arr[11].trim();
            }

            // 解析 特殊功能类型
            if (!arr[12].trim().isEmpty()) {
              ActivityType = arr[12].trim();
            }

            // 解析 特殊功能参数1
            if (!arr[13].trim().isEmpty()) {
              param_1 = arr[13].trim();
            }

            // 解析 特殊功能参数2
            if (!arr[14].trim().isEmpty()) {
              param_2 = arr[14].trim();
            }

            // 解析 角色组合id
            if (!arr[15].trim().isEmpty()) {
              npcGrounpId = arr[15].trim();
            }

            // 解析 主城显示优先级
            if (!arr[16].trim().isEmpty()) {
              showPriority = arr[16].trim();
            }

            // 解析 装饰物资源id
            if (!arr[17].trim().isEmpty()) {
              decorationId = arr[17].trim();
            }

            // 解析 资源物挂点
            if (!arr[18].trim().isEmpty()) {
              decorationPoint = arr[18].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ActivityNpcConfig config = new ActivityNpcConfig(id, config_name, characterModelId, defaultAni, bornShowAni, clickType, clickAniList, clickCameraList, moveDistance, cameraDistance, clickText, groupText, ActivityType, param_1, param_2, npcGrounpId, showPriority, decorationId, decorationPoint);
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

    public List<ActivityNpcConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ActivityNpcConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "activityNpc.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
