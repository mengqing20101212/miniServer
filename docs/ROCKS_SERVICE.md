# RocksService 持久化

`save/update/delete/asyncSave/asyncUpdate` 在 RocksDB WAL 同步刷盘后成功返回。
MySQL 由后台事务异步提交；失败记录永久保留并退避重试，不按重试次数丢弃。
同一实体的新全量状态替换旧状态。DELETE 为持久化删除操作。

## 上线配置

在各服务 Nacos 的 `db` 配置增加：

```json
{
  "idNodeId": 1001,
  "rocksPath": "/persistent/miniserver/rocksdb",
  "queryBarrierTimeoutMillis": 30000
}
```

`idNodeId` 必须显式分配为 0..16383，并在所有生成 ID 的 JVM 中唯一；缺失时启动失败。
Game、Login、Scene、GM 即使服务编号尾数相同，也必须使用不同的 idNodeId。
本地可使用 `-Dminiserver.idNodeId=1001 -Dminiserver.rocksPath=...` 覆盖。
同一节点不能同时运行两个生成器；文件锁只保护共享本地状态目录，跨主机唯一性由部署配置保证。

数据目录按环境、服务 ID、数据库 URL（不含连接参数）的摘要隔离。目录必须在持久磁盘上，
重启、升级、容器重建不能清空它。不要单独删除 outbox、ID reservation 或 MySQL 确认表。
ID 使用 41 位时间、14 位节点、8 位序列，纪元为 2026-01-01 UTC。
生成前强制保存时间预留区间；快速重启若时钟尚未超过旧区间，启动会报错，稍后重启即可。
时钟回拨拒绝继续生成，不存在允许重复 ID 的 DEBUG 模式。

已有 INT 主键及 GM 关联列需先执行 `docs/migrations/rocks-service-bigint.sql` 中适用的语句，
保留已有值。没有 GM 表的数据库不执行 GM 部分。生成器拒绝重新生成未迁移的自增 INT 列。
升级前用旧版本完成历史 Java 序列化死信的回放；新实体字段类型变化可能导致旧死信不能解码。
新版本仍尝试导入历史死信，失败会中止启动并保留原文件，不能跳过或删除。

## 自动保存边界

生成 setter 在值变化时递增版本。加载和首次构造不会自动写入；新实体先调用一次 `save` 进入托管。
托管实体以后无需显式 update：玩家任务、公共协议路由、场景命令、GM REST 控制器通过
`PersistenceScope` 合并本任务内的修改。新任务入口可使用 `try (PersistenceScope scope = PersistenceScope.open())`。
未处于上下文的托管 setter 会立即持久化，保证不漏写，但不能合并多次 set。
查询或发送响应前会提前 flush，因此同一任务可能在这些一致性边界产生多份快照。

实体必须在所属玩家/场景/业务线程中修改。全量覆盖要求同一行只有一个权威写入方，
并且实体完整加载。加载查询结果不标脏；相同值 setter 不标脏；托管主键不可改变。
集合或 byte[] 应先复制、修改副本，再调用 setter；修改旧引用后赋回相同对象不能检测变化。
玩家混合存储的 Blob setter 和 Entity setter 统一登记到 PlayerData。Blob 在作用域刷新时编码，
英雄和资源直接保存独立 Entity；新增记录无需业务手动 save。详见 `PLAYER_MODULE_PERSISTENCE_PLAN.md`。
作用域不是业务事务：异常也会保存已发生的修改，不自动回滚内存，不提供跨实体原子业务事务。
协程每个实际执行段单独建立作用域，不能跨异步线程依赖 ThreadLocal。
协程、场景任务在作用域成功写入 RocksDB 后才完成 Future，保存失败则返回异常。

`changeVersion != acceptedVersion` 表示仍有修改未交给 RocksDB；字段位图不再使用。
后台记录存储格式版本、全量 SQL 参数快照与操作版本，使用 Fury 0.10.3 的线程安全兼容配置。
快照不持有业务可变引用。只读取应用自行写入的本地序列化数据。

## 查询和恢复

所有通过 MysqlConnector.select/selectStrict 的查询都会先 flush 当前任务，
捕获当前数据库待提交实体版本并等待提交，覆盖任意 SQL、JOIN、排序和分页。
没有解析 SQL，因此使用数据库级屏障；失败实体会阻止屏障通过，超时抛出明确异常。
之后的新实体写入不延长本次目标；同实体新快照覆盖目标时，提交该新快照也可满足旧目标。
屏障只覆盖本进程已接收的写入；共享数据库其他服务的未提交 outbox 不在本进程屏障内。
跨服务读写必须由权威服务协调提交后再查询。多条 SQL 的一致快照仍需 MySQL 事务。

业务 SQL 和 `rocks_entity_commit` 的确认版本在同一 MySQL 事务提交。
提交后、RocksDB 清理前崩溃会安全重放并识别已完成版本；旧快照完成不能清理新快照。
启动先扫描恢复并等待历史积压提交，失败时不带着旧数据提供业务服务。
停服停止接收并终止后台线程，未完成记录留盘，不要求数据库故障期间无限等待停服。

本实现批量组织事务，批次失败回滚后按实体处理，让其他实体继续前进。
不支持自定义增量 SQL、跨实体事务和跨节点同时写同一实体。
MySQL 业务表与确认表都必须使用 InnoDB。硬盘丢失不属于本地 outbox 可恢复范围。

## 测试

`cd server && bash ./mvnw -B test` 运行各模块自动测试。
新增测试使用真实本地 RocksDB、Fury 和 H2 MySQL 模式下的真实 JDBC 事务，
覆盖失败恢复、实体覆盖、旧版本清理、新版本隔离、提交后崩溃重复恢复、生成器和 setter 自动收集。
H2 不能替代真实 MySQL 的部署联调。

历史外部连接测试改为显式提供测试环境后运行，不再默认连接仓库中的远程实例：
`MINISERVER_TEST_MYSQL_URL/USER/PASSWORD`、`MINISERVER_TEST_REDIS_HOST/PORT/PASSWORD`、
`MINISERVER_TEST_NACOS_URL`。没有相应配置时这些测试明确标记为 skipped。
MySQL 测试仍会写入测试记录，只能指向专用测试数据库。
