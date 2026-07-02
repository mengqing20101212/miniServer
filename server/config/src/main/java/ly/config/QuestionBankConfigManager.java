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
 * File: QuestionBankConfigManager
 */
public class QuestionBankConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final QuestionBankConfigManager instance = new QuestionBankConfigManager();
  private static final QuestionBankConfigManagerImpl instanceImplA = new QuestionBankConfigManagerImpl();
  private static final QuestionBankConfigManagerImpl instanceImplB = new QuestionBankConfigManagerImpl();

  public static QuestionBankConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static QuestionBankConfigManagerImpl getStandby() {
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
    QuestionBankConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class QuestionBankConfigManagerImpl extends AbstractConfigManger {
    private List<QuestionBankConfig> configList = List.of();
    private Map<Integer, QuestionBankConfig> configMap = Map.of();

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
      QuestionBankConfigChecker checker = new QuestionBankConfigChecker();
      checker.checkHeader(logger, configDir);
      List<QuestionBankConfig> newList = new ArrayList<>();
      Map<Integer, QuestionBankConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 19) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String questionContent = null;
          String questionNote = null;
          int type = 0;
          int questionShow = 0;
          String questionPicture = null;
          int optionShow = 0;
          String simpleA = null;
          String simpleB = null;
          String simpleC = null;
          String simpleD = null;
          String simpleAnswer = null;
          String simpleNote = null;
          String hardA = null;
          String hardB = null;
          String hardC = null;
          String hardD = null;
          String hardAnswer = null;
          String hardNote = null;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 题干
            if (!arr[1].trim().isEmpty()) {
              questionContent = arr[1].trim();
            }

            // 解析 题干备注
            if (!arr[2].trim().isEmpty()) {
              questionNote = arr[2].trim();
            }

            // 解析 题目类型
            if (!arr[3].trim().isEmpty()) {
              type = Integer.parseInt(arr[3].trim());
            }

            // 解析 题目形式
            if (!arr[4].trim().isEmpty()) {
              questionShow = Integer.parseInt(arr[4].trim());
            }

            // 解析 题干图片
            if (!arr[5].trim().isEmpty()) {
              questionPicture = arr[5].trim();
            }

            // 解析 选项形式
            if (!arr[6].trim().isEmpty()) {
              optionShow = Integer.parseInt(arr[6].trim());
            }

            // 解析 简单A选项
            if (!arr[7].trim().isEmpty()) {
              simpleA = arr[7].trim();
            }

            // 解析 简单B选项
            if (!arr[8].trim().isEmpty()) {
              simpleB = arr[8].trim();
            }

            // 解析 简单C选项
            if (!arr[9].trim().isEmpty()) {
              simpleC = arr[9].trim();
            }

            // 解析 简单D选项
            if (!arr[10].trim().isEmpty()) {
              simpleD = arr[10].trim();
            }

            // 解析 简单题答案
            if (!arr[11].trim().isEmpty()) {
              simpleAnswer = arr[11].trim();
            }

            // 解析 简单题备注
            if (!arr[12].trim().isEmpty()) {
              simpleNote = arr[12].trim();
            }

            // 解析 困难A选项
            if (!arr[13].trim().isEmpty()) {
              hardA = arr[13].trim();
            }

            // 解析 困难B选项
            if (!arr[14].trim().isEmpty()) {
              hardB = arr[14].trim();
            }

            // 解析 困难C选项
            if (!arr[15].trim().isEmpty()) {
              hardC = arr[15].trim();
            }

            // 解析 困难D选项
            if (!arr[16].trim().isEmpty()) {
              hardD = arr[16].trim();
            }

            // 解析 困难题答案
            if (!arr[17].trim().isEmpty()) {
              hardAnswer = arr[17].trim();
            }

            // 解析 困难题备注
            if (!arr[18].trim().isEmpty()) {
              hardNote = arr[18].trim();
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          QuestionBankConfig config = new QuestionBankConfig(id, questionContent, questionNote, type, questionShow, questionPicture, optionShow, simpleA, simpleB, simpleC, simpleD, simpleAnswer, simpleNote, hardA, hardB, hardC, hardD, hardAnswer, hardNote);
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

    public List<QuestionBankConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, QuestionBankConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "questionBank.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
