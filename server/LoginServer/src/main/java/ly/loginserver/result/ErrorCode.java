package ly.loginserver.result;

/**
 * 登录服 HTTP 返回结构，统一封装错误码、错误信息和接口数据。
 */
public enum ErrorCode {
  OK(0, "成功"),
  FAIL(1, "失败"),
  SYSTEM_ERROR(2, "系统错误"),
  PARAM_ERROR(3, "参数错误"),
  ACCOUNT_HAS_EXISTS(4, "该账号已经存在，不可在次注册"),
  ;
  private int code;
  private String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
