<?xml version="1.0" encoding="UTF-8"?>
<map version="1.0.1">
  <node TEXT="MiniServer 线上项目优化方向">
    <node TEXT="当前约束">
      <node TEXT="项目已稳定运行多年"/>
      <node TEXT="固定分区分服，玩家不会常规漂移到其他节点"/>
      <node TEXT="WebSocket + JSON + 阿里云负载均衡目前无明显线上问题"/>
      <node TEXT="数据库异步落库方案暂不确定"/>
      <node TEXT="没有现成压测模块和机器人"/>
    </node>
    <node TEXT="需要做：第一优先级">
      <node TEXT="建立观测和基线">
        <node TEXT="协议耗时：P50/P95/P99"/>
        <node TEXT="JSON 解析和序列化耗时"/>
        <node TEXT="MyBatis SQL 耗时、慢 SQL"/>
        <node TEXT="RPC 耗时、超时、失败率"/>
        <node TEXT="线程池活跃数、队列长度、拒绝次数"/>
        <node TEXT="数据库连接池使用率"/>
        <node TEXT="GC、Full GC、堆内存"/>
      </node>
      <node TEXT="RPC 外围增强，不改底层框架">
        <node TEXT="统一 RpcClientWrapper"/>
        <node TEXT="连接超时和读取超时"/>
        <node TEXT="独立 RPC 线程池，避免阻塞协议线程"/>
        <node TEXT="请求 ID、调用日志和失败分类"/>
        <node TEXT="有限重试、熔断和降级"/>
        <node TEXT="关键接口增加幂等参数"/>
      </node>
      <node TEXT="玩家业务串行化：先做高风险操作">
        <node TEXT="不为每个玩家创建真实线程"/>
        <node TEXT="同一玩家进入同一串行队列"/>
        <node TEXT="不同玩家继续并行处理"/>
        <node TEXT="优先覆盖扣费、发奖、领取、交易、结算"/>
        <node TEXT="避免重复操作、状态覆盖和并发修改"/>
      </node>
    </node>
    <node TEXT="需要做：第二优先级">
      <node TEXT="渐进优化 String + JSON 热点">
        <node TEXT="不做全项目一次性替换"/>
        <node TEXT="先定位高频协议和高耗时模块"/>
        <node TEXT="避免同一请求重复反序列化"/>
        <node TEXT="复用已解析对象"/>
        <node TEXT="减少整份 JSON 重建和无意义全量写入"/>
        <node TEXT="新增模块优先使用明确 Java 对象"/>
      </node>
      <node TEXT="RPC 结果的应用层异步化">
        <node TEXT="底层同步 Thrift/Nifty 保持不变"/>
        <node TEXT="同步调用放入 RPC 专用线程池"/>
        <node TEXT="完成后把结果投递回玩家队列"/>
        <node TEXT="同一玩家关键 RPC 保证顺序"/>
        <node TEXT="非关键通知类 RPC 可先异步化"/>
        <node TEXT="扣费、发奖、交易等仍需明确成功结果"/>
      </node>
    </node>
    <node TEXT="暂不做：保持线上稳定">
      <node TEXT="不全面替换 String + JSON 内存模型"/>
      <node TEXT="不改固定分区分服和玩家路由"/>
      <node TEXT="不改 WebSocket 接入协议"/>
      <node TEXT="不改阿里云负载均衡配置"/>
      <node TEXT="不重写 Thrift/Nifty RPC 底层"/>
      <node TEXT="不直接把全项目改为异步落库"/>
      <node TEXT="不大范围重写登录、连接和分服机制"/>
    </node>
    <node TEXT="待定：数据库持久化">
      <node TEXT="先统计读写频率、慢 SQL、重复写入"/>
      <node TEXT="区分关键数据和普通数据"/>
      <node TEXT="关键数据：事务、幂等、可确认成功"/>
      <node TEXT="普通数据：再评估批量保存或异步快照"/>
      <node TEXT="如采用异步，需要版本号、顺序、重试、恢复机制"/>
      <node TEXT="方案候选：同步事务 / 异步快照 / Outbox / 可靠消息"/>
    </node>
    <node TEXT="验证方式">
      <node TEXT="先用日志和线程栈定位，不依赖完整压测"/>
      <node TEXT="人工重复测试关键协议"/>
      <node TEXT="观察数据库变慢时线程池是否堆积"/>
      <node TEXT="观察同一玩家快速操作是否乱序"/>
      <node TEXT="后续只做最小 WebSocket 回放工具"/>
    </node>
    <node TEXT="推荐实施顺序">
      <node TEXT="1. 监控和耗时统计"/>
      <node TEXT="2. RPC 超时、日志、幂等外围封装"/>
      <node TEXT="3. 高风险玩家操作串行化"/>
      <node TEXT="4. 优化热点 JSON 重复解析和序列化"/>
      <node TEXT="5. 根据真实数据决定数据库方案"/>
      <node TEXT="6. 必要时补充最小 WebSocket 测试工具"/>
    </node>
  </node>
</map>
