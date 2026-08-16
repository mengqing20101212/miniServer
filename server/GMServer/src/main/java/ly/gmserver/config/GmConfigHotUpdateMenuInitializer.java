package ly.gmserver.config;

import java.util.Comparator;
import java.util.List;
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
    GmMenuEntry configRoot =
        ensureMenu("配置管理", "config:view", 0, "", "fas fa-cog", 950);
    if (configRoot == null || configRoot.getId() == null) {
      LoggerDef.SystemLogger.error("初始化GM配表热更菜单失败，配置管理父菜单不存在");
      return;
    }
    ensureMenu("配表热更", "config:hot-update", configRoot.getId(), "/gm/config/hot-update", "fas fa-upload", 951);
    ensureMenu("开发服分配", "dev:game-assignment", configRoot.getId(), "/gm/dev/game-assignment", "fas fa-server", 952);
    ensureMenu("临时脚本", "runtime-script:execute", configRoot.getId(), "/gm/runtime-script", "fas fa-code", 953);
  }

  private GmMenuEntry ensureMenu(
      String name,
      String permission,
      Integer parentId,
      String path,
      String icon,
      Integer sortOrder) {
    try {
      GmMenuEntry old = findExistingMenu(permission, path);
      if (old != null) {
        boolean changed = false;
        if (!name.equals(old.getName())) {
          old.setName(name);
          changed = true;
        }
        if (!parentId.equals(old.getParentId())) {
          old.setParentId(parentId);
          changed = true;
        }
        if (!path.equals(old.getPath())) {
          old.setPath(path);
          changed = true;
        }
        if (!icon.equals(old.getIcon())) {
          old.setIcon(icon);
          changed = true;
        }
        if (!sortOrder.equals(old.getSortOrder())) {
          old.setSortOrder(sortOrder);
          changed = true;
        }
        if (changed) {
          GmMenuEntryHelper.update(old, "name", "parent_id", "path", "icon", "sort_order");
        }
        return old;
      }

      GmMenuEntry menu = new GmMenuEntry();
      menu.setName(name);
      menu.setPermission(permission);
      menu.setParentId(parentId);
      menu.setPath(path);
      menu.setIcon(icon);
      menu.setSortOrder(sortOrder);
      GmMenuEntryHelper.save(menu);
      return findExistingMenu(permission, path);
    } catch (Exception e) {
      LoggerDef.SystemLogger.error("初始化GM配表热更菜单失败，permission={}", permission, e);
      return null;
    }
  }

  private GmMenuEntry findExistingMenu(String permission, String path) {
    List<GmMenuEntry> byPermission = GmMenuEntryHelper.select(new String[] {"permission"}, permission);
    if (path != null && !path.isBlank()) {
      return byPermission.stream()
          .filter(menu -> path.equals(menu.getPath()))
          .min(Comparator.comparingInt(GmMenuEntry::getId))
          .orElse(null);
    }
    return byPermission.stream()
        .min(Comparator.comparingInt(GmMenuEntry::getId))
        .orElse(null);
  }
}
