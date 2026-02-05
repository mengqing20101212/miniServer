package ly.db.entry;

import java.util.ArrayList;
import java.util.List;
import ly.db.MysqlService;

/*
 * 自动生成的代码, 如需改动需要在 @@@@@自定义区修改@@@@@
 */
public class UserInfoEntryHelper {
  public static UserInfoEntry getUserInfoEntryById(Long id) {
    return MysqlService.getInstance()
        .selectOnce(UserInfoEntry.class, new String[]{"id"}, id);
  }

  public static boolean save(UserInfoEntry userInfoEntry) {
    return MysqlService.getInstance().save(userInfoEntry);
  }

  public static boolean update(UserInfoEntry userInfoEntry, String... fileds) {
    return MysqlService.getInstance().update(userInfoEntry, fileds);
  }

  public static boolean delete(UserInfoEntry userInfoEntry) {
    return MysqlService.getInstance().delete(userInfoEntry);
  }

  public static void asyncSave(UserInfoEntry userInfoEntry) {
    MysqlService.getInstance().addSaveEntry(userInfoEntry);
  }

  public static void asyncUpdate(UserInfoEntry userInfoEntry, String... fileds) {
    MysqlService.getInstance().addUpdateEntry(userInfoEntry);
  }

  public static List<UserInfoEntry> select(String[] fields, Object... params) {

    if (fields != null && params != null && fields.length == params.length) {
      return MysqlService.getInstance().selectAll(UserInfoEntry.class, fields, params);
    }
    return new ArrayList<>();
  }

  // @@@@@自定义方法开始区@@@@@

  // @@@@@自定义方法结束区@@@@@
}