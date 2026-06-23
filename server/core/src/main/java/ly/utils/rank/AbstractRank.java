package ly.utils.rank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import ly.LoggerDef;
import ly.db.entry.RankHistoryEntry;
import ly.db.entry.RankHistoryEntryHelper;
import org.slf4j.Logger;

/** 具体排行榜的抽象基类。一个实例代表一个具体维度的排行榜。 */
public abstract class AbstractRank {
  private static final Logger logger = LoggerDef.SystemLogger;

  private final RankConfig config;
  private final AtomicBoolean settling = new AtomicBoolean(false);
  private final AtomicBoolean deleting = new AtomicBoolean(false);
  private volatile long lastSettledAtMillis;
  private volatile boolean settleSuccess;

  protected AbstractRank(RankConfig config) {
    this.config = config;
  }

  public abstract String getName();

  public abstract RankType getRankType();

  protected abstract Object[] getRankKeyParts();

  /** 到达该时间后禁止继续更新分数。返回 <=0 表示永久允许更新。 */
  public long getEndTimeMillis() {
    return 0L;
  }

  /** 到达该时间后由 RankService 自动启动结算。返回 <=0 表示不自动结算。 */
  public long getSettleAtMillis() {
    return 0L;
  }

  /** 具体玩家的结算逻辑由子类实现，例如发邮件奖励。 */
  protected abstract void settlePlayer(RankEntry entry);

  /** 子类可返回奖励 JSON，用于写入 rank_history。 */
  protected String buildRewardJson(RankEntry entry) {
    return "";
  }

  /** 删除 Redis 榜单前的业务回调，子类可按需扩展。 */
  protected void beforeDelete(List<RankEntry> snapshot) {}

  public RankConfig getConfig() {
    return config;
  }

  public String getRankKey() {
    return getRankType().getKey(getRankKeyParts());
  }

  /** 同步更新分数并返回最新排名，适合“更新后立刻要知道排名”的业务。 */
  public RankEntry updateRankScoreSync(long playerId, long score) {
    if (!canUpdateScore(System.currentTimeMillis())) {
      logger.warn("rank update ignored after endTime, rank={}, playerId={}", getName(), playerId);
      return getPlayerRank(playerId);
    }
    updateScoreInternal(playerId, score, System.currentTimeMillis());
    return getPlayerRank(playerId);
  }

  public boolean updateRankScoreAsync(long playerId, long score) {
    if (!canUpdateScore(System.currentTimeMillis())) {
      logger.warn("rank async update ignored after endTime, rank={}, playerId={}", getName(), playerId);
      return false;
    }
    return RankService.submit(RankUpdateTask.set(this, playerId, score));
  }

  public RankEntry addRankScoreSync(long playerId, long delta) {
    if (!canUpdateScore(System.currentTimeMillis())) {
      logger.warn("rank add ignored after endTime, rank={}, playerId={}", getName(), playerId);
      return getPlayerRank(playerId);
    }
    addScoreInternal(playerId, delta, System.currentTimeMillis());
    return getPlayerRank(playerId);
  }

  public boolean addRankScoreAsync(long playerId, long delta) {
    if (!canUpdateScore(System.currentTimeMillis())) {
      logger.warn("rank async add ignored after endTime, rank={}, playerId={}", getName(), playerId);
      return false;
    }
    return RankService.submit(RankUpdateTask.add(this, playerId, delta));
  }

  public List<RankEntry> topN(int n) {
    return range(1, n);
  }

  public List<RankEntry> range(int startRank, int endRank) {
    if (startRank <= 0 || endRank < startRank) {
      return List.of();
    }
    List<RankUtils.ScoredPlayer> scoredPlayers =
        RankUtils.reverseRange(getRankKey(), startRank - 1, endRank - 1);
    List<RankEntry> result = new ArrayList<>(scoredPlayers.size());
    int rank = startRank;
    // Redis 返回顺序已经是最终排名顺序；这里只负责按遍历顺序补 rank 字段。
    for (RankUtils.ScoredPlayer scoredPlayer : scoredPlayers) {
      result.add(toRankEntry(scoredPlayer, rank++));
    }
    return result;
  }

  public RankEntry getPlayerRank(long playerId) {
    Integer rankIndex = RankUtils.getReverseRank(getRankKey(), playerId);
    RankUtils.ScoredPlayer scoredPlayer = RankUtils.getScoredPlayer(getRankKey(), playerId);
    if (rankIndex == null || scoredPlayer == null) {
      return null;
    }
    return toRankEntry(scoredPlayer, rankIndex + 1);
  }

  public List<RankEntry> around(long playerId, int before, int after) {
    Integer rankIndex = RankUtils.getReverseRank(getRankKey(), playerId);
    if (rankIndex == null) {
      return List.of();
    }
    int startRank = Math.max(1, rankIndex + 1 - Math.max(0, before));
    int endRank = rankIndex + 1 + Math.max(0, after);
    return range(startRank, endRank);
  }

  public boolean remove(long playerId) {
    return RankUtils.remove(getRankKey(), playerId);
  }

  public boolean clear() {
    return RankUtils.clear(getRankKey());
  }

  final void executeTask(RankUpdateTask task) {
    // 异步任务入队后可能跨过 endTime，实际执行前必须再校验一次。
    if (!canUpdateScore(System.currentTimeMillis())) {
      logger.warn("rank queued task ignored after endTime, rank={}, playerId={}", getName(), task.playerId());
      return;
    }
    if (task.type() == RankUpdateType.SET) {
      updateScoreInternal(task.playerId(), task.value(), System.currentTimeMillis());
    } else if (task.type() == RankUpdateType.ADD) {
      addScoreInternal(task.playerId(), task.value(), System.currentTimeMillis());
    }
  }

  final void tryStartSettle(long nowMillis) {
    long settleAtMillis = getSettleAtMillis();
    if (settleAtMillis <= 0 || nowMillis < settleAtMillis || lastSettledAtMillis == settleAtMillis) {
      return;
    }
    if (!settling.compareAndSet(false, true)) {
      return;
    }
    // 结算可能包含发邮件、写库等慢操作，单独开虚拟线程，避免阻塞 RankService.tick。
    Thread.ofVirtual().name("RankSettle-" + getName()).start(() -> settleInternal(settleAtMillis));
  }

  final void tryStartDelete(long nowMillis) {
    if (!settleSuccess || !canDelete(nowMillis)) {
      return;
    }
    if (!deleting.compareAndSet(false, true)) {
      return;
    }
    // 删除前需要写 rank_history 备份，也放到独立虚拟线程里执行。
    Thread.ofVirtual().name("RankDelete-" + getName()).start(this::deleteInternal);
  }

  protected boolean canDelete(long nowMillis) {
    long settleAtMillis = getSettleAtMillis();
    long delay = config.getDeleteAfterSettleMillis();
    return settleAtMillis > 0 && delay > 0 && nowMillis >= settleAtMillis + delay;
  }

  private boolean canUpdateScore(long nowMillis) {
    long endTime = getEndTimeMillis();
    return endTime <= 0 || nowMillis < endTime;
  }

  private void updateScoreInternal(long playerId, long score, long nowMillis) {
    RankUtils.ScoredPlayer old = RankUtils.getScoredPlayer(getRankKey(), playerId);
    if (old != null && !config.isAllowScoreDown()) {
      if (score < old.score()) {
        return;
      }
      if (score == old.score()) {
        // 分数不变时保留首次达到该分数的时间，保证同分先到者排序稳定。
        nowMillis = old.scoreTimeMillis();
      }
    }
    RankUtils.setScore(getRankKey(), playerId, score, nowMillis);
    RankUtils.trimToMaxSize(getRankKey(), config.getMaxSize());
  }

  private void addScoreInternal(long playerId, long delta, long nowMillis) {
    RankUtils.ScoredPlayer old = RankUtils.getScoredPlayer(getRankKey(), playerId);
    long oldScore = 0L;
    if (old != null) {
      oldScore = old.score();
    }
    updateScoreInternal(playerId, Math.max(0L, oldScore + delta), nowMillis);
  }

  private RankEntry toRankEntry(RankUtils.ScoredPlayer scoredPlayer, int rank) {
    return new RankEntry(
        scoredPlayer.playerId(), rank, scoredPlayer.score(), scoredPlayer.scoreTimeMillis());
  }

  private void settleInternal(long settleAtMillis) {
    boolean success = true;
    List<RankEntry> snapshot = topN(config.getSettleSnapshotSize());
    try {
      for (RankEntry entry : snapshot) {
        try {
          // 每个玩家独立 try/catch；单个玩家结算失败不影响其他玩家继续处理。
          settlePlayer(entry);
        } catch (Exception e) {
          success = false;
          logger.error("rank settle player failed, rank={}, playerId={}", getName(), entry.getPlayerId(), e);
        }
      }
      if (success) {
        // 只有全部玩家结算成功，才记录本次结算时间并允许后续删除备份。
        lastSettledAtMillis = settleAtMillis;
        settleSuccess = true;
      }
    } finally {
      settling.set(false);
    }
  }

  private void deleteInternal() {
    List<RankEntry> snapshot = topN(config.getMaxSize());
    try {
      // 先落历史表，再删 Redis；备份失败时必须保留 Redis 榜单，便于后续重试和运营追溯。
      if (!backupHistory(snapshot)) {
        return;
      }
      beforeDelete(snapshot);
      if (clear()) {
        settleSuccess = false;
      }
    } finally {
      deleting.set(false);
    }
  }

  private boolean backupHistory(List<RankEntry> snapshot) {
    long settleTime = getSettleAtMillis();
    for (RankEntry entry : snapshot) {
      RankHistoryEntry history = new RankHistoryEntry();
      history.setRankKey(getRankKey());
      history.setPlayerId(entry.getPlayerId());
      history.setScore(entry.getScore());
      history.setScoreTime(entry.getScoreTimeMillis());
      history.setRankNo(entry.getRank());
      history.setReward(buildRewardJson(entry));
      history.setSettleTime(settleTime);
      history.setCreateTime(LocalDateTime.now());
      if (!RankHistoryEntryHelper.save(history)) {
        logger.error("rank history backup failed, rank={}, playerId={}", getName(), entry.getPlayerId());
        return false;
      }
    }
    return true;
  }
}
