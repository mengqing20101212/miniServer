package ly.config;

/**
 * 公共配置模型或枚举，描述服务器启动、数据库、Redis 和模块开关等基础参数。
 */
public enum RunModuleEnum {
  /** 测试 debug 模式，日志级别比较低 */
  TEST("TEST"),
  /*** 正式环境 校验比较严格*/
  ONLINE("ONLINE"),
  /** 压测模式 部分功能不校验 */
  PRESS("PRESS");
  private String module;

  RunModuleEnum(String module) {
    this.module = module;
  }

  public static RunModuleEnum getRunModuleEnum(String module) {
    RunModuleEnum[] values = RunModuleEnum.values();
    for (RunModuleEnum value : values) {
      if (value.module.equals(module)) {
        return value;
      }
    }

    throw new IllegalArgumentException("Unknown module: " + module);
  }
}
