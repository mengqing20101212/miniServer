package ly.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.listener.AbstractNamingChangeListener;
import com.alibaba.nacos.client.naming.listener.NamingChangeEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ly.LoggerDef;
import ly.ServerContext;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import ly.utils.CommonUtils;
import org.apache.logging.log4j.Logger;

/*
 * Nacos 服务 用于节点的发现，注册，以及配置文件的监听
 * Author: liuYang
 * Date: 2025/4/7
 * File: NacosService
 */
public class NacosService {
  Logger logger = LoggerDef.SystemLogger;
  private static final String RPC_NODE_LIST_SERVICE = "rpc_node_list_service";
  private static NacosService instance = new NacosService();
  private static ExecutorService executorService = Executors.newFixedThreadPool(1);
  private static NamingService namingService;
  int a = 0;

  /***nacos 请求操作最大超时时间戳*/
  static final long MAX_TIME_OUT = 5000;

  /** 存活的服务器节点信息 */
  private Map<String, NacosServerNode> nodeMap = new ConcurrentHashMap<>();

  private static NacosServerNode currentNode;

  private NacosService() {}

  public static NacosService getInstance() {
    return instance;
  }

  public void startUp(String nacosUrl, ServerTypeEnum serverType, String serverId, String env) {
    logger.info("开始启动 Nacos ");
    long startTime = System.currentTimeMillis();
    try {
      // 1 获取nacos 服务器配置
      Properties properties = new Properties();
      properties.put(PropertyKeyConst.SERVER_ADDR, nacosUrl);
      properties.setProperty(PropertyKeyConst.NAMESPACE, env);
      ConfigService configService = NacosFactory.createConfigService(properties);
      // 解析 服务器配置
      getServerConfig(configService, serverType, serverId);

      namingService = NacosFactory.createNamingService(properties);
      // 注册当前节点实例
      registerServerNode(namingService);

      // 监听节点变化
      subscribeServerNode(namingService);
    } catch (Exception e) {
      System.out.println(" 服务器 Nacos 启动失败 ");
      e.printStackTrace();
      System.exit(1);
    }
    long endTime = System.currentTimeMillis();
    logger.info(String.format(" Nacos 启动成功,耗时: %dms ", endTime - startTime));
  }

  private void subscribeServerNode(NamingService namingService) throws NacosException {
    EventListener serviceListener =
        new AbstractNamingChangeListener() {
          @Override
          public void onChange(NamingChangeEvent event) {
            if (event.isAdded()) {
              event
                  .getAddedInstances()
                  .forEach(
                      data -> {
                        addNewNode(data);
                      });
            }
            if (event.isRemoved()) {
              event
                  .getRemovedInstances()
                  .forEach(
                      data -> {
                        delNode(data.getInstanceId());
                      });
            }
            if (event.isModified()) {
              event
                  .getModifiedInstances()
                  .forEach(
                      data -> {
                        updateNode(data);
                      });
            }
          }

          @Override
          public Executor getExecutor() {
            return executorService;
          }
        };

    namingService.subscribe(RPC_NODE_LIST_SERVICE, ServerContext.ENV, serviceListener);
  }

  private void updateNode(Instance instance) {
    NacosServerNode node = nodeMap.get(instance.getInstanceId());
    if (node == null) {
      logger.info(String.format("updateNode 当前节点数量:%d 该节点不存在，直接添加: %s ", nodeMap.size(), instance));
      addNewNode(instance);
    } else {
      node.update(instance);
      logger.info(String.format("updateNode 当前节点数量:%d, 更新服务器节点: %s", nodeMap.size(), instance));
    }
  }

  private void delNode(String instanceId) {
    NacosServerNode delNode = nodeMap.remove(instanceId);
    logger.info(String.format("当前节点数量:%d, 删除服务器节点: %s", nodeMap.size(), delNode));
    nodeMap.remove(instanceId);
  }

  private void addNewNode(Instance instance) {
    logger.info(String.format("当前节点数量:%d, 新增服务器节点: %s", nodeMap.size(), instance));
    NacosServerNode newNode = NacosServerNode.createNacosServerNode(instance);
    if (newNode.getServerId().equals(ServerContext.getServerId())) {
      currentNode = newNode;
    }
    nodeMap.put(newNode.getServerId(), newNode);
  }

  public static NacosServerNode getCurrentNode() {
    return currentNode;
  }

  private void registerServerNode(NamingService namingService) throws NacosException {
    NacosServerNode curNode =
        NacosServerNode.createNacosServerNode(
            ServerContext.getServerId(),
            ServerContext.serverType,
            ServerContext.serverConfig.serverIp,
            ServerContext.serverConfig.getServerPort(),
            new HashMap<>());
    curNode.setLoadNum(0);
    namingService.registerInstance(RPC_NODE_LIST_SERVICE, ServerContext.ENV, curNode.getInstance());
  }

  public Map<String, NacosServerNode> getNodeMap() {
    return nodeMap;
  }

  public List<NacosServerNode> getNodeList(ServerTypeEnum serverType) {
    return nodeMap.values().stream()
        .filter(
            node -> {
              return node.serverType == serverType;
            })
        .toList();
  }

  private void getServerConfig(
      ConfigService configService, ServerTypeEnum serverType, String serverId) throws Exception {
    String configStr = configService.getConfig(serverId, serverType.getType(), MAX_TIME_OUT);
    if (configStr != null) {
      parserServerConfig(configStr);
    } else {
      throw new RuntimeException("获取nacos 配置失败");
    }

    configService.addListener(
        serverId,
        serverType.getType(),
        new Listener() {
          @Override
          public Executor getExecutor() {
            return executorService;
          }

          @Override
          public void receiveConfigInfo(String s) {
            logger.info("服务器配置: \n" + s);
            parserServerConfig(s);
          }
        });
  }

  private void parserServerConfig(String str) {
    try {
      ServerContext.serverConfig = CommonUtils.parserYaml(ServerConfig.class, str);
    } catch (Exception e) {
      logger.error(String.format("解析配置文件报错 \n\n  %s, \n\n%s", str, e.getMessage()));
      e.printStackTrace();
    }
  }

  public void shutdown() throws NacosException {
    namingService.shutDown();
    nodeMap.clear();
    logger.info("关闭 Nacos ");
  }

  /* public static void main(String[] args) {
    String nacosUrl = "localhost:8848";
    String serverType = "GAME";
    String serverId = "game1001";
    String env = "ly";
    ServerContext.ENV = env;
    ServerContext.serverType = ServerTypeEnum.GAME;
    for (int i = 0; i < 100; i++) {
      getInstance().startUp(nacosUrl, ServerTypeEnum.getByType(serverType), serverId, env);
    }
    try {
      Thread.sleep(Integer.MAX_VALUE);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }*/
}
