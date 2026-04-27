# GMServer 开发进度（中断于 2026-04-27 18:54）

## 已完成
- [x] 创建 GMServer 模块目录结构
- [x] `server/GMServer/pom.xml`（Spring Boot 3.4.4）
- [x] 添加 GMServer 到 `server/pom.xml` (parent modules)
- [x] `schema.sql` — 6 张表：gm_admin, gm_role, gm_role_permission, gm_menu, gm_role_menu, gm_operation_log
- [x] 修改 `tool/ParserDbEntry.java`：支持命令行参数 `core` 或 `GMServer` 指定输出目标
- [x] 编译 tool 模块
- [x] 创建 `GMServer/src/main/java/ly/db/entry/` 目录

## 待完成
1. 连接数据库 `118.25.76.117:3306/pick_money`，执行 schema.sql 建表
2. 运行 `ParserDbEntry GMServer` 生成 Entry/Helper 代码到 GMServer 模块
3. 编写 `application.yml`（端口 9090）
4. 编写 `GMServerApplication.java`
5. 编写 Security/JWT/AOP 框架代码
6. 编写 Controller/Service 业务代码
7. 编写 Thymeleaf 前端页面
8. 编译验证 + 提交

## 关键信息
- DB: 118.25.76.117:3306/pick_money, user=root, password=Ly@2026Root!8899
- JDK 21: D:\Soft\env\Java\jdk-21\bin\java.exe
- Maven: D:\Soft\env\apache-maven-3.9.15\bin\mvn.cmd
- 项目路径: D:\WORK\me\miniServer
- Spring Boot: 3.4.4
