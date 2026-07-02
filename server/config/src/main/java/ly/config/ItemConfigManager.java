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
 * File: ItemConfigManager
 */
public class ItemConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ItemConfigManager instance = new ItemConfigManager();
  private static final ItemConfigManagerImpl instanceImplA = new ItemConfigManagerImpl();
  private static final ItemConfigManagerImpl instanceImplB = new ItemConfigManagerImpl();

  public static ItemConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ItemConfigManagerImpl getStandby() {
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
    ItemConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ItemConfigManagerImpl extends AbstractConfigManger {
    private List<ItemConfig> configList = List.of();
    private Map<Integer, ItemConfig> configMap = Map.of();

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
      ItemConfigChecker checker = new ItemConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ItemConfig> newList = new ArrayList<>();
      Map<Integer, ItemConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 25) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          String description = null;
          String index = null;
          int icon = 0;
          int subIcon = 0;
          int quality = 0;
          int type = 0;
          int bagTag = 0;
          int school = 0;
          int grade = 0;
          int sequence = 0;
          int stack = 0;
          int existType = 0;
          int existTime = 0;
          int canSell = 0;
          String sellItem = null;
          int onlyServer = 0;
          int knapsackType = 0;
          String accessWay = null;
          int useType = 0;
          int turnId = 0;
          int newTips = 0;
          String func = null;
          int level = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 物品名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 物品描述
            if (!arr[2].trim().isEmpty()) {
              description = arr[2].trim();
            }

            // 解析 索引名
            if (!arr[3].trim().isEmpty()) {
              index = arr[3].trim();
            }

            // 解析 物品图标
            if (!arr[4].trim().isEmpty()) {
              icon = Integer.parseInt(arr[4].trim());
            }

            // 解析 主源核辅助图标
            if (!arr[5].trim().isEmpty()) {
              subIcon = Integer.parseInt(arr[5].trim());
            }

            // 解析 品质
            if (!arr[6].trim().isEmpty()) {
              quality = Integer.parseInt(arr[6].trim());
            }

            // 解析 类型
            if (!arr[7].trim().isEmpty()) {
              type = Integer.parseInt(arr[7].trim());
            }

            // 解析 类型标签
            if (!arr[8].trim().isEmpty()) {
              bagTag = Integer.parseInt(arr[8].trim());
            }

            // 解析 一级分类
            if (!arr[9].trim().isEmpty()) {
              school = Integer.parseInt(arr[9].trim());
            }

            // 解析 二级分类
            if (!arr[10].trim().isEmpty()) {
              grade = Integer.parseInt(arr[10].trim());
            }

            // 解析 三级分类
            if (!arr[11].trim().isEmpty()) {
              sequence = Integer.parseInt(arr[11].trim());
            }

            // 解析 堆叠数量
            if (!arr[12].trim().isEmpty()) {
              stack = Integer.parseInt(arr[12].trim());
            }

            // 解析 存在时间类型
            if (!arr[13].trim().isEmpty()) {
              existType = Integer.parseInt(arr[13].trim());
            }

            // 解析 存在时间
            if (!arr[14].trim().isEmpty()) {
              existTime = Integer.parseInt(arr[14].trim());
            }

            // 解析 是否能出售
            if (!arr[15].trim().isEmpty()) {
              canSell = Integer.parseInt(arr[15].trim());
            }

            // 解析 出售获得物品
            if (!arr[16].trim().isEmpty()) {
              sellItem = arr[16].trim();
            }

            // 解析 是否获得后在服务器使用
            if (!arr[17].trim().isEmpty()) {
              onlyServer = Integer.parseInt(arr[17].trim());
            }

            // 解析 在背包中分类
            if (!arr[18].trim().isEmpty()) {
              knapsackType = Integer.parseInt(arr[18].trim());
            }

            // 解析 获取途径
            if (!arr[19].trim().isEmpty()) {
              accessWay = arr[19].trim();
            }

            // 解析 是否可以使用
            if (!arr[20].trim().isEmpty()) {
              useType = Integer.parseInt(arr[20].trim());
            }

            // 解析 使用跳转
            if (!arr[21].trim().isEmpty()) {
              turnId = Integer.parseInt(arr[21].trim());
            }

            // 解析 新物品提示
            if (!arr[22].trim().isEmpty()) {
              newTips = Integer.parseInt(arr[22].trim());
            }

            // 解析 使用触发函数
            if (!arr[23].trim().isEmpty()) {
              func = arr[23].trim();
            }

            // 解析 级别
            if (!arr[24].trim().isEmpty()) {
              level = Integer.parseInt(arr[24].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ItemConfig config = new ItemConfig(id, name, description, index, icon, subIcon, quality, type, bagTag, school, grade, sequence, stack, existType, existTime, canSell, sellItem, onlyServer, knapsackType, accessWay, useType, turnId, newTips, func, level);
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

    public List<ItemConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, ItemConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "item.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
