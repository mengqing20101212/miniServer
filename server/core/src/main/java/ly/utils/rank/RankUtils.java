package ly.utils.rank;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import ly.LoggerDef;
import ly.redis.RedisUtils;
import org.redisson.api.RMap;
import org.redisson.api.RScoredSortedSet;
import org.redisson.client.protocol.ScoredEntry;
import org.slf4j.Logger;

/**
 * Redis ZSet 排行榜底层工具类，不承载具体业务排行榜状态。
 *
 * <p>数据结构：
 * <ul>
 *   <li>{@code rankKey}: ZSet，score 存业务分数，member 存 {@link RankMemberCodec} 编码后的时间和玩家ID。</li>
 *   <li>{@code rankKey:index}: Map，记录 playerId -> member，用于更新/删除时找到旧 member。</li>
 * </ul>
 *
 * <p>这里的 index 不是玩家展示数据，只是排行榜内部索引；玩家昵称、头像等展示信息仍由业务缓存读取。
 */
public final class RankUtils {
  private static final Logger logger = LoggerDef.SystemLogger;

  private RankUtils() {}

  public static boolean setScore(String rankKey, long playerId, long score, long scoreTimeMillis) {
    try {
      // member 内带时间；同一个玩家更新时必须先删旧 member，否则 ZSet 会保留多条历史记录。
      String oldMember = index(rankKey).get(playerId);
      if (oldMember != null) {
        zset(rankKey).remove(oldMember);
      }
      String member = RankMemberCodec.encode(playerId, scoreTimeMillis);
      zset(rankKey).add(RankScoreCodec.encode(score), member);
      index(rankKey).put(playerId, member);
      return true;
    } catch (Exception e) {
      logger.error("rank setScore failed, key={}, playerId={}", rankKey, playerId, e);
      return false;
    }
  }

  public static ScoredPlayer getScoredPlayer(String rankKey, long playerId) {
    try {
      // 先从内部索引拿 member，再用 member 去 ZSet 取分数。
      String member = index(rankKey).get(playerId);
      if (member == null) {
        return null;
      }
      Double redisScore = zset(rankKey).getScore(member);
      if (redisScore == null) {
        // ZSet 已被裁剪或清理但 index 还残留时，顺手修正索引。
        index(rankKey).remove(playerId);
        return null;
      }
      return toScoredPlayer(member, redisScore);
    } catch (Exception e) {
      logger.error("rank getScoredPlayer failed, key={}, playerId={}", rankKey, playerId, e);
      return null;
    }
  }

  public static Integer getReverseRank(String rankKey, long playerId) {
    try {
      // 排行榜对外从 1 开始；Redisson revRank 返回从 0 开始的反向排名索引。
      String member = index(rankKey).get(playerId);
      if (member == null) {
        return null;
      }
      return zset(rankKey).revRank(member);
    } catch (Exception e) {
      logger.error("rank getReverseRank failed, key={}, playerId={}", rankKey, playerId, e);
      return null;
    }
  }

  public static List<ScoredPlayer> reverseRange(String rankKey, int startIndex, int endIndex) {
    try {
      // Redis 返回结果已经按“分数降序、同分 member 反向字典序”排好，Java 侧不要二次排序。
      Collection<ScoredEntry<String>> entries = zset(rankKey).entryRangeReversed(startIndex, endIndex);
      List<ScoredPlayer> result = new ArrayList<>(entries.size());
      for (ScoredEntry<String> entry : entries) {
        result.add(toScoredPlayer(entry.getValue(), entry.getScore()));
      }
      return result;
    } catch (Exception e) {
      logger.error("rank reverseRange failed, key={}, start={}, end={}", rankKey, startIndex, endIndex, e);
      return Collections.emptyList();
    }
  }

  public static boolean remove(String rankKey, long playerId) {
    try {
      String member = index(rankKey).remove(playerId);
      return member != null && zset(rankKey).remove(member);
    } catch (Exception e) {
      logger.error("rank remove failed, key={}, playerId={}", rankKey, playerId, e);
      return false;
    }
  }

  public static boolean clear(String rankKey) {
    try {
      boolean zsetDeleted = zset(rankKey).delete();
      boolean indexDeleted = index(rankKey).delete();
      return zsetDeleted || indexDeleted;
    } catch (Exception e) {
      logger.error("rank clear failed, key={}", rankKey, e);
      return false;
    }
  }

  public static int size(String rankKey) {
    try {
      return zset(rankKey).size();
    } catch (Exception e) {
      logger.error("rank size failed, key={}", rankKey, e);
      return 0;
    }
  }

  public static void trimToMaxSize(String rankKey, int maxSize) {
    if (maxSize <= 0) {
      return;
    }
    try {
      int size = zset(rankKey).size();
      int removeCount = size - maxSize;
      if (removeCount <= 0) {
        return;
      }
      // 正序最前面是低分、或同分时更晚达到的玩家；裁掉这部分即可保留榜单上限。
      Collection<String> removedMembers = zset(rankKey).valueRange(0, removeCount - 1);
      zset(rankKey).removeRangeByRank(0, removeCount - 1);
      for (String member : removedMembers) {
        RankMemberCodec.DecodedMember decoded = RankMemberCodec.decode(member);
        // 裁剪 ZSet 后同步删除内部索引，避免玩家查询到已经不在榜上的旧 member。
        index(rankKey).remove(decoded.playerId());
      }
    } catch (Exception e) {
      logger.error("rank trim failed, key={}, maxSize={}", rankKey, maxSize, e);
    }
  }

  public static void expireAt(String rankKey, long expireAtMillis) {
    long ttlMillis = expireAtMillis - System.currentTimeMillis();
    if (ttlMillis <= 0) {
      return;
    }
    try {
      zset(rankKey).expire(Duration.ofMillis(ttlMillis));
      index(rankKey).expire(Duration.ofMillis(ttlMillis));
    } catch (Exception e) {
      logger.error("rank expire failed, key={}, expireAtMillis={}", rankKey, expireAtMillis, e);
    }
  }

  private static ScoredPlayer toScoredPlayer(String member, double redisScore) {
    RankMemberCodec.DecodedMember decoded = RankMemberCodec.decode(member);
    return new ScoredPlayer(decoded.playerId(), RankScoreCodec.decode(redisScore), decoded.scoreTimeMillis());
  }

  private static RScoredSortedSet<String> zset(String rankKey) {
    if (RedisUtils.redissonClient == null) {
      throw new IllegalStateException("RedissonClient 未初始化");
    }
    return RedisUtils.redissonClient.getScoredSortedSet(rankKey);
  }

  private static RMap<Long, String> index(String rankKey) {
    if (RedisUtils.redissonClient == null) {
      throw new IllegalStateException("RedissonClient 未初始化");
    }
    return RedisUtils.redissonClient.getMap(rankKey + ":index");
  }

  public record ScoredPlayer(long playerId, long score, long scoreTimeMillis) {}
}
