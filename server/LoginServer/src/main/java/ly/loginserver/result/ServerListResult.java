package ly.loginserver.result;

import java.util.ArrayList;
import java.util.List;
import ly.game.MiniPlayer;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: ServerListResult
 */
public class ServerListResult {

  ServerNode gate;
  List<ServerNode> gameServerList = new ArrayList<>();
  List<MiniPlayer> players = new ArrayList<>();

  public ServerListResult() {}

  public ServerNode getGate() {
    return gate;
  }

  public void setGate(ServerNode gate) {
    this.gate = gate;
  }

  public List<ServerNode> getGameServerList() {
    return gameServerList;
  }

  public void setGameServerList(List<ServerNode> gameServerList) {
    this.gameServerList = gameServerList;
  }

  public List<MiniPlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<MiniPlayer> players) {
    this.players = players;
  }

  public static class ServerNode {
    private String serverId;
    private String serverName;
    private int serverPort;
    private String serverIp;
    private String serverType;

    public ServerNode() {}

    public String getServerId() {
      return serverId;
    }

    public void setServerId(String serverId) {
      this.serverId = serverId;
    }

    public String getServerName() {
      return serverName;
    }

    public void setServerName(String serverName) {
      this.serverName = serverName;
    }

    public int getServerPort() {
      return serverPort;
    }

    public void setServerPort(int serverPort) {
      this.serverPort = serverPort;
    }

    public String getServerIp() {
      return serverIp;
    }

    public void setServerIp(String serverIp) {
      this.serverIp = serverIp;
    }

    public String getServerType() {
      return serverType;
    }

    public void setServerType(String serverType) {
      this.serverType = serverType;
    }
  }
}
