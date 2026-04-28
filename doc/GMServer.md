# GMServer 模块开发文档

## 状态：✅ 全部完成

GMServer 后台管理模块已完整实现，包含：
- 后端 REST API（管理员/角色/菜单 CRUD）
- 前端 Thymeleaf 页面（登录 + 管理后台）
- JWT 认证 + Spring Security
- 操作日志 AOP

**最新 commit**: `61b4ce3`

---

## 文件结构

```
server/GMServer/src/main/java/
├── ly/db/entry/                    ← 6张表的 Entry/Helper（自动生成）
├── ly/gmserver/
│   ├── GMServerApplication.java    ← 启动类
│   ├── config/
│   │   └── WebSecurityConfig.java  ← Spring Security 配置
│   ├── controller/
│   │   ├── AdminController.java    ← /api/admin/* (login, CRUD, /me, /info, /logout)
│   │   ├── RoleController.java     ← /api/role/* (CRUD + permissions)
│   │   ├── MenuController.java     ← /api/menu/* (CRUD + tree)
│   │   ├── LogController.java      ← /api/log/* (list with filters)
│   │   └── PageController.java     ← /gm/* (Thymeleaf 页面路由)
│   ├── dto/                        ← 6 个 DTO 类
│   ├── filter/
│   │   ├── JwtAuthFilter.java      ← JWT 认证过滤
│   │   └── OperationLogAspect.java ← 操作日志 AOP
│   ├── service/
│   │   ├── GmAdminService.java     ← 管理员业务
│   │   ├── GmRoleService.java      ← 角色 + 权限业务
│   │   └── GmMenuService.java      ← 菜单树业务
│   └── util/
│       └── JwtUtil.java            ← JWT 工具
└── resources/
    ├── application.yml             ← 端口 9090, DB: 118.25.76.117:3306/pick_money
    ├── schema.sql                  ← 6 张 GM 表
    └── templates/
        ├── login.html              ← 登录页
        ├── index.html              ← 管理后台布局（navbar + sidebar + iframe）
        ├── admin/list.html         ← 管理员列表
        ├── admin/form.html         ← 管理员新增/编辑
        ├── role/list.html          ← 角色列表
        ├── role/form.html          ← 角色新增/编辑（含权限 + 菜单勾选）
        ├── menu/list.html          ← 菜单树列表
        ├── menu/form.html          ← 菜单新增/编辑
        └── log/list.html           ← 操作日志查看
```

## 关键信息
- 端口: 9090
- 默认管理员: admin / admin123（bcrypt 加密）
- DB: 118.25.76.117:3306/pick_money, user=root
