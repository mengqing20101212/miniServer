-- GMServer 管理后台数据库表
-- 执行前先创建数据库（如果不存在）：
-- CREATE DATABASE IF NOT EXISTS pick_money DEFAULT CHARSET utf8mb4;

-- 管理员账户表
CREATE TABLE IF NOT EXISTS gm_admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '管理员ID',
    username VARCHAR(64) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(256) NOT NULL COMMENT '密码（bcrypt哈希）',
    role_id INT NOT NULL DEFAULT 1 COMMENT '角色ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 1=启用, 0=禁用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员账户';

-- 角色定义表
CREATE TABLE IF NOT EXISTS gm_role (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '角色ID',
    name VARCHAR(64) NOT NULL UNIQUE COMMENT '角色名称',
    description VARCHAR(256) DEFAULT '' COMMENT '角色描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色定义';

-- 角色权限映射表
CREATE TABLE IF NOT EXISTS gm_role_permission (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    role_id INT NOT NULL COMMENT '角色ID',
    permission VARCHAR(128) NOT NULL COMMENT '权限标识',
    UNIQUE KEY uk_role_perm (role_id, permission)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限映射';

-- 页面菜单定义表
CREATE TABLE IF NOT EXISTS gm_menu (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT '菜单ID',
    name VARCHAR(64) NOT NULL COMMENT '菜单名称',
    permission VARCHAR(128) DEFAULT '' COMMENT '所需权限（空=所有角色可见）',
    parent_id INT NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    path VARCHAR(256) DEFAULT '' COMMENT '前端路由路径',
    icon VARCHAR(64) DEFAULT '' COMMENT '图标',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='页面菜单';

-- 角色菜单可见性
CREATE TABLE IF NOT EXISTS gm_role_menu (
    id INT AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    role_id INT NOT NULL COMMENT '角色ID',
    menu_id INT NOT NULL COMMENT '菜单ID',
    UNIQUE KEY uk_role_menu (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单可见性';

-- 操作日志表
CREATE TABLE IF NOT EXISTS gm_operation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '日志ID',
    admin_id BIGINT NOT NULL COMMENT '管理员ID',
    username VARCHAR(64) NOT NULL COMMENT '管理员用户名',
    action VARCHAR(64) NOT NULL COMMENT '操作类型',
    target_type VARCHAR(64) DEFAULT '' COMMENT '目标类型',
    target_id VARCHAR(64) DEFAULT '' COMMENT '目标ID',
    detail TEXT DEFAULT NULL COMMENT '操作详情（JSON）',
    ip VARCHAR(64) DEFAULT '' COMMENT '操作IP',
    result VARCHAR(32) NOT NULL DEFAULT 'SUCCESS' COMMENT '结果 SUCCESS/FAIL',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- 默认数据
INSERT IGNORE INTO gm_role (id, name, description) VALUES (1, '超级管理员', '拥有所有权限');
INSERT IGNORE INTO gm_role (id, name, description) VALUES (2, '运营', '日常运营操作权限');
INSERT IGNORE INTO gm_role (id, name, description) VALUES (3, '客服', '客服查询权限');
INSERT IGNORE INTO gm_role (id, name, description) VALUES (4, '只读', '仅查看权限');

-- 默认管理员: admin / admin123（需手动插入时使用bcrypt密码）
INSERT IGNORE INTO gm_admin (id, username, password, role_id, status) VALUES (1, 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 1, 1);

-- 安全管理菜单。表结构由 Entry 自动建表链路生成，这里只补默认菜单数据。
INSERT IGNORE INTO gm_menu (id, name, permission, parent_id, path, icon, sort_order) VALUES (900, '安全管理', 'security:view', 0, '', 'fas fa-shield-alt', 900);
INSERT IGNORE INTO gm_menu (id, name, permission, parent_id, path, icon, sort_order) VALUES (901, '封禁管理', 'security:ban', 900, '/gm/security/ban', 'fas fa-ban', 901);
INSERT IGNORE INTO gm_menu (id, name, permission, parent_id, path, icon, sort_order) VALUES (902, '安全日志', 'security:event', 900, '/gm/security/event', 'fas fa-clipboard-list', 902);
