package ly.rpc;

/** RPC 同步调用失败后的可靠投递保存策略。 */
public enum RpcFailSavePolicy {
  /** 不保存失败消息，保持旧逻辑。 */
  NONE,

  /** 连接不可用或网络写出失败时保存；网络失败也可能发生在远端执行之后，接口须支持幂等。 */
  SEND_FAILED_ONLY,

  /** 发送失败或等待响应超时时都保存；仅用于支持业务幂等的接口，补发保留原 callId。 */
  SEND_FAILED_OR_TIMEOUT
}
