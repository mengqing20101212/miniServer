package ly.loginserver.service;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginService
 */
@Service
public class LoginService {
    static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public LoginEntry loadFromDB(String account) {
        List<LoginEntry> list = LoginEntryHelper.select(new String[]{"account"}, account);
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

    /**
     * 选择网关节点
     *
     * @return 网关节点
     */
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
        return RandomUtils.RandomInt(Integer.MAX_VALUE);
    }

    public String createToken(String account) {
        // 生成更安全的token，结合账号信息、时间戳和随机字符串
        long timestamp = System.currentTimeMillis();
        String randomStr = RandomUtils.generateRandomString(16);
        // 使用账号、时间戳和随机字符串组合生成token
        return String.format("%s_%d_%s",
                account.substring(0, Math.min(8, account.length())).hashCode(),
                timestamp,
                randomStr);
//        return RandomUtils.generateRandomString(8);
    }

    public LoginEntry createNewAccount(String account, String channel) {
        // 参数验证
        if (account == null || account.trim().isEmpty() || channel == null || channel.trim().isEmpty()) {
            logger.error("Invalid account or channel parameters");
            return null;
        }

        String createAccountLockKey = RedisKeys.LOCK_CREATE_ACCOUNT_KEY.getKey(account);
        boolean lockAcquired = false;

        try {
            // 双重检查锁定模式 - 先检查是否存在
            if (RedisUtils.exists(RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account))) {
                logger.warn("Account already exists: {}", account);
                return null;
            }

            // 获取分布式锁
            lockAcquired = RedisUtils.lock(createAccountLockKey);
            if (!lockAcquired) {
                logger.error("Failed to acquire lock for creating account: {}", account);
                return null;
            }

            // 获取锁后再次检查是否存在（防止并发创建）
            if (RedisUtils.exists(RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account))) {
                logger.warn("Account already exists after acquiring lock: {}", account);
                return null;
            }

            // 创建新账号
            LocalDateTime now = TimeUtils.now();
            LoginEntry entry = new LoginEntry();
            entry.setAccount(account.trim());
            entry.setChannel(channel.trim());
            entry.setId(createNewAccountId());
            entry.setToken(createToken(account));
            entry.setCreateTime(now);
            entry.setLastLoginTime(now);
            entry.save();
            // 保存成功后写入Redis
            String key = RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account);
            RedisUtils.set(key, entry.getId());
            saveToken(account, entry.getToken());

            // 预热缓存
            CacheService.getCacheService(LoginEntry.class).put(entry, account);

            logger.info("Successfully created account: {}", entry);
            return entry;
        } catch (Exception e) {
            logger.error("Error creating account: {}", account, e);
            return null;
        } finally {
            // 确保释放锁
            if (lockAcquired) {
                try {
                    RedisUtils.unlock(createAccountLockKey);
                } catch (Exception e) {
                    logger.error("Error releasing lock for account: {}", account, e);
                }
            }
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