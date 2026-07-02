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
 * File: Channel_typeConfigManager
 */
public class Channel_typeConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final Channel_typeConfigManager instance = new Channel_typeConfigManager();
  private static final Channel_typeConfigManagerImpl instanceImplA = new Channel_typeConfigManagerImpl();
  private static final Channel_typeConfigManagerImpl instanceImplB = new Channel_typeConfigManagerImpl();

  public static Channel_typeConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static Channel_typeConfigManagerImpl getStandby() {
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
    Channel_typeConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class Channel_typeConfigManagerImpl extends AbstractConfigManger {
    private List<Channel_typeConfig> configList = List.of();
    private Map<Integer, Channel_typeConfig> configMap = Map.of();

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
      Channel_typeConfigChecker checker = new Channel_typeConfigChecker();
      checker.checkHeader(logger, configDir);
      List<Channel_typeConfig> newList = new ArrayList<>();
      Map<Integer, Channel_typeConfig> newMap = new HashMap<>();
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
          String spname = null;
          int spid = 0;
          String spcode = null;
          String spdownloadurl = null;
          String checkserverip = null;
          int checkserverport = 0;
          int groupid = 0;
          String check_login_test_url = null;
          String check_login_url = null;
          String login_interface = null;
          String appid = null;
          String appsec = null;
          int isExteral = 0;
          try {
            // 解析 渠道id
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 渠道名
            if (!arr[1].trim().isEmpty()) {
              spname = arr[1].trim();
            }

            // 解析 渠道编号
            if (!arr[2].trim().isEmpty()) {
              spid = Integer.parseInt(arr[2].trim());
            }

            // 解析 渠道号
            if (!arr[3].trim().isEmpty()) {
              spcode = arr[3].trim();
            }

            // 解析 更新下载url
            if (!arr[4].trim().isEmpty()) {
              spdownloadurl = arr[4].trim();
            }

            // 解析 审核服ip
            if (!arr[5].trim().isEmpty()) {
              checkserverip = arr[5].trim();
            }

            // 解析 审核服端口
            if (!arr[6].trim().isEmpty()) {
              checkserverport = Integer.parseInt(arr[6].trim());
            }

            // 解析 渠道分组id
            if (!arr[7].trim().isEmpty()) {
              groupid = Integer.parseInt(arr[7].trim());
            }

            // 解析 登录校验测试环境
            if (!arr[8].trim().isEmpty()) {
              check_login_test_url = arr[8].trim();
            }

            // 解析 登录校验正式环境
            if (!arr[9].trim().isEmpty()) {
              check_login_url = arr[9].trim();
            }

            // 解析 登录接口
            if (!arr[10].trim().isEmpty()) {
              login_interface = arr[10].trim();
            }

            // 解析 appid
            if (!arr[11].trim().isEmpty()) {
              appid = arr[11].trim();
            }

            // 解析 sdk服务器秘钥
            if (!arr[12].trim().isEmpty()) {
              appsec = arr[12].trim();
            }

            // 解析 外发渠道
            if (!arr[13].trim().isEmpty()) {
              isExteral = Integer.parseInt(arr[13].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          Channel_typeConfig config = new Channel_typeConfig(id, spname, spid, spcode, spdownloadurl, checkserverip, checkserverport, groupid, check_login_test_url, check_login_url, login_interface, appid, appsec, isExteral);
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

    public List<Channel_typeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, Channel_typeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "channel_type.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
