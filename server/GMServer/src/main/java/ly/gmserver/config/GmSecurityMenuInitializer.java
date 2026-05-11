package ly.gmserver.config;

import ly.LoggerDef;
import ly.db.entry.GmMenuEntry;
import ly.db.entry.GmMenuEntryHelper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * GM 安全管理菜单初始化。
 *
 * <p>表结构由自动建表服务负责，这里只补齐默认菜单数据，保证新环境启动后页面入口可见。
 */
@Component
public class GmSecurityMenuInitializer implements ApplicationRunner {
  @Override
  public void run(ApplicationArguments args) {
    ensureMenu(900, "安全管理", "security:view", 0, "", "fas fa-shield-alt", 900);
    ensureMenu(901, "封禁管理", "security:ban", 900, "/gm/security/ban", "fas fa-ban", 901);
    ensureMenu(902, "安全日志", "security:event", 900, "/gm/security/event", "fas fa-clipboard-list", 902);
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
      LoggerDef.SystemLogger.error("初始化GM安全菜单失败，menuId={}", id, e);
    }
  }
}
