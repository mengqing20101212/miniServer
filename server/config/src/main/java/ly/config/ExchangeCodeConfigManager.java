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
 * File: ExchangeCodeConfigManager
 */
public class ExchangeCodeConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final ExchangeCodeConfigManager instance = new ExchangeCodeConfigManager();
  private static final ExchangeCodeConfigManagerImpl instanceImplA = new ExchangeCodeConfigManagerImpl();
  private static final ExchangeCodeConfigManagerImpl instanceImplB = new ExchangeCodeConfigManagerImpl();

  public static ExchangeCodeConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static ExchangeCodeConfigManagerImpl getStandby() {
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
    ExchangeCodeConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class ExchangeCodeConfigManagerImpl extends AbstractConfigManger {
    private List<ExchangeCodeConfig> configList = List.of();
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
      ExchangeCodeConfigChecker checker = new ExchangeCodeConfigChecker();
      checker.checkHeader(logger, configDir);
      List<ExchangeCodeConfig> newList = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 10) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int groupId = 0;
          String name = null;
          String beginTime = null;
          String endTime = null;
          int codeNum = 0;
          String channel = null;
          String rewards = null;
          int limit1 = 0;
          int limit2 = 0;
          String limit3 = null;
          try {
            // 解析 自增id
            if (!arr[0].trim().isEmpty()) {
              groupId = Integer.parseInt(arr[0].trim());
            }

            // 解析 礼包名称
            if (!arr[1].trim().isEmpty()) {
              name = arr[1].trim();
            }

            // 解析 开始领取时间
            if (!arr[2].trim().isEmpty()) {
              beginTime = arr[2].trim();
            }

            // 解析 结束领取时间
            if (!arr[3].trim().isEmpty()) {
              endTime = arr[3].trim();
            }

            // 解析 生成兑换码个数
            if (!arr[4].trim().isEmpty()) {
              codeNum = Integer.parseInt(arr[4].trim());
            }

            // 解析 渠道id
            if (!arr[5].trim().isEmpty()) {
              channel = arr[5].trim();
            }

            // 解析 获取的奖励列表
            if (!arr[6].trim().isEmpty()) {
              rewards = arr[6].trim();
            }

            // 解析 一个礼包码可有多少个角色激活，-1表示不限
            if (!arr[7].trim().isEmpty()) {
              limit1 = Integer.parseInt(arr[7].trim());
            }

            // 解析 一个角色可以激活同一批礼包码数量
            if (!arr[8].trim().isEmpty()) {
              limit2 = Integer.parseInt(arr[8].trim());
            }

            // 解析 该批礼包码能够被那些服务器使用，-1为所有
            if (!arr[9].trim().isEmpty()) {
              limit3 = arr[9].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          ExchangeCodeConfig config = new ExchangeCodeConfig(groupId, name, beginTime, endTime, codeNum, channel, rewards, limit1, limit2, limit3);
          config.afterLoad();
          newList.add(config);
        }
        checker.checkAfterParse(logger, newList);
        configList = List.copyOf(newList);
        afterLoad();
      } catch (IOException e) {
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    public void clear() {
      configList = List.of();
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

    public List<ExchangeCodeConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "exchangeCode.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
