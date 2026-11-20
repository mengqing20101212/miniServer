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
批量整卡用 `addHero(heroId, count)`。同批相同模板合并计数。未拥有时首张创建实例，其余每张折算 100 个 `heroInfo.heroDebris`；已拥有时全部折算。仅新实例占英雄槽，折算碎片和直接发的同 ID 道具合并入账。
同一 Award 对象禁止重复发放；这不替代业务流水幂等，Consume 重复调用会再次扣除。
ChangeReason 在持久化接收成功后输出审计日志，日志不是持久化财务流水。

资源推送按保存边界合并，RocksDB 接收成功后发送；不会因为通知失败重复扣款或发奖。
英雄升级把经验道具与额外费用合并后扣除，升星、觉醒同样使用 Consume。
英雄奖励在保存成功后向在线玩家推送一次完整的 `SC_HeroList`。主动查询沿用同一列表，离线玩家下次查询读取已保存的英雄。
客户端用 `CS_HeroUnlock` 传英雄模板 ID。Game 用 `Consume` 扣除英雄表 `unlockFragments` 个专属碎片（当前 10），再用 `Award` 创建英雄，两步在同一个玩家保存范围内完成。已拥有时直接返回原实例。纯重复整卡只推送资源变化。

机器人场景 `--test-hero-fragments 127.0.0.1 8889 game1001 新账号` 经 Login、Gate 登录。看到 `READY_BATCH` 后，在该玩家队列用 `Award.create().addHero(2, 3).addThing(3110003, 13)` 发测试奖励。看到 `READY_OWNED` 后用 `Award.create().addHero(2)` 再发一张。机器人自行核对列表、专属碎片余额和重复解锁结果。

玩家边界统一准备 Blob、普通 Entity 和删除记录，以一个 WriteBatch 接收。
RocksService 保存组标识，组内 MySQL SQL 与确认版本同事务提交，失败不拆组降级。
新快照覆盖组内实体时保留该未完成组的其他实体，重启后仍整体提交。
这不提供内存回滚；保存拒绝会抛异常且保留脏状态，恢复后再次提交，不能让调用方把异常当作成功。
查询、发送 RPC 和响应会提前刷新，因此一次业务若跨多个刷新边界，不构成单一原子事务。
跨玩家、跨节点事务和业务流水幂等不属于本次辅助类的能力。
