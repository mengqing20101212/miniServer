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
 * File: Mail_templateConfigManager
 */
public class Mail_templateConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final Mail_templateConfigManager instance = new Mail_templateConfigManager();
  private static final Mail_templateConfigManagerImpl instanceImplA = new Mail_templateConfigManagerImpl();
  private static final Mail_templateConfigManagerImpl instanceImplB = new Mail_templateConfigManagerImpl();

  public static Mail_templateConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static Mail_templateConfigManagerImpl getStandby() {
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
    Mail_templateConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class Mail_templateConfigManagerImpl extends AbstractConfigManger {
    private List<Mail_templateConfig> configList = List.of();
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
      Mail_templateConfigChecker checker = new Mail_templateConfigChecker();
      checker.checkHeader(logger, configDir);
      List<Mail_templateConfig> newList = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 11) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int icon = 0;
          String des = null;
          int part_begin = 0;
          int part_end = 0;
          int type = 0;
          String stamp = null;
          int valid_time = 0;
          String sender = null;
          String titile = null;
          String content = null;
          String attach = null;
          try {
            // 解析 邮件列表中, 邮件图标ID
            if (!arr[0].trim().isEmpty()) {
              icon = Integer.parseInt(arr[0].trim());
            }

            // 解析 邮件列表中, 描述
            if (!arr[1].trim().isEmpty()) {
              des = arr[1].trim();
            }

            // 解析 影响分区开始区间，仅福利类邮件有效
            if (!arr[2].trim().isEmpty()) {
              part_begin = Integer.parseInt(arr[2].trim());
            }

            // 解析 影响分区结束区间，仅福利类邮件有效
            if (!arr[3].trim().isEmpty()) {
              part_end = Integer.parseInt(arr[3].trim());
            }

            // 解析 邮件模版类型
            if (!arr[4].trim().isEmpty()) {
              type = Integer.parseInt(arr[4].trim());
            }

            // 解析 预设发送邮件时间,仅福利类邮件用
            if (!arr[5].trim().isEmpty()) {
              stamp = arr[5].trim();
            }

            // 解析 有效时间秒数, （无论是否阅读，到时间销毁）
            if (!arr[6].trim().isEmpty()) {
              valid_time = Integer.parseInt(arr[6].trim());
            }

            // 解析 发件人
            if (!arr[7].trim().isEmpty()) {
              sender = arr[7].trim();
            }

            // 解析 标题
            if (!arr[8].trim().isEmpty()) {
              titile = arr[8].trim();
            }

            // 解析 内容
            if (!arr[9].trim().isEmpty()) {
              content = arr[9].trim();
            }

            // 解析 附件
            if (!arr[10].trim().isEmpty()) {
              attach = arr[10].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          Mail_templateConfig config = new Mail_templateConfig(icon, des, part_begin, part_end, type, stamp, valid_time, sender, titile, content, attach);
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

    public List<Mail_templateConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "mail_template.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
