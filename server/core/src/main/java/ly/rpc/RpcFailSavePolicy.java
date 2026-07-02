package ly.rpc;

/** RPC 同步调用失败后的可靠投递保存策略。 */
public enum RpcFailSavePolicy {
  /** 不保存失败消息，保持旧逻辑。 */
  NONE,

  /** 只有连接失败、重连失败、消息没有写出去时保存。 */
  SEND_FAILED_ONLY,

  /** 发送失败或等待响应超时时都保存，后续按可靠消息异步补发。 */
  SEND_FAILED_OR_TIMEOUT
}
