package ly.config;

/*
 * 服务器 类型
 * Author: liuYang
 * Date: 2025/4/7
 * File: ServerTypeEnum
 */
public enum ServerTypeEnum {
  GAME("GAME", "游戏服"),
  GM("GM", "GMT 管理服"),
  LOGIN("LOGIN", "登录服务器"),
  GATE("GATE", "网关服务器"),
  CROSS("CROSS", "跨服玩法"),
  RECHARGE("RECHARGE", "充值服务器"),
  ;
  private String type;
  private String description;

  ServerTypeEnum(String type, String description) {
    this.type = type;
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static ServerTypeEnum getByType(String type) {
    for (ServerTypeEnum serverTypeEnum : ServerTypeEnum.values()) {
      if (serverTypeEnum.type.equals(type)) {
        return serverTypeEnum;
      }
    }
    throw new IllegalArgumentException("服务器类型不对 type:" + type);
  }
}
