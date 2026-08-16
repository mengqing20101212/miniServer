# 玩家模块持久化拆分方案

状态：第一阶段已于 2026-08-16 实施，旧 BLOB 清理和专用业务表拆分待后续完成。

## 1. 背景

当前所有玩法模块都封装在 `PlayerEntry.modules` 中。模块先分别编码为
`byte[]`，再由 `PlayerModuleData` 以 `Map<String, byte[]>` 聚合并整体编码到
MySQL `player.modules` BLOB 列。

该结构在玩法较少时具备加载简单、单行原子更新等优点，但随着模块数量和玩家
养成数据增长，会产生以下问题：

- 修改单个模块也需要重新编码整个 `PlayerModuleData`，CPU 成本为总模块数据量级。
- 每次更新都要重新分配总 BLOB，增加临时对象和年轻代 GC 压力。
- MySQL 必须重写整个 `modules` 列，产生网络和存储写放大。
- 当前字段类型为 `BLOB`，容量上限约 64 KiB，长期无法承载持续增加的玩法数据。
- 内存中同时保留模块对象、各模块字节和总 BLOB，存在重复数据。

当前实现还需要单独核查保存时机：`saveData()` 会更新内存中的模块字节并标记
`PlayerEntry.modules` 为脏，但业务修改后是否统一进入异步数据库队列需要在实施前
补齐和验证。

## 2. 目标架构

采用混合持久化模型：

```text
player 基础表
  + player_module 通用模块表
  + 少量专用业务表
```

`player` 继续保存名称、等级、登录时间等基础字段。大部分玩法使用通用模块表，
自然无限增长或需要跨玩家查询的数据使用专用表。

建议表结构：

```sql
CREATE TABLE player_module (
    player_id       BIGINT NOT NULL,
    module_id       INT NOT NULL,
    data_version    INT NOT NULL,
    revision        BIGINT NOT NULL,
    module_data     MEDIUMBLOB NOT NULL,
    update_time     DATETIME(6) NOT NULL,
    PRIMARY KEY (player_id, module_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

`module_id` 必须使用稳定的显式编号，不能直接使用可能因枚举顺序变化而改变的
ordinal。`data_version` 用于模块 protobuf 结构升级，`revision` 用于异步保存和
并发版本确认。

玩家登录时通过一次索引范围查询加载全部通用模块：

```sql
SELECT module_id, data_version, revision, module_data
FROM player_module
WHERE player_id = ?;
```

## 3. 数据归属

适合存放在 `player_module`：

- 英雄养成、任务状态、活动进度、图鉴、功能解锁等玩家私有状态。
- 数据规模有明确上限，不需要直接使用 SQL 做跨玩家筛选或排序的模块。

适合拆成专用业务表：

- 背包物品、邮件、好友、战斗记录等可能持续增长的数据。
- 充值、货币流水等需要审计和幂等保障的数据。
- 公会成员、排行榜等需要跨玩家查询的数据。

不采用“每个小玩法一张表”。普通玩法统一使用模块表，只有数据特性确实不同的
模块才建立专用表。

## 4. 保存语义

业务修改时只标记模块 dirty，不立即序列化和写库：

```text
业务修改
  -> 标记模块 dirty，并递增模块 revision
  -> 消息处理结束或定时刷新
  -> 每个脏模块只序列化一次
  -> 同一玩家的相关模块在一个事务中批量 UPSERT
  -> 数据库成功后按对应 revision 清除 dirty
```

英雄升级同时消耗资源时，HERO 和 RESOURCE 必须作为同一个持久化单元提交，避免
只保存其中一个模块。充值、货币消耗等关键操作采用立即提交或可靠日志；普通玩法
进度可以短周期合并写，并在玩家退出和服务器停机时强制刷新。

异步任务必须保存不可变快照和提交 revision，不能只持有可继续变化的 Entry
对象引用。只有数据库确认写入对应 revision 后才能清理该版本的 dirty 状态，避免
并发修改被较早的写任务错误标记为已持久化。

## 5. 实施步骤

1. 增加观测指标：总 BLOB 大小分布、模块大小分布、编码耗时、分配字节、每秒写入
   字节、数据库队列长度和失败重试量。
2. 临时将旧 `player.modules` 升级为 `MEDIUMBLOB`，避免迁移前先触碰 64 KiB 上限。
3. 在旧结构上先实现模块 dirty 集合和统一刷新，消除一次业务中重复编码总 BLOB。
4. 建立 `player_module`、模块稳定编号、数据版本和 revision 保存协议。
5. 新玩家直接使用新结构；老玩家登录时读取旧 BLOB，在事务中拆分并写入模块表。
6. 使用玩家存储版本控制读路径。灰度和回滚期间按发布策略决定是否临时双写。
7. 完成数据量、数据一致性、掉线保存、停机刷新、重试和回滚测试后删除旧 BLOB 路径。
8. 最后按实际规模拆分背包、邮件、流水等专用表。

## 6. 实施前基线

先执行数据库容量统计，确认迁移优先级：

```sql
SELECT
    COUNT(*) AS player_count,
    ROUND(AVG(OCTET_LENGTH(modules)) / 1024, 2) AS avg_kib,
    ROUND(MAX(OCTET_LENGTH(modules)) / 1024, 2) AS max_kib
FROM player;
```

同时记录 P50、P95、P99，重点关注接近 64 KiB 的玩家，以及序列化耗时和 GC 是否随
总模块大小线性增长。迁移收益应以这些基线数据验证，而不是只以模块数量判断。

## 7. 暂不采用的方案

- 只把 `BLOB` 改为 `MEDIUMBLOB`：只能解决容量，不能解决写放大。
- 改用 MySQL JSON 并做局部路径更新：不适合当前 protobuf 模型，类型和版本管理更差。
- 把 Redis 作为唯一持久化：会把问题转化为数据可靠性和恢复复杂度。
- 全量事件溯源：对当前项目复杂度过高，仅货币和充值流水可采用追加日志思想。

## 8. 最终决策

长期目标是将“玩家级大 BLOB”拆成“模块级小 BLOB”，并针对少数大规模或需要查询
的数据建立专用表。实施时优先修正 dirty、批量刷新和 revision 语义，再迁移存储，
避免只完成表结构拆分却保留不可靠的异步保存行为。

## 9. 已实施内容

- `ModuleEnum` 使用显式稳定 `module_id` 和 `data_version`。
- 新增 `player_module` 联合主键表，单个模块使用 `MEDIUMBLOB` 保存。
- 玩家登录优先读取模块表；模块表无数据时读取旧 `player.modules`，并在初始化后异步迁移。
- 模块保存使用不可变字节快照和单调递增 `revision`，旧批次晚到不会覆盖新版本。
- 每次玩家消息、事件或协程任务结束时，把本次变更的全部模块放在同一个 MySQL 事务中 UPSERT。
- 异步保存队列提供容量限制、并行虚拟线程、指数退避重试和停服等待刷新。
- `player.modules` 暂时保留作为老数据回退入口，并升级为 `MEDIUMBLOB`；新逻辑不再回写总 BLOB。

当前阶段不清空旧 BLOB，避免回滚时失去旧版本可读数据。正式删除旧字段前，需要完成存量迁移率统计、
灰度观察和回滚窗口确认；背包、邮件、流水等专用表仍按实际数据规模单独实施。
