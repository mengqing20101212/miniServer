package ly.loginserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import ly.cache.CacheService;
import ly.config.ServerTypeEnum;
import ly.db.entry.LoginEntry;
import ly.db.entry.LoginEntryHelper;
import ly.game.MiniPlayer;
import ly.game.MiniPlayerHelper;
import ly.loginserver.result.ServerListResult;
import ly.nacos.NacosServerNode;
import ly.nacos.NacosService;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import ly.utils.RandomUtils;
import ly.utils.TimeUtils;
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

  public LoginEntry loadFromDB(String account) {
    List<LoginEntry> list = LoginEntryHelper.select(new String[] {"account"}, account);
    if (list.isEmpty()) {
      logger.warn("No account found for account  from DB" + account);
      return null;
    }
    return list.get(0);
  }

  public List<MiniPlayer> getPlayers(String account) {
    LoginEntry entry = getLoginEntry(account);
    if (entry == null) {
      logger.warn("未查到该账号信息:" + account);
      return new ArrayList<MiniPlayer>();
    }
    List<Long> guids = new ArrayList<>();
    if (entry.getPlayers() != null) {
      String[] strs = entry.getPlayers().trim().split(";");
      for (String str : strs) {
        guids.add(Long.parseLong(str));
      }
      List<MiniPlayer> miniPlayerList = MiniPlayerHelper.getMiniPlayerList(guids);
      return miniPlayerList;
    }
    return new ArrayList<>();
  }

  public LoginEntry getLoginEntry(String account) {
    LoginEntry entry =
        (LoginEntry)
            CacheService.getCacheService(LoginEntry.class)
                .getWithSupplier(() -> this.loadFromDB(account), account);
    return entry;
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

  public int createNewAccountId() {

    return (int) RandomUtils.RandomInt(1000);
  }

  public String createToken(String account) {
    return RandomUtils.generateRandomString(8);
  }

  public LoginEntry createNewAccount(String account, String channel) {
    LocalDateTime now = TimeUtils.now();
    LoginEntry entry = new LoginEntry();
    entry.setAccount(account);
    entry.setChannel(channel);
    entry.setId(createNewAccountId());
    entry.setToken(createToken(account));
    entry.setCreateTime(now);
    entry.setLastLoginTime(now);
    if (entry.save()) {
      String key = RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account);
      RedisUtils.set(key, entry.getId());
      saveToken(account, entry.getToken());
      return entry;
    } else {
      return null;
    }
  }

  public void saveToken(String account, String token) {
    RedisUtils.setWithExpire(
        RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account), token, 1, TimeUnit.HOURS);
  }

  public String getToken(String account) {
    String oldToken = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_TOKEN_KEY.getKey(account));
    if (oldToken == null) {
      oldToken = createToken(account);
      saveToken(account, oldToken);
      return oldToken;
    }
    return oldToken;
  }
}
