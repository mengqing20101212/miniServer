package ly.loginserver.result;

import ly.game.MiniPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * 登录服 HTTP 返回结构，统一封装错误码、错误信息和接口数据。
 */
public class ServerListResult {

    ServerNode gate;
    List<ServerNode> gameServerList = new ArrayList<>();
    List<MiniPlayer> players = new ArrayList<>();
    long accountId;
    String token;

    public ServerListResult() {
    }

    public ServerNode getGate() {
        return gate;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

        public ServerNode() {
        }

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
