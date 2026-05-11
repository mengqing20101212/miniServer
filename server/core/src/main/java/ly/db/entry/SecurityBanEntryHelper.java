package ly.db.entry;

import java.util.ArrayList;
import java.util.List;

import ly.db.MysqlService;

/*
 * 自动生成风格的Helper代码，业务扩展请放在自定义区。
 */
public class SecurityBanEntryHelper {
  public static SecurityBanEntry getSecurityBanEntryById(Long id) {
    return MysqlService.getInstance().selectOnce(SecurityBanEntry.class, new String[] {"id"}, id);
  }

  public static boolean save(SecurityBanEntry securityBanEntry) {
    return MysqlService.getInstance().save(securityBanEntry);
  }

  public static boolean update(SecurityBanEntry securityBanEntry, String... fileds) {
    return MysqlService.getInstance().update(securityBanEntry, fileds);
  }

  public static boolean delete(SecurityBanEntry securityBanEntry) {
    return MysqlService.getInstance().delete(securityBanEntry);
  }

  public static void asyncSave(SecurityBanEntry securityBanEntry) {
    MysqlService.getInstance().addSaveEntry(securityBanEntry);
  }

  public static void asyncUpdate(SecurityBanEntry securityBanEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(securityBanEntry, fileds);
  }

  public static List<SecurityBanEntry> select(String[] fields, Object... params) {
    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(SecurityBanEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  public static List<SecurityBanEntry> selectAll() {
    return MysqlService.getInstance().selectAll(SecurityBanEntry.class, null);
  }

  public static List<SecurityBanEntry> selectActive() {
    return select(new String[] {"status"}, 1);
  }

  public static List<SecurityBanEntry> selectByTypeAndTarget(Integer banType, String target) {
    return select(new String[] {"ban_type", "target"}, banType, target);
  }

  // @@@@@自定义方法结束区@@@@@
}
