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
 * File: BattleQuestionConfigManager
 */
public class BattleQuestionConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final BattleQuestionConfigManager instance = new BattleQuestionConfigManager();
  private static final BattleQuestionConfigManagerImpl instanceImplA = new BattleQuestionConfigManagerImpl();
  private static final BattleQuestionConfigManagerImpl instanceImplB = new BattleQuestionConfigManagerImpl();

  public static BattleQuestionConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static BattleQuestionConfigManagerImpl getStandby() {
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
    BattleQuestionConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class BattleQuestionConfigManagerImpl extends AbstractConfigManger {
    private List<BattleQuestionConfig> configList = List.of();
    private Map<Integer, BattleQuestionConfig> configMap = Map.of();

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
      BattleQuestionConfigChecker checker = new BattleQuestionConfigChecker();
      checker.checkHeader(logger, configDir);
      List<BattleQuestionConfig> newList = new ArrayList<>();
      Map<Integer, BattleQuestionConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 16) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          String questionContent = null;
          int questionPic = 0;
          int answer = 0;
          String answer1 = null;
          String answerResult1 = null;
          int answerEffect1 = 0;
          String answer2 = null;
          String answerResult2 = null;
          int answerEffect2 = 0;
          String answer3 = null;
          String answerResult3 = null;
          int answerEffect3 = 0;
          String answer4 = null;
          String answerResult4 = null;
          int answerEffect4 = 0;
          try {
            // 解析 ID
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 题干
            if (!arr[1].trim().isEmpty()) {
              questionContent = arr[1].trim();
            }

            // 解析 标题图片
            if (!arr[2].trim().isEmpty()) {
              questionPic = Integer.parseInt(arr[2].trim());
            }

            // 解析 正确答案
            if (!arr[3].trim().isEmpty()) {
              answer = Integer.parseInt(arr[3].trim());
            }

            // 解析 选项1文字
            if (!arr[4].trim().isEmpty()) {
              answer1 = arr[4].trim();
            }

            // 解析 选项1反馈文字
            if (!arr[5].trim().isEmpty()) {
              answerResult1 = arr[5].trim();
            }

            // 解析 选项1效果
            if (!arr[6].trim().isEmpty()) {
              answerEffect1 = Integer.parseInt(arr[6].trim());
            }

            // 解析 选项2文字
            if (!arr[7].trim().isEmpty()) {
              answer2 = arr[7].trim();
            }

            // 解析 选项2反馈文字
            if (!arr[8].trim().isEmpty()) {
              answerResult2 = arr[8].trim();
            }

            // 解析 选项2效果
            if (!arr[9].trim().isEmpty()) {
              answerEffect2 = Integer.parseInt(arr[9].trim());
            }

            // 解析 选项3文字
            if (!arr[10].trim().isEmpty()) {
              answer3 = arr[10].trim();
            }

            // 解析 选项3反馈文字
            if (!arr[11].trim().isEmpty()) {
              answerResult3 = arr[11].trim();
            }

            // 解析 选项3效果
            if (!arr[12].trim().isEmpty()) {
              answerEffect3 = Integer.parseInt(arr[12].trim());
            }

            // 解析 选项4文字
            if (!arr[13].trim().isEmpty()) {
              answer4 = arr[13].trim();
            }

            // 解析 选项4反馈文字
            if (!arr[14].trim().isEmpty()) {
              answerResult4 = arr[14].trim();
            }

            // 解析 选项4效果
            if (!arr[15].trim().isEmpty()) {
              answerEffect4 = Integer.parseInt(arr[15].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          BattleQuestionConfig config = new BattleQuestionConfig(id, questionContent, questionPic, answer, answer1, answerResult1, answerEffect1, answer2, answerResult2, answerEffect2, answer3, answerResult3, answerEffect3, answer4, answerResult4, answerEffect4);
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

    public List<BattleQuestionConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BattleQuestionConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "battleQuestion.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
