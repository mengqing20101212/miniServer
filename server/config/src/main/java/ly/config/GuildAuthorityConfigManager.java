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
 * File: GuildAuthorityConfigManager
 */
public class GuildAuthorityConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final GuildAuthorityConfigManager instance = new GuildAuthorityConfigManager();
  private static final GuildAuthorityConfigManagerImpl instanceImplA = new GuildAuthorityConfigManagerImpl();
  private static final GuildAuthorityConfigManagerImpl instanceImplB = new GuildAuthorityConfigManagerImpl();

  public static GuildAuthorityConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static GuildAuthorityConfigManagerImpl getStandby() {
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
    GuildAuthorityConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class GuildAuthorityConfigManagerImpl extends AbstractConfigManger {
    private List<GuildAuthorityConfig> configList = List.of();
    private Map<Integer, GuildAuthorityConfig> configMap = Map.of();

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
      GuildAuthorityConfigChecker checker = new GuildAuthorityConfigChecker();
      checker.checkHeader(logger, configDir);
      List<GuildAuthorityConfig> newList = new ArrayList<>();
      Map<Integer, GuildAuthorityConfig> newMap = new HashMap<>();
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
          String Name = null;
          int level = 0;
          int upgrade = 0;
          int downgrade = 0;
          int dissolve = 0;
          int examine = 0;
          int kick = 0;
          int publish = 0;
          int notice = 0;
          int welfareShop = 0;
          int openBoss = 0;
          int mail = 0;
          int exit = 0;
          int label = 0;
          int guildName = 0;
          int sign = 0;
          int refreshShop = 0;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 成员类型
            if (!arr[1].trim().isEmpty()) {
              Name = arr[1].trim();
            }

            // 解析 级别
            if (!arr[2].trim().isEmpty()) {
              level = Integer.parseInt(arr[2].trim());
            }

            // 解析 升级公会权限
            if (!arr[3].trim().isEmpty()) {
              upgrade = Integer.parseInt(arr[3].trim());
            }

            // 解析 降级公会权限
            if (!arr[4].trim().isEmpty()) {
              downgrade = Integer.parseInt(arr[4].trim());
            }

            // 解析 解散公会权限
            if (!arr[5].trim().isEmpty()) {
              dissolve = Integer.parseInt(arr[5].trim());
            }

            // 解析 审批权限
            if (!arr[6].trim().isEmpty()) {
              examine = Integer.parseInt(arr[6].trim());
            }

            // 解析 踢人权限
            if (!arr[7].trim().isEmpty()) {
              kick = Integer.parseInt(arr[7].trim());
            }

            // 解析 发布招募权限
            if (!arr[8].trim().isEmpty()) {
              publish = Integer.parseInt(arr[8].trim());
            }

            // 解析 公告权限
            if (!arr[9].trim().isEmpty()) {
              notice = Integer.parseInt(arr[9].trim());
            }

            // 解析 福利商店购买权限
            if (!arr[10].trim().isEmpty()) {
              welfareShop = Integer.parseInt(arr[10].trim());
            }

            // 解析 开启首领权限
            if (!arr[11].trim().isEmpty()) {
              openBoss = Integer.parseInt(arr[11].trim());
            }

            // 解析 公会邮件权限
            if (!arr[12].trim().isEmpty()) {
              mail = Integer.parseInt(arr[12].trim());
            }

            // 解析 退出权限
            if (!arr[13].trim().isEmpty()) {
              exit = Integer.parseInt(arr[13].trim());
            }

            // 解析 修改标签权限
            if (!arr[14].trim().isEmpty()) {
              label = Integer.parseInt(arr[14].trim());
            }

            // 解析 修改名称
            if (!arr[15].trim().isEmpty()) {
              guildName = Integer.parseInt(arr[15].trim());
            }

            // 解析 修改标志
            if (!arr[16].trim().isEmpty()) {
              sign = Integer.parseInt(arr[16].trim());
            }

            // 解析 刷新宇宙商店
            if (!arr[17].trim().isEmpty()) {
              refreshShop = Integer.parseInt(arr[17].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          GuildAuthorityConfig config = new GuildAuthorityConfig(id, Name, level, upgrade, downgrade, dissolve, examine, kick, publish, notice, welfareShop, openBoss, mail, exit, label, guildName, sign, refreshShop);
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

    public List<GuildAuthorityConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildAuthorityConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildAuthority.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
