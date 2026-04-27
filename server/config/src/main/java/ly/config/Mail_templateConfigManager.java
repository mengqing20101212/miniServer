package ly.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import ly.utils.KV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;
import org.slf4j.Logger;

/*
 * 自动生成的代码 请不要改动，如需改动需要在 @@@@@自定义区修改@@@@@
 * File: Mail_templateConfigManager
 */
public class Mail_templateConfigManager implements InterfaceConfigManagerProxy {
  AtomicBoolean switched = new AtomicBoolean(false);
  private static final Mail_templateConfigManager instance = new Mail_templateConfigManager();
  private static final Mail_templateConfigManagerImpl instanceImplA =
      new Mail_templateConfigManagerImpl();
  private static final Mail_templateConfigManagerImpl instanceImplB =
      new Mail_templateConfigManagerImpl();

  public boolean isSwitched() {
    return switched.get();
  }

  public static Mail_templateConfigManagerImpl getInstance() {
    if (instance.isSwitched()) {
      return instanceImplA;
    } else {
      return instanceImplB;
    }
  }

  @Override
  public void loadConfig(Logger logger, String configDir) throws ConfigLoadException {
    getInstance().reload(logger, configDir);
  }

  public static class Mail_templateConfigManagerImpl extends AbstractConfigManger {

    List<Mail_templateConfig> configList = new ArrayList<Mail_templateConfig>();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    protected void reload(Logger logger, String configDir) throws ConfigLoadException {
      String fileName = configDir + File.separator + getConfigFileName();
      File file = new File(fileName);
      clear();
      if (!file.exists()) {
        logger.error(fileName + " does not exist");
        throw new ConfigLoadException("Config file does not exist :" + fileName);
      }
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;
        br.readLine(); //先读取一行表头 
        while ((line = br.readLine()) != null) { // 按行读取
          String[] arr = line.split("\t");
          Mail_templateConfig config = new Mail_templateConfig();
          try {
            //解析 邮件列表中, 邮件图标ID
            if (!arr[0].trim().isEmpty()) {
            config.icon =  Integer.parseInt(arr[0].trim());
            }

            //解析 邮件列表中, 描述
            if (!arr[1].trim().isEmpty()) {
            config.des = arr[1].trim();
            }

            //解析 影响分区开始区间，仅福利类邮件有效
            if (!arr[2].trim().isEmpty()) {
            config.part_begin =  Integer.parseInt(arr[2].trim());
            }

            //解析 影响分区结束区间，仅福利类邮件有效
            if (!arr[3].trim().isEmpty()) {
            config.part_end =  Integer.parseInt(arr[3].trim());
            }

            //解析 邮件模版类型
            if (!arr[4].trim().isEmpty()) {
            config.type =  Integer.parseInt(arr[4].trim());
            }

            //解析 预设发送邮件时间,仅福利类邮件用
            if (!arr[5].trim().isEmpty()) {
            config.stamp = arr[5].trim();
            }

            //解析 有效时间秒数, （无论是否阅读，到时间销毁）
            if (!arr[6].trim().isEmpty()) {
            config.valid_time =  Integer.parseInt(arr[6].trim());
            }

            //解析 发件人
            if (!arr[7].trim().isEmpty()) {
            config.sender = arr[7].trim();
            }

            //解析 标题
            if (!arr[8].trim().isEmpty()) {
            config.titile = arr[8].trim();
            }

            //解析 内容
            if (!arr[9].trim().isEmpty()) {
            config.content = arr[9].trim();
            }

            //解析 附件
            if (!arr[10].trim().isEmpty()) {
            config.attach = arr[10].trim();
            }


          } catch (Exception e) {
            logger.error(
                String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, line, e.getMessage()));
            e.printStackTrace();
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          config.afterLoad();
          configList.add(config);
        }
        afterLoad();
      } catch (IOException e) {
        e.printStackTrace();
        throw new ConfigLoadException("Config file could not be read :" + fileName);
      }
    }

    @Override
    protected void clear() {

      configList.clear();

      // @@@@@自定义clear方法开始区@@@@@


      // @@@@@自定义clear方法结束区@@@@@
    }

    private List<Integer> parseIntList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      String[] parts = value.split(",");
      List<Integer> result = new ArrayList<>();
      for (String part : parts) {
        try {
          result.add(Integer.parseInt(part.trim()));
        } catch (NumberFormatException e) {
          // 如果不是数字，则跳过
        }
      }
      return result;
    }

    private List<KV<Integer, Integer>> parseIntKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<Integer, Integer>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            try {
              Integer key = Integer.parseInt(keyStr);
              Integer val = Integer.parseInt(valueStr);
              result.add(new KV<>(key, val));
            } catch (NumberFormatException e) {
              // 如果不是数字，则跳过
            }
          }
        }
      }
      return result;
    }

    private List<KV<String, String>> parseStringKVList(String value) {
      if (value == null || value.trim().isEmpty()) {
        return new ArrayList<>();
      }
      List<KV<String, String>> result = new ArrayList<>();
      String[] pairs = value.split(",");
      for (String pair : pairs) {
        pair = pair.trim();
        if (!pair.isEmpty()) {
          int idx = pair.indexOf(":");
          if (idx > 0) {
            String keyStr = pair.substring(0, idx).trim();
            String valueStr = pair.substring(idx + 1).trim();
            result.add(new KV<>(keyStr, valueStr));
          }
        }
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
