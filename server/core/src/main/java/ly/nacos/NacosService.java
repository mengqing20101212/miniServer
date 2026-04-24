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
import ly.LoggerDef;
import ly.ServerContext;
import ly.config.ServerConfig;
import ly.config.ServerTypeEnum;
import ly.utils.CommonUtils;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private String currentNacosUrl;
    private String currentNamespace;

    /***nacos 请求操作最大超时时间戳*/
    static final long MAX_TIME_OUT = 5000;

    /**
     * 存活的服务器节点信息
     */
    private Map<String, NacosServerNode> nodeMap = new ConcurrentHashMap<>();

    private static NacosServerNode currentNode;

    private NacosService() {
    }

    public static NacosService getInstance() {
        return instance;
    }

    public void startUp(String nacosUrl, ServerTypeEnum serverType, String serverId, String env) {
        logger.info("开始启动 Nacos ");
        long startTime = System.currentTimeMillis();
        try {
            currentNacosUrl = nacosUrl;
            currentNamespace = env;
            // 1 获取nacos 服务器配置
            Properties properties = new Properties();
            properties.put(PropertyKeyConst.SERVER_ADDR, nacosUrl);
            properties.setProperty(PropertyKeyConst.NAMESPACE, env);
            properties.setProperty(PropertyKeyConst.USERNAME, "nacos");
            properties.setProperty(PropertyKeyConst.PASSWORD, "nacos");
            properties.setProperty(PropertyKeyConst.CONTEXT_PATH, "/");
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
        NacosException lastException = null;
        for (int i = 0; i < 10; i++) {
            try {
                namingService.registerInstance(
                        RPC_NODE_LIST_SERVICE, ServerContext.ENV, curNode.getInstance());
                logger.info("registerServerNode success, serverId={}, attempt={}", ServerContext.getServerId(), i + 1);
                return;
            } catch (NacosException e) {
                lastException = e;
                logger.warn(
                        "registerServerNode retry, serverId={}, attempt={}, errCode={}, errMsg={}",
                        ServerContext.getServerId(),
                        i + 1,
                        e.getErrCode(),
                        e.getErrMsg());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        if (lastException != null) {
            throw lastException;
        }
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
        if (configService == null) {
            throw new RuntimeException("ConfigService 不能为 null");
        }
        if (serverType == null) {
            throw new RuntimeException("ServerType 不能为 null");
        }
        if (serverId == null || serverId.trim().isEmpty()) {
            throw new RuntimeException("ServerId 不能为 null 或空字符串");
        }
        
        String configStr = configService.getConfig(serverId, serverType.getType(), MAX_TIME_OUT);
        if (configStr == null || configStr.isBlank()) {
            configStr = fetchConfigByHttp(serverId, serverType.getType());
        }
        if (configStr != null && !configStr.isBlank()) {
            parserServerConfig(configStr);
        } else {
            // 如果获取不到指定服务器的配置，尝试获取gate1001的配置作为默认配置
            configStr = configService.getConfig("gate1001", "GATE", MAX_TIME_OUT);
            if (configStr == null || configStr.isBlank()) {
                configStr = fetchConfigByHttp("gate1001", "GATE");
            }
            if (configStr != null && !configStr.isBlank()) {
                parserServerConfig(configStr);
            } else {
                throw new RuntimeException("获取nacos 配置失败，serverId=" + serverId + ", serverType=" + serverType.getType());
            }
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
                        if (s != null) {
                            logger.info("服务器配置: \n" + s);
                            parserServerConfig(s);
                        } else {
                            logger.warn("收到的配置信息为 null，忽略处理");
                        }
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

    private String fetchConfigByHttp(String dataId, String group) {
        if (currentNacosUrl == null || dataId == null || group == null) {
            return null;
        }
        try {
            String baseUrl =
                    currentNacosUrl.startsWith("http://") || currentNacosUrl.startsWith("https://")
                            ? currentNacosUrl
                            : "http://" + currentNacosUrl;
            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
            String url =
                    String.format(
                            "%s/nacos/v1/cs/configs?dataId=%s&group=%s&tenant=%s&username=nacos&password=nacos",
                            baseUrl,
                            URLEncoder.encode(dataId, StandardCharsets.UTF_8),
                            URLEncoder.encode(group, StandardCharsets.UTF_8),
                            URLEncoder.encode(
                                    currentNamespace == null ? "" : currentNamespace,
                                    StandardCharsets.UTF_8));
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response =
                    HttpClient.newHttpClient()
                            .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() == 200
                    && response.body() != null
                    && !response.body().isBlank()) {
                logger.info(
                        "fetchConfigByHttp success, dataId={}, group={}, namespace={}",
                        dataId,
                        group,
                        currentNamespace);
                return response.body();
            }
            logger.warn(
                    "fetchConfigByHttp failed, status={}, dataId={}, group={}, namespace={}",
                    response.statusCode(),
                    dataId,
                    group,
                    currentNamespace);
        } catch (Exception e) {
            logger.warn(
                    "fetchConfigByHttp exception, dataId={}, group={}, namespace={}",
                    dataId,
                    group,
                    currentNamespace,
                    e);
        }
        return null;
    }

    public void shutdown() throws NacosException {
        namingService.shutDown();
        nodeMap.clear();
        logger.info("关闭 Nacos ");
    }

    public static void main(String[] args) {
        String nacosUrl = "localhost:8848";
        String serverType = "GAME";
        String serverId = "game1001";
        String env = "ly";
        ServerContext.ENV = env;
        ServerContext.serverType = ServerTypeEnum.GAME;
        for (int i = 0; i < 1; i++) {
            getInstance().startUp(nacosUrl, ServerTypeEnum.getByType(serverType), serverId, env);
        }
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
