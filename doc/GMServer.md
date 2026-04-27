# GMServer 模块开发文档

## 项目信息
- **项目**: miniServer 游戏服务器
- **模块**: GMServer（后台管理系统）
- **路径**: `server/GMServer/`
- **起始时间**: 2026-04-27

---

## 已完成

### 基础框架
- [x] 创建 GMServer 模块目录结构
- [x] `server/GMServer/pom.xml`（Spring Boot 3.4.4 + Spring Security）
- [x] 添加 GMServer 到 `server/pom.xml` parent modules

### 数据库
- [x] 设计 6 张 GM 表：`gm_admin`, `gm_role`, `gm_role_permission`, `gm_menu`, `gm_role_menu`, `gm_operation_log`
- [x] `schema.sql` 建表脚本
- [x] 连接数据库（118.25.76.117:3306/pick_money）执行建表

### 代码生成
- [x] 改造 `tool/ParserDbEntry.java`：支持命令行参数、表名前缀过滤（`gm_*`）、自动执行 schema.sql、跨平台路径
- [x] 改造 `tool/ToolMain.java`：添加 `parserDbEntry` 命令
- [x] 编译 tool 模块
- [x] 自动生成 6 张表的 Entry/Helper 到 `ly.db.entry` 包
- [x] 删除旧的手写 Entity 文件（`ly.gmserver.entity` 包）

### 安全框架
- [x] `JwtUtil.java` — JWT 令牌生成/验证
- [x] `JwtAuthFilter.java` — JWT 认证过滤器（SecurityContextHolder 注入）
- [x] `WebSecurityConfig.java` — Spring Security 配置（放行 login 路径）
- [x] `OperationLogAspect.java` — 操作日志 AOP（跳过 login 和静态资源）

### 构建
- [x] `application.yml`（端口 9090, 数据源配置）
- [x] `GMServerApplication.java` — Spring Boot 启动类
- [x] 编译验证通过
- [x] Git 提交（commit `316dbf8`）

---

## 待完成

### 1. Controller/Service 业务代码
- [ ] `GmAdminController` — 管理员登录、CRUD
- [ ] `GmAdminService` — 管理员业务逻辑
- [ ] `GmRoleController` — 角色 CRUD
- [ ] `GmRoleService` — 角色 + 权限业务
- [ ] `GmMenuController` — 菜单 CRUD
- [ ] `GmMenuService` — 菜单管理
- [ ] DTO/Request 封装类

### 2. Thymeleaf 前端页面
- [ ] 登录页面（login.html）
- [ ] 管理员管理页面
- [ ] 角色管理页面
- [ ] 菜单管理页面
- [ ] 操作日志查看页面

### 3. 收尾
- [ ] 最终编译验证
- [ ] Git 提交

---

## 关键信息

### 数据库
| 字段 | 值 |
|------|----|
| 主机 | 118.25.76.117 |
| 端口 | 3306 |
| 数据库 | pick_money |
| 用户 | root |

### JDBC URL 参数
```
useSSL=false&allowPublicKeyRetrieval=true&connectTimeout=8000&socketTimeout=8000
```

### 工具链
| 工具 | 路径 |
|------|------|
| JDK 21 | /usr/lib/jvm/java-21-openjdk-amd64 |
| Maven | /mnt/d/Soft/env/apache-maven-3.9.15/bin/mvn |
| 项目路径 | /mnt/d/WORK/me/miniServer |

### 生成命令
```bash
# 编译 tool
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 mvn -f server/pom.xml package -pl tool -DskipTests -q

# 运行 ParserDbEntry（需从 server/ 目录执行）
cd server && /usr/lib/jvm/java-21-openjdk-amd64/bin/java -cp tool/target/tool-1.0-SNAPSHOT.jar ly.ParserDbEntry GMServer

# 编译 GMServer
JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64 mvn -f server/pom.xml compile -pl GMServer -am -q
```
