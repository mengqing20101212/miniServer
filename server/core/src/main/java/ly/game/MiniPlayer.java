package ly.game;

import com.alibaba.fastjson2.annotation.JSONField;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: MiniPlayer
 */
public class MiniPlayer {
  private long guid;
  private String playerName;
  private String gameServerId;
  private String headId;
  private int level;

  @JSONField(serialize = false, deserialize = false) // 该字段 不参与JSON 序列化
  private long fightPower;

  private long lastLoginTime;
  private long lastLogoutTime;

  public MiniPlayer() {}

  public long getGuid() {
    return guid;
  }

  public void setGuid(long guid) {
    this.guid = guid;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getGameServerId() {
    return gameServerId;
  }

  public void setGameServerId(String gameServerId) {
    this.gameServerId = gameServerId;
  }

  public String getHeadId() {
    return headId;
  }

  public void setHeadId(String headId) {
    this.headId = headId;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public long getFightPower() {
    return fightPower;
  }

  public void setFightPower(long fightPower) {
    this.fightPower = fightPower;
  }

  public long getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(long lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  public long getLastLogoutTime() {
    return lastLogoutTime;
  }

  public void setLastLogoutTime(long lastLogoutTime) {
    this.lastLogoutTime = lastLogoutTime;
  }
}
