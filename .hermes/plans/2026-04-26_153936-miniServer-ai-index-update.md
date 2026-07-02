# miniServer AI Index Update Plan

**目标**: 验证并更新 miniServer 项目的 AI 指导文件，确保索引数据与实时仓库状态一致。

**当前状态**: 项目已有完整的 AI 文件层（AGENTS.md、CLAUDE.md、GEMINI.md、.github/copilot-instructions.md、docs/AI_PROJECT_INDEX.md 等），但部分数据存在 drift。

---

## 1. 发现的问题

### 1.1 Java 文件计数不准确
- **docs/JAVA_SOURCE_INDEX.md**: 声称 189 个 Java 文件（生成于 2026-04-26 00:14:30）
- **docs/AI_PROJECT_INDEX.md**: 声称最新扫描结果是 189 个 Java 文件（更新于 2026-04-25）
- **实时扫描结果**: 实际 190 个 Java 文件
- **结论**: 数据过时，需要更新

### 1.2 模块数量不准确
- **server/doc/module_index.md**: 声称聚合了 6 个模块（更新于 2026-04-21）
- **server/pom.xml 实际内容**: 聚合了 8 个模块
  ```xml
  <modules>
      <module>config</module>
      <module>proto</module>
      <module>tool</module>
      <module>core</module>
      <module>LoginServer</module>
      <module>GameServer</module>
      <module>GateServer</module>
      <module>BotServer</module>
  </modules>
  ```
- **结论**: 文档中提到的"独立模块未在聚合 pom 中"的说法已经过时，现在 LoginServer 和 GateServer 都已加入聚合

### 1.3 其他文档准确性
- **AGENTS.md**: 列出了 8 个模块，与实际 pom.xml 一致（更新于 2026-04-26 00:29）✓
- **STARTUP.SKILL.md**: 包含启动参数和验证规则（更新于 2026-04-25 21:23）
- **docs/DEV_WORKFLOW.md**: 开发工作流文档（更新于 2026-04-26）✓

---

## 2. 当前已存在的 AI 文件清单

以下文件已存在且大部分内容准确：

### 核心指导文件
- `AGENTS.md` — 所有 AI 的主指导文件（推荐优先读）
- `CLAUDE.md` — Claude 专用（指向 AGENTS.md）
- `GEMINI.md` — Gemini 专用（指向 AGENTS.md）
- `.github/copilot-instructions.md` — GitHub Copilot 专用（指向 AGENTS.md）

### 项目索引文件
- `docs/AI_PROJECT_INDEX.md` — AI 项目索引和文档映射
- `docs/JAVA_SOURCE_INDEX.md` — Java 源码索引（需要更新计数）
- `server/doc/module_index.md` — 模块级代码索引（需要更新模块数）
- `server/doc/net_packet_unification_plan.md` — 协议收敛设计草案

### 工作流文件
- `docs/DEV_WORKFLOW.md` — 开发工作流
- `docs/ROOT_LOOSE_FILES_AUDIT.md` — 根目录松散文件审计

### 运行时文件
- `STARTUP.SKILL.md` — 启动技能文件（参数和顺序）
- `nacos-config.txt` — 旧的本地 Nacos 说明（可能与 STARTUP.SKILL.md 冲突）

---

## 3. 需要更新的文件

### 3.1 高优先级（必须更新）

#### `docs/JAVA_SOURCE_INDEX.md`
**问题**: Java 文件计数从 189 过时为 190

**更新内容**:
- 重新扫描仓库，生成完整的文件列表
- 更新汇总统计：main/test/other 文件数
- 更新各模块的文件数和包数
- 确保生成的 timestamp 为最新

**验证方法**:
```bash
find /mnt/d/WORK/me/miniServer -type f -name "*.java" | wc -l
```

---

#### `server/doc/module_index.md`
**问题**: 声称聚合 6 个模块，实际是 8 个

**更新内容**:
- 修正"总体模块清单"部分：从 6 个改为 8 个
- 删除"另外仓库中还存在两个独立模块（未在聚合 pom 中）"的说明
- 更新各模块的文件数（需要实时扫描确认）
- 更新文档末尾的更新时间戳

**验证方法**:
```bash
cat /mnt/d/WORK/me/miniServer/server/pom.xml | grep "<module>" | wc -l
```

---

#### `docs/AI_PROJECT_INDEX.md`
**问题**: 引用了过时的计数数据

**更新内容**:
- 更新第 21 行：`最新实扫 Java 文件数: 189` → `190`
- 更新第 114 行：`最新统计为 189 个 Java 文件` → `190`
- 更新文档更新时间为当前日期（2026-04-26）

---

### 3.2 中优先级（建议更新）

#### `README.md`
**问题**: 部分描述较理想化，与当前实际启动流程有差异

**可选更新内容**:
- 添加指向 `STARTUP.SKILL.md` 的链接，说明这是真实的运行参数来源
- 更新"启动各服务器模块"部分，引用更准确的启动参数
- 添加指向 `docs/DEV_WORKFLOW.md` 的链接，说明详细开发流程

**注意**: 如果用户表示 README 仍在维护中，可以暂不修改。

---

#### `nacos-config.txt`
**问题**: 包含 localhost Nacos 配置，与 STARTUP.SKILL.md 的远程 Nacos 配置冲突

**可选更新内容**:
- 在文件顶部添加历史说明标记，例如：
  ```
  # 历史说明：此文件记录的是本地 Nacos (localhost:8848) 的配置
  # 当前项目的实际运行参数以 STARTUP.SKILL.md 为准
  # STARTUP.SKILL.md 指向远程 Nacos: 118.25.76.117:8848
  ```

---

## 4. 执行步骤

### Step 1: 验证实时仓库状态
```bash
# 统计 Java 文件总数
find /mnt/d/WORK/me/miniServer -type f -name "*.java" | wc -l

# 验证聚合模块数
cat /mnt/d/WORK/me/miniServer/server/pom.xml | grep "<module>" | wc -l

# 列出所有模块
cat /mnt/d/WORK/me/miniServer/server/pom.xml | grep "<module>"
```

### Step 2: 更新 `docs/JAVA_SOURCE_INDEX.md`
- 使用 Python 脚本扫描仓库，生成新的索引文件
- 确保包含：文件路径、源码类别（main/test/other）、包名、主类型
- 更新汇总统计和各模块概览

### Step 3: 更新 `server/doc/module_index.md`
- 修正模块数量从 6 到 8
- 删除关于"独立模块"的过时描述
- 更新各模块的文件数（基于 Step 2 的结果）
- 更新文档更新时间

### Step 4: 更新 `docs/AI_PROJECT_INDEX.md`
- 修正 Java 文件计数从 189 到 190
- 更新文档更新时间

### Step 5: 可选 - 更新 `nacos-config.txt`
- 添加历史说明标记
- 避免与 STARTUP.SKILL.md 混淆

### Step 6: 验证
```bash
# 检查 git 状态
git status

# 确认修改的文件
git diff
```

### Step 7: 提交
```bash
# 添加更新的文件
git add docs/JAVA_SOURCE_INDEX.md server/doc/module_index.md docs/AI_PROJECT_INDEX.md

# 提交
git commit -m "docs: 更新 AI 索引文件以反映当前仓库状态

- 修正 Java 文件计数：189 → 190
- 修正聚合模块数：6 → 8
- 更新 docs/AI_PROJECT_INDEX.md 中的引用
- 更新 server/doc/module_index.md 移除过时的'独立模块'说明
"
```

---

## 5. 风险与注意事项

### 5.1 风险
- **无高风险**: 所有更新都是数据修正，不涉及代码逻辑
- **格式风险**: 更新 JAVA_SOURCE_INDEX.md 时需保持格式一致

### 5.2 注意事项
- **不要删除现有文件**: 所有更新都是修补，不是重建
- **保持一致性**: 确保所有文档中引用的数字保持一致
- **保留历史说明**: 对于可能冲突的文件（如 nacos-config.txt），添加说明而不是删除

---

## 6. 验证清单

更新完成后，验证以下内容：

- [ ] `docs/JAVA_SOURCE_INDEX.md` 中的 Java 文件总数等于 190
- [ ] `server/doc/module_index.md` 中的模块数等于 8
- [ ] `docs/AI_PROJECT_INDEX.md` 中的引用计数已更新
- [ ] 所有文档的更新时间戳为 2026-04-26
- [ ] git status 显示的修改文件与预期一致
- [ ] 所有修改已提交

---

## 7. 预期输出

更新后的文件应反映：
- Java 文件总数：190
- 聚合模块数：8 个（config, proto, tool, core, LoginServer, GameServer, GateServer, BotServer）
- 更新时间：2026-04-26

---

## 8. 后续建议

如果用户需要进一步优化项目，可以考虑：
1. **完善测试覆盖**: 目前只有 tool 模块有测试文件，其他模块可以补充
2. **统一配置管理**: 将分散的配置文件（如 nacos-config.txt）整合或明确标记
3. **更新 README**: 添加指向 AI 文件层的链接，方便新人快速上手
4. **添加 CI/CD**: 可以考虑添加 GitHub Actions 来自动验证构建

---

**计划创建时间**: 2026-04-26
**预期执行时间**: 15-30 分钟
