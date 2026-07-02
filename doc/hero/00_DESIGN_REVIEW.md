# Hero 模块设计方案审核报告

## 审核时间
2026-04-26

## 设计方案文件
- 01_DESIGN_MODULE_BASED.md（基于模块的设计方案）
- 02_IMPLEMENTATION_PLAN.md（实施计划）

## 审核结论
**总体评价：设计方案合理可行，但需要修复部分不一致和缺失问题后再实施**

---

## 审核发现

### 1. 设计方案与实施计划不一致（严重）

**问题：**
- 设计方案使用 PlayerModule 模块化存储，通过 player.modules 字段存储
- 实施计划却提到创建 player_hero/player_resource 表

**审核结论：**
应该统一采用设计方案的模块化方式，因为：
- 项目已使用 PlayerModule 框架（PlayerModuleData 使用 Map<String, byte[]> 存储）
- PlayerLogicModule 已经实现了模块化示例
- 模块化存储更轻量，不需要建表

**建议：** 废弃实施计划中的数据库表设计，统一采用 01_DESIGN_MODULE_BASED.md 的方案

---

### 2. 消息处理机制不明确（中等）

**问题：**
- 设计方案提到 HeroHandler 但未说明如何注册到 NetService
- 现有项目中未找到消息处理器注册机制

**审核结论：**
需要补充消息注册机制。可能的方式：
1. 在 NetService 中手动注册 Handler
2. 使用注解自动注册
3. 通过配置文件注册

**建议：** 实现前先查看 NetService 是否有现有的消息注册机制

---

### 3. 配置表缺失（严重）

**问题：**
设计方案引用的配置类在项目中不存在：
- HeroExpConfig（升级经验配置）
- HeroStarConfig（升星配置）
- HeroAwakenConfig（觉醒配置）

**审核结论：**
这些配置类必须先存在才能实现英雄功能。

**建议：**
1. 检查 excel 目录是否有对应的配置表文件
2. 如果没有，需要先创建配置表
3. 生成配置类（参考 HeroInfoConfig 的生成方式）

---

### 4. 资源类型定义位置不统一（轻微）

**问题：**
设计方案中 ResourceType 定义在 ly.logic.resource 包，但通常常量类应该放在 config 包

**审核结论：**
建议统一放在 config 包，便于全局访问和管理

**建议：** 将 ResourceType 定义在 ly.config.ResourceType

---

### 5. 错误码定义位置不明确（轻微）

**问题：**
设计方案错误码定义在 ly.proto.ErrorMsg，但项目中已有 ly.loginserver.result.ErrorCode

**审核结论：**
应该统一使用项目的错误码管理方式

**建议：** 查看 GameServer 是否有独立的错误码定义，如果没有，创建 ly.proto.ErrorCode

---

### 6. Proto 协议文件缺失（严重）

**问题：**
设计方案详细描述了 Hero.proto 和 Resource.proto，但 proto 目录下没有这些文件

**审核结论：**
必须先创建 Proto 文件并生成 Java 代码

**建议：**
1. 创建 proto/Hero.proto
2. 创建 proto/Resource.proto
3. 更新 proto/Cmd.proto 添加新的消息号
4. 生成 Proto Java 代码

---

### 7. HeroEntry 中 heroUid 生成规则不合理（中等）

**问题：**
设计方案中 heroUid 生成规则是：playerId * 100000 + heroId
- 如果 playerId 超过 10000，可能发生冲突
- 不符合分布式 ID 设计原则

**审核结论：**
建议使用更可靠的 ID 生成方案：
- playerId * 1000000 + heroId（扩大范围）
- 或使用雪花算法
- 或使用数据库自增

**建议：** 改为 playerId * 1000000 + heroId

---

### 8. 配置依赖未明确（中等）

**问题：**
设计方案引用了配置模板 ID：
- expModelId（升级经验模板）
- starModelId（升星模板）
- heroAwakenData（觉醒模板）

但未说明这些模板的存储结构和读取方式。

**审核结论：**
需要明确这些模板的配置类

**建议：**
1. 检查是否有通用的模板配置类
2. 如果没有，需要创建对应的模板配置

---

## 实施建议

### 优先级排序

**P0（必须先完成）：**
1. 创建/验证配置表
2. 创建 Proto 文件
3. 实现基础数据结构

**P1（核心功能）：**
1. 实现 HeroModule
2. 实现 ResourceModule
3. 实现消息处理器

**P2（优化完善）：**
1. 单元测试
2. 集成测试
3. 文档更新

### 实施步骤调整

**阶段一：准备工作（1天）**
- [ ] 检查并补充配置表（HeroExpConfig、HeroStarConfig、HeroAwakenConfig）
- [ ] 创建 proto/Hero.proto
- [ ] 创建 proto/Resource.proto
- [ ] 更新 proto/Cmd.proto
- [ ] 生成 Proto Java 代码

**阶段二：基础数据结构（0.5天）**
- [ ] 创建 HeroModuleData、HeroEntry
- [ ] 创建 ResourceModuleData
- [ ] 修正 heroUid 生成规则

**阶段三：模块实现（1天）**
- [ ] 实现 HeroModule（继承 AbstractModule）
- [ ] 实现 ResourceModule（继承 AbstractModule）
- [ ] 注册到 ModuleEnum

**阶段四：网络层（1天）**
- [ ] 实现 HeroHandler
- [ ] 注册消息处理器
- [ ] 实现协议转换

**阶段五：业务逻辑（1-2天）**
- [ ] 实现英雄查询
- [ ] 实现英雄升级
- [ ] 实现英雄升星
- [ ] 实现英雄觉醒

**阶段六：测试（1天）**
- [ ] 单元测试
- [ ] 功能测试
- [ ] 集成测试

---

## 风险评估

### 高风险项
1. **配置表缺失**：可能需要重新设计配置表结构
2. **消息注册机制不明确**：可能需要调整现有架构
3. **ID 生成冲突**：需要仔细验证唯一性

### 中风险项
1. **资源操作并发**：需要保证原子性
2. **数据序列化**：Protobuf 序列化需要充分测试
3. **性能优化**：大量英雄查询需要缓存

---

## 最终建议

1. **立即开始准备工作**：先解决配置表和 Proto 文件问题
2. **采用设计方案**：使用 01_DESIGN_MODULE_BASED.md 的模块化方案
3. **分阶段实施**：按调整后的实施步骤逐步推进
4. **充分测试**：每个阶段完成后进行单元测试
5. **文档同步**：实施过程中同步更新设计文档

---

## 下一步行动

1. 检查配置表文件
2. 创建 Proto 文件
3. 开始实施阶段一
