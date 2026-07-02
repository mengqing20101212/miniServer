package ly.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ly.LoggerDef;
import ly.ServerContext;
import ly.db.entry.SecurityBanEntry;
import ly.db.entry.SecurityBanEntryHelper;
import ly.db.entry.SecurityEventLogEntry;
import ly.db.entry.SecurityEventLogEntryHelper;
import ly.redis.RedisUtils;

/**
 * 封禁与安全事件的统一入口。
 *
 * <p>MySQL 是最终落库，Redis 是运行期快速判断缓存。服务启动时会把 MySQL 中仍然生效的封禁
 * 加载到 Redis，避免每个客户端包都访问数据库。
 */
public class SecurityBanService {
  public static final int STATUS_ACTIVE = 1;
  public static final int STATUS_RELEASED = 2;
  public static final int SOURCE_GM = 1;
  public static final int SOURCE_AUTO = 2;
  public static final int EVENT_BAN = 1;
  public static final int EVENT_RELEASE = 2;
  public static final int EVENT_REJECT = 3;
  public static final int EVENT_RATE_LIMIT = 4;

  private static final String REDIS_PREFIX = "security:ban:";
  private static final SecurityBanService INSTANCE = new SecurityBanService();

  public static SecurityBanService getInstance() {
    return INSTANCE;
  }

  private SecurityBanService() {}

  /**
   * 启动时从 MySQL 加载仍然有效的封禁记录。
   *
   * <p>过期记录不会写入 Redis；已经过期但状态仍为生效的记录会异步标记为解除，避免后续重复扫描。
   */
  public void loadActiveBansFromDb() {
    LocalDateTime now = LocalDateTime.now();
    List<SecurityBanEntry> entries = SecurityBanEntryHelper.selectActive();
    int loaded = 0;
    for (SecurityBanEntry entry : entries) {
      if (entry == null || entry.getBanType() == null || entry.getTarget() == null) {
        continue;
      }
      if (!entry.isActive(now)) {
        entry.setStatus(STATUS_RELEASED);
        entry.setUpdateTime(now);
        SecurityBanEntryHelper.asyncUpdate(entry, "status", "update_time");
        continue;
      }
      putRuntimeBan(entry);
      loaded++;
    }
    LoggerDef.SystemLogger.info("安全封禁加载完成，serverId={}, count={}", ServerContext.getServerId(), loaded);
  }

  public boolean isBanned(SecurityBanType type, String target) {
    if (type == null || target == null || target.isBlank()) {
      return false;
    }
    return RedisUtils.exists(redisKey(type, target));
  }

  public boolean isIpBanned(String ip) {
    return isBanned(SecurityBanType.IP, ip);
  }

  public boolean isAccountBanned(String account) {
    return isBanned(SecurityBanType.ACCOUNT, account);
  }

  public boolean isPlayerBanned(long playerId) {
    return playerId > 0 && isBanned(SecurityBanType.PLAYER, String.valueOf(playerId));
  }

  /**
   * 新增封禁：先写 MySQL，成功后写 Redis。
   *
   * <p>这样 GM 页面返回成功时，运行期拦截缓存也已经生效；如果 MySQL 失败则不污染 Redis。
   */
  public boolean createBan(
      SecurityBanType type,
      String target,
      String reason,
      Integer source,
      String operator,
      LocalDateTime endTime) {
    if (type == null || target == null || target.isBlank()) {
      return false;
    }
    LocalDateTime now = LocalDateTime.now();
    SecurityBanEntry entry = new SecurityBanEntry();
    entry.setBanType(type.getCode());
    entry.setTarget(normalizeTarget(type, target));
    entry.setReason(reason);
    entry.setSource(source == null ? SOURCE_GM : source);
    entry.setStatus(STATUS_ACTIVE);
    entry.setStartTime(now);
    entry.setEndTime(endTime);
    entry.setOperator(operator);
    entry.setCreateTime(now);
    entry.setUpdateTime(now);
    if (!SecurityBanEntryHelper.save(entry)) {
      return false;
    }
    putRuntimeBan(entry);
    writeEvent(EVENT_BAN, null, null, null, null, null, null, null, reason, "type=" + type.name());
    return true;
  }

  /**
   * 解除封禁：先更新 MySQL 状态，再删除 Redis 运行期缓存。
   */
  public boolean releaseBan(SecurityBanType type, String target, String operator) {
    if (type == null || target == null || target.isBlank()) {
      return false;
    }
    String normalizedTarget = normalizeTarget(type, target);
    List<SecurityBanEntry> entries =
        SecurityBanEntryHelper.selectByTypeAndTarget(type.getCode(), normalizedTarget);
    boolean changed = false;
    LocalDateTime now = LocalDateTime.now();
    for (SecurityBanEntry entry : entries) {
      if (entry == null || !Integer.valueOf(STATUS_ACTIVE).equals(entry.getStatus())) {
        continue;
      }
      entry.setStatus(STATUS_RELEASED);
      entry.setOperator(operator);
      entry.setUpdateTime(now);
      changed |= SecurityBanEntryHelper.update(entry, "status", "operator", "update_time");
    }
    RedisUtils.del(redisKey(type, normalizedTarget));
    if (changed) {
      writeEvent(EVENT_RELEASE, null, null, null, null, null, null, null, "解除封禁", "type=" + type.name());
    }
    return changed;
  }

  public void writeRejectEvent(
      String ip,
      String account,
      Long accountId,
      Long playerId,
      Integer cmd,
      Integer sid,
      Integer seq,
      String reason) {
    writeEvent(EVENT_REJECT, ip, account, accountId, playerId, cmd, sid, seq, reason, null);
  }

  public void writeRateLimitEvent(
      String ip,
      String account,
      Long accountId,
      Long playerId,
      Integer cmd,
      Integer sid,
      Integer seq,
      String reason) {
    writeEvent(EVENT_RATE_LIMIT, ip, account, accountId, playerId, cmd, sid, seq, reason, null);
  }

  private void putRuntimeBan(SecurityBanEntry entry) {
    SecurityBanType type = SecurityBanType.byCode(entry.getBanType());
    if (type == null) {
      return;
    }
    String key = redisKey(type, entry.getTarget());
    if (entry.getEndTime() == null) {
      RedisUtils.set(key, entry.getId());
      return;
    }
    long ttlMillis = Duration.between(LocalDateTime.now(), entry.getEndTime()).toMillis();
    if (ttlMillis > 0) {
      RedisUtils.setWithExpire(key, entry.getId(), ttlMillis, TimeUnit.MILLISECONDS);
    }
  }

  private void writeEvent(
      Integer eventType,
      String ip,
      String account,
      Long accountId,
      Long playerId,
      Integer cmd,
      Integer sid,
      Integer seq,
      String reason,
      String extra) {
    SecurityEventLogEntry event = new SecurityEventLogEntry();
    event.setEventType(eventType);
    event.setServerId(ServerContext.getServerId());
    event.setIp(ip);
    event.setAccount(account);
    event.setAccountId(accountId);
    event.setPlayerId(playerId);
    event.setCmd(cmd);
    event.setSid(sid);
    event.setSeq(seq);
    event.setReason(reason);
    event.setExtra(extra);
    event.setCreateTime(LocalDateTime.now());
    SecurityEventLogEntryHelper.asyncSave(event);
  }

  private String redisKey(SecurityBanType type, String target) {
    return REDIS_PREFIX + type.name().toLowerCase(Locale.ROOT) + ":" + normalizeTarget(type, target);
  }

  private String normalizeTarget(SecurityBanType type, String target) {
    String value = target == null ? "" : target.trim();
    if (type == SecurityBanType.IP || type == SecurityBanType.ACCOUNT || type == SecurityBanType.DEVICE) {
      return value.toLowerCase(Locale.ROOT);
    }
    return value;
  }
}
