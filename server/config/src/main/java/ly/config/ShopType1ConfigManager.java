package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import ly.utils.KV;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: ShopType1ConfigManager
 */
public class ShopType1ConfigManager implements InterfaceConfigManagerProxy {
  public static ShopType1ConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(ShopType1ConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new ShopType1ConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "shopType1.txt"; }

  public static class ShopType1ConfigManagerImpl extends AbstractConfigManger {
    private List<ShopType1Config> configList = List.of();
    private Map<Integer, ShopType1Config> configMap = Map.of();

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
      ShopType1ConfigChecker checker = new ShopType1ConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ShopType1Config> newList = new ArrayList<>();
      Map<Integer, ShopType1Config> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 9) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int type = 0;
          String subShopList = null;
          int openType = 0;
          int openPara1 = 0;
          int openPara2 = 0;
          int isShow = 0;
          int showSort = 0;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 商店名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 商店类别
            if (!arr[2].trim().isEmpty()) {
              type = Integer.parseInt(arr[2].trim());
            }

            // 解析 子商店列表
            if (!arr[3].trim().isEmpty()) {
              subShopList = arr[3].trim();
            }

            // 解析 商店开启条件类型
            if (!arr[4].trim().isEmpty()) {
              openType = Integer.parseInt(arr[4].trim());
            }

            // 解析 开启参数1
            if (!arr[5].trim().isEmpty()) {
              openPara1 = Integer.parseInt(arr[5].trim());
            }

            // 解析 开启参数2
            if (!arr[6].trim().isEmpty()) {
              openPara2 = Integer.parseInt(arr[6].trim());
            }

            // 解析 是否出现在商店里
            if (!arr[7].trim().isEmpty()) {
              isShow = Integer.parseInt(arr[7].trim());
            }

            // 解析 显示排序
            if (!arr[8].trim().isEmpty()) {
              showSort = Integer.parseInt(arr[8].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ShopType1Config config = new ShopType1Config(id, name, type, subShopList, openType, openPara1, openPara2, isShow, showSort);
          config.afterLoad();
          newList.add(config);
          newMap.put(config.id, config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
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

    public List<ShopType1Config> getConfigList() {
      return configList;
    }

    public Map<Integer, ShopType1Config> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "shopType1.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
