package ly.security;

public enum SecurityBanType {
  IP(1, "IP"),
  ACCOUNT(2, "账号"),
  PLAYER(3, "角色"),
  DEVICE(4, "设备");

  private final int code;
  private final String desc;

  SecurityBanType(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }

  public static SecurityBanType byCode(Integer code) {
    if (code == null) {
      return null;
    }
    for (SecurityBanType type : values()) {
      if (type.code == code) {
        return type;
      }
    }
    return null;
  }
}
