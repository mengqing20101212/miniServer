package ly.config.hotupdate;

/** Nacos 下发的配表热更指令。 */
public class ConfigHotUpdateCommand {
  public String commandType;
  public String publishId;
  public String version;
  public String downloadUrl;
  public String reportUrl;
  public long switchAtMillis;
}
