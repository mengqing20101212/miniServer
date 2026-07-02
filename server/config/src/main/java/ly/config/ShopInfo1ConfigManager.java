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
 * File: ShopInfo1ConfigManager
 */
public class ShopInfo1ConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ShopInfo1ConfigManager instance = new ShopInfo1ConfigManager();
  private static final ShopInfo1ConfigManagerImpl instanceImplA = new ShopInfo1ConfigManagerImpl();
  private static final ShopInfo1ConfigManagerImpl instanceImplB = new ShopInfo1ConfigManagerImpl();

  public static ShopInfo1ConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ShopInfo1ConfigManagerImpl getStandby() {
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
    ShopInfo1ConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ShopInfo1ConfigManagerImpl extends AbstractConfigManger {
    private List<ShopInfo1Config> configList = List.of();
    private Map<Integer, ShopInfo1Config> configMap = Map.of();

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
      ShopInfo1ConfigChecker checker = new ShopInfo1ConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ShopInfo1Config> newList = new ArrayList<>();
      Map<Integer, ShopInfo1Config> newMap = new HashMap<>();
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
          int grade = 0;
          int school = 0;
          String name = null;
          int type = 0;
          int showType = 0;
          String shopPara = null;
          String commodityList = null;
          int openType = 0;
          int RefreshBarType = 0;
          int openPara1 = 0;
          int openPara2 = 0;
          int openType2 = 0;
          int openPara3 = 0;
          int topId = 0;
          int background = 0;
          int subStoreShow = 0;
          int shopShow = 0;
          try {
            // 解析 子商店ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 一级商店id
            if (!arr[1].trim().isEmpty()) {
              grade = Integer.parseInt(arr[1].trim());
            }

            // 解析 二级商店id
            if (!arr[2].trim().isEmpty()) {
              school = Integer.parseInt(arr[2].trim());
            }

            // 解析 子商店名称
            if (!arr[3].trim().isEmpty()) {
              name = arr[3].trim();
            }

            // 解析 商店类别
            if (!arr[4].trim().isEmpty()) {
              type = Integer.parseInt(arr[4].trim());
            }

            // 解析 商店展示类型
            if (!arr[5].trim().isEmpty()) {
              showType = Integer.parseInt(arr[5].trim());
            }

            // 解析 商店参数
            if (!arr[6].trim().isEmpty()) {
              shopPara = arr[6].trim();
            }

            // 解析 商品列表
            if (!arr[7].trim().isEmpty()) {
              commodityList = arr[7].trim();
            }

            // 解析 子商店开启条件类型
            if (!arr[8].trim().isEmpty()) {
              openType = Integer.parseInt(arr[8].trim());
            }

            // 解析 刷新栏类型
            if (!arr[9].trim().isEmpty()) {
              RefreshBarType = Integer.parseInt(arr[9].trim());
            }

            // 解析 开启参数1
            if (!arr[10].trim().isEmpty()) {
              openPara1 = Integer.parseInt(arr[10].trim());
            }

            // 解析 开启参数2
            if (!arr[11].trim().isEmpty()) {
              openPara2 = Integer.parseInt(arr[11].trim());
            }

            // 解析 子商店开启条件类型2
            if (!arr[12].trim().isEmpty()) {
              openType2 = Integer.parseInt(arr[12].trim());
            }

            // 解析 开启参数1
            if (!arr[13].trim().isEmpty()) {
              openPara3 = Integer.parseInt(arr[13].trim());
            }

            // 解析 TOP表内id
            if (!arr[14].trim().isEmpty()) {
              topId = Integer.parseInt(arr[14].trim());
            }

            // 解析 背景框资源id
            if (!arr[15].trim().isEmpty()) {
              background = Integer.parseInt(arr[15].trim());
            }

            // 解析 无商品是否显示
            if (!arr[16].trim().isEmpty()) {
              subStoreShow = Integer.parseInt(arr[16].trim());
            }

            // 解析 是否显示商店
            if (!arr[17].trim().isEmpty()) {
              shopShow = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ShopInfo1Config config = new ShopInfo1Config(id, grade, school, name, type, showType, shopPara, commodityList, openType, RefreshBarType, openPara1, openPara2, openType2, openPara3, topId, background, subStoreShow, shopShow);
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

    public List<ShopInfo1Config> getConfigList() {
      return configList;
    }

    public Map<Integer, ShopInfo1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "shopInfo1.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
