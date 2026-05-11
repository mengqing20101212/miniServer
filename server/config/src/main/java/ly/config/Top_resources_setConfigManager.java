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
 * File: Top_resources_setConfigManager
 */
public class Top_resources_setConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final Top_resources_setConfigManager instance = new Top_resources_setConfigManager();
  private static final Top_resources_setConfigManagerImpl instanceImplA = new Top_resources_setConfigManagerImpl();
  private static final Top_resources_setConfigManagerImpl instanceImplB = new Top_resources_setConfigManagerImpl();

  public static Top_resources_setConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static Top_resources_setConfigManagerImpl getStandby() {
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
    Top_resources_setConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class Top_resources_setConfigManagerImpl extends AbstractConfigManger {
    private List<Top_resources_setConfig> configList = List.of();
    private Map<Integer, Top_resources_setConfig> configMap = Map.of();

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
      Top_resources_setConfigChecker checker = new Top_resources_setConfigChecker();
      checker.checkHeader(logger, configDir);
      List<Top_resources_setConfig> newList = new ArrayList<>();
      Map<Integer, Top_resources_setConfig> newMap = new HashMap<>();
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
          String desc = null;
          String title = null;
          int titleTypeJudgment = 0;
          int showtime = 0;
          int titleType = 0;
          String icon = null;
          String titleWord = null;
          String show_type = null;
          String fastTurnID = null;
          String activeType = null;
          int helpTurnId = 0;
          int helpdesc = 0;
          int activityId = 0;
          try {
            // 解析 功能id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 desc
            if (!arr[1].trim().isEmpty()) {
              desc = arr[1].trim();
            }

            // 解析 标题
            if (!arr[2].trim().isEmpty()) {
              title = arr[2].trim();
            }

            // 解析 标题类型
            if (!arr[3].trim().isEmpty()) {
              titleTypeJudgment = Integer.parseInt(arr[3].trim());
            }

            // 解析 是否显示时间
            if (!arr[4].trim().isEmpty()) {
              showtime = Integer.parseInt(arr[4].trim());
            }

            // 解析 字数
            if (!arr[5].trim().isEmpty()) {
              titleType = Integer.parseInt(arr[5].trim());
            }

            // 解析 图标id
            if (!arr[6].trim().isEmpty()) {
              icon = arr[6].trim();
            }

            // 解析 标题文字
            if (!arr[7].trim().isEmpty()) {
              titleWord = arr[7].trim();
            }

            // 解析 显示道具ID
            if (!arr[8].trim().isEmpty()) {
              show_type = arr[8].trim();
            }

            // 解析 加号跳转ID
            if (!arr[9].trim().isEmpty()) {
              fastTurnID = arr[9].trim();
            }

            // 解析 界面显示类型
            if (!arr[10].trim().isEmpty()) {
              activeType = arr[10].trim();
            }

            // 解析 帮助跳转id
            if (!arr[11].trim().isEmpty()) {
              helpTurnId = Integer.parseInt(arr[11].trim());
            }

            // 解析 帮助按钮内容
            if (!arr[12].trim().isEmpty()) {
              helpdesc = Integer.parseInt(arr[12].trim());
            }

            // 解析 关联的活动 Id
            if (!arr[13].trim().isEmpty()) {
              activityId = Integer.parseInt(arr[13].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          Top_resources_setConfig config = new Top_resources_setConfig(id, desc, title, titleTypeJudgment, showtime, titleType, icon, titleWord, show_type, fastTurnID, activeType, helpTurnId, helpdesc, activityId);
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

    public List<Top_resources_setConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Top_resources_setConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "top_resources_set.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
