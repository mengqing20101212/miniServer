package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class GuildLabelConfigCheckerBase extends AbstractConfigChecker<GuildLabelConfig> {
  @Override
  public String getConfigFileName() {
    return "guildLabel.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "type", "INT"),
        new ConfigColumnMeta(2, "degree", "INT"),
        new ConfigColumnMeta(3, "content", "STRING"),
        new ConfigColumnMeta(4, "color", "STRING"),
        new ConfigColumnMeta(5, "color2", "STRING"));
  }
}
