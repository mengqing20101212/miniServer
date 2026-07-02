package ly.net;

/**
 * 业务唯一 id 生成器接口。
 * <p>
 * 需要自定义会话 id、玩家 id 或对象 id 策略时，可通过该接口隔离具体生成算法。
 */
public interface IGuidCreator {
  public long createGuid();
}
