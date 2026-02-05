package ly.bot.http;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ly.LoggerDef;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP客户端，用于从LoginServer获取服务器列表
 * 
 * Author: OpenClaw AI Assistant
 * Date: 2026/2/5
 * File: HttpServerListClient
 */
public class HttpServerListClient {
    private static final Logger logger = LoggerDef.SystemLogger;
    private static final Gson gson = new Gson();

    /**
     * 服务器节点信息
     */
    public static class ServerNode {
        private String serverId;
        private String serverName;
        private int serverPort;
        private String serverIp;
        private String serverType;

        // Getter方法
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

    /**
     * 服务器列表结果
     */
    public static class ServerListResult {
        private ServerNode gate;
        private java.util.List<ServerNode> gameServerList;
        private java.util.List<Object> players; // 使用Object类型，因为不需要具体处理
        private long accountId;
        private String token;

        // Getter方法
        public ServerNode getGate() { return gate; }
        public java.util.List<ServerNode> getGameServerList() { return gameServerList; }
        public java.util.List<Object> getPlayers() { return players; }
        public long getAccountId() { return accountId; }
        public String getToken() { return token; }

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

    /**
     * 从LoginServer获取服务器列表
     * 
     * @param loginServerHost LoginServer主机地址
     * @param loginServerPort LoginServer端口
     * @param account 账号
     * @return 服务器列表结果
     */
    public static ServerListResult getServerList(String loginServerHost, int loginServerPort, String account) {
        try {
            // 构建请求URL
            String urlString = String.format("http://%s:%d/serverList?account=%s", 
                loginServerHost, loginServerPort, account);
            
            logger.debug("正在请求服务器列表: {}", urlString);
            
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求属性
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000); // 5秒连接超时
            connection.setReadTimeout(10000);   // 10秒读取超时
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                String jsonResponse = response.toString();
                logger.debug("收到服务器列表响应: {}", jsonResponse);
                
                // 解析响应 - 响应格式是 {"code":0,"msg":"success","data":{...}}
                // 我们需要解析外层，然后获取data部分
                try {
                    JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();
                    int code = responseObj.get("code").getAsInt();
                    
                    if (code == 0) { // 成功
                        JsonObject dataObj = responseObj.getAsJsonObject("data");
                        
                        // 手动解析ServerListResult对象
                        ServerListResult result = new ServerListResult();
                        
                        // 解析accountId和token
                        if (dataObj.has("accountId")) {
                            result.accountId = dataObj.get("accountId").getAsLong();
                        }
                        if (dataObj.has("token")) {
                            result.token = dataObj.get("token").getAsString();
                        }
                        
                        // 解析gate服务器节点
                        if (dataObj.has("gate") && dataObj.get("gate").isJsonObject()) {
                            JsonObject gateObj = dataObj.getAsJsonObject("gate");
                            ServerNode gateNode = new ServerNode();
                            
                            if (gateObj.has("serverId")) gateNode.serverId = gateObj.get("serverId").getAsString();
                            if (gateObj.has("serverName")) gateNode.serverName = gateObj.get("serverName").getAsString();
                            if (gateObj.has("serverPort")) gateNode.serverPort = gateObj.get("serverPort").getAsInt();
                            if (gateObj.has("serverIp")) gateNode.serverIp = gateObj.get("serverIp").getAsString();
                            if (gateObj.has("serverType")) gateNode.serverType = gateObj.get("serverType").getAsString();
                            
                            result.gate = gateNode;
                        }
                        
                        logger.info("成功获取服务器列表，Gate: {}:{}", 
                            result.gate != null ? result.gate.getServerIp() : "null", 
                            result.gate != null ? result.gate.getServerPort() : "null");
                        
                        return result;
                    } else {
                        String msg = responseObj.has("msg") ? responseObj.get("msg").getAsString() : "Unknown error";
                        logger.error("服务器返回错误码 {}: {}", code, msg);
                        return null;
                    }
                } catch (Exception e) {
                    logger.error("解析服务器列表响应失败: {}", jsonResponse, e);
                    return null;
                }
            } else {
                logger.error("获取服务器列表失败，HTTP响应码: {}", responseCode);
                return null;
            }
        } catch (Exception e) {
            logger.error("请求服务器列表时发生异常", e);
            return null;
        }
    }
}