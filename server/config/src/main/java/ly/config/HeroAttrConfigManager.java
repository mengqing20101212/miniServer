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
 * File: HeroAttrConfigManager
 */
public class HeroAttrConfigManager implements InterfaceConfigManagerProxy {
  public static HeroAttrConfigManagerImpl getInstance() {
    return ly.ConfigService.getInstance().table(HeroAttrConfigManagerImpl.class);
  }

  @Override
  public AbstractConfigManger createTable() { return new HeroAttrConfigManagerImpl(); }

  @Override
  public String getConfigFileName() { return "heroAttr.xml"; }

  public static class HeroAttrConfigManagerImpl extends AbstractConfigManger {
    private List<HeroAttrConfig> configList = List.of();
    // @@@@@自定义属性开始区@@@@@

    // @@@@@自定义属性结束区@@@@@

    @Override
    public void reload(Logger logger, String configDir) throws ConfigLoadException {
      ly.ConfigValueType type0 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type1 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type2 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type3 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type4 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type5 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type6 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type7 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type8 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type9 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type10 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type11 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type12 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type13 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type14 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type15 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type16 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type17 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type18 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type19 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type20 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type21 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type22 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type23 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type24 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type25 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type26 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type27 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type28 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type29 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type30 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type31 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type32 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type33 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type34 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type35 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type36 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type37 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type38 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type39 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type40 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type41 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type42 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type43 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type44 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type45 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type46 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type47 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type48 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type49 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type50 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type51 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type52 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type53 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type54 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type55 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type56 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type57 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type58 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type59 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type60 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type61 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type62 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type63 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type64 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type65 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type66 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type67 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type68 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type69 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type70 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type71 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type72 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type73 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type74 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type75 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type76 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type77 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type78 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type79 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type80 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type81 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type82 = ly.ConfigValueType.parse("list<int>");
      ly.ConfigValueType type83 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type84 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type85 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type86 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type87 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type88 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type89 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type90 = ly.ConfigValueType.parse("int");
      ly.ConfigValueType type91 = ly.ConfigValueType.parse("int");
      String fileName = configDir + File.separator + getConfigFileName();
      HeroAttrConfigChecker checker = new HeroAttrConfigChecker();
      List<HeroAttrConfig> newList = new ArrayList<>();
      ly.ConfigXml.read(java.nio.file.Path.of(fileName), checker.getExpectedColumns(), (lineNumber, values) -> {
          int parsedField0 = 0;
          int parsedField1 = 0;
          java.util.List<Integer> parsedField2 = null;
          java.util.List<Integer> parsedField3 = null;
          java.util.List<Integer> parsedField4 = null;
          java.util.List<Integer> parsedField5 = null;
          java.util.List<Integer> parsedField6 = null;
          java.util.List<Integer> parsedField7 = null;
          java.util.List<Integer> parsedField8 = null;
          java.util.List<Integer> parsedField9 = null;
          int parsedField10 = 0;
          int parsedField11 = 0;
          int parsedField12 = 0;
          int parsedField13 = 0;
          int parsedField14 = 0;
          int parsedField15 = 0;
          int parsedField16 = 0;
          int parsedField17 = 0;
          int parsedField18 = 0;
          int parsedField19 = 0;
          int parsedField20 = 0;
          int parsedField21 = 0;
          int parsedField22 = 0;
          int parsedField23 = 0;
          int parsedField24 = 0;
          int parsedField25 = 0;
          int parsedField26 = 0;
          int parsedField27 = 0;
          int parsedField28 = 0;
          int parsedField29 = 0;
          int parsedField30 = 0;
          int parsedField31 = 0;
          int parsedField32 = 0;
          int parsedField33 = 0;
          int parsedField34 = 0;
          int parsedField35 = 0;
          int parsedField36 = 0;
          int parsedField37 = 0;
          int parsedField38 = 0;
          int parsedField39 = 0;
          int parsedField40 = 0;
          int parsedField41 = 0;
          int parsedField42 = 0;
          int parsedField43 = 0;
          int parsedField44 = 0;
          int parsedField45 = 0;
          int parsedField46 = 0;
          int parsedField47 = 0;
          int parsedField48 = 0;
          int parsedField49 = 0;
          int parsedField50 = 0;
          int parsedField51 = 0;
          int parsedField52 = 0;
          int parsedField53 = 0;
          int parsedField54 = 0;
          int parsedField55 = 0;
          int parsedField56 = 0;
          int parsedField57 = 0;
          int parsedField58 = 0;
          int parsedField59 = 0;
          int parsedField60 = 0;
          int parsedField61 = 0;
          int parsedField62 = 0;
          int parsedField63 = 0;
          int parsedField64 = 0;
          int parsedField65 = 0;
          int parsedField66 = 0;
          int parsedField67 = 0;
          int parsedField68 = 0;
          int parsedField69 = 0;
          int parsedField70 = 0;
          int parsedField71 = 0;
          int parsedField72 = 0;
          int parsedField73 = 0;
          int parsedField74 = 0;
          int parsedField75 = 0;
          int parsedField76 = 0;
          int parsedField77 = 0;
          int parsedField78 = 0;
          int parsedField79 = 0;
          int parsedField80 = 0;
          int parsedField81 = 0;
          java.util.List<Integer> parsedField82 = null;
          int parsedField83 = 0;
          int parsedField84 = 0;
          int parsedField85 = 0;
          int parsedField86 = 0;
          int parsedField87 = 0;
          int parsedField88 = 0;
          int parsedField89 = 0;
          int parsedField90 = 0;
          int parsedField91 = 0;
          try {
            parsedField0 = ly.ConfigValues.read(type0, values.get("modelName"), fileName + ":行" + lineNumber + ":字段 modelName");
            parsedField1 = ly.ConfigValues.read(type1, values.get("level"), fileName + ":行" + lineNumber + ":字段 level");
            parsedField2 = ly.ConfigValues.read(type2, values.get("maxHP"), fileName + ":行" + lineNumber + ":字段 maxHP");
            parsedField3 = ly.ConfigValues.read(type3, values.get("attack"), fileName + ":行" + lineNumber + ":字段 attack");
            parsedField4 = ly.ConfigValues.read(type4, values.get("defence"), fileName + ":行" + lineNumber + ":字段 defence");
            parsedField5 = ly.ConfigValues.read(type5, values.get("speed"), fileName + ":行" + lineNumber + ":字段 speed");
            parsedField6 = ly.ConfigValues.read(type6, values.get("crit"), fileName + ":行" + lineNumber + ":字段 crit");
            parsedField7 = ly.ConfigValues.read(type7, values.get("critRatio"), fileName + ":行" + lineNumber + ":字段 critRatio");
            parsedField8 = ly.ConfigValues.read(type8, values.get("effectHit"), fileName + ":行" + lineNumber + ":字段 effectHit");
            parsedField9 = ly.ConfigValues.read(type9, values.get("effectDodge"), fileName + ":行" + lineNumber + ":字段 effectDodge");
            parsedField10 = ly.ConfigValues.read(type10, values.get("maxHP2"), fileName + ":行" + lineNumber + ":字段 maxHP2");
            parsedField11 = ly.ConfigValues.read(type11, values.get("attack2"), fileName + ":行" + lineNumber + ":字段 attack2");
            parsedField12 = ly.ConfigValues.read(type12, values.get("defence2"), fileName + ":行" + lineNumber + ":字段 defence2");
            parsedField13 = ly.ConfigValues.read(type13, values.get("speed2"), fileName + ":行" + lineNumber + ":字段 speed2");
            parsedField14 = ly.ConfigValues.read(type14, values.get("crit2"), fileName + ":行" + lineNumber + ":字段 crit2");
            parsedField15 = ly.ConfigValues.read(type15, values.get("critRatio2"), fileName + ":行" + lineNumber + ":字段 critRatio2");
            parsedField16 = ly.ConfigValues.read(type16, values.get("effectHit2"), fileName + ":行" + lineNumber + ":字段 effectHit2");
            parsedField17 = ly.ConfigValues.read(type17, values.get("effectDodge2"), fileName + ":行" + lineNumber + ":字段 effectDodge2");
            parsedField18 = ly.ConfigValues.read(type18, values.get("maxHP3"), fileName + ":行" + lineNumber + ":字段 maxHP3");
            parsedField19 = ly.ConfigValues.read(type19, values.get("attack3"), fileName + ":行" + lineNumber + ":字段 attack3");
            parsedField20 = ly.ConfigValues.read(type20, values.get("defence3"), fileName + ":行" + lineNumber + ":字段 defence3");
            parsedField21 = ly.ConfigValues.read(type21, values.get("speed3"), fileName + ":行" + lineNumber + ":字段 speed3");
            parsedField22 = ly.ConfigValues.read(type22, values.get("crit3"), fileName + ":行" + lineNumber + ":字段 crit3");
            parsedField23 = ly.ConfigValues.read(type23, values.get("critRatio3"), fileName + ":行" + lineNumber + ":字段 critRatio3");
            parsedField24 = ly.ConfigValues.read(type24, values.get("effectHit3"), fileName + ":行" + lineNumber + ":字段 effectHit3");
            parsedField25 = ly.ConfigValues.read(type25, values.get("effectDodge3"), fileName + ":行" + lineNumber + ":字段 effectDodge3");
            parsedField26 = ly.ConfigValues.read(type26, values.get("maxHP4"), fileName + ":行" + lineNumber + ":字段 maxHP4");
            parsedField27 = ly.ConfigValues.read(type27, values.get("attack4"), fileName + ":行" + lineNumber + ":字段 attack4");
            parsedField28 = ly.ConfigValues.read(type28, values.get("defence4"), fileName + ":行" + lineNumber + ":字段 defence4");
            parsedField29 = ly.ConfigValues.read(type29, values.get("speed4"), fileName + ":行" + lineNumber + ":字段 speed4");
            parsedField30 = ly.ConfigValues.read(type30, values.get("crit4"), fileName + ":行" + lineNumber + ":字段 crit4");
            parsedField31 = ly.ConfigValues.read(type31, values.get("critRatio4"), fileName + ":行" + lineNumber + ":字段 critRatio4");
            parsedField32 = ly.ConfigValues.read(type32, values.get("effectHit4"), fileName + ":行" + lineNumber + ":字段 effectHit4");
            parsedField33 = ly.ConfigValues.read(type33, values.get("effectDodge4"), fileName + ":行" + lineNumber + ":字段 effectDodge4");
            parsedField34 = ly.ConfigValues.read(type34, values.get("maxHP5"), fileName + ":行" + lineNumber + ":字段 maxHP5");
            parsedField35 = ly.ConfigValues.read(type35, values.get("attack5"), fileName + ":行" + lineNumber + ":字段 attack5");
            parsedField36 = ly.ConfigValues.read(type36, values.get("defence5"), fileName + ":行" + lineNumber + ":字段 defence5");
            parsedField37 = ly.ConfigValues.read(type37, values.get("speed5"), fileName + ":行" + lineNumber + ":字段 speed5");
            parsedField38 = ly.ConfigValues.read(type38, values.get("crit5"), fileName + ":行" + lineNumber + ":字段 crit5");
            parsedField39 = ly.ConfigValues.read(type39, values.get("critRatio5"), fileName + ":行" + lineNumber + ":字段 critRatio5");
            parsedField40 = ly.ConfigValues.read(type40, values.get("effectHit5"), fileName + ":行" + lineNumber + ":字段 effectHit5");
            parsedField41 = ly.ConfigValues.read(type41, values.get("effectDodge5"), fileName + ":行" + lineNumber + ":字段 effectDodge5");
            parsedField42 = ly.ConfigValues.read(type42, values.get("maxHP6"), fileName + ":行" + lineNumber + ":字段 maxHP6");
            parsedField43 = ly.ConfigValues.read(type43, values.get("attack6"), fileName + ":行" + lineNumber + ":字段 attack6");
            parsedField44 = ly.ConfigValues.read(type44, values.get("defence6"), fileName + ":行" + lineNumber + ":字段 defence6");
            parsedField45 = ly.ConfigValues.read(type45, values.get("speed6"), fileName + ":行" + lineNumber + ":字段 speed6");
            parsedField46 = ly.ConfigValues.read(type46, values.get("crit6"), fileName + ":行" + lineNumber + ":字段 crit6");
            parsedField47 = ly.ConfigValues.read(type47, values.get("critRatio6"), fileName + ":行" + lineNumber + ":字段 critRatio6");
            parsedField48 = ly.ConfigValues.read(type48, values.get("effectHit6"), fileName + ":行" + lineNumber + ":字段 effectHit6");
            parsedField49 = ly.ConfigValues.read(type49, values.get("effectDodge6"), fileName + ":行" + lineNumber + ":字段 effectDodge6");
            parsedField50 = ly.ConfigValues.read(type50, values.get("maxHP7"), fileName + ":行" + lineNumber + ":字段 maxHP7");
            parsedField51 = ly.ConfigValues.read(type51, values.get("attack7"), fileName + ":行" + lineNumber + ":字段 attack7");
            parsedField52 = ly.ConfigValues.read(type52, values.get("defence7"), fileName + ":行" + lineNumber + ":字段 defence7");
            parsedField53 = ly.ConfigValues.read(type53, values.get("speed7"), fileName + ":行" + lineNumber + ":字段 speed7");
            parsedField54 = ly.ConfigValues.read(type54, values.get("crit7"), fileName + ":行" + lineNumber + ":字段 crit7");
            parsedField55 = ly.ConfigValues.read(type55, values.get("critRatio7"), fileName + ":行" + lineNumber + ":字段 critRatio7");
            parsedField56 = ly.ConfigValues.read(type56, values.get("effectHit7"), fileName + ":行" + lineNumber + ":字段 effectHit7");
            parsedField57 = ly.ConfigValues.read(type57, values.get("effectDodge7"), fileName + ":行" + lineNumber + ":字段 effectDodge7");
            parsedField58 = ly.ConfigValues.read(type58, values.get("maxHP8"), fileName + ":行" + lineNumber + ":字段 maxHP8");
            parsedField59 = ly.ConfigValues.read(type59, values.get("attack8"), fileName + ":行" + lineNumber + ":字段 attack8");
            parsedField60 = ly.ConfigValues.read(type60, values.get("defence8"), fileName + ":行" + lineNumber + ":字段 defence8");
            parsedField61 = ly.ConfigValues.read(type61, values.get("speed8"), fileName + ":行" + lineNumber + ":字段 speed8");
            parsedField62 = ly.ConfigValues.read(type62, values.get("crit8"), fileName + ":行" + lineNumber + ":字段 crit8");
            parsedField63 = ly.ConfigValues.read(type63, values.get("critRatio8"), fileName + ":行" + lineNumber + ":字段 critRatio8");
            parsedField64 = ly.ConfigValues.read(type64, values.get("effectHit8"), fileName + ":行" + lineNumber + ":字段 effectHit8");
            parsedField65 = ly.ConfigValues.read(type65, values.get("effectDodge8"), fileName + ":行" + lineNumber + ":字段 effectDodge8");
            parsedField66 = ly.ConfigValues.read(type66, values.get("maxHP9"), fileName + ":行" + lineNumber + ":字段 maxHP9");
            parsedField67 = ly.ConfigValues.read(type67, values.get("attack9"), fileName + ":行" + lineNumber + ":字段 attack9");
            parsedField68 = ly.ConfigValues.read(type68, values.get("defence9"), fileName + ":行" + lineNumber + ":字段 defence9");
            parsedField69 = ly.ConfigValues.read(type69, values.get("speed9"), fileName + ":行" + lineNumber + ":字段 speed9");
            parsedField70 = ly.ConfigValues.read(type70, values.get("crit9"), fileName + ":行" + lineNumber + ":字段 crit9");
            parsedField71 = ly.ConfigValues.read(type71, values.get("critRatio9"), fileName + ":行" + lineNumber + ":字段 critRatio9");
            parsedField72 = ly.ConfigValues.read(type72, values.get("effectHit9"), fileName + ":行" + lineNumber + ":字段 effectHit9");
            parsedField73 = ly.ConfigValues.read(type73, values.get("effectDodge9"), fileName + ":行" + lineNumber + ":字段 effectDodge9");
            parsedField74 = ly.ConfigValues.read(type74, values.get("maxHP10"), fileName + ":行" + lineNumber + ":字段 maxHP10");
            parsedField75 = ly.ConfigValues.read(type75, values.get("attack10"), fileName + ":行" + lineNumber + ":字段 attack10");
            parsedField76 = ly.ConfigValues.read(type76, values.get("defence10"), fileName + ":行" + lineNumber + ":字段 defence10");
            parsedField77 = ly.ConfigValues.read(type77, values.get("speed10"), fileName + ":行" + lineNumber + ":字段 speed10");
            parsedField78 = ly.ConfigValues.read(type78, values.get("crit10"), fileName + ":行" + lineNumber + ":字段 crit10");
            parsedField79 = ly.ConfigValues.read(type79, values.get("critRatio10"), fileName + ":行" + lineNumber + ":字段 critRatio10");
            parsedField80 = ly.ConfigValues.read(type80, values.get("effectHit10"), fileName + ":行" + lineNumber + ":字段 effectHit10");
            parsedField81 = ly.ConfigValues.read(type81, values.get("effectDodge10"), fileName + ":行" + lineNumber + ":字段 effectDodge10");
            parsedField82 = ly.ConfigValues.read(type82, values.get("spCoe"), fileName + ":行" + lineNumber + ":字段 spCoe");
            parsedField83 = ly.ConfigValues.read(type83, values.get("spCoe2"), fileName + ":行" + lineNumber + ":字段 spCoe2");
            parsedField84 = ly.ConfigValues.read(type84, values.get("spCoe3"), fileName + ":行" + lineNumber + ":字段 spCoe3");
            parsedField85 = ly.ConfigValues.read(type85, values.get("spCoe4"), fileName + ":行" + lineNumber + ":字段 spCoe4");
            parsedField86 = ly.ConfigValues.read(type86, values.get("spCoe5"), fileName + ":行" + lineNumber + ":字段 spCoe5");
            parsedField87 = ly.ConfigValues.read(type87, values.get("spCoe6"), fileName + ":行" + lineNumber + ":字段 spCoe6");
            parsedField88 = ly.ConfigValues.read(type88, values.get("spCoe7"), fileName + ":行" + lineNumber + ":字段 spCoe7");
            parsedField89 = ly.ConfigValues.read(type89, values.get("spCoe8"), fileName + ":行" + lineNumber + ":字段 spCoe8");
            parsedField90 = ly.ConfigValues.read(type90, values.get("spCoe9"), fileName + ":行" + lineNumber + ":字段 spCoe9");
            parsedField91 = ly.ConfigValues.read(type91, values.get("spCoe10"), fileName + ":行" + lineNumber + ":字段 spCoe10");
          } catch (ConfigLoadException error) {
            logger.error(error.getMessage());
            return;
          }
          HeroAttrConfig config = new HeroAttrConfig(parsedField0, parsedField1, parsedField2, parsedField3, parsedField4, parsedField5, parsedField6, parsedField7, parsedField8, parsedField9, parsedField10, parsedField11, parsedField12, parsedField13, parsedField14, parsedField15, parsedField16, parsedField17, parsedField18, parsedField19, parsedField20, parsedField21, parsedField22, parsedField23, parsedField24, parsedField25, parsedField26, parsedField27, parsedField28, parsedField29, parsedField30, parsedField31, parsedField32, parsedField33, parsedField34, parsedField35, parsedField36, parsedField37, parsedField38, parsedField39, parsedField40, parsedField41, parsedField42, parsedField43, parsedField44, parsedField45, parsedField46, parsedField47, parsedField48, parsedField49, parsedField50, parsedField51, parsedField52, parsedField53, parsedField54, parsedField55, parsedField56, parsedField57, parsedField58, parsedField59, parsedField60, parsedField61, parsedField62, parsedField63, parsedField64, parsedField65, parsedField66, parsedField67, parsedField68, parsedField69, parsedField70, parsedField71, parsedField72, parsedField73, parsedField74, parsedField75, parsedField76, parsedField77, parsedField78, parsedField79, parsedField80, parsedField81, parsedField82, parsedField83, parsedField84, parsedField85, parsedField86, parsedField87, parsedField88, parsedField89, parsedField90, parsedField91);
          newList.add(config);
      });
      configList = List.copyOf(newList);
    }

    @Override
    protected void finishLoad(Logger logger) {
      for (HeroAttrConfig config : configList) config.afterLoad();
      new HeroAttrConfigChecker().checkAfterParse(logger, configList);
    }

    @Override
    public void clear() {
      configList = List.of();
      // @@@@@自定义clear方法开始区@@@@@

      // @@@@@自定义clear方法结束区@@@@@
    }

    public List<HeroAttrConfig> getConfigList() {
      return configList;
    }

    @Override
    public String getConfigFileName() {
      return "heroAttr.xml";
    }

    // @@@@@自定义方法开始区@@@@@
@Override
    protected void afterLoad() {

    }
    // @@@@@自定义方法结束区@@@@@
  }
}
