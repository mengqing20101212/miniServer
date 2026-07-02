package ly.db.entry;

import java.time.LocalDateTime;
import ly.db.AbstractEntry;
import ly.db.DbMeta;

/** 排行榜历史快照，用于结算后追溯排名、分数和奖励。 */
@DbMeta.DbTable(name = "rank_history")
public class RankHistoryEntry extends AbstractEntry {
  private static final String[] DIRTY_FIELDS = {
      "id", "rank_key", "player_id", "score", "score_time", "rank_no", "reward",
      "settle_time", "create_time"
  };

  @DbMeta.DbMasterKey(name = "id", autoIncrement = true)
  @DbMeta.DbField(name = "id")
  private Long id;

  @DbMeta.DbField(name = "rank_key")
  private String rank_key;

  @DbMeta.DbField(name = "player_id")
  private Long player_id;

  @DbMeta.DbField(name = "score")
  private Long score;

  @DbMeta.DbField(name = "score_time")
  private Long score_time;

  @DbMeta.DbField(name = "rank_no")
  private Integer rank_no;

  @DbMeta.DbField(name = "reward")
  private String reward;

  @DbMeta.DbField(name = "settle_time")
  private Long settle_time;

  @DbMeta.DbField(name = "create_time")
  private LocalDateTime create_time;

  public RankHistoryEntry() {
    initDirtyState(DIRTY_FIELDS.length);
  }

  @Override
  protected String[] allDirtyFieldNames() {
    return DIRTY_FIELDS;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
    autoAddCurVersion();
    markFieldDirty(0);
  }

  public String getRankKey() {
    return rank_key;
  }

  public void setRankKey(String rankKey) {
    this.rank_key = rankKey;
    autoAddCurVersion();
    markFieldDirty(1);
  }

  public Long getPlayerId() {
    return player_id;
  }

  public void setPlayerId(Long playerId) {
    this.player_id = playerId;
    autoAddCurVersion();
    markFieldDirty(2);
  }

  public Long getScore() {
    return score;
  }

  public void setScore(Long score) {
    this.score = score;
    autoAddCurVersion();
    markFieldDirty(3);
  }

  public Long getScoreTime() {
    return score_time;
  }

  public void setScoreTime(Long scoreTime) {
    this.score_time = scoreTime;
    autoAddCurVersion();
    markFieldDirty(4);
  }

  public Integer getRankNo() {
    return rank_no;
  }

  public void setRankNo(Integer rankNo) {
    this.rank_no = rankNo;
    autoAddCurVersion();
    markFieldDirty(5);
  }

  public String getReward() {
    return reward;
  }

  public void setReward(String reward) {
    this.reward = reward;
    autoAddCurVersion();
    markFieldDirty(6);
  }

  public Long getSettleTime() {
    return settle_time;
  }

  public void setSettleTime(Long settleTime) {
    this.settle_time = settleTime;
    autoAddCurVersion();
    markFieldDirty(7);
  }

  public LocalDateTime getCreateTime() {
    return create_time;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.create_time = createTime;
    autoAddCurVersion();
    markFieldDirty(8);
  }
}
