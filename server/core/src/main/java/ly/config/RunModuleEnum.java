package ly.config;

/*
 * Author: liuYang
 * Date: 2025/4/7
 * File: RunModuleEnum
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
