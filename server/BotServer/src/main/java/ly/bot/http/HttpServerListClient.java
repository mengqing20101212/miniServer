package ly.bot.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ly.LoggerDef;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 机器人 HTTP 客户端，负责从登录服获取注册、服务器列表等外部信息。
 */
public class HttpServerListClient {
    private static final Logger logger = LoggerDef.SystemLogger;
    @SuppressWarnings("unused")
    private static final Gson gson = new Gson();

    private final String baseUrl;

    public HttpServerListClient(String host, int port) {
        this.baseUrl = String.format("http://%s:%d", host, port);
    }

    public static class ServerNode {
        private String serverId;
        private String serverName;
        private int serverPort;
        private String serverIp;
        private String serverType;

        public String getServerId() { return serverId; }
        public String getServerName() { return serverName; }
        public int getServerPort() { return serverPort; }
        public String getServerIp() { return serverIp; }
        public String getServerType() { return serverType; }

        @Override
        public String toString() {
            return "ServerNode{" +
                    "serverId='" + serverId + '\'' +
                    ", serverName='" + serverName + '\'' +
                    ", serverPort=" + serverPort +
                    ", serverIp='" + serverIp + '\'' +
                    ", serverType='" + serverType + '\'' +
                    '}';
        }
    }

    public static class ServerListResult {
        private ServerNode gate;
        private List<ServerNode> gameServerList;
        private List<Object> players;
        private long accountId;
        private String token;

        public ServerNode getGate() { return gate; }
        public List<ServerNode> getGameServerList() { return gameServerList; }
        public List<Object> getPlayers() { return players; }
        public long getAccountId() { return accountId; }
        public String getToken() { return token; }

        public String getFirstGameServerId() {
            if (gameServerList != null && !gameServerList.isEmpty()) {
                return gameServerList.get(0).getServerId();
            }
            return null;
        }

        @Override
        public String toString() {
            return "ServerListResult{" +
                    "gate=" + gate +
                    ", gameServerList=" + gameServerList +
                    ", playersCount=" + (players != null ? players.size() : 0) +
                    ", accountId=" + accountId +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    public ServerListResult getServerList(String account) {
        return requestServerResult(
                String.format(
                        "%s/serverList?account=%s",
                        baseUrl, URLEncoder.encode(account, StandardCharsets.UTF_8)));
    }

    public ServerListResult register(String account, String channel) {
        return requestServerResult(
                String.format(
                        "%s/register?account=%s&channel=%s",
                        baseUrl,
                        URLEncoder.encode(account, StandardCharsets.UTF_8),
                        URLEncoder.encode(channel, StandardCharsets.UTF_8)));
    }

    private ServerListResult requestServerResult(String urlString) {
        try {
            logger.debug("request login server: {}", urlString);

            URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                logger.error("login server http failed, code={}", responseCode);
                return null;
            }

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String jsonResponse = response.toString();
            logger.debug("login server response: {}", jsonResponse);

            JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();
            int resultCode =
                    responseObj.has("result")
                            ? responseObj.get("result").getAsInt()
                            : responseObj.has("code")
                                    ? responseObj.get("code").getAsInt()
                                    : -1;
            if (resultCode != 0 || !responseObj.has("data") || responseObj.get("data").isJsonNull()) {
                String message =
                        responseObj.has("message")
                                ? responseObj.get("message").getAsString()
                                : responseObj.has("msg")
                                        ? responseObj.get("msg").getAsString()
                                        : "Unknown error";
                logger.error("login server business failed, result={}, message={}", resultCode, message);
                return null;
            }

            JsonObject dataObj = responseObj.getAsJsonObject("data");
            ServerListResult result = new ServerListResult();

            if (dataObj.has("accountId") && !dataObj.get("accountId").isJsonNull()) {
                result.accountId = dataObj.get("accountId").getAsLong();
            }
            if (dataObj.has("token") && !dataObj.get("token").isJsonNull()) {
                result.token = dataObj.get("token").getAsString();
            }
            if (dataObj.has("gate") && dataObj.get("gate").isJsonObject()) {
                result.gate = parseServerNode(dataObj.getAsJsonObject("gate"));
            }
            if (dataObj.has("gameServerList") && dataObj.get("gameServerList").isJsonArray()) {
                result.gameServerList =
                        gson.fromJson(
                                dataObj.get("gameServerList"),
                                new com.google.gson.reflect.TypeToken<List<ServerNode>>() {}.getType());
            }
            if (dataObj.has("players") && dataObj.get("players").isJsonArray()) {
                result.players =
                        gson.fromJson(
                                dataObj.get("players"),
                                new com.google.gson.reflect.TypeToken<List<Object>>() {}.getType());
            }

            logger.info(
                    "success get login server result, gate={}:{} accountId={} tokenPresent={}",
                    result.gate != null ? result.gate.getServerIp() : "null",
                    result.gate != null ? result.gate.getServerPort() : 0,
                    result.accountId,
                    result.token != null);
            return result;
        } catch (Exception e) {
            logger.error("request login server exception", e);
            return null;
        }
    }

    private ServerNode parseServerNode(JsonObject nodeObj) {
        ServerNode node = new ServerNode();
        if (nodeObj.has("serverId") && !nodeObj.get("serverId").isJsonNull()) {
            node.serverId = nodeObj.get("serverId").getAsString();
        }
        if (nodeObj.has("serverName") && !nodeObj.get("serverName").isJsonNull()) {
            node.serverName = nodeObj.get("serverName").getAsString();
        }
        if (nodeObj.has("serverPort") && !nodeObj.get("serverPort").isJsonNull()) {
            node.serverPort = nodeObj.get("serverPort").getAsInt();
        }
        if (nodeObj.has("serverIp") && !nodeObj.get("serverIp").isJsonNull()) {
            node.serverIp = nodeObj.get("serverIp").getAsString();
        }
        if (nodeObj.has("serverType") && !nodeObj.get("serverType").isJsonNull()) {
            node.serverType = nodeObj.get("serverType").getAsString();
        }
        return node;
    }
}
