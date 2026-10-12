package ly.rpc;

/** TIMEOUT/TRANSPORT do not imply that the remote business operation failed. */
public class RpcException extends RuntimeException {
  public enum Code { TIMEOUT, TRANSPORT, BUSY, CLOSED, INTERRUPTED, PROTOCOL }
  private final Code code;
  public RpcException(Code code, String message) { super(message); this.code = code; }
  public RpcException(Code code, String message, Throwable cause) { super(message, cause); this.code = code; }
  public Code code() { return code; }
}
