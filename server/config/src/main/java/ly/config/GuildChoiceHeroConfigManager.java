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
 * File: GuildChoiceHeroConfigManager
 */
public class GuildChoiceHeroConfigManager implements InterfaceConfigManagerProxy {
  public static GuildChoiceHeroConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(GuildChoiceHeroConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new GuildChoiceHeroConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "guildChoiceHero.xml"; }

  public static class GuildChoiceHeroConfigManagerImpl extends AbstractConfigManger {
    private List<GuildChoiceHeroConfig> configList = List.of();
    private Map<Integer, GuildChoiceHeroConfig> configMap = Map.of();

    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("string");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      GuildChoiceHeroConfigChecker checker = new GuildChoiceHeroConfigChecker();
      List<GuildChoiceHeroConfig> newList = new ArrayList<>();
      Map<Integer, GuildChoiceHeroConfig> newMap = new HashMap<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          int parsedField2 = 0;
          int parsedField3 = 0;
          int parsedField4 = 0;
          int parsedField5 = 0;
          int parsedField6 = 0;
          String parsedField7 = null;
          java.util.List<Integer> parsedField8 = null;
          int parsedField9 = 0;
          int parsedField10 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("id"), fileName + ":行" + lineNumber + ":字段 id");
            parsedField1 = ly.ConfigValues.read(type1, values.get("unlcokLevel"), fileName + ":行" + lineNumber + ":字段 unlcokLevel");
            parsedField2 = ly.ConfigValues.read(type2, values.get("npcid"), fileName + ":行" + lineNumber + ":字段 npcid");
            parsedField3 = ly.ConfigValues.read(type3, values.get("playerLv"), fileName + ":行" + lineNumber + ":字段 playerLv");
            parsedField4 = ly.ConfigValues.read(type4, values.get("sceneId"), fileName + ":行" + lineNumber + ":字段 sceneId");
            parsedField5 = ly.ConfigValues.read(type5, values.get("priority"), fileName + ":行" + lineNumber + ":字段 priority");
            parsedField6 = ly.ConfigValues.read(type6, values.get("reward1"), fileName + ":行" + lineNumber + ":字段 reward1");
            parsedField7 = ly.ConfigValues.read(type7, values.get("reward2"), fileName + ":行" + lineNumber + ":字段 reward2");
            parsedField8 = ly.ConfigValues.read(type8, values.get("bossSkill"), fileName + ":行" + lineNumber + ":字段 bossSkill");
            parsedField9 = ly.ConfigValues.read(type9, values.get("dropShow"), fileName + ":行" + lineNumber + ":字段 dropShow");
            parsedField10 = ly.ConfigValues.read(type10, values.get("bossDamageStatisticsType"), fileName + ":行" + lineNumber + ":字段 bossDamageStatisticsType");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          GuildChoiceHeroConfig config = new GuildChoiceHeroConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10);
          newList.add(config);
          newMap.put(config.id, config);
      });
      configList = List.copyOf(newList);
        configMap = Map.copyOf(newMap);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (GuildChoiceHeroConfig config : configList) config.afterLoad();
      new GuildChoiceHeroConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      configMap = Map.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<GuildChoiceHeroConfig> getConfigList() {
      return configList;
    }

    public Map<Integer, GuildChoiceHeroConfig> getConfigMap() {
      return configMap;
    }

    @Override
    public String getConfigFileName() {
      return "guildChoiceHero.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
