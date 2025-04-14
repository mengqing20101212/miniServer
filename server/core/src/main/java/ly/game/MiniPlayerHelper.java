package ly.game;

import java.util.ArrayList;
import java.util.List;
import ly.cache.CacheService;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: MiniPlayerHelper
 */
public class MiniPlayerHelper {
  /**
   * 同步阻塞 从redis获取 玩家 MiniPlayer
   *
   * @param guids 待获取的 玩家集合
   * @return
   */
  public static List<MiniPlayer> getMiniPlayerList(List<Long> guids) {
    List<MiniPlayer> miniPlayerList = new ArrayList<MiniPlayer>();
    for (long guid : guids) {
      MiniPlayer miniPlayer =
          (MiniPlayer)
              CacheService.getMiniPlayerCacheService()
                  .getWithSupplier(() -> getMiniPlayerFromRedis(guid), String.valueOf(guid));
      miniPlayerList.add(miniPlayer);
    }
    return miniPlayerList;
  }

  public static MiniPlayer getMiniPlayerFromRedis(long guid) {
    return RedisUtils.get(RedisKeys.MINI_PLAYER_KEY.getKey(guid));
  }
}
