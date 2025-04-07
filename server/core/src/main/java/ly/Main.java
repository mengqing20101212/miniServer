package ly;

public class Main {
  public static void main(String[] args) {
    String nacosUrl = "localhost:8848";
    String serverType = "GAME";
    String serverId = "game1001";
    String env = "ly";
    ServerContext.startUp(nacosUrl, serverType, serverId, env);
    try {
      Thread.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Hello, World!");
  }
}
