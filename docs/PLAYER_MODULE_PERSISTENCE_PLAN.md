# 玩家混合持久化

当前实现保留两种方式：小模块 Protobuf Blob、独立对象 Entity。新项目直接使用新表结构，不兼容旧数据。

## 数据划分

- `PlayerLogicModule`：登录时间等少量状态保存在 `player_module`。
- `HeroModule`：容量保存在模块 Blob；每个英雄保存为 `player_hero` 记录。
- `ResourceModule`：货币和堆叠道具按玩家、资源类型保存为 `player_resource`，数量为零保留记录；不创建资源模块 Blob。
- 后续有独立属性的道具使用实例 Entity，不将实例列表放进资源 Blob。
- 地图对象、行军、集结继续使用现有独立 Entity。

## 变化和保存

业务通过封装的模块方法和 Entity setter 修改数据，无需 `saveData/update`。
Blob setter 标记模块变化，Entity setter 自动登记所属玩家的变化。英雄集合只读，增删通过模块方法。
同一 `PersistenceScope` 内，每个脏模块刷新时编码一次、每个脏 Entity 提交一次。
查询、RPC、Future 完成前的已有作用域刷新同时覆盖两种存储。提前刷新后再次修改，下一边界重新提交。
没有作用域时立即保存，避免漏写；多个字段修改应在玩家任务中执行。

所有快照在所属玩家执行线程生成，直接交给 RocksService；不再使用模块级内存异步队列。
成功表示 RocksDB WAL 已可靠接收，MySQL 异步提交并由 RocksService 重试、恢复。
保存失败抛出异常并保留脏状态，重试不会清除较新变化。异常作用域也保存已发生修改，不提供业务回滚。
跨 Entity 操作不是事务，跨节点接管仍需排空原服任务并等待原服提交。

英雄实例 ID 使用框架主键生成器，不再由玩家 ID 乘法拼接。加载失败、Blob 解码失败中止玩家初始化，禁止静默重置。

## 新环境

使用 `generated-sql/create-tables.sql` 创建新库；旧 player.modules、旧英雄/资源模块 Blob 无迁移路径。
本次代码修改不自动删除数据库。重建测试环境须同时协调旧 RocksDB 待提交记录，避免旧格式被重新写回新表。
