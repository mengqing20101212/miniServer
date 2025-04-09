package ly;

/*
 * Author: liuYang
 * Date: 2025/4/7
 * File: IServer
 */
public interface IServer {
  public void startUp(ServerContext serverContext) throws Exception;

  public void shutDown();
}
