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
 * File: AbnormalStateConfigManager
 */
public class AbnormalStateConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final AbnormalStateConfigManager instance = new AbnormalStateConfigManager();
  private static final AbnormalStateConfigManagerImpl instanceImplA = new AbnormalStateConfigManagerImpl();
  private static final AbnormalStateConfigManagerImpl instanceImplB = new AbnormalStateConfigManagerImpl();

  public static AbnormalStateConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static AbnormalStateConfigManagerImpl getStandby() {
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
    AbnormalStateConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class AbnormalStateConfigManagerImpl extends AbstractConfigManger {
    private List<AbnormalStateConfig> configList = List.of();
    private Map<Integer, AbnormalStateConfig> configMap = Map.of();

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
      AbnormalStateConfigChecker checker = new AbnormalStateConfigChecker();
      checker.checkHeader(logger, configDir);
      List<AbnormalStateConfig> newList = new ArrayList<>();
      Map<Integer, AbnormalStateConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 24) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int type = 0;
          int canReTrigger = 0;
          int playAni = 0;
          int playShowAni = 0;
          int skipTurn = 0;
          int banEnergySkill = 0;
          int banPassiveSkill = 0;
          int banSSkill = 0;
          int canTriggerSkill = 0;
          int selectType = 0;
          String dispelList = null;
          String preventList = null;
          int isPlayAnim = 0;
          String stateAnim = null;
          String stateDead = null;
          String stateStart = null;
          String stateFinish = null;
          String startEffect = null;
          String runEffect = null;
          String endEffect = null;
          int statePriority = 0;
          int damageHitPro = 0;
          try {
            // 解析 状态编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 状态名
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 类型
            if (!arr[2].trim().isEmpty()) {
              type = Integer.parseInt(arr[2].trim());
            }

            // 解析 类型功能是否触发多次
            if (!arr[3].trim().isEmpty()) {
              canReTrigger = Integer.parseInt(arr[3].trim());
            }

            // 解析 进入状态后是否播其他动作
            if (!arr[4].trim().isEmpty()) {
              playAni = Integer.parseInt(arr[4].trim());
            }

            // 解析 进入状态后是否播展示动作
            if (!arr[5].trim().isEmpty()) {
              playShowAni = Integer.parseInt(arr[5].trim());
            }

            // 解析 跳过回合
            if (!arr[6].trim().isEmpty()) {
              skipTurn = Integer.parseInt(arr[6].trim());
            }

            // 解析 是否封主动
            if (!arr[7].trim().isEmpty()) {
              banEnergySkill = Integer.parseInt(arr[7].trim());
            }

            // 解析 是否封被动
            if (!arr[8].trim().isEmpty()) {
              banPassiveSkill = Integer.parseInt(arr[8].trim());
            }

            // 解析 是否封S技
            if (!arr[9].trim().isEmpty()) {
              banSSkill = Integer.parseInt(arr[9].trim());
            }

            // 解析 进入状态后是否触发技能
            if (!arr[10].trim().isEmpty()) {
              canTriggerSkill = Integer.parseInt(arr[10].trim());
            }

            // 解析 进入状态后选择类型
            if (!arr[11].trim().isEmpty()) {
              selectType = Integer.parseInt(arr[11].trim());
            }

            // 解析 驱散状态列表
            if (!arr[12].trim().isEmpty()) {
              dispelList = arr[12].trim();
            }

            // 解析 阻止状态列表
            if (!arr[13].trim().isEmpty()) {
              preventList = arr[13].trim();
            }

            // 解析 是否有状态动作
            if (!arr[14].trim().isEmpty()) {
              isPlayAnim = Integer.parseInt(arr[14].trim());
            }

            // 解析 状态待机
            if (!arr[15].trim().isEmpty()) {
              stateAnim = arr[15].trim();
            }

            // 解析 状态死亡
            if (!arr[16].trim().isEmpty()) {
              stateDead = arr[16].trim();
            }

            // 解析 开始动作
            if (!arr[17].trim().isEmpty()) {
              stateStart = arr[17].trim();
            }

            // 解析 结束动作
            if (!arr[18].trim().isEmpty()) {
              stateFinish = arr[18].trim();
            }

            // 解析 开始特效
            if (!arr[19].trim().isEmpty()) {
              startEffect = arr[19].trim();
            }

            // 解析 持续特效
            if (!arr[20].trim().isEmpty()) {
              runEffect = arr[20].trim();
            }

            // 解析 结束特效
            if (!arr[21].trim().isEmpty()) {
              endEffect = arr[21].trim();
            }

            // 解析 状态优先级
            if (!arr[22].trim().isEmpty()) {
              statePriority = Integer.parseInt(arr[22].trim());
            }

            // 解析 伤害命中加成
            if (!arr[23].trim().isEmpty()) {
              damageHitPro = Integer.parseInt(arr[23].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          AbnormalStateConfig config = new AbnormalStateConfig(id, name, type, canReTrigger, playAni, playShowAni, skipTurn, banEnergySkill, banPassiveSkill, banSSkill, canTriggerSkill, selectType, dispelList, preventList, isPlayAnim, stateAnim, stateDead, stateStart, stateFinish, startEffect, runEffect, endEffect, statePriority, damageHitPro);
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

    public List<AbnormalStateConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AbnormalStateConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "abnormalState.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
