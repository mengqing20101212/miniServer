package ly.loginserver.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import ly.cache.CacheService;
import ly.config.ServerTypeEnum;
import ly.db.entry.loginEntry;
import ly.db.entry.loginEntryHelper;
import ly.game.MiniPlayer;
import ly.game.MiniPlayerHelper;
import ly.loginserver.result.ServerListResult;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginService
 */
@Service
public class LoginService {
  static Logger logger = LoggerFactory.getLogger(LoginService.class);

  public loginEntry loadFromDB(String account) {
    List<loginEntry> list = loginEntryHelper.select(new String[] {"account"}, account);
    if (list.isEmpty()) {
      logger.warn("No account found for account  from DB" + account);
      return null;
    }
    return list.get(0);
  }

  public List<MiniPlayer> getPlayers(String account) {
    loginEntry entry =
        (loginEntry)
            CacheService.getCacheService(loginEntry.class)
                .getWithSupplier(() -> this.loadFromDB(account), account);
    if (entry == null) {
      logger.warn("未查到该账号信息:" + account);
      return new ArrayList<MiniPlayer>();
    }
    List<Long> guids = new ArrayList<>();
    String[] strs = entry.getPlayers().trim().split(";");
    for (String str : strs) {
      guids.add(Long.parseLong(str));
    }
    List<MiniPlayer> miniPlayerList = MiniPlayerHelper.getMiniPlayerList(guids);
    return miniPlayerList;
  }

  public ServerListResult.ServerNode selectGate() {
    List<NacosServerNode> list =
        NacosService.getInstance().getNodeList(ServerTypeEnum.GATE).stream()
            .sorted(Comparator.comparingInt(NacosServerNode::getLoadNum))
            .toList();
    if (list.isEmpty()) {
      return null;
    }
    NacosServerNode first = list.get(0);
    ServerListResult.ServerNode serverNode = new ServerListResult.ServerNode();
    serverNode.setServerId(first.getServerId());
    serverNode.setServerName(first.getServerName());
    serverNode.setServerIp(first.getIp());
    serverNode.setServerPort(first.getPort());
    serverNode.setServerType(ServerTypeEnum.GATE.name());
    return serverNode;
  }

  public List<ServerListResult.ServerNode> selectGameServerList() {
    List<NacosServerNode> list =
        NacosService.getInstance().getNodeList(ServerTypeEnum.GAME).stream()
            .filter(
                node -> {
                  return node.canUse();
                })
            .toList();
    List<ServerListResult.ServerNode> serverNodeList = new ArrayList<>();
    for (NacosServerNode node : list) {
      ServerListResult.ServerNode serverNode = new ServerListResult.ServerNode();
      serverNode.setServerId(node.getServerId());
      serverNode.setServerName(node.getServerName());
      serverNode.setServerType(ServerTypeEnum.GAME.name());
      serverNodeList.add(serverNode);
    }
    return serverNodeList;
  }
}
