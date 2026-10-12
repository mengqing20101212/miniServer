# Award 与 Consume

接口沿用 SlgServer 的短名称，不引入 Service、Plan 或强制 operationContext。

```java
Consume cost = Consume.create().addThing(currencyId, 100).addThing(itemId, 2);
if (!cost.isEnoughAndCost(player, ChangeReason.HERO_LEVEL_UP)) {
    return;
}
hero.setLevel(nextLevel);

Award.create().addThing(currencyId, 10).award(player, ChangeReason.QUEST_REWARD);
```

在所属玩家队列及 PersistenceScope 中执行。isEnough 只检查；isEnoughAndCost 先合并
相同 ID 的全部消耗、检查所有余额，再扣除。空集合成功，负数、非法 ID 或数量溢出抛异常。
create(String)/addThing(String) 接受 ID;数量|ID;数量，完整解析成功才修改已有集合。
addConsume/addAward 不共享可变内部数据，toMap 返回独立只读快照。

货币和堆叠道具统一使用 ResourceModule。英雄通过 Award.addHero(heroId) 明确指定，
避免两类表 ID 相同导致误判。第一版每种英雄只拥有一份，重复英雄拒绝，不自动转碎片。
Award 预检所有资源溢出和英雄配置、容量、重复条件后发放。
同一 Award 对象禁止重复发放；这不替代业务流水幂等，Consume 重复调用会再次扣除。
ChangeReason 在持久化接收成功后输出审计日志，日志不是持久化财务流水。

资源推送按保存边界合并，RocksDB 接收成功后发送；不会因为通知失败重复扣款或发奖。
英雄升级把经验道具与额外费用合并后扣除，升星、觉醒同样使用 Consume。
新增英雄按 heroId 判断重复，不再推导主键；多份相同英雄请求在任何修改前拒绝。

玩家边界统一准备 Blob、普通 Entity 和删除记录，以一个 WriteBatch 接收。
RocksService 保存组标识，组内 MySQL SQL 与确认版本同事务提交，失败不拆组降级。
新快照覆盖组内实体时保留该未完成组的其他实体，重启后仍整体提交。
这不提供内存回滚；保存拒绝会抛异常且保留脏状态，恢复后再次提交，不能让调用方把异常当作成功。
查询、发送 RPC 和响应会提前刷新，因此一次业务若跨多个刷新边界，不构成单一原子事务。
跨玩家、跨节点事务和业务流水幂等不属于本次辅助类的能力。
