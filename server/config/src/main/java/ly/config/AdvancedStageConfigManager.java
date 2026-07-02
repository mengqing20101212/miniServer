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
 * File: AdvancedStageConfigManager
 */
public class AdvancedStageConfigManager implements InterfaceConfigManagerProxy {
  private static final AtomicBoolean switched = new AtomicBoolean(false);
  private static final AdvancedStageConfigManager instance = new AdvancedStageConfigManager();
  private static final AdvancedStageConfigManagerImpl instanceImplA = new AdvancedStageConfigManagerImpl();
  private static final AdvancedStageConfigManagerImpl instanceImplB = new AdvancedStageConfigManagerImpl();

  public static AdvancedStageConfigManagerImpl getInstance() {
    return switched.get() ? instanceImplA : instanceImplB;
  }

  private static AdvancedStageConfigManagerImpl getStandby() {
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
    AdvancedStageConfigManagerImpl oldActive = getInstance();
    switched.set(!switched.get());
    return oldActive;
  }

  @Override
  public String getConfigFileName() {
    return getInstance().getConfigFileName();
  }

  public static class AdvancedStageConfigManagerImpl extends AbstractConfigManger {
    private List<AdvancedStageConfig> configList = List.of();
    private Map<Integer, AdvancedStageConfig> configMap = Map.of();

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
      AdvancedStageConfigChecker checker = new AdvancedStageConfigChecker();
      checker.checkHeader(logger, configDir);
      List<AdvancedStageConfig> newList = new ArrayList<>();
      Map<Integer, AdvancedStageConfig> newMap = new HashMap<>();
      try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String rowText;
        br.readLine();
        br.readLine();
        while ((rowText = br.readLine()) != null) {
          if (rowText.isBlank()) { continue; }
          String[] arr = rowText.split("\\t", -1);
          if (arr.length < 50) {
            throw new ConfigLoadException("Config column size mismatch :" + fileName + ", line=" + rowText);
          }
          int id = 0;
          int stageType = 0;
          int floor = 0;
          int cost = 0;
          int advance = 0;
          int sceneId = 0;
          int dropSelection = 0;
          String dropList = null;
          String upIcon = null;
          String dropGroup = null;
          String name = null;
          String lockTips = null;
          int preStage = 0;
          int nextStage = 0;
          int needLv = 0;
          int bossHead = 0;
          String model = null;
          String action = null;
          String word = null;
          String decorate1 = null;
          String decorate2 = null;
          String decorate3 = null;
          String bossId = null;
          String scaling = null;
          String offset = null;
          String offsetX = null;
          String offsetAngle = null;
          String showType = null;
          String selectionDis = null;
          String trigger = null;
          int spineModelResId = 0;
          int spineScale = 0;
          String spinePosOffset = null;
          String spineAnimation = null;
          String dropExpect = null;
          int recommendLv = 0;
          int recommendtype = 0;
          String recommendhero = null;
          String battleTipText = null;
          int selectionType = 0;
          int rankType = 0;
          int activityControlId = 0;
          String webDes = null;
          String dropExpectAdd = null;
          int recommendHero1 = 0;
          int recommendHero2 = 0;
          int iconType = 0;
          int noticeIcon = 0;
          int firstDropExpect = 0;
          int decorate2Background = 0;
          try {
            // 解析 编号
            if (!arr[0].trim().isEmpty()) {
              id = Integer.parseInt(arr[0].trim());
            }

            // 解析 关卡类型
            if (!arr[1].trim().isEmpty()) {
              stageType = Integer.parseInt(arr[1].trim());
            }

            // 解析 层数
            if (!arr[2].trim().isEmpty()) {
              floor = Integer.parseInt(arr[2].trim());
            }

            // 解析 体力消耗
            if (!arr[3].trim().isEmpty()) {
              cost = Integer.parseInt(arr[3].trim());
            }

            // 解析 预支体力消耗
            if (!arr[4].trim().isEmpty()) {
              advance = Integer.parseInt(arr[4].trim());
            }

            // 解析 关卡id
            if (!arr[5].trim().isEmpty()) {
              sceneId = Integer.parseInt(arr[5].trim());
            }

            // 解析 倾向选择
            if (!arr[6].trim().isEmpty()) {
              dropSelection = Integer.parseInt(arr[6].trim());
            }

            // 解析 掉落预览
            if (!arr[7].trim().isEmpty()) {
              dropList = arr[7].trim();
            }

            // 解析 是否有up图标
            if (!arr[8].trim().isEmpty()) {
              upIcon = arr[8].trim();
            }

            // 解析 关卡掉落
            if (!arr[9].trim().isEmpty()) {
              dropGroup = arr[9].trim();
            }

            // 解析 名称
            if (!arr[10].trim().isEmpty()) {
              name = arr[10].trim();
            }

            // 解析 解锁提示
            if (!arr[11].trim().isEmpty()) {
              lockTips = arr[11].trim();
            }

            // 解析 前置章节
            if (!arr[12].trim().isEmpty()) {
              preStage = Integer.parseInt(arr[12].trim());
            }

            // 解析 后置章节
            if (!arr[13].trim().isEmpty()) {
              nextStage = Integer.parseInt(arr[13].trim());
            }

            // 解析 解锁等级
            if (!arr[14].trim().isEmpty()) {
              needLv = Integer.parseInt(arr[14].trim());
            }

            // 解析 boss头像
            if (!arr[15].trim().isEmpty()) {
              bossHead = Integer.parseInt(arr[15].trim());
            }

            // 解析 形象人物模型
            if (!arr[16].trim().isEmpty()) {
              model = arr[16].trim();
            }

            // 解析 形象人物动作
            if (!arr[17].trim().isEmpty()) {
              action = arr[17].trim();
            }

            // 解析 形象人物对话
            if (!arr[18].trim().isEmpty()) {
              word = arr[18].trim();
            }

            // 解析 中心装饰参数1
            if (!arr[19].trim().isEmpty()) {
              decorate1 = arr[19].trim();
            }

            // 解析 中心装饰参数2
            if (!arr[20].trim().isEmpty()) {
              decorate2 = arr[20].trim();
            }

            // 解析 中心装饰参数3
            if (!arr[21].trim().isEmpty()) {
              decorate3 = arr[21].trim();
            }

            // 解析 bossID
            if (!arr[22].trim().isEmpty()) {
              bossId = arr[22].trim();
            }

            // 解析 缩放比例
            if (!arr[23].trim().isEmpty()) {
              scaling = arr[23].trim();
            }

            // 解析 Y轴偏移位置
            if (!arr[24].trim().isEmpty()) {
              offset = arr[24].trim();
            }

            // 解析 X轴偏移位置
            if (!arr[25].trim().isEmpty()) {
              offsetX = arr[25].trim();
            }

            // 解析 偏移角度
            if (!arr[26].trim().isEmpty()) {
              offsetAngle = arr[26].trim();
            }

            // 解析 是否特殊展示动作
            if (!arr[27].trim().isEmpty()) {
              showType = arr[27].trim();
            }

            // 解析 掉落倾向描述
            if (!arr[28].trim().isEmpty()) {
              selectionDis = arr[28].trim();
            }

            // 解析 突发事件触发概率
            if (!arr[29].trim().isEmpty()) {
              trigger = arr[29].trim();
            }

            // 解析 spine显示模型预设资源Id
            if (!arr[30].trim().isEmpty()) {
              spineModelResId = Integer.parseInt(arr[30].trim());
            }

            // 解析 缩放比例
            if (!arr[31].trim().isEmpty()) {
              spineScale = Integer.parseInt(arr[31].trim());
            }

            // 解析 位置偏移(x,y)
            if (!arr[32].trim().isEmpty()) {
              spinePosOffset = arr[32].trim();
            }

            // 解析 spine动画
            if (!arr[33].trim().isEmpty()) {
              spineAnimation = arr[33].trim();
            }

            // 解析 期望掉落数量
            if (!arr[34].trim().isEmpty()) {
              dropExpect = arr[34].trim();
            }

            // 解析 推荐等级
            if (!arr[35].trim().isEmpty()) {
              recommendLv = Integer.parseInt(arr[35].trim());
            }

            // 解析 推荐类型
            if (!arr[36].trim().isEmpty()) {
              recommendtype = Integer.parseInt(arr[36].trim());
            }

            // 解析 推荐英雄
            if (!arr[37].trim().isEmpty()) {
              recommendhero = arr[37].trim();
            }

            // 解析 战斗提示
            if (!arr[38].trim().isEmpty()) {
              battleTipText = arr[38].trim();
            }

            // 解析 倾向类型
            if (!arr[39].trim().isEmpty()) {
              selectionType = Integer.parseInt(arr[39].trim());
            }

            // 解析 排行榜枚举
            if (!arr[40].trim().isEmpty()) {
              rankType = Integer.parseInt(arr[40].trim());
            }

            // 解析 对应功能ID
            if (!arr[41].trim().isEmpty()) {
              activityControlId = Integer.parseInt(arr[41].trim());
            }

            // 解析 GM平台描述
            if (!arr[42].trim().isEmpty()) {
              webDes = arr[42].trim();
            }

            // 解析 期望掉落效率提升值
            if (!arr[43].trim().isEmpty()) {
              dropExpectAdd = arr[43].trim();
            }

            // 解析 推荐英雄1
            if (!arr[44].trim().isEmpty()) {
              recommendHero1 = Integer.parseInt(arr[44].trim());
            }

            // 解析 推荐英雄2
            if (!arr[45].trim().isEmpty()) {
              recommendHero2 = Integer.parseInt(arr[45].trim());
            }

            // 解析 图标类型
            if (!arr[46].trim().isEmpty()) {
              iconType = Integer.parseInt(arr[46].trim());
            }

            // 解析 获取提示图标
            if (!arr[47].trim().isEmpty()) {
              noticeIcon = Integer.parseInt(arr[47].trim());
            }

            // 解析 首通期望掉落数量
            if (!arr[48].trim().isEmpty()) {
              firstDropExpect = Integer.parseInt(arr[48].trim());
            }

            // 解析 英雄试炼角色背景
            if (!arr[49].trim().isEmpty()) {
              decorate2Background = Integer.parseInt(arr[49].trim());
            }

          } catch (Exception e) {
            logger.error(String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s", fileName, rowText, e.getMessage()));
            throw new ConfigLoadException("Error parsing config file :" + fileName);
          }
          AdvancedStageConfig config = new AdvancedStageConfig(id, stageType, floor, cost, advance, sceneId, dropSelection, dropList, upIcon, dropGroup, name, lockTips, preStage, nextStage, needLv, bossHead, model, action, word, decorate1, decorate2, decorate3, bossId, scaling, offset, offsetX, offsetAngle, showType, selectionDis, trigger, spineModelResId, spineScale, spinePosOffset, spineAnimation, dropExpect, recommendLv, recommendtype, recommendhero, battleTipText, selectionType, rankType, activityControlId, webDes, dropExpectAdd, recommendHero1, recommendHero2, iconType, noticeIcon, firstDropExpect, decorate2Background);
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

    public List<AdvancedStageConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, AdvancedStageConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "advancedStage.txt";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
