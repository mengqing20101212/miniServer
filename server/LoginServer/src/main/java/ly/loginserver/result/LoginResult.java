package ly.loginserver.result;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginResult
 */
public class LoginResult<T> {
  int result;
  String message;
  T data;

  public LoginResult(int result, String message, T data) {
    this.result = result;
    this.message = message;
    this.data = data;
  }

  public LoginResult(ErrorCode code) {
    this.result = code.getCode();
    this.message = code.getMessage();
  }

  public LoginResult(ErrorCode code, T data) {
    this.result = code.getCode();
    this.message = code.getMessage();
    this.data = data;
  }

  public LoginResult(int result, String message) {
    this.result = result;
    this.message = message;
  }

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public static <T> LoginResult success(T data) {
    return new LoginResult(ErrorCode.OK, data);
  }

  public static LoginResult fail(ErrorCode failCode) {
    return new LoginResult(failCode, null);
  }
}
