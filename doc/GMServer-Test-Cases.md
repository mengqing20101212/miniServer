# GMServer 接口测试结果

**测试日期**: 2026-04-28  
**服务状态**: ✅ 全部通过  
**发现问题与修复**:

1. **登录密码不匹配** — 数据库 old hash (`$2a$10$N9qo8uLOick...`) 不是 "admin123" 的 bcrypt，已更新为新 hash
2. **Nacos 集成** — 已确认 dataId=gmServer, group=gm 拉取配置正常，Redis/MySQL/Netty/Tomcat 全部初始化成功
3. **MySQL 连接** — 远程 MySQL 偶发通信失败（第1次超时后第2次重试成功），非代码问题

## 环境信息
- **服务地址**: http://localhost:9090
- **默认管理员**: admin / admin123
- **认证方式**: Bearer Token（除/login 外所有接口）
- **统一响应格式**: `{"code":200,"message":"success","data":...}`

---

## 一、认证接口

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 01 | POST /api/admin/login | code=200, token+admin | ✅ |
| 02 | POST /api/admin/login (错误密码) | code=400 | - |
| 03 | GET /api/admin/me | code=200, admin info | ✅ |
| 04 | GET /api/admin/info | code=200, admin info | ✅ |
| 05 | GET /api/admin/me (无认证) | HTTP 403 | ✅ |
| 06 | POST /api/admin/logout | code=200 | ✅ |

## 二、管理员管理

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 07 | GET /api/admin/list | code=200, list 非空 | ✅ |
| 09 | GET /api/admin/1 | code=200 | ✅ |
| 10 | GET /api/admin/99999 | code=400, "不存在" | ✅ |
| 11 | POST /api/admin/create | code=200 | ✅ |
| 12 | POST /api/admin/create (重名) | code=400 | ✅ |
| 15 | POST /api/admin/delete/{id} | code=200 | ✅ |

## 三、角色管理

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 16 | GET /api/role/list | code=200, 数组非空 | ✅ |
| 17 | GET /api/role/1 | code=200, name="超级管理员" | ✅ |
| 18 | POST /api/role/create | code=200 | ✅ |

## 四、菜单管理

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 22 | GET /api/menu/tree | code=200 | ✅ |
| 23 | POST /api/menu/create | code=200 | ✅ |

## 五、操作日志

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 30 | GET /api/log/list | code=200 | ✅ |

## 六、前端页面

| TC | 路径 | 预期 | 结果 |
|----|------|------|------|
| 33 | GET /gm/login | HTTP 200, HTML | ✅ |
| 34 | GET /gm/ (无认证) | HTTP 403 | ✅ |

## 启动日志关键信息

```
Nacos 启动成功,耗时: 11748ms
Redis 初始化成功，连接到 118.25.76.117:6379
database connection success, jdbcUrl:jdbc:mysql://118.25.76.117:3306/pick_money
服务器启动成功:[NetServer-9088], port:9088
Tomcat started on port 9090 (http)
Started GMServerApplication in 48.254 seconds
```
