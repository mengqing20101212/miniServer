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
 * File: CookMainConfigManager
 */
public class CookMainConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CookMainConfigManager instance = new CookMainConfigManager();
  private static final CookMainConfigManagerImpl instanceImplA = new CookMainConfigManagerImpl();
  private static final CookMainConfigManagerImpl instanceImplB = new CookMainConfigManagerImpl();

  public static CookMainConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CookMainConfigManagerImpl getStandby() {
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
    CookMainConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CookMainConfigManagerImpl extends AbstractConfigManger {
    private List<CookMainConfig> configList = List.of();
    private Map<Integer, CookMainConfig> configMap = Map.of();

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
      CookMainConfigChecker checker = new CookMainConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CookMainConfig> newList = new ArrayList<>();
      Map<Integer, CookMainConfig> newMap = new HashMap<>();
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
          int type = 0;
          String name = null;
          int icon = 0;
          int star = 0;
          String recipe = null;
          String cookSkill = null;
          int skillExp = 0;
          int skillIncrease = 0;
          String baseReward = null;
          int activityId = 0;
          int saitamaExp = 0;
          int dropId = 0;
          String word = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 类型
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 name
            if (!arr[2].trim().isEmpty()) {
              name = arr[2].trim();
            }

            // 解析 料理图标
            if (!arr[3].trim().isEmpty()) {
              icon = Integer.parseInt(arr[3].trim());
            }

            // 解析 星级
            if (!arr[4].trim().isEmpty()) {
              star = Integer.parseInt(arr[4].trim());
            }

            // 解析 配方
            if (!arr[5].trim().isEmpty()) {
              recipe = arr[5].trim();
            }

            // 解析 所需技巧
            if (!arr[6].trim().isEmpty()) {
              cookSkill = arr[6].trim();
            }

            // 解析 制作后技巧增加值
            if (!arr[7].trim().isEmpty()) {
              skillExp = Integer.parseInt(arr[7].trim());
            }

            // 解析 食谱增加技巧值
            if (!arr[8].trim().isEmpty()) {
              skillIncrease = Integer.parseInt(arr[8].trim());
            }

            // 解析 基础奖励
            if (!arr[9].trim().isEmpty()) {
              baseReward = arr[9].trim();
            }

            // 解析 关联活动id
            if (!arr[10].trim().isEmpty()) {
              activityId = Integer.parseInt(arr[10].trim());
            }

            // 解析 奖励埼玉好感度值
            if (!arr[11].trim().isEmpty()) {
              saitamaExp = Integer.parseInt(arr[11].trim());
            }

            // 解析 实际奖励id
            if (!arr[12].trim().isEmpty()) {
              dropId = Integer.parseInt(arr[12].trim());
            }

            // 解析 描述
            if (!arr[13].trim().isEmpty()) {
              word = arr[13].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CookMainConfig config = new CookMainConfig(id, type, name, icon, star, recipe, cookSkill, skillExp, skillIncrease, baseReward, activityId, saitamaExp, dropId, word);
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

    public List<CookMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CookMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "cookMain.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
