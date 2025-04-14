package ly.loginserver.result;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: ErrorCode
 */
public enum ErrorCode {
  OK(0, "成功"),
  FAIL(1, "失败"),
  SYSTEM_ERROR(2, "系统错误"),
  PARAM_ERROR(3, "参数错误"),
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
