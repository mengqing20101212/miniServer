package ly.rpc;

import ly.net.packet.AbstractMessagePacket;

/**
 * 可靠 RPC 重放回包处理器。
 *
 * <p>可靠 RPC 的保存和重试在 core 模块完成，但部分业务回包需要由具体服务器模块继续转发。
 * 例如 GateServer 重放发往 GameServer 的客户端业务包后，需要把 GameServer 回包再转给原客户端。
 */
@FunctionalInterface
public interface ReliableRpcReplayResponseHandler {

  /**
   * 处理一次可靠 RPC 重放收到的回包。
   *
   * @param message Redis outbox 中保存的可靠 RPC 消息
   * @param response 目标服务器返回的 RPC 回包
   * @return true 表示业务方已经消费该回包，可以删除 outbox 消息
   */
  boolean handle(ReliableRpcMessage message, AbstractMessagePacket response);
}
