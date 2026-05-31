package ly.gmserver.config;

import java.util.Comparator;
import java.util.List;
import ly.LoggerDef;
import ly.db.entry.GmMenuEntry;
import ly.db.entry.GmMenuEntryHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * GM 安全管理菜单初始化。
 *
 * <p>菜单 ID 是数据库自增主键，不能写死父子 ID。这里按 permission/path 找已有菜单，并使用数据库真实
 * ID 维护父子关系，避免每次启动重复插入孤立父菜单。
 */
@Component
public class GmSecurityMenuInitializer implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) {
    GmMenuEntry securityRoot =
        ensureMenu("安全管理", "security:view", 0, "", "fas fa-shield-alt", 900);
    if (securityRoot == null || securityRoot.getId() == null) {
      LoggerDef.SystemLogger.error("初始化GM安全菜单失败，安全管理父菜单不存在");
      return;
    }
    ensureMenu("封禁管理", "security:ban", securityRoot.getId(), "/gm/security/ban", "fas fa-ban", 901);
    ensureMenu(
        "安全日志",
        "security:event",
        securityRoot.getId(),
        "/gm/security/event",
        "fas fa-clipboard-list",
        902);
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
      LoggerDef.SystemLogger.error("初始化GM安全菜单失败，permission={}", permission, e);
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
