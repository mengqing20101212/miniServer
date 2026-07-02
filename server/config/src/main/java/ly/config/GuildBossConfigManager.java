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
 * File: GuildBossConfigManager
 */
public class GuildBossConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildBossConfigManager instance = new GuildBossConfigManager();
  private static final GuildBossConfigManagerImpl instanceImplA = new GuildBossConfigManagerImpl();
  private static final GuildBossConfigManagerImpl instanceImplB = new GuildBossConfigManagerImpl();

  public static GuildBossConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuildBossConfigManagerImpl getStandby() {
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
    GuildBossConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuildBossConfigManagerImpl extends AbstractConfigManger {
    private List<GuildBossConfig> configList = List.of();
    private Map<Integer, GuildBossConfig> configMap = Map.of();

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
      GuildBossConfigChecker checker = new GuildBossConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuildBossConfig> newList = new ArrayList<>();
      Map<Integer, GuildBossConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 15) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int sceneId = 0;
          int level = 0;
          int group = 0;
          int icon = 0;
          String battleReward = null;
          int killReward = 0;
          String disPlayBattleReward = null;
          String disPlayKillReward = null;
          String bossDesc = null;
          String battleDesc = null;
          String name = null;
          int bossId = 0;
          String bossSkill = null;
          int bossDamageStatisticsType = 0;
          try {
            // 解析 状态编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 对应关卡
            if (!arr[1].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[1].trim());
            }

            // 解析 BOSS等级
            if (!arr[2].trim().isEmpty()) {
              level = Integer.parseInt(arr[2].trim());
            }

            // 解析 BOSS组ID
            if (!arr[3].trim().isEmpty()) {
              group = Integer.parseInt(arr[3].trim());
            }

            // 解析 boss半身像
            if (!arr[4].trim().isEmpty()) {
              icon = Integer.parseInt(arr[4].trim());
            }

            // 解析 战斗奖励
            if (!arr[5].trim().isEmpty()) {
              battleReward = arr[5].trim();
            }

            // 解析 击败奖励
            if (!arr[6].trim().isEmpty()) {
              killReward = Integer.parseInt(arr[6].trim());
            }

            // 解析 前端展示战斗奖励
            if (!arr[7].trim().isEmpty()) {
              disPlayBattleReward = arr[7].trim();
            }

            // 解析 前端展示击败奖励
            if (!arr[8].trim().isEmpty()) {
              disPlayKillReward = arr[8].trim();
            }

            // 解析 BOSS特性
            if (!arr[9].trim().isEmpty()) {
              bossDesc = arr[9].trim();
            }

            // 解析 战斗限制
            if (!arr[10].trim().isEmpty()) {
              battleDesc = arr[10].trim();
            }

            // 解析 boss名字
            if (!arr[11].trim().isEmpty()) {
              name = arr[11].trim();
            }

            // 解析 bossId
            if (!arr[12].trim().isEmpty()) {
              bossId = Integer.parseInt(arr[12].trim());
            }

            // 解析 boss技能
            if (!arr[13].trim().isEmpty()) {
              bossSkill = arr[13].trim();
            }

            // 解析 BOSS伤害计算
            if (!arr[14].trim().isEmpty()) {
              bossDamageStatisticsType = Integer.parseInt(arr[14].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuildBossConfig config = new GuildBossConfig(id, sceneId, level, group, icon, battleReward, killReward, disPlayBattleReward, disPlayKillReward, bossDesc, battleDesc, name, bossId, bossSkill, bossDamageStatisticsType);
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

    public List<GuildBossConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildBossConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildBoss.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
