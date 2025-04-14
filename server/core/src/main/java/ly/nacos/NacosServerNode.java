package ly.nacos;

import com.alibaba.nacos.api.naming.pojo.Instance;
import java.util.HashMap;
import java.util.Map;
import ly.config.ServerTypeEnum;

/*
 * 服务器的节点信息
 * Author: liuYang
 * Date: 2025/4/7
 * File: NacosServerNode
 */
public class NacosServerNode {
  ServerTypeEnum serverType;
  Instance instance;

  /** 该节点负载 */
  int loadNum;

  public String getServerId() {
    return instance.getInstanceId();
  }

  public String getServerName() {
    return instance.getMetadata().getOrDefault("serverName", "null");
  }

  public ServerTypeEnum getServerType() {
    return serverType;
  }

  public void setServerType(ServerTypeEnum serverType) {
    this.serverType = serverType;
  }

  public int getLoadNum() {
    return loadNum;
  }

  public void setLoadNum(int loadNum) {
    this.loadNum = loadNum;
  }

  public void updateLoadNum() {
    loadNum++;
    instance.getMetadata().put("loadNum", String.valueOf(loadNum));
  }

  public Instance getInstance() {
    return instance;
  }

  public void setInstance(Instance instance) {
    this.instance = instance;
  }

  public String getIp() {
    return instance.getIp();
  }

  public int getPort() {
    return instance.getPort();
  }

  public void update(Instance instance) {
    setInstance(instance);
    setLoadNum(Integer.parseInt(instance.getMetadata().getOrDefault("loadNum", "0")));
  }

  public static NacosServerNode createNacosServerNode(
      String serverId, ServerTypeEnum serverType, String ip, int port, Map<String, String> params) {
    if (params == null) {
      params = new HashMap<>();
    }
    params.put("serverType", serverType.getType());
    params.put("serverId", serverId);
    params.put("loadNum", "0");
    Instance instance = new Instance();
    instance.setInstanceId(serverId);
    instance.setPort(port);
    instance.setIp(ip);
    instance.setEnabled(true);
    instance.setHealthy(true);
    instance.setClusterName(serverType.getType());
    instance.getMetadata().putAll(params);
    NacosServerNode curNode = new NacosServerNode();
    curNode.setInstance(instance);
    curNode.setServerType(serverType);
    return curNode;
  }

  public static NacosServerNode createNacosServerNode(Instance instance) {
    NacosServerNode newNode = new NacosServerNode();
    newNode.setInstance(instance);
    newNode.setLoadNum(Integer.parseInt(instance.getMetadata().getOrDefault("loadNum", "0")));
    newNode.setServerType(ServerTypeEnum.getByType(instance.getClusterName()));
    return newNode;
  }

  @Override
  public String toString() {
    return "NacosServerNode{"
        + "serverType="
        + serverType
        + ", instance="
        + instance
        + ", loadNum="
        + loadNum
        + '}';
  }

  public boolean canUse() {
    return instance.isEnabled();
  }
}
