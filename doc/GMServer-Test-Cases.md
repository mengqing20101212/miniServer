# GMServer 接口测试用例文档

## 环境信息
- **服务地址**: http://localhost:9090
- **默认管理员**: admin / admin123
- **认证方式**: Bearer Token（除/login 外所有接口）
- **统一响应格式**: `{"code":200,"message":"success","data":...}`

---

## 一、认证接口（/api/admin/*）

### TC-01 管理员登录（成功）
- **URL**: `POST /api/admin/login`
- **请求体**: `{"username":"admin","password":"admin123"}`
- **预期**: code=200, data.token 非空, data.admin.id=1
- **关联**: 获取 token 供后续测试使用

### TC-02 管理员登录（密码错误）
- **URL**: `POST /api/admin/login`
- **请求体**: `{"username":"admin","password":"wrongpass"}`
- **预期**: code=400, message="用户名或密码错误"

### TC-03 获取当前用户（/me）
- **URL**: `GET /api/admin/me`
- **Header**: Authorization: Bearer {token}
- **预期**: code=200, data.username="admin"

### TC-04 获取管理员信息（/info）
- **URL**: `GET /api/admin/info`
- **Header**: Authorization: Bearer {token}
- **预期**: code=200, data.id=1, data.username="admin"

### TC-05 未认证访问
- **URL**: `GET /api/admin/me`
- **不传 Authorization 头**
- **预期**: HTTP 403（Spring Security 拦截）

### TC-06 管理员注销（/logout）
- **URL**: `POST /api/admin/logout`
- **Header**: Authorization: Bearer {token}
- **预期**: code=200

---

## 二、管理员管理接口（/api/admin/*）

### TC-07 管理员列表
- **URL**: `GET /api/admin/list?page=1&pageSize=20`
- **预期**: code=200, data.list 非空, data.total > 0

### TC-08 管理员列表（带关键词搜索）
- **URL**: `GET /api/admin/list?keyword=admin`
- **预期**: code=200, data.list[0].username 包含 "admin"

### TC-09 获取单个管理员
- **URL**: `GET /api/admin/1`
- **预期**: code=200, data.id=1

### TC-10 获取不存在的管理员
- **URL**: `GET /api/admin/99999`
- **预期**: code=400, message="管理员不存在"

### TC-11 创建管理员
- **URL**: `POST /api/admin/create`
- **参数**: username=test_admin, password=test123, roleId=2
- **预期**: code=200

### TC-12 创建重名管理员
- **URL**: `POST /api/admin/create`
- **参数**: username=admin, password=test123
- **预期**: code=400, message 包含 "已存在"

### TC-13 更新管理员
- **URL**: `POST /api/admin/update`
- **参数**: id={test_admin的id}, status=0
- **预期**: code=200

### TC-14 重置密码
- **URL**: `POST /api/admin/reset-password`
- **参数**: id={test_admin的id}, newPassword=newpass123
- **预期**: code=200

### TC-15 删除管理员
- **URL**: `POST /api/admin/delete/{id}`
- **路径参数**: {test_admin的id}
- **预期**: code=200

---

## 三、角色管理接口（/api/role/*）

### TC-16 角色列表
- **URL**: `GET /api/role/list`
- **预期**: code=200, data 数组非空（至少有超级管理员/运营/客服/只读）

### TC-17 获取单个角色
- **URL**: `GET /api/role/1`
- **预期**: code=200, data.id=1, data.name="超级管理员"

### TC-18 创建角色
- **URL**: `POST /api/role/create`
- **参数**: name=测试角色, description=仅用于测试, permissions=perm1, permissions=perm2
- **预期**: code=200

### TC-19 创建重名角色
- **URL**: `POST /api/role/create`
- **参数**: name=测试角色
- **预期**: code=400

### TC-20 更新角色
- **URL**: `POST /api/role/update`
- **参数**: id={test_role_id}, name=测试角色_已更新, description=更新描述
- **预期**: code=200

### TC-21 删除角色
- **URL**: `POST /api/role/delete/{id}`
- **路径参数**: {test_role_id}
- **预期**: code=200

---

## 四、菜单管理接口（/api/menu/*）

### TC-22 获取菜单树
- **URL**: `GET /api/menu/tree`
- **预期**: code=200, data 数组（初始为空）

### TC-23 创建根菜单
- **URL**: `POST /api/menu/create`
- **参数**: name=系统管理, icon=fa-cog, sortOrder=1, path=/system
- **预期**: code=200

### TC-24 创建子菜单
- **URL**: `POST /api/menu/create`
- **参数**: name=管理员管理, parentId={root_menu_id}, icon=fa-users, path=/gm/admin/list, sortOrder=1
- **预期**: code=200

### TC-25 获取单个菜单
- **URL**: `GET /api/menu/{id}`
- **预期**: code=200, data.name="系统管理"

### TC-26 更新菜单
- **URL**: `POST /api/menu/update`
- **参数**: id={menu_id}, name=系统设置, sortOrder=2
- **预期**: code=200

### TC-27 删除有子菜单的菜单
- **URL**: `POST /api/menu/delete/{root_menu_id}`
- **预期**: code=400（有子节点不能删除）

### TC-28 删除子菜单
- **URL**: `POST /api/menu/delete/{child_menu_id}`
- **预期**: code=200

### TC-29 删除根菜单（子菜单已删除后）
- **URL**: `POST /api/menu/delete/{root_menu_id}`
- **预期**: code=200

---

## 五、操作日志接口（/api/log/*）

### TC-30 日志列表
- **URL**: `GET /api/log/list?page=1&pageSize=10`
- **预期**: code=200, data.list 非空或空数组

### TC-31 日志列表（带筛选）
- **URL**: `GET /api/log/list?username=admin&action=AdminController`
- **预期**: code=200, data.list 按条件过滤

### TC-32 日志列表（日期范围）
- **URL**: `GET /api/log/list?dateFrom=2026-01-01&dateTo=2026-12-31`
- **预期**: code=200, data.list 在日期范围内

---

## 六、页面路由

### TC-33 访问登录页
- **URL**: `GET /gm/login`
- **预期**: HTTP 200, 返回 HTML 页面（含登录表单）

### TC-34 访问首页（未认证）
- **URL**: `GET /gm/`
- **预期**: 被 Spring Security 拦截跳转到 /gm/login

### TC-35 带 token 访问首页
- **URL**: `GET /gm/index`
- **Cookie/Header**: Bearer token
- **预期**: HTTP 200, 返回包含侧边栏的 HTML

### TC-36 访问管理员列表页
- **URL**: `GET /gm/admin/list`
- **预期**: HTTP 200, HTML 页面
