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
 * File: BlockBattleConvergeConfigManager
 */
public class BlockBattleConvergeConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final BlockBattleConvergeConfigManager instance = new BlockBattleConvergeConfigManager();
  private static final BlockBattleConvergeConfigManagerImpl instanceImplA = new BlockBattleConvergeConfigManagerImpl();
  private static final BlockBattleConvergeConfigManagerImpl instanceImplB = new BlockBattleConvergeConfigManagerImpl();

  public static BlockBattleConvergeConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static BlockBattleConvergeConfigManagerImpl getStandby() {
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
    BlockBattleConvergeConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class BlockBattleConvergeConfigManagerImpl extends AbstractConfigManger {
    private List<BlockBattleConvergeConfig> configList = List.of();
    private Map<Integer, BlockBattleConvergeConfig> configMap = Map.of();

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
      BlockBattleConvergeConfigChecker checker = new BlockBattleConvergeConfigChecker();
      checker.checkHeader(logger, configDir);
      List<BlockBattleConvergeConfig> newList = new ArrayList<>();
      Map<Integer, BlockBattleConvergeConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 8) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int type = 0;
          int convergeCheck = 0;
          String convergePara = null;
          String convergeParaAtack = null;
          String beizhu = null;
          String dec = null;
          int useScence = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 选择类型
            if (!arr[1].trim().isEmpty()) {
              type = Integer.parseInt(arr[1].trim());
            }

            // 解析 是否集火
            if (!arr[2].trim().isEmpty()) {
              convergeCheck = Integer.parseInt(arr[2].trim());
            }

            // 解析 防守方视角
            if (!arr[3].trim().isEmpty()) {
              convergePara = arr[3].trim();
            }

            // 解析 进攻方视角
            if (!arr[4].trim().isEmpty()) {
              convergeParaAtack = arr[4].trim();
            }

            // 解析 备注
            if (!arr[5].trim().isEmpty()) {
              beizhu = arr[5].trim();
            }

            // 解析 集火类型描述
            if (!arr[6].trim().isEmpty()) {
              dec = arr[6].trim();
            }

            // 解析 使用场景
            if (!arr[7].trim().isEmpty()) {
              useScence = Integer.parseInt(arr[7].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          BlockBattleConvergeConfig config = new BlockBattleConvergeConfig(id, type, convergeCheck, convergePara, convergeParaAtack, beizhu, dec, useScence);
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

    public List<BlockBattleConvergeConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, BlockBattleConvergeConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "blockBattleConverge.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
