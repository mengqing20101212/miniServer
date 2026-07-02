package ly.config;

import java.util.List;
import ly.AbstractConfigChecker;
import ly.ConfigColumnMeta;

/** 自动生成的配置表检测基类，请不要手动修改。 */
public abstract class Server_iniConfigCheckerBase extends AbstractConfigChecker<Server_iniConfig> {
  @Override
  public String getConfigFileName() {
    return "server_ini.txt";
  }

  @Override
  public List<ConfigColumnMeta> getExpectedColumns() {
    return List.of(
        new ConfigColumnMeta(0, "id", "INT"),
        new ConfigColumnMeta(1, "name", "STRING"),
        new ConfigColumnMeta(2, "des", "STRING"),
        new ConfigColumnMeta(3, "dev_default", "STRING"),
        new ConfigColumnMeta(4, "a01_default", "STRING"),
        new ConfigColumnMeta(5, "b02_default", "STRING"),
        new ConfigColumnMeta(6, "c03_default", "STRING"),
        new ConfigColumnMeta(7, "d04_default", "STRING"),
        new ConfigColumnMeta(8, "e05_default", "STRING"),
        new ConfigColumnMeta(9, "f06_default", "STRING"),
        new ConfigColumnMeta(10, "g07_default", "STRING"),
        new ConfigColumnMeta(11, "h08_default", "STRING"),
        new ConfigColumnMeta(12, "i09_default", "STRING"),
        new ConfigColumnMeta(13, "weekly_multinode", "STRING"),
        new ConfigColumnMeta(14, "korea_dev_default", "STRING"),
        new ConfigColumnMeta(15, "korea_weekly_default", "STRING"),
        new ConfigColumnMeta(16, "korea_gray_default", "STRING"),
        new ConfigColumnMeta(17, "tencent_dev1_default", "STRING"),
        new ConfigColumnMeta(18, "tencent_dev2_default", "STRING"),
        new ConfigColumnMeta(19, "tencent_dev3_default", "STRING"),
        new ConfigColumnMeta(20, "tencent_dev4_default", "STRING"),
        new ConfigColumnMeta(21, "tencent_dev5_default", "STRING"),
        new ConfigColumnMeta(22, "tencent_gray_default", "STRING"),
        new ConfigColumnMeta(23, "tencent_official_default", "STRING"),
        new ConfigColumnMeta(24, "tencent_ios_cert_default", "STRING"),
        new ConfigColumnMeta(25, "tencent_release_default", "STRING"),
        new ConfigColumnMeta(26, "tencent_stress_test_default", "STRING"),
        new ConfigColumnMeta(27, "tencent_idc_test_default", "STRING"));
  }
}
