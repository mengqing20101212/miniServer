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
 * File: CookMaterialConfigManager
 */
public class CookMaterialConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final CookMaterialConfigManager instance = new CookMaterialConfigManager();
  private static final CookMaterialConfigManagerImpl instanceImplA = new CookMaterialConfigManagerImpl();
  private static final CookMaterialConfigManagerImpl instanceImplB = new CookMaterialConfigManagerImpl();

  public static CookMaterialConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static CookMaterialConfigManagerImpl getStandby() {
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
    CookMaterialConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class CookMaterialConfigManagerImpl extends AbstractConfigManger {
    private List<CookMaterialConfig> configList = List.of();
    private Map<Integer, CookMaterialConfig> configMap = Map.of();

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
      CookMaterialConfigChecker checker = new CookMaterialConfigChecker();
      checker.checkHeader(logger, configDir);
      List<CookMaterialConfig> newList = new ArrayList<>();
      Map<Integer, CookMaterialConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 18) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int type = 0;
          int classify = 0;
          int para = 0;
          int star = 0;
          int showId = 0;
          int price = 0;
          String note = null;
          int rewardId = 0;
          int rewardNum = 0;
          int friendNum = 0;
          int critRewardNum = 0;
          int critFriendNum = 0;
          int exRewardNum = 0;
          int exFriendNum = 0;
          String qteRewardNum = null;
          String qteFriendNum = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 类型
            if (!arr[2].trim().isEmpty()) {
              type = Integer.parseInt(arr[2].trim());
            }

            // 解析 种类
            if (!arr[3].trim().isEmpty()) {
              classify = Integer.parseInt(arr[3].trim());
            }

            // 解析 参数
            if (!arr[4].trim().isEmpty()) {
              para = Integer.parseInt(arr[4].trim());
            }

            // 解析 星级
            if (!arr[5].trim().isEmpty()) {
              star = Integer.parseInt(arr[5].trim());
            }

            // 解析 展示顺序
            if (!arr[6].trim().isEmpty()) {
              showId = Integer.parseInt(arr[6].trim());
            }

            // 解析 价值量
            if (!arr[7].trim().isEmpty()) {
              price = Integer.parseInt(arr[7].trim());
            }

            // 解析 作用描述
            if (!arr[8].trim().isEmpty()) {
              note = arr[8].trim();
            }

            // 解析 奖励id
            if (!arr[9].trim().isEmpty()) {
              rewardId = Integer.parseInt(arr[9].trim());
            }

            // 解析 奖励数量
            if (!arr[10].trim().isEmpty()) {
              rewardNum = Integer.parseInt(arr[10].trim());
            }

            // 解析 好感度值
            if (!arr[11].trim().isEmpty()) {
              friendNum = Integer.parseInt(arr[11].trim());
            }

            // 解析 暴击增加奖励数量
            if (!arr[12].trim().isEmpty()) {
              critRewardNum = Integer.parseInt(arr[12].trim());
            }

            // 解析 暴击好感度增加值
            if (!arr[13].trim().isEmpty()) {
              critFriendNum = Integer.parseInt(arr[13].trim());
            }

            // 解析 口味奖励数量
            if (!arr[14].trim().isEmpty()) {
              exRewardNum = Integer.parseInt(arr[14].trim());
            }

            // 解析 口味奖励好感度
            if (!arr[15].trim().isEmpty()) {
              exFriendNum = Integer.parseInt(arr[15].trim());
            }

            // 解析 qte奖励数量
            if (!arr[16].trim().isEmpty()) {
              qteRewardNum = arr[16].trim();
            }

            // 解析 qte奖励好感度
            if (!arr[17].trim().isEmpty()) {
              qteFriendNum = arr[17].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          CookMaterialConfig config = new CookMaterialConfig(id, name, type, classify, para, star, showId, price, note, rewardId, rewardNum, friendNum, critRewardNum, critFriendNum, exRewardNum, exFriendNum, qteRewardNum, qteFriendNum);
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

    public List<CookMaterialConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, CookMaterialConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "cookMaterial.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
