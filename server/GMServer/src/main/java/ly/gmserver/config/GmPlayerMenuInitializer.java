package ly.gmserver.config;

import java.util.Comparator;
import java.util.List;
import ly.LoggerDef;
import ly.db.entry.GmMenuEntry;
import ly.db.entry.GmMenuEntryHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/** GM 玩家管理菜单初始化。 */
@Component
public class GmPlayerMenuInitializer implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) {
    GmMenuEntry root = ensureMenu("玩家管理", "player:view", 0, "", "fas fa-gamepad", 800);
    if (root == null || root.getId() == null) {
      LoggerDef.SystemLogger.error("初始化 GM 玩家管理菜单失败，父菜单不存在");
      return;
    }
    ensureMenu("玩家详情", "player:detail", root.getId(), "/gm/player/detail", "fas fa-id-card", 801);
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
      LoggerDef.SystemLogger.error("初始化 GM 玩家菜单失败，permission={}", permission, e);
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
    return byPermission.stream().min(Comparator.comparingInt(GmMenuEntry::getId)).orElse(null);
  }
}
