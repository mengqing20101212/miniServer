# Hero 模块设计方案

## 1. 模块概述

Hero 模块是游戏的核心玩法模块之一，负责英雄的获取、培养、升级、升星、觉醒等核心功能。

### 1.1 功能范围

- **英雄管理**：英雄的获取、查询、列表展示
- **英雄升级**：消耗经验道具提升等级
- **英雄升星**：消耗升星道具和同名英雄提升星级
- **英雄觉醒**：消耗觉醒道具开启技能加成
- **资源管理**：消耗和获取游戏资源

### 1.2 设计原则

1. **配置驱动**：所有数值均由配置表控制，支持热更新
2. **事务性**：资源操作必须保证原子性
3. **可扩展**：预留接口支持后续扩展功能
4. **数据安全**：关键操作需客户端-服务端双重验证

## 2. 数据结构设计

### 2.1 数据库表设计

#### 2.1.1 玩家英雄表 (player_hero)

```sql
CREATE TABLE player_hero (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    player_id BIGINT NOT NULL COMMENT '玩家ID',
    hero_id INT NOT NULL COMMENT '英雄配置ID',
    level INT NOT NULL DEFAULT 1 COMMENT '等级',
    star INT NOT NULL DEFAULT 1 COMMENT '星级',
    awaken INT NOT NULL DEFAULT 0 COMMENT '觉醒等级',
    exp BIGINT NOT NULL DEFAULT 0 COMMENT '当前经验',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_player_hero (player_id, hero_id) COMMENT '玩家英雄唯一索引',
    KEY idx_player_id (player_id) COMMENT '玩家ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家英雄表';
```

#### 2.1.2 资源表 (player_resource)

```sql
CREATE TABLE player_resource (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    player_id BIGINT NOT NULL COMMENT '玩家ID',
    resource_type INT NOT NULL COMMENT '资源类型',
    amount BIGINT NOT NULL DEFAULT 0 COMMENT '数量',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_player_resource (player_id, resource_type) COMMENT '玩家资源唯一索引',
    KEY idx_player_id (player_id) COMMENT '玩家ID索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家资源表';
```

### 2.2 Proto 协议设计

#### 2.2.1 英雄协议

```protobuf
// 英雄相关协议 (MsgId: 5000-5099)

// 获取英雄列表请求
message CS_HeroList {
    int64 player_id = 1;  // 玩家ID
}

// 英雄信息
message HeroInfo {
    int64 hero_uid = 1;      // 英雄唯一ID
    int32 hero_id = 2;       // 英雄配置ID
    int32 level = 3;         // 等级
    int32 star = 4;          // 星级
    int32 awaken = 5;        // 觉醒等级
    int64 exp = 6;           // 当前经验
    int64 max_exp = 7;       // 升级所需经验
}

// 获取英雄列表响应
message SC_HeroList {
    repeated HeroInfo hero_list = 1;  // 英雄列表
}

// 英雄升级请求
message CS_HeroLevelUp {
    int64 hero_uid = 1;       // 英雄唯一ID
    repeated int32 exp_items = 2;  // 经验道具ID列表
}

// 英雄升级响应
message SC_HeroLevelUp {
    int32 result = 1;         // 结果 0:成功 1:失败
    int32 error_code = 2;     // 错误码
    HeroInfo hero_info = 3;    // 升级后的英雄信息
    map<int32, int64> cost_resources = 4;  // 消耗的资源
}

// 英雄升星请求
message CS_HeroStarUp {
    int64 hero_uid = 1;       // 英雄唯一ID
    int32 target_star = 2;    // 目标星级
}

// 英雄升星响应
message SC_HeroStarUp {
    int32 result = 1;         // 结果 0:成功 1:失败
    int32 error_code = 2;     // 错误码
    HeroInfo hero_info = 3;    // 升星后的英雄信息
    map<int32, int64> cost_resources = 4;  // 消耗的资源
}

// 英雄觉醒请求
message CS_HeroAwaken {
    int64 hero_uid = 1;       // 英雄唯一ID
}

// 英雄觉醒响应
message SC_HeroAwaken {
    int32 result = 1;         // 结果 0:成功 1:失败
    int32 error_code = 2;     // 错误码
    HeroInfo hero_info = 3;    // 觉醒后的英雄信息
    map<int32, int64> cost_resources = 4;  // 消耗的资源
}
```

#### 2.2.2 资源协议

```protobuf
// 资源相关协议 (MsgId: 5100-5199)

// 资源变化通知
message SC_ResourceChange {
    map<int32, int64> resource_changes = 1;  // 资源变化 map<资源类型, 变化量>
}

// 资源查询请求
message CS_ResourceQuery {
    repeated int32 resource_types = 1;  // 要查询的资源类型列表
}

// 资源查询响应
message SC_ResourceQuery {
    map<int32, int64> resources = 1;  // 资源列表 map<资源类型, 数量>
}
```

## 3. 业务逻辑设计

### 3.1 英雄升级流程

1. **客户端请求**：发送升级请求，包含英雄ID和经验道具
2. **服务端验证**：
   - 验证英雄归属
   - 验证资源充足
   - 验证等级上限
3. **资源扣除**：扣除经验道具
4. **经验计算**：计算总经验
5. **等级更新**：升级到对应等级
6. **数据保存**：保存到数据库
7. **响应通知**：返回结果和资源变化

### 3.2 英雄升星流程

1. **客户端请求**：发送升星请求
2. **服务端验证**：
   - 验证英雄归属
   - 验证星级要求
   - 验证资源充足
3. **资源扣除**：扣除升星材料和同名英雄
4. **星级更新**：提升星级
5. **数据保存**：保存到数据库
6. **响应通知**：返回结果和资源变化

### 3.3 英雄觉醒流程

1. **客户端请求**：发送觉醒请求
2. **服务端验证**：
   - 验证英雄归属
   - 验证觉醒条件
   - 验证资源充足
3. **资源扣除**：扣除觉醒道具
4. **觉醒更新**：提升觉醒等级
5. **数据保存**：保存到数据库
6. **响应通知**：返回结果和资源变化

## 4. 配置表映射

### 4.1 英雄配置表 (heroInfo.xlsx)

| 字段 | 类型 | 说明 |
|------|------|------|
| id | INT | 英雄ID |
| name | STRING | 英雄名称 |
| heroType | INT | 英雄类型 |
| quality | INT | 品质 |
| expModelId | INT | 升级经验模板ID |
| starModelId | INT | 升星经验模板ID |
| heroAwakenData | INT | 觉醒模板ID |

### 4.2 升级经验表 (heroExp.xlsx)

| 字段 | 类型 | 说明 |
|------|------|------|
| level | INT | 等级 |
| exp | INT | 所需经验 |

### 4.3 升星配置表 (heroStar.xlsx)

| 字段 | 类型 | 说明 |
|------|------|------|
| star | INT | 星级 |
| heroDebris | INT | 所需碎片数量 |
| coins | INT | 所需金币 |

### 4.4 觉醒配置表 (heroAwaken.xlsx)

| 字段 | 类型 | 说明 |
|------|------|------|
| awaken | INT | 觉醒等级 |
| heroAwakenItem | INT | 所需觉醒道具 |
| itemCount | INT | 道具数量 |

## 5. 错误码定义

| 错误码 | 说明 |
|--------|------|
| 5001 | 英雄不存在 |
| 5002 | 英雄不属于当前玩家 |
| 5003 | 资源不足 |
| 5004 | 等级已达上限 |
| 5005 | 星级已达上限 |
| 5006 | 觉醒已达上限 |
| 5007 | 升星条件不满足 |
| 5008 | 觉醒条件不满足 |

## 6. 性能优化

1. **缓存策略**：英雄信息缓存到 Redis，减少数据库查询
2. **批量操作**：支持批量英雄操作
3. **异步处理**：非关键操作异步处理
4. **数据库索引**：优化查询性能

## 7. 扩展性设计

1. **策略模式**：不同英雄类型使用不同策略
2. **监听器模式**：英雄变化事件通知
3. **插件化**：支持功能扩展

## 8. 测试要点

1. **功能测试**：升级、升星、觉醒功能正确性
2. **边界测试**：等级上限、资源边界
3. **并发测试**：高并发下的数据一致性
4. **性能测试**：响应时间、吞吐量
