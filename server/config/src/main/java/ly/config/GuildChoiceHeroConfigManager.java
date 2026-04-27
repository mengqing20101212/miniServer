package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ly.utils.KV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: GuildChoiceHeroConfigManager
 */
public class GuildChoiceHeroConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildChoiceHeroConfigManager instance = new GuildChoiceHeroConfigManager();
  private static final GuildChoiceHeroConfigManagerImpl instanceImplA =
      new GuildChoiceHeroConfigManagerImpl();
  private static final GuildChoiceHeroConfigManagerImpl instanceImplB =
      new GuildChoiceHeroConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static GuildChoiceHeroConfigManagerImpl getInstance() {
    if (instance.isSwitched()) {
      return instanceImplA;
    } else {
      return instanceImplB;
    }
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  public static class GuildChoiceHeroConfigManagerImpl extends AbstractConfigManger {

    List<GuildChoiceHeroConfig> configList = new ArrayList<GuildChoiceHeroConfig>();
    Map<Integer, GuildChoiceHeroConfig> configMap = new HashMap<Integer, GuildChoiceHeroConfig>();


    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    protected void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      clear();
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        br.readLine(); //先读取一行表头 
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          GuildChoiceHeroConfig config = new GuildChoiceHeroConfig();
          try {
            //解析 状态编号
            if (!arr[0].trim().isEmpty()) {
            config.id =  Integer.parseInt(arr[0].trim());
            }

            //解析 解锁等级
            if (!arr[1].trim().isEmpty()) {
            config.unlcokLevel =  Integer.parseInt(arr[1].trim());
            }

            //解析 英雄ID
            if (!arr[2].trim().isEmpty()) {
            config.npcid =  Integer.parseInt(arr[2].trim());
            }

            //解析 玩家等级
            if (!arr[3].trim().isEmpty()) {
            config.playerLv =  Integer.parseInt(arr[3].trim());
            }

            //解析 关卡ID
            if (!arr[4].trim().isEmpty()) {
            config.sceneId =  Integer.parseInt(arr[4].trim());
            }

            //解析 优先级
            if (!arr[5].trim().isEmpty()) {
            config.priority =  Integer.parseInt(arr[5].trim());
            }

            //解析 参与奖励
            if (!arr[6].trim().isEmpty()) {
            config.reward1 =  Integer.parseInt(arr[6].trim());
            }

            //解析 伤害奖励
            if (!arr[7].trim().isEmpty()) {
            config.reward2 = arr[7].trim();
            }

            //解析 boss技能
            if (!arr[8].trim().isEmpty()) {
            config.bossSkill = arr[8].trim();
            }

            //解析 掉落显示
            if (!arr[9].trim().isEmpty()) {
            config.dropShow =  Integer.parseInt(arr[9].trim());
            }

            //解析 BOSS伤害计算
            if (!arr[10].trim().isEmpty()) {
            config.bossDamageStatisticsType =  Integer.parseInt(arr[10].trim());
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
          configMap.put(config.id, config);
        }
        afterLoad();
      } catch (IOException e) {
        e.printStackTrace();
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    protected void clear() {

      configList.clear();
      configMap.clear();

      // @@@@@自定义clear方法开始区@@@@@


      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        try {
          result.add(Integer.parseInt(part.trim()));
        } catch (NumberFormatException e) {
          // 如果不是数字，则跳过
        }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            try {
              Integer key = Integer.parseInt(keyStr);
              Integer val = Integer.parseInt(valueStr);
              result.add(new KV<>(key, val));
            } catch (NumberFormatException e) {
              // 如果不是数字，则跳过
            }
          }
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<String, String>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            result.add(new KV<>(keyStr, valueStr));
          }
        }
      }
      return result;
    }

    public List<GuildChoiceHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildChoiceHeroConfig> getConfigMap() {
      return configMap;
    }
    @Override
    public String getConfigFileName() {
      return "guildChoiceHero.txt";
    }

    // @@@@@自定义方法开始区@@@@@
    @Override
    protected void afterLoad() {

    }

    // @@@@@自定义方法结束区@@@@@
  }
}
