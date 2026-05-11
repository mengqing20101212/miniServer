package ly.gmserver.config;

import ly.LoggerDef;
import ly.db.entry.GmMenuEntry;
import ly.db.entry.GmMenuEntryHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** GM 配表热更菜单初始化。 */
@Component
public class GmConfigHotUpdateMenuInitializer implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) {
    ensureMenu(950, "配置管理", "config:view", 0, "", "fas fa-cog", 950);
    ensureMenu(951, "配表热更", "config:hot-update", 950, "/gm/config/hot-update", "fas fa-upload", 951);
  }

  private void ensureMenu(
      Integer id,
      String name,
      String permission,
      Integer parentId,
      String path,
      String icon,
      Integer sortOrder) {
    try {
      GmMenuEntry old = GmMenuEntryHelper.getGmMenuEntryById(id);
      if (old != null) {
        return;
      }
      GmMenuEntry menu = new GmMenuEntry();
      menu.setId(id);
      menu.setName(name);
      menu.setPermission(permission);
      menu.setParentId(parentId);
      menu.setPath(path);
      menu.setIcon(icon);
      menu.setSortOrder(sortOrder);
      GmMenuEntryHelper.save(menu);
    } catch (Exception e) {
      LoggerDef.SystemLogger.error("初始化GM配置热更菜单失败，menuId={}", id, e);
    }
  }
}
