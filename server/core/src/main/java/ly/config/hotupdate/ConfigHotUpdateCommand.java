package ly.config.hotupdate;

import java.util.Map;

/** 一个不可变发布决定的传输对象；sequence 防止迟到指令把节点切回旧版本。 */
public class ConfigHotUpdateCommand {
    public String commandType;
    public String publishId;
    public String version;
    public String downloadUrl;
    public String reportUrl;
    public String digest;
    public long sequence;
    public long switchAtMillis;
    public Map<String, String> targets = Map.of();
}
