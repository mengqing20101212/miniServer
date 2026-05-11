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
 * File: DreamMainConfigManager
 */
public class DreamMainConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final DreamMainConfigManager instance = new DreamMainConfigManager();
  private static final DreamMainConfigManagerImpl instanceImplA = new DreamMainConfigManagerImpl();
  private static final DreamMainConfigManagerImpl instanceImplB = new DreamMainConfigManagerImpl();

  public static DreamMainConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static DreamMainConfigManagerImpl getStandby() {
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
    DreamMainConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class DreamMainConfigManagerImpl extends AbstractConfigManger {
    private List<DreamMainConfig> configList = List.of();
    private Map<Integer, DreamMainConfig> configMap = Map.of();

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
      DreamMainConfigChecker checker = new DreamMainConfigChecker();
      checker.checkHeader(logger, configDir);
      List<DreamMainConfig> newList = new ArrayList<>();
      Map<Integer, DreamMainConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 12) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String name = null;
          int nameResource = 0;
          int titleResource = 0;
          int effectResource = 0;
          int type = 0;
          int ticketId = 0;
          int ticketNum = 0;
          String eventPool = null;
          int bgResource = 0;
          String rewardList = null;
          String dropShow = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 名字
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 名字资源id
            if (!arr[2].trim().isEmpty()) {
              nameResource = Integer.parseInt(arr[2].trim());
            }

            // 解析 标题资源id
            if (!arr[3].trim().isEmpty()) {
              titleResource = Integer.parseInt(arr[3].trim());
            }

            // 解析 特效资源id
            if (!arr[4].trim().isEmpty()) {
              effectResource = Integer.parseInt(arr[4].trim());
            }

            // 解析 类型
            if (!arr[5].trim().isEmpty()) {
              type = Integer.parseInt(arr[5].trim());
            }

            // 解析 需要门票
            if (!arr[6].trim().isEmpty()) {
              ticketId = Integer.parseInt(arr[6].trim());
            }

            // 解析 门票数量
            if (!arr[7].trim().isEmpty()) {
              ticketNum = Integer.parseInt(arr[7].trim());
            }

            // 解析 事件随机池
            if (!arr[8].trim().isEmpty()) {
              eventPool = arr[8].trim();
            }

            // 解析 背景图
            if (!arr[9].trim().isEmpty()) {
              bgResource = Integer.parseInt(arr[9].trim());
            }

            // 解析 奖励预览
            if (!arr[10].trim().isEmpty()) {
              rewardList = arr[10].trim();
            }

            // 解析 新奖励预览
            if (!arr[11].trim().isEmpty()) {
              dropShow = arr[11].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          DreamMainConfig config = new DreamMainConfig(id, name, nameResource, titleResource, effectResource, type, ticketId, ticketNum, eventPool, bgResource, rewardList, dropShow);
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

    public List<DreamMainConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, DreamMainConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "dreamMain.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
