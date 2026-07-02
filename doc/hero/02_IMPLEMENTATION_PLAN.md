# Hero 模块实现计划

## 1. 实现阶段划分

### 阶段一：基础框架搭建（预计 2-3 天）

#### 1.1 数据库表创建
- [ ] 创建 player_hero 表
- [ ] 创建 player_resource 表
- [ ] 创建索引和约束

#### 1.2 基础数据模型
- [ ] PlayerHero 实体类
- [ ] PlayerResource 实体类
- [ ] HeroInfoConfig 配置类（已存在）
- [ ] HeroExpConfig 配置类
- [ ] HeroStarConfig 配置类
- [ ] HeroAwakenConfig 配置类

#### 1.3 DAO 层
- [ ] PlayerHeroDao 接口
- [ ] PlayerResourceDao 接口
- [ ] MyBatis Mapper XML

#### 1.4 Service 层接口定义
- [ ] IHeroService 接口
- [ ] IResourceService 接口

### 阶段二：资源系统实现（预计 2 天）

#### 2.1 资源服务实现
- [ ] ResourceServiceImpl 实现
- [ ] 资源增加方法
- [ ] 资源扣除方法
- [ ] 资源查询方法
- [ ] 资源变化通知

#### 2.2 资源配置
- [ ] 资源类型常量定义
- [ ] 资源配置表

#### 2.3 单元测试
- [ ] 资源服务测试
- [ ] 并发资源测试

### 阶段三：Hero 核心功能实现（预计 3-4 天）

#### 3.1 英雄查询功能
- [ ] 获取英雄列表
- [ ] 获取单个英雄信息
- [ ] Proto 协议实现

#### 3.2 英雄升级功能
- [ ] 升级逻辑实现
- [ ] 经验计算
- [ ] 等级上限验证
- [ ] Proto 协议实现

#### 3.3 英雄升星功能
- [ ] 升星逻辑实现
- [ ] 材料消耗计算
- [ ] 星级上限验证
- [ ] Proto 协议实现

#### 3.4 英雄觉醒功能
- [ ] 觉醒逻辑实现
- [ ] 觉醒条件验证
- [ ] 觉醒上限验证
- [ ] Proto 协议实现

### 阶段四：网络层集成（预计 2 天）

#### 4.1 消息处理器
- [ ] HeroListHandler
- [ ] HeroLevelUpHandler
- [ ] HeroStarUpHandler
- [ ] HeroAwakenHandler
- [ ] ResourceQueryHandler

#### 4.2 消息注册
- [ ] 注册到 NetService
- [ ] 消息号映射

### 阶段五：测试与优化（预计 2-3 天）

#### 5.1 单元测试
- [ ] HeroService 测试
- [ ] ResourceService 测试
- [ ] 边界条件测试

#### 5.2 集成测试
- [ ] 端到端功能测试
- [ ] 性能测试
- [ ] 并发测试

#### 5.3 优化
- [ ] 缓存优化
- [ ] 数据库查询优化
- [ ] 代码重构

## 2. 详细任务清单

### 2.1 数据层

#### PlayerHero 实体类
```java
public class PlayerHero {
    private Long id;
    private Long playerId;
    private Integer heroId;
    private Integer level;
    private Integer star;
    private Integer awaken;
    private Long exp;
    private Date createTime;
    private Date updateTime;
    // getters and setters
}
```

#### PlayerResource 实体类
```java
public class PlayerResource {
    private Long id;
    private Long playerId;
    private Integer resourceType;
    private Long amount;
    private Date createTime;
    private Date updateTime;
    // getters and setters
}
```

### 2.2 配置层

#### 资源类型定义
```java
public class ResourceType {
    public static final int GOLD = 1;        // 金币
    public static final int DIAMOND = 2;     // 钻石
    public static final int HERO_DEBRIS = 3; // 英雄碎片
    public static final int EXP_ITEM = 4;    // 经验道具
    public static final int AWAKEN_ITEM = 5; // 觉醒道具
}
```

### 2.3 服务层

#### HeroService 接口
```java
public interface IHeroService {
    /**
     * 获取玩家英雄列表
     */
    List<HeroInfo> getHeroList(Long playerId);

    /**
     * 获取单个英雄信息
     */
    HeroInfo getHeroInfo(Long heroUid);

    /**
     * 英雄升级
     */
    HeroLevelUpResult levelUp(Long heroUid, List<Integer> expItems);

    /**
     * 英雄升星
     */
    HeroStarUpResult starUp(Long heroUid, Integer targetStar);

    /**
     * 英雄觉醒
     */
    HeroAwakenResult awaken(Long heroUid);
}
```

#### ResourceService 接口
```java
public interface IResourceService {
    /**
     * 增加资源
     */
    boolean addResource(Long playerId, Integer resourceType, Long amount);

    /**
     * 扣除资源
     */
    boolean deductResource(Long playerId, Integer resourceType, Long amount);

    /**
     * 批量扣除资源
     */
    boolean deductResources(Long playerId, Map<Integer, Long> resources);

    /**
     * 获取资源数量
     */
    Long getResource(Long playerId, Integer resourceType);

    /**
     * 批量获取资源
     */
    Map<Integer, Long> getResources(Long playerId, List<Integer> resourceTypes);

    /**
     * 检查资源是否充足
     */
    boolean checkResource(Long playerId, Integer resourceType, Long amount);

    /**
     * 检查批量资源是否充足
     */
    boolean checkResources(Long playerId, Map<Integer, Long> resources);
}
```

### 2.4 网络层

#### 消息处理器基类
```java
public abstract class BaseHandler<T> {
    protected abstract void handle(ChannelHandlerContext ctx, T msg, Long playerId);

    protected void sendError(ChannelHandlerContext ctx, int errorCode) {
        // 发送错误响应
    }
}
```

#### HeroLevelUpHandler
```java
public class HeroLevelUpHandler extends BaseHandler<CS_HeroLevelUp> {
    @Autowired
    private IHeroService heroService;

    @Override
    protected void handle(ChannelHandlerContext ctx, CS_HeroLevelUp msg, Long playerId) {
        // 1. 验证参数
        // 2. 调用服务
        // 3. 返回结果
    }
}
```

## 3. 实现顺序

### 第一优先级（核心功能）
1. 数据库表创建
2. 实体类和 DAO
3. ResourceService 实现
4. HeroService 查询功能
5. HeroService 升级功能

### 第二优先级（扩展功能）
6. HeroService 升星功能
7. HeroService 觉醒功能
8. 网络层集成
9. 消息处理器

### 第三优先级（优化和测试）
10. 缓存实现
11. 单元测试
12. 集成测试
13. 性能优化

## 4. 依赖关系

```
数据库表 -> 实体类 -> DAO -> Service -> Handler
                     ↓
                  配置类
```

## 5. 风险点

### 5.1 技术风险
- **配置表格式变更**：需要预留灵活性
- **并发问题**：资源操作需要加锁或使用乐观锁
- **性能问题**：大量英雄查询需要优化

### 5.2 业务风险
- **数值平衡**：需要仔细设计升级曲线
- **资源产出**：需要控制资源产出与消耗的平衡

### 5.3 缓解措施
- 配置表解析增加容错机制
- 关键操作使用数据库事务
- 引入缓存层
- 充分的测试覆盖

## 6. 里程碑

- **M1（第 3 天）**：数据库和基础框架完成
- **M2（第 5 天）**：资源系统完成
- **M3（第 9 天）**：Hero 核心功能完成
- **M4（第 11 天）**：网络层集成完成
- **M5（第 14 天）**：测试和优化完成

## 7. 交付物

1. 数据库脚本（SQL）
2. 源代码（Java）
3. 单元测试代码
4. 集成测试代码
5. API 文档
6. 设计文档更新
