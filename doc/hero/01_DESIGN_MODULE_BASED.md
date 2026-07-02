# Hero 模块设计方案（基于 PlayerModule 框架）

## 1. 模块概述

Hero 模块作为 Player 的子模块实现，不创建独立的数据库表，所有数据通过 `PlayerModuleData` 存储在 `player.modules` 字段中。

### 1.1 功能范围

- **英雄管理**：英雄的获取、查询、列表展示
- **英雄升级**：消耗经验道具提升等级
- **英雄升星**：消耗升星道具和同名英雄提升星级
- **英雄觉醒**：消耗觉醒道具开启技能加成
- **资源管理**：消耗和获取游戏资源（作为独立子模块）

### 1.2 设计原则

1. **模块化**：继承 AbstractModule，挂载到 Player
2. **配置驱动**：所有数值均由配置表控制，支持热更新
3. **事务性**：资源操作必须保证原子性
4. **数据安全**：关键操作需客户端-服务端双重验证

## 2. 数据结构设计

### 2.1 模块数据结构（Protobuf）

#### 2.1.1 HeroModuleData（英雄模块数据）

```java
package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import java.util.ArrayList;
import java.util.List;

/**
 * 英雄模块数据
 */
@ProtobufClass
@EnableZigZap
public class HeroModuleData {
    @Protobuf(fieldType = FieldType.OBJECT, order = 1, required = false)
    public List<HeroEntry> heroList = new ArrayList<>();
    
    @Protobuf(fieldType = FieldType.INT32, order = 2, required = false)
    public int maxHeroCount = 100; // 最大英雄数量
}
```

#### 2.1.2 HeroEntry（英雄数据）

```java
package ly.logic.hero.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;

/**
 * 英雄数据实体
 */
@ProtobufClass
@EnableZigZap
public class HeroEntry {
    @Protobuf(fieldType = FieldType.INT64, order = 1, required = false)
    public long heroUid;  // 英雄唯一ID（生成规则：playerId * 100000 + heroId）
    
    @Protobuf(fieldType = FieldType.INT32, order = 2, required = false)
    public int heroId;    // 英雄配置ID
    
    @Protobuf(fieldType = FieldType.INT32, order = 3, required = false)
    public int level;     // 等级
    
    @Protobuf(fieldType = FieldType.INT32, order = 4, required = false)
    public int star;      // 星级
    
    @Protobuf(fieldType = FieldType.INT32, order = 5, required = false)
    public int awaken;    // 觉醒等级
    
    @Protobuf(fieldType = FieldType.INT64, order = 6, required = false)
    public long exp;      // 当前经验
}
```

#### 2.1.3 ResourceModuleData（资源模块数据）

```java
package ly.logic.resource.module;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.baidu.bjf.remoting.protobuf.annotation.EnableZigZap;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源模块数据
 */
@ProtobufClass
@EnableZigZap
public class ResourceModuleData {
    @Protobuf(fieldType = FieldType.MAP, order = 1, required = false)
    public Map<Integer, Long> resources = new HashMap<>();
}
```

### 2.2 配置表结构

#### 2.2.1 HeroInfoConfig（英雄基础配置）
- 已存在：`ly.config.HeroInfoConfig`
- 管理器：`ly.config.HeroInfoConfigManager`

#### 2.2.2 HeroExpConfig（升级经验配置）
```java
package ly.config;

public class HeroExpConfig {
    public int level;      // 等级
    public int exp;        // 升级所需经验
}
```

#### 2.2.3 HeroStarConfig（升星配置）
```java
package ly.config;

import java.util.List;
import ly.utils.KV;

public class HeroStarConfig {
    public int star;                  // 星级
    public int maxLevel;              // 该星级最大等级
    public int heroDebrisCount;       // 所需碎片数量
    public int costCoin;              // 所需金币
    public List<KV<Integer, Integer>> costItems; // 所需道具列表（道具ID，数量）
}
```

#### 2.2.4 HeroAwakenConfig（觉醒配置）
```java
package ly.config;

public class HeroAwakenConfig {
    public int awaken;       // 觉醒等级
    public int costItemId;   // 所需觉醒道具ID
    public int costCount;    // 道具数量
    public int unlockSkill;  // 解锁技能ID
}
```

### 2.3 Proto 协议设计

#### 2.3.1 英雄协议
```protobuf
// 获取英雄列表请求
message CS_HeroList {
}

// 英雄信息
message HeroInfo {
    int64 hero_uid = 1;
    int32 hero_id = 2;
    int32 level = 3;
    int32 star = 4;
    int32 awaken = 5;
    int64 exp = 6;
    int64 max_exp = 7;
}

// 获取英雄列表响应
message SC_HeroList {
    repeated HeroInfo hero_list = 1;
}

// 英雄升级请求
message CS_HeroLevelUp {
    int64 hero_uid = 1;
    repeated int32 exp_item_ids = 2;
}

// 英雄升级响应
message SC_HeroLevelUp {
    int32 result = 1;
    int32 error_code = 2;
    HeroInfo hero_info = 3;
}

// 英雄升星请求
message CS_HeroStarUp {
    int64 hero_uid = 1;
}

// 英雄升星响应
message SC_HeroStarUp {
    int32 result = 1;
    int32 error_code = 2;
    HeroInfo hero_info = 3;
}

// 英雄觉醒请求
message CS_HeroAwaken {
    int64 hero_uid = 1;
}

// 英雄觉醒响应
message SC_HeroAwaken {
    int32 result = 1;
    int32 error_code = 2;
    HeroInfo hero_info = 3;
}
```

#### 2.3.2 资源协议
```protobuf
// 资源变化通知
message SC_ResourceChange {
    map<int32, int64> changes = 1;
}

// 资源查询响应
message SC_ResourceQuery {
    map<int32, int64> resources = 1;
}
```

## 3. 模块实现设计

### 3.1 HeroModule（英雄模块）

```java
package ly.logic.hero.module;

import ly.logic.player.AbstractModule;
import ly.logic.player.ModuleEnum;
import ly.config.HeroInfoConfig;
import ly.config.HeroInfoConfigManager;
import java.util.List;
import java.util.ArrayList;

/**
 * 英雄模块
 */
public class HeroModule extends AbstractModule {
    private HeroModuleData moduleData;
    
    @Override
    public void onLoadData() {
        byte[] data = player.getPlayerData().getModuleData(ModuleEnum.HERO_MODULE);
        if (data != null && data.length > 0) {
            // 从字节数组加载
            // Codec<HeroModuleData> codec = ProtobufProxy.create(HeroModuleData.class);
            // moduleData = codec.decode(data);
        } else {
            moduleData = new HeroModuleData();
        }
    }
    
    @Override
    public boolean saveData() {
        // 序列化并保存到 PlayerModuleData
        // byte[] data = ProtobufProxy.create(HeroModuleData.class).encode(moduleData);
        // player.getPlayerData().getModuleData().addModuleData(ModuleEnum.HERO_MODULE.getName(), data);
        return true;
    }
    
    @Override
    public void onOpenFunction() {
        // 功能开放时的初始化
    }
    
    /**
     * 获取英雄列表
     */
    public List<HeroEntry> getHeroList() {
        return moduleData.heroList;
    }
    
    /**
     * 获取单个英雄
     */
    public HeroEntry getHero(long heroUid) {
        for (HeroEntry hero : moduleData.heroList) {
            if (hero.heroUid == heroUid) {
                return hero;
            }
        }
        return null;
    }
    
    /**
     * 添加英雄
     */
    public HeroEntry addHero(int heroId) {
        long heroUid = generateHeroUid(heroId);
        HeroEntry hero = new HeroEntry();
        hero.heroUid = heroUid;
        hero.heroId = heroId;
        hero.level = 1;
        hero.star = 1;
        hero.awaken = 0;
        hero.exp = 0;
        moduleData.heroList.add(hero);
        return hero;
    }
    
    /**
     * 生成英雄唯一ID
     */
    private long generateHeroUid(int heroId) {
        return player.getPlayerId() * 100000L + heroId;
    }
}
```

### 3.2 ResourceModule（资源模块）

```java
package ly.logic.resource.module;

import ly.logic.player.AbstractModule;
import ly.logic.player.ModuleEnum;
import java.util.Map;

/**
 * 资源模块
 */
public class ResourceModule extends AbstractModule {
    private ResourceModuleData moduleData;
    
    @Override
    public void onLoadData() {
        byte[] data = player.getPlayerData().getModuleData(ModuleEnum.RESOURCE_MODULE);
        if (data != null && data.length > 0) {
            // 从字节数组加载
        } else {
            moduleData = new ResourceModuleData();
            // 初始化默认资源
            moduleData.resources.put(ResourceType.GOLD, 0L);
            moduleData.resources.put(ResourceType.DIAMOND, 0L);
        }
    }
    
    @Override
    public boolean saveData() {
        // 序列化并保存
        return true;
    }
    
    @Override
    public void onOpenFunction() {
    }
    
    /**
     * 增加资源
     */
    public boolean addResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = moduleData.resources.getOrDefault(resourceType, 0L);
        moduleData.resources.put(resourceType, current + amount);
        notifyResourceChange(resourceType, amount);
        return true;
    }
    
    /**
     * 扣除资源
     */
    public boolean deductResource(int resourceType, long amount) {
        if (amount <= 0) return false;
        long current = moduleData.resources.getOrDefault(resourceType, 0L);
        if (current < amount) {
            return false;
        }
        moduleData.resources.put(resourceType, current - amount);
        notifyResourceChange(resourceType, -amount);
        return true;
    }
    
    /**
     * 获取资源数量
     */
    public long getResource(int resourceType) {
        return moduleData.resources.getOrDefault(resourceType, 0L);
    }
    
    /**
     * 通知资源变化
     */
    private void notifyResourceChange(int resourceType, long amount) {
        // 发送 SC_ResourceChange 消息
    }
}
```

### 3.3 资源类型常量

```java
package ly.logic.resource;

public class ResourceType {
    public static final int GOLD = 1;        // 金币
    public static final int DIAMOND = 2;     // 钻石
    public static final int HERO_DEBRIS = 3; // 英雄碎片
    public static final int EXP_ITEM = 4;    // 经验道具
    public static final int AWAKEN_ITEM = 5; // 觉醒道具
}
```

## 4. 模块注册

### 4.1 更新 ModuleEnum

```java
package ly.logic.player;

public enum ModuleEnum {
    PLAYER_LOGIC_MODULE(new PlayerLogicModule()),
    HERO_MODULE(new HeroModule()),
    RESOURCE_MODULE(new ResourceModule()),
    ;
    
    private AbstractModule module;
    
    ModuleEnum(AbstractModule module) {
        this.module = module;
    }
    
    public AbstractModule getModule() {
        return module;
    }
    
    public String getName() {
        return this.module.getClass().getName();
    }
}
```

## 5. 消息处理器

### 5.1 HeroHandler（英雄消息处理器）

```java
package ly.logic.hero.handler;

import io.netty.channel.ChannelHandlerContext;
import ly.logic.player.Player;
import ly.logic.hero.module.HeroModule;
import ly.proto.Cmd;
import ly.proto.Hero;
import ly.net.packet.AbstractMessagePacket;

/**
 * 英雄消息处理器
 */
public class HeroHandler {
    
    /**
     * 处理获取英雄列表
     */
    public void handleHeroList(Player player, Hero.CS_HeroList msg) {
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        List<HeroEntry> heroList = heroModule.getHeroList();
        
        Hero.SC_HeroList.Builder builder = Hero.SC_HeroList.newBuilder();
        for (HeroEntry entry : heroList) {
            Hero.HeroInfo info = buildHeroInfo(entry);
            builder.addHeroList(info);
        }
        
        player.sendMsg(Cmd.CMD.SC_HeroList, builder.build());
    }
    
    /**
     * 处理英雄升级
     */
    public void handleHeroLevelUp(Player player, Hero.CS_HeroLevelUp msg) {
        HeroModule heroModule = (HeroModule) player.getPlayerData().getModule(ModuleEnum.HERO_MODULE);
        ResourceModule resourceModule = (ResourceModule) player.getPlayerData().getModule(ModuleEnum.RESOURCE_MODULE);
        
        // 验证和升级逻辑
        HeroEntry hero = heroModule.getHero(msg.getHeroUid());
        if (hero == null) {
            player.sendErrorCode(Cmd.CMD.CS_HeroLevelUp, ErrorMsg.ErrorCode.HERO_NOT_FOUND);
            return;
        }
        
        // 扣除资源
        // 计算经验
        // 升级
        // 保存
        // 返回结果
    }
    
    /**
     * 处理英雄升星
     */
    public void handleHeroStarUp(Player player, Hero.CS_HeroStarUp msg) {
        // 类似升级逻辑
    }
    
    /**
     * 处理英雄觉醒
     */
    public void handleHeroAwaken(Player player, Hero.CS_HeroAwaken msg) {
        // 类似升级逻辑
    }
    
    private Hero.HeroInfo buildHeroInfo(HeroEntry entry) {
        Hero.HeroInfo.Builder builder = Hero.HeroInfo.newBuilder();
        builder.setHeroUid(entry.heroUid);
        builder.setHeroId(entry.heroId);
        builder.setLevel(entry.level);
        builder.setStar(entry.star);
        builder.setAwaken(entry.awaken);
        builder.setExp(entry.exp);
        // 计算升级所需经验
        builder.setMaxExp(calcMaxExp(entry.level));
        return builder.build();
    }
}
```

## 6. 错误码定义

```java
package ly.proto.ErrorMsg;

public enum ErrorCode {
    HERO_NOT_FOUND(5001, "英雄不存在"),
    HERO_NOT_BELONG(5002, "英雄不属于当前玩家"),
    RESOURCE_NOT_ENOUGH(5003, "资源不足"),
    LEVEL_MAX(5004, "等级已达上限"),
    STAR_MAX(5005, "星级已达上限"),
    AWAKEN_MAX(5006, "觉醒已达上限"),
    STAR_CONDITION_NOT_MET(5007, "升星条件不满足"),
    AWAKEN_CONDITION_NOT_MET(5008, "觉醒条件不满足"),
}
```

## 7. 业务流程

### 7.1 英雄升级流程
1. 客户端发送 CS_HeroLevelUp
2. 验证英雄存在
3. 验证资源充足（ResourceModule）
4. 扣除资源
5. 计算经验增加值
6. 累加经验，升级等级
7. 保存数据（saveData）
8. 返回 SC_HeroLevelUp

### 7.2 英雄升星流程
1. 客户端发送 CS_HeroStarUp
2. 验证英雄存在
3. 验证星级条件
4. 验证资源充足
5. 扣除资源
6. 星级+1
7. 保存数据
8. 返回 SC_HeroStarUp

### 7.3 英雄觉醒流程
1. 客户端发送 CS_HeroAwaken
2. 验证英雄存在
3. 验证觉醒条件
4. 验证资源充足
5. 扣除资源
6. 觉醒等级+1
7. 保存数据
8. 返回 SC_HeroAwaken

## 8. 实现计划

### 第一阶段：基础框架（1天）
- [ ] 创建 HeroModuleData、HeroEntry
- [ ] 创建 ResourceModuleData
- [ ] 创建 HeroModule、ResourceModule
- [ ] 注册到 ModuleEnum

### 第二阶段：资源系统（1天）
- [ ] 实现 ResourceModule 核心方法
- [ ] 实现资源通知机制

### 第三阶段：英雄核心功能（2天）
- [ ] 实现英雄查询
- [ ] 实现英雄升级
- [ ] 实现英雄升星
- [ ] 实现英雄觉醒

### 第四阶段：网络层集成（1天）
- [ ] 创建 HeroHandler
- [ ] 注册消息处理器
- [ ] 实现 Proto 消息

### 第五阶段：测试（1天）
- [ ] 单元测试
- [ ] 功能测试

## 9. 文件结构

```
server/GameServer/src/main/java/ly/logic/
├── hero/
│   ├── module/
│   │   ├── HeroModuleData.java
│   │   ├── HeroEntry.java
│   │   └── HeroModule.java
│   ├── handler/
│   │   └── HeroHandler.java
│   └── HeroConstant.java
├── resource/
│   ├── module/
│   │   ├── ResourceModuleData.java
│   │   └── ResourceModule.java
│   └── ResourceType.java
└── player/
    └── ModuleEnum.java (更新)
```
