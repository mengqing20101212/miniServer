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
 * File: TurnConfigManager
 */
public class TurnConfigManager implements InterfaceConfigManagerProxy {
  public static TurnConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(TurnConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new TurnConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "turn.txt"; }

  public static class TurnConfigManagerImpl extends AbstractConfigManger {
    private List<TurnConfig> configList = List.of();
    private Map<Integer, TurnConfig> configMap = Map.of();

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
      TurnConfigChecker checker = new TurnConfigChecker();
      checker.checkHeader(logger, configDir);
      List<TurnConfig> newList = new ArrayList<>();
      Map<Integer, TurnConfig> newMap = new HashMap<>();
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
          String description = null;
          int turnType = 0;
          String uiMsg = null;
          int type = 0;
          int type2 = 0;
          int isNet = 0;
          String param = null;
          String param2 = null;
          int activityId = 0;
          int guideId = 0;
          String frontUiMsg = null;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 描述
            if (!arr[1].trim().isEmpty()) {
              description = arr[1].trim();
            }

            // 解析 类型
            if (!arr[2].trim().isEmpty()) {
              turnType = Integer.parseInt(arr[2].trim());
            }

            // 解析 打开的uimsg
            if (!arr[3].trim().isEmpty()) {
              uiMsg = arr[3].trim();
            }

            // 解析 类型
            if (!arr[4].trim().isEmpty()) {
              type = Integer.parseInt(arr[4].trim());
            }

            // 解析 类型2
            if (!arr[5].trim().isEmpty()) {
              type2 = Integer.parseInt(arr[5].trim());
            }

            // 解析 是否要前置消息
            if (!arr[6].trim().isEmpty()) {
              isNet = Integer.parseInt(arr[6].trim());
            }

            // 解析 参数
            if (!arr[7].trim().isEmpty()) {
              param = arr[7].trim();
            }

            // 解析 参数2()目前没用
            if (!arr[8].trim().isEmpty()) {
              param2 = arr[8].trim();
            }

            // 解析 限制活动id
            if (!arr[9].trim().isEmpty()) {
              activityId = Integer.parseInt(arr[9].trim());
            }

            // 解析 引导组ID
            if (!arr[10].trim().isEmpty()) {
              guideId = Integer.parseInt(arr[10].trim());
            }

            // 解析 引导未完成打开的uimsg
            if (!arr[11].trim().isEmpty()) {
              frontUiMsg = arr[11].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          TurnConfig config = new TurnConfig(id, description, turnType, uiMsg, type, type2, isNet, param, param2, activityId, guideId, frontUiMsg);
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

    public List<TurnConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, TurnConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "turn.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
