package ly;

/**
 * 公共服务器启动示例或独立运行入口，用于本地验证网络框架。
 */
public interface IServer {
  public void startUp(ServerContext serverContext) throws Exception;

  public void shutDown();
}
