package ly.bot.entity;

/**
 * 机器人侧玩家信息模型，保存登录后得到的账号和角色基础数据。
 */
public class PlayerInfo {
    private long playerId;      // 玩家ID
    private long accountId;     // 账号ID
    private String account;     // 账号
    private String nickname;    // 昵称
    private int level;          // 等级
    private String token;       // 登录令牌
    
    public PlayerInfo() {}
    
    public PlayerInfo(long playerId, long accountId, String account, String nickname, int level, String token) {
        this.playerId = playerId;
        this.accountId = accountId;
        this.account = account;
        this.nickname = nickname;
        this.level = level;
        this.token = token;
    }
    
    // Getters and Setters
    public long getPlayerId() {
        return playerId;
    }
    
    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
    
    public long getAccountId() {
        return accountId;
    }
    
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    
    public String getAccount() {
        return account;
    }
    
    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getNickname() {
        return nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "PlayerInfo{" +
                "playerId=" + playerId +
                ", accountId=" + accountId +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", level=" + level +
                '}';
    }
}
